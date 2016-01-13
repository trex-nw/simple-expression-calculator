#!/bin/bash
jar_file=simple.expression.calculator-1.0-SNAPSHOT.jar
main_class=com.acme.calculator.main.Startup
log_lvl=debug
if [ $# -ne 1 ]; then
    echo "usage: $0 \"[expression to calculate]\""
    echo "example: $0 \"let(a, 5, add(a, a))\""
    exit
fi
param=$1
java -cp .:$jar_file $main_class "$param"
