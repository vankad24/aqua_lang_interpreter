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
public final static short MINUSEQUAL=287;
public final static short ADDEQUAL=288;
public final static short MULEQUAL=289;
public final static short DIVEQUAL=290;
public final static short PUBLIC=291;
public final static short STATIC=292;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    2,    3,    3,    4,    4,    5,    5,    5,    6,
    8,    8,    8,    8,    8,   10,   10,   11,    9,    9,
   12,   12,   13,   13,    7,    7,   16,   16,   17,   17,
   19,   19,   20,   20,   15,   15,   21,   14,    1,    1,
   22,   22,   23,   25,   25,   26,   27,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   28,
   36,   36,   31,   31,   32,   32,   34,   34,   33,   33,
   35,   35,   38,   38,   38,   41,   40,   39,   42,   42,
   43,   43,   44,   44,   45,   45,   29,   29,   30,   30,
   46,   46,   46,   46,   47,   47,   47,   47,   47,   48,
   49,   49,   50,   50,   50,   51,   51,   51,   51,   52,
   52,   52,   53,   53,   53,   53,   54,   54,   55,   55,
   55,   56,   56,   57,   57,   18,   37,   37,   58,   58,
   59,   59,   59,   59,   59,   60,   60,
};
final static short yylen[] = {                            2,
    1,    4,    3,    2,    1,    2,    1,    1,    0,    2,
    1,    1,    1,    1,    1,    1,    1,    3,    1,    3,
    1,    3,    1,    0,    6,    5,    1,    0,    1,    3,
    4,    3,    1,    0,    1,    3,    2,    3,    1,    0,
    1,    2,    1,    1,    0,    1,    2,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    3,    5,    7,    5,    5,    3,    6,    4,
    5,    3,    1,    1,    1,    1,    3,    5,    3,    1,
    1,    1,    1,    0,    1,    3,    2,    3,    2,    1,
    1,    1,    1,    3,    1,    1,    1,    1,    1,    3,
    1,    1,    2,    2,    1,    1,    3,    3,    3,    1,
    3,    3,    1,    1,    1,    1,    1,    3,    1,    3,
    3,    1,    3,    1,    3,    1,    3,    2,    1,    1,
    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,   12,   11,    0,    0,    0,    0,    0,   16,   14,
   13,    0,   95,   96,   98,   97,   99,    0,    0,   49,
    0,    1,   55,    0,    0,   17,   48,    0,    0,   41,
   43,   59,   46,   50,   51,   52,   53,   54,   56,   57,
   58,   60,   61,    0,   91,    0,    0,    0,   44,   87,
    0,    0,    0,    0,    0,   76,   93,    0,   73,   74,
   75,    0,    0,   92,  105,  106,    0,    0,    0,    0,
    0,    0,    0,    0,   89,    0,    0,    0,    0,    0,
    0,   21,    0,    0,    0,    0,   42,    0,  136,  137,
  134,  135,  132,  133,  131,    0,  128,   88,    0,    0,
    0,  103,  104,   72,   82,   81,    0,    0,    0,    0,
    0,    0,  113,  114,  115,  116,    0,    0,    0,    0,
    0,    0,    0,    0,   68,    0,    0,   38,   94,    0,
    0,   18,   32,    0,   29,  100,  127,   79,    0,    0,
  107,  108,  109,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   70,    0,    0,    0,    0,   35,
    0,   22,    0,   31,   71,    0,    0,   66,   67,    0,
   26,    0,    0,    0,   30,   78,    0,    0,   36,   25,
   65,
};
final static short yydgoto[] = {                         21,
   22,    0,    0,    0,    0,    0,   23,   24,   83,   55,
   26,   84,    0,   27,  159,    0,  134,   56,   57,    0,
  160,   29,   30,   31,   50,   32,   33,   34,   35,   36,
   37,   38,   39,   40,   41,   42,   43,   58,   59,   60,
   61,   62,  107,    0,    0,   63,   45,   64,   65,   66,
   67,   68,  117,   69,   70,   71,   72,   47,   96,   97,
};
final static short yysindex[] = {                      1391,
  -57,    0,    0,  148,  180,  208,  309, -108,    0,    0,
    0, -240,    0,    0,    0,    0,    0, 1391,  208,    0,
    0,    0,    0, -233,  -22,    0,    0,    0, 1391,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    4,    0,    0,  307,  -20,    0,    0,
   -3,  520,  208,  208,  -22,    0,    0, -108,    0,    0,
    0,  -51,    4,    0,    0,    0,   38,  -26,  -48, -203,
 -230, -219,  208, -108,    0,  208, -108, -196,   37,  -41,
   49,    0,   50,    5, -163,  193,    0, -162,    0,    0,
    0,    0,    0,    0,    0,  208,    0,    0,  208,   49,
   66,    0,    0,    0,    0,    0,  208,  208,  208,  208,
  208,  208,    0,    0,    0,    0,  208,  208,  208,  208,
  208,   67, -151,   69,    0,  536,  303,    0,    0, -233,
   18,    0,    0,   -8,    0,    0,    0,    0, -108,   54,
    0,    0,    0,   38,   38,  -26,  -48,  -48, -203, -230,
 1391, 1391, 1391,  208,    0, -108, -233,   68,   -7,    0,
    5,    0,  208,    0,    0,  208, -147,    0,    0,   77,
    0,    5, -204, -108,    0,    0, 1391,    0,    0,    0,
    0,
};
final static short yyrindex[] = {                       120,
 1261,    0,    0,    0,    0, 1344,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   -4,    0,    0,
    0,    0,    0,    0,  336,    0,    0, 1057,   11,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  209,    0, 1213,    0,    0,
  -37,    0,    0,    0,   29,    0,    0,    0,    0,    0,
    0,    0,    1,    0,    0,    0,   57,  419,  480,  787,
  847,  943,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, 1167, 1117,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, 1235,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -28,
    0,    0,    0,   99,  127,  447,  508,  759,  815,  875,
  161,    0,  161,    0,    0,    0,    0, -146,    0,    0,
 1139,    0,    0,    0,    0,    0, 1297,    0,    0,    0,
    0,    8,    0,    0,    0,    0,    0,  391,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
  105,    0,    0,    0,    0,    0,    0, -111,    0,   22,
    0, -110,    0,  812,    0,    0,    0, 1606,  801,    0,
  -45,    0,   96, -121,   81,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   78,    0,    0,
    0,    0,    0,    0,    0,  805,    0,  808,    0,  -27,
  -19,   14,    0,  -15,   12,   13,    0,    0,    0,    0,
};
final static int YYTABLESIZE=1772;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         16,
  101,   49,   16,   16,   16,   16,  106,   16,   16,   16,
   39,  115,   77,  116,   18,  157,  112,   86,  111,  161,
   80,   25,   16,   85,   16,  102,  103,   79,  102,  167,
  168,  169,  164,  174,   82,  163,  173,  101,   49,   25,
  101,  101,  101,  101,  101,  101,  172,  101,   37,   88,
   25,   37,  120,    2,    3,  181,  110,   99,  101,  101,
  101,  157,  101,    9,  121,  102,   10,   11,  126,  102,
  102,  102,  102,  102,  110,  102,  127,  118,  119,  108,
  141,  142,  143,  128,  109,   16,  102,  102,  102,  129,
  102,  144,  145,  130,   77,  131,  110,  110,  112,  110,
  110,  110,  147,  148,  132,  136,  139,  151,  152,  153,
  162,  166,  177,   85,  110,  110,  110,  178,  110,   40,
   40,   15,   80,  101,   87,  101,  111,  179,   98,  101,
  146,  149,    0,  150,    0,   39,    0,    0,  112,  112,
    0,  112,  112,  112,    0,    0,    0,    0,  158,    0,
    0,  102,    0,  102,    0,    0,  112,  112,  112,    0,
  112,    0,    0,    0,    0,    0,  111,  111,    0,  111,
  111,  111,   25,   25,   25,    0,    0,    0,    0,  110,
   54,  110,    0,    0,  111,  111,  111,   52,  111,    0,
    0,    0,   53,    0,  158,    0,    0,   94,   25,    0,
    0,    0,   94,   94,    0,   94,   94,   94,    0,    0,
   48,    0,   54,    0,    0,  105,    0,    0,    0,   73,
   94,  112,   94,  112,   53,   54,    0,    0,    0,   80,
  113,  114,   19,  133,    0,    0,    0,   53,    0,    0,
   54,   16,   16,   16,   16,   16,   16,   19,    0,  111,
    0,  111,   53,    0,   92,    0,    0,  101,  101,  101,
  101,  101,  101,  101,    0,  101,  101,  101,  101,  130,
    0,  101,  101,  101,  101,  101,  101,  101,  101,  101,
  101,  101,  101,  101,  101,  102,  102,  102,  102,  102,
  102,  102,    0,  102,  102,  102,  102,    0,    0,  102,
  102,  102,  102,  102,  102,  102,  102,  102,  102,  102,
  102,  102,  102,  110,  110,  110,  110,  110,  110,  110,
    0,  110,  110,  110,  110,    0,    0,  110,  110,  110,
  110,  110,  110,  110,  110,  110,  110,  110,  110,  110,
  110,   54,    0,  156,    0,    0,    0,    0,   76,    0,
    0,    0,    0,   53,    0,  112,  112,  112,  112,  112,
  112,  112,    0,  112,  112,  112,  112,   95,    0,  112,
  112,  112,  112,  112,  112,  112,  112,  112,  112,  112,
  112,  112,  112,  111,  111,  111,  111,  111,  111,  111,
   69,  111,  111,  111,  111,    0,  129,  111,  111,  111,
  111,  111,  111,  111,  111,  111,  111,  111,  111,  111,
  111,    0,    0,    0,    0,   51,    0,    0,  117,    0,
    0,   13,   14,   15,   16,   17,    0,   94,    0,    0,
   69,    0,   94,   94,    0,   94,   94,   94,    0,   94,
   94,   94,   94,   94,   94,    0,  118,    9,    0,   69,
   94,    0,   94,   13,   14,   15,   16,   17,  117,  117,
    9,    0,  117,    0,    0,    0,   13,   14,   15,   16,
   17,    0,    0,    0,    0,    9,  117,  117,  117,  119,
  117,   13,   14,   15,   16,   17,  118,  118,    0,    0,
  118,    0,    0,  130,  130,  130,  130,  130,  130,    0,
    0,    0,    0,    0,  118,  118,  118,  120,  118,    0,
    0,    0,    0,   69,    0,   69,    0,    0,    0,  119,
  119,    0,    0,  119,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  119,  119,    0,
    0,  117,    0,  117,    0,    0,    0,  120,  120,    0,
    0,  120,   54,    0,    0,    0,    0,    0,    0,   19,
    2,    3,    0,    0,   53,  120,  120,    0,   54,  118,
    9,  118,    0,   10,   11,  154,    9,    0,    0,    0,
   53,    0,   13,   14,   15,   16,   17,    0,    0,    0,
    0,   89,   90,   91,   92,   93,   94,    0,    0,    0,
    0,    0,  119,   15,  119,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  129,  129,  129,  129,  129,  129,    0,    0,    0,    0,
  120,    0,  120,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   69,   69,   69,
   69,   69,   69,   69,    0,   69,   69,    0,   69,    0,
    0,   69,   69,   69,   69,   69,   69,   69,   69,   94,
   94,   94,   94,   94,   94,  117,  117,  117,  117,  117,
  117,  117,    0,  117,  117,  117,  117,    0,    0,  117,
  117,  117,  117,  117,  117,  117,  117,  117,  117,  117,
  117,  117,  117,  118,  118,  118,  118,  118,  118,  118,
    0,  118,  118,  118,  118,    0,    0,  118,  118,  118,
  118,  118,  118,  118,  118,  118,  118,  118,  118,  118,
  118,    0,    0,    0,    0,    0,  119,  119,  119,  119,
  119,  119,  119,    0,  119,  119,  119,  119,    0,    0,
  119,  119,  119,  119,  119,  119,  119,  119,  121,    0,
  119,  119,  119,  119,  120,  120,  120,  120,  120,  120,
  120,    0,  120,  120,  120,  120,    0,    0,  120,  120,
  120,  120,  120,  120,  120,  120,  122,   51,  120,  120,
  120,  120,    0,   13,   14,   15,   16,   17,  121,  121,
   28,    0,  121,    9,   44,    0,    0,   46,    0,   13,
   14,   15,   16,   17,  123,    0,  121,  121,   28,   78,
    0,    0,   44,    0,    0,   46,  122,  122,    0,   28,
  122,    0,    0,   44,    0,    0,   46,    0,    0,    0,
    0,    0,    0,    0,  122,  122,  124,    0,    0,    0,
    0,    0,    0,    0,  123,  123,    0,    0,  123,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  104,
    0,    0,  123,  123,  125,    0,    0,    0,    0,    0,
    0,  121,    0,  121,    0,  123,  124,  124,  125,    0,
  124,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  124,  124,    0,    0,    0,  122,
    0,  122,    0,    0,  125,  125,    0,    0,  125,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  125,  125,    0,    0,    0,  123,    0,  123,
    0,    0,  126,    0,    0,    0,    0,    0,    0,    0,
  165,   28,   28,   28,    0,   44,   44,   44,   46,   46,
   46,    0,    0,    0,    0,    0,    0,  171,    0,  124,
    0,  124,    0,    0,    0,    0,    0,   28,    0,    0,
    0,   44,  126,  126,   46,  180,  126,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  125,    0,  125,
  126,  126,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  121,  121,  121,  121,  121,
  121,  121,    0,  121,  121,  121,  121,    0,    0,  121,
  121,  121,  121,  121,  121,  121,  121,    0,    0,  121,
  121,  121,  121,  122,  122,  122,  122,  122,  122,  122,
    0,  122,  122,  122,  122,    0,   62,  122,  122,  122,
  122,  122,  122,  122,  122,  126,    0,  126,    0,  122,
  122,  123,  123,  123,  123,  123,  123,  123,    0,  123,
  123,  123,  123,    0,    0,  123,  123,  123,  123,  123,
  123,  123,  123,    0,    0,    0,   62,  123,  123,    0,
    0,    0,   93,  124,  124,  124,  124,  124,  124,  124,
    0,  124,  124,  124,  124,   62,   19,  124,  124,  124,
  124,  124,  124,  124,  124,    0,    0,    0,    0,    0,
  124,  125,  125,  125,  125,  125,  125,  125,   20,  125,
  125,  125,  125,    0,    0,  125,  125,  125,  125,  125,
  125,  125,  125,    0,    0,    0,   19,    0,  125,    0,
   19,    0,    0,    0,    0,    0,   47,    0,    0,    0,
    0,    0,    0,    0,    0,   19,    0,    0,   20,   62,
    0,   62,   20,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   20,    0,  126,
  126,  126,  126,  126,  126,  126,   47,  126,  126,  126,
  126,    0,   45,  126,  126,  126,  126,  126,  126,  126,
  126,    0,    0,    0,    0,   47,    0,    0,    0,    0,
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
    0,    0,    0,   90,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   64,    0,   63,    0,   63,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   19,   19,   19,   19,   19,   19,   19,
    0,   19,   19,   45,   19,   45,    0,   19,   19,   19,
   19,   19,   19,   19,   19,   20,   20,   20,   20,   20,
   20,   20,   90,   20,   20,    0,   20,    0,    0,   20,
   20,   20,   20,   20,   20,   20,   20,    0,    0,   64,
    0,   64,    0,   47,   47,   47,   47,   47,   47,   47,
   19,   47,   47,    0,   47,    0,    0,   47,   47,   47,
   47,   47,   47,   47,   47,    0,    0,    0,    0,   20,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   90,    0,   90,   45,
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
   90,   90,   90,   90,   90,   90,   90,    0,   90,   90,
   74,   75,   77,    0,   90,   90,   90,    0,    0,    0,
    0,    0,    0,    0,   81,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    1,    2,    3,
    0,    4,    5,    6,    0,    7,    8,  100,    9,    0,
    0,   10,   11,   12,   13,   14,   15,   16,   17,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  122,    0,
    0,  124,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  135,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  137,    0,    0,  138,    0,    0,    0,    0,    0,
    0,    0,  140,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  155,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  170,
    0,    0,    0,    0,    0,    0,    0,    0,  175,    0,
    0,  176,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
    0,   59,   40,   41,   42,   43,   58,   45,   46,   47,
    0,   60,   41,   62,  123,  127,   43,   40,   45,  130,
   58,    0,   60,   46,   62,   53,   54,  268,    0,  151,
  152,  153,   41,   41,  268,   44,   44,   37,   59,   18,
   40,   41,   42,   43,   44,   45,  157,   47,   41,   46,
   29,   44,  283,  258,  259,  177,    0,   61,   58,   59,
   60,  173,   62,  268,  284,   37,  271,  272,  265,   41,
   42,   43,   44,   45,   37,   47,   40,  281,  282,   42,
  108,  109,  110,  125,   47,  123,   58,   59,   60,   41,
   62,  111,  112,   44,  123,   91,   40,   41,    0,   43,
   44,   45,  118,  119,  268,  268,   41,   41,  260,   41,
   93,   58,  260,   46,   58,   59,   60,   41,   62,    0,
  125,  268,   18,  123,   29,  125,    0,  173,   48,   52,
  117,  120,   -1,  121,   -1,  125,   -1,   -1,   40,   41,
   -1,   43,   44,   45,   -1,   -1,   -1,   -1,  127,   -1,
   -1,  123,   -1,  125,   -1,   -1,   58,   59,   60,   -1,
   62,   -1,   -1,   -1,   -1,   -1,   40,   41,   -1,   43,
   44,   45,  151,  152,  153,   -1,   -1,   -1,   -1,  123,
   33,  125,   -1,   -1,   58,   59,   60,   40,   62,   -1,
   -1,   -1,   45,   -1,  173,   -1,   -1,   37,  177,   -1,
   -1,   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,
  268,   -1,   33,   -1,   -1,  267,   -1,   -1,   -1,   40,
   60,  123,   62,  125,   45,   33,   -1,   -1,   -1,  267,
  279,  280,   40,   41,   -1,   -1,   -1,   45,   -1,   -1,
   33,  279,  280,  281,  282,  283,  284,   40,   -1,  123,
   -1,  125,   45,   -1,   46,   -1,   -1,  257,  258,  259,
  260,  261,  262,  263,   -1,  265,  266,  267,  268,   61,
   -1,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  280,  281,  282,  283,  284,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,  268,   -1,   -1,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  280,  281,
  282,  283,  284,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,   -1,   -1,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  280,  281,  282,  283,
  284,   33,   -1,   41,   -1,   -1,   -1,   -1,   40,   -1,
   -1,   -1,   -1,   45,   -1,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,  268,   61,   -1,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  280,  281,
  282,  283,  284,  257,  258,  259,  260,  261,  262,  263,
    0,  265,  266,  267,  268,   -1,   61,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  280,  281,  282,  283,
  284,   -1,   -1,   -1,   -1,  268,   -1,   -1,    0,   -1,
   -1,  274,  275,  276,  277,  278,   -1,   37,   -1,   -1,
   40,   -1,   42,   43,   -1,   45,   46,   47,   -1,  279,
  280,  281,  282,  283,  284,   -1,    0,  268,   -1,   59,
   60,   -1,   62,  274,  275,  276,  277,  278,   40,   41,
  268,   -1,   44,   -1,   -1,   -1,  274,  275,  276,  277,
  278,   -1,   -1,   -1,   -1,  268,   58,   59,   60,    0,
   62,  274,  275,  276,  277,  278,   40,   41,   -1,   -1,
   44,   -1,   -1,  285,  286,  287,  288,  289,  290,   -1,
   -1,   -1,   -1,   -1,   58,   59,   60,    0,   62,   -1,
   -1,   -1,   -1,  123,   -1,  125,   -1,   -1,   -1,   40,
   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,   -1,
   -1,  123,   -1,  125,   -1,   -1,   -1,   40,   41,   -1,
   -1,   44,   33,   -1,   -1,   -1,   -1,   -1,   -1,   40,
  258,  259,   -1,   -1,   45,   58,   59,   -1,   33,  123,
  268,  125,   -1,  271,  272,   40,  268,   -1,   -1,   -1,
   45,   -1,  274,  275,  276,  277,  278,   -1,   -1,   -1,
   -1,  285,  286,  287,  288,  289,  290,   -1,   -1,   -1,
   -1,   -1,  123,  268,  125,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  285,  286,  287,  288,  289,  290,   -1,   -1,   -1,   -1,
  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,
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
  275,  276,  277,  278,    0,   -1,   58,   59,   18,    8,
   -1,   -1,   18,   -1,   -1,   18,   40,   41,   -1,   29,
   44,   -1,   -1,   29,   -1,   -1,   29,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   58,   59,    0,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   40,   41,   -1,   -1,   44,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,
   -1,   -1,   58,   59,    0,   -1,   -1,   -1,   -1,   -1,
   -1,  123,   -1,  125,   -1,   74,   40,   41,   77,   -1,
   44,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   58,   59,   -1,   -1,   -1,  123,
   -1,  125,   -1,   -1,   40,   41,   -1,   -1,   44,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   58,   59,   -1,   -1,   -1,  123,   -1,  125,
   -1,   -1,    0,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  139,  151,  152,  153,   -1,  151,  152,  153,  151,  152,
  153,   -1,   -1,   -1,   -1,   -1,   -1,  156,   -1,  123,
   -1,  125,   -1,   -1,   -1,   -1,   -1,  177,   -1,   -1,
   -1,  177,   40,   41,  177,  174,   44,   -1,   -1,   -1,
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
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   73,   -1,
   -1,   76,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   86,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   96,   -1,   -1,   99,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  107,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  126,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  154,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  163,   -1,
   -1,  166,
};
}
final static short YYFINAL=21;
final static short YYMAXTOKEN=292;
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
"LOGICALAND","LOGICALOR","INCREMENT","DECREMENT","MINUSEQUAL","ADDEQUAL",
"MULEQUAL","DIVEQUAL","PUBLIC","STATIC",
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
"ForHeader : ForFull",
"ForHeader : ForNormal",
"ForHeader : ForShort",
"ForShort : Expr",
"ForNormal : ForVarInit ForSeparator Expr",
"ForFull : ForVarInit ForSeparator Expr ':' Expr",
"ForVarInit : IDENTIFIER '=' Expr",
"ForVarInit : IDENTIFIER",
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
"Assignment : LeftHandSide IncrementOp",
"LeftHandSide : Name",
"LeftHandSide : FieldAccess",
"AssignOp : '='",
"AssignOp : MULEQUAL",
"AssignOp : DIVEQUAL",
"AssignOp : MINUSEQUAL",
"AssignOp : ADDEQUAL",
"IncrementOp : INCREMENT",
"IncrementOp : DECREMENT",
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
    j0.process((Tree) yyval.obj);
}
break;
case 2:
//#line 16 "j0gram.y"
{
  yyval=j0.node("ClassDecl",1000,val_peek(1),val_peek(0));
 }
