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



//#line 8 "j0gram.y"
import static frontend.j0.yylex;
import static frontend.Yyerror.yyerror;
//#line 20 "Parser.java"




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
//public class ParserVal is defined in ParserVal.java


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
public final static short FLOAT=258;
public final static short INT=259;
public final static short ELSE=260;
public final static short FOR=261;
public final static short IF=262;
public final static short RETURN=263;
public final static short VOID=264;
public final static short WHILE=265;
public final static short DO=266;
public final static short IDENTIFIER=267;
public final static short CLASSNAME=268;
public final static short CLASS=269;
public final static short STRING=270;
public final static short BOOL=271;
public final static short INTLIT=272;
public final static short DOUBLELIT=273;
public final static short STRINGLIT=274;
public final static short BOOLLIT=275;
public final static short NULLVAL=276;
public final static short LESSTHANOREQUAL=277;
public final static short GREATERTHANOREQUAL=278;
public final static short ISEQUALTO=279;
public final static short NOTEQUALTO=280;
public final static short LOGICALAND=281;
public final static short LOGICALOR=282;
public final static short INCREMENT=283;
public final static short DECREMENT=284;
public final static short PUBLIC=285;
public final static short STATIC=286;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    2,    3,    3,    4,    4,    5,    5,    5,    6,
    9,    9,    9,    9,    9,   12,   12,   13,   10,   10,
   14,   14,   15,   15,    7,   16,   18,   19,   19,   20,
   20,   21,    8,   17,    1,    1,   22,   22,   23,   11,
   11,   25,   26,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   27,   35,   35,   30,   30,   31,
   31,   33,   33,   32,   32,   34,   39,   39,   39,   40,
   40,   41,   41,   42,   42,   28,   28,   29,   43,   43,
   43,   43,   44,   44,   44,   44,   44,   46,   46,   45,
   47,   47,   37,   37,   48,   48,   49,   49,   49,   50,
   50,   50,   50,   51,   51,   51,   52,   52,   52,   52,
   53,   53,   54,   54,   54,   55,   55,   56,   56,   38,
   38,   36,   57,   57,   58,   58,   58,
};
final static short yylen[] = {                            2,
    1,    4,    3,    2,    1,    2,    1,    1,    1,    3,
    1,    1,    1,    1,    1,    1,    1,    3,    1,    3,
    1,    3,    1,    1,    2,    4,    4,    1,    0,    1,
    3,    2,    2,    3,    1,    0,    1,    2,    1,    1,
    0,    2,    2,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    2,    1,    1,    3,    5,    7,
    5,    5,    3,    6,    4,    9,    1,    1,    0,    1,
    0,    1,    0,    1,    3,    2,    3,    3,    1,    1,
    1,    3,    1,    1,    1,    1,    1,    1,    3,    3,
    1,    0,    4,    6,    1,    1,    2,    2,    1,    1,
    3,    3,    3,    1,    3,    3,    1,    1,    1,    1,
    1,    3,    1,    3,    3,    1,    3,    1,    3,    1,
    1,    3,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,   12,   11,    0,    0,    0,    0,    0,   16,   14,
   13,   83,   84,   86,   85,   87,    0,    0,   45,    0,
    1,    0,    0,   17,   44,    0,   37,   39,   54,    0,
   46,   47,   48,   49,   50,   51,   52,   53,    0,   56,
    0,    0,   79,    0,    0,    0,   40,   76,    0,    0,
    0,    0,    0,  121,   81,    0,    0,   99,  100,    0,
    0,    0,    0,    0,    0,   70,    0,    0,    0,    0,
    0,    0,   21,    0,    0,    0,    0,   38,   42,   55,
    0,  126,  127,  125,    0,   77,   68,   74,    0,    0,
    0,    0,   80,   97,   98,    0,    0,    0,    0,    0,
    0,  107,  108,  109,  110,    0,    0,    0,    0,    0,
   78,    0,   63,    0,   34,   82,    0,    0,   18,   88,
    0,    0,    0,  122,    0,    0,    0,    0,  101,  102,
  103,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   65,    0,   22,    0,   93,    0,    0,    0,   75,    0,
   61,   62,    0,   89,    0,    0,    0,    0,   94,    0,
    0,   60,    0,   66,
};
final static short yydgoto[] = {                         20,
   21,    0,    0,    0,    0,    0,    0,    0,   22,   74,
   48,   53,   24,   75,    0,    0,   25,    0,    0,    0,
    0,   26,   27,   28,   29,   30,   31,   32,   33,   34,
   35,   36,   37,   38,   39,   54,   55,   66,   89,   67,
  160,   90,   57,   43,   44,  121,  122,   58,   59,   60,
   61,  106,   62,   63,   64,   65,   45,   85,
};
final static short yysindex[] = {                      1474,
  -55,    0,    0,  -26,  -21,   75,  156, -112,    0,    0,
    0,    0,    0,    0,    0,    0, 1474,   75,    0,    0,
    0, -249,    9,    0,    0, 1474,    0,    0,    0,  -28,
    0,    0,    0,    0,    0,    0,    0,    0,  -28,    0,
    0,    6,    0,    0,  -27,  -28,    0,    0,  580,   75,
   75,   75,    9,    0,    0, -112,    6,    0,    0,  -10,
  -15,  -47, -244, -228, -226,    0,  -28,   75, -112, -207,
  -66,   24,    0,   34,  -12, -187,   75,    0,    0,    0,
 -184,    0,    0,    0,   75,    0,    0,    0,   25,   41,
   45,    9,    0,    0,    0, -173,   75,   75,   75,   75,
   75,    0,    0,    0,    0,   75,   75,   75,   75,   75,
    0,   51,    0,  170,    0,    0, -249,    2,    0,    0,
   61,   65,   67,    0,   75,  -38, 1474, 1474,    0,    0,
    0,  -10,  -10,  -15,  -47,  -47, -244, -228, 1474,   75,
    0,  -12,    0,   75,    0,   75,   50,    9,    0, -150,
    0,    0,   71,    0,   72,  -38, 1474,    0,    0,   73,
   41,    0, -112,    0,
};
final static short yyrindex[] = {                       121,
 1307,    0,    0,    0,    0, 1434,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   -3,    0,    0,    0,
    0,    0,  -58,    0,    0,    7,    0,    0,    0, 1126,
    0,    0,    0,    0,    0,    0,    0,    0, 1126,    0,
 1153,    0,    0,    1,    0, 1126,    0,    0,   64,    0,
    0,    0,   57,    0,    0,    0,   96,    0,    0,  408,
  501,  748,  849,  910, 1187,    0, 1126,    0,    0,    0,
    0,    0,    0, 1229, 1099,    0,   87,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   70,
    0,  125,    0,    0,    0, 1254,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   89,    0,   29,    0,   76,    0,  -37,    0,    0,    0,
    0,  434,  465,  539,  784,  822,  875,  944,  -37,    0,
    0, 1208,    0,    0,    0,   87,    0,   42,    0, 1280,
    0,    0,    0,    0,    0,   90,    0,  379,    0,    0,
   93,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  127,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -13, 1657,    0,   28,    0,    0,    8,    0,    0,    0,
    0,    0,  116,  -46,    0,   97,    0,    0,    0,    0,
    0,    0,    0,    0,  -29,  410,  466,  827,    0,   22,
    0,   -8,  703,    0, 1192,    0,    3,    0,  -30,  -61,
   44,    0,  -57,   48,   43,    0,    0,    0,
};
final static int YYTABLESIZE=1814;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         82,
   80,   18,  123,   47,   82,   82,   35,   82,   82,   82,
   17,   52,  104,   49,  105,   70,   79,   73,   50,   88,
   94,   95,   82,   51,   82,   80,   99,  101,   90,  100,
   47,   97,   86,   84,  107,  108,   98,   80,  132,  133,
   80,   80,   80,   80,   80,   80,   80,   80,   77,  135,
  136,   81,  109,  111,   76,  110,   96,  114,  115,   80,
   80,  124,   80,   96,  116,   90,  129,  130,  131,   90,
   90,   90,   90,   90,   90,   90,  113,  117,  118,  119,
  150,  151,  123,  125,  126,  127,  128,   90,   90,   90,
   90,  139,  152,   96,  143,   95,  149,   96,   96,   96,
   96,   96,  123,   96,  144,  145,  146,   52,  156,  157,
  162,  158,  159,  163,   18,   96,   96,  123,   96,   51,
   36,   36,   69,   80,   96,   80,   88,   92,   67,   91,
   73,   35,   95,   72,   71,   95,   95,   95,   95,   95,
   95,   78,   95,   71,  142,   87,  147,  161,  155,  134,
    0,   90,  138,   90,   95,   95,  137,   95,    0,    0,
    0,   96,    0,    0,    0,   96,   96,   96,   96,   96,
  164,   96,    0,    0,    0,    0,    0,    0,    0,   96,
    0,   96,    0,   96,   96,    0,   96,    0,   52,    0,
    0,    0,    0,    0,    0,   68,    0,    0,    0,    0,
   51,    0,   52,    0,    0,    0,    0,    0,   15,  140,
    0,   46,    0,    0,   51,    0,    0,    0,   95,    0,
   95,    0,    0,    0,  123,  123,    0,    0,    9,  102,
  103,    0,    0,   12,   13,   14,   15,   16,    0,   82,
   82,   82,   82,   82,   82,    9,    0,   96,    0,   96,
   12,   13,   14,   15,   16,   82,   83,   80,   80,   80,
   80,   80,   80,   80,    0,   80,   80,   80,    0,    0,
   80,   80,   80,   80,   80,   80,   80,   80,   80,   80,
   80,   80,   80,  124,  124,   90,   90,   90,   90,   90,
   90,   90,    0,   90,   90,   90,    0,    0,   90,   90,
   90,   90,   90,   90,   90,   90,   90,   90,   90,   90,
   90,   90,   90,   96,   96,   96,   96,   96,   96,   96,
    0,   96,   96,   96,  123,  123,   96,   96,   96,   96,
   96,   96,   96,   96,   96,   96,   96,   96,   96,  123,
  123,    9,    0,    0,    0,    0,   12,   13,   14,   15,
   16,    0,   95,   95,   95,   95,   95,   95,   95,    0,
   95,   95,   95,    0,    0,   95,   95,   95,   95,   95,
   95,   95,   95,   95,   95,   95,   95,   95,   64,    0,
    0,   96,   96,   96,   96,   96,   96,   96,    0,   96,
   96,   96,    0,    0,   96,   96,   96,   96,   96,   96,
   96,   96,   96,   96,   96,   96,   96,  104,    0,   40,
    0,    0,    0,    0,    0,   82,    0,    0,   64,    0,
   82,   82,    9,   82,   82,   82,   40,   12,   13,   14,
   15,   16,    0,  106,    0,   40,    9,   64,   82,    0,
   82,   12,   13,   14,   15,   16,    0,  104,  104,    0,
  104,  104,  104,    0,    0,    0,    0,    0,   40,    0,
    0,    0,    0,    0,  105,   41,  104,  104,    0,  104,
    0,    0,    0,  106,  106,    0,  106,  106,  106,    0,
    0,    0,   41,    0,    0,    0,    0,    0,    0,    0,
    0,   41,  106,  106,    0,  106,    0,    0,    0,    0,
  111,   64,    0,   64,  105,  105,    0,  105,  105,  105,
    0,    0,    0,    0,   41,    0,    0,    0,    0,    0,
    0,    0,    0,  105,  105,    0,  105,    0,    0,    0,
  104,    0,  104,    0,    0,   40,   40,   40,  112,    0,
  111,  111,    0,    0,  111,    0,    0,    0,   40,    0,
    0,    0,    0,    0,    0,    0,  106,    0,  106,  111,
  111,    0,  111,    0,    0,   40,   40,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  112,  112,
    0,    0,  112,    0,    0,    0,    0,  105,    0,  105,
    0,   41,   41,   41,    0,    0,    0,  112,  112,    0,
  112,    0,    0,    0,   41,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   18,
    0,   41,   41,  111,    0,  111,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   64,   64,   64,   64,   64,
   64,   64,    0,   64,   64,   64,    0,    0,   64,   64,
   64,   64,   64,   64,   64,   82,   82,   82,   82,   82,
   82,  112,    0,  112,  104,  104,  104,  104,  104,  104,
  104,    0,  104,  104,  104,    0,    0,  104,  104,  104,
  104,  104,  104,  104,  104,  104,  104,  104,  104,  104,
  106,  106,  106,  106,  106,  106,  106,    0,  106,  106,
  106,    0,   42,  106,  106,  106,  106,  106,  106,  106,
  106,  106,  106,  106,  106,  106,    0,    0,    0,   42,
    0,  105,  105,  105,  105,  105,  105,  105,   42,  105,
  105,  105,    0,    0,  105,  105,  105,  105,  105,  105,
  105,  105,  105,  105,  105,  105,  105,  113,    0,    0,
    0,   42,    0,    0,    0,    0,    0,  111,  111,  111,
  111,  111,  111,  111,    0,  111,  111,  111,    0,    0,
  111,  111,  111,  111,  111,  111,  111,  111,  111,  111,
  111,  111,  111,  114,    0,    0,    0,  113,  113,    0,
    0,  113,    0,    0,    0,  112,  112,  112,  112,  112,
  112,  112,    0,  112,  112,  112,  113,    0,  112,  112,
  112,  112,  112,  112,  112,  112,  112,  112,  112,  112,
  112,  115,    0,  114,  114,    0,    0,  114,   42,   42,
   42,   56,    0,   69,    0,    0,    0,    2,    3,    0,
    0,   42,  114,    0,   72,    0,    9,    0,  116,   10,
   11,   12,   13,   14,   15,   16,    0,    0,   42,   42,
    0,  115,  115,    0,    0,  115,    0,    0,    0,    0,
  113,    0,  113,    0,  117,    0,   91,    0,    0,    0,
  115,    0,    0,    0,    0,    0,    0,    0,  116,  116,
    0,    0,  116,    0,  112,    0,    0,    0,    0,    0,
    0,    0,    0,  120,    0,    0,  114,  116,  114,  118,
    0,  124,    0,    0,  117,  117,    0,    0,  117,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  117,    0,    0,    0,    0,    0,    0,
  141,    0,    0,  119,  115,    0,  115,    0,    0,  118,
  118,    0,    0,  118,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  153,    0,  118,    0,
  154,  116,  120,  116,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  119,  119,    0,    0,  119,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  117,    0,  117,
    0,    0,  119,    0,  113,  113,  113,  113,  113,  113,
  113,    0,  113,  113,  113,    0,    0,  113,  113,  113,
  113,  113,  113,  113,    0,    0,  113,  113,  113,  113,
    0,    0,  118,    0,  118,    0,    0,    0,    0,    0,
  114,  114,  114,  114,  114,  114,  114,    0,  114,  114,
  114,    0,    0,  114,  114,  114,  114,  114,  114,  114,
    0,    0,  114,  114,  114,  114,  119,    0,  119,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  115,  115,
  115,  115,  115,  115,  115,    0,  115,  115,  115,    0,
    0,  115,  115,  115,  115,  115,  115,  115,   19,    0,
  115,  115,  115,  115,    0,  116,  116,  116,  116,  116,
  116,  116,    0,  116,  116,  116,    0,    0,  116,  116,
  116,  116,  116,  116,  116,   41,    0,    0,    0,  116,
  116,  117,  117,  117,  117,  117,  117,  117,   19,  117,
  117,  117,   19,    0,  117,  117,  117,  117,  117,  117,
  117,    0,   57,    0,    0,  117,  117,   19,    0,    0,
    0,    0,    0,    0,    0,   41,  118,  118,  118,  118,
  118,  118,  118,    0,  118,  118,  118,    0,    0,  118,
  118,  118,  118,  118,  118,  118,  120,    0,    0,    0,
    0,  118,   57,   57,    0,    0,   57,    0,   81,    0,
  119,  119,  119,  119,  119,  119,  119,   20,  119,  119,
  119,   57,    0,  119,  119,  119,  119,  119,  119,  119,
    0,   19,    0,   19,    0,  119,  120,  120,   43,    0,
  120,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   93,   93,    0,  120,    0,   20,   41,    0,
   41,   20,    0,   58,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   20,    0,   43,    0,
    0,    0,    0,    0,    0,   57,    0,   57,    0,   59,
    0,    0,    0,    0,    0,    0,    0,   43,   93,   93,
   93,   93,   93,   58,    0,    0,    0,   93,   93,   93,
   93,   93,    0,    0,    0,    0,   41,    0,    0,  120,
    0,  120,   58,    0,    0,    0,    0,    0,    0,   59,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   20,    0,   20,    0,    0,    0,    0,    0,   59,    0,
    0,    0,    0,    0,    0,    0,   41,    0,    0,    0,
    0,   43,    0,   43,    0,   19,   19,   19,   19,   19,
   19,   19,    0,   19,   19,   19,    0,    0,   19,   19,
   19,   19,   19,   19,   19,    0,   58,    0,   58,    0,
    0,    0,   41,   41,   41,   41,   41,   41,   41,    0,
   41,   41,   41,    0,    0,   41,   41,   41,   41,   41,
   41,   41,   59,    0,   59,    0,    0,    0,    0,   57,
   57,   57,   57,   57,   57,   57,    0,   57,   57,   57,
    0,    0,   57,   57,   57,   57,   57,   57,   57,   41,
    0,   41,    0,   71,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  120,  120,  120,  120,  120,  120,  120,
    0,  120,  120,  120,    0,    0,  120,  120,  120,  120,
  120,  120,  120,    0,   20,   20,   20,   20,   20,   20,
   20,    0,   20,   20,   20,    0,    0,   20,   20,   20,
   20,   20,   20,   20,    0,   43,   43,   43,   43,   43,
   43,   43,   71,   43,   43,   43,    0,    0,   43,   43,
   43,   43,   43,   43,   43,    0,    0,    0,    0,    0,
   58,   58,   58,   18,   58,   58,   58,    0,   58,   58,
   58,    0,    0,   58,   58,   58,   58,   58,   58,   58,
    0,    0,   19,    0,    0,    0,   59,   59,   59,    0,
   59,   59,   59,    0,   59,   59,   59,    0,    0,   59,
   59,   59,   59,   59,   59,   59,   71,    0,   71,    0,
    0,    0,    0,   41,   41,   41,   41,   41,   41,   41,
    0,   41,   41,    0,    0,    0,   41,   41,   41,   41,
   41,   41,   41,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   17,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   23,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   23,    0,    0,    0,    0,    0,    0,
    0,    0,   23,    0,    0,    0,    0,    0,    0,    0,
   71,   71,   71,   71,   71,   71,   71,    0,   71,   71,
    0,    0,    0,   71,   71,   23,    0,   92,   92,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    1,    2,    3,    0,    4,    5,    6,    0,    7,    8,
    9,    0,    0,   10,   11,   12,   13,   14,   15,   16,
    0,    0,    0,   92,   92,   92,   92,   92,    0,    0,
    0,    0,   92,   92,   92,   92,   92,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  148,   23,   23,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   23,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  148,   23,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
    0,   40,   61,   59,   42,   43,    0,   45,   46,   47,
  123,   33,   60,   40,   62,    8,   30,  267,   40,   49,
   51,   52,   60,   45,   62,   39,   37,   43,    0,   45,
   59,   42,   46,   61,  279,  280,   47,   37,  100,  101,
   40,   41,   42,   43,   44,   45,   46,   47,   40,  107,
  108,   46,  281,   67,   46,  282,    0,  265,  125,   59,
   60,   61,   62,   56,   41,   37,   97,   98,   99,   41,
   42,   43,   44,   45,   46,   47,   69,   44,   91,  267,
  127,  128,  267,   59,   44,   41,  260,   59,   60,   61,
   62,   41,  139,   37,   93,    0,  126,   41,   42,   43,
   44,   45,   61,   47,   44,   41,   40,   33,   59,  260,
  157,   41,   41,   41,   40,   59,   60,   61,   62,   45,
    0,  125,   59,  123,    0,  125,  156,   41,   59,   41,
   41,  125,   37,   41,   59,   40,   41,   42,   43,   44,
   45,   26,   47,   17,  117,   49,  125,  156,  146,  106,
   -1,  123,  110,  125,   59,   60,  109,   62,   -1,   -1,
   -1,   37,   -1,   -1,   -1,   41,   42,   43,   44,   45,
  163,   47,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,
   -1,  125,   -1,   59,   60,   -1,   62,   -1,   33,   -1,
   -1,   -1,   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,
   45,   -1,   33,   -1,   -1,   -1,   -1,   -1,  267,   40,
   -1,  267,   -1,   -1,   45,   -1,   -1,   -1,  123,   -1,
  125,   -1,   -1,   -1,  283,  284,   -1,   -1,  267,  277,
  278,   -1,   -1,  272,  273,  274,  275,  276,   -1,  277,
  278,  279,  280,  281,  282,  267,   -1,  123,   -1,  125,
  272,  273,  274,  275,  276,  283,  284,  257,  258,  259,
  260,  261,  262,  263,   -1,  265,  266,  267,   -1,   -1,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  280,  281,  282,  283,  284,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,   -1,   -1,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  280,  281,
  282,  283,  284,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  283,  284,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  280,  281,  282,  283,
  284,  267,   -1,   -1,   -1,   -1,  272,  273,  274,  275,
  276,   -1,  257,  258,  259,  260,  261,  262,  263,   -1,
  265,  266,  267,   -1,   -1,  270,  271,  272,  273,  274,
  275,  276,  277,  278,  279,  280,  281,  282,    0,   -1,
   -1,  257,  258,  259,  260,  261,  262,  263,   -1,  265,
  266,  267,   -1,   -1,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  280,  281,  282,    0,   -1,    0,
   -1,   -1,   -1,   -1,   -1,   37,   -1,   -1,   40,   -1,
   42,   43,  267,   45,   46,   47,   17,  272,  273,  274,
  275,  276,   -1,    0,   -1,   26,  267,   59,   60,   -1,
   62,  272,  273,  274,  275,  276,   -1,   40,   41,   -1,
   43,   44,   45,   -1,   -1,   -1,   -1,   -1,   49,   -1,
   -1,   -1,   -1,   -1,    0,    0,   59,   60,   -1,   62,
   -1,   -1,   -1,   40,   41,   -1,   43,   44,   45,   -1,
   -1,   -1,   17,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   26,   59,   60,   -1,   62,   -1,   -1,   -1,   -1,
    0,  123,   -1,  125,   40,   41,   -1,   43,   44,   45,
   -1,   -1,   -1,   -1,   49,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   59,   60,   -1,   62,   -1,   -1,   -1,
  123,   -1,  125,   -1,   -1,  126,  127,  128,    0,   -1,
   40,   41,   -1,   -1,   44,   -1,   -1,   -1,  139,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,   59,
   60,   -1,   62,   -1,   -1,  156,  157,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   40,   41,
   -1,   -1,   44,   -1,   -1,   -1,   -1,  123,   -1,  125,
   -1,  126,  127,  128,   -1,   -1,   -1,   59,   60,   -1,
   62,   -1,   -1,   -1,  139,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   40,
   -1,  156,  157,  123,   -1,  125,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,   -1,   -1,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  280,  281,
  282,  123,   -1,  125,  257,  258,  259,  260,  261,  262,
  263,   -1,  265,  266,  267,   -1,   -1,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  280,  281,  282,
  257,  258,  259,  260,  261,  262,  263,   -1,  265,  266,
  267,   -1,    0,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  280,  281,  282,   -1,   -1,   -1,   17,
   -1,  257,  258,  259,  260,  261,  262,  263,   26,  265,
  266,  267,   -1,   -1,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  280,  281,  282,    0,   -1,   -1,
   -1,   49,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,  263,   -1,  265,  266,  267,   -1,   -1,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  280,  281,  282,    0,   -1,   -1,   -1,   40,   41,   -1,
   -1,   44,   -1,   -1,   -1,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,   59,   -1,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  280,  281,
  282,    0,   -1,   40,   41,   -1,   -1,   44,  126,  127,
  128,    5,   -1,    7,   -1,   -1,   -1,  258,  259,   -1,
   -1,  139,   59,   -1,   18,   -1,  267,   -1,    0,  270,
  271,  272,  273,  274,  275,  276,   -1,   -1,  156,  157,
   -1,   40,   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,
  123,   -1,  125,   -1,    0,   -1,   50,   -1,   -1,   -1,
   59,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   40,   41,
   -1,   -1,   44,   -1,   68,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   77,   -1,   -1,  123,   59,  125,    0,
   -1,   85,   -1,   -1,   40,   41,   -1,   -1,   44,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   59,   -1,   -1,   -1,   -1,   -1,   -1,
  114,   -1,   -1,    0,  123,   -1,  125,   -1,   -1,   40,
   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  140,   -1,   59,   -1,
  144,  123,  146,  125,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   40,   41,   -1,   -1,   44,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,
   -1,   -1,   59,   -1,  257,  258,  259,  260,  261,  262,
  263,   -1,  265,  266,  267,   -1,   -1,  270,  271,  272,
  273,  274,  275,  276,   -1,   -1,  279,  280,  281,  282,
   -1,   -1,  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,  263,   -1,  265,  266,
  267,   -1,   -1,  270,  271,  272,  273,  274,  275,  276,
   -1,   -1,  279,  280,  281,  282,  123,   -1,  125,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,  263,   -1,  265,  266,  267,   -1,
   -1,  270,  271,  272,  273,  274,  275,  276,    0,   -1,
  279,  280,  281,  282,   -1,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,   -1,   -1,  270,  271,
  272,  273,  274,  275,  276,    0,   -1,   -1,   -1,  281,
  282,  257,  258,  259,  260,  261,  262,  263,   40,  265,
  266,  267,   44,   -1,  270,  271,  272,  273,  274,  275,
  276,   -1,    0,   -1,   -1,  281,  282,   59,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   40,  257,  258,  259,  260,
  261,  262,  263,   -1,  265,  266,  267,   -1,   -1,  270,
  271,  272,  273,  274,  275,  276,    0,   -1,   -1,   -1,
   -1,  282,   40,   41,   -1,   -1,   44,   -1,   46,   -1,
  257,  258,  259,  260,  261,  262,  263,    0,  265,  266,
  267,   59,   -1,  270,  271,  272,  273,  274,  275,  276,
   -1,  123,   -1,  125,   -1,  282,   40,   41,    0,   -1,
   44,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   51,   52,   -1,   59,   -1,   40,  123,   -1,
  125,   44,   -1,    0,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   59,   -1,   40,   -1,
   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,   -1,    0,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   97,   98,
   99,  100,  101,   40,   -1,   -1,   -1,  106,  107,  108,
  109,  110,   -1,   -1,   -1,   -1,    0,   -1,   -1,  123,
   -1,  125,   59,   -1,   -1,   -1,   -1,   -1,   -1,   40,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,   59,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   40,   -1,   -1,   -1,
   -1,  123,   -1,  125,   -1,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,   -1,   -1,  270,  271,
  272,  273,  274,  275,  276,   -1,  123,   -1,  125,   -1,
   -1,   -1,  257,  258,  259,  260,  261,  262,  263,   -1,
  265,  266,  267,   -1,   -1,  270,  271,  272,  273,  274,
  275,  276,  123,   -1,  125,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,  263,   -1,  265,  266,  267,
   -1,   -1,  270,  271,  272,  273,  274,  275,  276,  123,
   -1,  125,   -1,    0,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,   -1,   -1,  270,  271,  272,  273,
  274,  275,  276,   -1,  257,  258,  259,  260,  261,  262,
  263,   -1,  265,  266,  267,   -1,   -1,  270,  271,  272,
  273,  274,  275,  276,   -1,  257,  258,  259,  260,  261,
  262,  263,   59,  265,  266,  267,   -1,   -1,  270,  271,
  272,  273,  274,  275,  276,   -1,   -1,   -1,   -1,   -1,
  257,  258,  259,   40,  261,  262,  263,   -1,  265,  266,
  267,   -1,   -1,  270,  271,  272,  273,  274,  275,  276,
   -1,   -1,   59,   -1,   -1,   -1,  257,  258,  259,   -1,
  261,  262,  263,   -1,  265,  266,  267,   -1,   -1,  270,
  271,  272,  273,  274,  275,  276,  123,   -1,  125,   -1,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,   -1,   -1,   -1,  270,  271,  272,  273,
  274,  275,  276,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,    0,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   17,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   26,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,  263,   -1,  265,  266,
   -1,   -1,   -1,  270,  271,   49,   -1,   51,   52,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  258,  259,   -1,  261,  262,  263,   -1,  265,  266,
  267,   -1,   -1,  270,  271,  272,  273,  274,  275,  276,
   -1,   -1,   -1,   97,   98,   99,  100,  101,   -1,   -1,
   -1,   -1,  106,  107,  108,  109,  110,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  126,  127,  128,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  139,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  156,  157,
};
}
final static short YYFINAL=20;
final static short YYMAXTOKEN=286;
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
null,null,null,null,null,null,null,null,null,"BREAK","FLOAT","INT","ELSE","FOR",
"IF","RETURN","VOID","WHILE","DO","IDENTIFIER","CLASSNAME","CLASS","STRING",
"BOOL","INTLIT","DOUBLELIT","STRINGLIT","BOOLLIT","NULLVAL","LESSTHANOREQUAL",
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
"FieldDecl : Type VarDecls StmtEnd",
"Type : INT",
"Type : FLOAT",
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
"BlockStmt : Stmt",
"StmtEnd : ';'",
"StmtEnd :",
"LocalVarDeclStmt : LocalVarDecl StmtEnd",
"LocalVarDecl : Type VarDecls",
"Stmt : Block",
"Stmt : ';'",
"Stmt : ExprStmt",
"Stmt : BreakStmt",
"Stmt : ReturnStmt",
"Stmt : IfStmt",
"Stmt : IfElseStmt",
"Stmt : DoWhileStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : LocalVarDeclStmt",
"ExprStmt : StmtExpr StmtEnd",
"StmtExpr : Assignment",
"StmtExpr : MethodCall",
"IfStmt : IF Expr Block",
"IfStmt : IF '(' Expr ')' Stmt",
"IfElseStmt : IF '(' Expr ')' Stmt ELSE Stmt",
"IfElseStmt : IF Expr Block ELSE Stmt",
"WhileStmt : WHILE '(' Expr ')' Stmt",
"WhileStmt : WHILE Expr Block",
"DoWhileStmt : DO Block WHILE '(' Expr ')'",
"DoWhileStmt : DO Block WHILE Expr",
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
"BreakStmt : BREAK StmtEnd",
"BreakStmt : BREAK IDENTIFIER StmtEnd",
"ReturnStmt : RETURN ExprOpt StmtEnd",
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
//#line 12 "j0gram.y"
{
    yyval=j0.node("BlockStmtsOpt",1024,val_peek(0));
    j0.print(yyval);
    j0.process((Tree) yyval.obj);
}
break;
case 2:
//#line 17 "j0gram.y"
{
  yyval=j0.node("ClassDecl",1000,val_peek(1),val_peek(0));
  j0.print(yyval);
 }
