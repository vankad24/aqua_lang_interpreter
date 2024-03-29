%token BREAK FLOAT INT ELSE FOR IF RETURN VOID WHILE DO RANGESEPARATOR
%token IDENTIFIER CLASSNAME CLASS STRING BOOL FN
%token INTLIT DOUBLELIT STRINGLIT BOOLLIT NULLVAL
%token LESSTHANOREQUAL GREATERTHANOREQUAL
%token ISEQUALTO NOTEQUALTO LOGICALAND LOGICALOR
%token INCREMENT DECREMENT PUBLIC STATIC
%{
import static frontend.j0.yylex;
import static frontend.Yyerror.yyerror;
%}
%%
StartTerm: BlockStmtsOpt{
    $$=j0.node("BlockStmtsOpt",1024,$1);
    j0.process((Tree) $$.obj);
};
ClassDecl: PUBLIC CLASS IDENTIFIER ClassBody {
  $$=j0.node("ClassDecl",1000,$3,$4);
 } ;
ClassBody: '{' ClassBodyDecls '}' { $$=j0.node("ClassBody",1010,$2); }
         | '{' '}' { $$=j0.node("ClassBody",1011); };
ClassBodyDecls: ClassBodyDecl
| ClassBodyDecls ClassBodyDecl {
  $$=j0.node("ClassBodyDecls",1020,$1,$2); };
ClassBodyDecl: FieldDecl | MethodDecl |  ;
FieldDecl: Type VarDecls{
  $$=j0.node("FieldDecl",1030,$1,$2); };
Type: INT | FLOAT | BOOL | STRING | Name ;

Name: IDENTIFIER | QualifiedName ;
QualifiedName: Name '.' IDENTIFIER {
  $$=j0.node("QualifiedName",1040,$1,$3);};

VarDecls: VarDeclarator | VarDecls ',' VarDeclarator {
  $$=j0.node("VarDecls",1050,$1,$3); };
VarDeclarator: IDENTIFIER | VarDeclarator '[' ']' {
  $$=j0.node("VarDeclarator",1060,$1); };

BlockOpt: Block | ;

MethodDecl: FN IDENTIFIER '(' FormalParmList ')' Block { $$=j0.node("MethodDecl",1380,$2,$4,$6); }
|FN IDENTIFIER '(' ')' Block { $$=j0.node("MethodDecl",1381,$2,$5); };

ArgListOpt:  ArgList | ;
ArgList: Expr | ArgList ',' Expr {
  $$=j0.node("ArgList",1270,$1,$3); };

MethodCall: Name '(' ArgList ')' { $$=j0.node("MethodCall",1290,$1,$3); }
| Name '(' ')' { $$=j0.node("MethodCall",1291,$1); };
  //| Primary '.' IDENTIFIER '(' ArgListOpt ')' {
  //  $$=j0.node("MethodCall",1291,$1,$3,$5); };

FormalParmListOpt: FormalParmList | ;
FormalParmList: FormalParm | FormalParmList ',' FormalParm {
  $$=j0.node("FormalParmList",1090,$1,$3); };
FormalParm: Type VarDeclarator {
  $$=j0.node("FormalParm",1100,$1,$2);
 };

Block: '{' BlockStmtsOpt '}' {$$=j0.node("Block",1200,$2);};
BlockStmtsOpt: BlockStmts | ;
BlockStmts:  BlockStmt | BlockStmts BlockStmt {
  $$=j0.node("BlockStmts",1130,$1,$2); };
BlockStmt: Stmt ;

StmtEnd: ';' | ;

LocalVarDeclStmt: LocalVarDecl;
LocalVarDecl: Type VarDecls {
  $$=j0.node("LocalVarDecl",1140,$1,$2); };

Stmt: Block | ';' | ExprStmt | BreakStmt | ReturnStmt
      | IfStmt | IfElseStmt | MethodDecl
      | DoWhileStmt | WhileStmt | ForStmt | LocalVarDeclStmt;

ExprStmt: StmtExpr;

StmtExpr: Assignment | MethodCall ;

IfStmt: IF Expr Block { $$=j0.node("IfStmt",1150,$2,$3); }
| IF '(' Expr ')' Stmt { $$=j0.node("IfStmt",1151,$3,$5); };
IfElseStmt: IF '(' Expr ')' Stmt ELSE Stmt { $$=j0.node("IfElseStmt",1160,$3,$5,$7); }
| IF Expr Block ELSE Stmt { $$=j0.node("IfElseStmt",1161,$2,$3,$5); };

WhileStmt: WHILE '(' Expr ')' Stmt { $$=j0.node("WhileStmt",1210,$3,$5); }
| WHILE Expr Block { $$=j0.node("WhileStmt",1211,$2,$3); };

