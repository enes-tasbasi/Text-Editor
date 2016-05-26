package main;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.text.*;

import org.apache.commons.io.*;

import com.inet.jortho.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Panel extends JPanel {
	
	//variables
	private JTextPane textPane;
	private JLabel label;
	private MutableAttributeSet set;
	private StyledDocument doc;
	private File file;
	private StringBuilder sb;
	private Boolean fileOpen = false;
	
	//constructor
	public Panel() {
		
		setLayout(new BorderLayout());
		
		//panel for menus
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(1,1));
		
		//the main menu
		JMenuBar menuBar = new JMenuBar();
		
		//File menu
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);
		
		//Font menu
		JMenu fontMenu = new JMenu("Font");
		fontMenu.setMnemonic(KeyEvent.VK_O);
		menuBar.add(fontMenu);
		
		JMenu utilMenu = new JMenu("Utilities");
		utilMenu.setMnemonic(KeyEvent.VK_U);
		menuBar.add(utilMenu);
		
		menuPanel.add(menuBar);
		add(menuPanel, BorderLayout.NORTH);
		
		//--------------------------------//
		
		//File : open
		JMenuItem open = new JMenuItem("Open");
		open.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit()
					.getMenuShortcutKeyMask()));
		open.setMnemonic(KeyEvent.VK_O);
		open.addActionListener(new Listener());
		fileMenu.add(open);
		
		//File : save
		JMenuItem save = new JMenuItem("Save");
		save.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask()));
		save.setMnemonic(KeyEvent.VK_S);
		save.addActionListener(new Listener());
		fileMenu.add(save);
				
		//File : save as
		JMenuItem saveAs = new JMenuItem("Save As");
		saveAs.setAccelerator(KeyStroke.getKeyStroke('A', Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask()));
		saveAs.setMnemonic(KeyEvent.VK_V);
		saveAs.addActionListener(new Listener());
		fileMenu.add(saveAs);
				
		fileMenu.addSeparator();
				
		//File : exit
		JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_E);
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					System.exit(0);
							
			}
		});
		fileMenu.add(exit);
		
		//*******************************//
		//Font : Bold
		JMenuItem bold = new JMenuItem("Bold");
		bold.setAccelerator(KeyStroke.getKeyStroke('B', Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask()));
		bold.addActionListener(new Listener());
		fontMenu.add(bold);
				
		//Font : italic
		JMenuItem italic = new JMenuItem("Italic");
		italic.setAccelerator(KeyStroke.getKeyStroke('I', Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask()));
		italic.addActionListener(new Listener());
		fontMenu.add(italic);
				
		//Font : underline
		JMenuItem underline = new JMenuItem("Underline");
		underline.setAccelerator(KeyStroke.getKeyStroke('U', Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask()));
		underline.addActionListener(new Listener());
		fontMenu.add(underline);
				
		fontMenu.addSeparator();
				
		//Font : fontSize
		JMenuItem fontSize = new JMenuItem("Font size");
		fontSize.addActionListener(new Listener());
		fontMenu.add(fontSize);
				
		//Font : fontFamily
		JMenuItem fontFamily = new JMenuItem("Choose Font");
		fontFamily.addActionListener(new Listener());
		fontMenu.add(fontFamily);
		
		//*******************************//
		
		//Utilities : PDF
		JMenuItem pdf = new JMenuItem("Export to PDF");
		utilMenu.add(pdf);
		pdf.addActionListener(new Listener());
		
		JMenuItem find = new JMenuItem("Find");
		find.setMnemonic(KeyEvent.VK_F);
		find.addActionListener(new Listener());
		utilMenu.add(find);
		
		//------------------- top menu complete -----------------------//
		
		JPanel panePanel = new JPanel();
		panePanel.setLayout(new BorderLayout());
		
		textPane = new JTextPane();
