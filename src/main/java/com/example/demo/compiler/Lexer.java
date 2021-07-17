package com.example.demo.compiler;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.input.InputInterface;

@Component
public class Lexer {
	@Autowired
	private InputInterface input;
	private Scanner fileScan;
	private List<List<String>> fileList;
	private List<Token> tokenList;
	
	public Lexer() throws IOException{
		tokenList = new ArrayList<Token>();
		fileList = new ArrayList<List<String>>();
	}
	
	public void readFile(String inputString){
		tokenList = new ArrayList<Token>();
		fileList = new ArrayList<List<String>>();
		fileScan = new Scanner(inputString);
		while(fileScan.hasNext()) {
			String line = fileScan.nextLine();
			List<String> lineList = splitter(" +-*/();={}!", line);
			fileList.add(lineList);
		}
		printFileList();
	}
	
	public void printFileList() {
		for (List<String> list : fileList) {
			if (list == null) {
				System.out.println("Stopping Program due to syntax error");
				return;
			}
			for (String s : list) {
				System.out.print(s + ", ");
			}
			System.out.println("");
		}
	}
	
	public void printTokenList() {
		for(Token t : tokenList) {
			System.out.println(t);
		}
	}
	
	public List<Token> getTokenList(){
		return this.tokenList;
	}
	
	public void readTokens() {
		for (List<String> list : this.fileList) {
			if (list == null) {
				System.out.println("Stopping Program due to syntax error");
				return;
			}
			for (String s : list) {
				Token t = readToken(s);
				if (t.getTokenId() == Token.TokenId.ERROR) {
					System.out.println("Error encountered during lexical analysis");
					return;
				}
			}
		}
		System.out.println("");
		printTokenList();
	}
	
	public Token readToken(String str) {
		String identifierPattern = "[a-zA-Z][a-zA-Z0-9]*";
		String numericPattern = "[0-9]*[.]*[0-9]*";
		String additionPattern = "\\+";
		String subtractionPattern = "-";
		String multiplicationPattern = "\\*";
		String divisionPattern = "/";
		String logicalEqualsPattern = "==";
		String logicalNotEqualsPattern = "\\!=";
		String equalsPattern = "=";
		String semicolonPattern = ";";
		String lparenPattern = "\\(";
		String rparenPattern = "\\)";
		String lbracePattern = "\\{";
		String rbracePattern = "\\}";
		
		if (str.matches(identifierPattern)) {
			Token t = new Token(Token.TokenId.IDENTIFIER, str);
			tokenList.add(t);
			return t;
		}
		else if (str.matches(numericPattern)){
			Token t = new Token(Token.TokenId.NUMBER, str);
			tokenList.add(t);
			return t;
		}
		else if (str.matches(additionPattern)){
			Token t = new Token(Token.TokenId.ADDITION, str);
			tokenList.add(t);
			return t;
		}
		else if (str.matches(subtractionPattern)){
			Token t = new Token(Token.TokenId.SUBTRACT, str);
			tokenList.add(t);
			return t;
		}
		else if (str.matches(multiplicationPattern)){
			Token t = new Token(Token.TokenId.MULT, str);
			tokenList.add(t);
			return t;
		}
		else if (str.matches(divisionPattern)){
			Token t = new Token(Token.TokenId.DIV, str);
			tokenList.add(t);
			return t;
		}
		else if (str.matches(logicalEqualsPattern)){
			Token t = new Token(Token.TokenId.LOGICAL_EQUALS, str);
			tokenList.add(t);
			return t;
		}
		else if (str.matches(logicalNotEqualsPattern)){
			Token t = new Token(Token.TokenId.LOGICAL_NOT_EQUALS, str);
			tokenList.add(t);
			return t;
		}
		else if (str.matches(equalsPattern)){
			Token t = new Token(Token.TokenId.EQUALS, str);
			tokenList.add(t);
			return t;
		}
		else if (str.matches(semicolonPattern)){
			Token t = new Token(Token.TokenId.SEMICOLON, str);
			tokenList.add(t);
			return t;
		}
		else if (str.matches(lparenPattern)){
			Token t = new Token(Token.TokenId.LPAREN, str);
			tokenList.add(t);
			return t;
		}
		else if (str.matches(rparenPattern)){
			Token t = new Token(Token.TokenId.RPAREN, str);
			tokenList.add(t);
			return t;
		}
		else if (str.matches(lbracePattern)){
			Token t = new Token(Token.TokenId.LBRACE, str);
			tokenList.add(t);
			return t;
		}
		else if (str.matches(rbracePattern)){
			Token t = new Token(Token.TokenId.RBRACE, str);
			tokenList.add(t);
			return t;
		}
		Token t = new Token(Token.TokenId.ERROR, str);
		tokenList.add(t);
		return t;
	}
	
	public List<String> splitter(String regex, String str){
		List<String> list = new ArrayList<String>();
		int offset = 0;
		for(int i=0; i<str.length(); i++) {
			//is str.charAt(i) in regex?
			for (int j=0; j<regex.length(); j++) {
				if (str.charAt(i) == regex.charAt(j) || str.charAt(i) == '\t') {
					// yes, take array[offset] to array[i] substring add to list
					if (offset < i)
						list.add(str.substring(offset, i));
					offset = i+1;
					
					//check for logical equals operator look ahead
					if (str.charAt(i) == '=') {
						// check if we are at least two away from end and if we have two =
						if ((i < str.length()-1) && (str.charAt(i+1) == '=')) {
							offset = i+2;
							list.add(str.substring(i, i+2));
							i++;
							break;
						}
					}
					
					//check for logical not equals operator look ahead
					if (str.charAt(i) == '!') {
						// check if we are at least two away from end and if we have two =
						if ((i < str.length()-1) && (str.charAt(i+1) == '=')) {
							offset = i+2;
							list.add(str.substring(i, i+2));
							i++;
							break;
						}
						else {
							System.out.println("Error: received invalid token - !");
							return null;
						}
					}
					
					// skip spaces and tabs
					if (str.charAt(i) != ' ' && str.charAt(i) != '\t'){
						list.add(str.substring(i, i+1));
					}
					break;
				}
			}
			// check if we reached the end of str but still have data to add because offset is less than length
			if (i == str.length()-1) {
				if (offset < str.length()) {
					list.add(str.substring(offset, str.length()));
				}
			}
		}
		return list;
	}
	
}
