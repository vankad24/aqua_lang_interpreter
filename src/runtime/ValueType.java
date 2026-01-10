package runtime;

import frontend.j0;

public class ValueType {
    public static final int UNKNOWN = -1;
    public static final int INTEGER = 1;
    public static final int FLOAT = 2;
    public static final int FUNCTION = 3;
    public static final int CLASS = 4;
    public static final int BOOL = 5;
    public static final int STRING = 6;
    public static final int NONE = 7;
    public static final int INTARRAY = 8;

    static int[] types_priority = new int[]{BOOL, INTEGER, FLOAT, UNKNOWN};

    public static int getTypePriority(int t){
        for (int i = 0; i < types_priority.length; i++) {
            if (types_priority[i]==t)return i;
        }
        return -1;
    }

    public static String getName(int type){
        return switch (type){
            case UNKNOWN -> "Unknown";
            case INTEGER -> "Integer";
            case FLOAT -> "Float";
            case FUNCTION -> "Function";
            case CLASS -> "Class";
            case BOOL -> "Boolean";
            case STRING -> "String";
            case NONE -> "None";
            case INTARRAY -> "Intarray";
            default -> "Undefined";
        };
    }
}
