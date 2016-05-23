package main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.text.*;

public class Test extends JFrame{
	
	static JTextPane pane;

    public static void main(String[] args) throws IOException {
    	JFrame frame = new JFrame("Test");
    	frame.setLocationRelativeTo(null);
    	frame.setSize(500, 400);
    	JPanel panel = new JPanel();
    	panel.setLayout(new GridLayout(2,1));
    	
    	JTextPane textPane = new JTextPane();
    	panel.add(textPane);
    	
    	JButton button = new JButton("Button");
    	button.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e){
    		
    		  try {
    			File file = new File("C:\\Users\\Bera\\Desktop\\demo.txt");
    			new FileSaver(textPane.getText(), file);
    		  } catch(Exception e1) {
    			  e1.printStackTrace();
    		  }
    		}
    	});
    	panel.add(button);
    	frame.add(panel);
    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    	
    }
    
    
 }
