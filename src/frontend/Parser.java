//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package frontend;




import static frontend.j0.yylex;
import static frontend.Yyerror.yyerror;




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class parserVal is defined in parserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short BREAK=257;
public final static short DOUBLE=258;
public final static short ELSE=259;
public final static short FOR=260;
public final static short IF=261;
public final static short INT=262;
public final static short RETURN=263;
public final static short VOID=264;
public final static short WHILE=265;
public final static short IDENTIFIER=266;
public final static short CLASSNAME=267;
public final static short CLASS=268;
public final static short STRING=269;
public final static short BOOL=270;
public final static short INTLIT=271;
public final static short DOUBLELIT=272;
public final static short STRINGLIT=273;
public final static short BOOLLIT=274;
public final static short NULLVAL=275;
public final static short LESSTHANOREQUAL=276;
public final static short GREATERTHANOREQUAL=277;
public final static short ISEQUALTO=278;
public final static short NOTEQUALTO=279;
public final static short LOGICALAND=280;
public final static short LOGICALOR=281;
public final static short INCREMENT=282;
public final static short DECREMENT=283;
public final static short PUBLIC=284;
public final static short STATIC=285;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    2,    3,    3,    4,    4,    5,    5,    5,    6,
    9,    9,    9,    9,    9,   11,   11,   12,   10,   10,
   13,   13,   14,   14,    7,   15,   17,   18,   18,   19,
   19,   20,    8,   16,    1,    1,   21,   21,   22,   22,
   23,   25,   24,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   26,   34,   34,   29,   30,   31,   31,   38,
   38,   39,   32,   33,   40,   40,   40,   41,   41,   42,
   42,   43,   43,   27,   27,   28,   44,   44,   44,   44,
   45,   45,   45,   45,   45,   47,   47,   46,   48,   48,
   36,   36,   49,   49,   50,   50,   50,   51,   51,   51,
   51,   52,   52,   52,   53,   53,   53,   53,   54,   54,
   55,   55,   55,   56,   56,   57,   57,   37,   37,   35,
   58,   58,   59,   59,   59,
};
final static short yylen[] = {                            2,
    1,    4,    3,    2,    1,    2,    1,    1,    1,    3,
    1,    1,    1,    1,    1,    1,    1,    3,    1,    3,
    1,    3,    1,    1,    2,    4,    4,    1,    0,    1,
    3,    2,    2,    3,    1,    0,    1,    2,    1,    1,
    2,    2,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    2,    1,    1,    5,    7,    6,    8,    1,
    2,    2,    5,    9,    1,    1,    0,    1,    0,    1,
    0,    1,    3,    2,    3,    3,    1,    1,    1,    3,
    1,    1,    1,    1,    1,    1,    3,    3,    1,    0,
    4,    6,    1,    1,    2,    2,    1,    1,    3,    3,
    3,    1,    3,    3,    1,    1,    1,    1,    1,    3,
    1,    3,    3,    1,    3,    1,    3,    1,    1,    3,
    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,   12,    0,    0,   11,    0,    0,   16,   14,   13,
   81,   82,   84,   83,   85,    0,   44,    0,    0,    1,
    0,    0,   17,   43,    0,   37,   39,   40,    0,   45,
   46,   47,   48,   49,   50,   51,   52,    0,   54,    0,
    0,   77,    0,    0,    0,   74,    0,    0,    0,    0,
    0,  119,   79,   68,    0,    0,   97,   98,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   21,    0,    0,
    0,    0,   38,   41,   53,    0,  124,  125,  123,    0,
   75,   66,   72,    0,    0,    0,    0,   78,   95,   96,
   76,    0,    0,    0,    0,    0,  105,  106,  107,  108,
    0,    0,    0,    0,    0,    0,   34,   80,    0,    0,
   18,   86,    0,    0,    0,  120,    0,    0,    0,   99,
  100,  101,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   22,    0,   91,    0,    0,    0,   73,    0,   63,
   87,    0,    0,    0,    0,   60,   92,    0,    0,    0,
   57,   62,    0,   61,    0,    0,   59,   64,    0,    0,
   56,
};
final static short yydgoto[] = {                         19,
   20,    0,    0,    0,    0,    0,    0,    0,   21,   69,
   87,   23,   70,    0,    0,   24,    0,    0,    0,    0,
   25,   26,   27,   28,   29,   30,   31,   32,   33,   34,
   35,   36,   37,   38,   52,   53,   54,  145,  146,   84,
   55,  148,   85,   56,   42,   43,  113,  114,   57,   58,
   59,   60,  101,   61,   62,   63,   64,   44,   80,
};
final static short yysindex[] = {                       157,
  -56,    0,    9,   14,    0,   73,   16,    0,    0,    0,
    0,    0,    0,    0,    0,  157,    0,   73,    0,    0,
 -228,    5,    0,    0,  157,    0,    0,    0,  -13,    0,
    0,    0,    0,    0,    0,    0,    0,   -1,    0,    0,
   25,    0,    0,  -50,    3,    0,  175,   73,   73,   73,
    5,    0,    0,    0,   20,   25,    0,    0,    6,    7,
   12, -203, -194, -178,   73,  -20,   70,    0,   71,   23,
 -145,   73,    0,    0,    0, -144,    0,    0,    0,   73,
    0,    0,    0,   64,   83,   87,    5,    0,    0,    0,
    0,   73,   73,   73,   73,   73,    0,    0,    0,    0,
   73,   73,   73,   73,   73,   93,    0,    0, -228,   44,
    0,    0,   97,  117,  131,    0,   73,   47,   49,    0,
    0,    0,    6,    6,    7,   12,   12, -203, -194,  194,
   23,    0,   73,    0,   73,  120,    5,    0,  -77,    0,
    0,  146,   47,  -97,  -71,    0,    0,  148,   83,  150,
    0,    0,  -97,    0,   49,   73,    0,    0,  151,   49,
    0,
};
final static short yyrindex[] = {                       193,
    0,    0,    0,    0,    0,  135,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   74,    0,    0,    0,    0,
    0,  -59,    0,    0,   18,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   -2,
    0,    0,  -37,    0,    0,    0,  139,    0,    0,    0,
   48,    0,    0,    0,    0,   57,    0,    0,  -29,  101,
  -24,  -12,   -4,  137,    0,    0,    0,    0,  141,  -31,
    0,  154,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  142,    0,   88,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  161,    0,   22,    0,  135,    0,    0,    0,
    0,    0,   95,  108,  115,   29,  121,  125,  132,    0,
  -25,    0,    0,    0,  154,    0,   59,    0,    1,    0,
    0,    0,  162,    0,   21,    0,    0,    0,  163,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
  189,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  456,    0,  100,    0,    0,  196,    0,    0,    0,    0,
    0,  181,    0,   81,  165,    0,    0,    0, -117,    0,
    0,    0,    0,   65,  436,  463,  422,    0,   68,    0,
  102,    0,   75,  464,    0,  446,    0,   79,    0,  136,
  -18,  116,    0,   -6,  118,  122,    0,    0,    0,
};
final static int YYTABLESIZE=612;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         78,
   56,  121,   46,   78,   78,   78,   78,   78,   78,   78,
   79,  102,   19,  102,  102,  102,  111,   35,   20,  111,
   58,   78,   78,  122,   78,   16,  152,   19,  114,  102,
  102,  114,  102,   20,  111,  152,  116,   68,   55,  116,
   56,   55,   94,   79,   72,   74,  114,   92,   47,   96,
   71,   95,   93,   48,  116,   65,   55,   75,   88,   56,
   58,   81,   88,   88,   88,   88,   88,   88,   88,  112,
   76,   99,  112,  100,  102,  103,  123,  124,   91,   58,
   88,   88,   88,   88,   94,  104,   18,  112,   94,   94,
   94,   94,   94,   93,   94,  126,  127,   93,   93,   93,
   93,   93,  105,   93,  107,   50,   94,   94,  121,   94,
  108,   83,   18,  110,  109,   93,   93,   49,   93,  121,
  111,  115,  117,   56,   94,   56,  118,  119,   94,   94,
   94,   94,   94,  130,   94,  104,  132,  104,  104,  104,
  133,  109,   35,   58,  109,   58,   94,   94,  103,   94,
  103,  103,  103,  104,  104,  110,  104,  134,  110,  109,
  109,  113,  109,  150,  113,  115,  103,  103,  115,  103,
  135,   16,  117,  110,  110,  117,  110,  118,  143,  113,
  118,  144,  138,  115,   89,   90,  147,  153,  155,  156,
  117,  160,   36,   69,   90,  118,   18,   67,   36,   42,
   65,   89,   71,   70,   66,   73,   15,   83,  131,   45,
  140,   82,  154,  142,   18,   17,  125,  149,  136,    0,
    0,  128,  121,  121,    0,    0,  129,  120,  121,  122,
    0,   77,   78,   18,    0,    0,    0,    0,   78,   78,
   78,   78,   78,   78,  122,  122,  102,  102,  102,  102,
  102,  102,   17,  111,  111,  111,  111,   56,   56,    0,
   56,   56,   56,   56,    0,   56,   56,  114,  114,   56,
   56,   56,   56,   56,   56,   56,  116,   58,   58,   16,
   58,   58,   58,   58,    0,   58,   58,   97,   98,   58,
   58,   58,   58,   58,   58,   58,    0,   88,   88,   88,
   88,   88,   88,   88,   88,    0,  112,  112,  112,  112,
    0,    0,    8,    0,  139,    0,   16,   11,   12,   13,
   14,   15,    0,   94,   94,   94,   94,   94,   94,  121,
  121,    0,   93,   93,   93,   93,   93,   93,    8,  151,
  121,  121,    0,   11,   12,   13,   14,   15,  157,    0,
  158,    0,    0,    0,    0,  161,    0,    0,    0,    0,
    0,    0,    0,   94,   94,   94,   94,   94,   94,    0,
  104,  104,  104,  104,  104,  104,  109,  109,  109,  109,
  109,  109,    0,  103,  103,  103,  103,  103,  103,    0,
  110,  110,  110,  110,  110,  110,    0,    0,  113,  113,
  113,  113,    0,    0,  115,  115,    0,    0,    0,    0,
    0,    0,  117,    1,    2,    0,    3,    4,    5,    6,
    0,    7,    8,    0,    0,    9,   10,   11,   12,   13,
   14,   15,    2,    0,    0,   39,    5,    0,    0,   67,
    8,    0,    0,    9,   10,   11,   12,   13,   14,   15,
    1,   39,    0,    3,    4,   22,    6,    0,    7,    8,
   39,   51,   40,   41,   11,   12,   13,   14,   15,   86,
    0,   22,    0,   51,    0,    0,    0,    0,   40,   41,
   22,    0,   39,    0,    0,    0,  106,   40,   41,    0,
    0,    0,    0,  112,   88,   88,    0,    0,    0,    0,
    0,  116,   22,   51,    0,    0,    0,    0,    0,   40,
   41,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   51,    0,    0,    0,    0,    0,    0,   51,    0,    0,
    0,    0,    0,    0,    0,   51,    0,   88,   88,   88,
   88,   88,    0,    0,    0,    0,   88,   88,   88,   88,
   88,    0,    0,   39,  141,    0,  112,    0,    0,    0,
    0,    0,    0,    0,    0,   39,    0,    0,    0,    0,
    0,    0,   51,  137,    0,    0,    0,  159,   39,    0,
   40,   41,    0,    0,    0,  137,    0,    0,   51,    0,
   51,    0,   40,   41,    0,    0,    0,    0,  137,    0,
    0,    0,    0,    0,    0,   40,   41,    0,    0,    0,
    0,   51,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
    0,   61,   59,   41,   42,   43,   44,   45,   46,   47,
   61,   41,   44,   43,   44,   45,   41,    0,   44,   44,
    0,   59,   60,   61,   62,  123,  144,   59,   41,   59,
   60,   44,   62,   59,   59,  153,   41,  266,   41,   44,
   40,   44,   37,   46,   40,   59,   59,   42,   40,   43,
   46,   45,   47,   40,   59,   40,   59,   59,   37,   59,
   40,   59,   41,   42,   43,   44,   45,   46,   47,   41,
   46,   60,   44,   62,  278,  279,   95,   96,   59,   59,
   59,   60,   61,   62,   37,  280,   40,   59,   41,   42,
   43,   44,   45,   37,   47,  102,  103,   41,   42,   43,
   44,   45,  281,   47,  125,   33,   59,   60,   61,   62,
   41,   47,   40,   91,   44,   59,   60,   45,   62,   61,
  266,  266,   59,  123,   37,  125,   44,   41,   41,   42,
   43,   44,   45,   41,   47,   41,   93,   43,   44,   45,
   44,   41,  125,  123,   44,  125,   59,   60,   41,   62,
   43,   44,   45,   59,   60,   41,   62,   41,   44,   59,
   60,   41,   62,  261,   44,   41,   59,   60,   44,   62,
   40,  123,   41,   59,   60,   44,   62,   41,   59,   59,
   44,  259,  118,   59,   49,   50,   41,  259,   41,   40,
   59,   41,    0,   59,   41,   59,   40,   59,  125,   59,
   59,   41,   41,   41,   16,   25,  266,  143,  109,  266,
  130,   47,  145,  135,   40,   59,  101,  143,  117,   -1,
   -1,  104,  282,  283,   -1,   -1,  105,   92,   93,   94,
   -1,  282,  283,   40,   -1,   -1,   -1,   -1,  276,  277,
  278,  279,  280,  281,  282,  283,  276,  277,  278,  279,
  280,  281,   59,  278,  279,  280,  281,  257,  258,   -1,
  260,  261,  262,  263,   -1,  265,  266,  280,  281,  269,
  270,  271,  272,  273,  274,  275,  281,  257,  258,  123,
  260,  261,  262,  263,   -1,  265,  266,  276,  277,  269,
  270,  271,  272,  273,  274,  275,   -1,  276,  277,  278,
  279,  280,  281,  282,  283,   -1,  278,  279,  280,  281,
   -1,   -1,  266,   -1,  119,   -1,  123,  271,  272,  273,
  274,  275,   -1,  276,  277,  278,  279,  280,  281,  282,
  283,   -1,  276,  277,  278,  279,  280,  281,  266,  144,
  282,  283,   -1,  271,  272,  273,  274,  275,  153,   -1,
  155,   -1,   -1,   -1,   -1,  160,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  276,  277,  278,  279,  280,  281,   -1,
  276,  277,  278,  279,  280,  281,  276,  277,  278,  279,
  280,  281,   -1,  276,  277,  278,  279,  280,  281,   -1,
  276,  277,  278,  279,  280,  281,   -1,   -1,  278,  279,
  280,  281,   -1,   -1,  280,  281,   -1,   -1,   -1,   -1,
   -1,   -1,  281,  257,  258,   -1,  260,  261,  262,  263,
   -1,  265,  266,   -1,   -1,  269,  270,  271,  272,  273,
  274,  275,  258,   -1,   -1,    0,  262,   -1,   -1,   18,
  266,   -1,   -1,  269,  270,  271,  272,  273,  274,  275,
  257,   16,   -1,  260,  261,    0,  263,   -1,  265,  266,
   25,    6,    0,    0,  271,  272,  273,  274,  275,   48,
   -1,   16,   -1,   18,   -1,   -1,   -1,   -1,   16,   16,
   25,   -1,   47,   -1,   -1,   -1,   65,   25,   25,   -1,
   -1,   -1,   -1,   72,   49,   50,   -1,   -1,   -1,   -1,
   -1,   80,   47,   48,   -1,   -1,   -1,   -1,   -1,   47,
   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   65,   -1,   -1,   -1,   -1,   -1,   -1,   72,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   80,   -1,   92,   93,   94,
   95,   96,   -1,   -1,   -1,   -1,  101,  102,  103,  104,
  105,   -1,   -1,  118,  133,   -1,  135,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  130,   -1,   -1,   -1,   -1,
   -1,   -1,  117,  118,   -1,   -1,   -1,  156,  143,   -1,
  118,  118,   -1,   -1,   -1,  130,   -1,   -1,  133,   -1,
  135,   -1,  130,  130,   -1,   -1,   -1,   -1,  143,   -1,
   -1,   -1,   -1,   -1,   -1,  143,  143,   -1,   -1,   -1,
   -1,  156,
};
}
final static short YYFINAL=19;
final static short YYMAXTOKEN=285;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"BREAK","DOUBLE","ELSE","FOR","IF",
"INT","RETURN","VOID","WHILE","IDENTIFIER","CLASSNAME","CLASS","STRING","BOOL",
"INTLIT","DOUBLELIT","STRINGLIT","BOOLLIT","NULLVAL","LESSTHANOREQUAL",
"GREATERTHANOREQUAL","ISEQUALTO","NOTEQUALTO","LOGICALAND","LOGICALOR",
"INCREMENT","DECREMENT","PUBLIC","STATIC",
};
final static String yyrule[] = {
"$accept : StartTerm",
"StartTerm : BlockStmtsOpt",
"ClassDecl : PUBLIC CLASS IDENTIFIER ClassBody",
"ClassBody : '{' ClassBodyDecls '}'",
"ClassBody : '{' '}'",
"ClassBodyDecls : ClassBodyDecl",
"ClassBodyDecls : ClassBodyDecls ClassBodyDecl",
"ClassBodyDecl : FieldDecl",
"ClassBodyDecl : MethodDecl",
"ClassBodyDecl : ConstructorDecl",
"FieldDecl : Type VarDecls ';'",
"Type : INT",
"Type : DOUBLE",
"Type : BOOL",
"Type : STRING",
"Type : Name",
"Name : IDENTIFIER",
"Name : QualifiedName",
"QualifiedName : Name '.' IDENTIFIER",
"VarDecls : VarDeclarator",
"VarDecls : VarDecls ',' VarDeclarator",
"VarDeclarator : IDENTIFIER",
"VarDeclarator : VarDeclarator '[' ']'",
"MethodReturnVal : Type",
"MethodReturnVal : VOID",
"MethodDecl : MethodHeader Block",
"MethodHeader : PUBLIC STATIC MethodReturnVal MethodDeclarator",
"MethodDeclarator : IDENTIFIER '(' FormalParmListOpt ')'",
"FormalParmListOpt : FormalParmList",
"FormalParmListOpt :",
"FormalParmList : FormalParm",
"FormalParmList : FormalParmList ',' FormalParm",
"FormalParm : Type VarDeclarator",
"ConstructorDecl : MethodDeclarator Block",
"Block : '{' BlockStmtsOpt '}'",
"BlockStmtsOpt : BlockStmts",
"BlockStmtsOpt :",
"BlockStmts : BlockStmt",
"BlockStmts : BlockStmts BlockStmt",
"BlockStmt : LocalVarDeclStmt",
"BlockStmt : Stmt",
"LocalVarDeclStmt : LocalVarDecl ';'",
"LocalVarDecl : Type VarDecls",
"Stmt : Block",
"Stmt : ';'",
"Stmt : ExprStmt",
"Stmt : BreakStmt",
"Stmt : ReturnStmt",
"Stmt : IfThenStmt",
"Stmt : IfThenElseStmt",
"Stmt : IfThenElseIfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"ExprStmt : StmtExpr ';'",
"StmtExpr : Assignment",
"StmtExpr : MethodCall",
"IfThenStmt : IF '(' Expr ')' Block",
"IfThenElseStmt : IF '(' Expr ')' Block ELSE Block",
"IfThenElseIfStmt : IF '(' Expr ')' Block ElseIfSequence",
"IfThenElseIfStmt : IF '(' Expr ')' Block ElseIfSequence ELSE Block",
"ElseIfSequence : ElseIfStmt",
"ElseIfSequence : ElseIfSequence ElseIfStmt",
"ElseIfStmt : ELSE IfThenStmt",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"ForStmt : FOR '(' ForInit ';' ExprOpt ';' ForUpdate ')' Block",
"ForInit : StmtExprList",
"ForInit : LocalVarDecl",
"ForInit :",
"ExprOpt : Expr",
"ExprOpt :",
"ForUpdate : StmtExprList",
"ForUpdate :",
"StmtExprList : StmtExpr",
"StmtExprList : StmtExprList ',' StmtExpr",
"BreakStmt : BREAK ';'",
"BreakStmt : BREAK IDENTIFIER ';'",
"ReturnStmt : RETURN ExprOpt ';'",
"Primary : Literal",
"Primary : FieldAccess",
"Primary : MethodCall",
"Primary : '(' Expr ')'",
"Literal : INTLIT",
"Literal : DOUBLELIT",
"Literal : BOOLLIT",
"Literal : STRINGLIT",
"Literal : NULLVAL",
"ArgList : Expr",
"ArgList : ArgList ',' Expr",
"FieldAccess : Primary '.' IDENTIFIER",
"ArgListOpt : ArgList",
"ArgListOpt :",
"MethodCall : Name '(' ArgListOpt ')'",
"MethodCall : Primary '.' IDENTIFIER '(' ArgListOpt ')'",
"PostFixExpr : Primary",
"PostFixExpr : Name",
"UnaryExpr : '-' UnaryExpr",
"UnaryExpr : '!' UnaryExpr",
"UnaryExpr : PostFixExpr",
"MulExpr : UnaryExpr",
"MulExpr : MulExpr '*' UnaryExpr",
"MulExpr : MulExpr '/' UnaryExpr",
"MulExpr : MulExpr '%' UnaryExpr",
"AddExpr : MulExpr",
"AddExpr : AddExpr '+' MulExpr",
"AddExpr : AddExpr '-' MulExpr",
"RelOp : LESSTHANOREQUAL",
"RelOp : GREATERTHANOREQUAL",
"RelOp : '<'",
"RelOp : '>'",
"RelExpr : AddExpr",
"RelExpr : RelExpr RelOp AddExpr",
"EqExpr : RelExpr",
"EqExpr : EqExpr ISEQUALTO RelExpr",
"EqExpr : EqExpr NOTEQUALTO RelExpr",
"CondAndExpr : EqExpr",
"CondAndExpr : CondAndExpr LOGICALAND EqExpr",
"CondOrExpr : CondAndExpr",
"CondOrExpr : CondOrExpr LOGICALOR CondAndExpr",
"Expr : CondOrExpr",
"Expr : Assignment",
"Assignment : LeftHandSide AssignOp Expr",
"LeftHandSide : Name",
"LeftHandSide : FieldAccess",
"AssignOp : '='",
"AssignOp : INCREMENT",
"AssignOp : DECREMENT",
};

