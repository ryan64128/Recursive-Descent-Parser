package com.example.demo.compiler;

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

public class Parser {
	CustomScanner scan;
	Token currentToken;
	
	public Parser(String input) {
		this.scan = new CustomScanner(input);
	}
	
	public void program() {
		printParserMethod("PROGRAM");
		this.currentToken = this.scan.nextToken();
		statementList();
	}
	
	public void statementList() {
		printParserMethod("STATEMENT-LIST");
		statement();
		statementList();
	}
	
	public void statement() {
		printParserMethod("STATEMENT");
		currentToken = scan.nextToken();
		if (currentToken.getTokenId() == Token.TokenId.EQUALS) {
			expression();
		}
		printSyntaxErrorMsg(Token.TokenId.EQUALS, currentToken.getTokenId());

	}
	
	public void expression() {
		printParserMethod("EXPRESSION");
	}
	
	public void termTail() {
		
	}
	
	public void term() {
		
	}
	
	public void factorTail() {
		
	}
	
	public void factor() {
		
	}
	
	public void addOp() {
		
	}
	
	public void multOp() {
		
	}
	
	public void printParserMethod(String methodName) {
		System.out.println("Parsing <" + methodName + "> : current-token: " + this.currentToken);
	}
	
	public void printSyntaxErrorMsg(Token.TokenId expected, Token.TokenId found) {
		System.out.println("SYNTAX ERROR: Expected " + expected + " but found " + found);
	}
}