break;
case 3:
//#line 21 "j0gram.y"
{ yyval=j0.node("ClassBody",1010,val_peek(1)); }
break;
case 4:
//#line 22 "j0gram.y"
{ yyval=j0.node("ClassBody",1011); }
break;
case 6:
//#line 24 "j0gram.y"
{
  yyval=j0.node("ClassBodyDecls",1020,val_peek(1),val_peek(0)); }
break;
case 10:
//#line 27 "j0gram.y"
{
  yyval=j0.node("FieldDecl",1030,val_peek(2),val_peek(1)); }
break;
case 18:
//#line 32 "j0gram.y"
{
  yyval=j0.node("QualifiedName",1040,val_peek(2),val_peek(0));}
break;
case 20:
//#line 35 "j0gram.y"
{
  yyval=j0.node("VarDecls",1050,val_peek(2),val_peek(0)); }
break;
case 22:
//#line 37 "j0gram.y"
{
  yyval=j0.node("VarDeclarator",1060,val_peek(2)); }
break;
case 25:
//#line 41 "j0gram.y"
{
  yyval=j0.node("MethodDecl",1380,val_peek(1),val_peek(0));
 }
break;
case 26:
//#line 44 "j0gram.y"
{
  yyval=j0.node("MethodHeader",1070,val_peek(1),val_peek(0)); }