//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 8 "j0gram.y"
{
    yyval=j0.node("BlockStmtsOpt",1024,val_peek(0));
    j0.print(yyval);
    j0.process((Tree) yyval.obj);
}
break;
case 2:
//#line 13 "j0gram.y"
{
  yyval=j0.node("ClassDecl",1000,val_peek(1),val_peek(0));
  j0.print(yyval);
 }
break;
case 3:
//#line 17 "j0gram.y"
{ yyval=j0.node("ClassBody",1010,val_peek(1)); }
break;
case 4:
//#line 18 "j0gram.y"
{ yyval=j0.node("ClassBody",1011); }
break;
case 6:
//#line 20 "j0gram.y"
{
  yyval=j0.node("ClassBodyDecls",1020,val_peek(1),val_peek(0)); }
break;
case 10:
//#line 23 "j0gram.y"
{
  yyval=j0.node("FieldDecl",1030,val_peek(2),val_peek(1)); }
break;
case 18:
//#line 28 "j0gram.y"
{
  yyval=j0.node("QualifiedName",1040,val_peek(2),val_peek(0));}
break;
case 20:
//#line 31 "j0gram.y"
{
  yyval=j0.node("VarDecls",1050,val_peek(2),val_peek(0)); }
break;
case 22:
//#line 33 "j0gram.y"
{
  yyval=j0.node("VarDeclarator",1060,val_peek(2)); }
