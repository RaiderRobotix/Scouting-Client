package org.usfirst.frc.team25.scouting.client.models;

public class Comparison {
	
	private int lowerTeam, higherTeam;
	private String compareChar;
	
	public Comparison(int teamOne, int teamTwo, String compareChar){
		if (teamTwo > teamOne){
			this.lowerTeam = teamOne;
			this.higherTeam = teamTwo;
			this.compareChar = compareChar;
		}
		else{
			this.lowerTeam = teamTwo;
			this.higherTeam = teamOne;
			if(compareChar.equals("<"))
				this.compareChar = ">";
			else if(compareChar.equals(">"))
				this.compareChar = "<";
			else this.compareChar = "=";
		}
	}
	
	public boolean contains(int teamNum){
		return lowerTeam == teamNum || higherTeam == teamNum;
	}
	
	public int getBetterTeam(){
		if(compareChar.equals("<"))
			return higherTeam;
		if(compareChar.equals(">"))
			return lowerTeam;
		return 0;
	}
	
	public int getWorseTeam(){
		if(compareChar.equals("<"))
			return lowerTeam;
		if(compareChar.equals(">"))
			return higherTeam;
		return 0;
	}
	
	public int getLowerTeam(){
		return lowerTeam;
	}
	
	public int getHigherTeam(){
		return higherTeam;
	}
	
	public void printString(){
		System.out.println(lowerTeam + compareChar + higherTeam);
	}

}
