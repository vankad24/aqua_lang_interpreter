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
public final static short RANGESEPARATOR=267;
public final static short IDENTIFIER=268;
public final static short CLASSNAME=269;
public final static short CLASS=270;
public final static short STRING=271;
public final static short BOOL=272;
public final static short FN=273;
public final static short INTLIT=274;
public final static short DOUBLELIT=275;
public final static short STRINGLIT=276;
public final static short BOOLLIT=277;
public final static short NULLVAL=278;
public final static short LESSTHANOREQUAL=279;
public final static short GREATERTHANOREQUAL=280;
public final static short ISEQUALTO=281;
public final static short NOTEQUALTO=282;
public final static short LOGICALAND=283;
public final static short LOGICALOR=284;
public final static short INCREMENT=285;
public final static short DECREMENT=286;
public final static short PUBLIC=287;
public final static short STATIC=288;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    2,    3,    3,    4,    4,    5,    5,    5,    6,
    8,    8,    8,    8,    8,   10,   10,   11,    9,    9,
   12,   12,   13,   13,    7,    7,   16,   16,   15,   15,
   17,   14,    1,    1,   18,   18,   19,   21,   21,   22,
   23,   20,   20,   20,   20,   20,   20,   20,   20,   20,
   20,   20,   20,   24,   32,   32,   27,   27,   28,   28,
   30,   30,   29,   29,   31,   31,   36,   36,   36,   37,
   38,   39,   40,   40,   43,   42,   41,   41,   44,   44,
   45,   45,   25,   25,   26,   46,   46,   46,   46,   47,
   47,   47,   47,   47,   49,   49,   48,   50,   50,   34,
   34,   51,   51,   52,   52,   52,   53,   53,   53,   53,
   54,   54,   54,   55,   55,   55,   55,   56,   56,   57,
   57,   57,   58,   58,   59,   59,   35,   33,   60,   60,
   61,   61,   61,
};
final static short yylen[] = {                            2,
    1,    4,    3,    2,    1,    2,    1,    1,    0,    2,
    1,    1,    1,    1,    1,    1,    1,    3,    1,    3,
    1,    3,    1,    0,    6,    5,    1,    0,    1,    3,
    2,    3,    1,    0,    1,    2,    1,    1,    0,    1,
    2,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    3,    5,    7,    5,
    5,    3,    6,    4,    5,    3,    1,    1,    1,    1,
    3,    5,    1,    1,    1,    3,    1,    1,    1,    0,
    1,    3,    2,    3,    3,    1,    1,    1,    3,    1,
    1,    1,    1,    1,    1,    3,    3,    1,    0,    4,
    3,    1,    1,    2,    2,    1,    1,    3,    3,    3,
    1,    3,    3,    1,    1,    1,    1,    1,    3,    1,
    3,    3,    1,    3,    1,    3,    1,    3,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,   12,   11,    0,    0,    0,    0,    0,   16,   14,
   13,    0,   90,   91,   93,   92,   94,    0,    0,   43,
    0,    1,   49,    0,    0,   17,   42,    0,   35,   37,
   53,   40,   44,   45,   46,   47,   48,   50,   51,   52,
   54,   55,    0,    0,   86,    0,    0,    0,   38,   83,
    0,    0,    0,    0,    0,   88,   70,    0,   67,   68,
   69,    0,   73,   74,    0,   87,  106,  107,    0,    0,
    0,    0,    0,    0,    0,    0,   79,    0,    0,    0,
    0,    0,    0,    0,   21,    0,    0,    0,    0,   36,
    0,  132,  133,  131,    0,   84,    0,    0,    0,  104,
  105,   66,   78,   77,    0,    0,    0,    0,    0,    0,
  114,  115,  116,  117,    0,    0,    0,    0,    0,    0,
    0,   85,    0,   62,    0,    0,   32,   89,    0,    0,
   18,  101,   95,    0,   97,  128,   76,    0,    0,  108,
  109,  110,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   64,    0,    0,    0,    0,   29,    0,
   22,    0,  100,   65,    0,    0,   60,   61,    0,   26,
    0,    0,    0,   96,   72,    0,    0,   30,   25,   59,
};
final static short yydgoto[] = {                         21,
   22,    0,    0,    0,    0,    0,   23,   24,   86,   55,
   26,   87,    0,   27,  158,    0,  159,   28,   29,   30,
   50,   31,   32,   33,   34,   35,   36,   37,   38,   39,
   40,   41,   42,   56,   57,   58,   59,   60,   61,   62,
  105,   63,   64,   78,    0,   65,   45,   66,  134,    0,
   67,   68,   69,   70,  115,   71,   72,   73,   74,   47,
   95,
};
final static short yysindex[] = {                      1391,
  -57,    0,    0,  148,  180,  208,  309, -109,    0,    0,
    0, -233,    0,    0,    0,    0,    0, 1391,  208,    0,
    0,    0,    0, -229,   -9,    0,    0, 1391,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    5,    0,    0,  -50,   -3,    0,    0,
   14,  520,  208,  208,   -9,    0,    0, -109,    0,    0,
    0,  -39,    0,    0,    5,    0,    0,    0,   67,  -11,
  -40, -264, -216, -215,  208, -109,    0,   -3,  208, -109,
 -188,   38,  -35,   44,    0,   48,   15, -172,  193,    0,
 -163,    0,    0,    0,  208,    0,  208,   44,   66,    0,
    0,    0,    0,    0,  208,  208,  208,  208,  208,  208,
    0,    0,    0,    0,  208,  208,  208,  208,  208,   69,
 -149,    0,   71,    0,  536,  303,    0,    0, -229,   20,
    0,    0,    0,   -8,    0,    0,    0, -109,   60,    0,
    0,    0,   67,   67,  -11,  -40,  -40, -264, -216, 1391,
 1391, 1391,  208,    0, -109, -229,   74,    6,    0,   15,
    0,  208,    0,    0,  208, -132,    0,    0,   88,    0,
   15, -136, -109,    0,    0, 1391,    0,    0,    0,    0,
};
final static short yyrindex[] = {                       130,
 1261,    0,    0,    0,    0, 1344,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    9,    0,    0,
    0,    0,    0,    0,  -54,    0,    0,   12,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, 1057,    0,    0,  -31,    0, 1213,    0,    0,
  -37,    0,    0,    0,   29,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,    0,    0,    0,   57,  419,
  480,  787,  847,  943,    0,    0,    0, 1213,    0,    0,
    0,    0,    0,    0,    0, 1167, 1117,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 1235,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -28,    0,
    0,    0,   99,  127,  447,  508,  759,  815,  875,  161,
    0,  161,    0,    0,    0,    0, -137,    0,    0, 1139,
    0,    0,    0,    0,    0, 1297,    0,    0,    0,    0,
    8,    0,    0,    0,    0,    0,  391,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  115,    0,    0,    0,    0,    0,    0, -110,    0,   40,
    0, -101,    0,   45,    0,    0,  -34,    0,  113,  -68,
  -24,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  446, 1606,   93,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  716,    0,  801,    0,    0,
    0,  -27,  -45,   31,    0,  -23,   30,   28,    0,    0,
    0,
};
final static int YYTABLESIZE=1771;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         16,
  102,   49,   16,   16,   16,   16,  129,   16,   16,   16,
   94,   33,   71,   18,   87,  156,  116,  117,  104,  113,
   75,  114,   16,   96,   16,  100,  101,  160,  103,  130,
   89,  110,  163,  109,   82,  162,   88,  102,   85,   25,
  102,  102,  102,  102,  102,  102,  173,  102,   31,  172,
   91,   31,   81,  122,  171,   49,  111,   25,  102,  102,
  102,  156,  102,  143,  144,  103,  118,   25,  119,  103,
  103,  103,  103,  103,   97,  103,  125,  126,  140,  141,
  142,  166,  167,  168,  128,   16,  103,  103,  103,  127,
  103,  129,  146,  147,   71,  131,  111,  111,  113,  111,
  111,  111,  102,  108,  135,  130,  138,  180,  106,  150,
  151,  152,  161,  107,  111,  111,  111,  165,  111,   88,
  121,    2,    3,  102,  124,  102,  112,  176,  177,   34,
   15,    9,   83,   34,   10,   11,   33,  178,  113,  113,
   90,  113,  113,  113,   99,  145,  149,  148,    0,    0,
    0,  103,    0,  103,    0,    0,  113,  113,  113,    0,
  113,    0,    0,    0,    0,  157,  112,  112,    0,  112,
  112,  112,    0,    0,    0,    0,    0,    0,    0,  111,
   54,  111,  164,    0,  112,  112,  112,   52,  112,   25,
   25,   25,   53,    0,    0,    0,    0,   89,    0,  170,
    0,    0,   89,   89,    0,   89,   89,   89,    0,    0,
   48,  157,   54,   15,    0,   25,    0,  179,    0,   75,
   89,  113,   89,  113,   53,   54,    0,  103,    0,   75,
  129,  129,   19,  132,   92,   93,    0,   53,  111,  112,
   54,   16,   16,   16,   16,   16,   16,   19,    0,  112,
    0,  112,   53,  130,  130,    0,    0,  102,  102,  102,
  102,  102,  102,  102,    0,  102,  102,  102,  102,    0,
    0,  102,  102,  102,  102,  102,  102,  102,  102,  102,
  102,  102,  102,  102,  102,  103,  103,  103,  103,  103,
  103,  103,    0,  103,  103,  103,  103,    0,    0,  103,
  103,  103,  103,  103,  103,  103,  103,  103,  103,  103,
  103,  103,  103,  111,  111,  111,  111,  111,  111,  111,
    0,  111,  111,  111,  111,    0,    0,  111,  111,  111,
  111,  111,  111,  111,  111,  111,  111,  111,  111,  111,
  111,   54,    0,  155,    0,    0,    0,    0,   79,    0,
    0,    0,    0,   53,    0,  113,  113,  113,  113,  113,
  113,  113,    0,  113,  113,  113,  113,    0,    0,  113,
  113,  113,  113,  113,  113,  113,  113,  113,  113,  113,
  113,  113,  113,  112,  112,  112,  112,  112,  112,  112,
   63,  112,  112,  112,  112,    0,    0,  112,  112,  112,
  112,  112,  112,  112,  112,  112,  112,  112,  112,  112,
  112,    0,    0,    0,    0,   51,    0,    0,  118,    0,
    0,   13,   14,   15,   16,   17,    0,   89,    0,    0,
   63,    0,   89,   89,    0,   89,   89,   89,    0,   89,
   89,   89,   89,   89,   89,   43,  119,    9,    0,   63,
   89,    0,   89,   13,   14,   15,   16,   17,  118,  118,
    9,    0,  118,   43,    0,    0,   13,   14,   15,   16,
   17,    0,    0,   43,    0,    9,  118,  118,  118,  120,
  118,   13,   14,   15,   16,   17,  119,  119,    0,    0,
  119,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  119,  119,  119,  121,  119,    0,
    0,    0,    0,   63,    0,   63,    0,    0,    0,  120,
  120,    0,    0,  120,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  120,  120,    0,
    0,  118,    0,  118,    0,    0,    0,  121,  121,    0,
    0,  121,   54,    0,    0,    0,    0,    0,    0,   19,
    2,    3,    0,    0,   53,  121,  121,    0,   54,  119,
    9,  119,    0,   10,   11,  153,    9,    0,    0,    0,
   53,    0,   13,   14,   15,   16,   17,    0,    0,    0,
    0,    0,    0,    0,    0,   43,   43,   43,    0,    0,
    0,    0,  120,    0,  120,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   43,    0,    0,    0,    0,    0,    0,    0,    0,
  121,    0,  121,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   63,   63,   63,
   63,   63,   63,   63,    0,   63,   63,    0,   63,    0,
    0,   63,   63,   63,   63,   63,   63,   63,   63,   89,
   89,   89,   89,   89,   89,  118,  118,  118,  118,  118,
  118,  118,    0,  118,  118,  118,  118,    0,    0,  118,
  118,  118,  118,  118,  118,  118,  118,  118,  118,  118,
  118,  118,  118,  119,  119,  119,  119,  119,  119,  119,
    0,  119,  119,  119,  119,   44,    0,  119,  119,  119,
  119,  119,  119,  119,  119,  119,  119,  119,  119,  119,
  119,    0,    0,   44,    0,    0,  120,  120,  120,  120,
  120,  120,  120,   44,  120,  120,  120,  120,    0,    0,
  120,  120,  120,  120,  120,  120,  120,  120,  122,    0,
  120,  120,  120,  120,  121,  121,  121,  121,  121,  121,
  121,    0,  121,  121,  121,  121,    0,    0,  121,  121,
  121,  121,  121,  121,  121,  121,  123,   51,  121,  121,
  121,  121,    0,   13,   14,   15,   16,   17,  122,  122,
   46,    0,  122,    9,    0,    0,    0,    0,    0,   13,
   14,   15,   16,   17,  124,    0,  122,  122,   46,    0,
    0,    0,    0,    0,    0,    0,  123,  123,   46,    0,
  123,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  123,  123,  125,    0,    0,    0,
    0,    0,    0,    0,  124,  124,    0,    0,  124,    0,
    0,    0,    0,    0,    0,   44,   44,   44,    0,    0,
    0,    0,  124,  124,  126,    0,    0,    0,    0,    0,
    0,  122,    0,  122,    0,    0,  125,  125,    0,    0,
  125,   44,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  125,  125,    0,    0,    0,  123,
    0,  123,    0,    0,  126,  126,    0,    0,  126,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  126,  126,    0,    0,    0,  124,    0,  124,
    0,    0,  127,    0,    0,    0,    0,    0,    0,    0,
   46,   46,   46,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  125,
    0,  125,    0,    0,    0,    0,   46,    0,    0,    0,
    0,    0,  127,  127,    0,    0,  127,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  126,    0,  126,
  127,  127,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  122,  122,  122,  122,  122,
  122,  122,    0,  122,  122,  122,  122,    0,    0,  122,
  122,  122,  122,  122,  122,  122,  122,    0,    0,  122,
  122,  122,  122,  123,  123,  123,  123,  123,  123,  123,
    0,  123,  123,  123,  123,    0,   56,  123,  123,  123,
  123,  123,  123,  123,  123,  127,    0,  127,    0,  123,
  123,  124,  124,  124,  124,  124,  124,  124,    0,  124,
  124,  124,  124,    0,    0,  124,  124,  124,  124,  124,
  124,  124,  124,    0,    0,    0,   56,  124,  124,    0,
    0,    0,   88,  125,  125,  125,  125,  125,  125,  125,
    0,  125,  125,  125,  125,   56,   19,  125,  125,  125,
  125,  125,  125,  125,  125,    0,    0,    0,    0,    0,
  125,  126,  126,  126,  126,  126,  126,  126,   20,  126,
  126,  126,  126,    0,    0,  126,  126,  126,  126,  126,
  126,  126,  126,    0,    0,    0,   19,    0,  126,    0,
   19,    0,    0,    0,    0,    0,   41,    0,    0,    0,
    0,    0,    0,    0,    0,   19,    0,    0,   20,   56,
    0,   56,   20,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   20,    0,  127,
  127,  127,  127,  127,  127,  127,   41,  127,  127,  127,
  127,    0,   39,  127,  127,  127,  127,  127,  127,  127,
  127,    0,    0,    0,    0,   41,    0,    0,    0,    0,
    0,    0,    0,    0,   57,    0,    0,    0,    0,   19,
    0,   19,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   39,    0,    0,    0,    0,    0,    0,    0,
   39,   20,    0,   20,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   57,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   41,
    0,   41,    0,   57,    0,    0,   58,    0,    0,    0,
   39,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   56,   56,   56,   56,   56,   56,   56,
    0,   56,   56,    0,   56,    0,    0,   56,   56,   56,
   56,   56,   56,   56,   56,   39,   58,   39,    0,    0,
    0,    0,    0,   80,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   58,    0,   57,    0,   57,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   19,   19,   19,   19,   19,   19,   19,
    0,   19,   19,   39,   19,   39,    0,   19,   19,   19,
   19,   19,   19,   19,   19,   20,   20,   20,   20,   20,
   20,   20,   80,   20,   20,    0,   20,    0,    0,   20,
   20,   20,   20,   20,   20,   20,   20,    0,    0,   58,
    0,   58,    0,   41,   41,   41,   41,   41,   41,   41,
   19,   41,   41,    0,   41,    0,    0,   41,   41,   41,
   41,   41,   41,   41,   41,    0,    0,    0,    0,   20,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   80,    0,   80,   39,
   39,   39,   39,   39,   39,   39,    0,   39,   39,    0,
   39,    0,    0,   39,   39,   39,   39,   39,   39,   39,
   39,   57,   57,   57,    0,   57,   57,   57,    0,   57,
   57,    0,   57,    0,    0,   57,   57,   57,   57,   57,
   57,   57,   57,   18,    0,    0,    0,   39,   39,   39,
   39,   39,   39,   39,    0,   39,   39,    0,    0,    0,
    0,   39,   39,   39,   39,   39,   39,   39,   39,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   58,   58,   58,    0,   58,   58,   58,
    0,   58,   58,    0,   58,    0,    0,   58,   58,   58,
   58,   58,   58,   58,   58,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   80,   80,   80,   80,   80,   80,   80,    0,   80,   80,
   76,   77,   80,    0,   80,   80,   80,    0,    0,    0,
    0,    0,    0,    0,   84,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    1,    2,    3,
    0,    4,    5,    6,    0,    7,    8,   98,    9,    0,
    0,   10,   11,   12,   13,   14,   15,   16,   17,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  120,    0,    0,    0,  123,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  133,    0,    0,    0,    0,    0,
  136,    0,  137,    0,    0,    0,    0,    0,    0,    0,
  139,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  154,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  169,    0,
    0,    0,    0,    0,    0,    0,    0,  174,    0,    0,
  175,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
    0,   59,   40,   41,   42,   43,   61,   45,   46,   47,
   61,    0,   41,  123,   46,  126,  281,  282,   58,   60,
   58,   62,   60,   48,   62,   53,   54,  129,    0,   61,
   40,   43,   41,   45,  268,   44,   46,   37,  268,    0,
   40,   41,   42,   43,   44,   45,   41,   47,   41,   44,
   46,   44,    8,   78,  156,   59,    0,   18,   58,   59,
   60,  172,   62,  109,  110,   37,  283,   28,  284,   41,
   42,   43,   44,   45,   61,   47,  265,   40,  106,  107,
  108,  150,  151,  152,   41,  123,   58,   59,   60,  125,
   62,   44,  116,  117,  123,  268,   40,   41,    0,   43,
   44,   45,   58,   37,  268,   91,   41,  176,   42,   41,
  260,   41,   93,   47,   58,   59,   60,   58,   62,   46,
   76,  258,  259,  123,   80,  125,    0,  260,   41,    0,
  268,  268,   18,  125,  271,  272,  125,  172,   40,   41,
   28,   43,   44,   45,   52,  115,  119,  118,   -1,   -1,
   -1,  123,   -1,  125,   -1,   -1,   58,   59,   60,   -1,
   62,   -1,   -1,   -1,   -1,  126,   40,   41,   -1,   43,
   44,   45,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,
   33,  125,  138,   -1,   58,   59,   60,   40,   62,  150,
  151,  152,   45,   -1,   -1,   -1,   -1,   37,   -1,  155,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,
  268,  172,   33,  268,   -1,  176,   -1,  173,   -1,   40,
   60,  123,   62,  125,   45,   33,   -1,  267,   -1,  267,
  285,  286,   40,   41,  285,  286,   -1,   45,  279,  280,
   33,  279,  280,  281,  282,  283,  284,   40,   -1,  123,
   -1,  125,   45,  285,  286,   -1,   -1,  257,  258,  259,
  260,  261,  262,  263,   -1,  265,  266,  267,  268,   -1,
   -1,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  280,  281,  282,  283,  284,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,  268,   -1,   -1,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  280,  281,
  282,  283,  284,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,   -1,   -1,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  280,  281,  282,  283,
  284,   33,   -1,   41,   -1,   -1,   -1,   -1,   40,   -1,
   -1,   -1,   -1,   45,   -1,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,  268,   -1,   -1,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  280,  281,
  282,  283,  284,  257,  258,  259,  260,  261,  262,  263,
    0,  265,  266,  267,  268,   -1,   -1,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  280,  281,  282,  283,
  284,   -1,   -1,   -1,   -1,  268,   -1,   -1,    0,   -1,
   -1,  274,  275,  276,  277,  278,   -1,   37,   -1,   -1,
   40,   -1,   42,   43,   -1,   45,   46,   47,   -1,  279,
  280,  281,  282,  283,  284,    0,    0,  268,   -1,   59,
   60,   -1,   62,  274,  275,  276,  277,  278,   40,   41,
  268,   -1,   44,   18,   -1,   -1,  274,  275,  276,  277,
  278,   -1,   -1,   28,   -1,  268,   58,   59,   60,    0,
   62,  274,  275,  276,  277,  278,   40,   41,   -1,   -1,
   44,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   58,   59,   60,    0,   62,   -1,
   -1,   -1,   -1,  123,   -1,  125,   -1,   -1,   -1,   40,
   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,   -1,
   -1,  123,   -1,  125,   -1,   -1,   -1,   40,   41,   -1,
   -1,   44,   33,   -1,   -1,   -1,   -1,   -1,   -1,   40,
  258,  259,   -1,   -1,   45,   58,   59,   -1,   33,  123,
  268,  125,   -1,  271,  272,   40,  268,   -1,   -1,   -1,
   45,   -1,  274,  275,  276,  277,  278,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  150,  151,  152,   -1,   -1,
   -1,   -1,  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  176,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,  263,   -1,  265,  266,   -1,  268,   -1,
   -1,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  280,  281,  282,  283,  284,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,  268,   -1,   -1,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  280,  281,
  282,  283,  284,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,    0,   -1,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  280,  281,  282,  283,
  284,   -1,   -1,   18,   -1,   -1,  257,  258,  259,  260,
  261,  262,  263,   28,  265,  266,  267,  268,   -1,   -1,
  271,  272,  273,  274,  275,  276,  277,  278,    0,   -1,
  281,  282,  283,  284,  257,  258,  259,  260,  261,  262,
  263,   -1,  265,  266,  267,  268,   -1,   -1,  271,  272,
  273,  274,  275,  276,  277,  278,    0,  268,  281,  282,
  283,  284,   -1,  274,  275,  276,  277,  278,   40,   41,
    0,   -1,   44,  268,   -1,   -1,   -1,   -1,   -1,  274,
  275,  276,  277,  278,    0,   -1,   58,   59,   18,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   40,   41,   28,   -1,
   44,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   58,   59,    0,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   40,   41,   -1,   -1,   44,   -1,
   -1,   -1,   -1,   -1,   -1,  150,  151,  152,   -1,   -1,
   -1,   -1,   58,   59,    0,   -1,   -1,   -1,   -1,   -1,
   -1,  123,   -1,  125,   -1,   -1,   40,   41,   -1,   -1,
   44,  176,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   58,   59,   -1,   -1,   -1,  123,
   -1,  125,   -1,   -1,   40,   41,   -1,   -1,   44,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   58,   59,   -1,   -1,   -1,  123,   -1,  125,
   -1,   -1,    0,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  150,  151,  152,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,
   -1,  125,   -1,   -1,   -1,   -1,  176,   -1,   -1,   -1,
   -1,   -1,   40,   41,   -1,   -1,   44,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,
   58,   59,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,  268,   -1,   -1,  271,
  272,  273,  274,  275,  276,  277,  278,   -1,   -1,  281,
  282,  283,  284,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,   -1,    0,  271,  272,  273,
  274,  275,  276,  277,  278,  123,   -1,  125,   -1,  283,
  284,  257,  258,  259,  260,  261,  262,  263,   -1,  265,
  266,  267,  268,   -1,   -1,  271,  272,  273,  274,  275,
  276,  277,  278,   -1,   -1,   -1,   40,  283,  284,   -1,
   -1,   -1,   46,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,   59,    0,  271,  272,  273,
  274,  275,  276,  277,  278,   -1,   -1,   -1,   -1,   -1,
  284,  257,  258,  259,  260,  261,  262,  263,    0,  265,
  266,  267,  268,   -1,   -1,  271,  272,  273,  274,  275,
  276,  277,  278,   -1,   -1,   -1,   40,   -1,  284,   -1,
   44,   -1,   -1,   -1,   -1,   -1,    0,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   59,   -1,   -1,   40,  123,
   -1,  125,   44,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   -1,  257,
  258,  259,  260,  261,  262,  263,   40,  265,  266,  267,
  268,   -1,    0,  271,  272,  273,  274,  275,  276,  277,
  278,   -1,   -1,   -1,   -1,   59,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,    0,   -1,   -1,   -1,   -1,  123,
   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   40,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
    0,  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,
   -1,  125,   -1,   59,   -1,   -1,    0,   -1,   -1,   -1,
   40,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,   -1,  268,   -1,   -1,  271,  272,  273,
  274,  275,  276,  277,  278,  123,   40,  125,   -1,   -1,
   -1,   -1,   -1,    0,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   59,   -1,  123,   -1,  125,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  123,  268,  125,   -1,  271,  272,  273,
  274,  275,  276,  277,  278,  257,  258,  259,  260,  261,
  262,  263,   59,  265,  266,   -1,  268,   -1,   -1,  271,
  272,  273,  274,  275,  276,  277,  278,   -1,   -1,  123,
   -1,  125,   -1,  257,  258,  259,  260,  261,  262,  263,
   40,  265,  266,   -1,  268,   -1,   -1,  271,  272,  273,
  274,  275,  276,  277,  278,   -1,   -1,   -1,   -1,   59,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,  257,
  258,  259,  260,  261,  262,  263,   -1,  265,  266,   -1,
  268,   -1,   -1,  271,  272,  273,  274,  275,  276,  277,
  278,  257,  258,  259,   -1,  261,  262,  263,   -1,  265,
  266,   -1,  268,   -1,   -1,  271,  272,  273,  274,  275,
  276,  277,  278,  123,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,  263,   -1,  265,  266,   -1,   -1,   -1,
   -1,  271,  272,  273,  274,  275,  276,  277,  278,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,  258,  259,   -1,  261,  262,  263,
   -1,  265,  266,   -1,  268,   -1,   -1,  271,  272,  273,
  274,  275,  276,  277,  278,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,  263,   -1,  265,  266,
    5,    6,    7,   -1,  271,  272,  273,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   19,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
   -1,  261,  262,  263,   -1,  265,  266,   52,  268,   -1,
   -1,  271,  272,  273,  274,  275,  276,  277,  278,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   75,   -1,   -1,   -1,   79,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   89,   -1,   -1,   -1,   -1,   -1,
   95,   -1,   97,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  105,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  153,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  162,   -1,   -1,
  165,
};
}
final static short YYFINAL=21;
final static short YYMAXTOKEN=288;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
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
"IF","RETURN","VOID","WHILE","DO","RANGESEPARATOR","IDENTIFIER","CLASSNAME",
"CLASS","STRING","BOOL","FN","INTLIT","DOUBLELIT","STRINGLIT","BOOLLIT",
"NULLVAL","LESSTHANOREQUAL","GREATERTHANOREQUAL","ISEQUALTO","NOTEQUALTO",
"LOGICALAND","LOGICALOR","INCREMENT","DECREMENT","PUBLIC","STATIC",
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
"ClassBodyDecl :",
"FieldDecl : Type VarDecls",
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
"BlockOpt : Block",
"BlockOpt :",
"MethodDecl : FN IDENTIFIER '(' FormalParmList ')' Block",
"MethodDecl : FN IDENTIFIER '(' ')' Block",
"FormalParmListOpt : FormalParmList",
"FormalParmListOpt :",
"FormalParmList : FormalParm",
"FormalParmList : FormalParmList ',' FormalParm",
"FormalParm : Type VarDeclarator",
"Block : '{' BlockStmtsOpt '}'",
"BlockStmtsOpt : BlockStmts",
"BlockStmtsOpt :",
"BlockStmts : BlockStmt",
"BlockStmts : BlockStmts BlockStmt",
"BlockStmt : Stmt",
"StmtEnd : ';'",
"StmtEnd :",
"LocalVarDeclStmt : LocalVarDecl",
"LocalVarDecl : Type VarDecls",
"Stmt : Block",
"Stmt : ';'",
"Stmt : ExprStmt",
"Stmt : BreakStmt",
"Stmt : ReturnStmt",
"Stmt : IfStmt",
"Stmt : IfElseStmt",
"Stmt : MethodDecl",
"Stmt : DoWhileStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : LocalVarDeclStmt",
"ExprStmt : StmtExpr",
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
"ForStmt : FOR '(' ForHeader ')' Block",
"ForStmt : FOR ForHeader Block",
"ForHeader : ForShort",
"ForHeader : ForNormal",
"ForHeader : ForFull",
"ForShort : Expr",
"ForNormal : ForInit ForSeparator Expr",
"ForFull : ForInit ForSeparator Expr ':' Expr",
"ForInit : ForVarInit",
"ForInit : ForVar",
"ForVar : IDENTIFIER",
"ForVarInit : IDENTIFIER '=' Expr",
"ForSeparator : ':'",
"ForSeparator : RANGESEPARATOR",
"ExprOpt : Expr",
"ExprOpt :",
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
"MethodCall : Name '(' ArgList ')'",
"MethodCall : Name '(' ')'",
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
  yyval=j0.node("FieldDecl",1030,val_peek(1),val_peek(0)); }
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
//#line 42 "j0gram.y"
{ yyval=j0.node("MethodDecl",1380,val_peek(4),val_peek(2),val_peek(0)); }
break;
case 26:
//#line 43 "j0gram.y"
{ yyval=j0.node("MethodDecl",1381,val_peek(3),val_peek(0)); }
break;
case 30:
//#line 46 "j0gram.y"
{
  yyval=j0.node("FormalParmList",1090,val_peek(2),val_peek(0)); }
