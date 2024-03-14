package runtime;

import frontend.Parser;
import frontend.Token;
import frontend.Tree;

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
                String type = node.kids[0].tok.text;
                String var_name = node.kids[1].tok.text;
                scope.addVar(var_name, new RuntimeValue(type));

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
            default -> {
                for (Tree kid : node.kids) {
                    evalBlock(kid, scope);
                }
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
            case "AddExpr", "MulExpr"->{
                var left = evalExpr(node.kids[0], scope);
                var op = node.kids[1].tok.text;
                var right = evalExpr(node.kids[2], scope);
                return processOperator(left, op, right);
            }

        }
        return null;
    }


}
