grammar Javali; // parser grammar, parses streams of tokens

@header {
	// Java header
	package cd.frontend.parser;
}


// PARSER RULES

// types
type
	:   primitiveType   #typePrimitive
	|   referenceType   #typeReference
	;

methodType
	:   type     #methodTypeType
	|   'void'   #methodTypeVoid
	;

primitiveType
	:   'int'
	|   'boolean'
	;

referenceType
	:   Identifier   # referenceTypeId
	|   arrayType    # referenceTypeAr
	;

arrayType
	:   Identifier '[' ']'      # arrayTypeId
	|   primitiveType '[' ']'   # arrayTypePr
	;

// program structure
unit
 	: classDecl+ EOF
 	;

classDecl
	:   'class' Identifier ('extends' superClass)? '{' memberList '}'
	;

superClass
	: Identifier
	;

memberList
	:   (varDecl | methodDecl)*
	;

varDecl
	:   type Identifier (',' Identifier)* ';'
	;

methodDecl
	:   methodType Identifier '(' formalParamList? ')' '{' varDecl* statement* '}'
	;

formalParamList
	:   type Identifier (',' type Identifier)*
	;

// statements
statement
	:   assignmentStatement   # stmtAssignment
	|   methodCallStatement   # stmtMethodCall
	|   ifStatement           # stmtIf
	|   whileStatement        # stmtWhile
	|   returnStatement       # stmtReturn
	|   writeStatement        # stmtWrite
	;

statementBlock
	:   '{' statement* '}'
	;

assignmentStatement
	:   identAccess '=' expression ';'       # assignmentStmtExpr
	|   identAccess '=' newExpression ';'    # assignmentStmtNew
	|   identAccess '=' readExpression ';'   # assignmentStmtRead
	;

methodCallStatement
	:	Identifier '(' actualParamList? ')' ';'
    |	identAccess '.' Identifier '(' actualParamList? ')' ';'
	;

ifStatement
	:   'if' '(' expression ')' statementBlock ('else' statementBlock)?
	;

whileStatement
	:   'while' '(' expression ')' statementBlock
	;

returnStatement
	:   'return' expression? ';'
	;

writeStatement
	:   'write' '(' expression ')' ';'   # writeStmt
	|   'writeln' '(' ')' ';'            # writeLnStmt
	;

// expressions
newExpression
	:   'new' Identifier '(' ')'				 # newIdentifier
	|	'new' Identifier '[' expression ']'		 # newArrayId
	|	'new' primitiveType '[' expression ']'   # newArrayPr
	;

readExpression
	:   'read' '(' ')'
	;

actualParamList
	:   expression ( ',' expression )*
	;

identAccess
	:   Identifier                                            # identAccessId
	|   'this'                                                # identAccessThis
	|   identAccess '.' Identifier                            # identAccessField
	|   identAccess '[' expression ']'                        # identAccessArray
	|	Identifier '(' actualParamList? ')'                   # identAccessMethod
	|	identAccess '.' Identifier '(' actualParamList? ')'   # identAccessFieldMethod
	;

expression
	:   Literal                                     # LIT
	|   identAccess                                 # IDACC
	|   '(' expression ')'                          # PARS
	|   ('+'|'-'|'!') expression                    # UNARY
	|   '(' referenceType ')'expression             # CAST
	|   expression ('*'|'/'|'%') expression         # MULT
	|   expression ('+'|'-') expression             # ADD
	|   expression ('<'|'<='|'>'|'>=') expression   # COMP
	|   expression ('=='|'!=') expression           # EQ
	|   expression '&&' expression                  # LAND
	|   expression '||' expression                  # LOR
	;


// LEXER RULES

fragment
Letter
	:	'\u0024'
	|	'\u0041'..'\u005a'  // Latin capital letters 'A'..'Z'
	|	'\u005f'
	|	'\u0061'..'\u007a'  // Latin small letters 'a'..'z'
	|	'\u00c0'..'\u00d6'
	|	'\u00d8'..'\u00f6'
	|	'\u00f8'..'\u00ff'
	|	'\u0100'..'\u1fff'
	|	'\u3040'..'\u318f'
	|	'\u3300'..'\u337f'
	|	'\u3400'..'\u3d2d'
	|	'\u4e00'..'\u9fff'
	|	'\uf900'..'\ufaff'
	;

fragment
JavaIDDigit
	:	'\u0030'..'\u0039'  // Digits '0'..'9'
	|	'\u0660'..'\u0669'
	|	'\u06f0'..'\u06f9'
	|	'\u0966'..'\u096f'
	|	'\u09e6'..'\u09ef'
	|	'\u0a66'..'\u0a6f'
	|	'\u0ae6'..'\u0aef'
	|	'\u0b66'..'\u0b6f'
	|	'\u0be7'..'\u0bef'
	|	'\u0c66'..'\u0c6f'
	|	'\u0ce6'..'\u0cef'
	|	'\u0d66'..'\u0d6f'
	|	'\u0e50'..'\u0e59'
	|	'\u0ed0'..'\u0ed9'
	|	'\u1040'..'\u1049'
	;

fragment
HexDigit
	:   JavaIDDigit
	|   '\u0041'..'\u0046'  // Latin capital letters 'A'..'F'
	|   '\u0061'..'\u0066'  // Latin small letters 'a'..'f'
	;

fragment
Decimal
	:   '\u0030'
	|   '\u0031'..'\u0039' JavaIDDigit*
	;

fragment
Hex
	:   ('0x'|'0X') HexDigit+
	;

fragment
Integer
	:   Decimal
	|   Hex
	;

fragment
Boolean
	:   'true'
	|   'false'
	;

Literal
	:   Integer
	|   Boolean
	|   'null'
	;

Identifier
	:	Letter (Letter|JavaIDDigit)*
	;

// comments and white space does not produce tokens:
COMMENT
	:	'/*' .*? '*/' -> skip
	;

LINE_COMMENT
	:	'//' ~('\n'|'\r')* -> skip
	;

WS
	:	(' '|'\r'|'\t'|'\u000C'|'\n') -> skip
	;
