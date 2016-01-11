package com.acme.calculator.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.MathContext;

/**
 * Created by pturcotte on 1/8/16.
 */
public class Startup {
    private static final Logger logger = LogManager.getLogger(Startup.class);

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
}
