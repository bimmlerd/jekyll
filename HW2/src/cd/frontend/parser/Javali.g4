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
	:   'class' Identifier ('extends' Identifier)? '{' memberList '}'
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
	:	(identAccess '.')? Identifier '(' actualParamList? ')' ';'
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
	:   ( 'write' '(' expression ')' | 'writeln' '(' ')') ';'
	;

// expressions
newExpression
	:   'new' ( Identifier '(' ')'
			| Identifier '[' expression ']'
			| primitiveType '[' expression ']' )
	;

readExpression
	:   'read' '(' ')'
	;

actualParamList
	:   expression ( ',' expression )*
	;

identAccess
	:   Identifier
	|   'this'
	|   identAccess '.' Identifier
	|   identAccess '[' expression ']'
	|	Identifier '(' actualParamList? ')'
	|	identAccess '.' Identifier '(' actualParamList? ')'
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
	:	'\u0041'..'\u005a'
	|	'\u0061'..'\u007a'
	;

fragment
Digit
	:   '\u0030'..'\u0039' // '0'..'9'
	;

fragment
HexDigit
	:   Digit
	|   '\u0041'..'\u0046'
	|   '\u0061'..'\u0066'
	;

fragment
Decimal
	:   '\u0030'
	|   '\u0031'..'\u0039' Digit*
	;

fragment
Hex
	:   ('0x'|'0X') HexDigit+ // TODO unicodify
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

Identifier
	:	Letter (Letter|Digit)*
	;

Literal
	:   Integer
	|   Boolean
	|   'null'
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