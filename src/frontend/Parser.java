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
    9,    9,    9,    9,    9,   12,   12,   13,   10,   10,
   14,   14,   15,   15,    7,   16,   18,   19,   19,   20,
   20,   21,    8,   17,    1,    1,   22,   22,   23,   11,
   11,   25,   26,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   27,   35,   35,   30,   31,   32,
   32,   39,   39,   40,   33,   34,   41,   41,   41,   42,
   42,   43,   43,   44,   44,   28,   28,   29,   45,   45,
   45,   45,   46,   46,   46,   46,   46,   48,   48,   47,
   49,   49,   37,   37,   50,   50,   51,   51,   51,   52,
   52,   52,   52,   53,   53,   53,   54,   54,   54,   54,
   55,   55,   56,   56,   56,   57,   57,   58,   58,   38,
   38,   36,   59,   59,   60,   60,   60,
};
final static short yylen[] = {                            2,
    1,    4,    3,    2,    1,    2,    1,    1,    1,    3,
    1,    1,    1,    1,    1,    1,    1,    3,    1,    3,
    1,    3,    1,    1,    2,    4,    4,    1,    0,    1,
    3,    2,    2,    3,    1,    0,    1,    2,    1,    1,
    0,    2,    2,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    2,    1,    1,    5,    7,    6,
    8,    1,    2,    2,    5,    9,    1,    1,    0,    1,
    0,    1,    0,    1,    3,    2,    3,    3,    1,    1,
    1,    3,    1,    1,    1,    1,    1,    1,    3,    3,
    1,    0,    4,    6,    1,    1,    2,    2,    1,    1,
    3,    3,    3,    1,    3,    3,    1,    1,    1,    1,
    1,    3,    1,    3,    3,    1,    3,    1,    3,    1,
    1,    3,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,   12,    0,    0,   11,    0,    0,   16,   14,   13,
   83,   84,   86,   85,   87,    0,    0,   45,    0,    1,
    0,    0,   17,   44,    0,   37,   39,   54,    0,   46,
   47,   48,   49,   50,   51,   52,   53,    0,   56,    0,
    0,   79,    0,    0,    0,   40,   76,    0,    0,    0,
    0,    0,  121,   81,   70,    0,    0,   99,  100,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   21,    0,
    0,    0,    0,   38,   42,   55,    0,  126,  127,  125,
    0,   77,   68,   74,    0,    0,    0,    0,   80,   97,
   98,   78,    0,    0,    0,    0,    0,  107,  108,  109,
  110,    0,    0,    0,    0,    0,    0,   34,   82,    0,
    0,   18,   88,    0,    0,    0,  122,    0,    0,    0,
  101,  102,  103,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   22,    0,   93,    0,    0,    0,   75,    0,
   65,   89,    0,    0,    0,    0,   62,   94,    0,    0,
    0,   59,   64,    0,   63,    0,    0,   61,   66,    0,
    0,   58,
};
final static short yydgoto[] = {                         19,
   20,    0,    0,    0,    0,    0,    0,    0,   21,   70,
   47,   88,   23,   71,    0,    0,   24,    0,    0,    0,
    0,   25,   26,   27,   28,   29,   30,   31,   32,   33,
   34,   35,   36,   37,   38,   53,   54,   55,  146,  147,
   85,   56,  149,   86,   57,   42,   43,  114,  115,   58,
   59,   60,   61,  102,   62,   63,   64,   65,   44,   81,
};
final static short yysindex[] = {                      1285,
  -56,    0,  -20,  -18,    0,  -33,  -11,    0,    0,    0,
    0,    0,    0,    0,    0, 1285,  -33,    0,    0,    0,
 -252,  -15,    0,    0, 1285,    0,    0,    0,  -10,    0,
    0,    0,    0,    0,    0,    0,    0,  -10,    0,    0,
    4,    0,    0,  -57,  -10,    0,    0,  299,  -33,  -33,
  -33,  -15,    0,    0,    0,  -10,    4,    0,    0,  -26,
  -13,  -47, -245, -229, -228,  -33,  -73,   13,    0,   12,
  -34, -208,  -33,    0,    0,    0, -207,    0,    0,    0,
  -33,    0,    0,    0,    5,   32,   36,  -15,    0,    0,
    0,    0,  -33,  -33,  -33,  -33,  -33,    0,    0,    0,
    0,  -33,  -33,  -33,  -33,  -33,   37,    0,    0, -252,
  -14,    0,    0,   38,   40,   43,    0,  -33,  -23,  -38,
    0,    0,    0,  -26,  -26,  -13,  -47,  -47, -245, -229,
 1285,  -34,    0,  -33,    0,  -33,   25,  -15,    0, -173,
    0,    0,   50,  -23, -118, -166,    0,    0,   53,   32,
   61,    0,    0, -118,    0,  -38,  -33,    0,    0,   62,
  -38,    0,
};
final static short yyrindex[] = {                       104,
 1306,    0,    0,    0,    0, 1328,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -17,    0,    0,    0,    0,
    0,  -59,    0,    0,    8,    0,    0,    0, 1263,    0,
    0,    0,    0,    0,    0,    0,    0, 1263,    0, 1110,
    0,    0,    1,    0, 1263,    0,    0,   47,    0,    0,
    0,   55,    0,    0,    0, 1263,   95,    0,    0,  377,
  454,  504,  796,  846, 1132,    0,    0,    0,    0, 1197,
 1152,    0,   66,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   51,    0,  122,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   68,    0,   28,    0,   52,    0,    0,
    0,    0,    0,  404,  429,  479,  746,  771,  821,  868,
    0, 1175,    0,    0,    0,   66,    0,  -55,    0, 1220,
    0,    0,    0,   71,    0, 1240,    0,    0,    0,   72,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
  102,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -19,  818,    0,    9,    0,    0,   48,    0,    0,    0,
    0,    0,   96,   -8,    0,   79,    0,    0,    0, -127,
    0,    0,    0,    0,  -39,  125,  390,  426,    0,  -16,
    0,    2,    0,    3,  409,    0,   94,    0,   -7,    0,
  -27,  -61,   26,    0,  -64,   29,   42,    0,    0,    0,
};
final static int YYTABLESIZE=1598;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         51,
   80,  123,   46,   80,   16,  123,   17,   35,   84,   75,
   95,   50,  100,   69,  101,   93,   17,  153,   76,   48,
   94,   49,   90,   91,   73,   82,  153,   90,   66,   97,
   72,   96,  103,  104,  124,  125,   92,   80,  127,  128,
   80,   80,   80,   80,   80,   80,   80,   80,   46,   77,
  105,  108,  106,  109,   96,  110,  111,  112,  116,   80,
   80,  124,   80,  118,   90,  121,  122,  123,   90,   90,
   90,   90,   90,   90,   90,  119,  120,  131,  133,  139,
  135,  134,  136,  144,   16,  145,   90,   90,   90,   90,
  148,   96,  154,  156,   95,   96,   96,   96,   96,   96,
  157,   96,  161,   36,   84,   69,   92,   36,   91,   67,
   71,   73,   72,   96,   96,  123,   96,   67,  132,  137,
   74,   96,  141,   80,   39,   80,   83,  126,  143,  155,
    0,   95,   35,  129,   95,   95,   95,   95,   95,   95,
   39,   95,  151,   89,   89,    0,  150,  130,    0,   39,
   90,    0,   90,   95,   95,    0,   95,    0,   96,    0,
    0,    0,   96,   96,   96,   96,   96,  140,   96,    0,
    0,    0,   39,    0,    0,    0,    0,   96,    0,   96,
   96,   96,    0,   96,    0,    0,   89,   89,   89,   89,
   89,    0,  152,    0,    0,   89,   89,   89,   89,   89,
    0,  158,    0,  159,    0,    0,   15,    0,  162,   45,
    0,    0,    0,    0,    0,    0,    0,   95,    0,   95,
    0,    0,  123,  123,   78,   79,  123,  123,   98,   99,
    0,    0,    8,    0,    0,    0,    0,   11,   12,   13,
   14,   15,    8,   39,   96,    0,   96,   11,   12,   13,
   14,   15,    0,    0,    0,   39,    0,   80,   80,    0,
   80,   80,   80,   80,    0,   80,   80,    0,   39,   80,
   80,   80,   80,   80,   80,   80,   80,   80,   80,   80,
   80,   80,  124,  124,   90,   90,    0,   90,   90,   90,
   90,    0,   90,   90,    0,    0,   90,   90,   90,   90,
   90,   90,   90,   90,   90,   90,   90,   90,   90,   90,
   90,   96,   96,    0,   96,   96,   96,   96,    0,   96,
   96,    0,    0,   96,   96,   96,   96,   96,   96,   96,
   96,   96,   96,   96,   96,   96,  123,  123,   17,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   95,   95,    0,   95,   95,   95,   95,    0,   95,
   95,    0,    0,   95,   95,   95,   95,   95,   95,   95,
   95,   95,   95,   95,   95,   95,  104,    0,   96,   96,
    0,   96,   96,   96,   96,    0,   96,   96,    0,   40,
   96,   96,   96,   96,   96,   96,   96,   96,   96,   96,
   96,   96,   96,  106,    0,   40,    0,    0,   41,    0,
    0,    0,    0,    0,   40,    0,  104,  104,    0,  104,
  104,  104,    0,    0,   41,    0,    0,    0,  105,    0,
    0,    0,    0,   41,    0,  104,  104,   40,  104,    0,
    0,    0,   68,  106,  106,    0,  106,  106,  106,    0,
    0,    0,    0,  111,    0,    0,   41,    0,    0,    0,
    0,    0,  106,  106,    0,  106,    0,    0,  105,  105,
    0,  105,  105,  105,   87,    0,    0,    0,  112,    0,
    0,    0,    0,    0,    0,    0,    0,  105,  105,    0,
  105,  107,    0,  111,  111,    0,    0,  111,  113,  104,
    0,  104,    0,  113,    0,    0,  117,    0,   40,    0,
    0,    0,  111,  111,    0,  111,    0,    0,  112,  112,
   40,    0,  112,    0,    0,    0,  106,   41,  106,    0,
    0,    0,    0,   40,    0,    0,    0,  112,  112,   41,
  112,    0,    0,  113,  113,    0,    0,  113,    0,    0,
    0,  105,   41,  105,    0,    0,    2,    0,    0,  142,
    5,  113,  113,    0,    8,    0,    0,    9,   10,   11,
   12,   13,   14,   15,    0,    0,  111,    0,  111,    0,
    0,    0,  160,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  112,    0,  112,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  113,    0,  113,    0,
    0,    0,    0,  104,  104,    0,  104,  104,  104,  104,
    0,  104,  104,    0,    0,  104,  104,  104,  104,  104,
  104,  104,  104,  104,  104,  104,  104,  104,    0,    0,
  106,  106,    0,  106,  106,  106,  106,    0,  106,  106,
    0,    0,  106,  106,  106,  106,  106,  106,  106,  106,
  106,  106,  106,  106,  106,  105,  105,    0,  105,  105,
  105,  105,    0,  105,  105,    0,    0,  105,  105,  105,
  105,  105,  105,  105,  105,  105,  105,  105,  105,  105,
  111,  111,    0,  111,  111,  111,  111,    0,  111,  111,
    0,    0,  111,  111,  111,  111,  111,  111,  111,  111,
  111,  111,  111,  111,  111,  112,  112,    0,  112,  112,
  112,  112,    0,  112,  112,  114,    0,  112,  112,  112,
  112,  112,  112,  112,  112,  112,  112,  112,  112,  112,
  113,  113,    0,  113,  113,  113,  113,    0,  113,  113,
  115,    0,  113,  113,  113,  113,  113,  113,  113,    0,
    0,  113,  113,  113,  113,  114,  114,    0,    0,  114,
    0,    0,    0,    0,    0,  116,    0,    0,    0,    0,
    0,    0,    0,    0,  114,    0,    0,    0,    0,    0,
  115,  115,    0,    0,  115,    0,    0,   22,    0,    0,
  117,    0,    0,   52,    0,    0,    0,    0,    0,  115,
    0,    0,    0,   22,   52,  116,  116,    0,    0,  116,
    0,    0,   22,    0,    0,  118,    0,    0,    0,    0,
    0,    0,    0,    0,  116,    0,    0,    0,    0,    0,
  117,  117,    0,    0,  117,   22,   52,  119,  114,    0,
  114,    0,    0,    0,    0,    0,    0,    0,    0,  117,
    0,    0,    0,   52,    0,  118,  118,    0,    0,  118,
   52,    0,    0,  115,    0,  115,    0,    0,   52,    0,
    0,    0,    0,    0,  118,    0,    0,  119,  119,    0,
    0,  119,    0,    0,    0,    0,    0,    0,  116,    0,
  116,    0,    0,    0,    0,    0,  119,    0,    0,    0,
    0,    0,    0,    0,    0,   52,  138,    0,    0,    0,
    0,    0,    0,  117,    0,  117,    0,    0,   22,    0,
    0,   52,    0,   52,    0,    0,    0,    0,    0,    0,
    0,  138,    0,    0,    0,    0,    0,    0,  118,    0,
  118,    0,    0,    0,   52,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  119,    0,  119,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  114,  114,    0,  114,  114,  114,  114,    0,
  114,  114,    0,    0,  114,  114,  114,  114,  114,  114,
  114,    0,    0,  114,  114,  114,  114,  115,  115,    0,
  115,  115,  115,  115,    0,  115,  115,    0,    0,  115,
  115,  115,  115,  115,  115,  115,    0,    0,  115,  115,
  115,  115,  116,  116,    0,  116,  116,  116,  116,    0,
  116,  116,    0,    0,  116,  116,  116,  116,  116,  116,
  116,    0,    0,    0,    0,  116,  116,  117,  117,    0,
  117,  117,  117,  117,    0,  117,  117,    0,    0,  117,
  117,  117,  117,  117,  117,  117,    0,    0,    0,    0,
  117,  117,  118,  118,    0,  118,  118,  118,  118,   57,
  118,  118,    0,    0,  118,  118,  118,  118,  118,  118,
  118,    0,    0,    0,  119,  119,  118,  119,  119,  119,
  119,  120,  119,  119,    0,    0,  119,  119,  119,  119,
  119,  119,  119,    0,    0,    0,    0,    0,  119,   57,
   57,   19,    0,   57,    0,   81,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   57,    0,
    0,  120,  120,    0,   20,  120,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  120,   19,    0,    0,    0,   19,   43,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   19,    0,    0,    0,   20,    0,    0,    0,   20,   58,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   57,   20,   57,    0,   43,    0,    0,   60,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  120,   43,  120,    0,    0,   58,
    0,    0,   41,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   19,    0,   19,    0,   58,   60,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   20,   60,   20,
    0,    0,   41,    0,    0,   41,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   43,
    0,   43,    0,    0,   17,    0,    0,   71,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   58,   18,   58,   41,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   60,    0,   60,    0,   57,   57,    0,   57,
   57,   57,   57,    0,   57,   57,    0,    0,   57,   57,
   57,   57,   57,   57,   57,   41,   71,   41,  120,  120,
    0,  120,  120,  120,  120,    0,  120,  120,    0,    0,
  120,  120,  120,  120,  120,  120,  120,   16,   19,   19,
    0,   19,   19,   19,   19,    0,   19,   19,    0,    0,
   19,   19,   19,   19,   19,   19,   19,    0,   41,    0,
   41,   20,   20,    0,   20,   20,   20,   20,    0,   20,
   20,    0,    0,   20,   20,   20,   20,   20,   20,   20,
   71,    0,   71,   43,   43,    0,   43,   43,   43,   43,
    0,   43,   43,    0,    0,   43,   43,   43,   43,   43,
   43,   43,    0,    0,    0,    0,   58,   58,    0,   58,
   58,   58,   58,    0,   58,   58,    0,    0,   58,   58,
   58,   58,   58,   58,   58,    0,   60,   60,    0,   60,
   60,   60,   60,    0,   60,   60,    0,    0,   60,   60,
   60,   60,   60,   60,   60,    0,    0,    0,    0,   41,
   41,    0,   41,   41,   41,   41,    0,   41,   41,    0,
    0,   41,   41,   41,   41,   41,   41,   41,    0,    0,
    0,    1,    2,    0,    3,    4,    5,    6,    0,    7,
    8,    0,    0,    9,   10,   11,   12,   13,   14,   15,
    0,    0,   41,   41,    0,   41,   41,   41,   41,    0,
   41,    0,    0,    0,   41,   41,   41,   41,   41,   41,
   41,    0,    0,    0,   71,   71,    0,   71,   71,   71,
   71,    0,   71,    0,    0,    0,   71,   71,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
    0,   61,   59,   61,  123,   61,   40,    0,   48,   29,
   37,   45,   60,  266,   62,   42,   40,  145,   38,   40,
   47,   40,   50,   51,   40,   45,  154,    0,   40,   43,
   46,   45,  278,  279,   96,   97,   56,   37,  103,  104,
   40,   41,   42,   43,   44,   45,   46,   47,   59,   46,
  280,  125,  281,   41,    0,   44,   91,  266,  266,   59,
   60,   61,   62,   59,   37,   93,   94,   95,   41,   42,
   43,   44,   45,   46,   47,   44,   41,   41,   93,  119,
   41,   44,   40,   59,  123,  259,   59,   60,   61,   62,
   41,   37,  259,   41,    0,   41,   42,   43,   44,   45,
   40,   47,   41,    0,  144,   59,   41,  125,   41,   59,
   59,   41,   41,   59,   60,   61,   62,   16,  110,  118,
   25,    0,  131,  123,    0,  125,   48,  102,  136,  146,
   -1,   37,  125,  105,   40,   41,   42,   43,   44,   45,
   16,   47,  261,   50,   51,   -1,  144,  106,   -1,   25,
  123,   -1,  125,   59,   60,   -1,   62,   -1,   37,   -1,
   -1,   -1,   41,   42,   43,   44,   45,  120,   47,   -1,
   -1,   -1,   48,   -1,   -1,   -1,   -1,  123,   -1,  125,
   59,   60,   -1,   62,   -1,   -1,   93,   94,   95,   96,
   97,   -1,  145,   -1,   -1,  102,  103,  104,  105,  106,
   -1,  154,   -1,  156,   -1,   -1,  266,   -1,  161,  266,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,
   -1,   -1,  282,  283,  282,  283,  282,  283,  276,  277,
   -1,   -1,  266,   -1,   -1,   -1,   -1,  271,  272,  273,
  274,  275,  266,  119,  123,   -1,  125,  271,  272,  273,
  274,  275,   -1,   -1,   -1,  131,   -1,  257,  258,   -1,
  260,  261,  262,  263,   -1,  265,  266,   -1,  144,  269,
  270,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  280,  281,  282,  283,  257,  258,   -1,  260,  261,  262,
  263,   -1,  265,  266,   -1,   -1,  269,  270,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  280,  281,  282,
  283,  257,  258,   -1,  260,  261,  262,  263,   -1,  265,
  266,   -1,   -1,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  280,  281,  282,  283,   40,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,   -1,  260,  261,  262,  263,   -1,  265,
  266,   -1,   -1,  269,  270,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  280,  281,    0,   -1,  257,  258,
   -1,  260,  261,  262,  263,   -1,  265,  266,   -1,    0,
  269,  270,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  280,  281,    0,   -1,   16,   -1,   -1,    0,   -1,
   -1,   -1,   -1,   -1,   25,   -1,   40,   41,   -1,   43,
   44,   45,   -1,   -1,   16,   -1,   -1,   -1,    0,   -1,
   -1,   -1,   -1,   25,   -1,   59,   60,   48,   62,   -1,
   -1,   -1,   17,   40,   41,   -1,   43,   44,   45,   -1,
   -1,   -1,   -1,    0,   -1,   -1,   48,   -1,   -1,   -1,
   -1,   -1,   59,   60,   -1,   62,   -1,   -1,   40,   41,
   -1,   43,   44,   45,   49,   -1,   -1,   -1,    0,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   60,   -1,
   62,   66,   -1,   40,   41,   -1,   -1,   44,   73,  123,
   -1,  125,   -1,    0,   -1,   -1,   81,   -1,  119,   -1,
   -1,   -1,   59,   60,   -1,   62,   -1,   -1,   40,   41,
  131,   -1,   44,   -1,   -1,   -1,  123,  119,  125,   -1,
   -1,   -1,   -1,  144,   -1,   -1,   -1,   59,   60,  131,
   62,   -1,   -1,   40,   41,   -1,   -1,   44,   -1,   -1,
   -1,  123,  144,  125,   -1,   -1,  258,   -1,   -1,  134,
  262,  136,   59,   -1,  266,   -1,   -1,  269,  270,  271,
  272,  273,  274,  275,   -1,   -1,  123,   -1,  125,   -1,
   -1,   -1,  157,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,  125,   -1,
   -1,   -1,   -1,  257,  258,   -1,  260,  261,  262,  263,
   -1,  265,  266,   -1,   -1,  269,  270,  271,  272,  273,
  274,  275,  276,  277,  278,  279,  280,  281,   -1,   -1,
  257,  258,   -1,  260,  261,  262,  263,   -1,  265,  266,
   -1,   -1,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  280,  281,  257,  258,   -1,  260,  261,
  262,  263,   -1,  265,  266,   -1,   -1,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  280,  281,
  257,  258,   -1,  260,  261,  262,  263,   -1,  265,  266,
   -1,   -1,  269,  270,  271,  272,  273,  274,  275,  276,
  277,  278,  279,  280,  281,  257,  258,   -1,  260,  261,
  262,  263,   -1,  265,  266,    0,   -1,  269,  270,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  280,  281,
  257,  258,   -1,  260,  261,  262,  263,   -1,  265,  266,
    0,   -1,  269,  270,  271,  272,  273,  274,  275,   -1,
   -1,  278,  279,  280,  281,   40,   41,   -1,   -1,   44,
   -1,   -1,   -1,   -1,   -1,    0,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   59,   -1,   -1,   -1,   -1,   -1,
   40,   41,   -1,   -1,   44,   -1,   -1,    0,   -1,   -1,
    0,   -1,   -1,    6,   -1,   -1,   -1,   -1,   -1,   59,
   -1,   -1,   -1,   16,   17,   40,   41,   -1,   -1,   44,
   -1,   -1,   25,   -1,   -1,    0,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   59,   -1,   -1,   -1,   -1,   -1,
   40,   41,   -1,   -1,   44,   48,   49,    0,  123,   -1,
  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,
   -1,   -1,   -1,   66,   -1,   40,   41,   -1,   -1,   44,
   73,   -1,   -1,  123,   -1,  125,   -1,   -1,   81,   -1,
   -1,   -1,   -1,   -1,   59,   -1,   -1,   40,   41,   -1,
   -1,   44,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,
  125,   -1,   -1,   -1,   -1,   -1,   59,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  118,  119,   -1,   -1,   -1,
   -1,   -1,   -1,  123,   -1,  125,   -1,   -1,  131,   -1,
   -1,  134,   -1,  136,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  144,   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,
  125,   -1,   -1,   -1,  157,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,  258,   -1,  260,  261,  262,  263,   -1,
  265,  266,   -1,   -1,  269,  270,  271,  272,  273,  274,
  275,   -1,   -1,  278,  279,  280,  281,  257,  258,   -1,
  260,  261,  262,  263,   -1,  265,  266,   -1,   -1,  269,
  270,  271,  272,  273,  274,  275,   -1,   -1,  278,  279,
  280,  281,  257,  258,   -1,  260,  261,  262,  263,   -1,
  265,  266,   -1,   -1,  269,  270,  271,  272,  273,  274,
  275,   -1,   -1,   -1,   -1,  280,  281,  257,  258,   -1,
  260,  261,  262,  263,   -1,  265,  266,   -1,   -1,  269,
  270,  271,  272,  273,  274,  275,   -1,   -1,   -1,   -1,
  280,  281,  257,  258,   -1,  260,  261,  262,  263,    0,
  265,  266,   -1,   -1,  269,  270,  271,  272,  273,  274,
  275,   -1,   -1,   -1,  257,  258,  281,  260,  261,  262,
  263,    0,  265,  266,   -1,   -1,  269,  270,  271,  272,
  273,  274,  275,   -1,   -1,   -1,   -1,   -1,  281,   40,
   41,    0,   -1,   44,   -1,   46,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   -1,
   -1,   40,   41,   -1,    0,   44,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   59,   40,   -1,   -1,   -1,   44,    0,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   59,   -1,   -1,   -1,   40,   -1,   -1,   -1,   44,    0,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  123,   59,  125,   -1,   40,   -1,   -1,    0,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  123,   59,  125,   -1,   -1,   40,
   -1,   -1,    0,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  123,   -1,  125,   -1,   59,   40,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,   59,  125,
   -1,   -1,   40,   -1,   -1,    0,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  123,
   -1,  125,   -1,   -1,   40,   -1,   -1,    0,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  123,   59,  125,   40,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  123,   -1,  125,   -1,  257,  258,   -1,  260,
  261,  262,  263,   -1,  265,  266,   -1,   -1,  269,  270,
  271,  272,  273,  274,  275,  123,   59,  125,  257,  258,
   -1,  260,  261,  262,  263,   -1,  265,  266,   -1,   -1,
  269,  270,  271,  272,  273,  274,  275,  123,  257,  258,
   -1,  260,  261,  262,  263,   -1,  265,  266,   -1,   -1,
  269,  270,  271,  272,  273,  274,  275,   -1,  123,   -1,
  125,  257,  258,   -1,  260,  261,  262,  263,   -1,  265,
  266,   -1,   -1,  269,  270,  271,  272,  273,  274,  275,
  123,   -1,  125,  257,  258,   -1,  260,  261,  262,  263,
   -1,  265,  266,   -1,   -1,  269,  270,  271,  272,  273,
  274,  275,   -1,   -1,   -1,   -1,  257,  258,   -1,  260,
  261,  262,  263,   -1,  265,  266,   -1,   -1,  269,  270,
  271,  272,  273,  274,  275,   -1,  257,  258,   -1,  260,
  261,  262,  263,   -1,  265,  266,   -1,   -1,  269,  270,
  271,  272,  273,  274,  275,   -1,   -1,   -1,   -1,  257,
  258,   -1,  260,  261,  262,  263,   -1,  265,  266,   -1,
   -1,  269,  270,  271,  272,  273,  274,  275,   -1,   -1,
   -1,  257,  258,   -1,  260,  261,  262,  263,   -1,  265,
  266,   -1,   -1,  269,  270,  271,  272,  273,  274,  275,
   -1,   -1,  257,  258,   -1,  260,  261,  262,  263,   -1,
  265,   -1,   -1,   -1,  269,  270,  271,  272,  273,  274,
  275,   -1,   -1,   -1,  257,  258,   -1,  260,  261,  262,
  263,   -1,  265,   -1,   -1,   -1,  269,  270,
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
"FieldDecl : Type VarDecls StmtEnd",
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
"Stmt : IfThenStmt",
"Stmt : IfThenElseStmt",
"Stmt : IfThenElseIfStmt",
"Stmt : WhileStmt",
"Stmt : ForStmt",
"Stmt : LocalVarDeclStmt",
"ExprStmt : StmtExpr StmtEnd",
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
case 43:
//#line 64 "j0gram.y"
{
  yyval=j0.node("LocalVarDecl",1140,val_peek(1),val_peek(0)); }