break;
case 25:
//#line 37 "j0gram.y"
{
  yyval=j0.node("MethodDecl",1380,val_peek(1),val_peek(0));
 }
break;
case 26:
//#line 40 "j0gram.y"
{
  yyval=j0.node("MethodHeader",1070,val_peek(1),val_peek(0)); }
break;
case 27:
//#line 42 "j0gram.y"
{
  yyval=j0.node("MethodDeclarator",1080,val_peek(3),val_peek(1)); }
break;
case 31:
//#line 46 "j0gram.y"
{
  yyval=j0.node("FormalParmList",1090,val_peek(2),val_peek(0)); }
break;
case 32:
//#line 48 "j0gram.y"
{
  yyval=j0.node("FormalParm",1100,val_peek(1),val_peek(0));
 }
break;
case 33:
//#line 52 "j0gram.y"
{
  yyval=j0.node("ConstructorDecl",1110,val_peek(1),val_peek(0)); }
break;
case 34:
//#line 55 "j0gram.y"
{yyval=j0.node("Block",1200,val_peek(1));}
break;
case 38:
//#line 57 "j0gram.y"
{
  yyval=j0.node("BlockStmts",1130,val_peek(1),val_peek(0)); }
break;
case 42:
//#line 62 "j0gram.y"
{
  yyval=j0.node("LocalVarDecl",1140,val_peek(1),val_peek(0)); }
