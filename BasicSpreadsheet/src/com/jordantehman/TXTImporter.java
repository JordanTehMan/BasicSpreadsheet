package com.jordantehman;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TXTImporter {
	
	public static String[][] importTXT(File txt) {
		String[][] result = new String[5][8];
		
		for (int i = 0; i < 5; i++) {
			String curLine = "";
			try {
				curLine = Files.readAllLines(txt.toPath()).get(i);
			} catch (IOException e) {
				e.printStackTrace();
				//TODO: Make error output more descriptive.
			}
			for (int k = 0; k < 8; k++) {
				int start = curLine.indexOf("(" + (i + 1) + ", " + (k + 1) + ")");
				System.out.println(curLine);
				System.out.println("(" + (i + 1) + ", " + (k + 1) + ")");
				if (start == -1) {
					break;
				}
				String index = curLine.substring(start, curLine.indexOf("|", start) + 1);
				int firstQuote = index.indexOf("\"");
				int secondQuote = index.indexOf("\"", firstQuote + 1);
				String value = index.substring(firstQuote + 1, secondQuote);
				
				if (value.equals("\"")) {
					result[i][k] = "";
				} else {
					result[i][k] = value;
				}
			}
		}
		
		return result;
	}
}
