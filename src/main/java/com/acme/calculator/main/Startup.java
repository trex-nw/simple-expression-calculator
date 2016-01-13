package com.acme.calculator.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.MathContext;

/**
 * Created by pturcotte on 1/8/16.
 */
public class Startup {
    private static final Logger logger = LogManager.getLogger(Startup.class);

    public static void main(final String[] args) {
        if (args.length != 1) {
            final String className = Startup.class.getName();
            System.out.println(String.format("Unexpected number of arguments (%d)", args.length));
            System.out.println(String.format("usage:\n\tjava %s \"[expression]\" ", className));
            System.out.println(String.format("example:\n\tjava %s \"let(a,7,add(a,div(10,2)))\"", className));
            System.exit(-1);
        }

        // if needed / desired, could first remove all whitespace from the expression string:
        // final String exprWithWhitespaceRemoved = args[0].replaceAll("\\s+", "");

        Calculator parser = new Calculator(MathContext.DECIMAL128);

        System.out.println(parser.calculate(args[0]));
    }
}
