package org.usfirst.frc.team25.scouting.client.data;

import java.util.ArrayList;
import java.util.Arrays;

public class Alliance {
	
	TeamReport teamOne, teamTwo, teamThree;
	
	public double atLeastOneBaselinePercent, atLeastTwoBaselinePercent, allBaselinePercent,
		autoGearPercent, autoKpa, teleopKpa, totalKpa, atLeastOneTakeoffPercent, atLeastTwoTakeoffPercent,
		allTakeoffPercent, totalGears, avgSdKpa, predictedScore;

	public Alliance(TeamReport teamOne, TeamReport teamTwo, TeamReport teamThree){
		this.teamOne = teamOne;
		this.teamTwo = teamTwo;
		this.teamThree = teamThree;
	}
	
	
	public void calculateStats(){
		atLeastOneBaselinePercent = Statistics.percentAtLeastOne(teamOne.reachBaselinePercentage/100, 
				teamTwo.reachBaselinePercentage/100, teamThree.reachBaselinePercentage/100);
		atLeastTwoBaselinePercent = Statistics.percentAtLeastTwo(teamOne.reachBaselinePercentage/100, 
				teamTwo.reachBaselinePercentage/100, teamThree.reachBaselinePercentage/100);
		allBaselinePercent = Statistics.percentAll(teamOne.reachBaselinePercentage/100, 
				teamTwo.reachBaselinePercentage/100, teamThree.reachBaselinePercentage/100);
		autoGearPercent = Statistics.percentAtLeastOne(teamOne.avgAutoGears,
				teamTwo.avgAutoGears, teamThree.avgAutoGears);
		autoKpa = teamOne.avgAutoKpa+teamTwo.avgAutoKpa+teamThree.avgAutoKpa;
		
		teleopKpa = teamOne.avgTeleOpKpa+teamTwo.avgTeleOpKpa+teamThree.avgTeleOpKpa;
		atLeastOneTakeoffPercent = Statistics.percentAtLeastOne(teamOne.takeoffPercentage/100, 
				teamTwo.takeoffPercentage/100, teamThree.takeoffPercentage/100);
		atLeastTwoTakeoffPercent=Statistics.percentAtLeastTwo(teamOne.takeoffPercentage/100, 
				teamTwo.takeoffPercentage/100, teamThree.takeoffPercentage/100);
		allTakeoffPercent = Statistics.percentAll(teamOne.takeoffPercentage/100, 
				teamTwo.takeoffPercentage/100, teamThree.takeoffPercentage/100);
		totalGears = teamOne.avgAutoGears+teamOne.avgTeleOpGears+teamTwo.avgAutoGears
				+teamTwo.avgTeleOpGears+teamThree.avgAutoGears+teamThree.avgTeleOpGears+1; //include reserve gear
		totalKpa = autoKpa+teleopKpa;
		
		predictedScore = totalKpa;
		Double[] kpaSd = new Double[] {teamOne.sdAutoKpa+teamOne.sdTeleOpKpa,teamTwo.sdAutoKpa+teamTwo.sdTeleOpKpa,teamThree.sdAutoKpa+teamThree.sdTeleOpKpa};
		ArrayList<Double> temp = new ArrayList<Double>(Arrays.asList(kpaSd));
		avgSdKpa = Statistics.average(temp);
		if(predictedScore>=40-avgSdKpa)
				predictedScore+=20;
		predictedScore+=60*(autoGearPercent/100); 
		if(60*(autoGearPercent/100)<40) //automatic 40 points
			predictedScore+=40-60*(autoGearPercent/100);
		predictedScore+=5*(teamOne.reachBaselinePercentage/100+teamTwo.reachBaselinePercentage/100+teamThree.reachBaselinePercentage/100);
		predictedScore+=50*(teamOne.takeoffPercentage/100+teamTwo.takeoffPercentage/100+teamThree.takeoffPercentage/100);
		if(totalGears>=12.8) // four rotors
			predictedScore+=220;
		else if(totalGears>=6.8) //three rotors
			predictedScore+=80;
		else if(totalGears>=2.8) // two rotors
			predictedScore+=40;
	}
}
