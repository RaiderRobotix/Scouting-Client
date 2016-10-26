package org.usfirst.frc.team25.scouting.client.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.usfirst.frc.team25.scouting.client.models.ScoutEntry;

public class EventReport {
	
	ArrayList<ScoutEntry> scoutEntries;
	File teamNameList;
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
	
	public void generateReports(){
		//Iterates through the HashMap
				for (TeamReport report : teamReports.values()) {
				    report.autoGetTeamName(teamNameList);
				    
				}
				
		
		
	}

}
