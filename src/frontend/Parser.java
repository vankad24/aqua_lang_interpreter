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
   12,   12,   13,   13,    7,    7,   16,   16,   17,   17,
   19,   19,   20,   20,   15,   15,   21,   14,    1,    1,
   22,   22,   23,   25,   25,   26,   27,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   28,
   36,   36,   31,   31,   32,   32,   34,   34,   33,   33,
   35,   35,   38,   38,   38,   39,   40,   41,   42,   42,
   45,   44,   43,   43,   46,   46,   47,   47,   29,   29,
   30,   30,   48,   48,   48,   48,   49,   49,   49,   49,
   49,   50,   51,   51,   52,   52,   52,   53,   53,   53,
   53,   54,   54,   54,   55,   55,   55,   55,   56,   56,
   57,   57,   57,   58,   58,   59,   59,   18,   37,   60,
   60,   61,   61,   61,
};
final static short yylen[] = {                            2,
    1,    4,    3,    2,    1,    2,    1,    1,    0,    2,
    1,    1,    1,    1,    1,    1,    1,    3,    1,    3,
    1,    3,    1,    0,    6,    5,    1,    0,    1,    3,
    4,    3,    1,    0,    1,    3,    2,    3,    1,    0,
    1,    2,    1,    1,    0,    1,    2,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    3,    5,    7,    5,    5,    3,    6,    4,
    5,    3,    1,    1,    1,    1,    3,    5,    1,    1,
    1,    3,    1,    1,    1,    0,    1,    3,    2,    3,
    2,    1,    1,    1,    1,    3,    1,    1,    1,    1,
    1,    3,    1,    1,    2,    2,    1,    1,    3,    3,
    3,    1,    3,    3,    1,    1,    1,    1,    1,    3,
    1,    3,    3,    1,    3,    1,    3,    1,    3,    1,
    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,   12,   11,    0,    0,    0,    0,    0,   16,   14,
   13,    0,   97,   98,  100,   99,  101,    0,    0,   49,
    0,    1,   55,    0,    0,   17,   48,    0,    0,   41,
   43,   59,   46,   50,   51,   52,   53,   54,   56,   57,
   58,   60,   61,    0,   93,    0,    0,    0,   44,   89,
    0,    0,    0,    0,    0,   76,   95,    0,   73,   74,
   75,    0,   79,   80,    0,   94,  107,  108,    0,    0,
    0,    0,    0,    0,    0,    0,   91,    0,    0,    0,
    0,    0,    0,   21,    0,    0,    0,    0,   42,    0,
  133,  134,  132,    0,   90,    0,    0,    0,  105,  106,
   72,   84,   83,    0,    0,    0,    0,    0,    0,  115,
  116,  117,  118,    0,    0,    0,    0,    0,    0,    0,
    0,   68,    0,    0,   38,   96,    0,    0,   18,   32,
    0,   29,  102,  129,   82,    0,    0,  109,  110,  111,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   70,    0,    0,    0,    0,   35,    0,   22,    0,
   31,   71,    0,    0,   66,   67,    0,   26,    0,    0,
    0,   30,   78,    0,    0,   36,   25,   65,
};
final static short yydgoto[] = {                         21,
   22,    0,    0,    0,    0,    0,   23,   24,   85,   55,
   26,   86,    0,   27,  156,    0,  131,   56,   57,    0,
  157,   29,   30,   31,   50,   32,   33,   34,   35,   36,
   37,   38,   39,   40,   41,   42,   43,   58,   59,   60,
   61,   62,  104,   63,   64,    0,    0,   65,   45,   66,
   67,   68,   69,   70,  114,   71,   72,   73,   74,   47,
   94,
};
final static short yysindex[] = {                      1391,
  -57,    0,    0,  148,  180,  208,  309, -109,    0,    0,
    0, -240,    0,    0,    0,    0,    0, 1391,  208,    0,
    0,    0,    0, -213,   -9,    0,    0,    0, 1391,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   21,    0,    0,  -50,   10,    0,    0,
   14,  520,  208,  208,   -9,    0,    0, -109,    0,    0,
    0,  -39,    0,    0,   21,    0,    0,    0,   66,   11,
  -40, -264, -206, -199,  208, -109,    0,  208, -109, -175,
   52,  -32,   53,    0,   60,    5, -163,  193,    0, -162,
    0,    0,    0,  208,    0,  208,   53,   68,    0,    0,
    0,    0,    0,  208,  208,  208,  208,  208,  208,    0,
    0,    0,    0,  208,  208,  208,  208,  208,   69, -153,
   70,    0,  536,  303,    0,    0, -213,   19,    0,    0,
    6,    0,    0,    0,    0, -109,   62,    0,    0,    0,
   66,   66,   11,  -40,  -40, -264, -206, 1391, 1391, 1391,
  208,    0, -109, -213,   72,   24,    0,    5,    0,  208,
    0,    0,  208, -139,    0,    0,   81,    0,    5, -219,
 -109,    0,    0, 1391,    0,    0,    0,    0,
};
final static short yyrindex[] = {                       123,
 1261,    0,    0,    0,    0, 1344,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    3,    0,    0,
    0,    0,    0,    0,  -54,    0,    0, 1057,   12,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -31,    0, 1213,    0,    0,
  -37,    0,    0,    0,   29,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,    0,    0,    0,   57,  419,
  480,  787,  847,  943,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, 1167, 1117,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, 1235,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -28,    0,    0,    0,
   99,  127,  447,  508,  759,  815,  875,  161,    0,  161,
    0,    0,    0,    0, -143,    0,    0, 1139,    0,    0,
    0,    0,    0, 1297,    0,    0,    0,    0,   40,    0,
    0,    0,    0,    0,  391,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  111,    0,    0,    0,    0,    0,    0, -108,    0,  472,
    0, -103,    0,   56,    0,    0,    0,  843,  801,    0,
  -36,    0,  101, -116,   83,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   84,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  805,    0,  808,
    0,  -27,  -73,   27,    0,  -33,   16,   20,    0,    0,
    0,
};
final static int YYTABLESIZE=1669;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         16,
  103,   49,   16,   16,   16,   16,  130,   16,   16,   16,
   93,   39,   77,   18,   94,  154,  115,  116,  103,  112,
   81,  113,   16,  158,   16,   99,  100,   81,  104,  131,
   88,  164,  165,  166,  141,  142,   87,  103,    2,    3,
  103,  103,  103,  103,  103,  103,  161,  103,    9,  160,
  169,   10,   11,  109,   84,  108,  112,  178,  103,  103,
  103,  154,  103,   80,  171,  104,   90,  170,   49,  104,
  104,  104,  104,  104,   96,  104,  117,  138,  139,  140,
   37,  144,  145,   37,  118,   16,  104,  104,  104,  123,
  104,  124,  125,  126,   77,  128,  112,  112,  114,  112,
  112,  112,  107,  127,  129,  133,  149,  105,  136,  148,
  150,  159,  106,  101,  112,  112,  112,   87,  112,  163,
  174,  175,   40,  103,   15,  103,  113,   40,   82,   89,
   95,  120,  146,  176,  122,   98,   39,  147,  114,  114,
  143,  114,  114,  114,    0,    0,    0,    0,    0,    0,
    0,  104,    0,  104,    0,    0,  114,  114,  114,    0,
  114,    0,    0,    0,    0,    0,  113,  113,    0,  113,
  113,  113,    0,    0,    0,    0,    0,    0,    0,  112,
   54,  112,    0,    0,  113,  113,  113,   52,  113,    0,
    0,  162,   53,    0,    0,    0,    0,   96,    0,    0,
    0,    0,   96,   96,    0,   96,   96,   96,  168,    0,
   48,    0,   54,   15,    0,    0,    0,    0,    0,   75,
   96,  114,   96,  114,   53,   54,  177,  102,    0,   81,
  130,  130,   19,  130,   91,   92,    0,   53,  110,  111,
   54,   16,   16,   16,   16,   16,   16,   19,    0,  113,
    0,  113,   53,  131,  131,    0,    0,  103,  103,  103,
  103,  103,  103,  103,    0,  103,  103,  103,  103,    0,
    0,  103,  103,  103,  103,  103,  103,  103,  103,  103,
  103,  103,  103,  103,  103,  104,  104,  104,  104,  104,
  104,  104,    0,  104,  104,  104,  104,    0,    0,  104,
  104,  104,  104,  104,  104,  104,  104,  104,  104,  104,
  104,  104,  104,  112,  112,  112,  112,  112,  112,  112,
    0,  112,  112,  112,  112,    0,    0,  112,  112,  112,
  112,  112,  112,  112,  112,  112,  112,  112,  112,  112,
  112,   54,    0,  153,    0,    0,    0,    0,   78,    0,
    0,    0,    0,   53,    0,  114,  114,  114,  114,  114,
  114,  114,    0,  114,  114,  114,  114,    0,    0,  114,
  114,  114,  114,  114,  114,  114,  114,  114,  114,  114,
  114,  114,  114,  113,  113,  113,  113,  113,  113,  113,
   69,  113,  113,  113,  113,    0,    0,  113,  113,  113,
  113,  113,  113,  113,  113,  113,  113,  113,  113,  113,
  113,    0,    0,    0,    0,   51,    0,    0,  119,    0,
    0,   13,   14,   15,   16,   17,    0,   96,    0,    0,
   69,    0,   96,   96,    0,   96,   96,   96,    0,   96,
   96,   96,   96,   96,   96,    0,  120,    9,    0,   69,
   96,    0,   96,   13,   14,   15,   16,   17,  119,  119,
    9,    0,  119,    0,    0,    0,   13,   14,   15,   16,
   17,   25,    0,    0,    0,    9,  119,  119,  119,  121,
  119,   13,   14,   15,   16,   17,  120,  120,    0,   25,
  120,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   25,    0,    0,    0,  120,  120,  120,  122,  120,    0,
    0,    0,    0,   69,    0,   69,    0,    0,    0,  121,
  121,    0,    0,  121,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  121,  121,    0,
    0,  119,    0,  119,    0,    0,    0,  122,  122,    0,
    0,  122,   54,    0,    0,    0,    0,    0,    0,   19,
    2,    3,    0,    0,   53,  122,  122,    0,   54,  120,
    9,  120,    0,   10,   11,  151,    9,    0,    0,    0,
   53,    0,   13,   14,   15,   16,   17,    0,    0,    0,
    0,    0,    0,    0,    0,  155,    0,    0,    0,    0,
    0,    0,  121,    0,  121,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   25,
   25,   25,    0,    0,    0,    0,    0,    0,    0,    0,
  122,    0,  122,    0,    0,    0,    0,    0,    0,    0,
    0,  155,    0,    0,    0,   25,    0,   69,   69,   69,
   69,   69,   69,   69,    0,   69,   69,    0,   69,    0,
    0,   69,   69,   69,   69,   69,   69,   69,   69,   96,
   96,   96,   96,   96,   96,  119,  119,  119,  119,  119,
  119,  119,    0,  119,  119,  119,  119,    0,    0,  119,
  119,  119,  119,  119,  119,  119,  119,  119,  119,  119,
  119,  119,  119,  120,  120,  120,  120,  120,  120,  120,
    0,  120,  120,  120,  120,    0,    0,  120,  120,  120,
  120,  120,  120,  120,  120,  120,  120,  120,  120,  120,
  120,    0,    0,    0,    0,    0,  121,  121,  121,  121,
  121,  121,  121,    0,  121,  121,  121,  121,    0,    0,
  121,  121,  121,  121,  121,  121,  121,  121,  123,    0,
  121,  121,  121,  121,  122,  122,  122,  122,  122,  122,
  122,    0,  122,  122,  122,  122,    0,    0,  122,  122,
  122,  122,  122,  122,  122,  122,  124,   51,  122,  122,
  122,  122,    0,   13,   14,   15,   16,   17,  123,  123,
   28,    0,  123,    9,   44,    0,    0,   46,    0,   13,
   14,   15,   16,   17,  125,    0,  123,  123,   28,    0,
    0,    0,   44,    0,    0,   46,  124,  124,    0,   28,
  124,    0,    0,   44,    0,    0,   46,    0,    0,    0,
    0,    0,    0,    0,  124,  124,  126,   76,   77,   79,
    0,    0,    0,    0,  125,  125,    0,    0,  125,    0,
    0,   83,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  125,  125,  127,    0,    0,    0,    0,    0,
    0,  123,    0,  123,    0,    0,  126,  126,    0,    0,
  126,    0,    0,    0,   97,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  126,  126,    0,    0,    0,  124,
    0,  124,    0,    0,  127,  127,    0,  119,  127,    0,
  121,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  132,    0,  127,  127,    0,    0,  134,  125,  135,  125,
    0,    0,  128,    0,    0,    0,  137,    0,   28,   28,
   28,    0,   44,   44,   44,   46,   46,   46,    0,    0,
    0,    0,    0,    0,    0,  152,    0,    0,    0,  126,
    0,  126,    0,    0,   28,    0,    0,    0,   44,    0,
    0,   46,  128,  128,    0,    0,  128,    0,    0,    0,
    0,    0,    0,  167,    0,    0,    0,  127,    0,  127,
  128,  128,  172,    0,    0,  173,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  123,  123,  123,  123,  123,
  123,  123,    0,  123,  123,  123,  123,    0,    0,  123,
  123,  123,  123,  123,  123,  123,  123,    0,    0,  123,
  123,  123,  123,  124,  124,  124,  124,  124,  124,  124,
    0,  124,  124,  124,  124,    0,   62,  124,  124,  124,
  124,  124,  124,  124,  124,  128,    0,  128,    0,  124,
  124,  125,  125,  125,  125,  125,  125,  125,    0,  125,
  125,  125,  125,    0,    0,  125,  125,  125,  125,  125,
  125,  125,  125,    0,    0,    0,   62,  125,  125,    0,
    0,    0,   95,  126,  126,  126,  126,  126,  126,  126,
    0,  126,  126,  126,  126,   62,   19,  126,  126,  126,
  126,  126,  126,  126,  126,    0,    0,    0,    0,    0,
  126,  127,  127,  127,  127,  127,  127,  127,   20,  127,
  127,  127,  127,    0,    0,  127,  127,  127,  127,  127,
  127,  127,  127,    0,    0,    0,   19,    0,  127,    0,
   19,    0,    0,    0,    0,    0,   47,    0,    0,    0,
    0,    0,    0,    0,    0,   19,    0,    0,   20,   62,
    0,   62,   20,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   20,    0,  128,
  128,  128,  128,  128,  128,  128,   47,  128,  128,  128,
  128,    0,   45,  128,  128,  128,  128,  128,  128,  128,
  128,    0,    0,    0,    0,   47,    0,    0,    0,    0,
    0,    0,    0,    0,   63,    0,    0,    0,    0,   19,
    0,   19,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   45,    0,    0,    0,    0,    0,    0,    0,
   45,   20,    0,   20,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   63,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   47,
    0,   47,    0,   63,    0,    0,   64,    0,    0,    0,
   45,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   62,   62,   62,   62,   62,   62,   62,
    0,   62,   62,    0,   62,    0,    0,   62,   62,   62,
   62,   62,   62,   62,   62,   45,   64,   45,    0,    0,
    0,    0,    0,   92,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   64,    0,   63,    0,   63,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   19,   19,   19,   19,   19,   19,   19,
    0,   19,   19,   45,   19,   45,    0,   19,   19,   19,
   19,   19,   19,   19,   19,   20,   20,   20,   20,   20,
   20,   20,   92,   20,   20,    0,   20,    0,    0,   20,
   20,   20,   20,   20,   20,   20,   20,    0,    0,   64,
    0,   64,    0,   47,   47,   47,   47,   47,   47,   47,
   19,   47,   47,    0,   47,    0,    0,   47,   47,   47,
   47,   47,   47,   47,   47,    0,    0,    0,    0,   20,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   92,    0,   92,   45,
   45,   45,   45,   45,   45,   45,    0,   45,   45,    0,
   45,    0,    0,   45,   45,   45,   45,   45,   45,   45,
   45,   63,   63,   63,    0,   63,   63,   63,    0,   63,
   63,    0,   63,    0,    0,   63,   63,   63,   63,   63,
   63,   63,   63,   18,    0,    0,    0,   45,   45,   45,
   45,   45,   45,   45,    0,   45,   45,    0,    0,    0,
    0,   45,   45,   45,   45,   45,   45,   45,   45,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   64,   64,   64,    0,   64,   64,   64,
    0,   64,   64,    0,   64,    0,    0,   64,   64,   64,
   64,   64,   64,   64,   64,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   92,   92,   92,   92,   92,   92,   92,    0,   92,   92,
    0,    0,    0,    0,   92,   92,   92,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    1,    2,    3,
    0,    4,    5,    6,    0,    7,    8,    0,    9,    0,
    0,   10,   11,   12,   13,   14,   15,   16,   17,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
    0,   59,   40,   41,   42,   43,   61,   45,   46,   47,
   61,    0,   41,  123,   46,  124,  281,  282,   58,   60,
   58,   62,   60,  127,   62,   53,   54,  268,    0,   61,
   40,  148,  149,  150,  108,  109,   46,   37,  258,  259,
   40,   41,   42,   43,   44,   45,   41,   47,  268,   44,
  154,  271,  272,   43,  268,   45,    0,  174,   58,   59,
   60,  170,   62,    8,   41,   37,   46,   44,   59,   41,
   42,   43,   44,   45,   61,   47,  283,  105,  106,  107,
   41,  115,  116,   44,  284,  123,   58,   59,   60,  265,
   62,   40,  125,   41,  123,   91,   40,   41,    0,   43,
   44,   45,   37,   44,  268,  268,  260,   42,   41,   41,
   41,   93,   47,   58,   58,   59,   60,   46,   62,   58,
  260,   41,    0,  123,  268,  125,    0,  125,   18,   29,
   48,   76,  117,  170,   79,   52,  125,  118,   40,   41,
  114,   43,   44,   45,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  123,   -1,  125,   -1,   -1,   58,   59,   60,   -1,
   62,   -1,   -1,   -1,   -1,   -1,   40,   41,   -1,   43,
   44,   45,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,
   33,  125,   -1,   -1,   58,   59,   60,   40,   62,   -1,
   -1,  136,   45,   -1,   -1,   -1,   -1,   37,   -1,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,  153,   -1,
  268,   -1,   33,  268,   -1,   -1,   -1,   -1,   -1,   40,
   60,  123,   62,  125,   45,   33,  171,  267,   -1,  267,
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
  280,  281,  282,  283,  284,   -1,    0,  268,   -1,   59,
   60,   -1,   62,  274,  275,  276,  277,  278,   40,   41,
  268,   -1,   44,   -1,   -1,   -1,  274,  275,  276,  277,
  278,    0,   -1,   -1,   -1,  268,   58,   59,   60,    0,
   62,  274,  275,  276,  277,  278,   40,   41,   -1,   18,
   44,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   29,   -1,   -1,   -1,   58,   59,   60,    0,   62,   -1,
   -1,   -1,   -1,  123,   -1,  125,   -1,   -1,   -1,   40,
   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,   -1,
   -1,  123,   -1,  125,   -1,   -1,   -1,   40,   41,   -1,
   -1,   44,   33,   -1,   -1,   -1,   -1,   -1,   -1,   40,
  258,  259,   -1,   -1,   45,   58,   59,   -1,   33,  123,
  268,  125,   -1,  271,  272,   40,  268,   -1,   -1,   -1,
   45,   -1,  274,  275,  276,  277,  278,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  124,   -1,   -1,   -1,   -1,
   -1,   -1,  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  148,
  149,  150,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  170,   -1,   -1,   -1,  174,   -1,  257,  258,  259,
  260,  261,  262,  263,   -1,  265,  266,   -1,  268,   -1,
   -1,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  280,  281,  282,  283,  284,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,  268,   -1,   -1,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  280,  281,
  282,  283,  284,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,   -1,   -1,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  280,  281,  282,  283,
  284,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,
  261,  262,  263,   -1,  265,  266,  267,  268,   -1,   -1,
  271,  272,  273,  274,  275,  276,  277,  278,    0,   -1,
  281,  282,  283,  284,  257,  258,  259,  260,  261,  262,
  263,   -1,  265,  266,  267,  268,   -1,   -1,  271,  272,
  273,  274,  275,  276,  277,  278,    0,  268,  281,  282,
  283,  284,   -1,  274,  275,  276,  277,  278,   40,   41,
    0,   -1,   44,  268,    0,   -1,   -1,    0,   -1,  274,
  275,  276,  277,  278,    0,   -1,   58,   59,   18,   -1,
   -1,   -1,   18,   -1,   -1,   18,   40,   41,   -1,   29,
   44,   -1,   -1,   29,   -1,   -1,   29,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   58,   59,    0,    5,    6,    7,
   -1,   -1,   -1,   -1,   40,   41,   -1,   -1,   44,   -1,
   -1,   19,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   58,   59,    0,   -1,   -1,   -1,   -1,   -1,
   -1,  123,   -1,  125,   -1,   -1,   40,   41,   -1,   -1,
   44,   -1,   -1,   -1,   52,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   58,   59,   -1,   -1,   -1,  123,
   -1,  125,   -1,   -1,   40,   41,   -1,   75,   44,   -1,
   78,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   88,   -1,   58,   59,   -1,   -1,   94,  123,   96,  125,
   -1,   -1,    0,   -1,   -1,   -1,  104,   -1,  148,  149,
  150,   -1,  148,  149,  150,  148,  149,  150,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  123,   -1,   -1,   -1,  123,
   -1,  125,   -1,   -1,  174,   -1,   -1,   -1,  174,   -1,
   -1,  174,   40,   41,   -1,   -1,   44,   -1,   -1,   -1,
   -1,   -1,   -1,  151,   -1,   -1,   -1,  123,   -1,  125,
   58,   59,  160,   -1,   -1,  163,   -1,   -1,   -1,   -1,
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
   -1,   -1,   -1,   -1,  271,  272,  273,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
   -1,  261,  262,  263,   -1,  265,  266,   -1,  268,   -1,
   -1,  271,  272,  273,  274,  275,  276,  277,  278,
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
"ArgListOpt : ArgList",
"ArgListOpt :",
"ArgList : Expr",
"ArgList : ArgList ',' Expr",
"MethodCall : Name '(' ArgList ')'",
"MethodCall : Name '(' ')'",
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
"ReturnStmt : RETURN Expr",
"ReturnStmt : RETURN",
"Primary : Literal",
"Primary : FieldAccess",
"Primary : MethodCall",
"Primary : '(' Expr ')'",
"Literal : INTLIT",
"Literal : DOUBLELIT",
"Literal : BOOLLIT",
"Literal : STRINGLIT",
"Literal : NULLVAL",
"FieldAccess : Primary '.' IDENTIFIER",
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
  yyval=j0.node("ArgList",1270,val_peek(2),val_peek(0)); }
