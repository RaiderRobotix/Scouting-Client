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

public class BlueAlliance {
	
	public static ArrayList<Team> sortByTeamNum(ArrayList<Team> events){
		
		Collections.sort(events, new Comparator<Team>(){
			public int compare(Team t1, Team t2){
				return t1.team_number - t2.team_number;
			}
		});
		return events;
	}
	
	public static ArrayList<Match> filterQualification(ArrayList<Match> matches){
		for(int i = 0; i < matches.size(); i++){
			
			if(!matches.get(i).comp_level.equals("qm")){
				matches.remove(i);
				i--;
			}
		}
		return matches;
	}
	
	public static ArrayList<Match> sortByMatchNum(ArrayList<Match> matches){
		
		Collections.sort(matches, new Comparator<Match>(){
			public int compare(Match m1, Match m2){
				return m1.match_number - m2.match_number;
			}
		});
		return matches;
	}
	
	public static void exportSimpleTeamList(String eventCode, String fileName){
		try {
			PrintWriter outputFile = new PrintWriter(fileName);
			String teamList = "";
			ArrayList<Team> teams = sortByTeamNum(new ArrayList<Team>(Arrays.asList(Events.getEventTeamsList(eventCode))));
			for(Team team : teams)
	    		teamList+=team.team_number + ",";
			StringBuilder output = new StringBuilder(teamList);
			output.setCharAt(output.length()-1, ' ');
			
			outputFile.write(output.toString());
			outputFile.close();
	    	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void exportTeamList(String eventCode, String fileName){
		try {
			PrintWriter outputFile = new PrintWriter(fileName);
			String teamList = "";
			for(Team team : sortByTeamNum(new ArrayList<Team>(Arrays.asList(Events.getEventTeamsList(eventCode)))))
	    		teamList+=team.team_number + ","+team.nickname+"\n";
			
			
			
			outputFile.write(teamList);
			outputFile.close();
	    	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void exportMatchList(String eventCode, String fileName){
		try {
			PrintWriter outputFile = new PrintWriter(fileName);
			String matchList = "";
			for(Match match : sortByMatchNum(filterQualification(new ArrayList<Match>(Arrays.asList(Events.getEventMatches(eventCode)))))){
				
					matchList+=match.match_number+",";
					for(int i = 0; i < 2; i++)
						for(int j = 0; j < 3; j++)
							matchList+= i==0 ? match.alliances.red.teams[j].split("frc")[1]+",": match.alliances.blue.teams[j].split("frc")[1]+",";
					matchList+="\n";
				
				
			}
	    	
			outputFile.write(matchList);
			outputFile.close();
	    	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

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
