package ch5;

public class ValueType {
    public static final int INTEGER = 1;
    public static final int FLOAT = 2;
    public static final int FUNCTION = 3;
    public static final int CLASS = 4;

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
}
