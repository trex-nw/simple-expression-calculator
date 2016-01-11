grammar BasicCalculator;

@header {
package com.acme.calculator.antlr4;
}

calc: expr;

expr: function OPEN_PARENS expr COMMA expr CLOSE_PARENS
    | assignment OPEN_PARENS VARIABLE COMMA expr COMMA expr CLOSE_PARENS
    | VARIABLE
    | INTEGER
;

assignment: LET;

function: ADD
          | SUBTRACT
          | MULTIPLY
          | DIVIDE
;

LET:        'let';
ADD:        'add';
SUBTRACT:   'sub';
MULTIPLY:   'mult';
DIVIDE:     'div';

INTEGER: '-'? [0-9]+; // one or more numbers with optional minus sign
OPEN_PARENS: '(';
CLOSE_PARENS: ')';
COMMA: ',';
VARIABLE: ('a'..'z'|'A'..'Z')+;

IGNORE_WHITE_SPACE: [ \t\r\n]+ -> skip; // ignore specified characters
