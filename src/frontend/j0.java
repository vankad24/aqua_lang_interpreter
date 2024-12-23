package frontend;



import runtime.FunctionHandler;
import runtime.Interpreter;
import runtime.SymbolTable;

import java.io.FileReader;

/*
cd .\src\frontend\

Создать лексер:
..\..\jflex-1.9.1\bin\jflex .\javalex.l

Создать парсер:
..\..\byaccj1.15_win32\yacc.exe -Jpackage=frontend -Jclass=Parser j0gram.y

В Parser добавить
```
import static frontend.j0.yylex;
import static frontend.yyerror.yyerror;
```

..\..\Graphviz\bin\dot.exe -Tsvg ../../texts/hello.java.dot > ../../hello.svg

// jpg почему-то не работает. Вот, можно запустить для тестов:
echo 'digraph { a -> b }' | ..\..\Graphviz\bin\dot.exe -Tpng > output.png

* */
public class j0 {
    public static Yylex yylexer;
    public static Parser par;
    static boolean debug = false;
    static boolean disable_semantic = false;
    static String file_path = null;

    public static void main(String[] argv) throws Exception {
        parseArguments(argv);
        if (file_path == null){
            System.err.println("No file path provided");
            System.exit(1);
        }
        init(file_path);

        par = new Parser();
        //par.yydebug=true;
        yylineno = 1;
        int i = par.yyparse();
        if (debug && i == 0)
            System.out.println("No errors");
    }

    private static void parseArguments(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("-")) {
                switch (arg) {
                    case "--debug" -> debug = true;
                    case "--no-semantic" -> disable_semantic = true;
                    case "--24" -> {
                        System.out.println("Пасхалочку нашёл, дружок! Держи с полки пирожок!");
                        System.exit(0);
                    }
                    default -> {
                        System.err.println("Unknown flag '"+arg+"'");
                        System.exit(1);
                    }
                }
            }else {
                if (file_path != null){
                    System.err.println("Expected only one file path parameter");
                    System.exit(1);
                } else {
                    file_path = arg;
                }
            }
        }
    }

    public static int yylineno;
    public static String yyfilename;

    //   public static parserVal yylval;
    public static void init(String s) throws Exception {
        yyfilename = s;
        yylexer = new Yylex(new FileReader(s));
    }

    public static int YYEOF() {
        return Yylex.YYEOF;
    }

    public static int yylex() {
        /* rv - номер лексемы */
        int rv = 0;
        try {
            rv = yylexer.yylex();
        } catch (java.io.IOException ioException) {
            rv = -1;
        }
        return rv;
    }

    public static void lexErr(String s) {
        System.err.println(s);
        System.exit(1);
    }

    public static int scan(int code) {
        /* code - номер лексемы (определены как константы в parser) */
        j0.par.yylval =
                new ParserVal(new Tree("token", 0,
                        new Token(code, yylexer.yytext(), yylineno)));
        return code;
    }

    public static void newline() {
        yylineno++;
    }

    public static void whitespace() {
    }

    public static void comment() {
    }

    public static short ord(String s) {
        return (short) (s.charAt(0));
    }

    public static Tree unwrap(Object obj) {
        if (obj instanceof Token)
            return new Tree("token", 0, (Token) obj);
        else return (Tree) obj;
    }

    public static ParserVal node(String s, int r, ParserVal... p) {
        /* s - название правила (слева от :)
        * r - номер правила (из файла грамматики)
        * p - массив тех токенов, которые мы берём
        * */
        Tree[] t = new Tree[p.length];
        for (int i = 0; i < t.length; i++)
            t[i] = (Tree) (p[i].obj);
        return new ParserVal((Object) new Tree(s, r, t));
    }

    public static void process(Tree root) {
        if (debug)root.print();
//        ((Tree) root.obj).print_graph(yyfilename + ".dot");

        SymbolTable global_scope = new SymbolTable("global");
        FunctionHandler.initBuiltin(global_scope);

        if (!disable_semantic) {
            if (debug) System.out.println("----semantic----");
            Interpreter.semantic(root, global_scope);
            if (debug) System.out.println("No errors");
        }
        if (debug) System.out.println("----interpretation----");
        Interpreter.interpret(root, global_scope);
    }
}
