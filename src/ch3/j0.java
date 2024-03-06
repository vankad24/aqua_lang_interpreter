package ch3;

import java.io.FileReader;
public class j0 {
   static Yylex lex;
   public static int yylineno;
   public static token yylval;
   public static void main(String argv[]) throws Exception {
      String path = "./texts/hello.java";//argv[0];
      lex = new Yylex(new FileReader(path));
      yylineno = 1;
      int i;
      while ((i=lex.yylex()) != Yylex.YYEOF) {
         System.out.println("line "+yylineno+", token " + i + ": " + yytext());
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
   public static void newline() {
      yylineno++;
   }
   public static void whitespace() {
   }
   public static void comment() {
   }
   public static short ord(String s) { return (short)(s.charAt(0)); }
}
