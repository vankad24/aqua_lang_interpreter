package runtime;

import frontend.j0;

import java.util.HashMap;
import java.util.HashSet;

public class SymbolTable {
    String name;
    SymbolTable parent;
    HashMap<String, RuntimeValue> variables;
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
            j0.semerror("Interpreter error: Redeclaration of " + name);
        variables.put(name, runtimeValue);
    }

    public void setVar(String name, RuntimeValue value){
        var current_value = variables.get(name);
        if (current_value!=null){
            if ( current_value.type!=value.type)
                j0.semerror("Interpreter error: Assign type mismatch for "+ name);
            variables.put(name, value);
        } else if (parent != null) parent.setVar(name, value);
        else j0.semerror("Interpreter error: Unknown name "+ name);
    }

    boolean isInitialized(String var_name){
        if (assigned_vars.contains(var_name))return true;
        else {
            if (parent == null)return false;
            else return parent.isInitialized(var_name);
        }
    }

}


