package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.compiler.CustomScanner;
import com.example.demo.compiler.Token;

//@SpringBootTest
class CustomScannerTest {

	@Test
	void testCustomScanner() {
		CustomScanner scan = new CustomScanner("hello world");
	}
	
	@Test
	void testNext() {
		CustomScanner scan = new CustomScanner("test");
		assertEquals('t', scan.next());
		assertEquals('e', scan.next());
		assertEquals('s', scan.next());
		assertEquals('t', scan.next());
		assertEquals(0, scan.next());
	}
	
	@Test
	void testPeek() {
		CustomScanner scan = new CustomScanner("test");
		assertEquals('t', scan.peek());
		scan.next();
		scan.next();
		scan.next();
		assertEquals('t', scan.peek());
		assertEquals('t', scan.next());
		assertEquals(0, scan.peek());
	}
	
	@Test
	void testIsNumber() {
		CustomScanner scan = new CustomScanner("hello world");
		char c = '5';
		assertEquals(true, scan.isNumber(c));
		char d = 'd';
		assertEquals(false, scan.isNumber(d));
	}
	
	@Test
	void testIsLetter() {
		CustomScanner scan = new CustomScanner("hello world");
		char c = '[';
		assertEquals(false, scan.isLetter(c));
		char d = 'd';
		assertEquals(true, scan.isLetter(d));
	}
	
	@Test
	void testSkipWhiteSpace() {
		String str = "   a + b = 5;";
		CustomScanner scan = new CustomScanner(str);
		scan.skipWhiteSpace();
		assertEquals('a', scan.getCurrentChar());
	}
	
	@Test
	void testNextTokenIdentifier() {
		CustomScanner scan = new CustomScanner("hello world a1");
		assertEquals("hello", scan.nextToken().getValue());
		assertEquals("world", scan.nextToken().getValue());
		assertEquals(Token.TokenId.IDENTIFIER, scan.nextToken().getTokenId());
	}
	@Test
	void testNextTokenDecimalNumber() {
		CustomScanner scan = new CustomScanner("  .125   .1423");
		assertEquals(".125", scan.nextToken().getValue());
		assertEquals(".1423", scan.nextToken().getValue());
	}
	@Test
	void testNextTokenNumberDecimalNumber() {
		CustomScanner scan = new CustomScanner(" 12.125  3.1416cat");
		assertEquals("12.125", scan.nextToken().getValue());
		assertEquals("3.1416", scan.nextToken().getValue());
	}
	@Test
	void testNextTokenNumberDecimal() {
		CustomScanner scan = new CustomScanner(" 12.  125.");
		assertEquals("12.", scan.nextToken().getValue());
		assertEquals("125.", scan.nextToken().getValue());
	}
	@Test
	void testNextTokenEqualities() {
		CustomScanner scan = new CustomScanner(" = == !=");
		assertEquals(Token.TokenId.EQUALS, scan.nextToken().getTokenId());
		assertEquals(Token.TokenId.LOGICAL_EQUALS, scan.nextToken().getTokenId());
		assertEquals(Token.TokenId.LOGICAL_NOT_EQUALS, scan.nextToken().getTokenId());
	}
	
	@Test
	void testComments() {
		CustomScanner scan = new CustomScanner("//comment test\nhello");
		assertEquals("hello", scan.nextToken().getValue());
	}
	@Test
	void testOperators() {
		CustomScanner scan = new CustomScanner("+-*/");
		assertEquals(Token.TokenId.ADDITION, scan.nextToken().getTokenId());
		assertEquals(Token.TokenId.SUBTRACT, scan.nextToken().getTokenId());
		assertEquals(Token.TokenId.MULT, scan.nextToken().getTokenId());
		assertEquals(Token.TokenId.DIV, scan.nextToken().getTokenId());
	}
	
	@Test
	void testNextToken() {
		String str = "if (a == 5) {\n" +
					 "	int cat = 2.125;\n" +
					 "}";
		CustomScanner scan = new CustomScanner(str);
		assertEquals(Token.TokenId.IDENTIFIER, scan.nextToken().getTokenId());
		assertEquals(Token.TokenId.LPAREN, scan.nextToken().getTokenId());
		assertEquals(Token.TokenId.IDENTIFIER, scan.nextToken().getTokenId());
		assertEquals(Token.TokenId.LOGICAL_EQUALS, scan.nextToken().getTokenId());
		assertEquals(Token.TokenId.NUMBER, scan.nextToken().getTokenId());
		assertEquals(Token.TokenId.RPAREN, scan.nextToken().getTokenId());
		assertEquals(Token.TokenId.LBRACE, scan.nextToken().getTokenId());
		assertEquals(Token.TokenId.IDENTIFIER, scan.nextToken().getTokenId());
		assertEquals(Token.TokenId.IDENTIFIER, scan.nextToken().getTokenId());
		assertEquals(Token.TokenId.EQUALS, scan.nextToken().getTokenId());
		assertEquals(Token.TokenId.NUMBER, scan.nextToken().getTokenId());
		assertEquals(Token.TokenId.SEMICOLON, scan.nextToken().getTokenId());
		assertEquals(Token.TokenId.RBRACE, scan.nextToken().getTokenId());
	}

}
