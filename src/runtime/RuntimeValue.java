package runtime;

public class RuntimeValue {
    String name;
    int type;
    Object value = null;

    public RuntimeValue(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public RuntimeValue(String name, String type) {
        this.name = name;
        this.type = ValueType.stringToType(type);
    }


}
