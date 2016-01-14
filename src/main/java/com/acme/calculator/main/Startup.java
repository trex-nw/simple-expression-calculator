package com.acme.calculator.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.MathContext;
import java.util.Arrays;

/**
 * Entry point for this program to calculate the value of an expression provided on the command-line.
 *
 * The calculated result is printed to stdout.
 *
 * Future option: could add more cmd-line parameters to allow passing the desired precision & RoundingMode.
 *
 * Created by pturcotte on 1/8/16.
 */
public class Startup {
    private static final Logger logger = LogManager.getLogger(Startup.class);
    static String NEWLINE = System.lineSeparator();

    /**
     * main() needed to run this program from the command line.
     *
     * @param args command-line arguments passed to this program
     */
    public static void main(final String[] args) {
        logger.info("main()");
        logger.debug("main(): number of arguments: {}", args.length);
        logger.debug("main(): arguments: {}", Arrays.asList(args));

        // if needed / desired, could first remove all whitespace from the expression string:
        // final String exprWithWhitespaceRemoved = args[0].replaceAll("\\s+", "");

        if (argCountValid(args)) {
            Calculator parser = new Calculator(MathContext.DECIMAL128);
            final String expression = args[0];
            final String result = parser.calculate(expression);
            logger.debug("Expression: '{}' -> result: '{}'", expression, result);
            System.out.println(result);
        }
    }

    /**
     * Verifies that the expected number of command-line arguments were
     * passed in and prints usage information to stdout if an invalid
     * number of arguments is present.
     *
     * @param args command-line arguments passed to this program
     * @return boolean - whether expected number of parameters are present
     */
    static boolean argCountValid(String[] args) {
        boolean proceed = true;
        if (args.length != 1) {
            final String className = Startup.class.getName();
            System.out.println(String.format("Unexpected number of arguments (%d)", args.length));
            System.out.println(String.format("usage:%s\tjava %s \"[expression]\" ",NEWLINE, className));
            System.out.println(String.format("example:%s\tjava %s \"let(a,7,add(a,div(10,2)))\"",NEWLINE, className));
            proceed = false;
        }
        return proceed;
    }
}
