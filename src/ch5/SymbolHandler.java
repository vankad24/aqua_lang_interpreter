package ch5;

import java.util.HashMap;

public class SymbolHandler {
    HashMap<String, Float> floatVars = new HashMap<>();
    HashMap<String, Integer> intVars = new HashMap<>();

    public void add(SymbolEntry symbol) {
        switch (symbol.type){
            case SymbolType.FLOAT -> floatVars.put(symbol.name, 0.0f);
            case SymbolType.INTEGER -> intVars.put(symbol.name, 0);
        }
    }

    public void set(SymbolEntry symbol, String value) {
        switch (symbol.type){
            case SymbolType.FLOAT -> floatVars.put(symbol.name, Float.parseFloat(value));
            case SymbolType.INTEGER -> intVars.put(symbol.name, Integer.parseInt(value));
        }
    }

    public Object get(SymbolEntry symbol) {
        return switch (symbol.type){
            case SymbolType.FLOAT -> floatVars.get(symbol.name);
            case SymbolType.INTEGER -> intVars.get(symbol.name);
            default -> null;
        };
    }
}
