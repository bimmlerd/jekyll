// Generated from /Users/david/Documents/ETH/3/cd/e/jekyll/HW2/src/cd/frontend/parser/Javali.g4 by ANTLR 4.4

	// Java header
	package cd.frontend.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JavaliParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__37=1, T__36=2, T__35=3, T__34=4, T__33=5, T__32=6, T__31=7, T__30=8, 
		T__29=9, T__28=10, T__27=11, T__26=12, T__25=13, T__24=14, T__23=15, T__22=16, 
		T__21=17, T__20=18, T__19=19, T__18=20, T__17=21, T__16=22, T__15=23, 
		T__14=24, T__13=25, T__12=26, T__11=27, T__10=28, T__9=29, T__8=30, T__7=31, 
		T__6=32, T__5=33, T__4=34, T__3=35, T__2=36, T__1=37, T__0=38, Identifier=39, 
		Literal=40, COMMENT=41, LINE_COMMENT=42, WS=43;
	public static final String[] tokenNames = {
		"<INVALID>", "'/'", "'new'", "'return'", "'!='", "'class'", "'||'", "'while'", 
		"'void'", "';'", "'{'", "'&&'", "'='", "'}'", "'extends'", "'if'", "'<='", 
		"'int'", "'('", "'*'", "'this'", "','", "'.'", "'boolean'", "'write'", 
		"'>='", "'writeln'", "'['", "'=='", "'<'", "']'", "'>'", "'!'", "'%'", 
		"'read'", "'else'", "')'", "'+'", "'-'", "Identifier", "Literal", "COMMENT", 
		"LINE_COMMENT", "WS"
	};
	public static final int
		RULE_type = 0, RULE_primitiveType = 1, RULE_referenceType = 2, RULE_arrayType = 3, 
		RULE_unit = 4, RULE_classDecl = 5, RULE_memberList = 6, RULE_varDecl = 7, 
		RULE_methodDecl = 8, RULE_formalParamList = 9, RULE_statement = 10, RULE_statementBlock = 11, 
		RULE_methodCallStatement = 12, RULE_assignmentStatement = 13, RULE_writeStatement = 14, 
		RULE_ifStatement = 15, RULE_whileStatement = 16, RULE_returnStatement = 17, 
		RULE_newExpression = 18, RULE_readExpression = 19, RULE_actualParamList = 20, 
		RULE_identAccess = 21, RULE_expression = 22;
	public static final String[] ruleNames = {
		"type", "primitiveType", "referenceType", "arrayType", "unit", "classDecl", 
		"memberList", "varDecl", "methodDecl", "formalParamList", "statement", 
		"statementBlock", "methodCallStatement", "assignmentStatement", "writeStatement", 
		"ifStatement", "whileStatement", "returnStatement", "newExpression", "readExpression", 
		"actualParamList", "identAccess", "expression"
	};

	@Override
	public String getGrammarFileName() { return "Javali.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JavaliParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class TypeContext extends ParserRuleContext {
		public ReferenceTypeContext referenceType() {
			return getRuleContext(ReferenceTypeContext.class,0);
		}
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_type);
		try {
			setState(48);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(46); primitiveType();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(47); referenceType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimitiveTypeContext extends ParserRuleContext {
		public PrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitiveType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitPrimitiveType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimitiveTypeContext primitiveType() throws RecognitionException {
		PrimitiveTypeContext _localctx = new PrimitiveTypeContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_primitiveType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			_la = _input.LA(1);
			if ( !(_la==T__21 || _la==T__15) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReferenceTypeContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(JavaliParser.Identifier, 0); }
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public ReferenceTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_referenceType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitReferenceType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReferenceTypeContext referenceType() throws RecognitionException {
		ReferenceTypeContext _localctx = new ReferenceTypeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_referenceType);
		try {
			setState(54);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(52); match(Identifier);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(53); arrayType();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArrayTypeContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(JavaliParser.Identifier, 0); }
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public ArrayTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayTypeContext arrayType() throws RecognitionException {
		ArrayTypeContext _localctx = new ArrayTypeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_arrayType);
		try {
			setState(63);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(56); match(Identifier);
				setState(57); match(T__11);
				setState(58); match(T__8);
				}
				break;
			case T__21:
			case T__15:
				enterOuterAlt(_localctx, 2);
				{
				setState(59); primitiveType();
				setState(60); match(T__11);
				setState(61); match(T__8);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnitContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(JavaliParser.EOF, 0); }
		public ClassDeclContext classDecl(int i) {
			return getRuleContext(ClassDeclContext.class,i);
		}
		public List<ClassDeclContext> classDecl() {
			return getRuleContexts(ClassDeclContext.class);
		}
		public UnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnitContext unit() throws RecognitionException {
		UnitContext _localctx = new UnitContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_unit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(65); classDecl();
				}
				}
				setState(68); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__33 );
			setState(70); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(JavaliParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(JavaliParser.Identifier, i);
		}
		public MemberListContext memberList() {
			return getRuleContext(MemberListContext.class,0);
		}
		public ClassDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitClassDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDeclContext classDecl() throws RecognitionException {
		ClassDeclContext _localctx = new ClassDeclContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_classDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72); match(T__33);
			setState(73); match(Identifier);
			setState(76);
			_la = _input.LA(1);
			if (_la==T__24) {
				{
				setState(74); match(T__24);
				setState(75); match(Identifier);
				}
			}

			setState(78); match(T__28);
			setState(79); memberList();
			setState(80); match(T__25);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MemberListContext extends ParserRuleContext {
		public List<MethodDeclContext> methodDecl() {
			return getRuleContexts(MethodDeclContext.class);
		}
		public VarDeclContext varDecl(int i) {
			return getRuleContext(VarDeclContext.class,i);
		}
		public List<VarDeclContext> varDecl() {
			return getRuleContexts(VarDeclContext.class);
		}
		public MethodDeclContext methodDecl(int i) {
			return getRuleContext(MethodDeclContext.class,i);
		}
		public MemberListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_memberList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitMemberList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MemberListContext memberList() throws RecognitionException {
		MemberListContext _localctx = new MemberListContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_memberList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__30) | (1L << T__21) | (1L << T__15) | (1L << Identifier))) != 0)) {
				{
				setState(84);
				switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
				case 1:
					{
					setState(82); varDecl();
					}
					break;
				case 2:
					{
					setState(83); methodDecl();
					}
					break;
				}
				}
				setState(88);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDeclContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(JavaliParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(JavaliParser.Identifier, i);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public VarDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitVarDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDeclContext varDecl() throws RecognitionException {
		VarDeclContext _localctx = new VarDeclContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_varDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89); type();
			setState(90); match(Identifier);
			setState(95);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(91); match(T__17);
				setState(92); match(Identifier);
				}
				}
				setState(97);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(98); match(T__29);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodDeclContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(JavaliParser.Identifier, 0); }
		public VarDeclContext varDecl(int i) {
			return getRuleContext(VarDeclContext.class,i);
		}
		public List<VarDeclContext> varDecl() {
			return getRuleContexts(VarDeclContext.class);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public FormalParamListContext formalParamList() {
			return getRuleContext(FormalParamListContext.class,0);
		}
		public MethodDeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodDecl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitMethodDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodDeclContext methodDecl() throws RecognitionException {
		MethodDeclContext _localctx = new MethodDeclContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_methodDecl);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			switch (_input.LA(1)) {
			case T__21:
			case T__15:
			case Identifier:
				{
				setState(100); type();
				}
				break;
			case T__30:
				{
				setState(101); match(T__30);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(104); match(Identifier);
			setState(105); match(T__20);
			setState(107);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__21) | (1L << T__15) | (1L << Identifier))) != 0)) {
				{
				setState(106); formalParamList();
				}
			}

			setState(109); match(T__2);
			setState(110); match(T__28);
			setState(114);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(111); varDecl();
					}
					} 
				}
				setState(116);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			setState(120);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__35) | (1L << T__31) | (1L << T__23) | (1L << T__18) | (1L << T__14) | (1L << T__12) | (1L << Identifier))) != 0)) {
				{
				{
				setState(117); statement();
				}
				}
				setState(122);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(123); match(T__25);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormalParamListContext extends ParserRuleContext {
		public List<TerminalNode> Identifier() { return getTokens(JavaliParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(JavaliParser.Identifier, i);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public FormalParamListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formalParamList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitFormalParamList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FormalParamListContext formalParamList() throws RecognitionException {
		FormalParamListContext _localctx = new FormalParamListContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_formalParamList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125); type();
			setState(126); match(Identifier);
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(127); match(T__17);
				setState(128); type();
				setState(129); match(Identifier);
				}
				}
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public AssignmentStatementContext assignmentStatement() {
			return getRuleContext(AssignmentStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public WriteStatementContext writeStatement() {
			return getRuleContext(WriteStatementContext.class,0);
		}
		public MethodCallStatementContext methodCallStatement() {
			return getRuleContext(MethodCallStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_statement);
		try {
			setState(142);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(136); assignmentStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(137); methodCallStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(138); ifStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(139); whileStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(140); returnStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(141); writeStatement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementBlockContext extends ParserRuleContext {
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementBlock; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitStatementBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementBlockContext statementBlock() throws RecognitionException {
		StatementBlockContext _localctx = new StatementBlockContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_statementBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144); match(T__28);
			setState(148);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__35) | (1L << T__31) | (1L << T__23) | (1L << T__18) | (1L << T__14) | (1L << T__12) | (1L << Identifier))) != 0)) {
				{
				{
				setState(145); statement();
				}
				}
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(151); match(T__25);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodCallStatementContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(JavaliParser.Identifier, 0); }
		public ActualParamListContext actualParamList() {
			return getRuleContext(ActualParamListContext.class,0);
		}
		public IdentAccessContext identAccess() {
			return getRuleContext(IdentAccessContext.class,0);
		}
		public MethodCallStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodCallStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitMethodCallStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodCallStatementContext methodCallStatement() throws RecognitionException {
		MethodCallStatementContext _localctx = new MethodCallStatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_methodCallStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				setState(153); identAccess(0);
				setState(154); match(T__16);
				}
				break;
			}
			setState(158); match(Identifier);
			setState(159); match(T__20);
			setState(161);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__20) | (1L << T__18) | (1L << T__6) | (1L << T__1) | (1L << T__0) | (1L << Identifier) | (1L << Literal))) != 0)) {
				{
				setState(160); actualParamList();
				}
			}

			setState(163); match(T__2);
			setState(164); match(T__29);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentStatementContext extends ParserRuleContext {
		public NewExpressionContext newExpression() {
			return getRuleContext(NewExpressionContext.class,0);
		}
		public ReadExpressionContext readExpression() {
			return getRuleContext(ReadExpressionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IdentAccessContext identAccess() {
			return getRuleContext(IdentAccessContext.class,0);
		}
		public AssignmentStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitAssignmentStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentStatementContext assignmentStatement() throws RecognitionException {
		AssignmentStatementContext _localctx = new AssignmentStatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_assignmentStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166); identAccess(0);
			setState(167); match(T__26);
			setState(171);
			switch (_input.LA(1)) {
			case T__20:
			case T__18:
			case T__6:
			case T__1:
			case T__0:
			case Identifier:
			case Literal:
				{
				setState(168); expression(0);
				}
				break;
			case T__36:
				{
				setState(169); newExpression();
				}
				break;
			case T__4:
				{
				setState(170); readExpression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(173); match(T__29);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WriteStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public WriteStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_writeStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitWriteStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WriteStatementContext writeStatement() throws RecognitionException {
		WriteStatementContext _localctx = new WriteStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_writeStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(183);
			switch (_input.LA(1)) {
			case T__14:
				{
				setState(175); match(T__14);
				setState(176); match(T__20);
				setState(177); expression(0);
				setState(178); match(T__2);
				}
				break;
			case T__12:
				{
				setState(180); match(T__12);
				setState(181); match(T__20);
				setState(182); match(T__2);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(185); match(T__29);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public StatementBlockContext statementBlock(int i) {
			return getRuleContext(StatementBlockContext.class,i);
		}
		public List<StatementBlockContext> statementBlock() {
			return getRuleContexts(StatementBlockContext.class);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187); match(T__23);
			setState(188); match(T__20);
			setState(189); expression(0);
			setState(190); match(T__2);
			setState(191); statementBlock();
			setState(194);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(192); match(T__3);
				setState(193); statementBlock();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStatementContext extends ParserRuleContext {
		public StatementBlockContext statementBlock() {
			return getRuleContext(StatementBlockContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196); match(T__31);
			setState(197); match(T__20);
			setState(198); expression(0);
			setState(199); match(T__2);
			setState(200); statementBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202); match(T__35);
			setState(204);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__20) | (1L << T__18) | (1L << T__6) | (1L << T__1) | (1L << T__0) | (1L << Identifier) | (1L << Literal))) != 0)) {
				{
				setState(203); expression(0);
				}
			}

			setState(206); match(T__29);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NewExpressionContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(JavaliParser.Identifier, 0); }
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NewExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitNewExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NewExpressionContext newExpression() throws RecognitionException {
		NewExpressionContext _localctx = new NewExpressionContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_newExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208); match(T__36);
			setState(222);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				setState(209); match(Identifier);
				setState(210); match(T__20);
				setState(211); match(T__2);
				}
				break;
			case 2:
				{
				setState(212); match(Identifier);
				setState(213); match(T__11);
				setState(214); expression(0);
				setState(215); match(T__8);
				}
				break;
			case 3:
				{
				setState(217); primitiveType();
				setState(218); match(T__11);
				setState(219); expression(0);
				setState(220); match(T__8);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReadExpressionContext extends ParserRuleContext {
		public ReadExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_readExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitReadExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReadExpressionContext readExpression() throws RecognitionException {
		ReadExpressionContext _localctx = new ReadExpressionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_readExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(224); match(T__4);
			setState(225); match(T__20);
			setState(226); match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActualParamListContext extends ParserRuleContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ActualParamListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actualParamList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitActualParamList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActualParamListContext actualParamList() throws RecognitionException {
		ActualParamListContext _localctx = new ActualParamListContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_actualParamList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228); expression(0);
			setState(233);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(229); match(T__17);
				setState(230); expression(0);
				}
				}
				setState(235);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentAccessContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(JavaliParser.Identifier, 0); }
		public ActualParamListContext actualParamList() {
			return getRuleContext(ActualParamListContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IdentAccessContext identAccess() {
			return getRuleContext(IdentAccessContext.class,0);
		}
		public IdentAccessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identAccess; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitIdentAccess(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentAccessContext identAccess() throws RecognitionException {
		return identAccess(0);
	}

	private IdentAccessContext identAccess(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		IdentAccessContext _localctx = new IdentAccessContext(_ctx, _parentState);
		IdentAccessContext _prevctx = _localctx;
		int _startState = 42;
		enterRecursionRule(_localctx, 42, RULE_identAccess, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(245);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				{
				setState(237); match(Identifier);
				}
				break;
			case 2:
				{
				setState(238); match(T__18);
				}
				break;
			case 3:
				{
				setState(239); match(Identifier);
				setState(240); match(T__20);
				setState(242);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__20) | (1L << T__18) | (1L << T__6) | (1L << T__1) | (1L << T__0) | (1L << Identifier) | (1L << Literal))) != 0)) {
					{
					setState(241); actualParamList();
					}
				}

				setState(244); match(T__2);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(265);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(263);
					switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
					case 1:
						{
						_localctx = new IdentAccessContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identAccess);
						setState(247);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(248); match(T__16);
						setState(249); match(Identifier);
						}
						break;
					case 2:
						{
						_localctx = new IdentAccessContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identAccess);
						setState(250);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(251); match(T__11);
						setState(252); expression(0);
						setState(253); match(T__8);
						}
						break;
					case 3:
						{
						_localctx = new IdentAccessContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_identAccess);
						setState(255);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(256); match(T__16);
						setState(257); match(Identifier);
						setState(258); match(T__20);
						setState(260);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__20) | (1L << T__18) | (1L << T__6) | (1L << T__1) | (1L << T__0) | (1L << Identifier) | (1L << Literal))) != 0)) {
							{
							setState(259); actualParamList();
							}
						}

						setState(262); match(T__2);
						}
						break;
					}
					} 
				}
				setState(267);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,27,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CASTContext extends ExpressionContext {
		public ReferenceTypeContext referenceType() {
			return getRuleContext(ReferenceTypeContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public CASTContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitCAST(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class COMPContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public COMPContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitCOMP(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ADDContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ADDContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitADD(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IDACCContext extends ExpressionContext {
		public IdentAccessContext identAccess() {
			return getRuleContext(IdentAccessContext.class,0);
		}
		public IDACCContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitIDACC(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MULTContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public MULTContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitMULT(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LITContext extends ExpressionContext {
		public TerminalNode Literal() { return getToken(JavaliParser.Literal, 0); }
		public LITContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitLIT(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PARSContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PARSContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitPARS(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LANDContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public LANDContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitLAND(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EQContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public EQContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitEQ(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UNARYContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public UNARYContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitUNARY(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class LORContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public LORContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitLOR(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 44;
		enterRecursionRule(_localctx, 44, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(282);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				{
				_localctx = new UNARYContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(269);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__1) | (1L << T__0))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				setState(270); expression(8);
				}
				break;
			case 2:
				{
				_localctx = new CASTContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(271); match(T__20);
				setState(272); referenceType();
				setState(273); match(T__2);
				setState(274); expression(7);
				}
				break;
			case 3:
				{
				_localctx = new LITContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(276); match(Literal);
				}
				break;
			case 4:
				{
				_localctx = new IDACCContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(277); identAccess(0);
				}
				break;
			case 5:
				{
				_localctx = new PARSContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(278); match(T__20);
				setState(279); expression(0);
				setState(280); match(T__2);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(304);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(302);
					switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
					case 1:
						{
						_localctx = new MULTContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(284);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(285);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__37) | (1L << T__19) | (1L << T__5))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						consume();
						setState(286); expression(7);
						}
						break;
					case 2:
						{
						_localctx = new ADDContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(287);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(288);
						_la = _input.LA(1);
						if ( !(_la==T__1 || _la==T__0) ) {
						_errHandler.recoverInline(this);
						}
						consume();
						setState(289); expression(6);
						}
						break;
					case 3:
						{
						_localctx = new COMPContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(290);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(291);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__22) | (1L << T__13) | (1L << T__9) | (1L << T__7))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						consume();
						setState(292); expression(5);
						}
						break;
					case 4:
						{
						_localctx = new EQContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(293);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(294);
						_la = _input.LA(1);
						if ( !(_la==T__34 || _la==T__10) ) {
						_errHandler.recoverInline(this);
						}
						consume();
						setState(295); expression(4);
						}
						break;
					case 5:
						{
						_localctx = new LANDContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(296);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(297); match(T__27);
						setState(298); expression(3);
						}
						break;
					case 6:
						{
						_localctx = new LORContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(299);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(300); match(T__32);
						setState(301); expression(2);
						}
						break;
					}
					} 
				}
				setState(306);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 21: return identAccess_sempred((IdentAccessContext)_localctx, predIndex);
		case 22: return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3: return precpred(_ctx, 6);
		case 4: return precpred(_ctx, 5);
		case 5: return precpred(_ctx, 4);
		case 6: return precpred(_ctx, 3);
		case 7: return precpred(_ctx, 2);
		case 8: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean identAccess_sempred(IdentAccessContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 4);
		case 1: return precpred(_ctx, 3);
		case 2: return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3-\u0136\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2\3\2\5"+
		"\2\63\n\2\3\3\3\3\3\4\3\4\5\49\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5B\n"+
		"\5\3\6\6\6E\n\6\r\6\16\6F\3\6\3\6\3\7\3\7\3\7\3\7\5\7O\n\7\3\7\3\7\3\7"+
		"\3\7\3\b\3\b\7\bW\n\b\f\b\16\bZ\13\b\3\t\3\t\3\t\3\t\7\t`\n\t\f\t\16\t"+
		"c\13\t\3\t\3\t\3\n\3\n\5\ni\n\n\3\n\3\n\3\n\5\nn\n\n\3\n\3\n\3\n\7\ns"+
		"\n\n\f\n\16\nv\13\n\3\n\7\ny\n\n\f\n\16\n|\13\n\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\3\13\3\13\7\13\u0086\n\13\f\13\16\13\u0089\13\13\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\5\f\u0091\n\f\3\r\3\r\7\r\u0095\n\r\f\r\16\r\u0098\13\r\3\r\3"+
		"\r\3\16\3\16\3\16\5\16\u009f\n\16\3\16\3\16\3\16\5\16\u00a4\n\16\3\16"+
		"\3\16\3\16\3\17\3\17\3\17\3\17\3\17\5\17\u00ae\n\17\3\17\3\17\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u00ba\n\20\3\20\3\20\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\5\21\u00c5\n\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23"+
		"\3\23\5\23\u00cf\n\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u00e1\n\24\3\25\3\25\3\25\3\25\3\26"+
		"\3\26\3\26\7\26\u00ea\n\26\f\26\16\26\u00ed\13\26\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\5\27\u00f5\n\27\3\27\5\27\u00f8\n\27\3\27\3\27\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u0107\n\27\3\27\7\27"+
		"\u010a\n\27\f\27\16\27\u010d\13\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u011d\n\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\7\30\u0131\n\30\f\30\16\30\u0134\13\30\3\30\2\4,.\31\2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\34\36 \"$&(*,.\2\b\4\2\23\23\31\31\4\2\"\"\'(\5\2\3\3"+
		"\25\25##\3\2\'(\6\2\22\22\33\33\37\37!!\4\2\6\6\36\36\u014c\2\62\3\2\2"+
		"\2\4\64\3\2\2\2\68\3\2\2\2\bA\3\2\2\2\nD\3\2\2\2\fJ\3\2\2\2\16X\3\2\2"+
		"\2\20[\3\2\2\2\22h\3\2\2\2\24\177\3\2\2\2\26\u0090\3\2\2\2\30\u0092\3"+
		"\2\2\2\32\u009e\3\2\2\2\34\u00a8\3\2\2\2\36\u00b9\3\2\2\2 \u00bd\3\2\2"+
		"\2\"\u00c6\3\2\2\2$\u00cc\3\2\2\2&\u00d2\3\2\2\2(\u00e2\3\2\2\2*\u00e6"+
		"\3\2\2\2,\u00f7\3\2\2\2.\u011c\3\2\2\2\60\63\5\4\3\2\61\63\5\6\4\2\62"+
		"\60\3\2\2\2\62\61\3\2\2\2\63\3\3\2\2\2\64\65\t\2\2\2\65\5\3\2\2\2\669"+
		"\7)\2\2\679\5\b\5\28\66\3\2\2\28\67\3\2\2\29\7\3\2\2\2:;\7)\2\2;<\7\35"+
		"\2\2<B\7 \2\2=>\5\4\3\2>?\7\35\2\2?@\7 \2\2@B\3\2\2\2A:\3\2\2\2A=\3\2"+
		"\2\2B\t\3\2\2\2CE\5\f\7\2DC\3\2\2\2EF\3\2\2\2FD\3\2\2\2FG\3\2\2\2GH\3"+
		"\2\2\2HI\7\2\2\3I\13\3\2\2\2JK\7\7\2\2KN\7)\2\2LM\7\20\2\2MO\7)\2\2NL"+
		"\3\2\2\2NO\3\2\2\2OP\3\2\2\2PQ\7\f\2\2QR\5\16\b\2RS\7\17\2\2S\r\3\2\2"+
		"\2TW\5\20\t\2UW\5\22\n\2VT\3\2\2\2VU\3\2\2\2WZ\3\2\2\2XV\3\2\2\2XY\3\2"+
		"\2\2Y\17\3\2\2\2ZX\3\2\2\2[\\\5\2\2\2\\a\7)\2\2]^\7\27\2\2^`\7)\2\2_]"+
		"\3\2\2\2`c\3\2\2\2a_\3\2\2\2ab\3\2\2\2bd\3\2\2\2ca\3\2\2\2de\7\13\2\2"+
		"e\21\3\2\2\2fi\5\2\2\2gi\7\n\2\2hf\3\2\2\2hg\3\2\2\2ij\3\2\2\2jk\7)\2"+
		"\2km\7\24\2\2ln\5\24\13\2ml\3\2\2\2mn\3\2\2\2no\3\2\2\2op\7&\2\2pt\7\f"+
		"\2\2qs\5\20\t\2rq\3\2\2\2sv\3\2\2\2tr\3\2\2\2tu\3\2\2\2uz\3\2\2\2vt\3"+
		"\2\2\2wy\5\26\f\2xw\3\2\2\2y|\3\2\2\2zx\3\2\2\2z{\3\2\2\2{}\3\2\2\2|z"+
		"\3\2\2\2}~\7\17\2\2~\23\3\2\2\2\177\u0080\5\2\2\2\u0080\u0087\7)\2\2\u0081"+
		"\u0082\7\27\2\2\u0082\u0083\5\2\2\2\u0083\u0084\7)\2\2\u0084\u0086\3\2"+
		"\2\2\u0085\u0081\3\2\2\2\u0086\u0089\3\2\2\2\u0087\u0085\3\2\2\2\u0087"+
		"\u0088\3\2\2\2\u0088\25\3\2\2\2\u0089\u0087\3\2\2\2\u008a\u0091\5\34\17"+
		"\2\u008b\u0091\5\32\16\2\u008c\u0091\5 \21\2\u008d\u0091\5\"\22\2\u008e"+
		"\u0091\5$\23\2\u008f\u0091\5\36\20\2\u0090\u008a\3\2\2\2\u0090\u008b\3"+
		"\2\2\2\u0090\u008c\3\2\2\2\u0090\u008d\3\2\2\2\u0090\u008e\3\2\2\2\u0090"+
		"\u008f\3\2\2\2\u0091\27\3\2\2\2\u0092\u0096\7\f\2\2\u0093\u0095\5\26\f"+
		"\2\u0094\u0093\3\2\2\2\u0095\u0098\3\2\2\2\u0096\u0094\3\2\2\2\u0096\u0097"+
		"\3\2\2\2\u0097\u0099\3\2\2\2\u0098\u0096\3\2\2\2\u0099\u009a\7\17\2\2"+
		"\u009a\31\3\2\2\2\u009b\u009c\5,\27\2\u009c\u009d\7\30\2\2\u009d\u009f"+
		"\3\2\2\2\u009e\u009b\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0"+
		"\u00a1\7)\2\2\u00a1\u00a3\7\24\2\2\u00a2\u00a4\5*\26\2\u00a3\u00a2\3\2"+
		"\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a6\7&\2\2\u00a6"+
		"\u00a7\7\13\2\2\u00a7\33\3\2\2\2\u00a8\u00a9\5,\27\2\u00a9\u00ad\7\16"+
		"\2\2\u00aa\u00ae\5.\30\2\u00ab\u00ae\5&\24\2\u00ac\u00ae\5(\25\2\u00ad"+
		"\u00aa\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ac\3\2\2\2\u00ae\u00af\3\2"+
		"\2\2\u00af\u00b0\7\13\2\2\u00b0\35\3\2\2\2\u00b1\u00b2\7\32\2\2\u00b2"+
		"\u00b3\7\24\2\2\u00b3\u00b4\5.\30\2\u00b4\u00b5\7&\2\2\u00b5\u00ba\3\2"+
		"\2\2\u00b6\u00b7\7\34\2\2\u00b7\u00b8\7\24\2\2\u00b8\u00ba\7&\2\2\u00b9"+
		"\u00b1\3\2\2\2\u00b9\u00b6\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00bc\7\13"+
		"\2\2\u00bc\37\3\2\2\2\u00bd\u00be\7\21\2\2\u00be\u00bf\7\24\2\2\u00bf"+
		"\u00c0\5.\30\2\u00c0\u00c1\7&\2\2\u00c1\u00c4\5\30\r\2\u00c2\u00c3\7%"+
		"\2\2\u00c3\u00c5\5\30\r\2\u00c4\u00c2\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5"+
		"!\3\2\2\2\u00c6\u00c7\7\t\2\2\u00c7\u00c8\7\24\2\2\u00c8\u00c9\5.\30\2"+
		"\u00c9\u00ca\7&\2\2\u00ca\u00cb\5\30\r\2\u00cb#\3\2\2\2\u00cc\u00ce\7"+
		"\5\2\2\u00cd\u00cf\5.\30\2\u00ce\u00cd\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf"+
		"\u00d0\3\2\2\2\u00d0\u00d1\7\13\2\2\u00d1%\3\2\2\2\u00d2\u00e0\7\4\2\2"+
		"\u00d3\u00d4\7)\2\2\u00d4\u00d5\7\24\2\2\u00d5\u00e1\7&\2\2\u00d6\u00d7"+
		"\7)\2\2\u00d7\u00d8\7\35\2\2\u00d8\u00d9\5.\30\2\u00d9\u00da\7 \2\2\u00da"+
		"\u00e1\3\2\2\2\u00db\u00dc\5\4\3\2\u00dc\u00dd\7\35\2\2\u00dd\u00de\5"+
		".\30\2\u00de\u00df\7 \2\2\u00df\u00e1\3\2\2\2\u00e0\u00d3\3\2\2\2\u00e0"+
		"\u00d6\3\2\2\2\u00e0\u00db\3\2\2\2\u00e1\'\3\2\2\2\u00e2\u00e3\7$\2\2"+
		"\u00e3\u00e4\7\24\2\2\u00e4\u00e5\7&\2\2\u00e5)\3\2\2\2\u00e6\u00eb\5"+
		".\30\2\u00e7\u00e8\7\27\2\2\u00e8\u00ea\5.\30\2\u00e9\u00e7\3\2\2\2\u00ea"+
		"\u00ed\3\2\2\2\u00eb\u00e9\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec+\3\2\2\2"+
		"\u00ed\u00eb\3\2\2\2\u00ee\u00ef\b\27\1\2\u00ef\u00f8\7)\2\2\u00f0\u00f8"+
		"\7\26\2\2\u00f1\u00f2\7)\2\2\u00f2\u00f4\7\24\2\2\u00f3\u00f5\5*\26\2"+
		"\u00f4\u00f3\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f6\u00f8"+
		"\7&\2\2\u00f7\u00ee\3\2\2\2\u00f7\u00f0\3\2\2\2\u00f7\u00f1\3\2\2\2\u00f8"+
		"\u010b\3\2\2\2\u00f9\u00fa\f\6\2\2\u00fa\u00fb\7\30\2\2\u00fb\u010a\7"+
		")\2\2\u00fc\u00fd\f\5\2\2\u00fd\u00fe\7\35\2\2\u00fe\u00ff\5.\30\2\u00ff"+
		"\u0100\7 \2\2\u0100\u010a\3\2\2\2\u0101\u0102\f\3\2\2\u0102\u0103\7\30"+
		"\2\2\u0103\u0104\7)\2\2\u0104\u0106\7\24\2\2\u0105\u0107\5*\26\2\u0106"+
		"\u0105\3\2\2\2\u0106\u0107\3\2\2\2\u0107\u0108\3\2\2\2\u0108\u010a\7&"+
		"\2\2\u0109\u00f9\3\2\2\2\u0109\u00fc\3\2\2\2\u0109\u0101\3\2\2\2\u010a"+
		"\u010d\3\2\2\2\u010b\u0109\3\2\2\2\u010b\u010c\3\2\2\2\u010c-\3\2\2\2"+
		"\u010d\u010b\3\2\2\2\u010e\u010f\b\30\1\2\u010f\u0110\t\3\2\2\u0110\u011d"+
		"\5.\30\n\u0111\u0112\7\24\2\2\u0112\u0113\5\6\4\2\u0113\u0114\7&\2\2\u0114"+
		"\u0115\5.\30\t\u0115\u011d\3\2\2\2\u0116\u011d\7*\2\2\u0117\u011d\5,\27"+
		"\2\u0118\u0119\7\24\2\2\u0119\u011a\5.\30\2\u011a\u011b\7&\2\2\u011b\u011d"+
		"\3\2\2\2\u011c\u010e\3\2\2\2\u011c\u0111\3\2\2\2\u011c\u0116\3\2\2\2\u011c"+
		"\u0117\3\2\2\2\u011c\u0118\3\2\2\2\u011d\u0132\3\2\2\2\u011e\u011f\f\b"+
		"\2\2\u011f\u0120\t\4\2\2\u0120\u0131\5.\30\t\u0121\u0122\f\7\2\2\u0122"+
		"\u0123\t\5\2\2\u0123\u0131\5.\30\b\u0124\u0125\f\6\2\2\u0125\u0126\t\6"+
		"\2\2\u0126\u0131\5.\30\7\u0127\u0128\f\5\2\2\u0128\u0129\t\7\2\2\u0129"+
		"\u0131\5.\30\6\u012a\u012b\f\4\2\2\u012b\u012c\7\r\2\2\u012c\u0131\5."+
		"\30\5\u012d\u012e\f\3\2\2\u012e\u012f\7\b\2\2\u012f\u0131\5.\30\4\u0130"+
		"\u011e\3\2\2\2\u0130\u0121\3\2\2\2\u0130\u0124\3\2\2\2\u0130\u0127\3\2"+
		"\2\2\u0130\u012a\3\2\2\2\u0130\u012d\3\2\2\2\u0131\u0134\3\2\2\2\u0132"+
		"\u0130\3\2\2\2\u0132\u0133\3\2\2\2\u0133/\3\2\2\2\u0134\u0132\3\2\2\2"+
		"!\628AFNVXahmtz\u0087\u0090\u0096\u009e\u00a3\u00ad\u00b9\u00c4\u00ce"+
		"\u00e0\u00eb\u00f4\u00f7\u0106\u0109\u010b\u011c\u0130\u0132";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}