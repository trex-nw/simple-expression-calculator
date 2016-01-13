#### Simple Expression Calculator

##### Brief Description
---
This is a basic project using ANTLR4 to calculate the value of a single-line expression such as:
<p/>
    * let(a, let(b, 10, add(b,b)), let(b, 20, add(a, b))
<p/>
which evaluates to 40
<p/>

##### Building this project
---
git clone https://github.com/trex-nw/simple-expression-calculator.git <p/>
mvn clean install<p/>
* this project was built using Maven3<p/>

##### Running the code
---
cd target/deploy<p/>
chmod 755 ./run.sh<p/>
./run.sh "let(a,99,mult(a,11))"<p/>
1089 will be printed<p/>
<p/>
./logs/calculator.log will be created and will contain any text logged.<p/>
<p/>
./run.sh to see usage:<p/>
* usage: ./run.sh "[expression to calculate]" -log_lvl DEBUG|INFO|ERROR|DEFAULT
<p/>

##### Expression Syntax
---
The expression syntax supports basic arithmetic, as well as a "let" operation where an expression or number can 
be assigned to a variable.<p/>
 
The arithmetic operations supported are:
* div(val1, val2): divides val1 by val2
* mult(val1, val2): multiplies val1 and val2
* add(val1, val2): adds val1 and val2
* sub(val1, val2): subtracts val2 from val1

where val1 and val2 can be an integer or an expression that evaluates to a number.
 
The expected input integer range is Integer.MIN_VALUE to Integer.MAX_VALUE.
 
Digits of precision and rounding behavior can be specified when constructing 
the Calculator class by using a desired MathContext instance.
 
Arithmetic examples (using DECIMAL128): 
* div(9, 3) -&gt; 3
* add(20, div(9, 3)) -&gt; 23
* sub(-10, -12) -&gt; 2
* add(2147483647, 1) -&gt; 2147483648
* div(5, 9) -&gt; 0.5555555555555555555555555555555556
* div(1, 0) -&gt; "Problem parsing/calculating expression: 'div(1, 0)' (java.lang.ArithmeticException: Division by zero)"
    
The syntax of the "let" operation is: _let(varName, val_expr, expr)_
* varName: the name of the variable one more of characters: a-z|A-Z
* val_expr: a number or expression
* expr: an expression using the defined variable

The current scope is to support only these operations above, though it would be simple to add a modulo operation for example.

Examples incorporating the "let" operation:
* let(a, 5, add(a, 9)) -&gt; 14
* let(a, let(b, 10, add(b,b)), let(b, 20, add(a, b)) -&gt; 40

##### Plugins
---
Plugins included in the pom.xml for this project, with some basic configuration 
(i.e. to exclude generated ANTLR files as needed):
* PMD https://maven.apache.org/plugins/maven-pmd-plugin/
* checkstyle https://maven.apache.org/plugins/maven-checkstyle-plugin/
* findbugs http://gleclaire.github.io/findbugs-maven-plugin/
* Javadoc https://maven.apache.org/plugins/maven-javadoc-plugin/
    * _mvn site_ will generate reports to target/site/ for the plugins above
* JaCoCo - painful to configure, but finally found a good example at: http://www.petrikainulainen.net/programming/maven/creating-code-coverage-reports-for-unit-and-integration-tests-with-the-jacoco-maven-plugin/
    * _mvn clean test_ - runs the unit tests &amp; creates the code coverage report under target/site/jacoco-ut/.
* Shade plugin (builds uber-jar) https://maven.apache.org/plugins/maven-shade-plugin/
    * _mvn clean install_ - will create the combined jar file
    
##### Known limitations
---
* only integer values are accepted in the expression string
    * decimal values could be easily supported by updating the NUMBER definition in the BasicCalculator.g4 grammar file (with corresponding updates to unit tests)
* variable names are alphabetic only 
    * alpha-numeric variable names could be easily supported by updating the VARIABLE definition in the BasicCalculator.g4 grammar file (with corresponding updates to unit tests)
* The code to do calculations and determine the final value of the supplied expression string is recursive.
    * With default JVM settings, a StackOverflowError will occur at ~11 levels of nested expressions.
    * See the unit test assign12LevelsDeepCausesStackOverflow() in CalculatorAssignmentTest.
    * If necessary, a StackOverflowError can be alleviated by increasing the stack size:
        * use the -Xss flag to increase the stack size (-Xss&lt;size&gt;[g|G|m|M|k|K])
  * StackOverflowErrors could also be prevented if necessary by refactoring the code to not use recursion.

##### Testing / debugging changes to the BasicCalculator.g4 grammar file
---
* Per "The Definitive ANTLR 4 Reference" by Terrence Parr:<p/>
"ANTLR provides a flexible testing tool in the runtime library called TestRig.<p/>
It can display lots of information about how a recognizer matches input from a 
file or standard input. TestRig uses Java reflection to invoke compiled recognizers."

Note: there may well be room for improvement in this code (suggestions are welcome), since the functionality 
is based on a day of effort to find, understand the basics of, and apply ANTLR4.<p/>
All of the ancillary project configuration, unit tests and cleanup, etc. has taken some additional effort.

This project was made possible by the ANTLR framework from Terence Parr (http://www.antlr.org/) and the example at
http://niels.nu/blog/2015/antlr-is-awesome.html by Niels Dommerholt.

