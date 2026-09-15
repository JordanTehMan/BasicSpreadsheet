package com.jordantehman;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextField;

public class TXTExporter {
	public static void exportTXT(JTextField[][] fields) {
		try {
			File csv = null;
			String path = "";
			int num = 1;
			
			while (true) {
				new File("spreadsheets").mkdirs();
				path = "spreadsheets/" + "spreadsheet" + num + ".txt";
				csv = new File(path);
				
				if (!csv.exists()) {
					break;
				}
				
				num++;
			}
			
			try (FileWriter fw = new FileWriter(path, true);
					BufferedWriter bw = new BufferedWriter(fw)) {
				
					for (int i = 1; i <= 5; i++) {
						for (int k = 1; k <= 8; k++) {
							if (fields[i - 1][k - 1].getText().equals("")) {
								bw.write("(" + Integer.toString(i) + ", " + Integer.toString(k) + ") = " + "\"" + "" + "\"" + " | ");
							} else {
								bw.write("(" + Integer.toString(i) + ", " + Integer.toString(k) + ") = " + "\"" + fields[i - 1][k - 1].getText() + "\"" + " ");
							}
						}
						bw.write("END OF LINE");
						bw.newLine();
					}
					
					System.out.println("Your spreadsheet has been exported.");
						
			} catch (IOException a) {
				System.err.println(a);
			}
			csv.createNewFile();
		} catch (Exception b) {
			System.out.println(b);
		}
	}
}