break;
case 58:
//#line 75 "j0gram.y"
{
  yyval=j0.node("IfThenStmt",1150,val_peek(2),val_peek(0)); }
break;
case 59:
//#line 77 "j0gram.y"
{
  yyval=j0.node("IfThenElseStmt",1160,val_peek(4),val_peek(2),val_peek(0)); }
break;
case 60:
//#line 79 "j0gram.y"
{
  yyval=j0.node("IfThenElseIfStmt",1170,val_peek(3),val_peek(1),val_peek(0)); }
break;
case 61:
//#line 81 "j0gram.y"
{
  yyval=j0.node("IfThenElseIfStmt",1171,val_peek(5),val_peek(3),val_peek(2),val_peek(0)); }
break;
case 63:
//#line 84 "j0gram.y"
{
  yyval=j0.node("ElseIfSequence",1180,val_peek(1),val_peek(0)); }
break;
case 64:
//#line 86 "j0gram.y"
{
  yyval=j0.node("ElseIfStmt",1190,val_peek(0)); }
break;
case 65:
//#line 88 "j0gram.y"
{
  yyval=j0.node("WhileStmt",1210,val_peek(2),val_peek(0)); }
break;
case 66:
//#line 91 "j0gram.y"
{
  yyval=j0.node("ForStmt",1220,val_peek(6),val_peek(4),val_peek(2),val_peek(0)); }
