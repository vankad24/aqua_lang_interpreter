package runtime;

import frontend.Parser;
import frontend.Token;
import frontend.Tree;
import frontend.j0;

import static runtime.PrimitiveHandler.processOperator;


public class Interpreter {

    public void semantic(Tree root) {
        System.out.println("semantic");
    }

    static String[] built_in_functions = {"print", "println", "readInt", "readFloat"};

    static public void interpret(Tree root) {
        SymbolTable global_scope = new SymbolTable("global");
        for (var name : built_in_functions) {
            global_scope.addVar(name, new RuntimeValue(ValueType.FUNCTION, new FunctionType()));
        }
        evalBlock(root, global_scope);
    }

    public static RuntimeValue evalBlock(Tree node, SymbolTable scope) {
        if (node==null)return null;
        switch (node.sym) {
            case "LocalVarDecl" -> {
                var type = node.kids[0].tok;
                String var_name = node.kids[1].tok.text;
                scope.addVar(var_name, new RuntimeValue(PrimitiveHandler.tokenToType(type)));
            }
            case "Assignment" -> {
                String var_name = node.kids[0].tok.text;
                var result = evalExpr(node.kids[2], scope);
                scope.setVar(var_name, result);
            }

            case "MethodDecl" -> {
                String name = node.kids[0].tok.text;
                if (!scope.name.equals("global"))j0.error("cannot declare function "+name+" not in global scope");
                Tree params;
                Tree block;
                if (node.kids.length > 2){
                    params = node.kids[1];
                    block = node.kids[2];
                } else {
                    block = node.kids[1];
                    params = null;
                }
                scope.addVar(name, new RuntimeValue(ValueType.FUNCTION, new FunctionType(params, block)));
            }
            case "MethodCall" -> {
                processMethodCall(node, scope);
            }
            case "IfStmt" -> {
                return processIfElse(node.kids[0], node.kids[1], null, scope);
            }
            case "IfElseStmt" -> {
                return processIfElse(node.kids[0], node.kids[1], node.kids[2], scope);
            }
            case "WhileStmt" -> {
                return processWhile(node.kids[0], node.kids[1], scope);
            }
            case "DoWhileStmt" -> {
                return processDoWhile(node.kids[0], node.kids[1], scope);
            }
            case "BlockStmtsOpt", "BlockStmts", "Block" ->{
                for (Tree kid : node.kids) {
                    var result = evalBlock(kid, scope);
                    if (result!=null)return result;
                }
            }
            case "ForStmt"->{
                return processFor(node.kids[0], node.kids[1], scope);
            }
            case "ReturnStmt"->{
                if (!scope.is_inside_function)j0.error("return outside function");
                if (node.kids.length==0)return new RuntimeValue(ValueType.NONE);
                return evalExpr(node.kids[0], scope);
            }
            default -> {
                j0.error("the node is not implemented: "+node.sym+" "+node.tok);
            }
        }
        return null;
    }

    public static RuntimeValue evalExpr(Tree node, SymbolTable scope){
        switch (node.sym){
            case "token"->{
                Token t = node.tok;
                if (t.code == Parser.IDENTIFIER)
                    return scope.getVar(t.text);
                return PrimitiveHandler.literalToValue(node.tok);
            }
            case "MethodCall"->{
                var result = processMethodCall(node, scope);
                if (result == null || result.type == ValueType.NONE)j0.error("function "+node.kids[0].tok.text+" does not return a value");
                return result;
            }
            case "AddExpr", "MulExpr","RelExpr","EqExpr","CondAndExpr","CondOrExpr"->{
                var left = evalExpr(node.kids[0], scope);
                var op = node.kids[1].tok.text;
                var right = evalExpr(node.kids[2], scope);
                return processOperator(left, op, right);
            }

        }
        return null;
    }

    static RuntimeValue processIfElse(Tree condition_expr, Tree if_block, Tree else_block, SymbolTable scope){
        var condition = evalExpr(condition_expr, scope);
        if (condition.type != ValueType.BOOL) j0.error("not bool expression in the if stmt");
        if ((boolean) condition.value){
            return evalBlock(if_block, new SymbolTable("if_stmt", scope));
        }else if (else_block!=null){
            if (else_block.sym.equals("IfElseStmt"))return evalBlock(else_block, scope);
            else return evalBlock(else_block, new SymbolTable("else_stmt", scope));
        }
        return null;
    }

    static RuntimeValue processWhile(Tree expr, Tree block, SymbolTable scope) {
        var condition = evalExpr(expr, scope);
        if (condition.type != ValueType.BOOL) j0.error("not bool expression in the while stmt");
        while ((boolean) condition.value){
            var result = evalBlock(block, new SymbolTable("while_stmt", scope));
            if (result!=null)return result;
            condition = evalExpr(expr, scope);
        }
        return null;
    }

    static RuntimeValue processDoWhile(Tree block, Tree expr, SymbolTable scope) {
        var r = evalBlock(block, new SymbolTable("do_while_stmt", scope));
        if (r!=null)return r;
        var condition = evalExpr(expr, scope);
        if (condition.type != ValueType.BOOL) j0.error("not bool expression in the while stmt");
        while ((boolean) condition.value){
            r = evalBlock(block, new SymbolTable("do_while_stmt", scope));
            if (r!=null)return r;
            condition = evalExpr(expr, scope);
        }
        return null;
    }

    static RuntimeValue processFor(Tree header, Tree block, SymbolTable scope) {
        var for_scope = new SymbolTable("for_scope", scope);
        if (!header.sym.equals("ForNormal")&&!header.sym.equals("ForFull")) {
            int i = 0;
            while (true){
                var limit = evalExpr(header, scope);
                if (i>=(int)limit.value)break;
                var r =evalBlock(block, new SymbolTable("for_scope1", for_scope));
                if (r!=null)return r;
                i++;
            }
        }else {
            var var_init = header.kids[0];
            String var_name;
            if (var_init.sym.equals("ForVarInit")) {
                var t = header.kids[0];
                var_name = t.kids[0].tok.text;
                var result = evalExpr(t.kids[1], scope);
                if (result.type != ValueType.INTEGER) j0.error("not int expression in for init");
                for_scope.addVar(var_name, result);
            } else {
                var_name = var_init.tok.text;
                for_scope.addVar(var_name, new RuntimeValue(ValueType.INTEGER, 0));
            }
            var separator = header.kids[1];

            boolean greater_or_equal = false;
            if (!separator.tok.text.equals(":")) greater_or_equal = true;


            var limit = header.kids[2];
            boolean need_count_step = false;
            Tree step_expr = null;
            if (header.sym.equals("ForFull")) {
                step_expr = header.kids[3];
                need_count_step = true;
            }
            int i_value;
            while (true) {
                i_value = (int) for_scope.getVar(var_name).value;
                if (greater_or_equal) {
                    if (!(i_value <= (int) evalExpr(limit, scope).value)) break;
                } else {
                    if (!(i_value < (int) evalExpr(limit, scope).value)) break;
                }
                var r = evalBlock(block, new SymbolTable("for_scope", for_scope));
                if (r!=null)return r;
                int step;
                if (need_count_step) step = (int) evalExpr(step_expr, scope).value;
                else step = 1;
                for_scope.setVar(var_name, PrimitiveHandler.handleInt(i_value, step, "+"));
            }
        }
        return null;
    }

    static RuntimeValue processMethodCall(Tree node, SymbolTable scope){
        String name = node.kids[0].tok.text;
        Tree args;
        if (node.kids.length > 1)args = node.kids[1];
        else args = null;
        return FunctionHandler.eval(name, args, scope);
    }

}
