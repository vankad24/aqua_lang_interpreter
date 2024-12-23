package runtime;

import frontend.Token;
import frontend.j0;

public class ErrorHandler {
    public static void print(String msg){
        System.err.println(msg);
        System.exit(1);
    }

    public static void error(Token t, String msg){
        System.err.println("File "+j0.yyfilename+":"+t.lineno);
        System.err.println("\t"+t.text);
        System.err.println(msg);
        System.exit(1);
    }

    // The error appears only if there is a bug in the interpreter
    public static void interpreterError(String msg){
        print("Interpreter error: "+msg);
    }

    public static void notImplementedError(String msg){
        print("Not yet implemented: "+msg);
    }

    public static void syntaxError(Token t, String msg){
        error(t, "Syntax error: "+msg);
    }

    public static void typeError(Token t, String msg){
        error(t, "Type error: "+msg);
    }

    public static void redeclaration(Token t, String name){
        error(t, "Name error: name "+name+" already exist");
    }

    public static void unknownName(Token t, String name){
        error(t, "NameError: name '"+name+"' is not defined");
    }
    public static void nameError(Token t, String msg){
        error(t, "NameError: "+msg);
    }








}
