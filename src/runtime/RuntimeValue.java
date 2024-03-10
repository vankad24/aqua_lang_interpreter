package runtime;

public class RuntimeValue {
    int type;
    Object value = null;

    public RuntimeValue(int type, Object value) {
        this.type = type;
        this.value = value;
    }

    public RuntimeValue(String type) {
        this.type = ValueType.stringToType(type);
    }


}
