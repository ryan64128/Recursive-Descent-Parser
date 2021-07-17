package com.example.demo.input;

import org.springframework.stereotype.Component;

@Component
public class WebDataReader{
	private String text;
	
	public WebDataReader() {
		this.text = "default";
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
