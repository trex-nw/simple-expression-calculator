package com.acme.calculator.main;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by pturcotte on 1/10/16.
 */
public class CalculatorAdditionTests extends CalculatorTestSetup {
    @Test
    public void addErrorFunctionTypo() {
        String expr = "addd(12,9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error: invalid use of function or variable: 'addd'"));
    }

    @Test
    public void addErrorMissingClosingParens() {
        String expr = "add(12,9";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing ')'"));
        assertTrue(result.contains("on line 1, position 8"));
    }

    @Test
    public void addErrorMissingStartingParens() {
        String expr = "add 12,9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing '('"));
        assertTrue(result.contains("on line 1, position 4"));
    }

    @Test
    public void addErrorMissingClosingParensOneCharLonger() {
        String expr = "add(12,19";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing ')'"));
        assertTrue(result.contains("on line 1, position 9"));
    }

    @Test
    public void addErrorMissingComma() {
        String expr = "add(12 9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing ','"));
        assertTrue(result.contains("on line 1, position 7"));
    }

    @Test
    public void addErrorSurplusComma() {
        String expr = "add(123,,9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'no viable alternative at input ','"));
        assertTrue(result.contains("on line 1, position 8"));
    }

    @Test
    public void addErrorWhitespaceInOperation() {
        String expr = "a dd(12,9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error: invalid use of function or variable: 'a'"));
    }

    @Test
    public void addWithWhitespace() {
        assertEquals("21", calc("  add  (\t12 , \n\n 9  )"));
    }

    @Test
    public void addOneAndOne() {
        assertEquals("2", calc("add(1,1)"));
    }

    @Test
    public void addZero() {
        assertEquals("1", calc("add(1,0)"));
    }

    @Test
    public void addZeros() {
        assertEquals("0", calc("add(0,0)"));
    }

    @Test
    public void addOneAndNegativeOne() {
        assertEquals("0", calc("add(1,-1)"));
    }

    @Test
    public void addNegOneAndOne() {
        assertEquals("0", calc("add(-1,1)"));
    }

    @Test
    public void addTwoNegsWithCarry() {
        assertEquals("-626", calc("add(-191,-435)"));
    }

    @Test
    public void addSingleDigitsWithCarry() {
        assertEquals("11", calc("add(9,2)"));
    }

    @Test
    public void addTwoDigits() {
        assertEquals("77", calc("add(19,58)"));
    }

    @Test
    public void addTwoDigitsWithCarry() {
        assertEquals("132", calc("add(89,43)"));
    }

    @Test
    public void addTwoAndThreeDigits() {
        assertEquals("325", calc("add(25,300)"));
    }

    @Test
    public void addTwoAndThreeDigitsWithCarry() {
        assertEquals("424", calc("add(25,399)"));
    }

    @Test
    public void addOneToMax() {
        String expr = String.format("add(%d,1)", Integer.MAX_VALUE);
        assertEquals("2147483648", calc(expr));
    }

    @Test
    public void addTwoToMax() {
        String expr = String.format("add(%d,2)", Integer.MAX_VALUE);
        assertEquals("2147483649", calc(expr));
    }

    @Test
    public void addNegOneToMin() {
        String expr = String.format("add(%d,-1)", Integer.MIN_VALUE);
        assertEquals("-2147483649", calc(expr));
    }

    @Test
    public void addNegTwoToMin() {
        String expr = String.format("add(%d,-2)", Integer.MIN_VALUE);
        assertEquals("-2147483650", calc(expr));
    }

    @Test
    public void addDoubleMax() {
        String expr = String.format("add(%d,%d)", Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals("4294967294", calc(expr));
    }

    @Test
    public void addDoubleMin() {
        String expr = String.format("add(%d,%d)", Integer.MIN_VALUE, Integer.MIN_VALUE);
        assertEquals("-4294967296", calc(expr));
    }

    @Test
    public void addDoubleMaxSquared() {
        Long squared = (long)Integer.MAX_VALUE * Integer.MAX_VALUE;
        String expr = String.format("add(%d,%d)", squared, squared);
        assertEquals("9223372028264841218", calc(expr));
    }

    @Test
    public void addFractionalValues() {
        assertEquals("0.3428571428571428571428571428571429", calc("add(div(1,5), div(1,7))"));
    }

    @Test
    public void addFractionalNegValues() {
        assertEquals("-0.4857142857142857142857142857142857", calc("add(div(-1,5), div(2,-7))"));
    }

    @Test
    public void addNestedTwoLevels() {
        assertEquals("236", calc("add(add(34,89),add(72,41))"));
    }

    String exprThreeLevelsA = "add(add(add(34,89),add(72,41)),add(add(30,74),add(82,56)))";
    @Test
    public void addNestedThreeLevelsA() {
        assertEquals("478", calc(exprThreeLevelsA));
    }


    String exprThreeLevelsB = "add(add(add(87,31),add(45,19)),add(add(45,98),add(67,54)))";
    @Test
    public void addNestedThreeLevelsB() {
        assertEquals("446", calc(exprThreeLevelsB));
    }

    @Test
    public void addNestedFourLevels() {
        String expr = String.format("add(%s,%s)", exprThreeLevelsA, exprThreeLevelsB);
        assertEquals("924", calc(expr));
    }
}
