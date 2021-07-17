package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.compiler.Lexer;

@SpringBootTest
class SpringCompilerProjectApplicationTests {
	@Autowired
	Lexer lex;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void testSplitter() {
		String testString = "     cat+ dog  =  4;";
		String regex = " +=;";
		List<String> list = lex.splitter(regex, testString);
		assertEquals("cat", list.get(0));
		assertEquals("+", list.get(1));
		System.out.println(list);
	}

}
