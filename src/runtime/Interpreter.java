package runtime;

import frontend.Parser;
import frontend.Token;
import frontend.Tree;


public class Interpreter {
    SymbolTable global_scope = new SymbolTable("global", null);

    public void semantic(Tree root) {
        System.out.println("semantic");
    }


    public void interpret(Tree node) {
        switch (node.sym) {
            case "LocalVarDecl" -> {
                String type = node.kids[0].tok.text;
                String var_name = node.kids[1].tok.text;

                RuntimeValue runtimeValue = new RuntimeValue(var_name, type);
                global_scope.addVar(var_name, runtimeValue);

            }
            case "Assignment" -> {
                String var_name = node.kids[0].tok.text;
                Tree right = node.kids[2];

                if (right.sym.equals("token")){
                    String val = node.kids[2].tok.text;
                    global_scope.setVar(var_name, val);

                }else if (right.sym.equals("AddExpr")){
                    //todo
                }


            }
            case "MethodCall" -> {
                if (node.kids[0].tok.text.equals("println")) {
                    Token arg = node.kids[1].tok;
                    switch (arg.code) {
                        case Parser.IDENTIFIER -> {
                            String var_name = arg.text;

                            System.out.println(global_scope.getVar(var_name).value);
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
                    interpret(kid);
                }
            }
        }
    }

    public Object evalExpr(Tree node){
        return null;
    }
}