break;
case 27:
//#line 46 "j0gram.y"
{
  yyval=j0.node("MethodDeclarator",1080,val_peek(3),val_peek(1)); }
break;
case 31:
//#line 50 "j0gram.y"
{
  yyval=j0.node("FormalParmList",1090,val_peek(2),val_peek(0)); }
break;
case 32:
//#line 52 "j0gram.y"
{
  yyval=j0.node("FormalParm",1100,val_peek(1),val_peek(0));
 }
break;
case 33:
//#line 56 "j0gram.y"
{
  yyval=j0.node("ConstructorDecl",1110,val_peek(1),val_peek(0)); }
break;
case 34:
//#line 59 "j0gram.y"
{yyval=j0.node("Block",1200,val_peek(1));}
break;
case 38:
//#line 61 "j0gram.y"
{
  yyval=j0.node("BlockStmts",1130,val_peek(1),val_peek(0)); }
break;
case 43:
//#line 68 "j0gram.y"
{
  yyval=j0.node("LocalVarDecl",1140,val_peek(1),val_peek(0)); }
break;
case 58:
//#line 79 "j0gram.y"
{ yyval=j0.node("IfStmt",1150,val_peek(1),val_peek(0)); }
break;
case 59:
//#line 80 "j0gram.y"
{ yyval=j0.node("IfStmt",1151,val_peek(2),val_peek(0)); }
break;
case 60:
//#line 81 "j0gram.y"
{ yyval=j0.node("IfElseStmt",1160,val_peek(4),val_peek(2),val_peek(0)); }
break;
case 61:
//#line 82 "j0gram.y"
{ yyval=j0.node("IfElseStmt",1161,val_peek(3),val_peek(2),val_peek(0)); }
break;
case 62:
//#line 84 "j0gram.y"
{ yyval=j0.node("WhileStmt",1210,val_peek(2),val_peek(0)); }
break;
case 63:
//#line 85 "j0gram.y"
{ yyval=j0.node("WhileStmt",1211,val_peek(1),val_peek(0)); }
break;
case 64:
//#line 87 "j0gram.y"
{
    yyval=j0.node("DoWhileStmt",1212,val_peek(4),val_peek(1)); }
