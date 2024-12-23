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
        switch (node.sym) {
            case "LocalVarDecl" -> {
                declareVar(node.kids[0].tok, node.kids[1].tok.text, scope);
            }
            case "Assignment" -> {
                String var_name = node.kids[0].tok.text;
                var result = analyzeExpr(node.kids[2], scope);
                var v = scope.getVar(var_name);
                if (v == null) scope.addVar(var_name, result);
                else checkType(v.type, result.type);
                scope.assigned_vars.add(var_name);
            }
            case "MethodDecl" -> {
                String name = node.kids[0].tok.text;
                if (!scope.name.equals("global")) j0.error("cannot declare function " + name + " not in global scope");

                var v = scope.getVar(name);
                if (v != null) {
                    j0.semerror("Redeclaration of " + name);
                }

                Tree params;
                Tree block;
                if (node.kids.length > 2) {
                    params = node.kids[1];
                    block = node.kids[2];
                } else {
                    params = null;
                    block = node.kids[1];
                }

                var func_scope = new SymbolTable("func", scope);
                func_scope.is_inside_function = true;

                var params_list = new ArrayList<Tree>();
                FunctionHandler.addArgsToArrayList(params, params_list);

                for (var param : params_list) {
                    var var_name = param.kids[1].tok.text;
                    declareVar(param.kids[0].tok, var_name, func_scope);
                    func_scope.assigned_vars.add(var_name);
                }

                block.scope = func_scope;
                analyzeBlock(block, func_scope);
                ArrayList<Tree> returns = new ArrayList<>();
                collectAllReturnStmts(block, returns);

                RuntimeValue return_value = null;
                for (var ret : returns){
                    RuntimeValue current_return_v;
                    if (ret.kids.length > 0){
                        current_return_v = ret.kids[0].calculated_value;
                    }else {
                        current_return_v = new RuntimeValue(ValueType.NONE);
                    }
                    if (return_value==null)return_value = current_return_v;
                    else{
                        if (return_value.type!=current_return_v.type)j0.semerror("Incompatible types: the return types must be the same");
                    }
                }
                if (return_value == null)return_value = new RuntimeValue(ValueType.NONE);

                scope.addVar(name, new RuntimeValue(ValueType.FUNCTION, new FunctionType(params_list, block, return_value.type)));
            }
            case "MethodCall" -> {
                checkMethodCall(node, scope);
            }
            case "IfStmt", "IfElseStmt" -> {
                var r = analyzeExpr(node.kids[0], scope);
                checkType(ValueType.BOOL, r.type);

                var if_block = node.kids[1];
                var if_scope = new SymbolTable("if", scope);
                if_block.scope = if_scope;
                analyzeBlock(if_block, if_scope);
                if (node.sym.equals("IfElseStmt")) {
                    var else_block = node.kids[2];
                    var else_scope = new SymbolTable("else", scope);
                    else_block.scope = else_scope;
                    analyzeBlock(else_block, else_scope);

                    // set vars as assigned for if-else
                    for (String el : if_scope.assigned_vars) {
                        if (else_scope.assigned_vars.contains(el)) {
                            scope.assigned_vars.add(el);
                        }
                    }
                }
            }
            case "WhileStmt", "DoWhileStmt" -> {
                var r = analyzeExpr(node.kids[0], scope);
                checkType(ValueType.BOOL, r.type);

                var while_block = node.kids[1];
                var while_scope = new SymbolTable("while", scope);
                while_block.scope = while_scope;
                analyzeBlock(while_block, while_scope);

                // set vars as assigned for do-while
                if (node.sym.equals("DoWhileStmt")) {
                    scope.assigned_vars.addAll(while_scope.assigned_vars);
                }
            }
            case "ForStmt" -> {
                var for_block = node.kids[1];
                var for_scope = new SymbolTable("for", scope);
                var for_header = node.kids[0];
                if (for_header.sym.equals("ForShort")){
                    var r = analyzeExpr(for_header.kids[0], scope);
                    checkType(ValueType.INTEGER, r.type);
                }else{
                    // "ForNormal"
                    var for_var_init = for_header.kids[0];
                    var var_name = for_var_init.kids[0].tok.text;
                    declareVar(new Token(Parser.INT,"",0),var_name, for_scope);
                    if (for_var_init.rule == 1224){
                        var r = analyzeExpr(for_var_init.kids[1], for_scope);
                        checkType(ValueType.INTEGER, r.type);
                    }
                    for_scope.assigned_vars.add(var_name);

                    var r = analyzeExpr(for_header.kids[2], for_scope);
                    checkType(ValueType.INTEGER, r.type);

                    //"ForFull"
                    if (for_header.sym.equals("ForFull")){
                        r = analyzeExpr(for_header.kids[3], for_scope);
                        checkType(ValueType.INTEGER, r.type);
                    }

                }

                for_block.scope = for_scope;
                analyzeBlock(for_block, for_scope);
            }
            case "ReturnStmt" -> {
                if (!scope.is_inside_function) j0.error("return statement outside a function");
                if (node.kids.length != 0) analyzeExpr(node.kids[0], scope);
            }
            case "BlockStmtsOpt", "BlockStmts", "Block" -> {
                for (Tree kid : node.kids) {
                    analyzeBlock(kid, scope);

                }
            }
            default -> {
            }
        }
    }

    public static RuntimeValue analyzeExpr(Tree node, SymbolTable scope) {
        switch (node.sym) {
            case "token" -> {
                Token t = node.tok;
                if (t.code == Parser.IDENTIFIER) {
                    var var_name = t.text;
                    var v = scope.getVar(var_name);
                    if (v == null) {
                        j0.semerror("Unknown name " + var_name);
                    }else if (!scope.isInitialized(var_name)){
                        j0.semerror("The var " + var_name + " can not be initialized");
                    }
                    var r = new RuntimeValue(v.type);
                    node.calculated_value = r;
                    return r;
                } else{
                    var r = PrimitiveHandler.literalToValue(node.tok);
                    node.calculated_value = r;
                    return r;
                }
            }
            case "MethodCall" -> {
                var result = checkMethodCall(node, scope);
                if (result == null || result.type == ValueType.NONE)j0.error("function "+node.kids[0].tok.text+" does not return a value");
                return result;
            }
            case "AddExpr", "MulExpr", "RelExpr", "EqExpr", "CondAndExpr", "CondOrExpr" -> {
                var left = analyzeExpr(node.kids[0], scope);
                var op = node.kids[1].tok.text;
                var right = analyzeExpr(node.kids[2], scope);
                RuntimeValue r;
                if (left.value != null && right.value != null) {
                    r = processOperator(left, op, right);
                }else {
                    r = analyzeOperator(left, op, right);
                }
                node.calculated_value = r;
                return r;
            }

        }
        return new RuntimeValue(ValueType.UNKNOWN);
    }

    public static void declareVar(Token type, String var_name, SymbolTable scope) {
        if (scope.contains(var_name)) {
            j0.semerror("Redeclaration of " + var_name);
            return;
        }
        scope.addVar(var_name, new RuntimeValue(PrimitiveHandler.tokenToType(type)));
    }

    public static boolean checkType(int expected, int provided) {
        if (provided != expected) {
            j0.semerror("Assign type mismatch for");/*todo var name and type names*/
            return false;
        }
        return true;
    }

    public static void collectAllReturnStmts(Tree node, ArrayList<Tree> returns){
        if (node.sym.equals("ReturnStmt")){
            returns.add(node);
            return;
        }
        if (node.kids != null) {
            for (Tree kid : node.kids) {
                collectAllReturnStmts(kid, returns);
            }
        }
    }

    public static RuntimeValue checkMethodCall(Tree node, SymbolTable scope) {
        String name = node.kids[0].tok.text;
        var v = scope.getVar(name);
        if (v == null) {
            j0.semerror("Unknown name "+ name);
        }else {
            if(!checkType(v.type, ValueType.FUNCTION))j0.semerror("Name "+ name+ " is not a function");
            var func = ((FunctionType) v.value);
            if (node.kids.length > 1) {
                var args = node.kids[1];
                var args_list = new ArrayList<Tree>();
                FunctionHandler.addArgsToArrayList(args, args_list);
                for (var expr : args_list) analyzeExpr(expr, scope);
                if (!func.is_build_in) FunctionHandler.checkArgsNumber(name, func.params.size(), args_list.size());
            }
            return new RuntimeValue(func.return_type);
        }
        return null;
    }


    static public void interpret(Tree root) {
        evalBlock(root, root.scope);
    }

    public static RuntimeValue evalBlock(Tree node, SymbolTable scope) {
        if (node == null) return null;
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
            case "ForStmt" -> {
                return processFor(node.kids[0], node.kids[1], scope);
            }
            case "ReturnStmt" -> {
                if (node.kids.length == 0) return new RuntimeValue(ValueType.NONE);
                return evalExpr(node.kids[0], scope);
            }
            case "BlockStmtsOpt", "BlockStmts", "Block" -> {
                for (Tree kid : node.kids) {
                    var result = evalBlock(kid, scope);
                    if (result != null) return result;
                }
            }
            case "LocalVarDecl", "MethodDecl" -> { /* processed on semantic phase */ }
            default -> {
                j0.error("the node is not implemented: " + node.sym + " " + node.tok);
            }
        }
        return null;
    }

    public static RuntimeValue evalExpr(Tree node, SymbolTable scope) {
        switch (node.sym) {
            case "token" -> {
                Token t = node.tok;
                if (t.code == Parser.IDENTIFIER)
                    return scope.getVar(t.text);
                else
                    //PrimitiveHandler.literalToValue(node.tok);
                    return node.calculated_value;
            }
            case "MethodCall" -> {
                return processMethodCall(node, scope);
            }
            case "AddExpr", "MulExpr", "RelExpr", "EqExpr", "CondAndExpr", "CondOrExpr" -> {
                RuntimeValue r = node.calculated_value;
                if (r.value != null) return r;

                var left = evalExpr(node.kids[0], scope);
                var op = node.kids[1].tok.text;
                var right = evalExpr(node.kids[2], scope);
                return processOperator(left, op, right);
            }

        }
        return null;
    }

    static RuntimeValue processIfElse(Tree condition_expr, Tree if_block, Tree else_block, SymbolTable scope) {
        var condition = evalExpr(condition_expr, scope);
        if ((boolean) condition.value) {
            return evalBlock(if_block, new SymbolTable("if_stmt", scope));
        } else if (else_block != null) {
            if (else_block.sym.equals("IfElseStmt")) return evalBlock(else_block, scope);
            else return evalBlock(else_block, new SymbolTable("else_stmt", scope));
        }
        return null;
    }

    static RuntimeValue processWhile(Tree expr, Tree block, SymbolTable scope) {
        var condition = evalExpr(expr, scope);
        while ((boolean) condition.value) {
            var result = evalBlock(block, new SymbolTable("while_stmt", scope));
            if (result != null) return result;
            condition = evalExpr(expr, scope);
        }
        return null;
    }

    static RuntimeValue processDoWhile(Tree expr, Tree block, SymbolTable scope) {
        var r = evalBlock(block, new SymbolTable("do_while_stmt", scope));
        if (r != null) return r;
        var condition = evalExpr(expr, scope);
        while ((boolean) condition.value) {
            var while_scope = new SymbolTable("do_while_stmt", scope);
            r = evalBlock(block, while_scope);
            if (r != null) return r;
            condition = evalExpr(expr, while_scope);
        }
        return null;
    }

    static RuntimeValue processFor(Tree header, Tree block, SymbolTable scope) {
        var base_for_scope = new SymbolTable("base_for_scope", scope);
        if (header.sym.equals("ForShort")){
            int i = 0;
            while (true) {
                var limit = evalExpr(header.kids[0], scope);
                if (i >= (int) limit.value) break;
                var r = evalBlock(block, new SymbolTable("short_for_scope", base_for_scope));
                if (r != null) return r;
                i++;
            }
        } else {
            var var_init = header.kids[0];
            String var_name = var_init.kids[0].tok.text;
            RuntimeValue init_value;
            if (var_init.rule == 1224) init_value = evalExpr(var_init.kids[1], scope);
            else init_value = new RuntimeValue(ValueType.INTEGER, 0);
            base_for_scope.addVar(var_name, init_value);

            var separator = header.kids[1];

            boolean greater_or_equal = !separator.tok.text.equals(":");


            var limit = header.kids[2];
            boolean need_count_step = false;
            Tree step_expr = null;
            if (header.sym.equals("ForFull")) {
                step_expr = header.kids[3];
                need_count_step = true;
            }
            int i_value;
            while (true) {
                i_value = (int) base_for_scope.getVar(var_name).value;
                if (greater_or_equal) {
                    if (!(i_value <= (int) evalExpr(limit, scope).value)) break;
                } else {
                    if (!(i_value < (int) evalExpr(limit, scope).value)) break;
                }
                var r = evalBlock(block, new SymbolTable("for_scope", base_for_scope));
                if (r != null) return r;
                int step;
                if (need_count_step) step = (int) evalExpr(step_expr, scope).value;
                else step = 1;
                base_for_scope.setVar(var_name, PrimitiveHandler.handleInt(i_value, step, "+"));
            }
        }
        return null;
    }

    static RuntimeValue processMethodCall(Tree node, SymbolTable scope) {
        String name = node.kids[0].tok.text;
        Tree args;
        if (node.kids.length > 1) args = node.kids[1];
        else args = null;
        return FunctionHandler.eval(name, args, scope);
    }

}
