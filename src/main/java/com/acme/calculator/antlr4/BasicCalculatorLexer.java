// Generated from BasicCalculator.g4 by ANTLR 4.5.1

package com.acme.calculator.antlr4;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BasicCalculatorLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LET=1, ADD=2, SUBTRACT=3, MULTIPLY=4, DIVIDE=5, INTEGER=6, OPEN_PARENS=7, 
		CLOSE_PARENS=8, COMMA=9, VARIABLE=10, IGNORE_WHITE_SPACE=11;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"LET", "ADD", "SUBTRACT", "MULTIPLY", "DIVIDE", "INTEGER", "OPEN_PARENS", 
		"CLOSE_PARENS", "COMMA", "VARIABLE", "IGNORE_WHITE_SPACE"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'let'", "'add'", "'sub'", "'mult'", "'div'", null, "'('", "')'", 
		"','"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "LET", "ADD", "SUBTRACT", "MULTIPLY", "DIVIDE", "INTEGER", "OPEN_PARENS", 
		"CLOSE_PARENS", "COMMA", "VARIABLE", "IGNORE_WHITE_SPACE"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public BasicCalculatorLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "BasicCalculator.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\rH\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3"+
		"\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\5\7\60\n\7\3\7\6\7\63\n\7\r\7\16\7\64\3"+
		"\b\3\b\3\t\3\t\3\n\3\n\3\13\6\13>\n\13\r\13\16\13?\3\f\6\fC\n\f\r\f\16"+
		"\fD\3\f\3\f\2\2\r\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\3"+
		"\2\5\3\2\62;\4\2C\\c|\5\2\13\f\17\17\"\"K\2\3\3\2\2\2\2\5\3\2\2\2\2\7"+
		"\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2"+
		"\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\3\31\3\2\2\2\5\35\3\2\2\2\7"+
		"!\3\2\2\2\t%\3\2\2\2\13*\3\2\2\2\r/\3\2\2\2\17\66\3\2\2\2\218\3\2\2\2"+
		"\23:\3\2\2\2\25=\3\2\2\2\27B\3\2\2\2\31\32\7n\2\2\32\33\7g\2\2\33\34\7"+
		"v\2\2\34\4\3\2\2\2\35\36\7c\2\2\36\37\7f\2\2\37 \7f\2\2 \6\3\2\2\2!\""+
		"\7u\2\2\"#\7w\2\2#$\7d\2\2$\b\3\2\2\2%&\7o\2\2&\'\7w\2\2\'(\7n\2\2()\7"+
		"v\2\2)\n\3\2\2\2*+\7f\2\2+,\7k\2\2,-\7x\2\2-\f\3\2\2\2.\60\7/\2\2/.\3"+
		"\2\2\2/\60\3\2\2\2\60\62\3\2\2\2\61\63\t\2\2\2\62\61\3\2\2\2\63\64\3\2"+
		"\2\2\64\62\3\2\2\2\64\65\3\2\2\2\65\16\3\2\2\2\66\67\7*\2\2\67\20\3\2"+
		"\2\289\7+\2\29\22\3\2\2\2:;\7.\2\2;\24\3\2\2\2<>\t\3\2\2=<\3\2\2\2>?\3"+
		"\2\2\2?=\3\2\2\2?@\3\2\2\2@\26\3\2\2\2AC\t\4\2\2BA\3\2\2\2CD\3\2\2\2D"+
		"B\3\2\2\2DE\3\2\2\2EF\3\2\2\2FG\b\f\2\2G\30\3\2\2\2\7\2/\64?D\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}