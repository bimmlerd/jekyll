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
	 * Visit a parse tree produced by the {@code ADD}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitADD(@NotNull JavaliParser.ADDContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#writeStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWriteStatement(@NotNull JavaliParser.WriteStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IDACC}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIDACC(@NotNull JavaliParser.IDACCContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#methodCallStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodCallStatement(@NotNull JavaliParser.MethodCallStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#assignmentStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentStatement(@NotNull JavaliParser.AssignmentStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#formalParamList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParamList(@NotNull JavaliParser.FormalParamListContext ctx);
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
	 * Visit a parse tree produced by {@link JavaliParser#referenceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenceType(@NotNull JavaliParser.ReferenceTypeContext ctx);
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
	 * Visit a parse tree produced by {@link JavaliParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(@NotNull JavaliParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code COMP}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCOMP(@NotNull JavaliParser.COMPContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#methodType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodType(@NotNull JavaliParser.MethodTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#memberList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberList(@NotNull JavaliParser.MemberListContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MULT}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMULT(@NotNull JavaliParser.MULTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PARS}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPARS(@NotNull JavaliParser.PARSContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(@NotNull JavaliParser.StatementContext ctx);
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
	 * Visit a parse tree produced by the {@code CAST}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCAST(@NotNull JavaliParser.CASTContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#arrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(@NotNull JavaliParser.ArrayTypeContext ctx);
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
	 * Visit a parse tree produced by {@link JavaliParser#newExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExpression(@NotNull JavaliParser.NewExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#identAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentAccess(@NotNull JavaliParser.IdentAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(@NotNull JavaliParser.IfStatementContext ctx);
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
	 * Visit a parse tree produced by {@link JavaliParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(@NotNull JavaliParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#unit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnit(@NotNull JavaliParser.UnitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code LIT}
	 * labeled alternative in {@link JavaliParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLIT(@NotNull JavaliParser.LITContext ctx);
	/**
	 * Visit a parse tree produced by {@link JavaliParser#varDecl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDecl(@NotNull JavaliParser.VarDeclContext ctx);
}