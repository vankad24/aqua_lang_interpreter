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
    9,    9,    9,    9,    9,   12,   12,   13,   10,   10,
   14,   14,   15,   15,    7,   16,   18,   19,   19,   20,
   20,   21,    8,   17,    1,    1,   22,   22,   23,   11,
   11,   25,   26,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   27,   34,   34,   30,   30,   31,   31,
   32,   33,   38,   38,   38,   39,   39,   40,   40,   41,
   41,   28,   28,   29,   42,   42,   42,   42,   43,   43,
   43,   43,   43,   45,   45,   44,   46,   46,   36,   36,
   47,   47,   48,   48,   48,   49,   49,   49,   49,   50,
   50,   50,   51,   51,   51,   51,   52,   52,   53,   53,
   53,   54,   54,   55,   55,   37,   37,   35,   56,   56,
   57,   57,   57,
};
final static short yylen[] = {                            2,
    1,    4,    3,    2,    1,    2,    1,    1,    1,    3,
    1,    1,    1,    1,    1,    1,    1,    3,    1,    3,
    1,    3,    1,    1,    2,    4,    4,    1,    0,    1,
    3,    2,    2,    3,    1,    0,    1,    2,    1,    1,
    0,    2,    2,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    2,    1,    1,    3,    5,    7,    5,
    5,    9,    1,    1,    0,    1,    0,    1,    0,    1,
    3,    2,    3,    3,    1,    1,    1,    3,    1,    1,
    1,    1,    1,    1,    3,    3,    1,    0,    4,    6,
    1,    1,    2,    2,    1,    1,    3,    3,    3,    1,
    3,    3,    1,    1,    1,    1,    1,    3,    1,    3,
    3,    1,    3,    1,    3,    1,    1,    3,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,   12,   11,    0,    0,    0,    0,   16,   14,   13,
   79,   80,   82,   81,   83,    0,    0,   45,    0,    1,
    0,    0,   17,   44,    0,   37,   39,   53,    0,   46,
   47,   48,   49,   50,   51,   52,    0,   55,    0,    0,
   75,    0,    0,    0,   40,   72,    0,    0,    0,    0,
    0,  117,   77,    0,    0,   95,   96,    0,    0,    0,
    0,    0,    0,   66,    0,    0,    0,    0,   21,    0,
    0,    0,    0,   38,   42,   54,    0,  122,  123,  121,
    0,   73,   64,   70,    0,    0,    0,    0,   76,   93,
   94,    0,    0,    0,    0,    0,    0,  103,  104,  105,
  106,    0,    0,    0,    0,    0,   74,    0,   34,   78,
    0,    0,   18,   84,    0,    0,    0,  118,    0,    0,
    0,    0,   97,   98,   99,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   22,    0,   89,    0,    0,    0,
   71,    0,   60,   61,   85,    0,    0,    0,   90,    0,
    0,   59,    0,   62,
};
final static short yydgoto[] = {                         19,
   20,    0,    0,    0,    0,    0,    0,    0,   21,   70,
   46,   88,   23,   71,    0,    0,   24,    0,    0,    0,
    0,   25,   26,   27,   28,   29,   30,   31,   32,   33,
   34,   35,   36,   37,   38,   53,   64,   85,   65,  150,
   86,   55,   41,   42,  115,  116,   56,   57,   58,   59,
  102,   60,   61,   62,   63,   43,   81,
};
final static short yysindex[] = {                      1290,
  -55,    0,    0,  -23,  -18,   73,   -3,    0,    0,    0,
    0,    0,    0,    0,    0, 1290,   73,    0,    0,    0,
 -226,  -10,    0,    0, 1290,    0,    0,    0,   -9,    0,
    0,    0,    0,    0,    0,    0,   -9,    0,    0,   10,
    0,    0,  -32,   -9,    0,    0,  300,   73,   73,   73,
  -10,    0,    0,  -65,   10,    0,    0,  -16,  -25,  -46,
 -244, -221, -215,    0,   -9,   73,  -57,   26,    0,   35,
   -7, -185,   73,    0,    0,    0, -180,    0,    0,    0,
   73,    0,    0,    0,   32,   49,   62,  -10,    0,    0,
    0, -156,   73,   73,   73,   73,   73,    0,    0,    0,
    0,   73,   73,   73,   73,   73,    0,   64,    0,    0,
 -226,   15,    0,    0,   65,   70,   72,    0,   73,  -38,
 1290, 1290,    0,    0,    0,  -16,  -16,  -25,  -46,  -46,
 -244, -221, 1290,   -7,    0,   73,    0,   73,   60,  -10,
    0, -140,    0,    0,    0,   80,  -38, 1290,    0,   82,
   49,    0,  -65,    0,
};
final static short yyrindex[] = {                       127,
 1352,    0,    0,    0,    0,  722,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    3,    0,    0,    0,    0,
    0,  -58,    0,    0,   19,    0,    0,    0, 1085,    0,
    0,    0,    0,    0,    0,    0, 1085,    0, 1110,    0,
    0,    1,    0, 1085,    0,    0,   71,    0,    0,    0,
   55,    0,    0,    0,   95,    0,    0,  377,  454,  504,
  796,  846, 1136,    0, 1085,    0,    0,    0,    0, 1198,
 1157,    0,   88,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   74,    0,  122,    0,    0,
    0, 1239,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   90,    0,   28,    0,   75,    0,
  -37,    0,    0,    0,    0,  404,  429,  479,  746,  771,
  821,  872,    0, 1178,    0,    0,    0,   88,    0,   40,
    0, 1265,    0,    0,    0,    0,  100,    0,    0,    0,
  106,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  132,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   20, 1571,    0,   38,    0,    0,  -43,    0,    0,    0,
    0,    0,  131, -109,    0,  105,    0,    0,    0,    0,
    0,    0,    0,  -40,   77,  435,  411,    0,   41,    0,
   14,  465,    0,   96,    0,   24,    0,  -17,  -45,   66,
    0,  -50,   67,   68,    0,    0,    0,
};
final static int YYTABLESIZE=1719;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         78,
   76,   17,  119,   45,   78,   78,   84,   78,   78,   78,
   92,  142,  143,  100,   50,  101,   47,   97,   35,   96,
   95,   48,   78,  144,   78,   93,   49,   86,   80,   73,
   94,   90,   91,  103,  104,   72,   66,   76,  152,   69,
   76,   76,   76,   76,   76,   76,   76,   76,   75,   45,
  126,  127,  129,  130,   92,   77,   76,   16,  105,   76,
   76,  120,   76,   82,   86,  106,  110,  109,   86,   86,
   86,   86,   86,   86,   86,  123,  124,  125,  111,  141,
  113,   52,   52,  112,  107,  117,   86,   86,   86,   86,
  119,   92,  120,   52,   91,   92,   92,   92,   92,   92,
  119,   92,  121,  122,  133,   50,   84,  135,  136,  154,
  137,  138,   17,   92,   92,  119,   92,   49,  147,  148,
  149,   92,  153,   76,   52,   76,   36,   36,   88,   65,
   87,   91,   63,   67,   91,   91,   91,   91,   91,   91,
   69,   91,   52,   35,   89,   89,   68,   67,  134,   52,
   86,   83,   86,   91,   91,   74,   91,   52,   92,  139,
  151,  146,   92,   92,   92,   92,   92,  128,   92,    0,
    0,  131,    0,  132,    0,    0,    0,   92,    0,   92,
   92,   92,    0,   92,    0,    0,    0,    0,   89,   89,
   89,   89,   89,    0,    0,   52,    0,   89,   89,   89,
   89,   89,    0,    0,    0,    0,    0,   15,    0,    0,
   44,    0,   52,    0,   52,    0,    0,   91,    0,   91,
    0,    0,    0,  119,  119,    0,    0,    8,    0,   98,
   99,    0,   11,   12,   13,   14,   15,    0,   78,   78,
   78,   78,   78,   78,   92,    0,   92,    8,    0,   78,
   79,    0,   11,   12,   13,   14,   15,   76,   76,   76,
   76,   76,   76,   76,    0,   76,   76,    0,    0,   76,
   76,   76,   76,   76,   76,   76,   76,   76,   76,   76,
   76,   76,  120,  120,   86,   86,   86,   86,   86,   86,
   86,    0,   86,   86,    0,    0,   86,   86,   86,   86,
   86,   86,   86,   86,   86,   86,   86,   86,   86,   86,
   86,   92,   92,   92,   92,   92,   92,   92,    0,   92,
   92,  119,  119,   92,   92,   92,   92,   92,   92,   92,
   92,   92,   92,   92,   92,   92,  119,  119,    8,   17,
    0,    0,    0,   11,   12,   13,   14,   15,    0,    0,
    0,   91,   91,   91,   91,   91,   91,   91,    0,   91,
   91,    0,    0,   91,   91,   91,   91,   91,   91,   91,
   91,   91,   91,   91,   91,   91,  100,    0,   92,   92,
   92,   92,   92,   92,   92,    0,   92,   92,    0,    0,
   92,   92,   92,   92,   92,   92,   92,   92,   92,   92,
   92,   92,   92,  102,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   54,  100,  100,    0,  100,
  100,  100,    0,    0,    0,    0,    0,   68,  101,    0,
    0,    0,    0,    0,   39,  100,  100,    0,  100,    0,
    0,    0,    0,  102,  102,    0,  102,  102,  102,    0,
   39,    0,    0,  107,    0,    0,    0,    0,   87,   39,
    0,    0,  102,  102,   40,  102,    0,    0,  101,  101,
    0,  101,  101,  101,    0,    0,  108,    0,  108,    0,
   40,   39,    0,  114,    0,    0,    0,  101,  101,   40,
  101,  118,    0,  107,  107,    0,    0,  107,    0,  100,
    0,  100,    0,  109,    0,    0,    0,    0,    0,    0,
    0,   40,  107,  107,    0,  107,    0,    0,  108,  108,
    0,    0,  108,    0,    0,    0,  102,    0,  102,    0,
    0,    0,    0,    0,    0,    0,    0,  108,  108,    0,
  108,    0,    0,  109,  109,    0,  145,  109,  114,    0,
    0,  101,    0,  101,   39,   39,   39,    2,    3,    0,
    0,    0,  109,    0,    0,    8,    0,   39,    9,   10,
   11,   12,   13,   14,   15,    0,  107,    0,  107,    0,
    0,   39,   39,    0,   40,   40,   40,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   40,    0,    0,
    0,  108,    0,  108,    0,    0,    0,    0,    0,    0,
    0,   40,   40,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  109,    0,  109,    0,
    0,    0,    0,  100,  100,  100,  100,  100,  100,  100,
    0,  100,  100,    0,    0,  100,  100,  100,  100,  100,
  100,  100,  100,  100,  100,  100,  100,  100,    0,    0,
  102,  102,  102,  102,  102,  102,  102,    0,  102,  102,
    0,    0,  102,  102,  102,  102,  102,  102,  102,  102,
  102,  102,  102,  102,  102,  101,  101,  101,  101,  101,
  101,  101,    0,  101,  101,    0,    0,  101,  101,  101,
  101,  101,  101,  101,  101,  101,  101,  101,  101,  101,
  107,  107,  107,  107,  107,  107,  107,    0,  107,  107,
    0,   67,  107,  107,  107,  107,  107,  107,  107,  107,
  107,  107,  107,  107,  107,  108,  108,  108,  108,  108,
  108,  108,    0,  108,  108,  110,    0,  108,  108,  108,
  108,  108,  108,  108,  108,  108,  108,  108,  108,  108,
  109,  109,  109,  109,  109,  109,  109,    0,  109,  109,
  111,    0,  109,  109,  109,  109,  109,  109,  109,    0,
   67,  109,  109,  109,  109,  110,  110,    0,    0,  110,
    0,    0,    0,    0,    0,  112,    0,    0,    0,    0,
    0,    0,    0,    0,  110,    0,    0,    0,    0,    0,
  111,  111,    0,    0,  111,    0,    0,    0,    0,    0,
  113,    0,    0,    0,    0,    0,    0,    0,    0,  111,
    0,    0,    0,    0,    0,  112,  112,    0,    0,  112,
    0,    0,    0,    0,   67,  114,   67,    0,    0,    0,
    0,    0,    0,    0,  112,    0,    0,    0,    0,    0,
  113,  113,    0,    0,  113,    0,    0,    0,  110,    0,
  110,  115,    0,    0,    0,    0,    0,    0,    0,  113,
    0,    0,    0,    0,    0,  114,  114,    0,    0,  114,
    0,    0,    0,  111,    0,  111,    0,    0,    0,    0,
    0,    0,    0,    0,  114,    0,    0,    0,    0,    0,
    0,  115,  115,    0,    0,  115,    0,    0,  112,    0,
  112,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  115,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  113,    0,  113,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  114,    0,
  114,    0,    0,    0,    0,    0,    0,    0,   67,   67,
   67,   67,   67,   67,   67,    0,   67,    0,    0,    0,
   67,   67,    0,    0,  115,    0,  115,    0,    0,    0,
    0,    0,  110,  110,  110,  110,  110,  110,  110,    0,
  110,  110,    0,    0,  110,  110,  110,  110,  110,  110,
  110,    0,    0,  110,  110,  110,  110,  111,  111,  111,
  111,  111,  111,  111,    0,  111,  111,    0,    0,  111,
  111,  111,  111,  111,  111,  111,    0,    0,  111,  111,
  111,  111,  112,  112,  112,  112,  112,  112,  112,    0,
  112,  112,    0,    0,  112,  112,  112,  112,  112,  112,
  112,    0,    0,    0,    0,  112,  112,  113,  113,  113,
  113,  113,  113,  113,   41,  113,  113,    0,    0,  113,
  113,  113,  113,  113,  113,  113,    0,    0,    0,    0,
  113,  113,  114,  114,  114,  114,  114,  114,  114,   56,
  114,  114,    0,    0,  114,  114,  114,  114,  114,  114,
  114,    0,    0,    0,   41,    0,  114,    0,  115,  115,
  115,  115,  115,  115,  115,  116,  115,  115,    0,    0,
  115,  115,  115,  115,  115,  115,  115,    0,    0,   56,
   56,    0,  115,   56,    0,   77,   19,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   56,    0,
    0,    0,    0,    0,    0,  116,  116,   20,    0,  116,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  116,    0,   19,   43,    0,    0,
   19,    0,    0,    0,    0,    0,    0,   41,    0,   41,
    0,    0,    0,    0,    0,   19,    0,   20,    0,    0,
    0,   20,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   56,    0,   56,    0,   20,   43,   57,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   43,    0,  116,    0,
  116,    0,    0,    0,   58,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   57,   19,
    0,   19,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   57,    0,    0,
   20,    0,   20,    0,   58,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   43,    0,   43,   58,    0,    0,    0,    0,    0,   17,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   41,   41,   41,   41,   41,   41,   41,   18,   41,
   41,   41,    0,   41,   41,   41,   41,   41,   41,   41,
    0,   57,    0,   57,    0,    0,   56,   56,   56,   56,
   56,   56,   56,    0,   56,   56,    0,    0,   56,   56,
   56,   56,   56,   56,   56,    0,    0,   58,    0,   58,
    0,   41,  116,  116,  116,  116,  116,  116,  116,    0,
  116,  116,    0,    0,  116,  116,  116,  116,  116,  116,
  116,    0,   16,   19,   19,   19,   19,   19,   19,   19,
    0,   19,   19,    0,    0,   19,   19,   19,   19,   19,
   19,   19,    0,    0,   20,   20,   20,   20,   20,   20,
   20,    0,   20,   20,    0,    0,   20,   20,   20,   20,
   20,   20,   20,    0,   43,   43,   43,   43,   43,   43,
   43,    0,   43,   43,    0,    0,   43,   43,   43,   43,
   43,   43,   43,    0,   41,    0,   41,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   57,   57,   57,    0,   57,
   57,   57,    0,   57,   57,    0,    0,   57,   57,   57,
   57,   57,   57,   57,    0,    0,    0,    0,    0,    0,
    0,   58,   58,   58,    0,   58,   58,   58,    0,   58,
   58,    0,    0,   58,   58,   58,   58,   58,   58,   58,
    0,    0,    0,    0,    0,    0,    1,    2,    3,    0,
    4,    5,    6,    0,    7,    8,    0,    0,    9,   10,
   11,   12,   13,   14,   15,    0,    0,    0,    0,    0,
   22,    0,    0,    0,    0,   51,   51,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   22,   51,    0,    0,
    0,    0,    0,    0,    0,   22,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   41,   41,
   41,   41,   41,   41,   41,    0,   41,   22,   51,    0,
   41,   41,   41,   41,   41,   41,   41,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   51,    0,    0,    0,
    0,    0,    0,   51,    0,    0,    0,    0,    0,    0,
    0,   51,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   51,
  140,   22,   22,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   22,    0,    0,   51,    0,   51,    0,
    0,    0,    0,    0,    0,    0,    0,  140,   22,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
    0,   40,   61,   59,   42,   43,   47,   45,   46,   47,
   54,  121,  122,   60,   33,   62,   40,   43,    0,   45,
   37,   40,   60,  133,   62,   42,   45,    0,   61,   40,
   47,   49,   50,  278,  279,   46,   40,   37,  148,  266,
   40,   41,   42,   43,   44,   45,   46,   47,   29,   59,
   96,   97,  103,  104,    0,   46,   37,  123,  280,   59,
   60,   61,   62,   44,   37,  281,   41,  125,   41,   42,
   43,   44,   45,   46,   47,   93,   94,   95,   44,  120,
  266,    5,    6,   91,   65,  266,   59,   60,   61,   62,
   59,   37,   44,   17,    0,   41,   42,   43,   44,   45,
   61,   47,   41,  260,   41,   33,  147,   93,   44,  153,
   41,   40,   40,   59,   60,   61,   62,   45,   59,  260,
   41,    0,   41,  123,   48,  125,    0,  125,   41,   59,
   41,   37,   59,   59,   40,   41,   42,   43,   44,   45,
   41,   47,   66,  125,   49,   50,   41,   16,  111,   73,
  123,   47,  125,   59,   60,   25,   62,   81,   37,  119,
  147,  138,   41,   42,   43,   44,   45,  102,   47,   -1,
   -1,  105,   -1,  106,   -1,   -1,   -1,  123,   -1,  125,
   59,   60,   -1,   62,   -1,   -1,   -1,   -1,   93,   94,
   95,   96,   97,   -1,   -1,  119,   -1,  102,  103,  104,
  105,  106,   -1,   -1,   -1,   -1,   -1,  266,   -1,   -1,
  266,   -1,  136,   -1,  138,   -1,   -1,  123,   -1,  125,
   -1,   -1,   -1,  282,  283,   -1,   -1,  266,   -1,  276,
  277,   -1,  271,  272,  273,  274,  275,   -1,  276,  277,
  278,  279,  280,  281,  123,   -1,  125,  266,   -1,  282,
  283,   -1,  271,  272,  273,  274,  275,  257,  258,  259,
  260,  261,  262,  263,   -1,  265,  266,   -1,   -1,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  280,  281,  282,  283,  257,  258,  259,  260,  261,  262,
  263,   -1,  265,  266,   -1,   -1,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  280,  281,  282,
  283,  257,  258,  259,  260,  261,  262,  263,   -1,  265,
  266,  282,  283,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  280,  281,  282,  283,  266,   40,
   -1,   -1,   -1,  271,  272,  273,  274,  275,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,  263,   -1,  265,
  266,   -1,   -1,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  280,  281,    0,   -1,  257,  258,
  259,  260,  261,  262,  263,   -1,  265,  266,   -1,   -1,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  280,  281,    0,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,    5,   40,   41,   -1,   43,
   44,   45,   -1,   -1,   -1,   -1,   -1,   17,    0,   -1,
   -1,   -1,   -1,   -1,    0,   59,   60,   -1,   62,   -1,
   -1,   -1,   -1,   40,   41,   -1,   43,   44,   45,   -1,
   16,   -1,   -1,    0,   -1,   -1,   -1,   -1,   48,   25,
   -1,   -1,   59,   60,    0,   62,   -1,   -1,   40,   41,
   -1,   43,   44,   45,   -1,   -1,   66,   -1,    0,   -1,
   16,   47,   -1,   73,   -1,   -1,   -1,   59,   60,   25,
   62,   81,   -1,   40,   41,   -1,   -1,   44,   -1,  123,
   -1,  125,   -1,    0,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   47,   59,   60,   -1,   62,   -1,   -1,   40,   41,
   -1,   -1,   44,   -1,   -1,   -1,  123,   -1,  125,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   60,   -1,
   62,   -1,   -1,   40,   41,   -1,  136,   44,  138,   -1,
   -1,  123,   -1,  125,  120,  121,  122,  258,  259,   -1,
   -1,   -1,   59,   -1,   -1,  266,   -1,  133,  269,  270,
  271,  272,  273,  274,  275,   -1,  123,   -1,  125,   -1,
   -1,  147,  148,   -1,  120,  121,  122,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  133,   -1,   -1,
   -1,  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  147,  148,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,   -1,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,   -1,   -1,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  280,  281,   -1,   -1,
  257,  258,  259,  260,  261,  262,  263,   -1,  265,  266,
   -1,   -1,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  280,  281,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,   -1,   -1,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  280,  281,
  257,  258,  259,  260,  261,  262,  263,   -1,  265,  266,
   -1,    0,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  280,  281,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,    0,   -1,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  280,  281,
  257,  258,  259,  260,  261,  262,  263,   -1,  265,  266,
    0,   -1,  269,  270,  271,  272,  273,  274,  275,   -1,
   59,  278,  279,  280,  281,   40,   41,   -1,   -1,   44,
   -1,   -1,   -1,   -1,   -1,    0,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   59,   -1,   -1,   -1,   -1,   -1,
   40,   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,   -1,
    0,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,
   -1,   -1,   -1,   -1,   -1,   40,   41,   -1,   -1,   44,
   -1,   -1,   -1,   -1,  123,    0,  125,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   59,   -1,   -1,   -1,   -1,   -1,
   40,   41,   -1,   -1,   44,   -1,   -1,   -1,  123,   -1,
  125,    0,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,
   -1,   -1,   -1,   -1,   -1,   40,   41,   -1,   -1,   44,
   -1,   -1,   -1,  123,   -1,  125,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   59,   -1,   -1,   -1,   -1,   -1,
   -1,   40,   41,   -1,   -1,   44,   -1,   -1,  123,   -1,
  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   59,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  123,   -1,  125,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,
  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,  263,   -1,  265,   -1,   -1,   -1,
  269,  270,   -1,   -1,  123,   -1,  125,   -1,   -1,   -1,
   -1,   -1,  257,  258,  259,  260,  261,  262,  263,   -1,
  265,  266,   -1,   -1,  269,  270,  271,  272,  273,  274,
  275,   -1,   -1,  278,  279,  280,  281,  257,  258,  259,
  260,  261,  262,  263,   -1,  265,  266,   -1,   -1,  269,
  270,  271,  272,  273,  274,  275,   -1,   -1,  278,  279,
  280,  281,  257,  258,  259,  260,  261,  262,  263,   -1,
  265,  266,   -1,   -1,  269,  270,  271,  272,  273,  274,
  275,   -1,   -1,   -1,   -1,  280,  281,  257,  258,  259,
  260,  261,  262,  263,    0,  265,  266,   -1,   -1,  269,
  270,  271,  272,  273,  274,  275,   -1,   -1,   -1,   -1,
  280,  281,  257,  258,  259,  260,  261,  262,  263,    0,
  265,  266,   -1,   -1,  269,  270,  271,  272,  273,  274,
  275,   -1,   -1,   -1,   40,   -1,  281,   -1,  257,  258,
  259,  260,  261,  262,  263,    0,  265,  266,   -1,   -1,
  269,  270,  271,  272,  273,  274,  275,   -1,   -1,   40,
   41,   -1,  281,   44,   -1,   46,    0,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   -1,
   -1,   -1,   -1,   -1,   -1,   40,   41,    0,   -1,   44,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   59,   -1,   40,    0,   -1,   -1,
   44,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,
   -1,   -1,   -1,   -1,   -1,   59,   -1,   40,   -1,   -1,
   -1,   44,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  123,   -1,  125,   -1,   59,   40,    0,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   59,   -1,  123,   -1,
  125,   -1,   -1,   -1,    0,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   40,  123,
   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   -1,   -1,
  123,   -1,  125,   -1,   40,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  123,   -1,  125,   59,   -1,   -1,   -1,   -1,   -1,   40,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,  263,   59,  265,
  266,    0,   -1,  269,  270,  271,  272,  273,  274,  275,
   -1,  123,   -1,  125,   -1,   -1,  257,  258,  259,  260,
  261,  262,  263,   -1,  265,  266,   -1,   -1,  269,  270,
  271,  272,  273,  274,  275,   -1,   -1,  123,   -1,  125,
   -1,   40,  257,  258,  259,  260,  261,  262,  263,   -1,
  265,  266,   -1,   -1,  269,  270,  271,  272,  273,  274,
  275,   -1,  123,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,   -1,   -1,  269,  270,  271,  272,  273,
  274,  275,   -1,   -1,  257,  258,  259,  260,  261,  262,
  263,   -1,  265,  266,   -1,   -1,  269,  270,  271,  272,
  273,  274,  275,   -1,  257,  258,  259,  260,  261,  262,
  263,   -1,  265,  266,   -1,   -1,  269,  270,  271,  272,
  273,  274,  275,   -1,  123,   -1,  125,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,   -1,  261,
  262,  263,   -1,  265,  266,   -1,   -1,  269,  270,  271,
  272,  273,  274,  275,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,   -1,  261,  262,  263,   -1,  265,
  266,   -1,   -1,  269,  270,  271,  272,  273,  274,  275,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,   -1,
  261,  262,  263,   -1,  265,  266,   -1,   -1,  269,  270,
  271,  272,  273,  274,  275,   -1,   -1,   -1,   -1,   -1,
    0,   -1,   -1,   -1,   -1,    5,    6,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   16,   17,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   25,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,  263,   -1,  265,   47,   48,   -1,
  269,  270,  271,  272,  273,  274,  275,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   66,   -1,   -1,   -1,
   -1,   -1,   -1,   73,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   81,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  119,
  120,  121,  122,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  133,   -1,   -1,  136,   -1,  138,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  147,  148,
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
null,null,null,null,null,null,null,null,null,"BREAK","FLOAT","INT","ELSE","FOR",
"IF","RETURN","VOID","WHILE","IDENTIFIER","CLASSNAME","CLASS","STRING","BOOL",
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
case 57:
//#line 79 "j0gram.y"
{ yyval=j0.node("IfStmt",1150,val_peek(1),val_peek(0)); }
break;
case 58:
//#line 80 "j0gram.y"
{ yyval=j0.node("IfStmt",1151,val_peek(2),val_peek(0)); }
break;
case 59:
//#line 81 "j0gram.y"
{ yyval=j0.node("IfElseStmt",1160,val_peek(4),val_peek(2),val_peek(0)); }
break;
case 60:
//#line 82 "j0gram.y"
{ yyval=j0.node("IfElseStmt",1161,val_peek(3),val_peek(2),val_peek(0)); }
break;
case 61:
//#line 84 "j0gram.y"
{
  yyval=j0.node("WhileStmt",1210,val_peek(2),val_peek(0)); }