break;
case 31:
//#line 49 "j0gram.y"
{ yyval=j0.node("MethodCall",1290,val_peek(3),val_peek(1)); }
break;
case 32:
//#line 50 "j0gram.y"
{ yyval=j0.node("MethodCall",1291,val_peek(2)); }
break;
case 36:
//#line 55 "j0gram.y"
{
  yyval=j0.node("FormalParmList",1090,val_peek(2),val_peek(0)); }
break;
case 37:
//#line 57 "j0gram.y"
{
  yyval=j0.node("FormalParm",1100,val_peek(1),val_peek(0));
 }
break;
case 38:
//#line 61 "j0gram.y"
{yyval=j0.node("Block",1200,val_peek(1));}
break;
case 42:
//#line 63 "j0gram.y"
{
  yyval=j0.node("BlockStmts",1130,val_peek(1),val_peek(0)); }
break;
case 47:
//#line 70 "j0gram.y"
{
  yyval=j0.node("LocalVarDecl",1140,val_peek(1),val_peek(0)); }
break;
case 63:
//#line 81 "j0gram.y"
{ yyval=j0.node("IfStmt",1150,val_peek(1),val_peek(0)); }
break;
case 64:
//#line 82 "j0gram.y"
{ yyval=j0.node("IfStmt",1151,val_peek(2),val_peek(0)); }
break;
case 65:
//#line 83 "j0gram.y"
{ yyval=j0.node("IfElseStmt",1160,val_peek(4),val_peek(2),val_peek(0)); }
break;
case 66:
//#line 84 "j0gram.y"
{ yyval=j0.node("IfElseStmt",1161,val_peek(3),val_peek(2),val_peek(0)); }
break;
case 67:
//#line 86 "j0gram.y"
{ yyval=j0.node("WhileStmt",1210,val_peek(2),val_peek(0)); }
break;
case 68:
//#line 87 "j0gram.y"
{ yyval=j0.node("WhileStmt",1211,val_peek(1),val_peek(0)); }
break;
case 69:
//#line 89 "j0gram.y"
{
    yyval=j0.node("DoWhileStmt",1212,val_peek(4),val_peek(1)); }