break;
case 56:
//#line 73 "j0gram.y"
{
  yyval=j0.node("IfThenStmt",1150,val_peek(2),val_peek(0)); }
break;
case 57:
//#line 75 "j0gram.y"
{
  yyval=j0.node("IfThenElseStmt",1160,val_peek(4),val_peek(2),val_peek(0)); }
break;
case 58:
//#line 77 "j0gram.y"
{
  yyval=j0.node("IfThenElseIfStmt",1170,val_peek(3),val_peek(1),val_peek(0)); }
break;
case 59:
//#line 79 "j0gram.y"
{
  yyval=j0.node("IfThenElseIfStmt",1171,val_peek(5),val_peek(3),val_peek(2),val_peek(0)); }
break;
case 61:
//#line 82 "j0gram.y"
{
  yyval=j0.node("ElseIfSequence",1180,val_peek(1),val_peek(0)); }
break;
case 62:
//#line 84 "j0gram.y"
{
  yyval=j0.node("ElseIfStmt",1190,val_peek(0)); }
break;
case 63:
//#line 86 "j0gram.y"
{
  yyval=j0.node("WhileStmt",1210,val_peek(2),val_peek(0)); }
break;
case 64:
//#line 89 "j0gram.y"
{
  yyval=j0.node("ForStmt",1220,val_peek(6),val_peek(4),val_peek(2),val_peek(0)); }
