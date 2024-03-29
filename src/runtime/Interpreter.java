package runtime;

import frontend.Parser;
import frontend.Token;
import frontend.Tree;
import frontend.j0;

import java.util.ArrayList;

import static runtime.PrimitiveHandler.analyzeOperator;
import static runtime.PrimitiveHandler.processOperator;


public class Interpreter {

    public static void semantic(Tree root) {
        SymbolTable global_scope = new SymbolTable("global");
        root.scope = global_scope;
        FunctionHandler.initBuiltin(global_scope);
        analyzeBlock(root, global_scope);
    }

    public static void analyzeBlock(Tree node, SymbolTable scope) {
        switch (node.sym){
            case "LocalVarDecl" -> {
                declareVar(node.kids[0].tok, node.kids[1].tok.text, scope);
            }
            case "Assignment" -> {
                String var_name = node.kids[0].tok.text;
                var result = analyzeExpr(node.kids[2], scope);
                if (!scope.lookFor(var_name))scope.addVar(var_name, result);
                else checkType(scope.getVar(var_name).type, result.type);
            }
            case "MethodDecl" -> {
                String name = node.kids[0].tok.text;
                if (!scope.name.equals("global"))j0.error("cannot declare function "+name+" not in global scope");

                if (scope.lookFor(name)){
                    j0.semerror("Redeclaration of " + name);
                } else {
                    Tree params;
                    Tree block;
                    if (node.kids.length > 2) {
                        params = node.kids[1];
                        block = node.kids[2];
                    } else {
                        block = node.kids[1];
                        params = null;
                    }

                    /* todo analyze params */

                    var func_scope = new SymbolTable("func", scope);
                    func_scope.is_inside_function = true;

                    var params_list = new ArrayList<Tree>();
                    FunctionHandler.addArgsToArrayList(params, params_list);

                    for (var param : params_list){
                        declareVar(param.kids[0].tok, param.kids[1].tok.text, func_scope);
                    }

                    scope.addVar(name, new RuntimeValue(ValueType.FUNCTION, new FunctionType(params, block)));

                    block.scope = func_scope;
                    analyzeBlock(block, func_scope);

                }
            }
            case "MethodCall" -> {
                checkMethodCall(node, scope);
            }
            case "IfStmt","IfElseStmt" -> {
                var r = analyzeExpr(node.kids[0], scope);
                checkType(ValueType.BOOL, r.type);

                var if_block = node.kids[1];
                var if_scope = new SymbolTable("if", scope);
                if_block.scope = if_scope;
                analyzeBlock(if_block, if_scope);
                if (node.sym.equals("IfElseStmt")){
                    var else_block = node.kids[2];
                    var else_scope = new SymbolTable("else", scope);
                    else_block.scope = else_scope;
                    analyzeBlock(else_block, else_scope);
                }

                /*todo check uninitialized vars after if*/
            }
            case "WhileStmt", "DoWhileStmt"->{
                var r = analyzeExpr(node.kids[0], scope);
                checkType(ValueType.BOOL, r.type);

                var while_block = node.kids[1];
                var while_scope = new SymbolTable("while", scope);
                while_block.scope = while_scope;
                analyzeBlock(while_block, while_scope);
            }
            case "ForStmt"->{
                var for_header = node.kids[0];
                // todo analyze for_header

                var for_block = node.kids[1];
                var for_scope = new SymbolTable("for", scope);
                for_block.scope = for_scope;
                analyzeBlock(for_block, for_scope);
            }
            case "ReturnStmt"->{
                if (!scope.is_inside_function)j0.error("return outside function");
                analyzeExpr(node.kids[0], scope);
            }
            case "BlockStmtsOpt", "BlockStmts", "Block" ->{
                for (Tree kid : node.kids) {
                    analyzeBlock(kid, scope);

                }
            }
            default -> {}
        }
    }

    public static RuntimeValue analyzeExpr(Tree node, SymbolTable scope) {
        switch (node.sym){
            case "token"->{
                Token t = node.tok;
                if (t.code == Parser.IDENTIFIER){
                    var var_name = t.text;
                    if (!scope.lookFor(var_name)){
                        j0.semerror("Unknown name "+ var_name);
                        return null;
                    }
                    return scope.getVar(var_name);
                }
                return PrimitiveHandler.literalToValue(node.tok);
            }
            case "MethodCall"->{
                var result = checkMethodCall(node, scope);
                return result;
                //                todo
                //                if (result == null || result.type == ValueType.NONE)j0.error("function "+node.kids[0].tok.text+" does not return a value");
            }
            case "AddExpr", "MulExpr","RelExpr","EqExpr","CondAndExpr","CondOrExpr"->{
                var left = analyzeExpr(node.kids[0], scope);
                var op = node.kids[1].tok.text;
                var right = analyzeExpr(node.kids[2], scope);
                return analyzeOperator(left, op, right);
            }

        }
        return null;
    }

    public static void declareVar(Token type, String var_name, SymbolTable scope){
        if (scope.contains(var_name)){
            j0.semerror("Redeclaration of " + var_name);
            return;
        }
        scope.addVar(var_name, new RuntimeValue(PrimitiveHandler.tokenToType(type)));
    }

    public static boolean checkType(int expected, int provided){
        if (provided!=expected){
            j0.semerror("Assign type mismatch for");/*todo var name and type names*/
            return false;
        }
        return true;
    }

    public static boolean checkVar(String name, SymbolTable scope, int expected_type){
        if(scope.lookFor(name)){
            return checkType(expected_type, scope.getVar(name).type);
        }else {
            j0.semerror("Unknown name "+ name);
            return false;
        }
    }

    public static RuntimeValue checkMethodCall(Tree node, SymbolTable scope){
        // todo calc func return type
        String name = node.kids[0].tok.text;
        if (checkVar(name, scope, ValueType.FUNCTION)) {
            var func = ((FunctionType) scope.getVar(name).value);
            if (node.kids.length > 1){
                var args = node.kids[1];
                var args_list = new ArrayList<Tree>();
                FunctionHandler.addArgsToArrayList(args, args_list);
                for (var expr : args_list)analyzeExpr(expr, scope);
            }
            return new RuntimeValue(func.return_type);
        }
        return null;
    }


    static public void interpret(Tree root) {
//        evalBlock(root, root.scope);
    }

    public static RuntimeValue evalBlock(Tree node, SymbolTable scope) {
        if (node==null)return null;
        switch (node.sym) {
            case "Assignment" -> {
                String var_name = node.kids[0].tok.text;
                var result = evalExpr(node.kids[2], scope);
                scope.setVar(var_name, result);
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
            case "ForStmt"->{
                return processFor(node.kids[0], node.kids[1], scope);
            }
            case "ReturnStmt"->{
                if (node.kids.length==0)return new RuntimeValue(ValueType.NONE);
                return evalExpr(node.kids[0], scope);
            }
            case "BlockStmtsOpt", "BlockStmts", "Block" ->{
                for (Tree kid : node.kids) {
                    var result = evalBlock(kid, scope);
                    if (result!=null)return result;
                }
            }
            case "LocalVarDecl", "MethodDecl" -> { /* processed on semantic phase */ }
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

    static RuntimeValue processDoWhile(Tree expr, Tree block, SymbolTable scope) {
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
