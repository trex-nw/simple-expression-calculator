#!/bin/bash
jar_file=simple.expression.calculator-1.0-SNAPSHOT.jar
main_class=com.acme.calculator.main.Startup

showUsageAndExit() {
    echo "usage: $0 \"[expression to calculate]\" -log_lvl DEBUG|INFO|ERROR|DEFAULT"
    echo "example: $0 \"let(a, 5, add(a, a))\" -log_lvl DEBUG"
    echo "example: $0 \"let(a, 5, add(a, a))\""
    exit
}

if [ $# -ne 1 ] && [ $# -ne 3 ]; then
    showUsageAndExit
fi

expr=$1

if [ $# -eq 1 ]; then
    log_lvl=DEFAULT
else
    if [ $2 != "-log_lvl" ]; then
        showUsageAndExit
    else
        log_lvl=$3
    fi
fi


# Perhaps the best way to do this, but provides a quick way to change logging levels via the command-line.
# todo: find how to have log4j2 to use cmd-line option within (just one) XML or properties file
# log4j supported this, but log4j2 doesn't seem to, at least when using an XML config file.

case $log_lvl in
DEFAULT)
    log_file=log4j2.xml
    ;;
DEBUG)
    log_file=log4j2_debug.xml
    ;;
INFO)
    log_file=log4j2_info.xml
    ;;
ERROR)
    log_file=log4j2_error.xml
    ;;
*)
    echo "Log level of: \"$log_lvl\" not recognized."
    showUsageAndExit
esac

log_file_opt="-Dlog4j.configurationFile=$PWD/$log_file"
java -cp .:$jar_file $log_file_opt $main_class "$expr"
