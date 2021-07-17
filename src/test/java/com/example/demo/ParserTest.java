package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.example.demo.compiler.Parser;

class ParserTest {
	Parser parser;
	
	@Test
	void testProgramMethod() {
		parser = new Parser("cat=(dog+bat)*cat");
		parser.program();
	}

}
