package org.usfirst.frc.team25.scouting.client.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.usfirst.frc.team25.scouting.client.models.ScoutEntry;

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
	
	/** Dictionary of TeamReports, based on team number
	 * 
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
		
		
	}
	
	public void setTeamNameList(File list){
		teamNameList = list;
	}
	
	/** Generates all LaTeX-based PDF summary reports for the current event
	 * TODO finish this
	 * @param outputDirectory Output directory for generated files
	 */
	public void generateReports(File outputDirectory){
		//Iterates through the HashMap
		for (TeamReport report : teamReports.values()) {
		    report.autoGetTeamName(teamNameList);
		    report.generateReport(outputDirectory);
		}
		
	}
	
	/** Serializes the ArrayList of all ScoutEntrys into a JSON file
	 * TODO finish this
	 * @param outputDirectory
	 */
	public void generateCombineJson(File outputDirectory){
		
	}

}
