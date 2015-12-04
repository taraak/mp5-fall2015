// Generated from Formula.g4 by ANTLR 4.4

package formula;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FormulaParser}.
 */
public interface FormulaListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FormulaParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpr(@NotNull FormulaParser.OrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormulaParser#orExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpr(@NotNull FormulaParser.OrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormulaParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(@NotNull FormulaParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormulaParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(@NotNull FormulaParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link FormulaParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpr(@NotNull FormulaParser.AndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link FormulaParser#andExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpr(@NotNull FormulaParser.AndExprContext ctx);
}