package com.acme.calculator.main;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by pturcotte on 1/12/16.
 */
public class CalculatorAssignmentTests extends CalculatorTestSetup {

    @Test
    public void assignErrorFunctionTypo() {
        String expr = "le(a,12,add(a,a))";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error: invalid use of function or variable: 'le'"));
    }

    @Test
    public void assignErrorMissingClosingParens() {
        String expr = "let(a,12,add(a,a)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing ')'"));
        assertTrue(result.contains("on line 1, position 17"));
    }

    @Test
    public void assignErrorMissingStartingParens() {
        String expr = "let a,5,9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing '('"));
        assertTrue(result.contains("on line 1, position 4"));
    }

    @Test
    public void assignErrorMissingClosingParensOneCharLonger() {
        String expr = "let(a,5,9";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing ')'"));
        assertTrue(result.contains("on line 1, position 9"));
    }

    @Test
    public void assignErrorMissingComma() {
        String expr = "let(a,9 add(a,5))";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing ','"));
        assertTrue(result.contains("on line 1, position 8"));
    }

    @Test
    public void assignErrorSurplusComma() {
        String expr = "let(a,9,,5)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'no viable alternative at input ','"));
        assertTrue(result.contains("on line 1, position 8"));
    }

    @Test
    public void assignErrorWhitespaceInOperation() {
        String expr = "le t(a,12,9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error: invalid use of function or variable: 'le'"));
    }

    @Test
    public void assignErrorInvalidVariableName() {
        String expr = "let(5,12,add(a,a)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'mismatched input '5' expecting VARIABLE'"));
        assertTrue(result.contains("on line 1, position 4"));
    }

    @Test
    public void assignErrorVarMismatch() {
        String expr = "let(aaa,11,mult(aab,55))";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error: invalid use of function or variable: 'aab'"));
    }

    @Test
    public void assignWithWhitespace() {
        assertEquals("6", calc("  let  (\t a, 5 , \n\n add(a,  1 )  )"));
    }

    @Test
    public void assignVarNotUsedInExpr() {
        assertEquals("69", calc("let(wxyz,11,mult(3,23))"));
    }

    @Test
    public void assignLowerCaseMult() {
        assertEquals("605", calc("let(abc,11,mult(abc,55))"));
    }

    @Test
    public void assignMixedCaseMult() {
        assertEquals("605", calc("let(aBcDE,11,mult(aBcDE,55))"));
    }

    @Test
    public void assignAddToSelf() {
        assertEquals("10", calc("let(a,5,add(a, a))"));
    }

    @Test
    public void assignTwoVarsWithMult() {
        assertEquals("55", calc("let(a,5,let(b,mult(a, 10),add(b,a)))"));
    }

    @Test
    public void assignEmbedded() {
        assertEquals("40", calc("let(a, let(b, 10, add(b,b)), let(b, 20, add(a, b)))"));
    }

    @Test
    public void assignZeroSubSelf() {
        assertEquals("0", calc("let(a,0,sub(a, a))"));
    }

    @Test
    public void assignMaxMult() {
        String expr = String.format("let(a,%d,mult(a, a))", Integer.MAX_VALUE);
        assertEquals("4611686014132420609", calc(expr));
    }

    @Test
    public void assignMaxAddOne() {
        String expr = String.format("let(a,%d,add(a, 1))", Integer.MAX_VALUE);
        assertEquals("2147483648", calc(expr));
    }

    @Test
    public void assignMinMult() {
        String expr = String.format("let(a,%d,mult(a, 99))", Integer.MIN_VALUE);
        assertEquals("-212600881152", calc(expr));
    }

    @Test
    public void assignMinSubOne() {
        String expr = String.format("let(a,%d,sub(a, 1))", Integer.MIN_VALUE);
        assertEquals("-2147483649", calc(expr));
    }
}
