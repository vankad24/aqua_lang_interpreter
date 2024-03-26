package frontend;



import runtime.Interpreter;

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
* */
public class j0 {
    public static Yylex yylexer;
    public static Parser par;

    public static void main(String argv[]) throws Exception {
//        init("texts/hello.java");
//        int i;
//        while (true){
//            int i;
//
//        }
        init("texts/factorial_task.aqua");
        par = new Parser();
        //par.yydebug=true;
        yylineno = 1;
        int i = par.yyparse();
        if (i == 0)
            System.out.println("no errors");
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

    public static void print(ParserVal root) {
        ((Tree) root.obj).print();
        ((Tree) root.obj).print_graph(yyfilename + ".dot");
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
        Interpreter interpreter = new Interpreter();
        interpreter.semantic(root);
        System.out.println("----interpretation----");
        interpreter.interpret(root);
    }

    public static void semerror(String s) {
        System.out.println("semantic error: " + s);
        System.exit(1);
    }

    public static void error(String s) {
        System.out.println("some error: " + s);
        System.exit(1);
    }
}
