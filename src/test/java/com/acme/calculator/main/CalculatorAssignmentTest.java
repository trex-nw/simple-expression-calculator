package com.acme.calculator.main;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by pturcotte on 1/12/16.
 */
public class CalculatorAssignmentTest extends CalculatorTestSetup {

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
    public void assignErrorDecimal() {
        String expr = "let(a,99.9,add(a,11))";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'token recognition error at: '.'"));
        assertTrue(result.contains("on line 1, position 8"));
    }

    @Test
    public void assignErrorDecimalInExpr() {
        String expr = "let(a,99,add(a,1.9))";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'token recognition error at: '.'"));
        assertTrue(result.contains("on line 1, position 16"));
    }

    @Test
    public void assignErrorInvalidVariableNameNumeric() {
        String expr = "let(5,12,add(5,5)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'mismatched input '5' expecting VARIABLE'"));
        assertTrue(result.contains("on line 1, position 4"));
    }

    @Test
    public void assignErrorInvalidVariableNameAlphaNumeric() {
        String expr = "let(a1,12,add(a1,a1)";
        String result = calc(expr);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains(expr));
        assertTrue(result.contains("java.lang.IllegalArgumentException: Syntax error in expression: 'extraneous input '1' expecting ','"));
        assertTrue(result.contains("on line 1, position 5"));
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

    @Test
    public void assignCapitalLetterFirst() {
        assertEquals("81", calc("let(Aa,9,mult(Aa, Aa))"));
    }

    @Test
    public void assignLongVarName() {
        assignLongerVarNameReps(1);
    }

    @Test
    public void assignLongerVarNameX100() {
        assignLongerVarNameReps(100);
    }

    @Test
    public void assignLongerVarNameX1000() {
        assignLongerVarNameReps(1000);
    }

    @Test
    public void assignLongerVarNameX100K() {
        assignLongerVarNameReps(100000);
    }

    // 1,000,000 works fine, but starts to impact build time (an extra second delay) and doesn't seem necesarry
//    @Test
//    public void assignLongerVarNameX1M() {
//        assignLongerVarNameReps(1000000);
//    }

    @Test
    public void assignOneLevelDeep() {
        assertEquals("17", assignLevelsDeep(1));
    }

    @Test
    public void assign5LevelsDeep() {
        assertEquals("167", assignLevelsDeep(5));
    }

// Not reliable - this test with 10 levels of depth sometimes results in a StackOverflowError on the Travis CI build server
//    @Test
//    public void assign10LevelsDeep() {
//        assertEquals("5127", assignLevelsDeep(10));
//    }

    @Test
    // 11 levels deep also causes a StackOverflowError, but going a little deeper to help
    // protect against different default build conditions
    // (i.e. if a build environment happens to allocate more stack memory)
    //
    // The JVM stack size can be increased using the -Xss flag. (-Xss<size>[g|G|m|M|k|K])
    // This flag can be specified either via the project’s configuration, or via the command line.
    public void assign12LevelsDeepCausesStackOverflow() {
        String result = assignLevelsDeep(12);
        assertTrue(result.startsWith(Calculator.ERROR_PREFIX));
        assertTrue(result.contains("java.lang.StackOverflowError"));
    }

//    @Test
//   this test causes: "java.lang.OutOfMemoryError: Java heap space" when run from an IDE with the default IDE setup
//    public void assign100LevelsDeep() {
//        assertEquals("?", assignLevelsDeep(100));
//    }

    // Note - this method would need an update to be able to work for depth > 26 due to the character
    // arithmetic used.
    // Fyi, a depth of 12 produces an expression string that is 69,636 characters long.
    private String assignLevelsDeep(int depth) {
        String varName = "a";
        String exprFormat = "let(%s,7,add(%s,5))";
        for (int ctr = 0; ctr < depth; ctr++) {
            String var = varName + (char)('a' + ctr);
            exprFormat = String.format(exprFormat, var, exprFormat);
        }
        String expr = String.format(exprFormat,"zzz", "zzz");
        return calc(expr);
    }

    private void assignLongerVarNameReps(int reps) {
        String origVar = "theQuickBrownFoxJumpsOverTheLazyDog";
        StringBuilder builder =  new StringBuilder();
        for (int ctr = 0; ctr < reps; ctr++) {
            builder.append(origVar);
        }
        String var = builder.toString();
        String expr = "let(" + var + ",77,add(" + var + ", " + var + "))";
        assertTrue(expr.length() > origVar.length() * reps);
        assertEquals("154", calc(expr));
    }
}
