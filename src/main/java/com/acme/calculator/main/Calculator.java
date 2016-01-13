package com.acme.calculator.main;

import com.acme.calculator.antlr4.BasicCalculatorLexer;
import com.acme.calculator.antlr4.BasicCalculatorParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * This class provides the functionality to parse and calculate an arithmetic expression such as
 * "let(a, 5, add(a, 9))".
 * <p>
 * The expression supports basic arithmetic, as well as a "let" operation where an expression or number
 * can be assigned to a variable.
 * <p>
 * The arithmetic operations supported are:
 * div(val1, val2): divides val1 by val2
 * mult(val1, val2): multiplies val1 and val2
 * add(val1, val2): adds val1 and val2
 * sub(val1, val2): subtracts val2 from val1
 * where val1 and val2 can be an integer or an expression that evaluates
 * to a number.
 * <p>
 * The expected input integer range is Integer.MIN_VALUE to Integer.MAX_VALUE.
 * <p>
 * Digits of precision and rounding behavior can be specified using a desired
 * MathContext instance.
 * <p>
 * Arithmetic examples:
 * <p>
 * div(9, 3) -&gt; 3
 * <p>
 * add(20, div(9, 3)) -&gt; 23
 * <p>
 * sub(-10, -12) -&gt; 2
 * <p>
 * add(2147483647, 1) -&gt; 2147483648
 * <p>
 * div(5, 9) -&gt; 0.5555555555555555555555555555555556
 * <p>
 * div(1, 0) -&gt; "Problem parsing/calculating expression: 'div(1, 0)' (java.lang.ArithmeticException: Division by zero)"
 * <p>
 * The syntax of the "let" operation is:
 * <p>
 * let(varName, val_expr, expr)
 * <p>
 * varName: the name of the variable one more of characters: a-z|A-Z
 * <p>
 * val_expr: a number or expression
 * <p>
 * expr: an expression using the defined variable
 * <p>
 * Examples incorporating the "let" operation:
 * <p>
 * let(a, 5, add(a, 9)) -&gt; 14
 * <p>
 * let(a, let(b, 10, add(b,b)), let(b, 20, add(a, b)) -&gt; 40
 * <p>
 * This code was made possible by the ANTLR framework by Terence Parr (http://www.antlr.org/) and the example at
 * http://niels.nu/blog/2015/antlr-is-awesome.html by Niels Dommerholt.
 */
public class Calculator {
    private static final Logger logger = LogManager.getLogger(Calculator.class);
    static final String ERROR_PREFIX = "Problem parsing/calculating expression:"; // prefix for error string
    static final String PERIOD = ".";
    static final String ZERO = "0";
    private final Map<String, BigDecimal> variablesMap = new HashMap<>();
    private final ANTLRErrorListener errorListener;
    // i.e. MathContext.DECIMAL128 = 34 digits, RoundingMode.HALF_EVEN
    private final MathContext mathContext;

    /**
     * Constructs an instance of this class, with the MathContext specifying
     * the precision and rounding mode that is used for the BigDecimal
     * arithmetic.
     *
     * @param mathContextToSet MathContext to be used, such as MathContext.DECIMAL128
     */
    public Calculator(final MathContext mathContextToSet) {
        logger.debug("Calculator() MathContext: {}", mathContextToSet.toString());
        this.mathContext = mathContextToSet;
        this.errorListener = createErrorListener();
    }

    /**
     * Parses an expression, performs the specified calculations in the expression, and returns a String result.
     *
     * @param expression the expression to parse and calculate
     * @return String result of the expression; can be a descriptive error message
     */
    public String calculate(final String expression) {
        logger.info("calculate()");
        logger.debug("calculate() expression: '{}'", expression);
        String result = null;
        try {
            BigDecimal value = parse(expression);
            result = value.toEngineeringString();
            // Remove trailing zeros so 1/16 + 1/16 -> 0.125 (instead of 0.1250)
            // and 2.000...0 becomes "2"
            if (result.contains(PERIOD) && result.endsWith(ZERO)) {
                result = value.stripTrailingZeros().toEngineeringString();
                logger.debug("used stripTrailingZeros() on output");
            }
            // For the integer value case, BigDecimal.toBigIntegerExact() can also be used to remove trailing zeros.
        } catch (Throwable xcptn) {
            final String errorMsg = String.format("%s '%s'", ERROR_PREFIX, expression);
            logger.error(errorMsg, xcptn);
            result = errorMsg + " (" + xcptn.toString() + ")";
        }
        return result;
    }

    /**
     * Parses an expression, performs the specified calculations in the expression, and returns a BigDecimal result.
     *
     * @param expression the expression to parse and calculate
     * @return BigDecimal result of the expression
     */
    private BigDecimal parse(final String expression) {
        logger.debug("parse() expression: '{}'", expression);
        // Clear out any prexisting variable values in the event that an instance of this class is invoked more
        // than once, since there isn't currently a use-case to cache variable data across multiple expressions.
        variablesMap.clear();
        final BasicCalculatorLexer lexer = createLexer(expression);

        final BasicCalculatorParser parser = createParser(lexer);

        // root of the AST (Abstract Syntax Tree)
        final BasicCalculatorParser.ExprContext context = parser.expr();

        // todo: research further
        // This was intended for debugging use but isn't working as expected - causes "Syntax error in expression"
        // ParseTree tree = parser.calc(); // begin parsing at "calc" rule
        // System.out.println(tree.toStringTree(parser)); // print LISP-style tree }

        // visit all the branches of the tree to obtain the result
        return visit(context);
    }

    /**
     * The lexer reads characters and converts them into a token stream.
     * The tokens are consumed by the parser as it builds an AST.
     *
     * @param lexer
     * @return BasicCalculatorParser created
     */
    private BasicCalculatorParser createParser(final BasicCalculatorLexer lexer) {
        logger.debug("createParser()");
        final CommonTokenStream tokens = new CommonTokenStream(lexer);
        final BasicCalculatorParser parser = new BasicCalculatorParser(tokens);
        // Replace default Antlr4 error listener (which prints errors to stderr).
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        return parser;
    }

    /**
     * Creates a lexer to tokenize the expression.
     *
     * @param expression to be tokenized
     * @return BasicCalculatorLexer created
     */
    private BasicCalculatorLexer createLexer(final String expression) {
        logger.debug("createLexer()");
        final BasicCalculatorLexer lexer = new BasicCalculatorLexer(new ANTLRInputStream(expression));
        // Replace default Antlr4 error listener (which prints errors to stderr).
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);
        return lexer;
    }

    /**
     * Visits the branches in the expression tree recursively until
     * reaching a leaf, performing relevant calculations as needed at each
     * node to calculate the final result.
     *
     * @param context AST of the expression to be calculated
     * @return BigDecimal with total of the calculations in the expression
     */
    private BigDecimal visit(final BasicCalculatorParser.ExprContext context) {
        if (context.INTEGER() != null) {
            String text = context.INTEGER().getText();
            logger.trace("visited INTEGER with text: '{}'", text);
            return new BigDecimal(text, mathContext);
        } else if (context.assignment() != null) {
            final String variableName = context.VARIABLE().getText();
            final BigDecimal value = visit(context.expr(0));
            variablesMap.put(variableName, value);
            logger.trace("Stored var value {} -> {}", variableName, value);
            return visit(context.expr(1));
        } else if (context.VARIABLE() != null) {
            final String variableName = context.VARIABLE().getText();
            final BigDecimal value = variablesMap.get(variableName);
            if (value == null) {
                throw new IllegalArgumentException(
                        String.format("Syntax error: invalid use of function or variable: '%s'", variableName));
            }
            logger.trace("Retrieved var value {} -> {}", variableName, value);
            return value;
        } else if (context.function().ADD() != null) {
            logger.trace("ADD");
            return visit(context.expr(0)).add(visit(context.expr(1)), mathContext);
        } else if (context.function().SUBTRACT() != null) {
            logger.trace("SUBTRACT");
            return visit(context.expr(0)).subtract(visit(context.expr(1)), mathContext);
        } else if (context.function().MULTIPLY() != null) {
            logger.trace("MULTIPLY");
            return visit(context.expr(0)).multiply(visit(context.expr(1)), mathContext);
        } else if (context.function().DIVIDE() != null) {
            logger.trace("DIVIDE");
            return visit(context.expr(0)).divide(visit(context.expr(1)), mathContext);
        } else {
            throw new IllegalStateException();
        }
    }

    /*
     * Helper method to create an ANTLRErrorListener.
     * Only the syntaxError() method is utilized.
     * todo: The other three methods below could be researched further to
     * determine if they may be of use for edge case(s).
     */
    private ANTLRErrorListener createErrorListener() {
        return new ANTLRErrorListener() {
            public void syntaxError(final Recognizer<?, ?> arg0, final Object obj, final int line,
                                    final int position, final String message, final RecognitionException ex) {
                throw new IllegalArgumentException(String.format(Locale.ROOT,
                        "Syntax error in expression: '%s' on line %s, position %s",
                        message, line, position));
            }

            @Override
            // not used by lexer code
            public void reportAmbiguity(final Parser parser, final DFA dfa, final int i, final int i1,
                                        final boolean b, final BitSet bitSet, final ATNConfigSet atnConfigSet) {
            }

            @Override
            // not used by lexer code
            public void reportAttemptingFullContext(final Parser parser, final DFA dfa, final int i, final int i1,
                                                    final BitSet bitSet, final ATNConfigSet atnConfigSet) {
            }

            @Override
            // not used by lexer code
            public void reportContextSensitivity(final Parser parser, final DFA dfa, final int i, final int i1,
                                                 final int i2, final ATNConfigSet atnConfigSet) {
            }
        };
    }
}
