package formula;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import ca.ece.ubc.cpen221.mp5.Restaurant;


public class FormulaFactory {
    
    
    /**
     * @param string must contain a well-formed formula string of boolean literals and operators..
     * @return Formula corresponding to the string
     */
    public static Formula parse(String string) {
        // Create a stream of tokens using the lexer.
        CharStream stream = new ANTLRInputStream(string);
        FormulaLexer lexer = new FormulaLexer(stream);
        lexer.reportErrorsAsExceptions();
        TokenStream tokens = new CommonTokenStream(lexer);
        
        // Feed the tokens into the parser.
        FormulaParser parser = new FormulaParser(tokens);
        parser.reportErrorsAsExceptions();
        
        // Generate the parse tree using the starter rule.
        ParseTree tree = parser.orExpr(); // "root" is the starter rule.
        
        // debugging option #1: print the tree to the console
        // System.err.println(tree.toStringTree(parser));

        // debugging option #2: show the tree in a window
        // ((RuleContext)tree).inspect(parser);

        // debugging option #3: walk the tree with a listener
        // new ParseTreeWalker().walk(new FormulaListener_PrintEverything(), tree);
        
        // Finally, construct a Document value by walking over the parse tree.
        ParseTreeWalker walker = new ParseTreeWalker();
        FormulaListener_FormulaCreator listener = new FormulaListener_FormulaCreator();
        walker.walk(listener, tree);
        
        // return the Document value that the listener created
        return null; //CHANGE
    }
    
    
    private static class FormulaListener_FormulaCreator extends FormulaBaseListener {
        
        //stack stores the restaurant that matches each of the atomic expressions
        private Stack<Set<Restaurant>> stack = new Stack<Set<Restaurant>>();
        
        // this represents the set of restaurants that corresponds to the  query
        private final Set<Restaurant> restaurantSet = Collections.synchronizedSet(new HashSet<Restaurant>());
        
        @Override
        public void exitOrExpr(@NotNull FormulaParser.OrExprContext ctx) { 
            //or expression is the union of the restaurants given by each of its two expressions
            if (ctx.OR() != null) {
                // we matched the OR rule
                Set<Restaurant> result1 = stack.pop();
                Set<Restaurant> result2 = stack.pop();
                
                Or or = new Or(result1, result2);
                stack.push(or.evaluate());
                
            } 
            else {
                //do nothing
            }
        }
        
        @Override
        public void exitAndExpr(@NotNull FormulaParser.AndExprContext ctx) { 
            //and expression is the intersection of the restaurants found by evaluating each of its two expressions
            if (ctx.AND() != null) {
                // we matched the OR rule
                Set<Restaurant> result1 = stack.pop();
                Set<Restaurant> result2 = stack.pop();
                
                And and = new And(result1, result2);
                stack.push(and.evaluate());
                
            } 
            else {
                //do nothing
            }
        }
        
        @Override
        public void exitAtom(@NotNull FormulaParser.AtomContext ctx) { 
        }
        
        
       
    }
    
    private static class FormulaListener_PrintEverything extends FormulaBaseListener {
        public void enterOrExpr(@NotNull FormulaParser.OrExprContext ctx) { 
            System.err.println("entering or expression " + ctx.getText());
        }
        
        public void exitOrExpr(@NotNull FormulaParser.OrExprContext ctx) { 
            System.err.println("exiting or expression " + ctx.getText());
        }
        
        public void enterAtom(@NotNull FormulaParser.AtomContext ctx) { 
            System.err.println("entering atom " + ctx.getText());
        }
        
        public void exitAtom(@NotNull FormulaParser.AtomContext ctx) { 
            System.err.println("exiting atom " + ctx.getText());
        }
        public void enterAndExpr(@NotNull FormulaParser.AndExprContext ctx) { 
            System.err.println("entering and expression " + ctx.getText());
        }
        public void exitAndExpr(@NotNull FormulaParser.AndExprContext ctx) { 
            System.err.println("entering exiting and expression " + ctx.getText());
        }

    }
}
