package ch5;

import java.io.FileReader;
import java.util.HashMap;

/*
cd .\src\ch5\

Создать лексер:
..\..\jflex-1.9.1\bin\jflex .\javalex.l

Создать парсер:
..\..\byaccj1.15_win32\yacc.exe -Jpackage=ch5 -Jclass=parser j0gram.y

В Parser добавить
```
import static ch5.j0.yylex;
import static ch5.yyerror.yyerror;
```

..\..\Graphviz\bin\dot.exe -Tsvg ../../texts/hello.java.dot > ../../hello.svg
* */
public class j0 {
    public static Yylex yylexer;
    public static parser par;

    public static void main(String argv[]) throws Exception {
//        init("texts/hello.java");
        init("texts/first.aqua");
        par = new parser();
        //                  par.yydebug=true;
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

    public static String yytext() {
        return yylexer.yytext();
    }

    public static void lexErr(String s) {
        System.err.println(s);
        System.exit(1);
    }

    public static int scan(int cat) {
        /* cat - номер лексемы (определены как константы в parser) */
        j0.par.yylval =
                new parserVal(new tree("token", 0,
                        new token(cat, yytext(), yylineno)));
        return cat;
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

    public static void print(parserVal root) {
        ((tree) root.obj).print();
        ((tree) root.obj).print_graph(yyfilename + ".dot");
    }

    public static tree unwrap(Object obj) {
        if (obj instanceof token)
            return new tree("token", 0, (token) obj);
        else return (tree) obj;
    }

    public static parserVal node(String s, int r, parserVal... p) {
        /* s - название правила (слева от :)
        * r - номер правила (из файла грамматики)
        * p - массив тех токенов, которые мы берём
        * */
        tree[] t = new tree[p.length];
        for (int i = 0; i < t.length; i++)
            t[i] = (tree) (p[i].obj);
        return new parserVal((Object) new tree(s, r, t));
    }

    public static void process(tree root) {
        Interpreter interpreter = new Interpreter();
        interpreter.semantic(root);
        System.out.println("----interpretation----");
        interpreter.interpret(root);
    }


}