break;
case 3:
//#line 19 "j0gram.y"
{ yyval=j0.node("ClassBody",1010,val_peek(1)); }
break;
case 4:
//#line 20 "j0gram.y"
{ yyval=j0.node("ClassBody",1011); }
break;
case 6:
//#line 22 "j0gram.y"
{
  yyval=j0.node("ClassBodyDecls",1020,val_peek(1),val_peek(0)); }
break;
case 10:
//#line 25 "j0gram.y"
{
  yyval=j0.node("FieldDecl",1030,val_peek(1),val_peek(0)); }
break;
case 18:
//#line 30 "j0gram.y"
{
  yyval=j0.node("QualifiedName",1040,val_peek(2),val_peek(0));}
break;
case 20:
//#line 33 "j0gram.y"
{
  yyval=j0.node("VarDecls",1050,val_peek(2),val_peek(0)); }
break;
case 22:
//#line 35 "j0gram.y"
{
  yyval=j0.node("VarDeclarator",1060,val_peek(2)); }
break;
case 25:
//#line 40 "j0gram.y"
{ yyval=j0.node("MethodDecl",1380,val_peek(4),val_peek(2),val_peek(0)); }
break;
case 26:
//#line 41 "j0gram.y"
{ yyval=j0.node("MethodDecl",1381,val_peek(3),val_peek(0)); }
break;
case 30:
//#line 44 "j0gram.y"
{
  yyval=j0.node("ArgList",1270,val_peek(2),val_peek(0)); }
