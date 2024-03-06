package ch5;

import java.util.HashMap;

public class Interpreter {
    HashMap<String, Float> floatVars = new HashMap<>();
    HashMap<String, Integer> intVars = new HashMap<>();

    void semantic(tree root) {
        System.out.println("semantic");
    }


    void interpret(tree node) {

        switch (node.sym) {
            case "LocalVarDecl" -> {
                String type = node.kids[0].tok.text;
                String var_name = node.kids[1].tok.text;
                if (type.equals("float")) {
                    floatVars.put(var_name, 0.0F);
                } else if (type.equals("int")) {
                    intVars.put(var_name, 0);
                }
            }
            case "Assignment" -> {
                String var_name = node.kids[0].tok.text;
                String val = node.kids[2].tok.text;
                if (floatVars.containsKey(var_name)) {
                    floatVars.put(var_name, Float.parseFloat(val));
                } else if (intVars.containsKey(var_name)) {
                    intVars.put(var_name, Integer.parseInt(val));
                }
            }
            case "MethodCall" -> {
                if (node.kids[0].tok.text.equals("println")) {
                    token arg = node.kids[1].tok;
                    switch (arg.cat) {
                        case parser.IDENTIFIER -> {
                            String var_name = arg.text;
                            if (floatVars.containsKey(var_name)) {
                                System.out.println(floatVars.get(var_name));
                            } else if (intVars.containsKey(var_name)) {
                                System.out.println(intVars.get(var_name));
                            }
                        }
                        case parser.STRINGLIT -> {
                            System.out.println(arg.text);
                        }
                    }
                } else {
                    System.out.println("unknown function " + node.kids[0].tok.text);
                }
            }
            default -> {
                for (tree kid : node.kids) {
                    interpret(kid);
                }
            }
        }
    }
}
