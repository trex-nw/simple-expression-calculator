// Generated from BasicCalculator.g4 by ANTLR 4.5.1

package com.acme.calculator.antlr4;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BasicCalculatorParser}.
 */
public interface BasicCalculatorListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BasicCalculatorParser#calc}.
	 * @param ctx the parse tree
	 */
	void enterCalc(BasicCalculatorParser.CalcContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicCalculatorParser#calc}.
	 * @param ctx the parse tree
	 */
	void exitCalc(BasicCalculatorParser.CalcContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicCalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(BasicCalculatorParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicCalculatorParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(BasicCalculatorParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicCalculatorParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(BasicCalculatorParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicCalculatorParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(BasicCalculatorParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link BasicCalculatorParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(BasicCalculatorParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BasicCalculatorParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(BasicCalculatorParser.FunctionContext ctx);
}