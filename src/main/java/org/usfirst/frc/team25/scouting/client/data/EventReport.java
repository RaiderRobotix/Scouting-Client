package org.usfirst.frc.team25.scouting.client.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.usfirst.frc.team25.scouting.client.models.Autonomous;
import org.usfirst.frc.team25.scouting.client.models.PreMatch;
import org.usfirst.frc.team25.scouting.client.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.client.models.TeleOp;

import com.google.gson.Gson;

/** Object model holding all data for an event
 * 
 * @author sng
 *
 */
public class EventReport {
	
	/** Unsorted list of ScoutEntrys TODO create method to sort them
	 * 
	 */
	ArrayList<ScoutEntry> scoutEntries;
	File teamNameList;
	String event;
	
	/** Dictionary of TeamReports, based on team number
	 */
	HashMap<Integer, TeamReport> teamReports = new HashMap<Integer, TeamReport>(); 
	
	public EventReport(ArrayList<ScoutEntry> entries){
		scoutEntries = entries;
		for(ScoutEntry entry : scoutEntries){
			int teamNum = entry.getPreMatch().getTeamNum();
			if(!teamReports.containsKey(teamNum))
				teamReports.put(teamNum, new TeamReport(teamNum));
			
			teamReports.get(entry.getPreMatch().getTeamNum()).addEntry(entry);
		}
		event = scoutEntries.get(0).getPreMatch().getCurrentEvent();
		
		
	}
	
	
	
	public void setTeamNameList(File list){
		teamNameList = list;
	}
	
	
	/** Generates all LaTeX-based PDF summary reports for the current event
	 * @param outputDirectory Output directory for generated files
	 */
	public void generateReports(File outputDirectory){
		//Iterates through the HashMap
		for (TeamReport report : teamReports.values()) {
		    report.autoGetTeamName(teamNameList);
		    report.generateReport(outputDirectory);
		}
		
	}
	
	/** Generates summary and team Excel spreadsheets 
	 * 
	 * @param outputDirectory Output directory for generated fields
	 */
	public void generateSpreadsheet(File outputDirectory){
		final String COMMA = ",";
		String header = "";
		String fileContents = header + "\n";
		for(ScoutEntry entry : scoutEntries){
			PreMatch pm = entry.getPreMatch();
			Autonomous auto = entry.getAuto();
			TeleOp tele = entry.getTeleOp();
			
			fileContents+=pm.getMatchNum()+COMMA+pm.getTeamNum()+COMMA;
			fileContents+=auto.getHighShots()+COMMA+auto.getLowShots()+COMMA;
			fileContents+=tele.getHighShots()+COMMA+tele.getLowShots()+COMMA+tele.isTowerBreached()+COMMA+tele.isTowerScaled()+COMMA;
			fileContents+=entry.getPostMatch().comment+COMMA+'\n';
			
			
		}
				
		
		FileManager.outputFile(outputDirectory.getAbsolutePath() + "\\Data - All - " + event , "csv", fileContents);
	}
	
	/** Serializes the ArrayList of all ScoutEntrys into a JSON file
	 * @param outputDirectory
	 */
	public void generateCombineJson(File outputDirectory){
		Gson gson = new Gson();
		String jsonString = gson.toJson(scoutEntries);
		FileManager.outputFile(outputDirectory.getAbsolutePath() + "\\Data - All - " + event , "json", jsonString);
	}

}
