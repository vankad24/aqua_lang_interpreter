package runtime;

import java.util.HashMap;

public class SymbolHandler {
    HashMap<String, Float> floatVars = new HashMap<>();
    HashMap<String, Integer> intVars = new HashMap<>();

    public void add(RuntimeValue symbol) {
        switch (symbol.type){
            case ValueType.FLOAT -> floatVars.put(symbol.name, 0.0f);
            case ValueType.INTEGER -> intVars.put(symbol.name, 0);
        }
    }

    public void set(RuntimeValue symbol, String value) {
        switch (symbol.type){
            case ValueType.FLOAT -> floatVars.put(symbol.name, Float.parseFloat(value));
            case ValueType.INTEGER -> intVars.put(symbol.name, Integer.parseInt(value));
        }
    }

    public Object get(RuntimeValue symbol) {
        return switch (symbol.type){
            case ValueType.FLOAT -> floatVars.get(symbol.name);
            case ValueType.INTEGER -> intVars.get(symbol.name);
            default -> null;
        };
    }
}