break;
case 31:
//#line 48 "j0gram.y"
{
  yyval=j0.node("FormalParm",1100,val_peek(1),val_peek(0));
 }
break;
case 32:
//#line 52 "j0gram.y"
{yyval=j0.node("Block",1200,val_peek(1));}
break;
case 36:
//#line 54 "j0gram.y"
{
  yyval=j0.node("BlockStmts",1130,val_peek(1),val_peek(0)); }
break;
case 41:
//#line 61 "j0gram.y"
{
  yyval=j0.node("LocalVarDecl",1140,val_peek(1),val_peek(0)); }
break;
case 57:
//#line 72 "j0gram.y"
{ yyval=j0.node("IfStmt",1150,val_peek(1),val_peek(0)); }
break;
case 58:
//#line 73 "j0gram.y"
{ yyval=j0.node("IfStmt",1151,val_peek(2),val_peek(0)); }
break;
case 59:
//#line 74 "j0gram.y"
{ yyval=j0.node("IfElseStmt",1160,val_peek(4),val_peek(2),val_peek(0)); }
break;
case 60:
//#line 75 "j0gram.y"
{ yyval=j0.node("IfElseStmt",1161,val_peek(3),val_peek(2),val_peek(0)); }
break;
case 61:
//#line 77 "j0gram.y"
{ yyval=j0.node("WhileStmt",1210,val_peek(2),val_peek(0)); }
break;
case 62:
//#line 78 "j0gram.y"
{ yyval=j0.node("WhileStmt",1211,val_peek(1),val_peek(0)); }
break;
case 63:
//#line 80 "j0gram.y"
{
    yyval=j0.node("DoWhileStmt",1212,val_peek(4),val_peek(1)); }
