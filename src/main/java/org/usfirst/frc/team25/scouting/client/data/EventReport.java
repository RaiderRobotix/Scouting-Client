package org.usfirst.frc.team25.scouting.client.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.usfirst.frc.team25.scouting.client.models.Autonomous;
import org.usfirst.frc.team25.scouting.client.models.PostMatch;
import org.usfirst.frc.team25.scouting.client.models.PreMatch;
import org.usfirst.frc.team25.scouting.client.models.ScoutEntry;
import org.usfirst.frc.team25.scouting.client.models.TeleOp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
	HashMap<Integer, TeamReport> teamReports = new HashMap<Integer, TeamReport>(); 
	
	public EventReport(ArrayList<ScoutEntry> entries){
		scoutEntries = entries;
		for(ScoutEntry entry : scoutEntries){
			entry.calculateDerivedStats();
			
			int teamNum = entry.getPreMatch().getTeamNum();
			if(!teamReports.containsKey(teamNum))
				teamReports.put(teamNum, new TeamReport(teamNum));
			
			teamReports.get(teamNum).addEntry(entry);
		}
		event = scoutEntries.get(0).getPreMatch().getCurrentEvent();
		
		
	}
	
	public void processTeamReports(){
		
		for(Integer key : teamReports.keySet()){
		
			TeamReport report = teamReports.get(key);
			if(teamNameList!=null){
				report.autoGetTeamName(teamNameList);
				System.out.println(key);
			}
			report.calculateStats();
			
			
			teamReports.put(key, report);
		}
		
		
	}
	
	
	
	public void setTeamNameList(File list){
		teamNameList = list;
	}
	
	public void generateTeamReportSpreadsheet(File outputDirectory){
		final String COMMA = ",";
		String header = "teamNum,teamName,avgAutoScore,avgTeleOpScore,avgMatchScore,avgAutoKpa,avgTeleOpKpa,avgAutoGears,avgTeleOpGears,avgTotalFuel,"
				+ "avgHoppers,sdAutoScore,sdTeleOpScore,sdAutoScore,sdTeleOpScore,sdAutoKpa,sdTeleOpKpa,sdAutoGears,sdMatchScore,sdTeleOpGears,sdTotalFuel,"
				+ "takeoffAttemptPercentage,takeoffPercentage,takeoffAttemptSuccessPercentage,pilotPlayPercentage,avgPointsPerCycle,sdPointsPerCycle,"
				+ "avgCycles,sdCycles,reachBaselinePercentage,avgHighGoals,sdHighGoals,avgLowGoals,sdLowGoals,autoShootsKey,autoAbility,teleOpAbility,"
				+ "driveTeamAbility,robotQualities,firstPickAbility,secondPickAbility,frequentRobotCommentStr,frequentPilotCommentStr,\n";
		
		
		String fileContents = header;
		for(int key : teamReports.keySet()){
			TeamReport report = teamReports.get(key);
			fileContents += report.teamNum+COMMA+report.teamName+COMMA+report.avgAutoScore+COMMA+report.avgTeleOpScore+COMMA+report.avgMatchScore+
					COMMA+report.avgAutoKpa+COMMA+report.avgTeleOpKpa+COMMA+report.avgAutoGears+COMMA+report.avgTeleOpGears+COMMA+report.avgTotalFuel+
					COMMA+report.avgHoppers+COMMA+report.sdAutoScore+COMMA+report.sdTeleOpScore+COMMA+report.sdAutoScore+COMMA+report.sdTeleOpScore+COMMA+
					report.sdAutoKpa+COMMA+report.sdTeleOpKpa+COMMA+report.sdAutoGears+COMMA+report.sdMatchScore+COMMA+report.sdTeleOpGears+COMMA+
					report.sdTotalFuel+COMMA+report.takeoffAttemptPercentage+COMMA+report.takeoffPercentage+COMMA+report.takeoffAttemptSuccessPercentage+
					COMMA+report.pilotPlayPercentage+COMMA+report.avgPointsPerCycle+COMMA+report.sdPointsPerCycle+COMMA+report.avgCycles+COMMA+report.sdCycles+
					COMMA+report.reachBaselinePercentage+COMMA+report.avgHighGoals+COMMA+report.sdHighGoals+COMMA+report.avgLowGoals+COMMA+report.sdLowGoals+COMMA+
					report.autoShootsKey+COMMA+report.autoAbility+COMMA+report.teleOpAbility+COMMA+report.driveTeamAbility+COMMA+report.robotQualities+
					COMMA+report.firstPickAbility+COMMA+report.secondPickAbility+COMMA+report.frequentRobotCommentStr+COMMA+report.frequentPilotCommentStr+COMMA+'\n';		
			
		}
				
		
		try {
			FileManager.outputFile(outputDirectory.getAbsolutePath() + "\\TeamReports - " + event , "csv", fileContents);
		} catch (FileNotFoundException e) {
			// 
			e.printStackTrace();
		}
	}
	
	/** Generates summary and team Excel spreadsheets 
	 * 
	 * @param outputDirectory Output directory for generated fields
	 */
	public void generateRawSpreadsheet(File outputDirectory){
		final String COMMA = ",";
		String header = "Scout Name, Match Num, Scouting Pos, Team Num, Pilot Playing, High goals auto, "
				+ "Low goals auto, Gears auto, Rotors auto, Reached baseline, Hopper used auto, Shoots from key auto,"
				+ "High goals tele, Low goals tele, Gears tele, Rotors tele, Hoppers tele, Cycles, Takeoff attempt,"
				+ "Takeoff success, Robot comment, Pilot comment,";
		ArrayList<String> keys = new ArrayList<>();
		
		

		for(String key : scoutEntries.get(0).getPostMatch().getRobotQuickCommentSelections().keySet()){
			header+=key+",";
			keys.add(key);
		}
		
		
		String fileContents = header + "\n";
		for(ScoutEntry entry : scoutEntries){
			PreMatch pre = entry.getPreMatch();
			Autonomous auto = entry.getAuto();
			TeleOp tele = entry.getTeleOp();
			PostMatch post = entry.getPostMatch(); 
			
			fileContents+=pre.getScoutName()+COMMA + pre.getMatchNum()+COMMA+pre.getScoutPos()+COMMA+
					pre.getTeamNum()+COMMA+pre.isPilotPlaying()+COMMA;
			fileContents+=auto.getHighGoals()+COMMA+auto.getLowGoals()+COMMA+auto.getGearsDelivered()+COMMA+
					auto.getRotorsStarted()+COMMA+auto.isBaselineCrossed()+COMMA+auto.isUseHoppers()+COMMA+
					auto.isShootsFromKey()+COMMA;
			fileContents+=tele.getHighGoals()+COMMA+tele.getLowGoals()+COMMA+tele.getGearsDelivered()+COMMA+
					tele.getRotorsStarted()+COMMA+tele.getHopppersUsed()+COMMA+tele.getNumCycles()+COMMA+tele.isAttemptTakeoff()+
					COMMA+tele.isReadyTakeoff()+COMMA;
			fileContents+=post.getRobotComment()+COMMA+post.getPilotComment()+COMMA;
			
			for(String key : keys)
				fileContents+=post.getRobotQuickCommentSelections().get(key)+COMMA;
			
			
			fileContents+='\n';
			
			
		}
				
		
		try {
			FileManager.outputFile(outputDirectory.getAbsolutePath() + "\\Data - All - " + event , "csv", fileContents);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/** Serializes the ArrayList of all ScoutEntrys into a JSON file
	 * @param outputDirectory
	 * @return true if operation is successful, false otherwise
	 */
	public boolean generateCombineJson(File outputDirectory){
		Gson gson = new Gson();
		String jsonString = gson.toJson(scoutEntries);
		try {
			FileManager.outputFile(outputDirectory.getAbsolutePath() + "\\Data - All - " + event , "json", jsonString);
		} catch (FileNotFoundException e) {
			
			return false;
		}
		return true;
	}
	
	/** Serializes the HashMap of all TeamReports
	 * @param outputDirectory
	 */
	public void generateTeamReportJson(File outputDirectory){

		Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();
		
		ArrayList<TeamReport> teamReportList = new ArrayList<>();
		
		for(int key : teamReports.keySet())
			teamReportList.add(teamReports.get(key));
		
		String jsonString = gson.toJson(teamReportList);
		try {
			FileManager.outputFile(outputDirectory.getAbsolutePath() + "\\TeamReports - " + event , "json", jsonString);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
