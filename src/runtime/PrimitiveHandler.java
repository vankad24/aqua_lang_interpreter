package runtime;

import frontend.Parser;
import frontend.Token;

public class PrimitiveHandler {

    public static RuntimeValue processOperator(RuntimeValue v1, String op, RuntimeValue v2) {
        int result_type = determineResultType(v1, v2);
        v1 = castToType(v1, result_type);
        v2 = castToType(v2, result_type);

        return switch (result_type) {
            case ValueType.FLOAT -> handleFloat((float) v1.value, (float) v2.value, op);
            case ValueType.INTEGER -> handleInt((int) v1.value, (int) v2.value, op);
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
                }
            }
            case ValueType.INTEGER -> {
                switch (v.type) {
                    case ValueType.FLOAT -> {
                        return new RuntimeValue(ValueType.INTEGER, (int) (float) v.value);
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
            default -> null;
        };
    }

    static RuntimeValue handleInt(int v1, int v2, String op) {
        var result = switch (op) {
            case "+" -> v1 + v2;
            case "-" -> v1 - v2;
            case "*" -> v1 * v2;
            case "/" -> v1 / v2;
            case "%" -> v1 % v2;
            default -> {
                throw new IllegalArgumentException("Unsupported operator: " + op);
            }
        };
        return new RuntimeValue(ValueType.INTEGER, result);
    }

    static RuntimeValue handleFloat(float v1, float v2, String op) {
        var result = switch (op) {
            case "+" -> v1 + v2;
            case "-" -> v1 - v2;
            case "*" -> v1 * v2;
            case "/" -> v1 / v2;
            default -> {
                throw new IllegalArgumentException("Unsupported operator: " + op);
            }
        };
        return new RuntimeValue(ValueType.FLOAT, result);
    }
}
