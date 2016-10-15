package org.usfirst.frc.team25.scouting.client.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.usfirst.frc.team25.scouting.client.data.BlueAlliance;

public class Window {
	
	static File dataDirectory;
	
	public static File selectFolder(JFrame frame, String dialogTitle){
		JFileChooser chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle(dialogTitle);
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);
	    if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) { 
	        System.out.println("getCurrentDirectory(): " 
	           +  chooser.getCurrentDirectory());
	        System.out.println("getSelectedFile() : " 
	           +  chooser.getSelectedFile());
	        
	        return chooser.getSelectedFile();
	        }
	      else {
	        System.out.println("No Selection ");
	        return null;
	      }
	 }
	
	public static void processData(JFrame frame){
		JLabel introText = new JLabel("<html><h1>Processing data</h1><br>Press start to select data folder</html>");
		introText.setHorizontalAlignment(JLabel.CENTER);
		introText.setFont(new Font("Arial", Font.PLAIN, 16));
		frame.setContentPane(introText);
		frame.setVisible(true);
	}
	
	public static JFrame addIcon(JFrame frame){
		try {
		    frame.setIconImage(ImageIO.read(new File("res/logo.png")));
		}
		catch (IOException exc) {
		    exc.printStackTrace();
		}
		return frame;
	}
	
	
	public static void initialize(){
		
		JLabel introText = new JLabel("<html><h1>Team 25 Scouting Client</h1><br>Press start to select data folder</html>");
		introText.setHorizontalAlignment(JLabel.CENTER);
		introText.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JFrame frame = addIcon(new JFrame("Team 25 Scouting Client"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame.getContentPane().add(introText, BorderLayout.CENTER);

		
		JButton startButton = new JButton("Start");
		JButton downloadButton = new JButton("Download");
		JPanel panel = new JPanel(new GridLayout(1,2,1,1));
		panel.add(startButton);
		panel.add(downloadButton);
		startButton.setSize(100,70);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		frame.setSize(500, 200);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
		frame.setVisible(true);
		
		
		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				dataDirectory = selectFolder(frame, "Select data folder");
				if(dataDirectory!=null){
					frame.setVisible(false);
					processData(frame);
				}
				
					
			}
		});
		
		downloadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFrame eventCodePrompt = addIcon(new JFrame());
				File outputDirectory = selectFolder(eventCodePrompt, "Select output folder");
				if(outputDirectory==null)
					return;
				eventCodePrompt.setIconImage(null);
				
				
				String userInput = JOptionPane.showInputDialog(eventCodePrompt,
						"Enter the event code or \"25\" to download data for all events", "Enter event code",JOptionPane.PLAIN_MESSAGE);
				if(userInput.contains("25"))
					BlueAlliance.downloadRaiderEvents(outputDirectory);
				else if(userInput==null)
					return;
				else if(!BlueAlliance.downloadEventData(outputDirectory, userInput)) //Invalid event code
					JOptionPane.showMessageDialog(eventCodePrompt, "Invalid event code. Please try again", "Error", JOptionPane.PLAIN_MESSAGE);
				
	
			}
		});
	}
	
	public static void main(String[] args){
		
		initialize();
	}
}
