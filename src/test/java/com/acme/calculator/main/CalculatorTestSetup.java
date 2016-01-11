package com.acme.calculator.main;

import org.junit.Before;

import java.math.MathContext;

/**
 * Created by pturcotte on 1/10/16.
 */
public class CalculatorTestSetup {
    Calculator calculator;

    @Before
    public void init() {
        calculator = new Calculator(MathContext.DECIMAL128);
    }


    String calc(String expression) {
        return calculator.calculate(expression);
    }
}