break;
case 70:
//#line 91 "j0gram.y"
{
        yyval=j0.node("DoWhileStmt",1213,val_peek(2),val_peek(0)); }
break;
case 71:
//#line 112 "j0gram.y"
{ yyval=j0.node("ForStmt",1220,val_peek(2),val_peek(0)); }
break;
case 72:
//#line 113 "j0gram.y"
{ yyval=j0.node("ForStmt",1221,val_peek(1),val_peek(0)); }
break;
case 77:
//#line 118 "j0gram.y"
{ yyval=j0.node("ForNormal",1222,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 78:
//#line 119 "j0gram.y"
{ yyval=j0.node("ForFull",1223,val_peek(4),val_peek(3),val_peek(2),val_peek(0)); }
break;
case 82:
//#line 123 "j0gram.y"
{ yyval=j0.node("ForVarInit",1224,val_peek(2),val_peek(0)); }
break;
case 88:
//#line 128 "j0gram.y"
{
  yyval=j0.node("StmtExprList",1230,val_peek(2),val_peek(0)); }
break;
case 90:
//#line 131 "j0gram.y"
{
  yyval=j0.node("BreakStmt",1240,val_peek(1)); }
break;
case 91:
//#line 133 "j0gram.y"
{ yyval=j0.node("ReturnStmt",1250,val_peek(0)); }
break;
case 92:
//#line 134 "j0gram.y"
{ yyval=j0.node("ReturnStmt",1251);}
break;
case 96:
//#line 136 "j0gram.y"
{
  yyval=val_peek(1);}
break;
case 102:
//#line 140 "j0gram.y"
{
  yyval=j0.node("FieldAccess",1280,val_peek(2),val_peek(0)); }
break;
case 105:
//#line 144 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1300,val_peek(1),val_peek(0)); }
break;
case 106:
//#line 146 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1301,val_peek(1),val_peek(0)); }
break;
case 109:
//#line 150 "j0gram.y"
{
      yyval=j0.node("MulExpr",1310,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 110:
//#line 152 "j0gram.y"
{
      yyval=j0.node("MulExpr",1311,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 111:
//#line 154 "j0gram.y"
{
      yyval=j0.node("MulExpr",1312,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 113:
//#line 157 "j0gram.y"
{
      yyval=j0.node("AddExpr",1320,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 114:
//#line 159 "j0gram.y"
{
      yyval=j0.node("AddExpr",1321,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 120:
//#line 162 "j0gram.y"
{
  yyval=j0.node("RelExpr",1330,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 122:
//#line 166 "j0gram.y"
{
  yyval=j0.node("EqExpr",1340,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 123:
//#line 168 "j0gram.y"
{
  yyval=j0.node("EqExpr",1341,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 125:
//#line 170 "j0gram.y"
{
  yyval=j0.node("CondAndExpr", 1350, val_peek(2),val_peek(1), val_peek(0)); }
break;
case 127:
//#line 172 "j0gram.y"
{
  yyval=j0.node("CondOrExpr", 1360, val_peek(2),val_peek(1), val_peek(0)); }
break;
case 129:
//#line 176 "j0gram.y"
{
yyval=j0.node("Assignment",1370, val_peek(2), val_peek(1), val_peek(0)); }
break;
//#line 1131 "Parser.java"
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
