package runtime;

import frontend.Tree;
import frontend.j0;

import java.util.ArrayList;

public class FunctionHandler {

    public static void eval(String name, Tree args, SymbolTable scope){
        var runtimeValue = scope.getVar(name);
        if (runtimeValue.type != ValueType.FUNCTION) j0.error(name+ " is not a function");

        var func = (FunctionType) runtimeValue.value;

        if (func.is_build_in)evalBuildin(name, args, scope);
        else {
            var args_list = new ArrayList<Tree>();
            addArgsToArrayList(args, args_list);

            var params_list = new ArrayList<Tree>();
            addArgsToArrayList(func.params, params_list);

            if (params_list.size()!=args_list.size())j0.error("function "+name+": expected "+params_list.size()+" arguments, got "+args_list.size());

            var local_scope = new SymbolTable("function", scope);
            for (int i = 0; i <args_list.size(); i++) {
                var varInit = params_list.get(i);
                var type = varInit.kids[0].tok;
                var var_name = varInit.kids[1].tok.text;
                var value = Interpreter.evalExpr(args_list.get(i), scope);
                local_scope.addVar(var_name, new RuntimeValue(PrimitiveHandler.tokenToType(type)));
                local_scope.setVar(var_name, value);
            }
            Interpreter.evalBlock(func.block, local_scope);
        }
    }

    public static void evalBuildin(String name, Tree args, SymbolTable scope){
        var args_list = new ArrayList<Tree>();
        addArgsToArrayList(args, args_list);

        switch (name){
            case "print","println"->{
                for (int i = 0; i <args_list.size(); i++) {
                    var runtimeValue = Interpreter.evalExpr(args_list.get(i), scope);
                    System.out.print(runtimeValue.value);
                    if (i != args_list.size()-1) System.out.print(" ");
                }

                if (name.equals("println")) System.out.println();
            }
            default -> {
                j0.error(name + " not yet implemented");
            }
        }
    }

    static void addArgsToArrayList(Tree arg, ArrayList<Tree> elements){
        if (arg!=null) {
            if (arg.sym.equals("ArgList") || arg.sym.equals("FormalParmList")) {
                for (var node : arg.kids) addArgsToArrayList(node, elements);
            } else elements.add(arg);
        }
    }

}
