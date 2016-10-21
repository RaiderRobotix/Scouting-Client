package org.usfirst.frc.team25.scouting.client.data;

import java.io.File;
import java.util.ArrayList;

import org.usfirst.frc.team25.scouting.client.models.ScoutEntry;

public class TeamReport {
	
	private int teamNum;
	private ArrayList<ScoutEntry> entries;
	String teamName = null;
	
	public TeamReport(int teamNum){
		this.teamNum = teamNum;
	}
	
	public void autoGetTeamName(File dataDirectory){
		
	}
	
	public void addEntry(ScoutEntry entry){
		entries.add(entry);
	}
	
	public void generateSpreadsheet(){
		
	}
	
	public void generateReport(){
		
	}

}
