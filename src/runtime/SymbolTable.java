package runtime;

import frontend.j0;

import java.util.HashMap;

public class SymbolTable {
    String name;
    SymbolTable parent;
    HashMap<String, RuntimeValue> variables;

    public SymbolTable(String name, SymbolTable parent){
        this.name = name;
        this.parent = parent;
        variables = new HashMap<>();
    }

    public RuntimeValue getVar(String name){
        RuntimeValue runtimeValue = variables.get(name);
        if (runtimeValue != null){
            if (runtimeValue.value == null) j0.semerror("The variable "+ name+" is not defined");
            return runtimeValue;
        }
        if (parent == null) j0.semerror("Unknown name "+ name);
        return parent.getVar(name);
    }

    public void addVar(String name, RuntimeValue runtimeValue) {
        if (variables.containsKey(name))
            j0.semerror("Redeclaration of " + name);
        variables.put(name, runtimeValue);
    }

    public void setVar(String name, Object value){
        RuntimeValue runtimeValue = variables.get(name);
        if (runtimeValue != null){
            runtimeValue.value = value;
        }
        else if (parent == null) j0.semerror("Unknown name "+ name);
        else parent.setVar(name, value);
    }

}


