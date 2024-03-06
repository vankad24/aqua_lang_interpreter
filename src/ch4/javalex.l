package ch4;
%%
%int
id=([a-zA-Z_][a-zA-Z0-9_]*)
%%
"/*"([^*]|"*"+[^/*])*"*"+"/" { j0.comment(); }
"//".*\r?\n                  { j0.comment(); }
[ \t\r\f]+                   { j0.whitespace(); }
\n                           { j0.newline(); }
"break"                { return j0.scan(Parser.BREAK); }
"double"               { return j0.scan(Parser.DOUBLE); } 
"else"                 { return j0.scan(Parser.ELSE); }
"false"                { return j0.scan(Parser.BOOLLIT); }
"for"                  { return j0.scan(Parser.FOR); }
"if"                   { return j0.scan(Parser.IF); }
"int"                  { return j0.scan(Parser.INT); }
"null"                 { return j0.scan(Parser.NULLVAL); }
"public"               { return j0.scan(Parser.PUBLIC); }
"return"               { return j0.scan(Parser.RETURN); }
"static"               { return j0.scan(Parser.STATIC); }
"string"               { return j0.scan(Parser.STRING); }
"true"                 { return j0.scan(Parser.BOOLLIT); }
"bool"                 { return j0.scan(Parser.BOOL); }
"void"                 { return j0.scan(Parser.VOID); }
"while"                { return j0.scan(Parser.WHILE); }
"class"                { return j0.scan(Parser.CLASS); }
"("                    { return j0.scan(j0.ord("("));}
")"                    { return j0.scan(j0.ord(")"));}
"["                    { return j0.scan(j0.ord("["));}
"]"                    { return j0.scan(j0.ord("]"));}
"{"                    { return j0.scan(j0.ord("{"));}
"}"                    { return j0.scan(j0.ord("}"));}
";"                    { return j0.scan(j0.ord(";"));}
":"                    { return j0.scan(j0.ord(":"));}
"!"                    { return j0.scan(j0.ord("!"));}
"*"                    { return j0.scan(j0.ord("*"));}
"/"                    { return j0.scan(j0.ord("/"));}
"%"                    { return j0.scan(j0.ord("%"));}
"+"                    { return j0.scan(j0.ord("+"));}
"-"                    { return j0.scan(j0.ord("-"));}
"<"                    { return j0.scan(j0.ord("<"));}
"<="                   { return j0.scan(Parser.LESSTHANOREQUAL);}
">"                    { return j0.scan(j0.ord(">"));}
">="                   { return j0.scan(Parser.GREATERTHANOREQUAL);}
"=="                   { return j0.scan(Parser.ISEQUALTO);}
"!="                   { return j0.scan(Parser.NOTEQUALTO);}
"&&"                   { return j0.scan(Parser.LOGICALAND);}
"||"                   { return j0.scan(Parser.LOGICALOR);}
"="                    { return j0.scan(j0.ord("=")); }
"+="                   { return j0.scan(Parser.INCREMENT); }
"-="                   { return j0.scan(Parser.DECREMENT); }
","                    { return j0.scan(j0.ord(",")); }
"."                    { return j0.scan(j0.ord(".")); }
{id}                   { return j0.scan(Parser.IDENTIFIER); }
[0-9]+                 { return j0.scan(Parser.INTLIT); }
[0-9]*"."[0-9]*([eE][+-]?[0-9]+)? { return j0.scan(Parser.DOUBLELIT); }
([0-9]+)([eE][+-]?([0-9]+))  { return j0.scan(Parser.DOUBLELIT); }
\"([^\"]|(\\.))*\"     { return j0.scan(Parser.STRINGLIT); }
.                      { j0.lexErr("unrecognized character"); }
