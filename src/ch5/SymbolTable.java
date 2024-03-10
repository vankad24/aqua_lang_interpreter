package ch5;

import java.util.HashMap;

public class SymbolTable {
    String name;
    SymbolTable parent;
    HashMap<String, RuntimeValue> entries;

    SymbolTable(String name, SymbolTable parent){
        this.name = name;
        this.parent = parent;
        entries = new HashMap<>();
    }

    RuntimeValue get(String name){
        RuntimeValue entry = entries.get(name);
        if (entry != null)return entry;
        if (parent != null) return parent.get(name);
        j0.semerror("unknown symbol "+ name);
        return null;
    }

    void add(String name, RuntimeValue entry) {
        if (entries.containsKey(name)) {
            j0.semerror("redeclaration of " + name);
        } else {
            entries.put(name, entry);
        }
    }

}


