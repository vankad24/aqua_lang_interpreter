package runtime;

import frontend.Parser;
import frontend.Token;
import frontend.Tree;


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
                return literalToValue(node.tok, scope);
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

    public RuntimeValue processOperator(RuntimeValue v1, String op, RuntimeValue v2) {
        int resultType = determineResultType(v1, v2);
        v1 = castToType(v1, resultType);
        v2 = castToType(v2, resultType);

        switch(op) {
            case "+":
                return performAddition(v1, v2, resultType);
            case "-":
                return performSubtraction(v1, v2, resultType);
            case "*":
                return performMultiplication(v1, v2, resultType);
            case "/":
                return performDivision(v1, v2, resultType);
            default:
                throw new IllegalArgumentException("Unsupported operator: " + op);
        }
    }

    RuntimeValue castToType(RuntimeValue v, int type){
        if (v.type == type)return v;
        switch (type) {
            case ValueType.FLOAT ->{
                switch (v.type) {
                    case ValueType.INTEGER -> {
                        return new RuntimeValue(ValueType.FLOAT, (float) (int) v.value);
                    }
                }
            }
            case ValueType.INTEGER ->{
                switch (v.type) {
                    case ValueType.FLOAT -> {
                        return new RuntimeValue(ValueType.INTEGER, (int) (float) v.value);
                    }
                }
            }

        }
        return null;
    }

    private int determineResultType(RuntimeValue v1, RuntimeValue v2) {
        if (ValueType.getTypePriority(v1.type)>ValueType.getTypePriority(v2.type))
            return v1.type;
        else return v2.type;
    }

    private RuntimeValue performAddition(RuntimeValue v1, RuntimeValue v2, int type) {
        switch (type) {
            case ValueType.FLOAT:
                return new RuntimeValue(ValueType.FLOAT, (float)v1.value + (float)v2.value);
            case ValueType.INTEGER:
                return new RuntimeValue(ValueType.INTEGER, (int)v1.value + (int)v2.value);
            default:
                throw new IllegalArgumentException("Unsupported value type: " + type);
        }
    }

    private RuntimeValue performSubtraction(RuntimeValue v1, RuntimeValue v2, int type) {
        switch (type) {
            case ValueType.FLOAT:
                return new RuntimeValue(ValueType.FLOAT, (float)v1.value - (float)v2.value);
            case ValueType.INTEGER:
                return new RuntimeValue(ValueType.INTEGER, (int)v1.value - (int)v2.value);
            default:
                throw new IllegalArgumentException("Unsupported value type: " + type);
        }
    }

    private RuntimeValue performMultiplication(RuntimeValue v1, RuntimeValue v2, int type) {
        switch (type) {
            case ValueType.FLOAT:
                return new RuntimeValue(ValueType.FLOAT, (float)v1.value * (float)v2.value);
            case ValueType.INTEGER:
                return new RuntimeValue(ValueType.INTEGER, (int)v1.value * (int)v2.value);
            default:
                throw new IllegalArgumentException("Unsupported value type: " + type);
        }
    }

    private RuntimeValue performDivision(RuntimeValue v1, RuntimeValue v2, int type) {
        switch (type) {
            case ValueType.FLOAT:
                return new RuntimeValue(ValueType.FLOAT, (float)v1.value / (float)v2.value);
            case ValueType.INTEGER:
                return new RuntimeValue(ValueType.INTEGER, (int)v1.value / (int)v2.value);
            default:
                throw new IllegalArgumentException("Unsupported value type: " + type);
        }
    }

    RuntimeValue literalToValue(Token t, SymbolTable scope){
        if (t.code == Parser.IDENTIFIER)
            return scope.getVar(t.text);
        return switch (t.code){
            case Parser.DOUBLELIT -> new RuntimeValue(ValueType.FLOAT, Float.parseFloat(t.text));
            case Parser.INTLIT -> new RuntimeValue(ValueType.INTEGER, Integer.parseInt(t.text));
            default -> null;
        };
    }

}