DoWhileStmt: DO Block WHILE '(' Expr ')' {
    $$=j0.node("DoWhileStmt",1212,$5,$2); }|
    DO Block WHILE Expr{
        $$=j0.node("DoWhileStmt",1213,$4,$2); };

//todo FOR '(' ForHeader ')' Stmt
/*
ForStmt: FOR '(' ForHeader ')' Stmt { $$=j0.node("ForStmt",1220,$3,$5); }
 | FOR ForHeader Block { $$=j0.node("ForStmt",1221,$2,$3); };

ForHeader: ForInit ForLimit ForStep;

ForInit: IDENTIFIER '=' Expr ForSeparator{ $$=j0.node("ForInit",1221,$1,$3,$4); }
 | IDENTIFIER ForSeparator{ $$=j0.node("ForInit",1222,$1,$2); }
 | { $$=j0.node("ForInit",1223); };

ForLimit: Expr;

ForStep: ':' Expr { $$=j0.node("ForStep",1224,$2); }
| { $$=j0.node("ForStep",1225); };

*/

ForStmt: FOR '(' ForHeader ')' Block { $$=j0.node("ForStmt",1220,$3,$5); }
 | FOR ForHeader Block { $$=j0.node("ForStmt",1221,$2,$3); };

ForHeader: ForShort | ForNormal | ForFull;

ForShort: Expr;
ForNormal: ForInit ForSeparator Expr { $$=j0.node("ForNormal",1222,$1,$2,$3); };
ForFull: ForInit ForSeparator Expr ':' Expr { $$=j0.node("ForFull",1223,$1,$2,$3,$5); }

ForInit: ForVarInit | ForVar;
ForVar: IDENTIFIER;
ForVarInit: IDENTIFIER '=' Expr { $$=j0.node("ForVarInit",1224,$1,$3); };
ForSeparator: ':' | RANGESEPARATOR;

ExprOpt: Expr |  ;

StmtExprList: StmtExpr | StmtExprList ',' StmtExpr {
  $$=j0.node("StmtExprList",1230,$1,$3); };

BreakStmt: BREAK StmtEnd | BREAK IDENTIFIER StmtEnd {
  $$=j0.node("BreakStmt",1240,$2); };
ReturnStmt: RETURN Expr { $$=j0.node("ReturnStmt",1250,$2); }
  |RETURN { $$=j0.node("ReturnStmt",1251);};

Primary:  Literal | FieldAccess | MethodCall | '(' Expr ')' {
  $$=$2;};
Literal: INTLIT	| DOUBLELIT | BOOLLIT | STRINGLIT | NULLVAL ;

FieldAccess: Primary '.' IDENTIFIER {
  $$=j0.node("FieldAccess",1280,$1,$3); };

PostFixExpr: Primary | Name ;
UnaryExpr: '-' UnaryExpr {
  $$=j0.node("UnaryExpr",1300,$1,$2); }
    | '!' UnaryExpr {
  $$=j0.node("UnaryExpr",1301,$1,$2); }
    | PostFixExpr ;
MulExpr: UnaryExpr
    | MulExpr '*' UnaryExpr {
      $$=j0.node("MulExpr",1310,$1,$2,$3); }
    | MulExpr '/' UnaryExpr {
      $$=j0.node("MulExpr",1311,$1,$2,$3); }
    | MulExpr '%' UnaryExpr {
      $$=j0.node("MulExpr",1312,$1,$2,$3); };
AddExpr: MulExpr
    | AddExpr '+' MulExpr {
      $$=j0.node("AddExpr",1320,$1,$2,$3); }
    | AddExpr '-' MulExpr {
      $$=j0.node("AddExpr",1321,$1,$2,$3); };
RelOp: LESSTHANOREQUAL | GREATERTHANOREQUAL | '<' | '>' ;
RelExpr: AddExpr | RelExpr RelOp AddExpr {
  $$=j0.node("RelExpr",1330,$1,$2,$3); };

EqExpr: RelExpr
    | EqExpr ISEQUALTO RelExpr {
  $$=j0.node("EqExpr",1340,$1,$2,$3); }
| EqExpr NOTEQUALTO RelExpr {
  $$=j0.node("EqExpr",1341,$1,$2,$3); };
CondAndExpr: EqExpr | CondAndExpr LOGICALAND EqExpr {
  $$=j0.node("CondAndExpr", 1350, $1,$2, $3); };
CondOrExpr: CondAndExpr | CondOrExpr LOGICALOR CondAndExpr {
  $$=j0.node("CondOrExpr", 1360, $1,$2, $3); };

Expr: CondOrExpr;
Assignment: LeftHandSide AssignOp Expr {
$$=j0.node("Assignment",1370, $1, $2, $3); };
LeftHandSide: Name | FieldAccess ;
AssignOp: '=' | INCREMENT | DECREMENT ;
%%
