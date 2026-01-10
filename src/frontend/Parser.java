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
public final static short INTARRAY=274;
public final static short INTLIT=275;
public final static short DOUBLELIT=276;
public final static short STRINGLIT=277;
public final static short BOOLLIT=278;
public final static short NULLVAL=279;
public final static short LESSTHANOREQUAL=280;
public final static short GREATERTHANOREQUAL=281;
public final static short ISEQUALTO=282;
public final static short NOTEQUALTO=283;
public final static short LOGICALAND=284;
public final static short LOGICALOR=285;
public final static short INCREMENT=286;
public final static short DECREMENT=287;
public final static short MINUSEQUAL=288;
public final static short ADDEQUAL=289;
public final static short MULEQUAL=290;
public final static short DIVEQUAL=291;
public final static short PUBLIC=292;
public final static short STATIC=293;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    2,    3,    3,    4,    4,    5,    5,    5,    6,
    8,    8,    8,    8,    8,    8,   10,   10,   11,    9,
    9,   12,   12,   13,   13,    7,    7,   16,   16,   17,
   17,   19,   19,   20,   20,   15,   15,   21,   14,    1,
    1,   22,   22,   23,   25,   25,   26,   27,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   24,   24,
   28,   36,   36,   31,   31,   32,   32,   34,   34,   33,
   33,   35,   35,   38,   38,   38,   41,   40,   39,   42,
   42,   43,   43,   44,   44,   45,   45,   29,   29,   30,
   30,   46,   46,   46,   46,   47,   47,   47,   47,   47,
   48,   49,   49,   50,   50,   50,   51,   51,   51,   51,
   52,   52,   52,   53,   53,   53,   53,   54,   54,   55,
   55,   55,   56,   56,   57,   57,   18,   37,   37,   58,
   58,   59,   59,   59,   59,   59,   60,   60,
};
final static short yylen[] = {                            2,
    1,    4,    3,    2,    1,    2,    1,    1,    0,    2,
    1,    1,    1,    1,    1,    1,    1,    1,    3,    1,
    3,    1,    3,    1,    0,    6,    5,    1,    0,    1,
    3,    4,    3,    1,    0,    1,    3,    2,    3,    1,
    0,    1,    2,    1,    1,    0,    1,    2,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    3,    5,    7,    5,    5,    3,    6,
    4,    5,    3,    1,    1,    1,    1,    3,    5,    3,
    1,    1,    1,    1,    0,    1,    3,    2,    3,    2,
    1,    1,    1,    1,    3,    1,    1,    1,    1,    1,
    3,    1,    1,    2,    2,    1,    1,    3,    3,    3,
    1,    3,    3,    1,    1,    1,    1,    1,    3,    1,
    3,    3,    1,    3,    1,    3,    1,    3,    2,    1,
    1,    1,    1,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,   12,   11,    0,    0,    0,    0,    0,   17,   14,
   13,    0,   16,   96,   97,   99,   98,  100,    0,    0,
   50,    0,    1,   56,    0,    0,   18,   49,    0,    0,
   42,   44,   60,   47,   51,   52,   53,   54,   55,   57,
   58,   59,   61,   62,    0,   92,    0,    0,    0,   45,
   88,    0,    0,    0,    0,    0,   77,   94,    0,   74,
   75,   76,    0,    0,   93,  106,  107,    0,    0,    0,
    0,    0,    0,    0,    0,   90,    0,    0,    0,    0,
    0,    0,   22,    0,    0,    0,    0,   43,    0,  137,
  138,  135,  136,  133,  134,  132,    0,  129,   89,    0,
    0,    0,  104,  105,   73,   83,   82,    0,    0,    0,
    0,    0,    0,  114,  115,  116,  117,    0,    0,    0,
    0,    0,    0,    0,    0,   69,    0,    0,   39,   95,
    0,    0,   19,   33,    0,   30,  101,  128,   80,    0,
    0,  108,  109,  110,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   71,    0,    0,    0,    0,
   36,    0,   23,    0,   32,   72,    0,    0,   67,   68,
    0,   27,    0,    0,    0,   31,   79,    0,    0,   37,
   26,   66,
};
final static short yydgoto[] = {                         22,
   23,    0,    0,    0,    0,    0,   24,   25,   84,   56,
   27,   85,    0,   28,  160,    0,  135,   57,   58,    0,
  161,   30,   31,   32,   51,   33,   34,   35,   36,   37,
   38,   39,   40,   41,   42,   43,   44,   59,   60,   61,
   62,   63,  108,    0,    0,   64,   46,   65,   66,   67,
   68,   69,  118,   70,   71,   72,   73,   48,   97,   98,
};
final static short yysindex[] = {                      1384,
  -57,    0,    0,  119,  398,  502, 1070, -106,    0,    0,
    0, -246,    0,    0,    0,    0,    0,    0, 1384,  502,
    0,    0,    0,    0, -240,    9,    0,    0,    0, 1384,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -11,    0,    0,  102,   -3,    0,
    0,   -7, 1317,  502,  502,    9,    0,    0, -106,    0,
    0,    0,  -51,  -11,    0,    0,    0,  -18,    7,  -49,
 -217, -226, -215,  502, -106,    0,  502, -106, -197,   36,
  -47,   39,    0,   47,    2, -173,  158,    0, -171,    0,
    0,    0,    0,    0,    0,    0,  502,    0,    0,  502,
   39,   60,    0,    0,    0,    0,    0,  502,  502,  502,
  502,  502,  502,    0,    0,    0,    0,  502,  502,  502,
  502,  502,   62, -156,   64,    0, 1356,  259,    0,    0,
 -240,   14,    0,    0,  -26,    0,    0,    0,    0, -106,
   50,    0,    0,    0,  -18,  -18,    7,  -49,  -49, -217,
 -226, 1384, 1384, 1384,  502,    0, -106, -240,   65,   -5,
    0,    2,    0,  502,    0,    0,  502, -145,    0,    0,
   75,    0,    2, -124, -106,    0,    0, 1384,    0,    0,
    0,    0,
};
final static short yyrindex[] = {                       120,
 1179,    0,    0,    0,    0, 1305,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   -6,    0,
    0,    0,    0,    0,    0,  396,    0,    0,  965,   12,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   33,    0, 1109,    0,
    0,  -37,    0,    0,    0,   30,    0,    0,    0,    0,
    0,    0,    0,    1,    0,    0,    0,   69,  439,  526,
  650,  861,  931,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, 1055,  988,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, 1212,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -27,    0,    0,    0,   98,  193,  481,  555,  612,  807,
  898,  135,    0,  135,    0,    0,    0,    0, -146,    0,
    0, 1018,    0,    0,    0,    0,    0, 1282,    0,    0,
    0,    0,   -4,    0,    0,    0,    0,    0,  362,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
  104,    0,    0,    0,    0,    0,    0, -112,    0,   34,
    0, -111,    0,   43,    0,    0,    0, 1577,   87,    0,
  -44,    0,   95, -121,   83,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   80,    0,    0,
    0,    0,    0,    0,    0,  515,    0,  573,    0,  -28,
  -13,   18,    0,  -35,   19,   23,    0,    0,    0,    0,
};
final static int YYTABLESIZE=1744;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         17,
  102,   50,   17,   17,   17,   17,  107,   17,   17,   17,
  116,   40,  117,   78,  165,  158,   19,  164,  111,  162,
   81,   80,   17,  109,   17,  103,  104,   83,  110,  103,
  168,  169,  170,   26,   89,  175,   38,  102,  174,   38,
  102,  102,  102,  102,  102,  102,  173,  102,   87,  113,
   79,  112,   26,  100,   86,   50,  182,  121,  102,  102,
  102,  158,  102,   26,  119,  120,  103,  127,  111,  122,
  103,  103,  103,  103,  103,  128,  103,  129,   93,  130,
  142,  143,  144,  148,  149,   17,   29,  103,  103,  103,
  131,  103,  132,  131,  133,   78,  137,  113,  145,  146,
  140,  105,  152,  153,  154,   29,  163,  167,  111,  111,
   86,  111,  111,  111,  178,  179,   29,  124,   41,   41,
  126,   15,   81,  102,   88,  102,  111,  111,  111,  180,
  111,   99,  102,    2,    3,  147,   40,  113,  113,  150,
  113,  113,  113,    9,  151,    0,   10,   11,    0,   13,
    0,   55,  103,    0,  103,  113,  113,  113,   53,  113,
    0,  159,   96,   54,    0,    0,    0,    0,    0,    0,
    0,   95,    0,    0,    0,    0,   95,   95,    0,   95,
   95,   95,  166,    0,    0,   26,   26,   26,    0,    0,
   55,  111,  112,  111,   95,    0,   95,   20,  134,  172,
    0,    0,   54,    0,    0,    0,    0,  159,    0,    0,
   49,   26,    0,    0,    0,  106,    0,  181,    0,    0,
  113,    0,  113,    0,    0,    0,    0,    0,    0,   81,
  114,  115,  112,  112,    0,  112,  112,  112,   29,   29,
   29,    0,   17,   17,   17,   17,   17,   17,    0,    0,
  112,  112,  112,    0,  112,    0,    0,  102,  102,  102,
  102,  102,  102,  102,   29,  102,  102,  102,  102,    0,
    0,  102,  102,  102,  102,  102,  102,  102,  102,  102,
  102,  102,  102,  102,  102,  102,  103,  103,  103,  103,
  103,  103,  103,    0,  103,  103,  103,  103,    0,  157,
  103,  103,  103,  103,  103,  103,  103,  103,  103,  103,
  103,  103,  103,  103,  103,  112,    0,  112,  131,  131,
  131,  131,  131,  131,    0,  111,  111,  111,  111,  111,
  111,  111,    0,  111,  111,  111,  111,    0,    0,  111,
  111,  111,  111,  111,  111,  111,  111,  111,  111,  111,
  111,  111,  111,  111,  113,  113,  113,  113,  113,  113,
  113,   70,  113,  113,  113,  113,    0,    0,  113,  113,
  113,  113,  113,  113,  113,  113,  113,  113,  113,  113,
  113,  113,  113,    0,    0,    0,   52,   90,   91,   92,
   93,   94,   95,   14,   15,   16,   17,   18,   95,    0,
    0,   70,    0,   95,   95,    0,   95,   95,   95,    0,
    0,    0,    0,    0,   95,   95,   95,   95,   95,   95,
   70,   95,    0,   95,    0,    9,    0,    0,    0,    0,
   55,    0,   14,   15,   16,   17,   18,   74,  118,    0,
    0,    0,   54,    0,    0,    0,    0,    0,    0,  112,
  112,  112,  112,  112,  112,  112,  130,  112,  112,  112,
  112,    0,    0,  112,  112,  112,  112,  112,  112,  112,
  112,  112,  112,  112,  112,  112,  112,  112,  118,  118,
  119,    0,  118,    0,   70,    0,   70,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  118,  118,  118,    0,
  118,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   45,    0,    2,    3,    0,    0,
  119,  119,    0,    0,  119,  120,    9,    0,    0,   10,
   11,    0,   13,   45,   55,    0,    0,    0,  119,  119,
  119,   20,  119,    0,   45,    0,   54,    0,    0,    0,
    0,    0,    0,    0,  121,    0,    0,    0,    0,    0,
    0,  118,    0,  118,    0,  120,  120,    0,    0,  120,
    0,    0,   47,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  120,  120,    0,    0,    0,    0,    0,
    0,   47,    0,    0,  121,  121,    0,    0,  121,    0,
    0,    0,   47,  119,    0,  119,    0,    0,    0,    0,
    0,  122,  121,  121,    0,    0,    0,    0,   70,   70,
   70,   70,   70,   70,   70,    0,   70,   70,    0,   70,
    0,    0,   70,   70,   70,   70,   70,   70,   70,   70,
   70,   95,   95,   95,   95,   95,   95,    0,  120,  123,
  120,  122,  122,    0,    0,  122,    0,    0,    0,    0,
    0,    0,    0,   15,    0,    9,   45,   45,   45,  122,
  122,    0,   14,   15,   16,   17,   18,  121,    0,  121,
    0,  130,  130,  130,  130,  130,  130,    0,    0,  123,
  123,    0,   45,  123,    0,  118,  118,  118,  118,  118,
  118,  118,    0,  118,  118,  118,  118,  123,  123,  118,
  118,  118,  118,  118,  118,  118,  118,  118,  118,  118,
  118,  118,  118,  118,   47,   47,   47,    0,    0,    0,
    0,    0,    0,    0,  122,    0,  122,  119,  119,  119,
  119,  119,  119,  119,    0,  119,  119,  119,  119,    0,
   47,  119,  119,  119,  119,  119,  119,  119,  119,  119,
  119,  119,  119,  119,  119,  119,    0,    0,    0,    9,
    0,    0,  123,    0,  123,    0,   14,   15,   16,   17,
   18,    0,  120,  120,  120,  120,  120,  120,  120,    0,
  120,  120,  120,  120,    0,    0,  120,  120,  120,  120,
  120,  120,  120,  120,  120,    0,  124,  120,  120,  120,
  120,  121,  121,  121,  121,  121,  121,  121,    0,  121,
  121,  121,  121,    0,    0,  121,  121,  121,  121,  121,
  121,  121,  121,  121,    0,    0,  121,  121,  121,  121,
    0,    0,    0,    0,    0,    0,  124,  124,    0,    0,
  124,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  125,    0,    0,    0,  124,  124,    0,    0,  122,  122,
  122,  122,  122,  122,  122,    0,  122,  122,  122,  122,
    0,    0,  122,  122,  122,  122,  122,  122,  122,  122,
  122,    0,    0,  122,  122,  122,  122,  126,    0,    0,
  125,  125,    0,    0,  125,    0,  123,  123,  123,  123,
  123,  123,  123,    0,  123,  123,  123,  123,  125,  125,
  123,  123,  123,  123,  123,  123,  123,  123,  123,  124,
  127,  124,    0,  123,  123,    0,    0,  126,  126,    0,
    0,  126,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  126,  126,    0,    0,    0,
    0,    0,    0,    0,   63,    0,    0,    0,    0,    0,
  127,  127,    0,    0,  127,    0,    0,    0,    0,    0,
    0,    0,    0,  125,    0,  125,    0,   20,  127,  127,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   63,    0,    0,    0,    0,    0,
   94,    0,    0,    0,    0,    0,    0,   21,    0,    0,
  126,    0,  126,   63,    0,    0,    0,   20,    0,    0,
    0,   20,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   20,    0,    0,    0,
    0,    0,    0,  127,   48,  127,    0,   21,    0,    0,
    0,   21,    0,  124,  124,  124,  124,  124,  124,  124,
    0,  124,  124,  124,  124,    0,   21,  124,  124,  124,
  124,  124,  124,  124,  124,  124,    0,   63,    0,   63,
  124,  124,    0,    0,   48,    0,    0,    0,    0,    0,
    0,    0,   55,    0,    0,    0,    0,    0,   46,   77,
   20,    0,   20,   48,   54,    0,    0,  125,  125,  125,
  125,  125,  125,  125,    0,  125,  125,  125,  125,    0,
    0,  125,  125,  125,  125,  125,  125,  125,  125,  125,
   21,    0,   21,    0,    0,  125,    0,    0,   46,    0,
    0,    0,    0,    0,  126,  126,  126,  126,  126,  126,
  126,    0,  126,  126,  126,  126,    0,    0,  126,  126,
  126,  126,  126,  126,  126,  126,  126,   48,   46,   48,
    0,    0,  126,    0,    0,    0,    0,  127,  127,  127,
  127,  127,  127,  127,    0,  127,  127,  127,  127,    0,
    0,  127,  127,  127,  127,  127,  127,  127,  127,  127,
    0,   64,    0,    0,    0,    0,    0,    0,   46,    0,
    0,   63,   63,   63,   63,   63,   63,   63,    0,   63,
   63,   46,   63,   46,    0,   63,   63,   63,   63,   63,
   63,   63,   63,   63,   20,   20,   20,   20,   20,   20,
   20,   64,   20,   20,    0,   20,    0,    0,   20,   20,
   20,   20,   20,   20,   20,   20,   20,    0,    0,    0,
   64,    0,    0,    0,   21,   21,   21,   21,   21,   21,
   21,   65,   21,   21,    0,   21,    0,    0,   21,   21,
   21,   21,   21,   21,   21,   21,   21,    0,    0,    0,
    0,   46,    0,   46,   91,    0,    0,    0,    0,    0,
    0,   48,   48,   48,   48,   48,   48,   48,    0,   48,
   48,   65,   48,    0,    0,   48,   48,   48,   48,   48,
   48,   48,   48,   48,   64,    0,   64,    9,    0,    0,
   65,    0,    0,    0,   14,   15,   16,   17,   18,   55,
    0,    0,    0,    0,    0,    0,   20,    0,    0,    0,
    0,   54,    0,   91,    0,   46,   46,   46,   46,   46,
   46,   46,    0,   46,   46,    0,   46,    0,    0,   46,
   46,   46,   46,   46,   46,   46,   46,   46,   55,    0,
    0,    0,    0,    0,    0,  155,    0,    0,    0,    0,
   54,    0,    0,    0,   65,    0,   65,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   20,    0,    0,    0,   91,    0,   91,
    0,    0,    0,    0,    0,   46,   46,   46,   46,   46,
   46,   46,   21,   46,   46,    0,    0,    0,    0,   46,
   46,   46,   46,   46,   46,   46,   46,   46,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   64,   64,
   64,    0,   64,   64,   64,    0,   64,   64,    0,   64,
    0,    0,   64,   64,   64,   64,   64,   64,   64,   64,
   64,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   19,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   65,   65,
   65,    0,   65,   65,   65,    0,   65,   65,    0,   65,
    0,    0,   65,   65,   65,   65,   65,   65,   65,   65,
   65,   91,   91,   91,   91,   91,   91,   91,    0,   91,
   91,    0,    0,    0,    0,   91,   91,   91,   91,    0,
    0,   75,   76,   78,   52,    0,    0,    0,    0,    0,
    0,   14,   15,   16,   17,   18,   82,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    9,    0,    0,    0,    0,    0,  101,
   14,   15,   16,   17,   18,    0,    0,    0,    0,    0,
    1,    2,    3,    0,    4,    5,    6,    0,    7,    8,
  123,    9,    0,  125,   10,   11,   12,   13,   14,   15,
   16,   17,   18,  136,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  138,    0,    0,  139,    0,    0,    0,
    0,    0,    0,    0,  141,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  156,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  171,    0,    0,    0,    0,    0,    0,    0,    0,
  176,    0,    0,  177,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
    0,   59,   40,   41,   42,   43,   58,   45,   46,   47,
   60,    0,   62,   41,   41,  128,  123,   44,   37,  131,
   58,  268,   60,   42,   62,   54,   55,  268,   47,    0,
  152,  153,  154,    0,   46,   41,   41,   37,   44,   44,
   40,   41,   42,   43,   44,   45,  158,   47,   40,   43,
    8,   45,   19,   61,   46,   59,  178,  284,   58,   59,
   60,  174,   62,   30,  282,  283,   37,  265,    0,  285,
   41,   42,   43,   44,   45,   40,   47,  125,   46,   41,
  109,  110,  111,  119,  120,  123,    0,   58,   59,   60,
   44,   62,   91,   61,  268,  123,  268,    0,  112,  113,
   41,   59,   41,  260,   41,   19,   93,   58,   40,   41,
   46,   43,   44,   45,  260,   41,   30,   75,  125,    0,
   78,  268,   19,  123,   30,  125,   58,   59,   60,  174,
   62,   49,   53,  258,  259,  118,  125,   40,   41,  121,
   43,   44,   45,  268,  122,   -1,  271,  272,   -1,  274,
   -1,   33,  123,   -1,  125,   58,   59,   60,   40,   62,
   -1,  128,   61,   45,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,  140,   -1,   -1,  152,  153,  154,   -1,   -1,
   33,  123,    0,  125,   60,   -1,   62,   40,   41,  157,
   -1,   -1,   45,   -1,   -1,   -1,   -1,  174,   -1,   -1,
  268,  178,   -1,   -1,   -1,  267,   -1,  175,   -1,   -1,
  123,   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,  267,
  280,  281,   40,   41,   -1,   43,   44,   45,  152,  153,
  154,   -1,  280,  281,  282,  283,  284,  285,   -1,   -1,
   58,   59,   60,   -1,   62,   -1,   -1,  257,  258,  259,
  260,  261,  262,  263,  178,  265,  266,  267,  268,   -1,
   -1,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  280,  281,  282,  283,  284,  285,  257,  258,  259,  260,
  261,  262,  263,   -1,  265,  266,  267,  268,   -1,   41,
  271,  272,  273,  274,  275,  276,  277,  278,  279,  280,
  281,  282,  283,  284,  285,  123,   -1,  125,  286,  287,
  288,  289,  290,  291,   -1,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,  268,   -1,   -1,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  280,  281,
  282,  283,  284,  285,  257,  258,  259,  260,  261,  262,
  263,    0,  265,  266,  267,  268,   -1,   -1,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  280,  281,  282,
  283,  284,  285,   -1,   -1,   -1,  268,  286,  287,  288,
  289,  290,  291,  275,  276,  277,  278,  279,   37,   -1,
   -1,   40,   -1,   42,   43,   -1,   45,   46,   47,   -1,
   -1,   -1,   -1,   -1,  280,  281,  282,  283,  284,  285,
   59,   60,   -1,   62,   -1,  268,   -1,   -1,   -1,   -1,
   33,   -1,  275,  276,  277,  278,  279,   40,    0,   -1,
   -1,   -1,   45,   -1,   -1,   -1,   -1,   -1,   -1,  257,
  258,  259,  260,  261,  262,  263,   61,  265,  266,  267,
  268,   -1,   -1,  271,  272,  273,  274,  275,  276,  277,
  278,  279,  280,  281,  282,  283,  284,  285,   40,   41,
    0,   -1,   44,   -1,  123,   -1,  125,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   58,   59,   60,   -1,
   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,    0,   -1,  258,  259,   -1,   -1,
   40,   41,   -1,   -1,   44,    0,  268,   -1,   -1,  271,
  272,   -1,  274,   19,   33,   -1,   -1,   -1,   58,   59,
   60,   40,   62,   -1,   30,   -1,   45,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,    0,   -1,   -1,   -1,   -1,   -1,
   -1,  123,   -1,  125,   -1,   40,   41,   -1,   -1,   44,
   -1,   -1,    0,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   58,   59,   -1,   -1,   -1,   -1,   -1,
   -1,   19,   -1,   -1,   40,   41,   -1,   -1,   44,   -1,
   -1,   -1,   30,  123,   -1,  125,   -1,   -1,   -1,   -1,
   -1,    0,   58,   59,   -1,   -1,   -1,   -1,  257,  258,
  259,  260,  261,  262,  263,   -1,  265,  266,   -1,  268,
   -1,   -1,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  280,  281,  282,  283,  284,  285,   -1,  123,    0,
  125,   40,   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  268,   -1,  268,  152,  153,  154,   58,
   59,   -1,  275,  276,  277,  278,  279,  123,   -1,  125,
   -1,  286,  287,  288,  289,  290,  291,   -1,   -1,   40,
   41,   -1,  178,   44,   -1,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,  267,  268,   58,   59,  271,
  272,  273,  274,  275,  276,  277,  278,  279,  280,  281,
  282,  283,  284,  285,  152,  153,  154,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  123,   -1,  125,  257,  258,  259,
  260,  261,  262,  263,   -1,  265,  266,  267,  268,   -1,
  178,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  280,  281,  282,  283,  284,  285,   -1,   -1,   -1,  268,
   -1,   -1,  123,   -1,  125,   -1,  275,  276,  277,  278,
  279,   -1,  257,  258,  259,  260,  261,  262,  263,   -1,
  265,  266,  267,  268,   -1,   -1,  271,  272,  273,  274,
  275,  276,  277,  278,  279,   -1,    0,  282,  283,  284,
  285,  257,  258,  259,  260,  261,  262,  263,   -1,  265,
  266,  267,  268,   -1,   -1,  271,  272,  273,  274,  275,
  276,  277,  278,  279,   -1,   -1,  282,  283,  284,  285,
   -1,   -1,   -1,   -1,   -1,   -1,   40,   41,   -1,   -1,
   44,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
    0,   -1,   -1,   -1,   58,   59,   -1,   -1,  257,  258,
  259,  260,  261,  262,  263,   -1,  265,  266,  267,  268,
   -1,   -1,  271,  272,  273,  274,  275,  276,  277,  278,
  279,   -1,   -1,  282,  283,  284,  285,    0,   -1,   -1,
   40,   41,   -1,   -1,   44,   -1,  257,  258,  259,  260,
  261,  262,  263,   -1,  265,  266,  267,  268,   58,   59,
  271,  272,  273,  274,  275,  276,  277,  278,  279,  123,
    0,  125,   -1,  284,  285,   -1,   -1,   40,   41,   -1,
   -1,   44,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   58,   59,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,    0,   -1,   -1,   -1,   -1,   -1,
   40,   41,   -1,   -1,   44,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  123,   -1,  125,   -1,    0,   58,   59,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,   -1,
   46,   -1,   -1,   -1,   -1,   -1,   -1,    0,   -1,   -1,
  123,   -1,  125,   59,   -1,   -1,   -1,   40,   -1,   -1,
   -1,   44,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   59,   -1,   -1,   -1,
   -1,   -1,   -1,  123,    0,  125,   -1,   40,   -1,   -1,
   -1,   44,   -1,  257,  258,  259,  260,  261,  262,  263,
   -1,  265,  266,  267,  268,   -1,   59,  271,  272,  273,
  274,  275,  276,  277,  278,  279,   -1,  123,   -1,  125,
  284,  285,   -1,   -1,   40,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   33,   -1,   -1,   -1,   -1,   -1,    0,   40,
  123,   -1,  125,   59,   45,   -1,   -1,  257,  258,  259,
  260,  261,  262,  263,   -1,  265,  266,  267,  268,   -1,
   -1,  271,  272,  273,  274,  275,  276,  277,  278,  279,
  123,   -1,  125,   -1,   -1,  285,   -1,   -1,   40,   -1,
   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,
  263,   -1,  265,  266,  267,  268,   -1,   -1,  271,  272,
  273,  274,  275,  276,  277,  278,  279,  123,    0,  125,
   -1,   -1,  285,   -1,   -1,   -1,   -1,  257,  258,  259,
  260,  261,  262,  263,   -1,  265,  266,  267,  268,   -1,
   -1,  271,  272,  273,  274,  275,  276,  277,  278,  279,
   -1,    0,   -1,   -1,   -1,   -1,   -1,   -1,   40,   -1,
   -1,  257,  258,  259,  260,  261,  262,  263,   -1,  265,
  266,  123,  268,  125,   -1,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  257,  258,  259,  260,  261,  262,
  263,   40,  265,  266,   -1,  268,   -1,   -1,  271,  272,
  273,  274,  275,  276,  277,  278,  279,   -1,   -1,   -1,
   59,   -1,   -1,   -1,  257,  258,  259,  260,  261,  262,
  263,    0,  265,  266,   -1,  268,   -1,   -1,  271,  272,
  273,  274,  275,  276,  277,  278,  279,   -1,   -1,   -1,
   -1,  123,   -1,  125,    0,   -1,   -1,   -1,   -1,   -1,
   -1,  257,  258,  259,  260,  261,  262,  263,   -1,  265,
  266,   40,  268,   -1,   -1,  271,  272,  273,  274,  275,
  276,  277,  278,  279,  123,   -1,  125,  268,   -1,   -1,
   59,   -1,   -1,   -1,  275,  276,  277,  278,  279,   33,
   -1,   -1,   -1,   -1,   -1,   -1,   40,   -1,   -1,   -1,
   -1,   45,   -1,   59,   -1,  257,  258,  259,  260,  261,
  262,  263,   -1,  265,  266,   -1,  268,   -1,   -1,  271,
  272,  273,  274,  275,  276,  277,  278,  279,   33,   -1,
   -1,   -1,   -1,   -1,   -1,   40,   -1,   -1,   -1,   -1,
   45,   -1,   -1,   -1,  123,   -1,  125,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   40,   -1,   -1,   -1,  123,   -1,  125,
   -1,   -1,   -1,   -1,   -1,  257,  258,  259,  260,  261,
  262,  263,   59,  265,  266,   -1,   -1,   -1,   -1,  271,
  272,  273,  274,  275,  276,  277,  278,  279,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,   -1,  261,  262,  263,   -1,  265,  266,   -1,  268,
   -1,   -1,  271,  272,  273,  274,  275,  276,  277,  278,
  279,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  123,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,
  259,   -1,  261,  262,  263,   -1,  265,  266,   -1,  268,
   -1,   -1,  271,  272,  273,  274,  275,  276,  277,  278,
  279,  257,  258,  259,  260,  261,  262,  263,   -1,  265,
  266,   -1,   -1,   -1,   -1,  271,  272,  273,  274,   -1,
   -1,    5,    6,    7,  268,   -1,   -1,   -1,   -1,   -1,
   -1,  275,  276,  277,  278,  279,   20,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  268,   -1,   -1,   -1,   -1,   -1,   53,
  275,  276,  277,  278,  279,   -1,   -1,   -1,   -1,   -1,
  257,  258,  259,   -1,  261,  262,  263,   -1,  265,  266,
   74,  268,   -1,   77,  271,  272,  273,  274,  275,  276,
  277,  278,  279,   87,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   97,   -1,   -1,  100,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  108,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  127,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  155,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  164,   -1,   -1,  167,
};
}
final static short YYFINAL=22;
final static short YYMAXTOKEN=293;
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
"CLASS","STRING","BOOL","FN","INTARRAY","INTLIT","DOUBLELIT","STRINGLIT",
"BOOLLIT","NULLVAL","LESSTHANOREQUAL","GREATERTHANOREQUAL","ISEQUALTO",
"NOTEQUALTO","LOGICALAND","LOGICALOR","INCREMENT","DECREMENT","MINUSEQUAL",
"ADDEQUAL","MULEQUAL","DIVEQUAL","PUBLIC","STATIC",
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
"Type : INTARRAY",
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
case 19:
//#line 30 "j0gram.y"
{
  yyval=j0.node("QualifiedName",1040,val_peek(2),val_peek(0));}
