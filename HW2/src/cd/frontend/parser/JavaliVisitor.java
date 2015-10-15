// Generated from /Users/andrina/Sources/jekyll/HW2/src/cd/frontend/parser/Javali.g4 by ANTLR 4.4

	// Java header
	package cd.frontend.parser;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link JavaliParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface JavaliVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code arrayTypeId}
	 * labeled alternative in {@link JavaliParser#arrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayTypeId(@NotNull JavaliParser.ArrayTypeIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ADD}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitADD(@NotNull JavaliParser.ADDContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#methodCallStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCallStatement(@NotNull JavaliParser.MethodCallStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#formalParamList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParamList(@NotNull JavaliParser.FormalParamListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code COMP}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCOMP(@NotNull JavaliParser.COMPContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MULT}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMULT(@NotNull JavaliParser.MULTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code typeReference}
	 * labeled alternative in {@link JavaliParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeReference(@NotNull JavaliParser.TypeReferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignmentStmtRead}
	 * labeled alternative in {@link JavaliParser#assignmentStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStmtRead(@NotNull JavaliParser.AssignmentStmtReadContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignmentStmtExpr}
	 * labeled alternative in {@link JavaliParser#assignmentStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStmtExpr(@NotNull JavaliParser.AssignmentStmtExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CAST}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCAST(@NotNull JavaliParser.CASTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignmentStmtNew}
	 * labeled alternative in {@link JavaliParser#assignmentStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStmtNew(@NotNull JavaliParser.AssignmentStmtNewContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#classDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDecl(@NotNull JavaliParser.ClassDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#readExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReadExpression(@NotNull JavaliParser.ReadExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EQ}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEQ(@NotNull JavaliParser.EQContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LOR}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLOR(@NotNull JavaliParser.LORContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtMethodCall}
	 * labeled alternative in {@link JavaliParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtMethodCall(@NotNull JavaliParser.StmtMethodCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#unit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnit(@NotNull JavaliParser.UnitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code methodTypeType}
	 * labeled alternative in {@link JavaliParser#methodType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodTypeType(@NotNull JavaliParser.MethodTypeTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identAccessField}
	 * labeled alternative in {@link JavaliParser#identAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentAccessField(@NotNull JavaliParser.IdentAccessFieldContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newArrayId}
	 * labeled alternative in {@link JavaliParser#newExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArrayId(@NotNull JavaliParser.NewArrayIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identAccessThis}
	 * labeled alternative in {@link JavaliParser#identAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentAccessThis(@NotNull JavaliParser.IdentAccessThisContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayTypePr}
	 * labeled alternative in {@link JavaliParser#arrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayTypePr(@NotNull JavaliParser.ArrayTypePrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code typePrimitive}
	 * labeled alternative in {@link JavaliParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypePrimitive(@NotNull JavaliParser.TypePrimitiveContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IDACC}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIDACC(@NotNull JavaliParser.IDACCContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#statementBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementBlock(@NotNull JavaliParser.StatementBlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LAND}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLAND(@NotNull JavaliParser.LANDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code methodTypeVoid}
	 * labeled alternative in {@link JavaliParser#methodType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodTypeVoid(@NotNull JavaliParser.MethodTypeVoidContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtWrite}
	 * labeled alternative in {@link JavaliParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtWrite(@NotNull JavaliParser.StmtWriteContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#methodDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDecl(@NotNull JavaliParser.MethodDeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(@NotNull JavaliParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identAccessArray}
	 * labeled alternative in {@link JavaliParser#identAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentAccessArray(@NotNull JavaliParser.IdentAccessArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#memberList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberList(@NotNull JavaliParser.MemberListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtIf}
	 * labeled alternative in {@link JavaliParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtIf(@NotNull JavaliParser.StmtIfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newArrayPr}
	 * labeled alternative in {@link JavaliParser#newExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewArrayPr(@NotNull JavaliParser.NewArrayPrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PARS}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPARS(@NotNull JavaliParser.PARSContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(@NotNull JavaliParser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#actualParamList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitActualParamList(@NotNull JavaliParser.ActualParamListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code UNARY}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUNARY(@NotNull JavaliParser.UNARYContext ctx);
	/**
	 * Visit a parse tree produced by the {@code referenceTypeAr}
	 * labeled alternative in {@link JavaliParser#referenceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenceTypeAr(@NotNull JavaliParser.ReferenceTypeArContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identAccessMethod}
	 * labeled alternative in {@link JavaliParser#identAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentAccessMethod(@NotNull JavaliParser.IdentAccessMethodContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newIdentifier}
	 * labeled alternative in {@link JavaliParser#newExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewIdentifier(@NotNull JavaliParser.NewIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(@NotNull JavaliParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code writeStmt}
	 * labeled alternative in {@link JavaliParser#writeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteStmt(@NotNull JavaliParser.WriteStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(@NotNull JavaliParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code writeLnStmt}
	 * labeled alternative in {@link JavaliParser#writeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteLnStmt(@NotNull JavaliParser.WriteLnStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtWhile}
	 * labeled alternative in {@link JavaliParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtWhile(@NotNull JavaliParser.StmtWhileContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identAccessFieldMethod}
	 * labeled alternative in {@link JavaliParser#identAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentAccessFieldMethod(@NotNull JavaliParser.IdentAccessFieldMethodContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LIT}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLIT(@NotNull JavaliParser.LITContext ctx);
	/**
	 * Visit a parse tree produced by the {@code referenceTypeId}
	 * labeled alternative in {@link JavaliParser#referenceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenceTypeId(@NotNull JavaliParser.ReferenceTypeIdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtAssignment}
	 * labeled alternative in {@link JavaliParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtAssignment(@NotNull JavaliParser.StmtAssignmentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identAccessId}
	 * labeled alternative in {@link JavaliParser#identAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentAccessId(@NotNull JavaliParser.IdentAccessIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(@NotNull JavaliParser.VarDeclContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmtReturn}
	 * labeled alternative in {@link JavaliParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmtReturn(@NotNull JavaliParser.StmtReturnContext ctx);
}