break;
case 65:
//#line 89 "j0gram.y"
{
        yyval=j0.node("DoWhileStmt",1213,val_peek(2),val_peek(0)); }
break;
case 66:
//#line 92 "j0gram.y"
{
  yyval=j0.node("ForStmt",1220,val_peek(6),val_peek(4),val_peek(2),val_peek(0)); }
break;
case 75:
//#line 98 "j0gram.y"
{
  yyval=j0.node("StmtExprList",1230,val_peek(2),val_peek(0)); }
break;
case 77:
//#line 101 "j0gram.y"
{
  yyval=j0.node("BreakStmt",1240,val_peek(1)); }
break;
case 78:
//#line 103 "j0gram.y"
{
  yyval=j0.node("ReturnStmt",1250,val_peek(1)); }
break;
case 82:
//#line 106 "j0gram.y"
{
  yyval=val_peek(1);}
break;
case 89:
//#line 110 "j0gram.y"
{
  yyval=j0.node("ArgList",1270,val_peek(2),val_peek(0)); }
break;
case 90:
//#line 112 "j0gram.y"
{
  yyval=j0.node("FieldAccess",1280,val_peek(2),val_peek(0)); }
break;
case 93:
//#line 116 "j0gram.y"
{
  yyval=j0.node("MethodCall",1290,val_peek(3),val_peek(1)); }
