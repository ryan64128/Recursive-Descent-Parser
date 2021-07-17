package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.compiler.Lexer;
import com.example.demo.compiler.Token;
import com.example.demo.input.WebDataReader;

@Controller
public class MainController {
	@Autowired
	Lexer lexer;
	
	@RequestMapping("/")
	public String displayHomePage(Model model){
		WebDataReader reader = new WebDataReader();
		model.addAttribute("reader", reader);
		model.addAttribute("tokenList", lexer.getTokenList());
		return "index";
	}
	
	@RequestMapping(value="/read_input", method=RequestMethod.POST)
	public String readInput(@ModelAttribute("reader") WebDataReader reader) {
		lexer.readFile(reader.getText());
		lexer.readTokens();
		return "redirect:/";
	}
	
}
