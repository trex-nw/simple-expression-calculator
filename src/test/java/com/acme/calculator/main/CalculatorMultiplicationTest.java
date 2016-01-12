package com.acme.calculator.main;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by pturcotte on 1/11/16.
 */
public class CalculatorMultiplicationTest extends CalculatorTestSetup {
    @Test
    public void multiplyErrorFunctionTypo() {
        String expr = "mul(12,9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error: invalid use of function or variable: 'mul'"));
    }

    @Test
    public void multiplyErrorMissingClosingParens() {
        String expr = "mult(12,9";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing ')'"));
        assertTrue(result.contains("on line 1, position 9"));
    }

    @Test
    public void multiplyErrorMissingStartingParens() {
        String expr = "mult 12,9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing '('"));
        assertTrue(result.contains("on line 1, position 5"));
    }

    @Test
    public void multiplyErrorMissingClosingParensOneCharLonger() {
        String expr = "mult(12,19";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing ')'"));
        assertTrue(result.contains("on line 1, position 10"));
    }

    @Test
    public void multiplyErrorMissingComma() {
        String expr = "mult(12 9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'missing ','"));
        assertTrue(result.contains("on line 1, position 8"));
    }

    @Test
    public void multiplyErrorSurplusComma() {
        String expr = "mult(123,,9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'no viable alternative at input ','"));
        assertTrue(result.contains("on line 1, position 9"));
    }

    @Test
    public void multiplyErrorWhitespaceInOperation() {
        String expr = "m ult(12,9)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error: invalid use of function or variable: 'm'"));
    }

    @Test
    public void multiplyWithWhitespace() {
        assertEquals("108", calc("  mult  (\t12 , \n\n 9  )"));
    }

    @Test
    public void multiplyOneAndOne() {
        assertEquals("1", calc("mult(1,1)"));
    }

    @Test
    public void multiplyZero() {
        assertEquals("0", calc("mult(1,0)"));
    }

    @Test
    public void multiplyZeros() {
        assertEquals("0", calc("mult(0,0)"));
    }

    @Test
    public void multiplyOneAndNegativeOne() {
        assertEquals("-1", calc("mult(1,-1)"));
    }

    @Test
    public void multiplyNegOneAndOne() {
        assertEquals("-1", calc("mult(-1,1)"));
    }

    @Test
    public void multiplyTwoNegsWithCarry() {
        assertEquals("83085", calc("mult(-191,-435)"));
    }

    @Test
    public void multiplySingleDigitsWithCarry() {
        assertEquals("18", calc("mult(9,2)"));
    }

    @Test
    public void multiplyTwoDigits() {
        assertEquals("1102", calc("mult(19,58)"));
    }

    @Test
    public void multiplyTwoAndThreeDigits() {
        assertEquals("9975", calc("mult(25,399)"));
    }

    @Test
    public void multiplyMaxAndOne() {
        String expr = String.format("mult(%d,1)", Integer.MAX_VALUE);
        assertEquals("2147483647", calc(expr));
    }

    @Test
    public void multiplyMaxAndTwo() {
        String expr = String.format("mult(%d,2)", Integer.MAX_VALUE);
        assertEquals("4294967294", calc(expr));
    }

    @Test
    public void multiplyMaxAndOneOverMax() {
        String expr = String.format("mult(%d,div(1,%d))", Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals("0.9999999999999999999999999999999999", calc(expr));
    }

    @Test
    public void multiplyMinAndNegOne() {
        String expr = String.format("mult(%d,-1)", Integer.MIN_VALUE);
        assertEquals("2147483648", calc(expr));
    }

    @Test
    public void multiplyMinAndNegTwo() {
        String expr = String.format("mult(%d,-2)", Integer.MIN_VALUE);
        assertEquals("4294967296", calc(expr));
    }

    @Test
    public void multiplyMaxAndMax() {
        String expr = String.format("mult(%d,%d)", Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals("4611686014132420609", calc(expr));
    }

    @Test
    public void multiplyMinAndMin() {
        String expr = String.format("mult(%d,%d)", Integer.MIN_VALUE, Integer.MIN_VALUE);
        assertEquals("4611686018427387904", calc(expr));
    }

    @Test
    public void squareMaxSquared() {
        Long squared = (long)Integer.MAX_VALUE * Integer.MAX_VALUE;
        String expr = String.format("mult(%d,%d)", squared, squared);
        assertEquals("21.26764789294457273699886026968793E+36", calc(expr));
    }

    @Test
    public void multiplyFractionalValues() {
        assertEquals("0.00390625", calc("mult(div(1,16), div(1,16))"));
    }

    @Test
    public void multiplyFractionalValuesOfWhole() {
        assertEquals("0.9999999999999999999999999999999999", calc("mult(div(1,3), 3)"));
    }

    @Test
    public void multiplyFractionalValuesRepeating() {
        assertEquals("0.0408163265306122448979591836734694", calc("mult(div(2,7), div(1,7))"));
    }

    @Test
    public void multiplyFractionalNegValues() {
        assertEquals("0.05714285714285714285714285714285714", calc("mult(div(-1,5), div(2,-7))"));
    }

    @Test
    public void multiplyNestedTwoLevels() {
        assertEquals("8932752", calc("mult(mult(34,89),mult(72,41))"));
    }

    String exprThreeLevelsA = "mult(mult(mult(34,89),mult(72,41)),mult(mult(30,74),mult(82,56)))";
    @Test
    public void multiplyNestedThreeLevelsA() {
        assertEquals("91062617748480", calc(exprThreeLevelsA));
    }

    String exprThreeLevelsB = "mult(mult(mult(87,31),mult(45,19)),mult(mult(45,98),mult(67,54)))";
    @Test
    public void multiplyNestedThreeLevelsB() {
        assertEquals("36792069180300", calc(exprThreeLevelsB));
    }

    @Test
    public void multiplyNestedFourLevels() {
        String expr = String.format("mult(%s,%s)", exprThreeLevelsA, exprThreeLevelsB);
        assertEquals("3350382131941290785170944000", calc(expr));
    }
}
