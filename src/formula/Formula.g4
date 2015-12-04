grammar Formula;

// This puts "package formula;" at the top of the output Java files.
@header {
package formula;
}

// This adds code to the generated lexer and parser. Do not change these lines.
@members {
    // This method makes the lexer or parser stop running if it encounters
    // invalid input and throw a RuntimeException.
    public void reportErrorsAsExceptions() {
        //removeErrorListeners();
        
        addErrorListener(new ExceptionThrowingErrorListener());
    }
    
    private static class ExceptionThrowingErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                Object offendingSymbol, int line, int charPositionInLine,
                String msg, RecognitionException e) {
            throw new RuntimeException(msg);
        }
    }
}

/*
 * These are the lexical rules. They define the tokens used by the lexer.
 *   *** Antlr requires tokens to be CAPITALIZED
 */
AND : '&&';
OR : '||';
IN : 'in' LPAREN STRING RPAREN;
CATEGORY : 'category' LPAREN STRING RPAREN;
RATING : 'rating' LPAREN RANGE RPAREN;
PRICE : 'price' LPAREN RANGE RPAREN;
NAME : 'name' LPAREN STRING RPAREN;
LPAREN : '(' ;
RPAREN : ')' ;
RANGE : [1-5]'..'[1-5];
WHITESPACE : [ \t\r\n]+ -> skip ;
STRING: [a-zA-Z0-9] [a-zA-Z0-9' '&]*;


/*
 * These are the parser rules. They define the structures used by the parser.
 * 
 */
orExpr : andExpr (OR andExpr)*;
andExpr : atom (AND atom)*;
atom : IN | CATEGORY | RATING | PRICE | NAME | (LPAREN orExpr RPAREN);