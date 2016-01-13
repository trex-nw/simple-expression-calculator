package com.acme.calculator.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.MathContext;
import java.util.Arrays;

/**
 * Created by pturcotte on 1/8/16.
 */
public class Startup {
    private static final Logger logger = LogManager.getLogger(Startup.class);

    public static void main(final String[] args) {
        logger.info("main()");
        logger.debug("main(): number of arguments: {}", args.length);
        logger.debug("main(): arguments: {}", Arrays.asList(args));

        // if needed / desired, could first remove all whitespace from the expression string:
        // final String exprWithWhitespaceRemoved = args[0].replaceAll("\\s+", "");

        // Future option: could add more cmd-line parameters to allow passing the desired precision & RoundingMode

        if (argCountValid(args)) {
            Calculator parser = new Calculator(MathContext.DECIMAL128);
            final String expression = args[0];
            final String result = parser.calculate(expression);
            logger.debug("Expression: '{}' -> result: '{}'", expression, result);
            System.out.println(result);
        }
    }

    static boolean argCountValid(String[] args) {
        boolean proceed = true;
        if (args.length != 1) {
            final String className = Startup.class.getName();
            System.out.println(String.format("Unexpected number of arguments (%d)", args.length));
            System.out.println(String.format("usage:\n\tjava %s \"[expression]\" ", className));
            System.out.println(String.format("example:\n\tjava %s \"let(a,7,add(a,div(10,2)))\"", className));
            proceed = false;
        }
        return proceed;
    }
}
