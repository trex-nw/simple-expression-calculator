#!/bin/bash
main_class=com.acme.calculator.main.Startup
log_lvl=debug
if [ $# -ne 1 ]; then
    echo "usage: $0 \"[expression to calculate]\""
    echo "example: $0 \"let(a, 5, add(a, a))\""
    exit
fi
cmd="java $main_class \"$1\""
echo $cmd
$cmd
