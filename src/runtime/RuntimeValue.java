package runtime;

public class RuntimeValue {
    public int type;
    public Object value = null;

    public RuntimeValue(int type, Object value) {
        this.type = type;
        this.value = value;
    }

    public RuntimeValue(int type) {
        this.type = type;
    }

}
