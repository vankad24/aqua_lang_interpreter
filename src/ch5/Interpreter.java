package ch5;

import java.util.HashMap;

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

                SymbolEntry entry = new SymbolEntry(var_name, type);
                global_scope.add(var_name, entry);
                symbolHandler.add(entry);
            }
            case "Assignment" -> {
                String var_name = node.kids[0].tok.text;
                String val = node.kids[2].tok.text;

                SymbolEntry symbolEntry = global_scope.get(var_name);
                symbolHandler.set(symbolEntry, val);
            }
            case "MethodCall" -> {
                if (node.kids[0].tok.text.equals("println")) {
                    Token arg = node.kids[1].tok;
                    switch (arg.code) {
                        case Parser.IDENTIFIER -> {
                            String var_name = arg.text;
                            SymbolEntry symbolEntry = global_scope.get(var_name);
                            System.out.println(symbolHandler.get(symbolEntry));
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