break;
case 21:
//#line 33 "j0gram.y"
{
  yyval=j0.node("VarDecls",1050,val_peek(2),val_peek(0)); }
break;
case 23:
//#line 35 "j0gram.y"
{
  yyval=j0.node("VarDeclarator",1060,val_peek(2)); }
break;
case 26:
//#line 40 "j0gram.y"
{ yyval=j0.node("MethodDecl",1380,val_peek(4),val_peek(2),val_peek(0)); }
break;
case 27:
//#line 41 "j0gram.y"
{ yyval=j0.node("MethodDecl",1381,val_peek(3),val_peek(0)); }
break;
case 31:
//#line 44 "j0gram.y"
{
  yyval=j0.node("ArgList",1270,val_peek(2),val_peek(0)); }
break;
case 32:
//#line 47 "j0gram.y"
{ yyval=j0.node("MethodCall",1290,val_peek(3),val_peek(1)); }
break;
case 33:
//#line 48 "j0gram.y"
{ yyval=j0.node("MethodCall",1291,val_peek(2)); }
break;
case 37:
//#line 53 "j0gram.y"
{
  yyval=j0.node("FormalParmList",1090,val_peek(2),val_peek(0)); }
break;
case 38:
//#line 55 "j0gram.y"
{
  yyval=j0.node("FormalParm",1100,val_peek(1),val_peek(0));
 }