break;
case 73:
//#line 95 "j0gram.y"
{
  yyval=j0.node("StmtExprList",1230,val_peek(2),val_peek(0)); }
break;
case 75:
//#line 98 "j0gram.y"
{
  yyval=j0.node("BreakStmt",1240,val_peek(1)); }
break;
case 76:
//#line 100 "j0gram.y"
{
  yyval=j0.node("ReturnStmt",1250,val_peek(1)); }
break;
case 80:
//#line 103 "j0gram.y"
{
  yyval=val_peek(1);}
break;
case 87:
//#line 107 "j0gram.y"
{
  yyval=j0.node("ArgList",1270,val_peek(2),val_peek(0)); }
break;
case 88:
//#line 109 "j0gram.y"
{
  yyval=j0.node("FieldAccess",1280,val_peek(2),val_peek(0)); }
break;
case 91:
//#line 113 "j0gram.y"
{
  yyval=j0.node("MethodCall",1290,val_peek(3),val_peek(1)); }
break;
case 92:
//#line 115 "j0gram.y"
{
    yyval=j0.node("MethodCall",1291,val_peek(5),val_peek(3),val_peek(1)); }
break;
case 95:
//#line 120 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1300,val_peek(1),val_peek(0)); }
break;
case 96:
//#line 122 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1301,val_peek(1),val_peek(0)); }
break;
case 99:
//#line 126 "j0gram.y"
{
      yyval=j0.node("MulExpr",1310,val_peek(2),val_peek(0)); }
