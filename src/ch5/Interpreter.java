package ch5;

public class Interpreter {

    SymbolTable global_scope = new SymbolTable("global", null);
    SymbolHandler symbolHandler = new SymbolHandler();

    void semantic(Tree root) {
        System.out.println("semantic");
    }


    void interpret(Tree node) {

        switch (node.sym) {
            case "LocalVarDecl" -> {
                String type = node.kids[0].tok.text;
                String var_name = node.kids[1].tok.text;

                RuntimeValue entry = new RuntimeValue(var_name, type);
                global_scope.add(var_name, entry);
                symbolHandler.add(entry);
            }
            case "Assignment" -> {
                String var_name = node.kids[0].tok.text;
                Tree right = node.kids[2];

                if (right.sym.equals("token")){
                    String val = node.kids[2].tok.text;
                    RuntimeValue runtimeValue = global_scope.get(var_name);
                    symbolHandler.set(runtimeValue, val);
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
                            RuntimeValue runtimeValue = global_scope.get(var_name);
                            System.out.println(symbolHandler.get(runtimeValue));
                        }
                        case Parser.STRINGLIT -> {
                            System.out.println(arg.text);
                        }
                    }
                } else {
                    System.out.println("unknown function " + node.kids[0].tok.text);
                }
            }
            default -> {
                for (Tree kid : node.kids) {
                    interpret(kid);
                }
            }
        }
    }
}