break;
case 39:
//#line 59 "j0gram.y"
{yyval=j0.node("Block",1200,val_peek(1));}
break;
case 43:
//#line 61 "j0gram.y"
{
  yyval=j0.node("BlockStmts",1130,val_peek(1),val_peek(0)); }
break;
case 48:
//#line 68 "j0gram.y"
{
  yyval=j0.node("LocalVarDecl",1140,val_peek(1),val_peek(0)); }
break;
case 64:
//#line 79 "j0gram.y"
{ yyval=j0.node("IfStmt",1150,val_peek(1),val_peek(0)); }
break;
case 65:
//#line 80 "j0gram.y"
{ yyval=j0.node("IfStmt",1151,val_peek(2),val_peek(0)); }
break;
case 66:
//#line 81 "j0gram.y"
{ yyval=j0.node("IfElseStmt",1160,val_peek(4),val_peek(2),val_peek(0)); }
break;
case 67:
//#line 82 "j0gram.y"
{ yyval=j0.node("IfElseStmt",1161,val_peek(3),val_peek(2),val_peek(0)); }
break;
case 68:
//#line 84 "j0gram.y"
{ yyval=j0.node("WhileStmt",1210,val_peek(2),val_peek(0)); }
break;
case 69:
//#line 85 "j0gram.y"
{ yyval=j0.node("WhileStmt",1211,val_peek(1),val_peek(0)); }
break;
case 70:
//#line 87 "j0gram.y"
{
    yyval=j0.node("DoWhileStmt",1212,val_peek(1),val_peek(4)); }
