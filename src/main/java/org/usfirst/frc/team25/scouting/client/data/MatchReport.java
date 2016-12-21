package org.usfirst.frc.team25.scouting.client.data;

import org.usfirst.frc.team25.scouting.client.models.ScoutEntry;

public class MatchReport {

	int matchNum;
	ScoutEntry[][] alliances = new ScoutEntry[2][3];
	int redScore, blueScore;
	
	
	public MatchReport(int matchNum){
		
	}
	
	public void addEntry(ScoutEntry entry){
		String pos = entry.getPreMatch().getScoutPos();
		int allianceIndex, posIndex;
		if(pos.split(" ")[0].equals("Red"))
			allianceIndex = 0;
		else allianceIndex = 1;
		
		posIndex = Integer.parseInt(pos.split(" ")[1])-1;
		
		alliances[allianceIndex][posIndex] = entry;
		
	}
	
	public void updateScores(){
		redScore = blueScore = 0;
		for(int i = 0; i < 3; i++)
			redScore+=alliances[0][i].getScore();
		for(int i = 0; i < 3; i++)
			blueScore+=alliances[1][i].getScore();
	}
}