break;
case 94:
//#line 118 "j0gram.y"
{
    yyval=j0.node("MethodCall",1291,val_peek(5),val_peek(3),val_peek(1)); }
break;
case 97:
//#line 123 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1300,val_peek(1),val_peek(0)); }
break;
case 98:
//#line 125 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1301,val_peek(1),val_peek(0)); }
break;
case 101:
//#line 129 "j0gram.y"
{
      yyval=j0.node("MulExpr",1310,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 102:
//#line 131 "j0gram.y"
{
      yyval=j0.node("MulExpr",1311,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 103:
//#line 133 "j0gram.y"
{
      yyval=j0.node("MulExpr",1312,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 105:
//#line 136 "j0gram.y"
{
      yyval=j0.node("AddExpr",1320,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 106:
//#line 138 "j0gram.y"
{
      yyval=j0.node("AddExpr",1321,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 112:
//#line 141 "j0gram.y"
{
  yyval=j0.node("RelExpr",1330,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 114:
//#line 145 "j0gram.y"
{
  yyval=j0.node("EqExpr",1340,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 115:
//#line 147 "j0gram.y"
{
  yyval=j0.node("EqExpr",1341,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 117:
//#line 149 "j0gram.y"
{
  yyval=j0.node("CondAndExpr", 1350, val_peek(2),val_peek(1), val_peek(0)); }
break;
case 119:
//#line 151 "j0gram.y"
{
  yyval=j0.node("CondOrExpr", 1360, val_peek(2),val_peek(1), val_peek(0)); }
break;
case 122:
//#line 155 "j0gram.y"
{
yyval=j0.node("Assignment",1370, val_peek(2), val_peek(1), val_peek(0)); }
break;
//#line 1142 "Parser.java"
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