break;
case 62:
//#line 87 "j0gram.y"
{
  yyval=j0.node("ForStmt",1220,val_peek(6),val_peek(4),val_peek(2),val_peek(0)); }
break;
case 71:
//#line 93 "j0gram.y"
{
  yyval=j0.node("StmtExprList",1230,val_peek(2),val_peek(0)); }
break;
case 73:
//#line 96 "j0gram.y"
{
  yyval=j0.node("BreakStmt",1240,val_peek(1)); }
break;
case 74:
//#line 98 "j0gram.y"
{
  yyval=j0.node("ReturnStmt",1250,val_peek(1)); }
break;
case 78:
//#line 101 "j0gram.y"
{
  yyval=val_peek(1);}
break;
case 85:
//#line 105 "j0gram.y"
{
  yyval=j0.node("ArgList",1270,val_peek(2),val_peek(0)); }
break;
case 86:
//#line 107 "j0gram.y"
{
  yyval=j0.node("FieldAccess",1280,val_peek(2),val_peek(0)); }
break;
case 89:
//#line 111 "j0gram.y"
{
  yyval=j0.node("MethodCall",1290,val_peek(3),val_peek(1)); }
break;
case 90:
//#line 113 "j0gram.y"
{
    yyval=j0.node("MethodCall",1291,val_peek(5),val_peek(3),val_peek(1)); }
