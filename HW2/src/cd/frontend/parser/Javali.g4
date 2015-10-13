grammar Javali; // parser grammar, parses streams of tokens

@header {
	// Java header
	package cd.frontend.parser;
}


// PARSER RULES
// TODO: declare appropriate parser rules

type
	:   primitiveType
	|   referenceType
	;

methodType
	:   type
	|   'void'
	;

primitiveType
	:   'int'
	|   'boolean'
	;

referenceType
	:   Identifier
	|   arrayType
	;

arrayType
	:   Identifier '[' ']'
	|   primitiveType '[' ']'
	;


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

statement
	:   assignmentStatement | methodCallStatement | ifStatement | whileStatement | returnStatement | writeStatement
	;

statementBlock
	:   '{' statement* '}'
	;

methodCallStatement
//	:   methodCallExpression ';'
	:	(identAccess '.')? Identifier '(' actualParamList? ')' ';'
	;

assignmentStatement
	:   identAccess '=' ( expression | newExpression | readExpression ) ';'
	;

writeStatement
	:   ( 'write' '(' expression ')' | 'writeln' '(' ')') ';'
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

newExpression
	:   'new' ( Identifier '(' ')'
			| Identifier '[' expression ']'
			| primitiveType '[' expression ']' )
	;

readExpression
	:   'read' '(' ')'
	;

/*
methodCallExpression
//	:   Identifier '(' actualParamList? ')'
//	|   identAccess '.' Identifier '(' actualParamList? ')' //TODO handle indirect left recursion
	:   (identAccess '.')? Identifier '(' actualParamList? ')'
	;
*/

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
//	|   methodCallExpression //TODO handle indirect left recursion
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
// TODO: provide appropriate lexer rules for numbers and boolean literals

Identifier
	:	Letter (Letter|Digit)*
	;

Literal
	:   Integer
	|   Boolean
	|   'null'
	;

fragment
Boolean
	:   'true'
	|   'false'
	;

fragment
Integer
	:   Decimal
	|   Hex
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
HexDigit
	:   Digit
	|   '\u0041'..'\u0046'
	|   '\u0061'..'\u0066'
	;

fragment
Letter
	:	'\u0041'..'\u005a'
	|	'\u0061'..'\u007a'
	;

fragment
Digit
	:   '\u0030'..'\u0039' // '0'..'9'
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