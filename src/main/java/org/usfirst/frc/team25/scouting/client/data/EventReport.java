package org.usfirst.frc.team25.scouting.client.data;

import java.io.File;
import java.util.ArrayList;

import org.usfirst.frc.team25.scouting.client.models.ScoutEntry;

public class EventReport {
	
	ArrayList<ScoutEntry> scoutEntries;
	File teamNameList;
	ArrayList<TeamReport> teamReports;
	
	public EventReport(ArrayList<ScoutEntry> entry){
		scoutEntries = entry;
	}
	
	public void setTeamNameList(File list){
		teamNameList = list;
	}
	
	public void generateReports(){
		
	}

}
