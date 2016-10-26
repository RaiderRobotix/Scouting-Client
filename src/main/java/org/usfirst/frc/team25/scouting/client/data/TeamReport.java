package org.usfirst.frc.team25.scouting.client.data;

import java.io.File;
import java.util.ArrayList;

import org.usfirst.frc.team25.scouting.client.models.ScoutEntry;

public class TeamReport {
	
	private int teamNum;
	private ArrayList<ScoutEntry> entries;
	String teamName;
	
	public TeamReport(int teamNum){
		this.teamNum = teamNum;
		entries = new ArrayList<ScoutEntry>();
	}
	
	public void autoGetTeamName(File dataDirectory){
		String data = FileManager.getFileString(dataDirectory);
		String[] values = data.split(",\n");
		for(int i = 0; i < values.length; i++){
			
			if(values[i].split(",")[0].equals(Integer.toString(teamNum))){
				teamName = values[i].split(",")[1];
				
				return;
			}
		}
		
			
	}
	
	public int getTeamNum(){
		return teamNum;
	}
	
	public void addEntry(ScoutEntry entry){
		entries.add(entry);
	}
	
	public void generateSpreadsheet(){
		
	}
	
	public void generateReport(){
		
	}

}
