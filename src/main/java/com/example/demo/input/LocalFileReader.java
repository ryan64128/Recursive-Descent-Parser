package com.example.demo.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class LocalFileReader implements InputInterface{
	private StringBuilder fileRead;
	private final int BUFFER_SIZE = 1024;
	
	@Override
	public String getInput() {
		return fileRead.toString();
	}
	
	public LocalFileReader() throws FileNotFoundException{
		FileReader reader = new FileReader(new File("C:/Users/Ryan/Compiler Eclipse Workspace/Spring-Compiler-Project/src/main/resources/sample.txt"));
		char[] buffer = new char[BUFFER_SIZE];
		fileRead = new StringBuilder();
		try {
			int charsRead = 0;
			while ((charsRead = reader.read(buffer, 0, BUFFER_SIZE)) > 0) {
				if (charsRead == BUFFER_SIZE) {
					String str = new String(buffer);
					fileRead.append(str);
				}
				else {
					char[] tempBuf = new char[charsRead];
					for (int i=0; i<charsRead; i++) {
						tempBuf[i] = buffer[i];
					}
					String str = new String(tempBuf);
					fileRead.append(str);
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