break;
case 31:
//#line 47 "j0gram.y"
{ yyval=j0.node("MethodCall",1290,val_peek(3),val_peek(1)); }
break;
case 32:
//#line 48 "j0gram.y"
{ yyval=j0.node("MethodCall",1291,val_peek(2)); }
break;
case 36:
//#line 53 "j0gram.y"
{
  yyval=j0.node("FormalParmList",1090,val_peek(2),val_peek(0)); }
break;
case 37:
//#line 55 "j0gram.y"
{
  yyval=j0.node("FormalParm",1100,val_peek(1),val_peek(0));
 }
break;
case 38:
//#line 59 "j0gram.y"
{yyval=j0.node("Block",1200,val_peek(1));}
break;
case 42:
//#line 61 "j0gram.y"
{
  yyval=j0.node("BlockStmts",1130,val_peek(1),val_peek(0)); }
break;
case 47:
//#line 68 "j0gram.y"
{
  yyval=j0.node("LocalVarDecl",1140,val_peek(1),val_peek(0)); }
break;
case 63:
//#line 79 "j0gram.y"
{ yyval=j0.node("IfStmt",1150,val_peek(1),val_peek(0)); }
break;
case 64:
//#line 80 "j0gram.y"
{ yyval=j0.node("IfStmt",1151,val_peek(2),val_peek(0)); }
break;
case 65:
//#line 81 "j0gram.y"
{ yyval=j0.node("IfElseStmt",1160,val_peek(4),val_peek(2),val_peek(0)); }
break;
case 66:
//#line 82 "j0gram.y"
{ yyval=j0.node("IfElseStmt",1161,val_peek(3),val_peek(2),val_peek(0)); }
break;
case 67:
//#line 84 "j0gram.y"
{ yyval=j0.node("WhileStmt",1210,val_peek(2),val_peek(0)); }
break;
case 68:
//#line 85 "j0gram.y"
{ yyval=j0.node("WhileStmt",1211,val_peek(1),val_peek(0)); }
break;
case 69:
//#line 87 "j0gram.y"
{
    yyval=j0.node("DoWhileStmt",1212,val_peek(1),val_peek(4)); }
