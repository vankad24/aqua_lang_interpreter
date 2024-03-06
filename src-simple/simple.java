import java.io.FileReader;

// jflex command:
// .\jflex-1.9.1\bin\jflex .\src\lexemes.l

public class simple {
    static Yylex lex;
    public static int yylineno;
    public static token yylval;
    public static void main(String argv[]) throws Exception {
        String path = "./texts/dorrie2.txt";//argv[0];
        lex = new Yylex(new FileReader(path));
        yylineno = 1;
        int i;
        while ((i=lex.yylex()) != Yylex.YYEOF) {
            System.out.println("token " + i +
                    " (line " + yylval.lineno + "): " + yytext());
        }
    }
    public static String yytext() {
        return lex.yytext();
    }
    public static void lexErr(String s) {
        System.err.println(s + ": line " + yylineno +
                ": " + yytext());
        System.exit(1);
    }
    public static int scan(int cat) {
        yylval = new token(cat, yytext(), yylineno);
        return cat;
    }
    public static void increment_lineno() {
        yylineno++;
    }
}