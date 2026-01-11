package runtime;

import frontend.Tree;
import frontend.j0;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FunctionHandler {
    static Scanner sc = new Scanner(System.in);

     static Object[][] built_in_functions = {{"print", ValueType.NONE}, {"println", ValueType.NONE}, {"readInt",ValueType.INTEGER}, {"readFloat",ValueType.FLOAT }, {"newIntArray",ValueType.INTARRAY }, {"getElement",ValueType.INTEGER }, {"setElement",ValueType.NONE }, {"readCharsAsIntArray",ValueType.INTARRAY }, {"printChar", ValueType.NONE}, {"getLen", ValueType.INTEGER}, {"readChar", ValueType.INTEGER}, {"getArg", ValueType.INTARRAY }, {"readFile", ValueType.INTARRAY },};

    public static void initBuiltin(SymbolTable scope){
        for (var pair : built_in_functions) {
            scope.addVar(((String) pair[0]), new RuntimeValue(ValueType.FUNCTION, new FunctionType((int) pair[1])));
        }
    }

    public static RuntimeValue eval(String name, Tree args, SymbolTable scope){
        var func = (FunctionType) scope.getVar(name).value;

        if (func.is_build_in)return evalBuiltin(name, args, scope);
        else {
            var args_list = new ArrayList<Tree>();
            addArgsToArrayList(args, args_list);

            var func_scope = new SymbolTable("function", scope);
            func_scope.is_inside_function = true;
            for (int i = 0; i <args_list.size(); i++) {
                var varInit = func.params.get(i);
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
                    if (runtimeValue.type == ValueType.INTARRAY){
                        System.out.print(Arrays.toString((int[])runtimeValue.value));
                    }else System.out.print(runtimeValue.value);
                    if (i != args_list.size()-1) System.out.print(" ");
                }

                if (name.equals("println")) System.out.println();
            }
            case "printChar" ->{
                checkArgsNumber(name, 1, args_list.size());
                var runtimeValue = Interpreter.evalExpr(args_list.get(0), scope);
                checkArgsType(name, ValueType.INTEGER, runtimeValue.type);
                System.out.print((char)(int)runtimeValue.value);
            }
            case "readInt"->{
                checkArgsNumber(name, 0, args_list.size());
                return new RuntimeValue(ValueType.INTEGER, sc.nextInt());
            }
            case "readFloat"->{
                checkArgsNumber(name, 0, args_list.size());
                return new RuntimeValue(ValueType.FLOAT, sc.nextFloat());
            }
            case "newIntArray"->{
                checkArgsNumber(name, 1, args_list.size());
                var runtimeValue = Interpreter.evalExpr(args_list.get(0), scope);
                checkArgsType(name, ValueType.INTEGER, runtimeValue.type);
                int len = (int)runtimeValue.value;
                return new RuntimeValue(ValueType.INTARRAY, new int[len]);
            }
            case "readCharsAsIntArray"->{
                checkArgsNumber(name, 0, args_list.size());
                String s = sc.nextLine();
                while (sc.hasNextLine()){
                    s+=sc.nextLine();
                }
                int[] arr = new int[s.length()];
                for (int i = 0; i < s.length(); i++) {
                    arr[i] = s.charAt(i);
                }
                return new RuntimeValue(ValueType.INTARRAY, arr);
            }
            case "readChar"->{
                checkArgsNumber(name, 0, args_list.size());
                return new RuntimeValue(ValueType.INTEGER, (int)sc.nextLine().charAt(0));
            }
            case "getElement" -> {
                checkArgsNumber(name, 2, args_list.size());
                var runtimeValue1 = Interpreter.evalExpr(args_list.get(0), scope);
                checkArgsType(name, ValueType.INTARRAY, runtimeValue1.type);
                var runtimeValue2 = Interpreter.evalExpr(args_list.get(1), scope);
                checkArgsType(name, ValueType.INTEGER, runtimeValue2.type);

                var arr = (int[])runtimeValue1.value;
                var index = (int)runtimeValue2.value;
                return new RuntimeValue(ValueType.INTEGER, arr[index]);
            }
            case "setElement" -> {
                checkArgsNumber(name, 3, args_list.size());
                var runtimeValue1 = Interpreter.evalExpr(args_list.get(0), scope);
                checkArgsType(name, ValueType.INTARRAY, runtimeValue1.type);
                var runtimeValue2 = Interpreter.evalExpr(args_list.get(1), scope);
                checkArgsType(name, ValueType.INTEGER, runtimeValue2.type);
                var runtimeValue3 = Interpreter.evalExpr(args_list.get(2), scope);
                checkArgsType(name, ValueType.INTEGER, runtimeValue3.type);

                var arr = (int[])runtimeValue1.value;
                var index = (int)runtimeValue2.value;
                var value = (int)runtimeValue3.value;
                arr[index] = value;
            }
            case "getLen" -> {
                checkArgsNumber(name, 1, args_list.size());
                var runtimeValue1 = Interpreter.evalExpr(args_list.get(0), scope);
                checkArgsType(name, ValueType.INTARRAY, runtimeValue1.type);
                var arr = (int[])runtimeValue1.value;
                return new RuntimeValue(ValueType.INTEGER, arr.length);
            }
            case "getArg" -> {
                checkArgsNumber(name, 1, args_list.size());
                var runtimeValue1 = Interpreter.evalExpr(args_list.get(0), scope);
                checkArgsType(name, ValueType.INTEGER, runtimeValue1.type);
                int index = (int)runtimeValue1.value;
                if (index<0)index = j0.args.length+index;
                String s = j0.args[index];

                int[] arr = new int[s.length()];
                for (int i = 0; i < s.length(); i++) {
                    arr[i] = s.charAt(i);
                }
                return new RuntimeValue(ValueType.INTARRAY, arr);
            }
            case "readFile" -> {
                checkArgsNumber(name, 1, args_list.size());
                var runtimeValue1 = Interpreter.evalExpr(args_list.get(0), scope);
                checkArgsType(name, ValueType.INTARRAY, runtimeValue1.type);
                String path = "";
                var arr = (int[])runtimeValue1.value;

                for (int i = 0; i < arr.length; i++) {
                    path+= (char)arr[i];
                }
                String s="";
                try {
                    s = readFileToString(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                arr = new int[s.length()];
                for (int i = 0; i < s.length(); i++) {
                    arr[i] = s.charAt(i);
                }
                return new RuntimeValue(ValueType.INTARRAY, arr);
            }
            default -> {
                ErrorHandler.notImplementedError("build in function "+name);
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
        if (params!=args)ErrorHandler.print("Function "+fun_name+" takes "+params+" arguments, but "+args+" was given");
    }

    static void checkArgsType(String fun_name, int expected, int got){
        if (expected!=got)ErrorHandler.print("Function "+fun_name+" expected "+ValueType.getName(expected)+" type, but "+ValueType.getName(got)+" was given");
    }

    public static String readFileToString(String filePath) throws IOException {
        return Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
    }

}
