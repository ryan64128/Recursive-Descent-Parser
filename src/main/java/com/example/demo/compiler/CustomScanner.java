package com.example.demo.compiler;

import com.example.demo.compiler.Token.TokenId;

public class CustomScanner{
	private byte[] values;
	private int cursor;
	private char currentChar;
	
	public CustomScanner(String value) {
		this.values = value.getBytes();
		this.cursor = 0;
		this.currentChar = (char)this.values[0];
	}
	
	public char next() {
		if (cursor < values.length) {
			return (char)values[cursor++];
		}
		else {
			return 0;
		}
	}
	
	public char peek() {
		if (cursor < values.length) {
			return (char)values[cursor];
		}
		else {
			return 0;
		}
	}
	
	public boolean hasNext() {
		return cursor < values.length;
	}
	
	public boolean isNumber(char c) {
		return c >= '0' && c <= '9';
	}
	
	public boolean isLetter(char c) {
		return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
	}
	
	public Token nextToken() {
		if(hasNext()) {
			currentChar = next();
			skipWhiteSpace();
			skipComments();
			
			// return single char tokens
			if (currentChar == '+') {
				return new Token(Token.TokenId.ADDITION, "+");
			}
			else if (currentChar == '-') {
				return new Token(Token.TokenId.SUBTRACT, "-");
			}
			else if (currentChar == '*') {
				return new Token(Token.TokenId.MULT, "*");
			}
			else if (currentChar == '/') {
				return new Token(Token.TokenId.DIV, "/");
			}
			else if (currentChar == ';') {
				return new Token(Token.TokenId.SEMICOLON, ";");
			}
			else if (currentChar == '(') {
				return new Token(Token.TokenId.LPAREN, "(");
			}
			else if (currentChar == ')') {
				return new Token(Token.TokenId.RPAREN, ")");
			}
			else if (currentChar == '{') {
				return new Token(Token.TokenId.LBRACE, "{");
			}
			else if (currentChar == '}') {
				return new Token(Token.TokenId.RBRACE, "}");
			}
			
			// read equals and logical equals
			else if (currentChar == '=') {
				if (peek() == '=') {
					next();
					return new Token(Token.TokenId.LOGICAL_EQUALS, "==");
				}
				else {
					return new Token(Token.TokenId.EQUALS, "=");
				}
			}
			
			// read not and not equals
			else if (currentChar == '!') {
				if (peek() == '=') {
					next();
					return new Token(Token.TokenId.LOGICAL_NOT_EQUALS, "!=");
				}
				else {
					return new Token(Token.TokenId.NOT, "!");
				}
			}
			
			// read decimal point starting numbers
			else if (currentChar == '.') {
				if (isNumber(peek())){
					currentChar = next();
					StringBuilder str = new StringBuilder(".");
					str.append(currentChar);
					while(isNumber(peek())) {
						currentChar = next();
						str.append(currentChar);
					}
					return new Token(Token.TokenId.NUMBER, str.toString());
				}
				else {
					return new Token(Token.TokenId.ERROR, "Error");
				}
			}
			
			// read all other numbers
			else if (isNumber(currentChar)) {
				int decimalCount = 0;
				StringBuilder str = new StringBuilder();
				str.append(currentChar);
				if (isNumber(peek()) || peek() == '.'){
					currentChar = next();   // consume either digit or .
					str.append(currentChar);
					if (currentChar == '.') {
						decimalCount++;
					}
					while(isNumber(peek()) || peek() == '.') {
						if (peek() == '.') {
							if (decimalCount < 1) {
								currentChar = next();
								str.append(currentChar);
								decimalCount++;
							}
							else {
								return new Token(Token.TokenId.ERROR, "Error");
							}
						}
						else {
							currentChar = next();
							str.append(currentChar);
						}
					}
				}
				return new Token(Token.TokenId.NUMBER, str.toString());
			}

			// read identifiers
			else if (isLetter(currentChar)) {
				StringBuilder str = new StringBuilder();
				str.append(currentChar);
				while (isNumber(peek()) || isLetter(peek())) {
					currentChar = next();
					str.append(currentChar);
				}
				return new Token(Token.TokenId.IDENTIFIER, str.toString());
			}
		}
		return null;
	}
	
	public void skipWhiteSpace() {
		// skip any whitespace
		while (currentChar == ' ' || currentChar == '\t' || currentChar == '\n') {
			currentChar = next();
		}
	}
	
	public void skipComments() {
		// skip comments
		if (currentChar == '/') {
			if (peek() == '/') {
				next();  // consume second /
				while (peek() != '\n') {   // consume until next char is \n
					next();
				}
				next();  // consume \n
				currentChar = next();
			}
		}
	}
	
	public char getCharAt(int index) {
		return (char)values[index];
	}
	
	public char getFirst() {
		return (char)values[0];
	}
	
	public char getCurrentChar() {
		return this.currentChar;
	}
}
