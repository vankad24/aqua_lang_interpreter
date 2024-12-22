package runtime;

import frontend.Parser;
import frontend.Token;
import frontend.j0;

public class PrimitiveHandler {

    public static RuntimeValue analyzeOperator(RuntimeValue v1, String op, RuntimeValue v2) {
        int new_type = determineResultType(v1, v2);
        // todo calc return type and add a cast node
        var r = switch (new_type) {
            case ValueType.FLOAT -> processOperator(new RuntimeValue(new_type, 1f),op,new RuntimeValue(new_type, 1f));
            case ValueType.INTEGER -> processOperator(new RuntimeValue(new_type, 1),op,new RuntimeValue(new_type, 1));
            case ValueType.BOOL -> processOperator(new RuntimeValue(new_type, true),op,new RuntimeValue(new_type, true));
            default -> null;
        };
        return new RuntimeValue(r.type);
    }

    public static RuntimeValue processOperator(RuntimeValue v1, String op, RuntimeValue v2) {
        int new_type = determineResultType(v1, v2);
        v1 = castToType(v1, new_type);
        v2 = castToType(v2, new_type);

        return switch (new_type) {
            case ValueType.FLOAT -> handleFloat((float) v1.value, (float) v2.value, op);
            case ValueType.INTEGER -> handleInt((int) v1.value, (int) v2.value, op);
            case ValueType.BOOL -> handleBool((boolean) v1.value, (boolean) v2.value, op);
            default -> null;
        };

    }


    static RuntimeValue castToType(RuntimeValue v, int type) {
        if (v.type == type) return v;
        switch (type) {
            case ValueType.FLOAT -> {
                switch (v.type) {
                    case ValueType.INTEGER -> {
                        return new RuntimeValue(ValueType.FLOAT, (float) (int) v.value);
                    }
                    case ValueType.BOOL -> {
                        return new RuntimeValue(ValueType.FLOAT, (boolean)v.value ? 1f : 0f);
                    }
                }
            }
            case ValueType.INTEGER -> {
                switch (v.type) {
                    case ValueType.FLOAT -> {
                        return new RuntimeValue(ValueType.INTEGER, (int) (float) v.value);
                    }
                    case ValueType.BOOL -> {
                        return new RuntimeValue(ValueType.INTEGER, (boolean)v.value ? 1 : 0);
                    }
                }
            }

        }
        return null;
    }

    private static int determineResultType(RuntimeValue v1, RuntimeValue v2) {
        if (ValueType.getTypePriority(v1.type) > ValueType.getTypePriority(v2.type))
            return v1.type;
        else return v2.type;
    }

    public static RuntimeValue literalToValue(Token t) {
        return switch (t.code) {
            case Parser.DOUBLELIT -> new RuntimeValue(ValueType.FLOAT, Float.parseFloat(t.text));
            case Parser.INTLIT -> new RuntimeValue(ValueType.INTEGER, Integer.parseInt(t.text));
            case Parser.BOOLLIT -> new RuntimeValue(ValueType.BOOL, t.text.equals("true"));
            case Parser.STRINGLIT -> new RuntimeValue(ValueType.STRING, t.text);
            default -> null;
        };
    }

    public static int tokenToType(Token type){
        return switch (type.code){
            case Parser.INT-> ValueType.INTEGER;
            case Parser.FLOAT-> ValueType.FLOAT;
            case Parser.BOOL-> ValueType.BOOL;
            default -> {
                j0.semerror("unknown type "+type);
                yield -1;
            }
        };
    }

    static RuntimeValue handleInt(int v1, int v2, String op) {
        return switch (op) {
            case "+" -> new RuntimeValue(ValueType.INTEGER, v1 + v2);
            case "-" -> new RuntimeValue(ValueType.INTEGER,v1 - v2);
            case "*" -> new RuntimeValue(ValueType.INTEGER,v1 * v2);
            case "/" -> new RuntimeValue(ValueType.INTEGER,v1 / v2);
            case "%" -> new RuntimeValue(ValueType.INTEGER,v1 % v2);
            case ">" -> new RuntimeValue(ValueType.BOOL,v1 > v2);
            case "<" -> new RuntimeValue(ValueType.BOOL,v1 < v2);
            case "<=" -> new RuntimeValue(ValueType.BOOL,v1 <= v2);
            case ">=" -> new RuntimeValue(ValueType.BOOL,v1 >= v2);
            case "==" -> new RuntimeValue(ValueType.BOOL,v1 == v2);
            case "!=" -> new RuntimeValue(ValueType.BOOL,v1 != v2);
            default -> {
                throw new IllegalArgumentException("Unsupported operator: " + op);
            }
        };
    }

    static RuntimeValue handleFloat(float v1, float v2, String op) {
        return switch (op) {
            case "+" -> new RuntimeValue(ValueType.FLOAT, v1 + v2);
            case "-" -> new RuntimeValue(ValueType.FLOAT,v1 - v2);
            case "*" -> new RuntimeValue(ValueType.FLOAT,v1 * v2);
            case "/" -> new RuntimeValue(ValueType.FLOAT,v1 / v2);
            case ">" -> new RuntimeValue(ValueType.BOOL,v1 > v2);
            case "<" -> new RuntimeValue(ValueType.BOOL,v1 < v2);
            case "<=" -> new RuntimeValue(ValueType.BOOL,v1 <= v2);
            case ">=" -> new RuntimeValue(ValueType.BOOL,v1 >= v2);
            case "==" -> new RuntimeValue(ValueType.BOOL,v1 == v2);
            case "!=" -> new RuntimeValue(ValueType.BOOL,v1 != v2);
            default -> {
                throw new IllegalArgumentException("Unsupported operator: " + op);
            }
        };
    }

    private static RuntimeValue handleBool(boolean v1, boolean v2, String op) {
        return switch (op) {
            case "+" -> {
                int r = 0;
                if (v1)r++;
                if (v2)r++;
                yield new RuntimeValue(ValueType.INTEGER, r);
            }
            case "*" -> new RuntimeValue(ValueType.INTEGER,v1 && v2 ? 1 : 0);
            case "&&" -> new RuntimeValue(ValueType.BOOL,v1 && v2);
            case "||" -> new RuntimeValue(ValueType.BOOL,v1 || v2);
            case "==" -> new RuntimeValue(ValueType.BOOL,v1 == v2);
            case "!=" -> new RuntimeValue(ValueType.BOOL,v1 != v2);
            default -> {
                throw new IllegalArgumentException("Unsupported operator: " + op);
            }
        };
    }
}
