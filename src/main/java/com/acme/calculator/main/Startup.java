package com.acme.calculator.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.MathContext;

/**
 * Created by pturcotte on 1/8/16.
 */
public class Startup {
    private static final Logger logger = LogManager.getLogger(Startup.class);

    private static final String WHITESPACE_REGEXP = "\\s";
    private static final char LEFT_PARENS = '(';
    private static final char RIGHT_PARENS = ')';
    private static final String SYNTAX_ERROR_PREFIX = "Expression syntax error";

    // some early code (to remove any surplus white-space), prior to going down the ANTLR route
    public String cleanup(final String expr) {
        if (expr == null) {
            return null;
        }
        final String cleaned = expr.replace(WHITESPACE_REGEXP, "");
        logger.trace("cleanup(): '{}' -> '{}'", expr, cleaned);
        return cleaned;
    }

    // some early code (thinking of pre-validating basic syntax of the expression such as parentheses), prior to going down the ANTLR route
    public boolean isValid(final String expr) {
        int leftParensCount = 0;
        int rightParensCount = 0;
        boolean valid = true;

        if (expr == null) {
            logger.error("Expression is null");
            return false;
        }
        if (expr.length() == 0) {
            logger.error("Expression is empty.");
            return false;
        }

        for (int psn = 0; psn < expr.length() && valid; psn++) {
            char nextChar = expr.charAt(psn);
            if (nextChar == LEFT_PARENS) {
                leftParensCount++;
            } else if (nextChar == RIGHT_PARENS) {
                rightParensCount++;
                if (rightParensCount > leftParensCount) {
                    logger.error("{} at position {}: a parens is misplaced or missing in expression: '{}'", SYNTAX_ERROR_PREFIX, psn, expr);
                    valid = false;
                }
            }
        }

        if (valid) {
            if (leftParensCount != rightParensCount) {
                logger.error("{}: left parens count != right parens count ({} != {}) in expression: '{}'", SYNTAX_ERROR_PREFIX, leftParensCount, rightParensCount, expr);
                valid = false;
            }

            if (leftParensCount == 0) {
                logger.error("{}: no parentheses '{}', '{}' found in expression: '{}'", SYNTAX_ERROR_PREFIX, LEFT_PARENS, RIGHT_PARENS, expr);
                valid = false;
            }
        }
        return valid;
    }

    public static void main(String[] args) {
        Calculator parser = new Calculator(MathContext.DECIMAL128);
        System.out.println(parser.calculate("add(div(1,5), div(1,7))"));

        System.out.println(parser.calculate("add(5, 1)"));
        System.out.println(parser.calculate("sub(-10, -12)"));
        System.out.println(parser.calculate("let(a, 5, div(1, 0))"));
//        System.out.println(parser.calculate("let(b, 5, add(a, a))"));
        System.out.println(parser.calculate("div(5, 9)"));
        System.out.println(parser.calculate("let(a, 5, 9)"));

        System.out.println(parser.calculate("div(5, 1)"));
//        System.out.println(parser.calculate("div(5, 2)"));
//        System.out.println(parser.calculate("div(5, 13)"));
//        System.out.println(parser.calculate("div(1, 9)"));
//        System.out.println(parser.calculate("div(5, 9)"));
//        System.out.println(parser.calculate("add(0, 0)"));
//        System.out.println(parser.calculate("add(2147483647, 2147483647)"));
//        System.out.println(parser.calculate("add(-2147483647, -2147483647)"));
//        System.out.println(parser.calculate("mult(2147483647, 2147483647)"));
//        System.out.println(parser.calculate("mult(9903520300447984150353281023, mult(9903520300447984150353281023, 9903520300447984150353281023))").toEngineeringString());
//        System.out.println(parser.calculate("let(a, 5, add(a, a))"));
//        System.out.println(parser.calculate("let(a, 5, let(b, mult(a, 10), add(b,a)))"));
        System.out.println(parser.calculate("let(a, let(b, 10, add(b,b)), let(b, 20, add(a, b)))"));

//        System.out.println(parser.calculate("add(32,15)"));
//        System.out.println(parser.calculate("sub(101,3)"));
//        System.out.println(parser.calculate("mult(101,3)"));
//        System.out.println(parser.calculate("div(101,3)"));
//        System.out.println(parser.calculate("add(1,mult(2,3))"));
//        System.out.println(parser.calculate("mult(add(2,2),div(9,3))"));
        // System.out.println(parser.calculate("let(a,3)"));
    }


//    public static void main(final String[] args) {
//        new Startup().isValid("add(2,2)");
//        new Startup().isValid("add(");
//        new Startup().isValid("add)(");
//    }
}
