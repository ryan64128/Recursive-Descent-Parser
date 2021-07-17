package com.example.demo.compiler;

public class Token {

	public enum TokenId {
		DEFAULT {
			@Override
			public String toString() {
				return "<DEFAULT>";
			}
		},
		IDENTIFIER {
			@Override
			public String toString() {
				return "<IDENTIFIER>";
			}
		},
		NUMBER {
			@Override
			public String toString() {
				return "<NUMBER>";
			}
		},
		ADDITION {
			@Override
			public String toString() {
				return "<ADDITION>";
			}
		},
		SUBTRACT {
			@Override
			public String toString() {
				return "<SUBTRACT>";
			}
		},
		MULT {
			@Override
			public String toString() {
				return "<MULT>";
			}
		},
		DIV {
			@Override
			public String toString() {
				return "<DIV>";
			}
		},
		EQUALS {
			@Override
			public String toString() {
				return "<EQUALS>";
			}
		},
		SEMICOLON {
			@Override
			public String toString() {
				return "<SEMICOLON>";
			}
		},
		LPAREN {
			@Override
			public String toString() {
				return "<LPAREN>";
			}
		},
		RPAREN {
			@Override
			public String toString() {
				return "<RPAREN>";
			}
		},
		LBRACE {
			@Override
			public String toString() {
				return "<LBRACE>";
			}
		},
		RBRACE {
			@Override
			public String toString() {
				return "<RBRACE>";
			}
		},
		LOGICAL_EQUALS {
			@Override
			public String toString() {
				return "<LOGICAL_EQUALS>";
			}
		},
		LOGICAL_NOT_EQUALS {
			@Override
			public String toString() {
				return "<LOGICAL_NOT_EQUALS>";
			}
		},
		NOT {
			@Override
			public String toString() {
				return "<NOT>";
			}
		},
		ERROR {
			@Override
			public String toString() {
				return "<ERROR>";
			}
		}
	}
	
	private TokenId tokenId;
	private String value;
	
	public Token(TokenId tokenId, String value) {
		this.tokenId = tokenId;
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "[" + this.tokenId + ", " + this.value + "]";
	}

	public TokenId getTokenId() {
		return tokenId;
	}

	public void setTokenId(TokenId tokenId) {
		this.tokenId = tokenId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
