package runtime;

import frontend.j0;

public class ValueType {
    public static final int INTEGER = 1;
    public static final int FLOAT = 2;
    public static final int FUNCTION = 3;
    public static final int CLASS = 4;

    static int[] types_priority = new int[]{INTEGER, FLOAT};

    public static int stringToType(String type){
        return switch (type){
            case "int"-> INTEGER;
            case "float"-> FLOAT;
            default -> {
                j0.semerror("unknown type "+type);
                yield -1;
            }
        };
    }

    public static int getTypePriority(int t){
        for (int i = 0; i < types_priority.length; i++) {
            if (types_priority[i]==t)return i;
        }
        return -1;
    }
}