break;
case 64:
//#line 82 "j0gram.y"
{
        yyval=j0.node("DoWhileStmt",1213,val_peek(2),val_peek(0)); }
break;
case 65:
//#line 103 "j0gram.y"
{ yyval=j0.node("ForStmt",1220,val_peek(2),val_peek(0)); }
break;
case 66:
//#line 104 "j0gram.y"
{ yyval=j0.node("ForStmt",1221,val_peek(1),val_peek(0)); }
break;
case 71:
//#line 109 "j0gram.y"
{ yyval=j0.node("ForNormal",1222,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 72:
//#line 110 "j0gram.y"
{ yyval=j0.node("ForFull",1223,val_peek(4),val_peek(3),val_peek(2),val_peek(0)); }
break;
case 76:
//#line 114 "j0gram.y"
{ yyval=j0.node("ForVarInit",1224,val_peek(2),val_peek(0)); }
break;
case 82:
//#line 119 "j0gram.y"
{
  yyval=j0.node("StmtExprList",1230,val_peek(2),val_peek(0)); }
break;
case 84:
//#line 122 "j0gram.y"
{
  yyval=j0.node("BreakStmt",1240,val_peek(1)); }
break;
case 85:
//#line 124 "j0gram.y"
{
  yyval=j0.node("ReturnStmt",1250,val_peek(1)); }
break;
case 89:
//#line 127 "j0gram.y"
{
  yyval=val_peek(1);}
break;
case 96:
//#line 131 "j0gram.y"
{
  yyval=j0.node("ArgList",1270,val_peek(2),val_peek(0)); }
break;
case 97:
//#line 133 "j0gram.y"
{
  yyval=j0.node("FieldAccess",1280,val_peek(2),val_peek(0)); }
break;
case 100:
//#line 137 "j0gram.y"
{ yyval=j0.node("MethodCall",1290,val_peek(3),val_peek(1)); }
break;
case 101:
//#line 138 "j0gram.y"
{ yyval=j0.node("MethodCall",1291,val_peek(2)); }
break;
case 104:
//#line 143 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1300,val_peek(1),val_peek(0)); }
break;
case 105:
//#line 145 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1301,val_peek(1),val_peek(0)); }
break;
case 108:
//#line 149 "j0gram.y"
{
      yyval=j0.node("MulExpr",1310,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 109:
//#line 151 "j0gram.y"
{
      yyval=j0.node("MulExpr",1311,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 110:
//#line 153 "j0gram.y"
{
      yyval=j0.node("MulExpr",1312,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 112:
//#line 156 "j0gram.y"
{
      yyval=j0.node("AddExpr",1320,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 113:
//#line 158 "j0gram.y"
{
      yyval=j0.node("AddExpr",1321,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 119:
//#line 161 "j0gram.y"
{
  yyval=j0.node("RelExpr",1330,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 121:
//#line 165 "j0gram.y"
{
  yyval=j0.node("EqExpr",1340,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 122:
//#line 167 "j0gram.y"
{
  yyval=j0.node("EqExpr",1341,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 124:
//#line 169 "j0gram.y"
{
  yyval=j0.node("CondAndExpr", 1350, val_peek(2),val_peek(1), val_peek(0)); }
break;
case 126:
//#line 171 "j0gram.y"
{
  yyval=j0.node("CondOrExpr", 1360, val_peek(2),val_peek(1), val_peek(0)); }
break;
case 128:
//#line 175 "j0gram.y"
{
yyval=j0.node("Assignment",1370, val_peek(2), val_peek(1), val_peek(0)); }
break;
//#line 1149 "Parser.java"
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
