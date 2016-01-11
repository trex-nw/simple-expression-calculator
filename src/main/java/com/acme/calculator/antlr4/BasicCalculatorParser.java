// Generated from BasicCalculator.g4 by ANTLR 4.5.1

package com.acme.calculator.antlr4;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BasicCalculatorParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		LET=1, ADD=2, SUBTRACT=3, MULTIPLY=4, DIVIDE=5, INTEGER=6, OPEN_PARENS=7, 
		CLOSE_PARENS=8, COMMA=9, VARIABLE=10, IGNORE_WHITE_SPACE=11;
	public static final int
		RULE_calc = 0, RULE_expr = 1, RULE_assignment = 2, RULE_function = 3;
	public static final String[] ruleNames = {
		"calc", "expr", "assignment", "function"
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

	@Override
	public String getGrammarFileName() { return "BasicCalculator.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public BasicCalculatorParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class CalcContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public CalcContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_calc; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicCalculatorListener ) ((BasicCalculatorListener)listener).enterCalc(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicCalculatorListener ) ((BasicCalculatorListener)listener).exitCalc(this);
		}
	}

	public final CalcContext calc() throws RecognitionException {
		CalcContext _localctx = new CalcContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_calc);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(8);
			expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public TerminalNode OPEN_PARENS() { return getToken(BasicCalculatorParser.OPEN_PARENS, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(BasicCalculatorParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(BasicCalculatorParser.COMMA, i);
		}
		public TerminalNode CLOSE_PARENS() { return getToken(BasicCalculatorParser.CLOSE_PARENS, 0); }
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public TerminalNode VARIABLE() { return getToken(BasicCalculatorParser.VARIABLE, 0); }
		public TerminalNode INTEGER() { return getToken(BasicCalculatorParser.INTEGER, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicCalculatorListener ) ((BasicCalculatorListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicCalculatorListener ) ((BasicCalculatorListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_expr);
		try {
			setState(28);
			switch (_input.LA(1)) {
			case ADD:
			case SUBTRACT:
			case MULTIPLY:
			case DIVIDE:
				enterOuterAlt(_localctx, 1);
				{
				setState(10);
				function();
				setState(11);
				match(OPEN_PARENS);
				setState(12);
				expr();
				setState(13);
				match(COMMA);
				setState(14);
				expr();
				setState(15);
				match(CLOSE_PARENS);
				}
				break;
			case LET:
				enterOuterAlt(_localctx, 2);
				{
				setState(17);
				assignment();
				setState(18);
				match(OPEN_PARENS);
				setState(19);
				match(VARIABLE);
				setState(20);
				match(COMMA);
				setState(21);
				expr();
				setState(22);
				match(COMMA);
				setState(23);
				expr();
				setState(24);
				match(CLOSE_PARENS);
				}
				break;
			case VARIABLE:
				enterOuterAlt(_localctx, 3);
				{
				setState(26);
				match(VARIABLE);
				}
				break;
			case INTEGER:
				enterOuterAlt(_localctx, 4);
				{
				setState(27);
				match(INTEGER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentContext extends ParserRuleContext {
		public TerminalNode LET() { return getToken(BasicCalculatorParser.LET, 0); }
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicCalculatorListener ) ((BasicCalculatorListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicCalculatorListener ) ((BasicCalculatorListener)listener).exitAssignment(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			match(LET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionContext extends ParserRuleContext {
		public TerminalNode ADD() { return getToken(BasicCalculatorParser.ADD, 0); }
		public TerminalNode SUBTRACT() { return getToken(BasicCalculatorParser.SUBTRACT, 0); }
		public TerminalNode MULTIPLY() { return getToken(BasicCalculatorParser.MULTIPLY, 0); }
		public TerminalNode DIVIDE() { return getToken(BasicCalculatorParser.DIVIDE, 0); }
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BasicCalculatorListener ) ((BasicCalculatorListener)listener).enterFunction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BasicCalculatorListener ) ((BasicCalculatorListener)listener).exitFunction(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << ADD) | (1L << SUBTRACT) | (1L << MULTIPLY) | (1L << DIVIDE))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\r%\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\37\n\3\3\4\3\4\3\5\3\5\3\5\2\2\6\2\4"+
		"\6\b\2\3\3\2\4\7#\2\n\3\2\2\2\4\36\3\2\2\2\6 \3\2\2\2\b\"\3\2\2\2\n\13"+
		"\5\4\3\2\13\3\3\2\2\2\f\r\5\b\5\2\r\16\7\t\2\2\16\17\5\4\3\2\17\20\7\13"+
		"\2\2\20\21\5\4\3\2\21\22\7\n\2\2\22\37\3\2\2\2\23\24\5\6\4\2\24\25\7\t"+
		"\2\2\25\26\7\f\2\2\26\27\7\13\2\2\27\30\5\4\3\2\30\31\7\13\2\2\31\32\5"+
		"\4\3\2\32\33\7\n\2\2\33\37\3\2\2\2\34\37\7\f\2\2\35\37\7\b\2\2\36\f\3"+
		"\2\2\2\36\23\3\2\2\2\36\34\3\2\2\2\36\35\3\2\2\2\37\5\3\2\2\2 !\7\3\2"+
		"\2!\7\3\2\2\2\"#\t\2\2\2#\t\3\2\2\2\3\36";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}