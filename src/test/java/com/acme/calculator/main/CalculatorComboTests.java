package com.acme.calculator.main;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by pturcotte on 1/10/16.
 */
public class CalculatorComboTests extends CalculatorTestSetup {
    @Test
    public void addOneAndOne() {
        String result = calc("add(1,1)");
        assertEquals("2", result);
    }

}
