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


    public void setVar(String name, RuntimeValue value){
        var current_value = variables.get(name);
        if (current_value!=null){
            if ( current_value.type!=value.type)
                j0.semerror("Assign type mismatch for "+ name);
            variables.put(name, value);
        } else if (parent == null) j0.semerror("Unknown name "+ name);
        else parent.setVar(name, value);
    }


}


