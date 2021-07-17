package com.example.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.example.demo.compiler.Lexer;

@SpringBootApplication
public class SpringCompilerProjectApplication {
	
	public static void main(String[] args) throws IOException{
		ApplicationContext context = SpringApplication.run(SpringCompilerProjectApplication.class, args);
//		Lexer lex = context.getBean(Lexer.class);
//		lex.readFile();
//		lex.readTokens();
	}

}
