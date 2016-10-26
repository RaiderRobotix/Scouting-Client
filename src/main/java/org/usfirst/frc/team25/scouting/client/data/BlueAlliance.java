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
 *
 */
public class BlueAlliance {
	
	
	/** Exports a simple comma delimited sorted file of teams playing at an event.
	 *  Output file intended to be read by Scouting App
	 * @param eventCode Fully qualified event key, i.e. "2016pahat" for Hatsboro-Horsham in 2016
	 * @param fileName File name of output file. Should be a csv.
	 */
	 static void exportSimpleTeamList(String eventCode, String fileName){
		try {
			PrintWriter outputFile = new PrintWriter(fileName);
			String teamList = "";
			ArrayList<Team> teams = Sorters.sortByTeamNum(new ArrayList<Team>(Arrays.asList(Events.getEventTeamsList(eventCode))));
			for(Team team : teams)
	    		teamList+=team.team_number + ",";
			StringBuilder output = new StringBuilder(teamList);
			output.setCharAt(output.length()-1, ' ');
			
			outputFile.write(output.toString());
			outputFile.close();
	    	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	/** Exports a comma and line break delimited file of team numbers and names at an event. 
	 * Each line contains a comma delimited pair of team number and team nickname.
	 * @param eventCode Fully qualified event key, i.e. "2016pahat" for Hatsboro-Horsham in 2016
	 * @param fileName File name of output file. Should be a csv.
	 */
	
	 static void exportTeamList(String eventCode, String fileName){
		try {
			PrintWriter outputFile = new PrintWriter(fileName);
			String teamList = "";
			
			for(Team team : Sorters.sortByTeamNum(new ArrayList<Team>(Arrays.asList(Events.getEventTeamsList(eventCode)))))
	    		teamList+=team.team_number + ","+team.nickname+",\n";
			
			
			
			outputFile.write(teamList);
			outputFile.close();
	    	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/** Generates a file with list of teams playing in each match
	 * Line number is equal to match number
	 * Each line contains comma delimited team numbers for red alliance, then blue alliance.
	 * @param eventCode Fully qualified event key, i.e. "2016pahat" for Hatsboro-Horsham in 2016
	 * @param fileName File name of output. Should be csv.
	 */
	 static void exportMatchList(String eventCode, String fileName){
		try {
			PrintWriter outputFile = new PrintWriter(fileName);
			String matchList = "";
			for(Match match : Sorters.sortByMatchNum(Sorters.filterQualification(new ArrayList<Match>(Arrays.asList(Events.getEventMatches(eventCode)))))){
				
					matchList+=match.match_number+",";
					for(int i = 0; i < 2; i++)
						for(int j = 0; j < 3; j++)
							//A ternary operator is used here for convenience. TODO fix this unreadable mess 
							matchList+= i==0 ? match.alliances.red.teams[j].split("frc")[1]+",": match.alliances.blue.teams[j].split("frc")[1]+",";
					matchList+=",\n";
				
				
			}
	    	
			outputFile.write(matchList);
			outputFile.close();
	    	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/** Gets all 
	 * 
	 * @param outputFolder
	 */
	public static void downloadRaiderEvents(File outputFolder){
		
		for(Event event : Teams.getTeamEvents("frc25", Calendar.getInstance().get(Calendar.YEAR)))
			downloadEventData(outputFolder, event.key);
		
	}
	
	public static boolean downloadEventData(File outputFolder, String eventCode){
		try{
			exportSimpleTeamList(eventCode, outputFolder.getAbsolutePath()+"\\Teams - " + Events.getEvent(eventCode).short_name + ".csv");
			exportTeamList(eventCode, outputFolder.getAbsolutePath()+"\\TeamNames - " + Events.getEvent(eventCode).short_name + ".csv");
		}catch(Exception e){
			return false;
		}
		exportMatchList(eventCode, outputFolder.getAbsolutePath()+"\\Matches - " + Events.getEvent(eventCode).short_name + ".csv");
		return true;
	}
	
}