break;
case 71:
//#line 89 "j0gram.y"
{
        yyval=j0.node("DoWhileStmt",1213,val_peek(0),val_peek(2)); }
break;
case 72:
//#line 110 "j0gram.y"
{ yyval=j0.node("ForStmt",1220,val_peek(2),val_peek(0)); }
break;
case 73:
//#line 111 "j0gram.y"
{ yyval=j0.node("ForStmt",1221,val_peek(1),val_peek(0)); }
break;
case 77:
//#line 115 "j0gram.y"
{ yyval=j0.node("ForShort",1226,val_peek(0)); }
break;
case 78:
//#line 116 "j0gram.y"
{ yyval=j0.node("ForNormal",1222,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 79:
//#line 117 "j0gram.y"
{ yyval=j0.node("ForFull",1223,val_peek(4),val_peek(3),val_peek(2),val_peek(0)); }
break;
case 80:
//#line 119 "j0gram.y"
{ yyval=j0.node("ForVarInit",1224,val_peek(2),val_peek(0)); }
break;
case 81:
//#line 120 "j0gram.y"
{ yyval=j0.node("ForVarInit",1225,val_peek(0)); }
break;
case 87:
//#line 126 "j0gram.y"
{
  yyval=j0.node("StmtExprList",1230,val_peek(2),val_peek(0)); }
break;
case 89:
//#line 129 "j0gram.y"
{
  yyval=j0.node("BreakStmt",1240,val_peek(1)); }
break;
case 90:
//#line 131 "j0gram.y"
{ yyval=j0.node("ReturnStmt",1250,val_peek(0)); }
break;
case 91:
//#line 132 "j0gram.y"
{ yyval=j0.node("ReturnStmt",1251);}
break;
case 95:
//#line 134 "j0gram.y"
{
  yyval=val_peek(1);}
break;
case 101:
//#line 138 "j0gram.y"
{
  yyval=j0.node("FieldAccess",1280,val_peek(2),val_peek(0)); }
break;
case 104:
//#line 142 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1300,val_peek(1),val_peek(0)); }
break;
case 105:
//#line 144 "j0gram.y"
{
  yyval=j0.node("UnaryExpr",1301,val_peek(1),val_peek(0)); }
break;
case 108:
//#line 148 "j0gram.y"
{
      yyval=j0.node("MulExpr",1310,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 109:
//#line 150 "j0gram.y"
{
      yyval=j0.node("MulExpr",1311,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 110:
//#line 152 "j0gram.y"
{
      yyval=j0.node("MulExpr",1312,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 112:
//#line 155 "j0gram.y"
{
      yyval=j0.node("AddExpr",1320,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 113:
//#line 157 "j0gram.y"
{
      yyval=j0.node("AddExpr",1321,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 119:
//#line 160 "j0gram.y"
{
  yyval=j0.node("RelExpr",1330,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 121:
//#line 164 "j0gram.y"
{
  yyval=j0.node("EqExpr",1340,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 122:
//#line 166 "j0gram.y"
{
  yyval=j0.node("EqExpr",1341,val_peek(2),val_peek(1),val_peek(0)); }
break;
case 124:
//#line 168 "j0gram.y"
{
  yyval=j0.node("CondAndExpr", 1350, val_peek(2),val_peek(1), val_peek(0)); }
break;
case 126:
//#line 170 "j0gram.y"
{
  yyval=j0.node("CondOrExpr", 1360, val_peek(2),val_peek(1), val_peek(0)); }
break;
case 128:
//#line 174 "j0gram.y"
{
yyval=j0.node("Assignment",1370, val_peek(2), val_peek(1), val_peek(0)); }
break;
case 129:
//#line 176 "j0gram.y"
{ yyval=j0.node("Assignment",1380, val_peek(1), val_peek(0)); }
break;
//#line 1168 "Parser.java"
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
