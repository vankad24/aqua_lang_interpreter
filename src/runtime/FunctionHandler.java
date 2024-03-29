package runtime;

import frontend.Tree;
import frontend.j0;

import java.util.ArrayList;
import java.util.Scanner;

public class FunctionHandler {
    static Scanner sc = new Scanner(System.in);

    static Object[][] built_in_functions = {{"print", ValueType.NONE}, {"println", ValueType.NONE}, {"readInt",ValueType.INTEGER}, {"readFloat",ValueType.FLOAT } };

    public static void initBuiltin(SymbolTable scope){
        for (var pair : built_in_functions) {
            scope.addVar(((String) pair[0]), new RuntimeValue(ValueType.FUNCTION, new FunctionType((int) pair[1])));
        }
    }

    public static RuntimeValue eval(String name, Tree args, SymbolTable scope){
        var runtimeValue = scope.getVar(name);
        if (runtimeValue.type != ValueType.FUNCTION) j0.error(name+ " is not a function");

        var func = (FunctionType) runtimeValue.value;

        if (func.is_build_in)return evalBuiltin(name, args, scope);
        else {
            var args_list = new ArrayList<Tree>();
            addArgsToArrayList(args, args_list);

            var params_list = new ArrayList<Tree>();
            addArgsToArrayList(func.params, params_list);

            checkArgsNumber(name, params_list.size(), args_list.size());

            var func_scope = new SymbolTable("function", scope);
            func_scope.is_inside_function = true;
            for (int i = 0; i <args_list.size(); i++) {
                var varInit = params_list.get(i);
                var type = varInit.kids[0].tok;
                var var_name = varInit.kids[1].tok.text;
                var value = Interpreter.evalExpr(args_list.get(i), scope);
                func_scope.addVar(var_name, new RuntimeValue(PrimitiveHandler.tokenToType(type)));
                func_scope.setVar(var_name, value);
            }
            return Interpreter.evalBlock(func.block, func_scope);
        }
    }

    public static RuntimeValue evalBuiltin(String name, Tree args, SymbolTable scope){
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
            case "readInt"->{
                checkArgsNumber(name, 0, args_list.size());
                return new RuntimeValue(ValueType.INTEGER, sc.nextInt());
            }
            case "readFloat"->{
                checkArgsNumber(name, 0, args_list.size());
                return new RuntimeValue(ValueType.FLOAT, sc.nextFloat());
            }
            default -> {
                j0.error(name + " not yet implemented");
            }
        }
        return null;
    }

    static void addArgsToArrayList(Tree arg, ArrayList<Tree> elements){
        if (arg!=null) {
            if (arg.sym.equals("ArgList") || arg.sym.equals("FormalParmList")) {
                for (var node : arg.kids) addArgsToArrayList(node, elements);
            } else elements.add(arg);
        }
    }

    static void checkArgsNumber(String fun_name, int params, int args){
        if (params!=args)j0.error("function "+fun_name+": expected "+params+" arguments, but got "+args);
    }

}
