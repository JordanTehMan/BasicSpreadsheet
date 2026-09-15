/* Big TODO list here:
 * 1. Add row/column names
 * 2. Implement basic function capability (Modify spreadsheet based off of a set of user-defined rules
 * 3. CSV support
 * 4. Better import start path
 * 5. Improve interface and reorganize stuff
 */

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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

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
		
		//Create the spreadsheet's columns and rows
		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 8; k++) {
				JTextField field = new JTextField();
				textPanel.add(field);
				fields[i][k] = field;
			}
		}
		
		//Create column identifiers
		for (int i = 1; i <= 8; i++) {
			final int index = i;
			JLabel label = new JLabel(Integer.toString(index));
			label.setHorizontalAlignment(JLabel.CENTER);
			columnPanel.add(label);
		}
		
		//Create row identifiers
		for (int i = 1; i <= 5; i++) {
			final int index = i;
			JLabel label = new JLabel(Integer.toString(index));
			label.setVerticalAlignment(JLabel.NORTH);
			rowPanel.add(label);
		}
		
		//Export button, most logic in separate class as fields does not need to be modified
		JButton export = new JButton("Export");
		export.setIcon(new ImageIcon("print.png"));
		export.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TXTExporter.exportTXT(fields);
			}
		});
		
		//Import button, some logic is kept here for simplicity sake due to the need to modify fields
		JButton importBtn = new JButton("Import");
		importBtn.setIcon(new ImageIcon("import.png"));
		importBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser dialog = new JFileChooser();
				dialog.setAcceptAllFileFilterUsed(false);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
				dialog.setFileFilter(filter);
				File file;
				
				if (dialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					file = dialog.getSelectedFile();
					if (file != null) {
						String[][] values = TXTImporter.importTXT(file);
						for (int i = 0; i < 5; i++) {
							for (int k = 0; k < 8; k++) {
								fields[i][k].setText(values[i][k]);
							}
						}
					} else {
						System.err.println("Error occured while importing.");
						//TODO: Make error output more descriptive.
					}
				}
			}
		});
		
		buttonPanel.add(importBtn);
		buttonPanel.add(export);
		
		frame.add(textPanel, BorderLayout.CENTER);
		frame.add(columnPanel, BorderLayout.NORTH);
		frame.add(rowPanel, BorderLayout.WEST);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		
		frame.setVisible(true);
	}
}