break;
case 93:
//#line 118 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1300,val_peek(1),val_peek(0)); }
break;
case 94:
//#line 120 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1301,val_peek(1),val_peek(0)); }
break;
case 97:
//#line 124 "j0gram.y"
{
      yyval=j0.node("MulExpr",1310,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 98:
//#line 126 "j0gram.y"
{
      yyval=j0.node("MulExpr",1311,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 99:
//#line 128 "j0gram.y"
{
      yyval=j0.node("MulExpr",1312,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 101:
//#line 131 "j0gram.y"
{
      yyval=j0.node("AddExpr",1320,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 102:
//#line 133 "j0gram.y"
{
      yyval=j0.node("AddExpr",1321,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 108:
//#line 136 "j0gram.y"
{
  yyval=j0.node("RelExpr",1330,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 110:
//#line 140 "j0gram.y"
{
  yyval=j0.node("EqExpr",1340,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 111:
//#line 142 "j0gram.y"
{
  yyval=j0.node("EqExpr",1341,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 113:
//#line 144 "j0gram.y"
{
  yyval=j0.node("CondAndExpr", 1350, val_peek(2),val_peek(1), val_peek(0)); }
break;
case 115:
//#line 146 "j0gram.y"
{
  yyval=j0.node("CondOrExpr", 1360, val_peek(2),val_peek(1), val_peek(0)); }
break;
case 118:
//#line 150 "j0gram.y"
{
yyval=j0.node("Assignment",1370, val_peek(2), val_peek(1), val_peek(0)); }
break;
//#line 1101 "Parser.java"
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