break;
case 75:
//#line 97 "j0gram.y"
{
  yyval=j0.node("StmtExprList",1230,val_peek(2),val_peek(0)); }
break;
case 77:
//#line 100 "j0gram.y"
{
  yyval=j0.node("BreakStmt",1240,val_peek(1)); }
break;
case 78:
//#line 102 "j0gram.y"
{
  yyval=j0.node("ReturnStmt",1250,val_peek(1)); }
break;
case 82:
//#line 105 "j0gram.y"
{
  yyval=val_peek(1);}
break;
case 89:
//#line 109 "j0gram.y"
{
  yyval=j0.node("ArgList",1270,val_peek(2),val_peek(0)); }
break;
case 90:
//#line 111 "j0gram.y"
{
  yyval=j0.node("FieldAccess",1280,val_peek(2),val_peek(0)); }
break;
case 93:
//#line 115 "j0gram.y"
{
  yyval=j0.node("MethodCall",1290,val_peek(3),val_peek(1)); }
break;
case 94:
//#line 117 "j0gram.y"
{
    yyval=j0.node("MethodCall",1291,val_peek(5),val_peek(3),val_peek(1)); }
break;
case 97:
//#line 122 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1300,val_peek(1),val_peek(0)); }
break;
case 98:
//#line 124 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1301,val_peek(1),val_peek(0)); }
break;
case 101:
//#line 128 "j0gram.y"
{
      yyval=j0.node("MulExpr",1310,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 102:
//#line 130 "j0gram.y"
{
      yyval=j0.node("MulExpr",1311,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 103:
//#line 132 "j0gram.y"
{
      yyval=j0.node("MulExpr",1312,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 105:
//#line 135 "j0gram.y"
{
      yyval=j0.node("AddExpr",1320,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 106:
//#line 137 "j0gram.y"
{
      yyval=j0.node("AddExpr",1321,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 112:
//#line 140 "j0gram.y"
{
  yyval=j0.node("RelExpr",1330,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 114:
//#line 144 "j0gram.y"
{
  yyval=j0.node("EqExpr",1340,val_peek(2),val_peek(0)); }
break;
case 115:
//#line 146 "j0gram.y"
{
  yyval=j0.node("EqExpr",1341,val_peek(2),val_peek(0)); }
break;
case 117:
//#line 148 "j0gram.y"
{
  yyval=j0.node("CondAndExpr", 1350, val_peek(2), val_peek(0)); }
break;
case 119:
//#line 150 "j0gram.y"
{
  yyval=j0.node("CondOrExpr", 1360, val_peek(2), val_peek(0)); }
break;
case 122:
//#line 154 "j0gram.y"
{
yyval=j0.node("Assignment",1370, val_peek(2), val_peek(1), val_peek(0)); }
break;
//#line 1094 "Parser.java"
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
