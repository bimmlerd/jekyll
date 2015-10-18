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
		T__6=32, T__5=33, T__4=34, T__3=35, T__2=36, T__1=37, T__0=38, Literal=39, 
		Identifier=40, COMMENT=41, LINE_COMMENT=42, WS=43;
	public static final String[] tokenNames = {
		"<INVALID>", "'/'", "'new'", "'return'", "'!='", "'class'", "'||'", "'while'", 
		"';'", "'{'", "'void'", "'&&'", "'='", "'}'", "'extends'", "'if'", "'<='", 
		"'int'", "'('", "'*'", "'this'", "','", "'.'", "'boolean'", "'write'", 
		"'>='", "'writeln'", "'['", "'=='", "'<'", "']'", "'>'", "'!'", "'%'", 
		"'read'", "'else'", "')'", "'+'", "'-'", "Literal", "Identifier", "COMMENT", 
		"LINE_COMMENT", "WS"
	};
	public static final int
		RULE_type = 0, RULE_methodType = 1, RULE_primitiveType = 2, RULE_referenceType = 3, 
		RULE_arrayType = 4, RULE_unit = 5, RULE_classDecl = 6, RULE_superClass = 7, 
		RULE_memberList = 8, RULE_varDecl = 9, RULE_methodDecl = 10, RULE_formalParamList = 11, 
		RULE_statement = 12, RULE_statementBlock = 13, RULE_assignmentStatement = 14, 
		RULE_methodCallStatement = 15, RULE_ifStatement = 16, RULE_whileStatement = 17, 
		RULE_returnStatement = 18, RULE_writeStatement = 19, RULE_newExpression = 20, 
		RULE_readExpression = 21, RULE_actualParamList = 22, RULE_identAccess = 23, 
		RULE_expression = 24;
	public static final String[] ruleNames = {
		"type", "methodType", "primitiveType", "referenceType", "arrayType", "unit", 
		"classDecl", "superClass", "memberList", "varDecl", "methodDecl", "formalParamList", 
		"statement", "statementBlock", "assignmentStatement", "methodCallStatement", 
		"ifStatement", "whileStatement", "returnStatement", "writeStatement", 
		"newExpression", "readExpression", "actualParamList", "identAccess", "expression"
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
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TypeReferenceContext extends TypeContext {
		public ReferenceTypeContext referenceType() {
			return getRuleContext(ReferenceTypeContext.class,0);
		}
		public TypeReferenceContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitTypeReference(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TypePrimitiveContext extends TypeContext {
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public TypePrimitiveContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitTypePrimitive(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_type);
		try {
			setState(52);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				_localctx = new TypePrimitiveContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(50); primitiveType();
				}
				break;
			case 2:
				_localctx = new TypeReferenceContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(51); referenceType();
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

	public static class MethodTypeContext extends ParserRuleContext {
		public MethodTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methodType; }
	 
		public MethodTypeContext() { }
		public void copyFrom(MethodTypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MethodTypeTypeContext extends MethodTypeContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public MethodTypeTypeContext(MethodTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitMethodTypeType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MethodTypeVoidContext extends MethodTypeContext {
		public MethodTypeVoidContext(MethodTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitMethodTypeVoid(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodTypeContext methodType() throws RecognitionException {
		MethodTypeContext _localctx = new MethodTypeContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_methodType);
		try {
			setState(56);
			switch (_input.LA(1)) {
			case T__21:
			case T__15:
			case Identifier:
				_localctx = new MethodTypeTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(54); type();
				}
				break;
			case T__28:
				_localctx = new MethodTypeVoidContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(55); match(T__28);
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
		enterRule(_localctx, 4, RULE_primitiveType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
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
		public ReferenceTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_referenceType; }
	 
		public ReferenceTypeContext() { }
		public void copyFrom(ReferenceTypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ReferenceTypeArContext extends ReferenceTypeContext {
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
		public ReferenceTypeArContext(ReferenceTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitReferenceTypeAr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReferenceTypeIdContext extends ReferenceTypeContext {
		public TerminalNode Identifier() { return getToken(JavaliParser.Identifier, 0); }
		public ReferenceTypeIdContext(ReferenceTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitReferenceTypeId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReferenceTypeContext referenceType() throws RecognitionException {
		ReferenceTypeContext _localctx = new ReferenceTypeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_referenceType);
		try {
			setState(62);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				_localctx = new ReferenceTypeIdContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(60); match(Identifier);
				}
				break;
			case 2:
				_localctx = new ReferenceTypeArContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(61); arrayType();
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
		public ArrayTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayType; }
	 
		public ArrayTypeContext() { }
		public void copyFrom(ArrayTypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ArrayTypeIdContext extends ArrayTypeContext {
		public TerminalNode Identifier() { return getToken(JavaliParser.Identifier, 0); }
		public ArrayTypeIdContext(ArrayTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitArrayTypeId(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ArrayTypePrContext extends ArrayTypeContext {
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public ArrayTypePrContext(ArrayTypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitArrayTypePr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayTypeContext arrayType() throws RecognitionException {
		ArrayTypeContext _localctx = new ArrayTypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_arrayType);
		try {
			setState(71);
			switch (_input.LA(1)) {
			case Identifier:
				_localctx = new ArrayTypeIdContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(64); match(Identifier);
				setState(65); match(T__11);
				setState(66); match(T__8);
				}
				break;
			case T__21:
			case T__15:
				_localctx = new ArrayTypePrContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(67); primitiveType();
				setState(68); match(T__11);
				setState(69); match(T__8);
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
		enterRule(_localctx, 10, RULE_unit);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(73); classDecl();
				}
				}
				setState(76); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__33 );
			setState(78); match(EOF);
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
		public TerminalNode Identifier() { return getToken(JavaliParser.Identifier, 0); }
		public SuperClassContext superClass() {
			return getRuleContext(SuperClassContext.class,0);
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
		enterRule(_localctx, 12, RULE_classDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80); match(T__33);
			setState(81); match(Identifier);
			setState(84);
			_la = _input.LA(1);
			if (_la==T__24) {
				{
				setState(82); match(T__24);
				setState(83); superClass();
				}
			}

			setState(86); match(T__29);
			setState(87); memberList();
			setState(88); match(T__25);
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

	public static class SuperClassContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(JavaliParser.Identifier, 0); }
		public SuperClassContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_superClass; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitSuperClass(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SuperClassContext superClass() throws RecognitionException {
		SuperClassContext _localctx = new SuperClassContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_superClass);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90); match(Identifier);
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
		enterRule(_localctx, 16, RULE_memberList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__28) | (1L << T__21) | (1L << T__15) | (1L << Identifier))) != 0)) {
				{
				setState(94);
				switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
				case 1:
					{
					setState(92); varDecl();
					}
					break;
				case 2:
					{
					setState(93); methodDecl();
					}
					break;
				}
				}
				setState(98);
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
		enterRule(_localctx, 18, RULE_varDecl);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99); type();
			setState(100); match(Identifier);
			setState(105);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(101); match(T__17);
				setState(102); match(Identifier);
				}
				}
				setState(107);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(108); match(T__30);
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
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public MethodTypeContext methodType() {
			return getRuleContext(MethodTypeContext.class,0);
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
		enterRule(_localctx, 20, RULE_methodDecl);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(110); methodType();
			setState(111); match(Identifier);
			setState(112); match(T__20);
			setState(114);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__21) | (1L << T__15) | (1L << Identifier))) != 0)) {
				{
				setState(113); formalParamList();
				}
			}

			setState(116); match(T__2);
			setState(117); match(T__29);
			setState(121);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(118); varDecl();
					}
					} 
				}
				setState(123);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			}
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__35) | (1L << T__31) | (1L << T__23) | (1L << T__18) | (1L << T__14) | (1L << T__12) | (1L << Identifier))) != 0)) {
				{
				{
				setState(124); statement();
				}
				}
				setState(129);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(130); match(T__25);
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
		enterRule(_localctx, 22, RULE_formalParamList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132); type();
			setState(133); match(Identifier);
			setState(140);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(134); match(T__17);
				setState(135); type();
				setState(136); match(Identifier);
				}
				}
				setState(142);
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
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	 
		public StatementContext() { }
		public void copyFrom(StatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class StmtMethodCallContext extends StatementContext {
		public MethodCallStatementContext methodCallStatement() {
			return getRuleContext(MethodCallStatementContext.class,0);
		}
		public StmtMethodCallContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitStmtMethodCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtWhileContext extends StatementContext {
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public StmtWhileContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitStmtWhile(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtIfContext extends StatementContext {
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public StmtIfContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitStmtIf(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtAssignmentContext extends StatementContext {
		public AssignmentStatementContext assignmentStatement() {
			return getRuleContext(AssignmentStatementContext.class,0);
		}
		public StmtAssignmentContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitStmtAssignment(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtWriteContext extends StatementContext {
		public WriteStatementContext writeStatement() {
			return getRuleContext(WriteStatementContext.class,0);
		}
		public StmtWriteContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitStmtWrite(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StmtReturnContext extends StatementContext {
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public StmtReturnContext(StatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitStmtReturn(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_statement);
		try {
			setState(149);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				_localctx = new StmtAssignmentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(143); assignmentStatement();
				}
				break;
			case 2:
				_localctx = new StmtMethodCallContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(144); methodCallStatement();
				}
				break;
			case 3:
				_localctx = new StmtIfContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(145); ifStatement();
				}
				break;
			case 4:
				_localctx = new StmtWhileContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(146); whileStatement();
				}
				break;
			case 5:
				_localctx = new StmtReturnContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(147); returnStatement();
				}
				break;
			case 6:
				_localctx = new StmtWriteContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(148); writeStatement();
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
		enterRule(_localctx, 26, RULE_statementBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151); match(T__29);
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__35) | (1L << T__31) | (1L << T__23) | (1L << T__18) | (1L << T__14) | (1L << T__12) | (1L << Identifier))) != 0)) {
				{
				{
				setState(152); statement();
				}
				}
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(158); match(T__25);
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
		public AssignmentStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignmentStatement; }
	 
		public AssignmentStatementContext() { }
		public void copyFrom(AssignmentStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AssignmentStmtExprContext extends AssignmentStatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IdentAccessContext identAccess() {
			return getRuleContext(IdentAccessContext.class,0);
		}
		public AssignmentStmtExprContext(AssignmentStatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitAssignmentStmtExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignmentStmtNewContext extends AssignmentStatementContext {
		public NewExpressionContext newExpression() {
			return getRuleContext(NewExpressionContext.class,0);
		}
		public IdentAccessContext identAccess() {
			return getRuleContext(IdentAccessContext.class,0);
		}
		public AssignmentStmtNewContext(AssignmentStatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitAssignmentStmtNew(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AssignmentStmtReadContext extends AssignmentStatementContext {
		public ReadExpressionContext readExpression() {
			return getRuleContext(ReadExpressionContext.class,0);
		}
		public IdentAccessContext identAccess() {
			return getRuleContext(IdentAccessContext.class,0);
		}
		public AssignmentStmtReadContext(AssignmentStatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitAssignmentStmtRead(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentStatementContext assignmentStatement() throws RecognitionException {
		AssignmentStatementContext _localctx = new AssignmentStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_assignmentStatement);
		try {
			setState(175);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new AssignmentStmtExprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(160); identAccess(0);
				setState(161); match(T__26);
				setState(162); expression(0);
				setState(163); match(T__30);
				}
				break;
			case 2:
				_localctx = new AssignmentStmtNewContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(165); identAccess(0);
				setState(166); match(T__26);
				setState(167); newExpression();
				setState(168); match(T__30);
				}
				break;
			case 3:
				_localctx = new AssignmentStmtReadContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(170); identAccess(0);
				setState(171); match(T__26);
				setState(172); readExpression();
				setState(173); match(T__30);
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
		enterRule(_localctx, 30, RULE_methodCallStatement);
		int _la;
		try {
			setState(194);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(177); match(Identifier);
				setState(178); match(T__20);
				setState(180);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__20) | (1L << T__18) | (1L << T__6) | (1L << T__1) | (1L << T__0) | (1L << Literal) | (1L << Identifier))) != 0)) {
					{
					setState(179); actualParamList();
					}
				}

				setState(182); match(T__2);
				setState(183); match(T__30);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(184); identAccess(0);
				setState(185); match(T__16);
				setState(186); match(Identifier);
				setState(187); match(T__20);
				setState(189);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__20) | (1L << T__18) | (1L << T__6) | (1L << T__1) | (1L << T__0) | (1L << Literal) | (1L << Identifier))) != 0)) {
					{
					setState(188); actualParamList();
					}
				}

				setState(191); match(T__2);
				setState(192); match(T__30);
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
		enterRule(_localctx, 32, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196); match(T__23);
			setState(197); match(T__20);
			setState(198); expression(0);
			setState(199); match(T__2);
			setState(200); statementBlock();
			setState(203);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(201); match(T__3);
				setState(202); statementBlock();
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
		enterRule(_localctx, 34, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205); match(T__31);
			setState(206); match(T__20);
			setState(207); expression(0);
			setState(208); match(T__2);
			setState(209); statementBlock();
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
		enterRule(_localctx, 36, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211); match(T__35);
			setState(213);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__20) | (1L << T__18) | (1L << T__6) | (1L << T__1) | (1L << T__0) | (1L << Literal) | (1L << Identifier))) != 0)) {
				{
				setState(212); expression(0);
				}
			}

			setState(215); match(T__30);
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
		public WriteStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_writeStatement; }
	 
		public WriteStatementContext() { }
		public void copyFrom(WriteStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class WriteLnStmtContext extends WriteStatementContext {
		public WriteLnStmtContext(WriteStatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitWriteLnStmt(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class WriteStmtContext extends WriteStatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public WriteStmtContext(WriteStatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitWriteStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WriteStatementContext writeStatement() throws RecognitionException {
		WriteStatementContext _localctx = new WriteStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_writeStatement);
		try {
			setState(227);
			switch (_input.LA(1)) {
			case T__14:
				_localctx = new WriteStmtContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(217); match(T__14);
				setState(218); match(T__20);
				setState(219); expression(0);
				setState(220); match(T__2);
				setState(221); match(T__30);
				}
				break;
			case T__12:
				_localctx = new WriteLnStmtContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(223); match(T__12);
				setState(224); match(T__20);
				setState(225); match(T__2);
				setState(226); match(T__30);
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

	public static class NewExpressionContext extends ParserRuleContext {
		public NewExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_newExpression; }
	 
		public NewExpressionContext() { }
		public void copyFrom(NewExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class NewIdentifierContext extends NewExpressionContext {
		public TerminalNode Identifier() { return getToken(JavaliParser.Identifier, 0); }
		public NewIdentifierContext(NewExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitNewIdentifier(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NewArrayPrContext extends NewExpressionContext {
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NewArrayPrContext(NewExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitNewArrayPr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NewArrayIdContext extends NewExpressionContext {
		public TerminalNode Identifier() { return getToken(JavaliParser.Identifier, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NewArrayIdContext(NewExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitNewArrayId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NewExpressionContext newExpression() throws RecognitionException {
		NewExpressionContext _localctx = new NewExpressionContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_newExpression);
		try {
			setState(245);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				_localctx = new NewIdentifierContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(229); match(T__36);
				setState(230); match(Identifier);
				setState(231); match(T__20);
				setState(232); match(T__2);
				}
				break;
			case 2:
				_localctx = new NewArrayIdContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(233); match(T__36);
				setState(234); match(Identifier);
				setState(235); match(T__11);
				setState(236); expression(0);
				setState(237); match(T__8);
				}
				break;
			case 3:
				_localctx = new NewArrayPrContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(239); match(T__36);
				setState(240); primitiveType();
				setState(241); match(T__11);
				setState(242); expression(0);
				setState(243); match(T__8);
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
		enterRule(_localctx, 42, RULE_readExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247); match(T__4);
			setState(248); match(T__20);
			setState(249); match(T__2);
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
		enterRule(_localctx, 44, RULE_actualParamList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(251); expression(0);
			setState(256);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(252); match(T__17);
				setState(253); expression(0);
				}
				}
				setState(258);
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
		public IdentAccessContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identAccess; }
	 
		public IdentAccessContext() { }
		public void copyFrom(IdentAccessContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IdentAccessMethodContext extends IdentAccessContext {
		public TerminalNode Identifier() { return getToken(JavaliParser.Identifier, 0); }
		public ActualParamListContext actualParamList() {
			return getRuleContext(ActualParamListContext.class,0);
		}
		public IdentAccessMethodContext(IdentAccessContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitIdentAccessMethod(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdentAccessFieldMethodContext extends IdentAccessContext {
		public TerminalNode Identifier() { return getToken(JavaliParser.Identifier, 0); }
		public ActualParamListContext actualParamList() {
			return getRuleContext(ActualParamListContext.class,0);
		}
		public IdentAccessContext identAccess() {
			return getRuleContext(IdentAccessContext.class,0);
		}
		public IdentAccessFieldMethodContext(IdentAccessContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitIdentAccessFieldMethod(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdentAccessIdContext extends IdentAccessContext {
		public TerminalNode Identifier() { return getToken(JavaliParser.Identifier, 0); }
		public IdentAccessIdContext(IdentAccessContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitIdentAccessId(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdentAccessFieldContext extends IdentAccessContext {
		public TerminalNode Identifier() { return getToken(JavaliParser.Identifier, 0); }
		public IdentAccessContext identAccess() {
			return getRuleContext(IdentAccessContext.class,0);
		}
		public IdentAccessFieldContext(IdentAccessContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitIdentAccessField(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdentAccessThisContext extends IdentAccessContext {
		public IdentAccessThisContext(IdentAccessContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitIdentAccessThis(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdentAccessArrayContext extends IdentAccessContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IdentAccessContext identAccess() {
			return getRuleContext(IdentAccessContext.class,0);
		}
		public IdentAccessArrayContext(IdentAccessContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JavaliVisitor ) return ((JavaliVisitor<? extends T>)visitor).visitIdentAccessArray(this);
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
		int _startState = 46;
		enterRecursionRule(_localctx, 46, RULE_identAccess, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(268);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				_localctx = new IdentAccessIdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(260); match(Identifier);
				}
				break;
			case 2:
				{
				_localctx = new IdentAccessThisContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(261); match(T__18);
				}
				break;
			case 3:
				{
				_localctx = new IdentAccessMethodContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(262); match(Identifier);
				setState(263); match(T__20);
				setState(265);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__20) | (1L << T__18) | (1L << T__6) | (1L << T__1) | (1L << T__0) | (1L << Literal) | (1L << Identifier))) != 0)) {
					{
					setState(264); actualParamList();
					}
				}

				setState(267); match(T__2);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(288);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(286);
					switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
					case 1:
						{
						_localctx = new IdentAccessFieldContext(new IdentAccessContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_identAccess);
						setState(270);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(271); match(T__16);
						setState(272); match(Identifier);
						}
						break;
					case 2:
						{
						_localctx = new IdentAccessArrayContext(new IdentAccessContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_identAccess);
						setState(273);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(274); match(T__11);
						setState(275); expression(0);
						setState(276); match(T__8);
						}
						break;
					case 3:
						{
						_localctx = new IdentAccessFieldMethodContext(new IdentAccessContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_identAccess);
						setState(278);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(279); match(T__16);
						setState(280); match(Identifier);
						setState(281); match(T__20);
						setState(283);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__20) | (1L << T__18) | (1L << T__6) | (1L << T__1) | (1L << T__0) | (1L << Literal) | (1L << Identifier))) != 0)) {
							{
							setState(282); actualParamList();
							}
						}

						setState(285); match(T__2);
						}
						break;
					}
					} 
				}
				setState(290);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
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
		int _startState = 48;
		enterRecursionRule(_localctx, 48, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(305);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				{
				_localctx = new UNARYContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(292);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__6) | (1L << T__1) | (1L << T__0))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				setState(293); expression(8);
				}
				break;
			case 2:
				{
				_localctx = new CASTContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(294); match(T__20);
				setState(295); referenceType();
				setState(296); match(T__2);
				setState(297); expression(7);
				}
				break;
			case 3:
				{
				_localctx = new LITContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(299); match(Literal);
				}
				break;
			case 4:
				{
				_localctx = new IDACCContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(300); identAccess(0);
				}
				break;
			case 5:
				{
				_localctx = new PARSContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(301); match(T__20);
				setState(302); expression(0);
				setState(303); match(T__2);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(327);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(325);
					switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
					case 1:
						{
						_localctx = new MULTContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(307);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(308);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__37) | (1L << T__19) | (1L << T__5))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						consume();
						setState(309); expression(7);
						}
						break;
					case 2:
						{
						_localctx = new ADDContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(310);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(311);
						_la = _input.LA(1);
						if ( !(_la==T__1 || _la==T__0) ) {
						_errHandler.recoverInline(this);
						}
						consume();
						setState(312); expression(6);
						}
						break;
					case 3:
						{
						_localctx = new COMPContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(313);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(314);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__22) | (1L << T__13) | (1L << T__9) | (1L << T__7))) != 0)) ) {
						_errHandler.recoverInline(this);
						}
						consume();
						setState(315); expression(5);
						}
						break;
					case 4:
						{
						_localctx = new EQContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(316);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(317);
						_la = _input.LA(1);
						if ( !(_la==T__34 || _la==T__10) ) {
						_errHandler.recoverInline(this);
						}
						consume();
						setState(318); expression(4);
						}
						break;
					case 5:
						{
						_localctx = new LANDContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(319);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(320); match(T__27);
						setState(321); expression(3);
						}
						break;
					case 6:
						{
						_localctx = new LORContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(322);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(323); match(T__32);
						setState(324); expression(2);
						}
						break;
					}
					} 
				}
				setState(329);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,31,_ctx);
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
		case 23: return identAccess_sempred((IdentAccessContext)_localctx, predIndex);
		case 24: return expression_sempred((ExpressionContext)_localctx, predIndex);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3-\u014d\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\3\2\3\2\5\2\67\n\2\3\3\3\3\5\3;\n\3\3\4\3\4\3\5\3\5\5\5A\n"+
		"\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6J\n\6\3\7\6\7M\n\7\r\7\16\7N\3\7\3\7"+
		"\3\b\3\b\3\b\3\b\5\bW\n\b\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\7\na\n\n\f\n"+
		"\16\nd\13\n\3\13\3\13\3\13\3\13\7\13j\n\13\f\13\16\13m\13\13\3\13\3\13"+
		"\3\f\3\f\3\f\3\f\5\fu\n\f\3\f\3\f\3\f\7\fz\n\f\f\f\16\f}\13\f\3\f\7\f"+
		"\u0080\n\f\f\f\16\f\u0083\13\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u008d"+
		"\n\r\f\r\16\r\u0090\13\r\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u0098\n\16"+
		"\3\17\3\17\7\17\u009c\n\17\f\17\16\17\u009f\13\17\3\17\3\17\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20"+
		"\u00b2\n\20\3\21\3\21\3\21\5\21\u00b7\n\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\21\3\21\5\21\u00c0\n\21\3\21\3\21\3\21\5\21\u00c5\n\21\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\5\22\u00ce\n\22\3\23\3\23\3\23\3\23\3\23\3\23\3\24"+
		"\3\24\5\24\u00d8\n\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\5\25\u00e6\n\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\26\3\26\5\26\u00f8\n\26\3\27\3\27\3\27\3\27"+
		"\3\30\3\30\3\30\7\30\u0101\n\30\f\30\16\30\u0104\13\30\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\5\31\u010c\n\31\3\31\5\31\u010f\n\31\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u011e\n\31\3\31"+
		"\7\31\u0121\n\31\f\31\16\31\u0124\13\31\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u0134\n\32\3\32\3\32\3\32"+
		"\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\32"+
		"\3\32\7\32\u0148\n\32\f\32\16\32\u014b\13\32\3\32\2\4\60\62\33\2\4\6\b"+
		"\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\2\b\4\2\23\23\31\31\4\2"+
		"\"\"\'(\5\2\3\3\25\25##\3\2\'(\6\2\22\22\33\33\37\37!!\4\2\6\6\36\36\u0162"+
		"\2\66\3\2\2\2\4:\3\2\2\2\6<\3\2\2\2\b@\3\2\2\2\nI\3\2\2\2\fL\3\2\2\2\16"+
		"R\3\2\2\2\20\\\3\2\2\2\22b\3\2\2\2\24e\3\2\2\2\26p\3\2\2\2\30\u0086\3"+
		"\2\2\2\32\u0097\3\2\2\2\34\u0099\3\2\2\2\36\u00b1\3\2\2\2 \u00c4\3\2\2"+
		"\2\"\u00c6\3\2\2\2$\u00cf\3\2\2\2&\u00d5\3\2\2\2(\u00e5\3\2\2\2*\u00f7"+
		"\3\2\2\2,\u00f9\3\2\2\2.\u00fd\3\2\2\2\60\u010e\3\2\2\2\62\u0133\3\2\2"+
		"\2\64\67\5\6\4\2\65\67\5\b\5\2\66\64\3\2\2\2\66\65\3\2\2\2\67\3\3\2\2"+
		"\28;\5\2\2\29;\7\f\2\2:8\3\2\2\2:9\3\2\2\2;\5\3\2\2\2<=\t\2\2\2=\7\3\2"+
		"\2\2>A\7*\2\2?A\5\n\6\2@>\3\2\2\2@?\3\2\2\2A\t\3\2\2\2BC\7*\2\2CD\7\35"+
		"\2\2DJ\7 \2\2EF\5\6\4\2FG\7\35\2\2GH\7 \2\2HJ\3\2\2\2IB\3\2\2\2IE\3\2"+
		"\2\2J\13\3\2\2\2KM\5\16\b\2LK\3\2\2\2MN\3\2\2\2NL\3\2\2\2NO\3\2\2\2OP"+
		"\3\2\2\2PQ\7\2\2\3Q\r\3\2\2\2RS\7\7\2\2SV\7*\2\2TU\7\20\2\2UW\5\20\t\2"+
		"VT\3\2\2\2VW\3\2\2\2WX\3\2\2\2XY\7\13\2\2YZ\5\22\n\2Z[\7\17\2\2[\17\3"+
		"\2\2\2\\]\7*\2\2]\21\3\2\2\2^a\5\24\13\2_a\5\26\f\2`^\3\2\2\2`_\3\2\2"+
		"\2ad\3\2\2\2b`\3\2\2\2bc\3\2\2\2c\23\3\2\2\2db\3\2\2\2ef\5\2\2\2fk\7*"+
		"\2\2gh\7\27\2\2hj\7*\2\2ig\3\2\2\2jm\3\2\2\2ki\3\2\2\2kl\3\2\2\2ln\3\2"+
		"\2\2mk\3\2\2\2no\7\n\2\2o\25\3\2\2\2pq\5\4\3\2qr\7*\2\2rt\7\24\2\2su\5"+
		"\30\r\2ts\3\2\2\2tu\3\2\2\2uv\3\2\2\2vw\7&\2\2w{\7\13\2\2xz\5\24\13\2"+
		"yx\3\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2\2\2|\u0081\3\2\2\2}{\3\2\2\2~\u0080"+
		"\5\32\16\2\177~\3\2\2\2\u0080\u0083\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082"+
		"\3\2\2\2\u0082\u0084\3\2\2\2\u0083\u0081\3\2\2\2\u0084\u0085\7\17\2\2"+
		"\u0085\27\3\2\2\2\u0086\u0087\5\2\2\2\u0087\u008e\7*\2\2\u0088\u0089\7"+
		"\27\2\2\u0089\u008a\5\2\2\2\u008a\u008b\7*\2\2\u008b\u008d\3\2\2\2\u008c"+
		"\u0088\3\2\2\2\u008d\u0090\3\2\2\2\u008e\u008c\3\2\2\2\u008e\u008f\3\2"+
		"\2\2\u008f\31\3\2\2\2\u0090\u008e\3\2\2\2\u0091\u0098\5\36\20\2\u0092"+
		"\u0098\5 \21\2\u0093\u0098\5\"\22\2\u0094\u0098\5$\23\2\u0095\u0098\5"+
		"&\24\2\u0096\u0098\5(\25\2\u0097\u0091\3\2\2\2\u0097\u0092\3\2\2\2\u0097"+
		"\u0093\3\2\2\2\u0097\u0094\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0096\3\2"+
		"\2\2\u0098\33\3\2\2\2\u0099\u009d\7\13\2\2\u009a\u009c\5\32\16\2\u009b"+
		"\u009a\3\2\2\2\u009c\u009f\3\2\2\2\u009d\u009b\3\2\2\2\u009d\u009e\3\2"+
		"\2\2\u009e\u00a0\3\2\2\2\u009f\u009d\3\2\2\2\u00a0\u00a1\7\17\2\2\u00a1"+
		"\35\3\2\2\2\u00a2\u00a3\5\60\31\2\u00a3\u00a4\7\16\2\2\u00a4\u00a5\5\62"+
		"\32\2\u00a5\u00a6\7\n\2\2\u00a6\u00b2\3\2\2\2\u00a7\u00a8\5\60\31\2\u00a8"+
		"\u00a9\7\16\2\2\u00a9\u00aa\5*\26\2\u00aa\u00ab\7\n\2\2\u00ab\u00b2\3"+
		"\2\2\2\u00ac\u00ad\5\60\31\2\u00ad\u00ae\7\16\2\2\u00ae\u00af\5,\27\2"+
		"\u00af\u00b0\7\n\2\2\u00b0\u00b2\3\2\2\2\u00b1\u00a2\3\2\2\2\u00b1\u00a7"+
		"\3\2\2\2\u00b1\u00ac\3\2\2\2\u00b2\37\3\2\2\2\u00b3\u00b4\7*\2\2\u00b4"+
		"\u00b6\7\24\2\2\u00b5\u00b7\5.\30\2\u00b6\u00b5\3\2\2\2\u00b6\u00b7\3"+
		"\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00b9\7&\2\2\u00b9\u00c5\7\n\2\2\u00ba"+
		"\u00bb\5\60\31\2\u00bb\u00bc\7\30\2\2\u00bc\u00bd\7*\2\2\u00bd\u00bf\7"+
		"\24\2\2\u00be\u00c0\5.\30\2\u00bf\u00be\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0"+
		"\u00c1\3\2\2\2\u00c1\u00c2\7&\2\2\u00c2\u00c3\7\n\2\2\u00c3\u00c5\3\2"+
		"\2\2\u00c4\u00b3\3\2\2\2\u00c4\u00ba\3\2\2\2\u00c5!\3\2\2\2\u00c6\u00c7"+
		"\7\21\2\2\u00c7\u00c8\7\24\2\2\u00c8\u00c9\5\62\32\2\u00c9\u00ca\7&\2"+
		"\2\u00ca\u00cd\5\34\17\2\u00cb\u00cc\7%\2\2\u00cc\u00ce\5\34\17\2\u00cd"+
		"\u00cb\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce#\3\2\2\2\u00cf\u00d0\7\t\2\2"+
		"\u00d0\u00d1\7\24\2\2\u00d1\u00d2\5\62\32\2\u00d2\u00d3\7&\2\2\u00d3\u00d4"+
		"\5\34\17\2\u00d4%\3\2\2\2\u00d5\u00d7\7\5\2\2\u00d6\u00d8\5\62\32\2\u00d7"+
		"\u00d6\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00da\7\n"+
		"\2\2\u00da\'\3\2\2\2\u00db\u00dc\7\32\2\2\u00dc\u00dd\7\24\2\2\u00dd\u00de"+
		"\5\62\32\2\u00de\u00df\7&\2\2\u00df\u00e0\7\n\2\2\u00e0\u00e6\3\2\2\2"+
		"\u00e1\u00e2\7\34\2\2\u00e2\u00e3\7\24\2\2\u00e3\u00e4\7&\2\2\u00e4\u00e6"+
		"\7\n\2\2\u00e5\u00db\3\2\2\2\u00e5\u00e1\3\2\2\2\u00e6)\3\2\2\2\u00e7"+
		"\u00e8\7\4\2\2\u00e8\u00e9\7*\2\2\u00e9\u00ea\7\24\2\2\u00ea\u00f8\7&"+
		"\2\2\u00eb\u00ec\7\4\2\2\u00ec\u00ed\7*\2\2\u00ed\u00ee\7\35\2\2\u00ee"+
		"\u00ef\5\62\32\2\u00ef\u00f0\7 \2\2\u00f0\u00f8\3\2\2\2\u00f1\u00f2\7"+
		"\4\2\2\u00f2\u00f3\5\6\4\2\u00f3\u00f4\7\35\2\2\u00f4\u00f5\5\62\32\2"+
		"\u00f5\u00f6\7 \2\2\u00f6\u00f8\3\2\2\2\u00f7\u00e7\3\2\2\2\u00f7\u00eb"+
		"\3\2\2\2\u00f7\u00f1\3\2\2\2\u00f8+\3\2\2\2\u00f9\u00fa\7$\2\2\u00fa\u00fb"+
		"\7\24\2\2\u00fb\u00fc\7&\2\2\u00fc-\3\2\2\2\u00fd\u0102\5\62\32\2\u00fe"+
		"\u00ff\7\27\2\2\u00ff\u0101\5\62\32\2\u0100\u00fe\3\2\2\2\u0101\u0104"+
		"\3\2\2\2\u0102\u0100\3\2\2\2\u0102\u0103\3\2\2\2\u0103/\3\2\2\2\u0104"+
		"\u0102\3\2\2\2\u0105\u0106\b\31\1\2\u0106\u010f\7*\2\2\u0107\u010f\7\26"+
		"\2\2\u0108\u0109\7*\2\2\u0109\u010b\7\24\2\2\u010a\u010c\5.\30\2\u010b"+
		"\u010a\3\2\2\2\u010b\u010c\3\2\2\2\u010c\u010d\3\2\2\2\u010d\u010f\7&"+
		"\2\2\u010e\u0105\3\2\2\2\u010e\u0107\3\2\2\2\u010e\u0108\3\2\2\2\u010f"+
		"\u0122\3\2\2\2\u0110\u0111\f\6\2\2\u0111\u0112\7\30\2\2\u0112\u0121\7"+
		"*\2\2\u0113\u0114\f\5\2\2\u0114\u0115\7\35\2\2\u0115\u0116\5\62\32\2\u0116"+
		"\u0117\7 \2\2\u0117\u0121\3\2\2\2\u0118\u0119\f\3\2\2\u0119\u011a\7\30"+
		"\2\2\u011a\u011b\7*\2\2\u011b\u011d\7\24\2\2\u011c\u011e\5.\30\2\u011d"+
		"\u011c\3\2\2\2\u011d\u011e\3\2\2\2\u011e\u011f\3\2\2\2\u011f\u0121\7&"+
		"\2\2\u0120\u0110\3\2\2\2\u0120\u0113\3\2\2\2\u0120\u0118\3\2\2\2\u0121"+
		"\u0124\3\2\2\2\u0122\u0120\3\2\2\2\u0122\u0123\3\2\2\2\u0123\61\3\2\2"+
		"\2\u0124\u0122\3\2\2\2\u0125\u0126\b\32\1\2\u0126\u0127\t\3\2\2\u0127"+
		"\u0134\5\62\32\n\u0128\u0129\7\24\2\2\u0129\u012a\5\b\5\2\u012a\u012b"+
		"\7&\2\2\u012b\u012c\5\62\32\t\u012c\u0134\3\2\2\2\u012d\u0134\7)\2\2\u012e"+
		"\u0134\5\60\31\2\u012f\u0130\7\24\2\2\u0130\u0131\5\62\32\2\u0131\u0132"+
		"\7&\2\2\u0132\u0134\3\2\2\2\u0133\u0125\3\2\2\2\u0133\u0128\3\2\2\2\u0133"+
		"\u012d\3\2\2\2\u0133\u012e\3\2\2\2\u0133\u012f\3\2\2\2\u0134\u0149\3\2"+
		"\2\2\u0135\u0136\f\b\2\2\u0136\u0137\t\4\2\2\u0137\u0148\5\62\32\t\u0138"+
		"\u0139\f\7\2\2\u0139\u013a\t\5\2\2\u013a\u0148\5\62\32\b\u013b\u013c\f"+
		"\6\2\2\u013c\u013d\t\6\2\2\u013d\u0148\5\62\32\7\u013e\u013f\f\5\2\2\u013f"+
		"\u0140\t\7\2\2\u0140\u0148\5\62\32\6\u0141\u0142\f\4\2\2\u0142\u0143\7"+
		"\r\2\2\u0143\u0148\5\62\32\5\u0144\u0145\f\3\2\2\u0145\u0146\7\b\2\2\u0146"+
		"\u0148\5\62\32\4\u0147\u0135\3\2\2\2\u0147\u0138\3\2\2\2\u0147\u013b\3"+
		"\2\2\2\u0147\u013e\3\2\2\2\u0147\u0141\3\2\2\2\u0147\u0144\3\2\2\2\u0148"+
		"\u014b\3\2\2\2\u0149\u0147\3\2\2\2\u0149\u014a\3\2\2\2\u014a\63\3\2\2"+
		"\2\u014b\u0149\3\2\2\2\"\66:@INV`bkt{\u0081\u008e\u0097\u009d\u00b1\u00b6"+
		"\u00bf\u00c4\u00cd\u00d7\u00e5\u00f7\u0102\u010b\u010e\u011d\u0120\u0122"+
		"\u0133\u0147\u0149";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}