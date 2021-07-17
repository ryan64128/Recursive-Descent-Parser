package com.example.demo.compiler;

import com.example.demo.compiler.Token.TokenId;

// Context-Free Grammar Definition
// <program> ->        <statement-list>
// <statement-list> -> <statement> <statement-list> | epsilon
// <statement> ->      ID = <expression> 
// <expression> ->     <term> <term-tail>
// <term-tail> ->      <add-op> <term> <term-tail> | epsilon
// <term> ->           <factor> <factor-tail>
// <factor-tail> ->    <mult-op> <factor> <factor-tail> | epsilon
// <factor> ->         ( <expression> ) | ID | NUMBER
// <add-op> ->         + | -
// <mult-op> ->        * | /

// test grammar
// <start> ->      <expr-list>
// <expr-list> ->  IDENT <expr>
// <expr> ->       + <expr-list> | epsilon

public class Parser {
	CustomScanner scan;
	Token currentToken;
	
	public Parser(String input) {
		this.scan = new CustomScanner(input);
	}
	
	public boolean match(Token.TokenId id) {
		if (currentToken != null) {
			if (currentToken.getTokenId() == id) {
				this.currentToken = this.scan.nextToken();
				return true;
			}
		}
		return false;
	}
	
	public boolean require(Token.TokenId id) {
		if (match(id)) {
			return true;
		}
		else {
			throw new RuntimeException("SYNTAX ERROR: Expected " + id + " but found " + currentToken.getTokenId());
		}
	}
	
	// <start> -> <expr-list>
	public void start() {
		this.currentToken = this.scan.nextToken();
		System.out.println("Parsing <start> - Current Token: " + currentToken);
		expressionList();
		System.out.println("Parsing Completed");
	}
	
	// <expr-list> -> IDENT <expr>
	public void expressionList() {
		System.out.println("Parsing <expr-list> - Current Token: " + currentToken);
		require(Token.TokenId.IDENTIFIER);
		expr();
	}
	
	// <expr> -> + <expr-list> | epsilon
	public void expr() {
		System.out.println("Parsing <expr> - Current Token: " + currentToken);
		if (currentToken != null) {
			require(Token.TokenId.ADDITION);
			expressionList();
		}
	}
	
	// <program> -> <statement-list>
	public void program() {
		currentToken = this.scan.nextToken();
		printParserMethod("PROGRAM");
		statementList();
		System.out.println("Parsing Completed");
	}
	
	// <statement-list> -> <statement> <statement-list> | EPSILON
	public void statementList() {
		printParserMethod("STATEMENT-LIST");
		if (currentToken != null) {
			statement();
			statementList();
		}
	}
	
	// <statement> -> IDENT = <expression> 
	public void statement() {
		printParserMethod("STATEMENT");
		match(Token.TokenId.IDENTIFIER);
		match(Token.TokenId.EQUALS);
		expression();
	}
	
	// <expression> -> <term> <term-tail>
	public void expression() {
		printParserMethod("EXPRESSION");
		term();
		termTail();
	}
	
	// <term-tail> -> <add-op> <term> <term-tail> | EPSILON
	public void termTail() {
		printParserMethod("TERM-TAIL");
		if (match(Token.TokenId.ADDITION) || match(Token.TokenId.SUBTRACT)) {
			term();
			termTail();
		}
	}
	
	// <term> -> <factor> <factor-tail>
	public void term() {
		printParserMethod("TERM");
		factor();
		factorTail();
	}
	
	// <factor-tail> -> <mult-op> <factor> <factor-tail> | EPSILON
	public void factorTail() {
		printParserMethod("FACTOR-TAIL");
		if (match(Token.TokenId.MULT) || match(Token.TokenId.DIV)) {
			factor();
			factorTail();
		}
	}
	
	// <factor> -> ( <expression> ) | IDENT | NUMBER
	public void factor() {
		printParserMethod("FACTOR");
		if (match(Token.TokenId.LPAREN)) {
			expression();
			require(Token.TokenId.RPAREN);
		}
		else if (match(Token.TokenId.IDENTIFIER)) {
			;
		}
		else if (match(Token.TokenId.NUMBER)) {
			;
		}
		else {
			throw new RuntimeException("SYNTAX ERROR: Invalid Factor");
		}
	}
	
	// <add-op> -> + | -
	public void addOp() {
		printParserMethod("ADD-OP");
		if (match(Token.TokenId.ADDITION)) {
			;
		}
		else if (match(Token.TokenId.SUBTRACT)) {
			;
		}
		else {
			throw new RuntimeException("SYNTAX ERROR: Invalid Add-Op");
		}
	}
	
	// <mult-op> -> * | /
	public void multOp() {
		printParserMethod("MULT-OP");
		if (match(Token.TokenId.MULT)) {
			;
		}
		else if (match(Token.TokenId.DIV)) {
			;
		}
		else {
			throw new RuntimeException("SYNTAX ERROR: Invalid Mult-Op");
		}
	}
	
	public void printParserMethod(String methodName) {
		System.out.println("Parsing <" + methodName + "> : current-token: " + this.currentToken);
	}
	
	public void printSyntaxErrorMsg(Token.TokenId expected, Token.TokenId found) {
		System.out.println("SYNTAX ERROR: Expected " + expected + " but found " + found);
	}
}
