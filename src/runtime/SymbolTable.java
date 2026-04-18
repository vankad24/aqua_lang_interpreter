package runtime;

import frontend.j0;

import java.util.HashMap;
import java.util.HashSet;

public class SymbolTable {
    String name;
    SymbolTable parent;
    public HashMap<String, RuntimeValue> variables;
    HashSet<String> assigned_vars;
    boolean is_inside_function;

    public SymbolTable(String name){
        this.name = name;
        this.parent = null;
        variables = new HashMap<>();
        assigned_vars = new HashSet<>();
        is_inside_function = false;
    }
    public SymbolTable(String name, SymbolTable parent){
        this.name = name;
        this.parent = parent;
        variables = new HashMap<>();
        assigned_vars = new HashSet<>();
        is_inside_function = parent.is_inside_function;
    }

    public RuntimeValue getVar(String name){
        if (variables.containsKey(name)) return variables.get(name);
        if (parent==null) return null;
        return parent.getVar(name);
    }

    public boolean contains(String name){
        return variables.containsKey(name);
    }
    public void addVar(String name, RuntimeValue runtimeValue) {
        if (variables.containsKey(name))
            ErrorHandler.interpreterError("Redeclaration of name" + name);
        variables.put(name, runtimeValue);
    }

    public void setVar(String name, RuntimeValue value){
        var current_value = variables.get(name);
        if (current_value!=null){
            if ( current_value.type!=value.type)
                ErrorHandler.interpreterError("Assign type mismatch for name: "+ name);
            variables.put(name, value);
        } else if (parent != null) parent.setVar(name, value);
        else ErrorHandler.interpreterError("Unknown name: "+ name);
    }

    boolean isInitialized(String var_name){
        if (assigned_vars.contains(var_name))return true;
        else {
            if (parent == null)return false;
            else return parent.isInitialized(var_name);
        }
    }

}


