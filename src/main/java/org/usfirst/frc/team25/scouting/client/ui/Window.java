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
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.usfirst.frc.team25.scouting.client.data.Alliance;
import org.usfirst.frc.team25.scouting.client.data.BlueAlliance;
import org.usfirst.frc.team25.scouting.client.data.EventReport;
import org.usfirst.frc.team25.scouting.client.data.FileManager;
import org.usfirst.frc.team25.scouting.client.data.Statistics;
import org.usfirst.frc.team25.scouting.client.data.TeamReport;
import org.usfirst.frc.team25.scouting.client.models.ScoutEntry;

import com.adithyasairam.tba4j.Events;
import com.adithyasairam.tba4j.models.Match;

/** Main class; used to initialize the GUI
 * 
 * @author sng
 *
 */

public class Window {
	
	/** When processing data, dataDirectory must have the qualified <code>short_name</code> for the event
	 *  Downloaded data from TBA should be in same folder as well
	 *  Output spreadsheets/summary reports to be written to same folder
	 */
	static File dataDirectory;
	
	//Regular expression to split filename into name and extension
	public static final String FILE_EXTENSION_REGEX = "\\.";
	

	/** Processes data
	 * TODO: Make a better GUI with user feedback
	 * @param frame JFrame to be modified
	 */
	public static void processData(JFrame frame){
		JLabel introText = new JLabel("<html><h1>Processing data</h1><br>Processing...</html>");
		introText.setHorizontalAlignment(JLabel.CENTER);
		introText.setFont(new Font("Arial", Font.PLAIN, 16));
		frame.setContentPane(introText);
		frame.setVisible(true);
		
		String eventName = dataDirectory.getName();
		
		
		ArrayList<File> jsonFileList = new ArrayList<File>();
		File teamNameList = null;
		
		
		//Gets a list of data files in directory. JSON is required, csv is optional
		for(File file : FileManager.getFilesFromDirectory(dataDirectory)){
			String fileName = file.getName();
			
			
			try{
			if(fileName.split(FILE_EXTENSION_REGEX)[1].equals("json")&&fileName.contains(eventName)&&fileName.contains("Data")){
				jsonFileList.add(file);
				
			}
			if(fileName.split(FILE_EXTENSION_REGEX)[1].equals("csv")&&fileName.contains(eventName)&&fileName.contains("TeamNames"))
				teamNameList = file;
			}catch(ArrayIndexOutOfBoundsException e){
				
			}//Caused when there is only a directory, no file
		}
		
		ArrayList<ScoutEntry> scoutEntries = FileManager.deserializeData(jsonFileList);
			
		
		if(scoutEntries.size()==0){
			JOptionPane.showMessageDialog(addIcon(new JFrame()), "No JSON data files found or root folder not named after event", "Error", JOptionPane.PLAIN_MESSAGE);
			introText.setText("<html><h1>Processing data</h1><br>Error!</html>"); 
			return;
		}
		EventReport report = new EventReport(scoutEntries);
		
		if(teamNameList!=null)
			report.setTeamNameList(teamNameList);
		
		
		report.generateRawSpreadsheet(dataDirectory);
		report.processTeamReports();
		
		if(report.generateCombineJson(dataDirectory)) //combined JSON file successfully generated
			for(File file : jsonFileList)
				if(!file.getName().contains("All"))
					file.delete();
		
		report.generateTeamReportJson(dataDirectory);
		report.generateTeamReportSpreadsheet(dataDirectory);
		
		introText.setText("<html><h1>Processing data</h1><br>Done!</html>"); 
		
		frame.setVisible(false);
		initializeAnalyzer(report);
		
	}
	
	public static boolean isValidTeamNum(EventReport report, String teamNumStr){
		try{
			int teamNum = Integer.parseInt(teamNumStr);
			return report.isTeamPlaying(teamNum);
				
		}catch(NumberFormatException exc){
			return false;
		}
	}
	
	public static void initializeAnalyzer(EventReport eventReport){
		JLabel introText = new JLabel("<html><h1>Analyze data</h1><br>Retrieve alliance or team information</html>");
		introText.setHorizontalAlignment(JLabel.CENTER);
		introText.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JFrame frame = addIcon(new JFrame("Team 25 Scouting Client"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame.getContentPane().add(introText, BorderLayout.CENTER);

		
		JButton teamButton = new JButton("Team report");
		JButton allianceButton = new JButton("Alliance report");
		JPanel panel = new JPanel(new GridLayout(1,2,1,1));
		panel.add(teamButton);
		panel.add(allianceButton);
		
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		frame.setSize(500, 200);
		
		//Sets window to launch at the center of screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
		frame.setVisible(true);
		
		teamButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFrame teamNumPrompt = addIcon(new JFrame());
			
				String userInput = JOptionPane.showInputDialog(teamNumPrompt,
						"Enter the team number to retrieve stats", "Enter team number",JOptionPane.PLAIN_MESSAGE);
				if(!isValidTeamNum(eventReport, userInput))
					JOptionPane.showMessageDialog(teamNumPrompt, "Invalid team number for event. Please try again", "Error", JOptionPane.PLAIN_MESSAGE);
				else JOptionPane.showMessageDialog(teamNumPrompt, eventReport.quickTeamReport(Integer.parseInt(userInput)), "Team "+userInput, JOptionPane.PLAIN_MESSAGE);
					
			}
		});
		
		allianceButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFrame teamNumPrompt = addIcon(new JFrame());
				JTextField t1 = new JTextField(5);
			    JTextField t2 = new JTextField(5);
			    JTextField t3 = new JTextField(5);
			    
			    JPanel panel = new JPanel();
			    panel.add(new JLabel("Team #1:"));
			    panel.add(t1);
			    
			    panel.add(new JLabel("Team #2:"));
			    
			    panel.add(t2);
			    panel.add(new JLabel("Team #3:"));
			    panel.add(t3);
				
				int input = JOptionPane.showOptionDialog(teamNumPrompt, panel, "Enter alliance", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE
						, null, null, null);
				if(input == JOptionPane.OK_OPTION){
					if(!isValidTeamNum(eventReport, t1.getText())||!isValidTeamNum(eventReport,t2.getText())||!isValidTeamNum(eventReport,t3.getText()))
						JOptionPane.showMessageDialog(teamNumPrompt, "Invalid team number(s) for event. Please try again", "Error", JOptionPane.PLAIN_MESSAGE);
					else JOptionPane.showMessageDialog(teamNumPrompt, eventReport.allianceReport(Integer.parseInt(t1.getText()), Integer.parseInt(t2.getText()), Integer.parseInt(t3.getText()))
							, t1.getText()+", "+t2.getText()+", "+t3.getText()
							, JOptionPane.PLAIN_MESSAGE);
				}
					
			}
		});
	}
	
	/**
	 * Adds the Team 25 logo to the window
	 * @param frame JFrame to be modified
	 * @return Modified JFrame
	 */
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
		
		JLabel introText = new JLabel("<html><h1>Team 25 Scouting Client - v1.4</h1><br>Press start to select data folder</html>");
		introText.setHorizontalAlignment(JLabel.CENTER);
		introText.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JFrame frame = addIcon(new JFrame("Team 25 Scouting Client"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame.getContentPane().add(introText, BorderLayout.CENTER);

		
		JButton startButton = new JButton("Start");
		JButton downloadButton = new JButton("Download");
		JPanel panel = new JPanel(new GridLayout(1,2,1,1));
		panel.add(startButton);
		panel.add(downloadButton); //Elements are added to the JFrame. TODO Improve readability?
		startButton.setSize(100,70);
		frame.getContentPane().add(panel, BorderLayout.SOUTH);
		
		frame.setSize(500, 200);
		
		//Sets window to launch at the center of screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
		
		frame.setVisible(true);
		
		
		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				final JFrame dataFrame = frame;
				dataDirectory = FileManager.selectFolder(dataFrame, "Select data folder");
				if(dataDirectory!=null){
					dataFrame.setVisible(false);
					processData(dataFrame);
				}
				
					
			}
		});
		
		downloadButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JFrame eventCodePrompt = addIcon(new JFrame());
				File outputDirectory = FileManager.selectFolder(eventCodePrompt, "Select output folder");
				if(outputDirectory==null)
					return;
				eventCodePrompt.setIconImage(null);
				
				
				String userInput = JOptionPane.showInputDialog(eventCodePrompt,
						"Enter the event code or \"25\" to download data for all events", "Enter event code",JOptionPane.PLAIN_MESSAGE);
				
				if(userInput.contains("25")){ // Gonna be a problem in 2025...
					String[] splitInput = userInput.split("\\+");
					if(splitInput.length==1)
						BlueAlliance.downloadRaiderEvents(outputDirectory);
					else BlueAlliance.downloadRaiderEvents(outputDirectory, Integer.parseInt(splitInput[1]));
					
				}
				
				else if(!BlueAlliance.downloadEventData(outputDirectory, userInput)) //Invalid event code
					JOptionPane.showMessageDialog(eventCodePrompt, "Invalid event code. Please try again", "Error", JOptionPane.PLAIN_MESSAGE);
				
	
			}
		});
	}
	
	public static void main(String[] args){
		
		initialize();
	}
}
