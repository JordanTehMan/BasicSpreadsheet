package com.jordantehman;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainFrame {
	
	private JFrame frame;
	private JTextField[][] fields = new JTextField[5][8];
	
	public MainFrame() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Basic Spreadsheet");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		frame.setSize(new Dimension(800, 500));
		ImageIcon icon = new ImageIcon("icon.png");
		frame.setIconImage(icon.getImage());
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new GridLayout(5, 8, 0, 0));
		JPanel columnPanel = new JPanel();
		columnPanel.setLayout(new GridLayout(0, 8, 0, 0));
		JPanel rowPanel = new JPanel();
		rowPanel.setLayout(new GridLayout(5, 0, 0, 0));
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout());
		
		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 8; k++) {
				JTextField field = new JTextField();
				textPanel.add(field);
				fields[i][k] = field;
			}
		}
		
		for (int i = 1; i <= 8; i++) {
			final int index = i;
			JLabel label = new JLabel(Integer.toString(index));
			label.setHorizontalAlignment(JLabel.CENTER);
			columnPanel.add(label);
		}
		
		for (int i = 1; i <= 5; i++) {
			final int index = i;
			JLabel label = new JLabel(Integer.toString(index));
			label.setVerticalAlignment(JLabel.NORTH);
			rowPanel.add(label);
		}
		
		JButton export = new JButton("Export");
		export.setIcon(new ImageIcon("print.png"));
		export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					File csv = null;
					String path = "";
					int num = 1;
					
					while (true) {
						path = "spreadsheet" + num + ".txt";
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
										bw.write("(" + Integer.toString(i) + ", " + Integer.toString(k) + ") = " + "(Empty)" + " ");
									} else {
										bw.write("(" + Integer.toString(i) + ", " + Integer.toString(k) + ") = " + fields[i - 1][k - 1].getText() + " ");
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
		});
		JButton importBtn = new JButton("Import");
		importBtn.setIcon(new ImageIcon("import.png"));
		buttonPanel.add(importBtn);
		buttonPanel.add(export);
		
		frame.add(textPanel, BorderLayout.CENTER);
		frame.add(columnPanel, BorderLayout.NORTH);
		frame.add(rowPanel, BorderLayout.WEST);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		
		frame.setVisible(true);
	}
}
