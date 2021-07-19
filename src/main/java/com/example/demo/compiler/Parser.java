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
	private CustomScanner scan;
	private Token currentToken;
	private Token prevToken = null;
	private Node<String> root;
	
	public Parser(String input) {
		this.scan = new CustomScanner(input);
	}
	
	public boolean match(Token.TokenId id) {
		if (currentToken != null) {
			if (currentToken.getTokenId() == id) {
				this.prevToken = this.currentToken;
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
		int depth = 0;
		currentToken = this.scan.nextToken();
		root = new Node<String>("<Program>", depth);
		printParserMethod("PROGRAM");
		statementList(root, depth+1);
		System.out.println("Parsing Completed");
	}
	
	// <statement-list> -> <statement> <statement-list> | EPSILON
	public void statementList(Node<String> parent, int depth) {
		Node<String> child = addChild(parent, "<Statement-List>", depth);
		printParserMethod("STATEMENT-LIST");
		if (currentToken != null) {
			statement(child, depth+1);
			statementList(child, depth+1);
		}
	}
	
	// <statement> -> IDENT = <expression> 
	public void statement(Node<String> parent, int depth) {
		Node<String> child = addChild(parent, "<Statement>", depth);
		printParserMethod("STATEMENT");
		match(Token.TokenId.IDENTIFIER);
		match(Token.TokenId.EQUALS);
		expression(child, depth+1);
	}
	
	// <expression> -> <term> <term-tail>
	public void expression(Node<String> parent, int depth) {
		Node<String> child = addChild(parent, "<Expression>", depth);
		printParserMethod("EXPRESSION");
		term(child, depth+1);
		termTail(child, depth+1);
	}
	
	// <term-tail> -> <add-op> <term> <term-tail> | EPSILON
	public void termTail(Node<String> parent, int depth) {
		Node<String> child = addChild(parent, "<Term-Tail>", depth);
		printParserMethod("TERM-TAIL");
		if (match(Token.TokenId.ADDITION)) {
			addChild(child, "+", depth+1);
			term(child, depth+1);
			termTail(child, depth+1);
		}
		else if (match(Token.TokenId.SUBTRACT)) {
			addChild(child, "-", depth+1);
			term(child, depth+1);
			termTail(child, depth+1);
		}
	}
	
	// <term> -> <factor> <factor-tail>
	public void term(Node<String> parent, int depth) {
		Node<String> child = addChild(parent, "<Term>", depth);
		printParserMethod("TERM");
		factor(child, depth+1);
		factorTail(child, depth+1);
	}
	
	// <factor-tail> -> <mult-op> <factor> <factor-tail> | EPSILON
	public void factorTail(Node<String> parent, int depth) {
		Node<String> child = addChild(parent, "<Factor-Tail>", depth);
		printParserMethod("FACTOR-TAIL");
		if (match(Token.TokenId.MULT)) {
			addChild(child, "*", depth+1);
			factor(child, depth+1);
			factorTail(child, depth+1);
		}
		else if (match(Token.TokenId.DIV)) {
			addChild(child, "/", depth+1);
			factor(child, depth+1);
			factorTail(child, depth+1);
		}
	}
	
	// <factor> -> ( <expression> ) | IDENT | NUMBER
	public void factor(Node<String> parent, int depth) {
		Node<String> child = addChild(parent, "<Factor>", depth);
		printParserMethod("FACTOR");
		if (match(Token.TokenId.LPAREN)) {
			addChild(child, "(", depth+1);
			expression(child, depth+1);
			require(Token.TokenId.RPAREN);
			addChild(child, ")", depth+1);
		}
		else if (match(Token.TokenId.IDENTIFIER)) {
			addChild(child, prevToken.getValue(), depth+1);
		}
		else if (match(Token.TokenId.NUMBER)) {
			addChild(child, prevToken.getValue(), depth+1);
		}
		else {
			throw new RuntimeException("SYNTAX ERROR: Invalid Factor");
		}
	}
	
	// <add-op> -> + | -
	public void addOp(Node<String> parent, int depth) {
		Node<String> child = addChild(parent, "Add-Op", depth);
		printParserMethod("ADD-OP");
		if (match(Token.TokenId.ADDITION)) {
			child.addChild(new Node<String>(Token.TokenId.ADDITION.toString(), depth+1));
		}
		else if (match(Token.TokenId.SUBTRACT)) {
			child.addChild(new Node<String>(Token.TokenId.SUBTRACT.toString(), depth+1));
		}
		else {
			throw new RuntimeException("SYNTAX ERROR: Invalid Add-Op");
		}
	}
	
	// <mult-op> -> * | /
	public void multOp(Node<String> parent, int depth) {
		Node<String> child = addChild(parent, "Mult-Op", depth);
		printParserMethod("MULT-OP");
		if (match(Token.TokenId.MULT)) {
			child.addChild(new Node<String>(Token.TokenId.MULT.toString(), depth+1));
		}
		else if (match(Token.TokenId.DIV)) {
			child.addChild(new Node<String>(Token.TokenId.DIV.toString(), depth+1));
		}
		else {
			throw new RuntimeException("SYNTAX ERROR: Invalid Mult-Op");
		}
	}
	
	public Node<String> addChild(Node<String> parent, String data, int depth){
		Node<String> child = new Node<String>(data, depth);
		child.setParent(parent);
		parent.addChild(child);
		return child;
	}
	
	public void printAbstractSyntaxTree() {
		root.printTree();
	}
	
	public void printParserMethod(String methodName) {
		System.out.println("Parsing <" + methodName + "> : current-token: " + this.currentToken);
	}
	
	public void printSyntaxErrorMsg(Token.TokenId expected, Token.TokenId found) {
		System.out.println("SYNTAX ERROR: Expected " + expected + " but found " + found);
	}
}
