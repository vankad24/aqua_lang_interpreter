package ch4;
public class trivial {
   static ch4.Parser par;
   public static void main(String argv[]) throws Exception {
      String path = "texts/dorrie3.in";
      ch4.lexer.init(path);
      par = new ch4.Parser();
      int i = par.yyparse();
      if (i == 0)
         System.out.println("no errors");
   }
}