break;
case 70:
//#line 89 "j0gram.y"
{
        yyval=j0.node("DoWhileStmt",1213,val_peek(0),val_peek(2)); }
break;
case 71:
//#line 110 "j0gram.y"
{ yyval=j0.node("ForStmt",1220,val_peek(2),val_peek(0)); }
break;
case 72:
//#line 111 "j0gram.y"
{ yyval=j0.node("ForStmt",1221,val_peek(1),val_peek(0)); }
break;
case 76:
//#line 115 "j0gram.y"
{ yyval=j0.node("ForShort",1226,val_peek(0)); }
break;
case 77:
//#line 116 "j0gram.y"
{ yyval=j0.node("ForNormal",1222,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 78:
//#line 117 "j0gram.y"
{ yyval=j0.node("ForFull",1223,val_peek(4),val_peek(3),val_peek(2),val_peek(0)); }
break;
case 79:
//#line 119 "j0gram.y"
{ yyval=j0.node("ForVarInit",1224,val_peek(2),val_peek(0)); }
break;
case 80:
//#line 120 "j0gram.y"
{ yyval=j0.node("ForVarInit",1225,val_peek(0)); }
break;
case 86:
//#line 126 "j0gram.y"
{
  yyval=j0.node("StmtExprList",1230,val_peek(2),val_peek(0)); }
break;
case 88:
//#line 129 "j0gram.y"
{
  yyval=j0.node("BreakStmt",1240,val_peek(1)); }
break;
case 89:
//#line 131 "j0gram.y"
{ yyval=j0.node("ReturnStmt",1250,val_peek(0)); }
break;
case 90:
//#line 132 "j0gram.y"
{ yyval=j0.node("ReturnStmt",1251);}
break;
case 94:
//#line 134 "j0gram.y"
{
  yyval=val_peek(1);}
break;
case 100:
//#line 138 "j0gram.y"
{
  yyval=j0.node("FieldAccess",1280,val_peek(2),val_peek(0)); }
break;
case 103:
//#line 142 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1300,val_peek(1),val_peek(0)); }
break;
case 104:
//#line 144 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1301,val_peek(1),val_peek(0)); }
break;
case 107:
//#line 148 "j0gram.y"
{
      yyval=j0.node("MulExpr",1310,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 108:
//#line 150 "j0gram.y"
{
      yyval=j0.node("MulExpr",1311,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 109:
//#line 152 "j0gram.y"
{
      yyval=j0.node("MulExpr",1312,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 111:
//#line 155 "j0gram.y"
{
      yyval=j0.node("AddExpr",1320,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 112:
//#line 157 "j0gram.y"
{
      yyval=j0.node("AddExpr",1321,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 118:
//#line 160 "j0gram.y"
{
  yyval=j0.node("RelExpr",1330,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 120:
//#line 164 "j0gram.y"
{
  yyval=j0.node("EqExpr",1340,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 121:
//#line 166 "j0gram.y"
{
  yyval=j0.node("EqExpr",1341,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 123:
//#line 168 "j0gram.y"
{
  yyval=j0.node("CondAndExpr", 1350, val_peek(2),val_peek(1), val_peek(0)); }
break;
case 125:
//#line 170 "j0gram.y"
{
  yyval=j0.node("CondOrExpr", 1360, val_peek(2),val_peek(1), val_peek(0)); }
break;
case 127:
//#line 174 "j0gram.y"
{
yyval=j0.node("Assignment",1370, val_peek(2), val_peek(1), val_peek(0)); }
break;
case 128:
//#line 176 "j0gram.y"
{ yyval=j0.node("Assignment",1380, val_peek(1), val_peek(0)); }
break;
//#line 1172 "Parser.java"
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
