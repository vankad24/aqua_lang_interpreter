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


    public void interpret(Tree root) {
        SymbolTable global_scope = new SymbolTable("global", null);
        evalBlock(root, global_scope);
    }

    public void evalBlock(Tree node, SymbolTable scope) {
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
            case "MethodCall" -> {
                if (node.kids[0].tok.text.equals("println")) {
                    Token arg = node.kids[1].tok;
                    switch (arg.code) {
                        case Parser.IDENTIFIER -> {
                            String var_name = arg.text;

                            System.out.println(scope.getVar(var_name).value);
                        }
                        case Parser.STRINGLIT -> {
                            System.out.println(arg.text);
                        }
                    }
                } else {
                    System.out.println("Unknown function " + node.kids[0].tok.text);
                }
            }
            case "IfStmt" -> {
                processIfElse(node.kids[0], node.kids[1], null, scope);
            }
            case "IfElseStmt" -> {
                processIfElse(node.kids[0], node.kids[1], node.kids[2], scope);
            }
            case "WhileStmt" -> {
                processWhile(node.kids[0], node.kids[1], scope);
            }
            case "DoWhileStmt" -> {
                processDoWhile(node.kids[0], node.kids[1], scope);
            }
            case "BlockStmtsOpt", "BlockStmts", "Block" ->{
                for (Tree kid : node.kids) {
                    evalBlock(kid, scope);
                }
            }
            default -> {
                j0.error("the node is not implemented: "+node.sym+" "+node.tok);
            }
        }
    }

    public RuntimeValue evalExpr(Tree node, SymbolTable scope){
        switch (node.sym){
            case "token"->{
                Token t = node.tok;
                if (t.code == Parser.IDENTIFIER)
                    return scope.getVar(t.text);
                return PrimitiveHandler.literalToValue(node.tok);
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

    void processIfElse(Tree condition_expr, Tree if_block, Tree else_block, SymbolTable scope){
        var condition = evalExpr(condition_expr, scope);
        if (condition.type != ValueType.BOOL) j0.error("not bool expression in the if stmt");
        if ((boolean) condition.value){
            evalBlock(if_block, new SymbolTable("if_stmt", scope));
        }else if (else_block!=null){
            if (else_block.sym.equals("IfElseStmt"))evalBlock(else_block, scope);
            else evalBlock(else_block, new SymbolTable("else_stmt", scope));
        }
    }

    void processWhile(Tree expr, Tree block, SymbolTable scope) {
        var condition = evalExpr(expr, scope);
        if (condition.type != ValueType.BOOL) j0.error("not bool expression in the while stmt");
        while ((boolean) condition.value){
            evalBlock(block, new SymbolTable("while_stmt", scope));
            condition = evalExpr(expr, scope);
        }
    }

    void processDoWhile(Tree block, Tree expr, SymbolTable scope) {
        evalBlock(block, new SymbolTable("do_while_stmt", scope));
        var condition = evalExpr(expr, scope);
        if (condition.type != ValueType.BOOL) j0.error("not bool expression in the while stmt");
        while ((boolean) condition.value){
            evalBlock(block, new SymbolTable("do_while_stmt", scope));
            condition = evalExpr(expr, scope);
        }
    }


}
