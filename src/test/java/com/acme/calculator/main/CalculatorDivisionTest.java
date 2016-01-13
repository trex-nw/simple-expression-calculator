package com.acme.calculator.main;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by pturcotte on 1/11/16.
 */
public class CalculatorDivisionTest extends CalculatorTestSetup {
    @Test
    public void divideErrorFunctionTypo() {
        String expr = "vid(12,9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error: invalid use of function or variable: 'vid'"));
    }

    @Test
    public void divideErrorMissingClosingParens() {
        String expr = "div(12,9";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing ')'"));
        assertTrue(result.contains("on line 1, position 8"));
    }

    @Test
    public void divideErrorMissingStartingParens() {
        String expr = "div 12,9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing '('"));
        assertTrue(result.contains("on line 1, position 4"));
    }

    @Test
    public void divideErrorMissingClosingParensOneCharLonger() {
        String expr = "div(12,19";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing ')'"));
        assertTrue(result.contains("on line 1, position 9"));
    }

    @Test
    public void divideErrorMissingComma() {
        String expr = "div(12 9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing ','"));
        assertTrue(result.contains("on line 1, position 7"));
    }

    @Test
    public void divideErrorSurplusComma() {
        String expr = "div(123,,9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'no viable alternative at input ','"));
        assertTrue(result.contains("on line 1, position 8"));
    }

    @Test
    public void divideErrorWhitespaceInOperation() {
        String expr = "d iv(12,9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error: invalid use of function or variable: 'd'"));
    }

    @Test
    public void divideErrorDecimal() {
        String expr = "div(12,.1)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'token recognition error at: '.'"));
        assertTrue(result.contains("on line 1, position 7"));
    }

    @Test
    public void divideWithWhitespace() {
        assertEquals("1.333333333333333333333333333333333", calc("  div  (\t12 , \n\n 9  )"));
    }

    @Test
    public void divideOneByOne() {
        assertEquals("1", calc("div(1,1)"));
    }

    @Test
    public void divideByZero() {
        assertEquals("Problem parsing/calculating expression: 'div(1,0)' (java.lang.ArithmeticException: Division by zero)", calc("div(1,0)"));
    }

    @Test
    public void divideZeroByZero() {
        assertEquals("Problem parsing/calculating expression: 'div(0,0)' (java.lang.ArithmeticException: Division undefined)", calc("div(0,0)"));
    }

    @Test
    public void divideOneByNegativeOne() {
        assertEquals("-1", calc("div(1,-1)"));
    }

    @Test
    public void divideNegOneByOne() {
        assertEquals("-1", calc("div(-1,1)"));
    }

    @Test
    public void divideTwoNegs() {
        assertEquals("0.439080459770114942528735632183908", calc("div(-191,-435)"));
    }

    @Test
    public void divideSingleDigits() {
        assertEquals("4.5", calc("div(9,2)"));
    }

    @Test
    public void divideTwoDigits() {
        assertEquals("0.3275862068965517241379310344827586", calc("div(19,58)"));
    }

    @Test
    public void divideTwoByThreeDigits() {
        assertEquals("0.06265664160401002506265664160401003", calc("div(25,399)"));
    }

    @Test
    public void divideMaxByOne() {
        String expr = String.format("div(%d,1)", Integer.MAX_VALUE);
        assertEquals("2147483647", calc(expr));
    }

    @Test
    public void divideMaxByTwo() {
        String expr = String.format("div(%d,2)", Integer.MAX_VALUE);
        assertEquals("1073741823.5", calc(expr));
    }

    @Test
    public void divideMaxByOneOverMax() {
        String expr = String.format("div(%d,div(1,%d))", Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals("4611686014132420609", calc(expr));
    }

    @Test
    public void divideMinByNegOne() {
        String expr = String.format("div(%d,-1)", Integer.MIN_VALUE);
        assertEquals("2147483648", calc(expr));
    }

    @Test
    public void divideMinByNegTwo() {
        String expr = String.format("div(%d,-2)", Integer.MIN_VALUE);
        assertEquals("1073741824", calc(expr));
    }

    @Test
    public void divideMaxByMax() {
        String expr = String.format("div(%d,%d)", Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals("1", calc(expr));
    }

    @Test
    public void divideMinByMin() {
        String expr = String.format("div(%d,%d)", Integer.MIN_VALUE, Integer.MIN_VALUE);
        assertEquals("1", calc(expr));
    }

    @Test
    public void divideMaxSquaredByItself() {
        Long squared = (long)Integer.MAX_VALUE * Integer.MAX_VALUE;
        String expr = String.format("div(%d,%d)", squared, squared);
        assertEquals("1", calc(expr));
    }

    @Test
    public void divideSixteenthByItself() {
        assertEquals("1", calc("div(div(1,16), div(1,16))"));
    }

    @Test
    public void flipFraction() {
        assertEquals("3", calc("div(1, div(1,3))"));
    }

    @Test
    public void divideTwoSevenths() {
        assertEquals("1.999999999999999999999999999999999", calc("div(div(2,7), div(1,7))"));
    }

    @Test
    public void divideFractionalNegValues() {
        assertEquals("0.7", calc("div(div(-1,5), div(2,-7))"));
    }

    @Test
    public void divideNestedTwoLevels() {
        assertEquals("0.2175405742821473158551810237203496", calc("div(div(34,89),div(72,41))"));
    }

    String exprThreeLevelsA = "div(div(div(34,89),div(72,41)),div(div(30,74),div(82,56)))";
    @Test
    public void divideNestedThreeLevelsA() {
        assertEquals("0.7857358361571844717912133642470721", calc(exprThreeLevelsA));
    }

    String exprThreeLevelsB = "div(div(div(87,31),div(45,19)),div(div(45,98),div(67,54)))";
    @Test
    public void divideNestedThreeLevelsB() {
        assertEquals("3.201792999690251781052258949511042", calc(exprThreeLevelsB));
    }

    @Test
    public void divideNestedFourLevels() {
        String expr = String.format("div(%s,%s)", exprThreeLevelsA, exprThreeLevelsB);
        assertEquals("0.2454049453644249383201261575002995", calc(expr));
    }
}