//		textPane.addCaretListener(new caretListener());
		
		label = new JLabel("Status");
		panePanel.add(label, BorderLayout.PAGE_END);
		
		JScrollPane scroll = new JScrollPane(textPane);
	//	scroll.setViewportView(textPane);
		panePanel.add(scroll);
		
		add(panePanel);
		
		//--------------------- textPane and bottom label complete -------------//
		
		// add the spell checker
		SpellChecker.setUserDictionaryProvider(new FileUserDictionary());      
		SpellChecker.registerDictionaries( null, null );
	    SpellChecker.register(textPane);
	    
	    //declare varibales  for later use
	    sb = new StringBuilder("");
	    
	    set = textPane.getInputAttributes();
	    
	    doc = textPane.getStyledDocument();

	    FontFamily("Rockwell");
	    
	    FontSize(20);
	    
	    textPane.setText(" ");
	    
	    
	}
	
	//************************ Action Listeneres ********************//
    private class Listener implements ActionListener {
    	public void actionPerformed(ActionEvent evt) {
    		String source = evt.getActionCommand();
    		
    		switch(source) {
    		
    		case "Open" :
    			Open();
    			break;
    		case "Save" :
    			Save();
    			break;
    		case "Save As" :
    			SaveAs();
    			break;
    		case "Bold" :
    			if(StyleConstants.isBold(set) == false) {
					Bold();
				}
				else {
					ClearBold();
				}
    			break;
    		case "Italic" :
    			if(StyleConstants.isItalic(set) == false) {
					Italic();
				}
				else {
					ClearItalic();
				}
    			break;
    		case "Underline" :
    			if(StyleConstants.isUnderline(set) == false) {
					Underline();
				}
				else {
					ClearUnderline();
				}
    			break;
    		case "Font size" :
    			String size = (String) JOptionPane.showInputDialog("Enter the font size");
				FontSize(Integer.parseInt(size));
    			break;
    		case "Choose Font" :
				GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
				String[] fnt = ge.getAvailableFontFamilyNames();
				
				String input = (String) JOptionPane.showInputDialog(null, "Choose the font ", 
						"Font", JOptionPane.QUESTION_MESSAGE, null, fnt, fnt[0]);
				FontFamily(input);
    			break;
    		}
    		
    	}
    }
    
    private void SaveAs() {
    	   
        if( fileOpen == true) {
    Save();
    file = null;
    }
       
        JFileChooser dir = new JFileChooser(new File("C://Users//Bera//Desktop"));
       
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        dir.setFileFilter(filter);
       
        int returnVal = dir.showSaveDialog(dir);
       
            if (returnVal == JFileChooser.APPROVE_OPTION) {
           
            file = dir.getSelectedFile();
           
            try {
            	
//	            FileWriter fw = new FileWriter(file);
//	            sb.equals(textPane.getText());
//	            fw.write(textPane.getText());
//	            fw.close();	
            	
//            	sb.equals(textPane.getText());
//            	
//            	Formatter x = new Formatter(file);
//            	x.format(sb.toString());
//            	x.close();
            	
            	FileUtils.writeStringToFile(file, textPane.getText());
        
            } catch(IOException e) {
            e.printStackTrace();
            }
           
            fileOpen = true;
            }
            
            else {
            //TODO: write updateInfo code
            }
        }
    
    private void Save() {
    	if(fileOpen == true) {
	    	
			try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
				
				out.write(textPane.getText());
				sb.equals(textPane.getText());
				
			} catch (IOException e1) {
				//TODO: write updateInfo
			}
    	} else {
    		SaveAs();
    	}
    }
    
    private void Open() {
    	

    	if(fileOpen == true) {
			Save();
			file = null;
		}	
    	
    	JFileChooser fc = new JFileChooser(new File("C://Users//Bera//Desktop"));
    	
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
    	fc.setFileFilter(filter);
		
		int returnVal = fc.showOpenDialog(fc);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
        } else {
          //TODO: write updateInfo method
        }
        
		try {
			 sb.equals("");
			 
			 Scanner in = new Scanner(file);
				while(in.hasNext()) {
					String line = in.nextLine();
					sb.append(line).append("\n");
				}
				
				textPane.setText(sb.toString());
			
			// method #2
			
//			FileReader fr = new FileReader(file);
//			BufferedReader br = new BufferedReader(fr);
//			
//			String x;
//			while((x = br.readLine()) != null) {
//				sb.append(x + "\n");
//			}
//			
//			textPane.setText(sb.toString());
//			br.close();
			
			fileOpen = true;
			
		} catch(IOException e) {
			//TODO: write updateInfo
		}
    }
    
    //---------------- all the font classes -------------------//
    
    private void Bold() {
    	StyleConstants.setBold(set, true);
    	doc.setCharacterAttributes(1, 0, set, false);
    }
    
    private void ClearBold() {
    	StyleConstants.setBold(set, false);
    	doc.setCharacterAttributes(1, 0, set, false);
    }
    
    private void Italic() {
    	StyleConstants.setItalic(set, true);
    	doc.setCharacterAttributes(1, 0, set, false);
    }
    
    private void ClearItalic() {
    	StyleConstants.setItalic(set, false);
    	doc.setCharacterAttributes(1, 0, set, false);
    }
    
    private void Underline() {
    	StyleConstants.setUnderline(set, true);
    	doc.setCharacterAttributes(1, 0, set, false);
    }
    
    private void ClearUnderline() {
    	StyleConstants.setUnderline(set, false);
    	doc.setCharacterAttributes(1, 0, set, false);
    }
    
    private void FontSize(int size) {
    	StyleConstants.setFontSize(set, size);
    	doc.setCharacterAttributes(1, 0, set, false);
    }
    
    private void FontFamily(String font) {
    	StyleConstants.setFontFamily(set, font);
    	doc.setCharacterAttributes(1, 0, set, false);
    }
    
    //------------------------------------//
}
