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
public final static short INTLIT=273;
public final static short DOUBLELIT=274;
public final static short STRINGLIT=275;
public final static short BOOLLIT=276;
public final static short NULLVAL=277;
public final static short LESSTHANOREQUAL=278;
public final static short GREATERTHANOREQUAL=279;
public final static short ISEQUALTO=280;
public final static short NOTEQUALTO=281;
public final static short LOGICALAND=282;
public final static short LOGICALOR=283;
public final static short INCREMENT=284;
public final static short DECREMENT=285;
public final static short PUBLIC=286;
public final static short STATIC=287;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    2,    3,    3,    4,    4,    5,    5,    5,    6,
    9,    9,    9,    9,    9,   11,   11,   12,   10,   10,
   13,   13,   14,   14,    7,   15,   17,   18,   18,   19,
   19,   20,    8,   16,    1,    1,   21,   21,   22,   24,
   24,   25,   26,   23,   23,   23,   23,   23,   23,   23,
   23,   23,   23,   23,   27,   35,   35,   30,   30,   31,
   31,   33,   33,   32,   32,   34,   34,   39,   39,   39,
   40,   41,   42,   43,   43,   46,   45,   44,   44,   47,
   47,   48,   48,   28,   28,   29,   49,   49,   49,   49,
   50,   50,   50,   50,   50,   52,   52,   51,   53,   53,
   37,   37,   54,   54,   55,   55,   55,   56,   56,   56,
   56,   57,   57,   57,   58,   58,   58,   58,   59,   59,
   60,   60,   60,   61,   61,   62,   62,   38,   36,   63,
   63,   64,   64,   64,
};
final static short yylen[] = {                            2,
    1,    4,    3,    2,    1,    2,    1,    1,    1,    2,
    1,    1,    1,    1,    1,    1,    1,    3,    1,    3,
    1,    3,    1,    1,    2,    4,    4,    1,    0,    1,
    3,    2,    2,    3,    1,    0,    1,    2,    1,    1,
    0,    1,    2,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    3,    5,    7,
    5,    5,    3,    6,    4,    5,    3,    1,    1,    1,
    1,    3,    5,    1,    1,    1,    3,    1,    1,    1,
    0,    1,    3,    2,    3,    3,    1,    1,    1,    3,
    1,    1,    1,    1,    1,    1,    3,    3,    1,    0,
    4,    6,    1,    1,    2,    2,    1,    1,    3,    3,
    3,    1,    3,    3,    1,    1,    1,    1,    1,    3,
    1,    3,    3,    1,    3,    1,    3,    1,    3,    1,
    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,   12,   11,    0,    0,    0,    0,    0,   16,   14,
   13,   91,   92,   94,   93,   95,    0,    0,   45,    0,
    1,    0,    0,   17,   44,    0,   37,   39,   54,   42,
   46,   47,   48,   49,   50,   51,   52,   53,   55,   56,
    0,    0,   87,    0,    0,    0,   40,   84,    0,    0,
    0,    0,    0,   89,   71,    0,   68,   69,   70,    0,
   74,   75,    0,   88,  107,  108,    0,    0,    0,    0,
    0,    0,    0,    0,   80,    0,    0,    0,    0,    0,
    0,   21,    0,    0,    0,    0,   38,    0,  133,  134,
  132,    0,   85,    0,    0,    0,  105,  106,   67,   79,
   78,    0,    0,    0,    0,    0,    0,  115,  116,  117,
  118,    0,    0,    0,    0,    0,    0,    0,   86,    0,
   63,    0,   34,   90,    0,    0,   18,   96,    0,    0,
    0,  129,   77,    0,    0,  109,  110,  111,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   65,
    0,   22,    0,  101,    0,   66,    0,    0,   61,   62,
    0,   97,    0,   73,    0,    0,  102,   60,
};
final static short yydgoto[] = {                         20,
   21,    0,    0,    0,    0,    0,    0,    0,   22,   83,
   53,   24,   84,    0,    0,   25,    0,    0,    0,    0,
   26,   27,   28,   48,   29,   30,   31,   32,   33,   34,
   35,   36,   37,   38,   39,   40,   54,  128,   56,   57,
   58,   59,   60,  102,   61,   62,   76,    0,   63,   43,
   64,  129,  130,   65,   66,   67,   68,  112,   69,   70,
   71,   72,   45,   92,
};
final static short yysindex[] = {                      1338,
  -52,    0,    0,  -21,  168,  511,  559, -101,    0,    0,
    0,    0,    0,    0,    0,    0, 1338,  511,    0,    0,
    0, -228,   -7,    0,    0, 1338,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    3,    0,    0,  -50,   -8,    0,    0,   -3,  914,
  511,  511,   -7,    0,    0, -101,    0,    0,    0,  -44,
    0,    0,    3,    0,    0,    0,   -6,  -11,  -47, -227,
 -216, -215,  511, -101,    0,   -8,  511, -101, -189,  -46,
   42,    0,   40,    2, -177,  511,    0, -173,    0,    0,
    0,  511,    0,  511,   42,   55,    0,    0,    0,    0,
    0,  511,  511,  511,  511,  511,  511,    0,    0,    0,
    0,  511,  511,  511,  511,  511,   56, -155,    0,   65,
    0,  990,    0,    0, -228,   15,    0,    0,   66,   70,
   72,    0,    0, -101,   60,    0,    0,    0,   -6,   -6,
  -11,  -47,  -47, -227, -216, 1338, 1338, 1338,  511,    0,
    2,    0,  511,    0,  511,    0,  511, -147,    0,    0,
   73,    0,   79,    0, 1338,    0,    0,    0,
};
final static short yyrindex[] = {                       121,
 1310,    0,    0,    0,    0,  351,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   -2,    0,    0,    0,
    0,    0,  -59,    0,    0,   26,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  970,    0,    0,  133,    0, 1222,    0,    0,  -37,    0,
    0,    0,   57,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   30,    0,    0,    0,   87,  439,  495,  815,
  869,  944,    0,    0,    0, 1222,    0,    0,    0,    0,
    0,    0, 1197, 1026,    0,   81,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, 1250,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   88,    0,
    1,    0,    0,    0,  -14,    0,    0,    0,  125,  371,
  466,  537,  781,  842,  902,   96,    0,   96,    0,    0,
 1154,    0,    0,    0,   81,    0,    0, 1271,    0,    0,
    0,    0,    0,    0,    0,  409,    0,    0,
};
final static short yygindex[] = {                         0,
  117,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   52,    0,   10,    0,    0,   29,    0,    0,    0,    0,
    0,  110, -130,  -26,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  421, 1548,   90,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  826,    0,
  831,    0,  -18,    0,  -23,  -51,   32,    0,  -49,   33,
   34,    0,    0,    0,
};
final static int YYTABLESIZE=1705;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         16,
   98,  130,   16,   16,   16,   16,   47,   16,   16,   16,
   91,   52,  110,  101,  111,  158,  159,  160,   50,   93,
   76,   17,   16,   51,   16,   35,   72,   97,   98,  103,
  105,  107,   86,  106,  168,  103,   79,   98,   85,   82,
  104,   98,   98,   98,   98,   98,   98,   98,   88,  119,
   47,   23,  113,  114,  139,  140,  104,   94,   98,   98,
   98,   98,   98,  142,  143,  115,  103,  116,   23,  103,
  103,  103,  103,  103,  103,  122,  103,   23,  123,  136,
  137,  138,  124,  125,   99,   16,  112,  103,  103,  103,
  127,  103,  126,  104,  131,  134,  146,  104,  104,  104,
  104,  104,  118,  104,  147,  148,  121,  152,   72,  153,
  154,  155,  165,  166,  104,  104,  104,  157,  104,  167,
   36,  100,   36,   98,  114,   98,  112,  112,   99,  112,
  112,  112,   90,   80,  151,   87,  163,   90,   90,   96,
   90,   90,   90,  141,  112,  112,  112,  144,  112,  145,
   35,    0,  103,    0,  103,   90,    0,   90,    0,    0,
    0,    0,  156,    0,  114,  114,    0,  114,  114,  114,
    0,    0,    0,    0,    0,    0,    0,    0,   88,  104,
    0,  104,  114,  114,  114,    0,  114,    0,    0,    0,
    0,    0,    0,  131,    0,    0,    0,   23,   23,   23,
   52,    0,    0,    0,    0,    0,    0,   73,   15,  112,
    0,  112,   51,    0,    0,   46,   23,    0,    0,    0,
    0,    0,  100,    0,  130,  130,    0,    0,    0,   76,
  108,  109,    0,   89,   90,    0,    0,    0,    0,    0,
   16,   16,   16,   16,   16,   16,   49,  114,    0,  114,
    0,   12,   13,   14,   15,   16,    0,   98,   98,   98,
   98,   98,   98,   98,    0,   98,   98,   98,   98,    0,
    0,   98,   98,   98,   98,   98,   98,   98,   98,   98,
   98,   98,   98,   98,   98,   98,  103,  103,  103,  103,
  103,  103,  103,    0,  103,  103,  103,  103,    0,    0,
  103,  103,  103,  103,  103,  103,  103,  103,  103,  103,
  103,  103,  103,  104,  104,  104,  104,  104,  104,  104,
    0,  104,  104,  104,  104,    0,    0,  104,  104,  104,
  104,  104,  104,  104,  104,  104,  104,  104,  104,  104,
    0,    0,    0,  112,  112,  112,  112,  112,  112,  112,
   81,  112,  112,  112,  112,    0,    0,  112,  112,  112,
  112,  112,  112,  112,  112,  112,  112,  112,  112,  112,
  113,    0,    0,   90,   90,   90,   90,   90,   90,    0,
    0,  114,  114,  114,  114,  114,  114,  114,    0,  114,
  114,  114,  114,    0,    0,  114,  114,  114,  114,  114,
  114,  114,  114,  114,  114,  114,  114,  114,   64,   81,
  113,  113,    0,  113,  113,  113,  131,  131,    0,    0,
   41,    0,    0,    0,    0,    0,    0,    0,  113,  113,
  113,    0,  113,    0,    0,    9,    0,   41,  119,    0,
   12,   13,   14,   15,   16,   90,   41,    0,   64,    0,
   90,   90,    0,   90,   90,   90,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  120,    0,   64,   90,    0,
   90,    0,    0,   81,    0,   81,    0,    0,  119,  119,
    0,    0,  119,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  113,  121,  113,  119,  119,  119,    0,
  119,    0,    0,    0,    0,  120,  120,    0,    0,  120,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  120,  120,  120,    0,  120,    0,    0,
    0,   64,    0,   64,  121,  121,  122,    0,  121,    0,
    0,    0,    0,   52,    0,    0,    0,    0,    0,    0,
   18,    0,  121,  121,    0,   51,    0,    0,    0,    0,
    0,  119,    0,  119,    0,    0,   41,   41,   41,    0,
    0,    0,    0,    0,    0,    0,  122,  122,    0,    0,
  122,    0,    0,    0,    0,   41,    0,    0,  120,    0,
  120,   52,    0,    0,  122,  122,    0,    0,   77,    0,
    0,    0,    0,   51,    0,    0,    0,   81,   81,   81,
   81,   81,   81,   81,    0,   81,   81,  121,    0,  121,
    0,   81,   81,    0,    0,    0,    0,  113,  113,  113,
  113,  113,  113,  113,    0,  113,  113,  113,  113,    0,
    0,  113,  113,  113,  113,  113,  113,  113,  113,  113,
  113,  113,  113,  113,    0,    0,    0,    0,    0,  122,
    0,  122,    0,    0,    0,   64,   64,   64,   64,   64,
   64,   64,    0,   64,   64,    0,   64,    0,    0,   64,
   64,   64,   64,   64,   64,   64,   90,   90,   90,   90,
   90,   90,    0,    0,    0,  119,  119,  119,  119,  119,
  119,  119,    0,  119,  119,  119,  119,    0,    0,  119,
  119,  119,  119,  119,  119,  119,  119,  119,  119,  119,
  119,  119,  120,  120,  120,  120,  120,  120,  120,    0,
  120,  120,  120,  120,    0,    0,  120,  120,  120,  120,
  120,  120,  120,  120,  120,  120,  120,  120,  120,    0,
    0,  121,  121,  121,  121,  121,  121,  121,    0,  121,
  121,  121,  121,    0,    0,  121,  121,  121,  121,  121,
  121,  121,    0,    0,  121,  121,  121,  121,    9,    0,
  123,    0,    0,   12,   13,   14,   15,   16,    0,    0,
    0,    0,    0,  122,  122,  122,  122,  122,  122,  122,
    0,  122,  122,  122,  122,    0,    0,  122,  122,  122,
  122,  122,  122,  122,  124,    0,  122,  122,  122,  122,
  123,  123,    0,    0,  123,   42,    9,    0,    0,    0,
   44,   12,   13,   14,   15,   16,    0,    0,  123,  123,
    0,  125,   42,    0,    0,    0,    0,   44,    0,    0,
    0,   42,    0,    0,  124,  124,   44,    0,  124,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  126,    0,
    0,    0,  124,  124,    0,    0,    0,    0,    0,    0,
    0,  125,  125,    0,    0,  125,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  125,
  125,  127,    0,  123,    0,  123,    0,    0,  126,  126,
    0,    0,  126,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  126,  126,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  124,    0,  124,
    0,  127,  127,  128,    0,  127,   52,    0,    0,    0,
    0,    0,    0,   18,    0,    0,    0,    0,   51,  127,
  127,    0,    0,    0,  125,    0,  125,    0,    0,   57,
    0,   42,   42,   42,    0,    0,   44,   44,   44,    0,
    0,    0,    0,  128,  128,    0,    0,  128,    0,    0,
   42,  126,    0,  126,    0,   44,    0,    0,    0,    0,
    0,  128,  128,    0,    0,    0,    0,    0,    0,   57,
    0,    0,    0,    0,    0,   89,    0,    0,    0,    0,
    0,    0,   52,    0,  127,   19,  127,    0,   57,  149,
    0,    0,    0,    0,   51,    0,    0,  123,  123,  123,
  123,  123,  123,  123,    0,  123,  123,  123,  123,    0,
    0,  123,  123,  123,  123,  123,  123,  123,    0,    0,
  123,  123,  123,  123,    0,   19,  128,    0,  128,   19,
    0,  124,  124,  124,  124,  124,  124,  124,    0,  124,
  124,  124,  124,    0,   19,  124,  124,  124,  124,  124,
  124,  124,   57,    0,   57,    0,  124,  124,  125,  125,
  125,  125,  125,  125,  125,    0,  125,  125,  125,  125,
    0,    0,  125,  125,  125,  125,  125,  125,  125,    0,
    0,    0,    0,  125,  125,  126,  126,  126,  126,  126,
  126,  126,    0,  126,  126,  126,  126,    0,    0,  126,
  126,  126,  126,  126,  126,  126,    0,    0,   19,    0,
   19,  126,    0,   20,    0,    0,    0,    0,  127,  127,
  127,  127,  127,  127,  127,    0,  127,  127,  127,  127,
    0,    0,  127,  127,  127,  127,  127,  127,  127,    0,
    0,   49,    0,    0,  127,    0,   12,   13,   14,   15,
   16,    0,    0,   20,    0,    0,   43,   20,    0,    0,
  128,  128,  128,  128,  128,  128,  128,    0,  128,  128,
  128,  128,   20,    0,  128,  128,  128,  128,  128,  128,
  128,   41,    0,    0,    0,    0,   57,   57,   57,   57,
   57,   57,   57,    0,   57,   57,   43,   57,    0,    0,
   57,   57,   57,   57,   57,   57,   57,    0,    0,   58,
    0,    0,    0,    0,    0,   43,    0,    9,    0,    0,
    0,   41,   12,   13,   14,   15,   16,    0,    0,    0,
   59,    0,    0,    0,    0,    0,   20,    0,   20,    0,
    0,    0,   19,   19,   19,   19,   19,   19,   19,   58,
   19,   19,    0,   19,    0,    0,   19,   19,   19,   19,
   19,   19,   19,    0,    0,    0,    0,    0,   58,   41,
   59,    0,    0,    0,    0,    0,    0,    0,    0,   43,
    0,   43,    0,    0,    0,    0,    0,    0,    0,   59,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   41,    0,   41,    0,    0,   41,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   58,    0,   58,    0,    0,   18,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   59,    0,   59,   19,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   20,   20,   20,   20,   20,   20,   20,    0,   20,   20,
    0,   20,    0,    0,   20,   20,   20,   20,   20,   20,
   20,    0,   41,    0,   41,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   43,   43,   43,   43,   43,   43,   43,
   17,   43,   43,    0,   43,    0,    0,   43,   43,   43,
   43,   43,   43,   43,    0,    0,    0,    0,   41,   41,
   41,   41,   41,   41,   41,    0,   41,   41,    0,   41,
    0,    0,   41,   41,   41,   41,   41,   41,   41,    0,
    0,    0,    0,    0,    0,    0,   58,   58,   58,    0,
   58,   58,   58,    0,   58,   58,    0,   58,    0,    0,
   58,   58,   58,   58,   58,   58,   58,   59,   59,   59,
    0,   59,   59,   59,    0,   59,   59,    0,   59,    0,
    0,   59,   59,   59,   59,   59,   59,   59,    0,    0,
    0,   55,   74,   75,   78,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   81,   41,   41,   41,   41,
   41,   41,   41,    0,   41,   41,    0,    0,    0,    0,
   41,   41,   41,   41,   41,   41,   41,    0,    0,    0,
    0,    0,    0,    0,    1,    2,    3,   95,    4,    5,
    6,    0,    7,    8,    0,    9,    0,    0,   10,   11,
   12,   13,   14,   15,   16,    0,    0,    0,    0,    0,
  117,    0,    0,    0,  120,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  132,
    0,  133,    0,    0,    0,    0,    0,    0,    0,  135,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  150,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  161,    0,    0,    0,
  162,    0,    0,    0,  164,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
    0,   61,   40,   41,   42,   43,   59,   45,   46,   47,
   61,   33,   60,   58,   62,  146,  147,  148,   40,   46,
   58,  123,   60,   45,   62,    0,   41,   51,   52,    0,
   37,   43,   40,   45,  165,   42,    8,   37,   46,  268,
   47,   41,   42,   43,   44,   45,   46,   47,   46,   76,
   59,    0,  280,  281,  106,  107,    0,   61,   58,   59,
   60,   61,   62,  113,  114,  282,   37,  283,   17,   40,
   41,   42,   43,   44,   45,  265,   47,   26,  125,  103,
  104,  105,   41,   44,   56,  123,    0,   58,   59,   60,
  268,   62,   91,   37,  268,   41,   41,   41,   42,   43,
   44,   45,   74,   47,  260,   41,   78,   93,  123,   44,
   41,   40,  260,   41,   58,   59,   60,   58,   62,   41,
    0,   41,  125,  123,    0,  125,   40,   41,   41,   43,
   44,   45,   37,   17,  125,   26,  155,   42,   43,   50,
   45,   46,   47,  112,   58,   59,   60,  115,   62,  116,
  125,   -1,  123,   -1,  125,   60,   -1,   62,   -1,   -1,
   -1,   -1,  134,   -1,   40,   41,   -1,   43,   44,   45,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   46,  123,
   -1,  125,   58,   59,   60,   -1,   62,   -1,   -1,   -1,
   -1,   -1,   -1,   61,   -1,   -1,   -1,  146,  147,  148,
   33,   -1,   -1,   -1,   -1,   -1,   -1,   40,  268,  123,
   -1,  125,   45,   -1,   -1,  268,  165,   -1,   -1,   -1,
   -1,   -1,  267,   -1,  284,  285,   -1,   -1,   -1,  267,
  278,  279,   -1,  284,  285,   -1,   -1,   -1,   -1,   -1,
  278,  279,  280,  281,  282,  283,  268,  123,   -1,  125,
   -1,  273,  274,  275,  276,  277,   -1,  257,  258,  259,
  260,  261,  262,  263,   -1,  265,  266,  267,  268,   -1,
   -1,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  280,  281,  282,  283,  284,  285,  257,  258,  259,  260,
  261,  262,  263,   -1,  265,  266,  267,  268,   -1,   -1,
  271,  272,  273,  274,  275,  276,  277,  278,  279,  280,
  281,  282,  283,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,   -1,   -1,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  280,  281,  282,  283,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,  263,
    0,  265,  266,  267,  268,   -1,   -1,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  280,  281,  282,  283,
    0,   -1,   -1,  278,  279,  280,  281,  282,  283,   -1,
   -1,  257,  258,  259,  260,  261,  262,  263,   -1,  265,
  266,  267,  268,   -1,   -1,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  280,  281,  282,  283,    0,   59,
   40,   41,   -1,   43,   44,   45,  284,  285,   -1,   -1,
    0,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,
   60,   -1,   62,   -1,   -1,  268,   -1,   17,    0,   -1,
  273,  274,  275,  276,  277,   37,   26,   -1,   40,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,    0,   -1,   59,   60,   -1,
   62,   -1,   -1,  123,   -1,  125,   -1,   -1,   40,   41,
   -1,   -1,   44,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  123,    0,  125,   58,   59,   60,   -1,
   62,   -1,   -1,   -1,   -1,   40,   41,   -1,   -1,   44,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   58,   59,   60,   -1,   62,   -1,   -1,
   -1,  123,   -1,  125,   40,   41,    0,   -1,   44,   -1,
   -1,   -1,   -1,   33,   -1,   -1,   -1,   -1,   -1,   -1,
   40,   -1,   58,   59,   -1,   45,   -1,   -1,   -1,   -1,
   -1,  123,   -1,  125,   -1,   -1,  146,  147,  148,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   40,   41,   -1,   -1,
   44,   -1,   -1,   -1,   -1,  165,   -1,   -1,  123,   -1,
  125,   33,   -1,   -1,   58,   59,   -1,   -1,   40,   -1,
   -1,   -1,   -1,   45,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,  263,   -1,  265,  266,  123,   -1,  125,
   -1,  271,  272,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,  263,   -1,  265,  266,  267,  268,   -1,
   -1,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  280,  281,  282,  283,   -1,   -1,   -1,   -1,   -1,  123,
   -1,  125,   -1,   -1,   -1,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,   -1,  268,   -1,   -1,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  280,  281,
  282,  283,   -1,   -1,   -1,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,  268,   -1,   -1,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  280,  281,
  282,  283,  257,  258,  259,  260,  261,  262,  263,   -1,
  265,  266,  267,  268,   -1,   -1,  271,  272,  273,  274,
  275,  276,  277,  278,  279,  280,  281,  282,  283,   -1,
   -1,  257,  258,  259,  260,  261,  262,  263,   -1,  265,
  266,  267,  268,   -1,   -1,  271,  272,  273,  274,  275,
  276,  277,   -1,   -1,  280,  281,  282,  283,  268,   -1,
    0,   -1,   -1,  273,  274,  275,  276,  277,   -1,   -1,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,   -1,   -1,  271,  272,  273,
  274,  275,  276,  277,    0,   -1,  280,  281,  282,  283,
   40,   41,   -1,   -1,   44,    0,  268,   -1,   -1,   -1,
    0,  273,  274,  275,  276,  277,   -1,   -1,   58,   59,
   -1,    0,   17,   -1,   -1,   -1,   -1,   17,   -1,   -1,
   -1,   26,   -1,   -1,   40,   41,   26,   -1,   44,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,    0,   -1,
   -1,   -1,   58,   59,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   40,   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   58,
   59,    0,   -1,  123,   -1,  125,   -1,   -1,   40,   41,
   -1,   -1,   44,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,
   -1,   40,   41,    0,   -1,   44,   33,   -1,   -1,   -1,
   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,   45,   58,
   59,   -1,   -1,   -1,  123,   -1,  125,   -1,   -1,    0,
   -1,  146,  147,  148,   -1,   -1,  146,  147,  148,   -1,
   -1,   -1,   -1,   40,   41,   -1,   -1,   44,   -1,   -1,
  165,  123,   -1,  125,   -1,  165,   -1,   -1,   -1,   -1,
   -1,   58,   59,   -1,   -1,   -1,   -1,   -1,   -1,   40,
   -1,   -1,   -1,   -1,   -1,   46,   -1,   -1,   -1,   -1,
   -1,   -1,   33,   -1,  123,    0,  125,   -1,   59,   40,
   -1,   -1,   -1,   -1,   45,   -1,   -1,  257,  258,  259,
  260,  261,  262,  263,   -1,  265,  266,  267,  268,   -1,
   -1,  271,  272,  273,  274,  275,  276,  277,   -1,   -1,
  280,  281,  282,  283,   -1,   40,  123,   -1,  125,   44,
   -1,  257,  258,  259,  260,  261,  262,  263,   -1,  265,
  266,  267,  268,   -1,   59,  271,  272,  273,  274,  275,
  276,  277,  123,   -1,  125,   -1,  282,  283,  257,  258,
  259,  260,  261,  262,  263,   -1,  265,  266,  267,  268,
   -1,   -1,  271,  272,  273,  274,  275,  276,  277,   -1,
   -1,   -1,   -1,  282,  283,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,  268,   -1,   -1,  271,
  272,  273,  274,  275,  276,  277,   -1,   -1,  123,   -1,
  125,  283,   -1,    0,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,  263,   -1,  265,  266,  267,  268,
   -1,   -1,  271,  272,  273,  274,  275,  276,  277,   -1,
   -1,  268,   -1,   -1,  283,   -1,  273,  274,  275,  276,
  277,   -1,   -1,   40,   -1,   -1,    0,   44,   -1,   -1,
  257,  258,  259,  260,  261,  262,  263,   -1,  265,  266,
  267,  268,   59,   -1,  271,  272,  273,  274,  275,  276,
  277,    0,   -1,   -1,   -1,   -1,  257,  258,  259,  260,
  261,  262,  263,   -1,  265,  266,   40,  268,   -1,   -1,
  271,  272,  273,  274,  275,  276,  277,   -1,   -1,    0,
   -1,   -1,   -1,   -1,   -1,   59,   -1,  268,   -1,   -1,
   -1,   40,  273,  274,  275,  276,  277,   -1,   -1,   -1,
    0,   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,   -1,
   -1,   -1,  257,  258,  259,  260,  261,  262,  263,   40,
  265,  266,   -1,  268,   -1,   -1,  271,  272,  273,  274,
  275,  276,  277,   -1,   -1,   -1,   -1,   -1,   59,    0,
   40,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,
   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  123,   -1,  125,   -1,   -1,   40,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  123,   -1,  125,   -1,   -1,   40,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  123,   -1,  125,   59,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,  258,  259,  260,  261,  262,  263,   -1,  265,  266,
   -1,  268,   -1,   -1,  271,  272,  273,  274,  275,  276,
  277,   -1,  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,  263,
  123,  265,  266,   -1,  268,   -1,   -1,  271,  272,  273,
  274,  275,  276,  277,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,  263,   -1,  265,  266,   -1,  268,
   -1,   -1,  271,  272,  273,  274,  275,  276,  277,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  259,   -1,
  261,  262,  263,   -1,  265,  266,   -1,  268,   -1,   -1,
  271,  272,  273,  274,  275,  276,  277,  257,  258,  259,
   -1,  261,  262,  263,   -1,  265,  266,   -1,  268,   -1,
   -1,  271,  272,  273,  274,  275,  276,  277,   -1,   -1,
   -1,    4,    5,    6,    7,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   18,  257,  258,  259,  260,
  261,  262,  263,   -1,  265,  266,   -1,   -1,   -1,   -1,
  271,  272,  273,  274,  275,  276,  277,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  257,  258,  259,   50,  261,  262,
  263,   -1,  265,  266,   -1,  268,   -1,   -1,  271,  272,
  273,  274,  275,  276,  277,   -1,   -1,   -1,   -1,   -1,
   73,   -1,   -1,   -1,   77,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   92,
   -1,   94,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  102,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  122,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  149,   -1,   -1,   -1,
  153,   -1,   -1,   -1,  157,
};
}
final static short YYFINAL=20;
final static short YYMAXTOKEN=287;
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
"CLASS","STRING","BOOL","INTLIT","DOUBLELIT","STRINGLIT","BOOLLIT","NULLVAL",
"LESSTHANOREQUAL","GREATERTHANOREQUAL","ISEQUALTO","NOTEQUALTO","LOGICALAND",
"LOGICALOR","INCREMENT","DECREMENT","PUBLIC","STATIC",
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
"LocalVarDeclStmt : LocalVarDecl",
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
//#line 93 "j0gram.y"
{ yyval=j0.node("ForStmt",1220,val_peek(2),val_peek(0)); }
break;
case 67:
//#line 94 "j0gram.y"
{ yyval=j0.node("ForStmt",1221,val_peek(1),val_peek(0)); }
break;
case 72:
//#line 99 "j0gram.y"
{ yyval=j0.node("ForNormal",1222,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 73:
//#line 100 "j0gram.y"
{ yyval=j0.node("ForFull",1223,val_peek(4),val_peek(3),val_peek(2),val_peek(0)); }
break;
case 77:
//#line 104 "j0gram.y"
{ yyval=j0.node("ForVarInit",1224,val_peek(2),val_peek(0)); }
break;
case 83:
//#line 109 "j0gram.y"
{
  yyval=j0.node("StmtExprList",1230,val_peek(2),val_peek(0)); }
break;
case 85:
//#line 112 "j0gram.y"
{
  yyval=j0.node("BreakStmt",1240,val_peek(1)); }
break;
case 86:
//#line 114 "j0gram.y"
{
  yyval=j0.node("ReturnStmt",1250,val_peek(1)); }
break;
case 90:
//#line 117 "j0gram.y"
{
  yyval=val_peek(1);}
break;
case 97:
//#line 121 "j0gram.y"
{
  yyval=j0.node("ArgList",1270,val_peek(2),val_peek(0)); }
break;
case 98:
//#line 123 "j0gram.y"
{
  yyval=j0.node("FieldAccess",1280,val_peek(2),val_peek(0)); }
break;
case 101:
//#line 127 "j0gram.y"
{
  yyval=j0.node("MethodCall",1290,val_peek(3),val_peek(1)); }
break;
case 102:
//#line 129 "j0gram.y"
{
    yyval=j0.node("MethodCall",1291,val_peek(5),val_peek(3),val_peek(1)); }
break;
case 105:
//#line 134 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1300,val_peek(1),val_peek(0)); }
break;
case 106:
//#line 136 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1301,val_peek(1),val_peek(0)); }
break;
case 109:
//#line 140 "j0gram.y"
{
      yyval=j0.node("MulExpr",1310,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 110:
//#line 142 "j0gram.y"
{
      yyval=j0.node("MulExpr",1311,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 111:
//#line 144 "j0gram.y"
{
      yyval=j0.node("MulExpr",1312,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 113:
//#line 147 "j0gram.y"
{
      yyval=j0.node("AddExpr",1320,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 114:
//#line 149 "j0gram.y"
{
      yyval=j0.node("AddExpr",1321,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 120:
//#line 152 "j0gram.y"
{
  yyval=j0.node("RelExpr",1330,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 122:
//#line 156 "j0gram.y"
{
  yyval=j0.node("EqExpr",1340,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 123:
//#line 158 "j0gram.y"
{
  yyval=j0.node("EqExpr",1341,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 125:
//#line 160 "j0gram.y"
{
  yyval=j0.node("CondAndExpr", 1350, val_peek(2),val_peek(1), val_peek(0)); }
break;
case 127:
//#line 162 "j0gram.y"
{
  yyval=j0.node("CondOrExpr", 1360, val_peek(2),val_peek(1), val_peek(0)); }
break;
case 129:
//#line 166 "j0gram.y"
{
yyval=j0.node("Assignment",1370, val_peek(2), val_peek(1), val_peek(0)); }
break;
//#line 1147 "Parser.java"
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
