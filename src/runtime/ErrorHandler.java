package runtime;

import frontend.Token;
import frontend.j0;

public class ErrorHandler {
    public static void print(String msg){
        throw new RuntimeException(msg);
    }

    public static void error(Token t, String msg){
        String result_msg = "";
        if (t!=null) {
            result_msg+="File " + j0.yyfilename + ":" + t.lineno + "\n";
            result_msg+="\t" + t.text + "\n";
        }
        result_msg+=msg;
        throw new RuntimeException(result_msg);
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
