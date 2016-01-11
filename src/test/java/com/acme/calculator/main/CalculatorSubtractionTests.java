package com.acme.calculator.main;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by pturcotte on 1/10/16.
 */
public class CalculatorSubtractionTests extends CalculatorTestSetup {
    @Test
    public void subtractErrorFunctionTypo() {
        String expr = "ub(12,9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error: invalid use of function or variable: 'ub'"));
    }

    @Test
    public void subtractErrorMissingClosingParens() {
        String expr = "sub(12,9";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing ')'"));
        assertTrue(result.contains("on line 1, position 8"));
    }

    @Test
    public void subtractErrorMissingStartingParens() {
        String expr = "sub 12,9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing '('"));
        assertTrue(result.contains("on line 1, position 4"));
    }

    @Test
    public void subtractErrorMissingClosingParensOneCharLonger() {
        String expr = "sub(12,19";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing ')'"));
        assertTrue(result.contains("on line 1, position 9"));
    }

    @Test
    public void subtractErrorMissingComma() {
        String expr = "sub(12 9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing ','"));
        assertTrue(result.contains("on line 1, position 7"));
    }

    @Test
    public void subtractErrorSurplusComma() {
        String expr = "sub(123,9,)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'extraneous input ',' expecting ')'"));
        assertTrue(result.contains("on line 1, position 9"));
    }

    @Test
    public void subtractErrorWhitespaceInOperation() {
        String expr = "su b(12,9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error: invalid use of function or variable: 'su'"));
    }
    
    @Test
    public void subtractWithWhitespace() {
        assertEquals("3", calc("  sub  (\t12 , \n\n 9  )"));
    }


    @Test
    public void subtractOneFromTen() {
        assertEquals("9", calc("sub(10,1)"));
    }

    @Test
    public void subtractTenFromOne() {
        assertEquals("-9", calc("sub(1,10)"));
    }

    @Test
    public void subtractOneAndOne() {
        assertEquals("0", calc("sub(1,1)"));
    }

    @Test
    public void subtractZero() {
        assertEquals("1", calc("sub(1,0)"));
    }

    @Test
    public void subtractOneFromZero() {
        assertEquals("-1", calc("sub(0,1)"));
    }

    @Test
    public void subtractZeros() {
        assertEquals("0", calc("sub(0,0)"));
    }

    @Test
    public void subtractNegOneFromNegOne() {
        assertEquals("2", calc("sub(1,-1)"));
    }

    @Test
    public void subtractOneFromNegOne() {
        assertEquals("-2", calc("sub(-1,1)"));
    }

    @Test
    public void subtractTwoNegsWithCarry() {
        assertEquals("-435", calc("sub(-626, -191)"));
    }

    @Test
    public void subtractSingleDigits() {
        assertEquals("3", calc("sub(8,5)"));
    }

    @Test
    public void subtractWithBorrow() {
        assertEquals("8", calc("sub(17,9)"));
    }

    @Test
    public void subtractTwoDigits() {
        assertEquals("39", calc("sub(58,19)"));
    }

    @Test
    public void subtractTwoDigitsWithBorrow() {
        assertEquals("89", calc("sub(132,43)"));
    }

    @Test
    public void subtractThreeAndTwoDigits() {
        assertEquals("276", calc("sub(301,25)"));
    }

    @Test
    public void subtractThreeAndTwoDigitsWithBorrow() {
        assertEquals("375", calc("sub(439,64)"));
    }
    
    @Test
    public void subtractOneFromMaxPlusOne() {
        String result = calc(String.format("sub(%d,1)", Integer.MAX_VALUE + 1L));
        assertEquals("2147483647", result);
    }

    @Test
    public void subtractTwoFromMaxPlusOne() {
        String result = calc(String.format("sub(%d,2)", Integer.MAX_VALUE + 1L));
        assertEquals("2147483646", result);
    }

    @Test
    public void subtractOneFromMin() {
        String result = calc(String.format("sub(%d,1)", Integer.MIN_VALUE));
        assertEquals("-2147483649", result);
    }

    @Test
    public void subtractTwoFromMin() {
        String result = calc(String.format("sub(%d,2)", Integer.MIN_VALUE));
        assertEquals("-2147483650", result);
    }

    @Test
    public void subtractFractionalValues() {
        assertEquals("0.0571428571428571428571428571428571", calc("sub(div(1,5), div(1,7))"));
    }

    @Test
    public void subtractFractionalNegValues() {
        assertEquals("-0.0571428571428571428571428571428571", calc("sub(div(1,-5), div(-1,7))"));
    }

    @Test
    public void subtractNestedTwoLevels() {
        assertEquals("-86", calc("sub(sub(34,89),sub(72,41))"));
    }

    String exprThreeLevelsA = "sub(sub(sub(34,89),sub(72,41)),sub(sub(30,74),sub(82,56)))";
    @Test
    public void subtractNestedThreeLevelsA() {
        assertEquals("-16", calc(exprThreeLevelsA));
    }


    String exprThreeLevelsB = "sub(sub(sub(87,31),sub(45,19)),sub(sub(45,98),sub(67,54)))";
    @Test
    public void subtractNestedThreeLevelsB() {
        assertEquals("96", calc(exprThreeLevelsB));
    }

    @Test
    public void subtractNestedFourLevels() {
        String expr = String.format("sub(%s,%s)", exprThreeLevelsA, exprThreeLevelsB);
        assertEquals("-112", calc(expr));
    }
}
