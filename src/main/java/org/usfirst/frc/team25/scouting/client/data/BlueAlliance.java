package org.usfirst.frc.team25.scouting.client.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import com.thebluealliance.api.v3.TBA;
import com.thebluealliance.api.v3.models.Event;
import com.thebluealliance.api.v3.models.Match;
import com.thebluealliance.api.v3.models.Team;



/** Class of static methods used to interface with online data from The Blue Alliance
 *  @author sng
 */
public class BlueAlliance {
	
	
	/** Exports a simple comma delimited sorted file of teams playing at an event.
	 *  Output file intended to be read by Scouting App
	 * @param eventCode Fully qualified event key, i.e. "2016pahat" for Hatboro-Horsham in 2016
	 * @param fileName File name of output file, without extension
	 */
	 static void exportSimpleTeamList(String eventCode, String fileName, TBA tba) throws FileNotFoundException{
		
		
			String teamList = "";
			ArrayList<Team> teams;
			try {
				teams = Sorters.sortByTeamNum(new ArrayList<Team>(Arrays.asList(tba.eventRequest.getTeams(eventCode))));
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			for(Team team : teams)
	    		teamList+=team.getTeamNumber() + ",";
			StringBuilder output = new StringBuilder(teamList);
			output.setCharAt(output.length()-1, ' ');
			
			FileManager.outputFile(fileName, "csv", teamList);
		
		
	}
	
	/** Exports a comma and line break delimited file of team numbers and names at an event. 
	 * Each line contains a comma delimited pair of team number and team nickname.
	 * @param eventCode Fully qualified event key, i.e. "2016pahat" for Hatboro-Horsham in 2016
	 * @param fileName File name of output file, without extension
	 */
	
	 static void exportTeamList(String eventCode, String fileName, TBA tba)throws FileNotFoundException{
		
		String teamList = "";
			
			try {
				for(Team team : Sorters.sortByTeamNum(new ArrayList<Team>(Arrays.asList(tba.eventRequest.getTeams(eventCode)))))
					teamList+=team.getTeamNumber() + ","+team.getNickname()+",\n";
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		FileManager.outputFile(fileName, "csv", teamList);
		
	}
	
	/** Generates a file with list of teams playing in each match
	 * Each line contains comma delimited match number, then team numbers for red alliance, then blue alliance.
	 * @param eventCode Fully qualified event key, i.e. "2016pahat" for Hatboro-Horsham in 2016
	 * @param fileName File name of output, without extension
	 */
	 static void exportMatchList(String eventCode, String fileName, TBA tba) throws FileNotFoundException{
		String matchList = "";
		try {
			for(Match match : Sorters.sortByMatchNum(Sorters.filterQualification(new ArrayList<Match>(Arrays.asList(tba.eventRequest.getMatches(eventCode)))))){
				
					matchList+=match.getMatchNumber()+",";
					for(int i = 0; i < 2; i++) //iterate through two alliances
						for(int j = 0; j < 3; j++){ //iterate through teams in alliance
							if(i==0) 
								matchList+=match.getRedAlliance().getTeamKeys()[j].split("frc")[1]+",";
							else matchList+=match.getBlueAlliance().getTeamKeys()[j].split("frc")[1]+",";
						}
					matchList+=",\n";
				
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		FileManager.outputFile(fileName, "csv", matchList);
		
	}

	/** Downloads all data from events that Team 25 is playing in for the current calendar year  
	 * @param outputFolder Output folder for downloaded files
	 */
	public static void downloadRaiderEvents(File outputFolder, TBA tba){
		
		try {
			for(Event event : tba.teamRequest.getEvents(25, Calendar.getInstance().get(Calendar.YEAR)))
				downloadEventTeamData(outputFolder, event.getKey(), tba);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/** Downloads all data from events that Team 25 is playing in for the specified year
	 * @param outputFolder Output folder for downloaded files
	 */
	public static void downloadRaiderEvents(File outputFolder, int year, TBA tba){
		
		try {
			for(Event event : tba.teamRequest.getEvents(25, year))
				downloadEventTeamData(outputFolder, event.getKey(), tba);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/** Downloads the team lists and match lists for an FRC event
	 * Generates appropriate file names from TBA based on the short_name of the event
	 * @param outputFolder Output folder for downloaded files
	 * @param eventCode Fully qualified event key
	 * @return True if download of team list is successful, false otherwise
	 */
	public static boolean downloadEventTeamData(File outputFolder, String eventCode, TBA tba){
		try{
			String eventShortName = tba.eventRequest.getEvent(eventCode).getKey();
			exportSimpleTeamList(eventCode, outputFolder.getAbsolutePath()+"\\Teams - " + eventShortName, tba);
			exportTeamList(eventCode, outputFolder.getAbsolutePath()+"\\TeamNames - " + eventShortName, tba);
			exportMatchList(eventCode, outputFolder.getAbsolutePath()+"\\Matches - " + eventShortName, tba);
		}catch(Exception e){
			return false;
		}
		
		return true;
	}
	
	public static ArrayList<Match> downloadEventMatchData( String eventCode, TBA tba) throws IOException{
		return Sorters.sortByMatchNum(Sorters.filterQualification(new ArrayList<Match>(Arrays.asList(tba.eventRequest.getMatches(eventCode)))));
	}
	
	
}
