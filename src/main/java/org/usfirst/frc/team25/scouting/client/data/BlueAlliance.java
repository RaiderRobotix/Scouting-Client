package org.usfirst.frc.team25.scouting.client.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import com.adithyasairam.tba4j.Events;
import com.adithyasairam.tba4j.Teams;
import com.adithyasairam.tba4j.models.Event;
import com.adithyasairam.tba4j.models.Match;
import com.adithyasairam.tba4j.models.Team;


/** Class of static methods used to interface with online data from The Blue Alliance
 *  Utilizes TBA Java API written by Adithya Sairam
 *  @author sng
 */
public class BlueAlliance {
	
	
	/** Exports a simple comma delimited sorted file of teams playing at an event.
	 *  Output file intended to be read by Scouting App
	 * @param eventCode Fully qualified event key, i.e. "2016pahat" for Hatsboro-Horsham in 2016
	 * @param fileName File name of output file, without extension
	 */
	 static void exportSimpleTeamList(String eventCode, String fileName){
		
		
			String teamList = "";
			ArrayList<Team> teams = Sorters.sortByTeamNum(new ArrayList<Team>(Arrays.asList(Events.getEventTeamsList(eventCode))));
			for(Team team : teams)
	    		teamList+=team.team_number + ",";
			StringBuilder output = new StringBuilder(teamList);
			output.setCharAt(output.length()-1, ' ');
			
			FileManager.outputFile(fileName, "csv", teamList);
		
		
	}
	
	/** Exports a comma and line break delimited file of team numbers and names at an event. 
	 * Each line contains a comma delimited pair of team number and team nickname.
	 * @param eventCode Fully qualified event key, i.e. "2016pahat" for Hatsboro-Horsham in 2016
	 * @param fileName File name of output file, without extension
	 */
	
	 static void exportTeamList(String eventCode, String fileName){
		
		String teamList = "";
			
			for(Team team : Sorters.sortByTeamNum(new ArrayList<Team>(Arrays.asList(Events.getEventTeamsList(eventCode)))))
	    		teamList+=team.team_number + ","+team.nickname+",\n";
		FileManager.outputFile(fileName, "csv", teamList);
		
	}
	
	/** Generates a file with list of teams playing in each match
	 * Each line contains comma delimited match number, then team numbers for red alliance, then blue alliance.
	 * @param eventCode Fully qualified event key, i.e. "2016pahat" for Hatsboro-Horsham in 2016
	 * @param fileName File name of output, without extension
	 */
	 static void exportMatchList(String eventCode, String fileName){
		String matchList = "";
		for(Match match : Sorters.sortByMatchNum(Sorters.filterQualification(new ArrayList<Match>(Arrays.asList(Events.getEventMatches(eventCode)))))){
			
				matchList+=match.match_number+",";
				for(int i = 0; i < 2; i++) //iterate through two alliances
					for(int j = 0; j < 3; j++) //iterate through teams in alliance
						//A ternary operator is used here for convenience. TODO fix this unreadable mess 
						matchList+= i==0 ? match.alliances.red.teams[j].split("frc")[1]+",": match.alliances.blue.teams[j].split("frc")[1]+",";
				matchList+=",\n";
			
			
		}
		FileManager.outputFile(fileName, "csv", matchList);
		
	}

	/** Downloads all data from events that Team 25 is playing in for the current calendar year  
	 * @param outputFolder Output folder for downloaded files
	 */
	public static void downloadRaiderEvents(File outputFolder){
		
		for(Event event : Teams.getTeamEvents("frc25", Calendar.getInstance().get(Calendar.YEAR)))
			downloadEventData(outputFolder, event.key);
		
	}
	
	/** Downloads all data from events that Team 25 is playing in for the specified year
	 * @param outputFolder Output folder for downloaded files
	 */
	public static void downloadRaiderEvents(File outputFolder, int year){
		
		for(Event event : Teams.getTeamEvents("frc25", year))
			downloadEventData(outputFolder, event.key);
		
	}
	
	/** Downloads the team lists and match lists for an FRC event
	 * Generates appropriate file names from TBA based on the short_name of the event
	 * @param outputFolder Output folder for downloaded files
	 * @param eventCode Fully qualified event key
	 * @return True if download of team list is successful, false otherwise
	 */
	public static boolean downloadEventData(File outputFolder, String eventCode){
		try{
			exportSimpleTeamList(eventCode, outputFolder.getAbsolutePath()+"\\Teams - " + Events.getEvent(eventCode).short_name);
			exportTeamList(eventCode, outputFolder.getAbsolutePath()+"\\TeamNames - " + Events.getEvent(eventCode).short_name);
		}catch(Exception e){
			return false;
		}
		exportMatchList(eventCode, outputFolder.getAbsolutePath()+"\\Matches - " + Events.getEvent(eventCode).short_name);
		return true;
	}
	
}