break;
case 100:
//#line 128 "j0gram.y"
{
      yyval=j0.node("MulExpr",1311,val_peek(2),val_peek(0)); }
break;
case 101:
//#line 130 "j0gram.y"
{
      yyval=j0.node("MulExpr",1312,val_peek(2),val_peek(0)); }
break;
case 103:
//#line 133 "j0gram.y"
{
      yyval=j0.node("AddExpr",1320,val_peek(2),val_peek(0)); }
break;
case 104:
//#line 135 "j0gram.y"
{
      yyval=j0.node("AddExpr",1321,val_peek(2),val_peek(0)); }
break;
case 110:
//#line 138 "j0gram.y"
{
  yyval=j0.node("RelExpr",1330,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 112:
//#line 142 "j0gram.y"
{
  yyval=j0.node("EqExpr",1340,val_peek(2),val_peek(0)); }
break;
case 113:
//#line 144 "j0gram.y"
{
  yyval=j0.node("EqExpr",1341,val_peek(2),val_peek(0)); }
break;
case 115:
//#line 146 "j0gram.y"
{
  yyval=j0.node("CondAndExpr", 1350, val_peek(2), val_peek(0)); }
break;
case 117:
//#line 148 "j0gram.y"
{
  yyval=j0.node("CondOrExpr", 1360, val_peek(2), val_peek(0)); }
break;
case 120:
//#line 152 "j0gram.y"
{
yyval=j0.node("Assignment",1370, val_peek(2), val_peek(1), val_peek(0)); }
break;
//#line 896 "parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
