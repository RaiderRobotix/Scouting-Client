package org.usfirst.frc.team25.scouting.client.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import org.usfirst.frc.team25.scouting.client.data.Statistics;

public class ScoutEntry implements Serializable {

    public PreMatch preMatch;
    public Autonomous auto;
    public TeleOp teleOp;
    public PostMatch postMatch;
    public transient int autoScore, teleScore, totalScore, autoKpa, teleOpKpa;
    public transient double pointsPerCycle;

    //Actual member variables will be set using setters as data is filled in
    public ScoutEntry() {}

    public PreMatch getPreMatch() {
        return preMatch;
    }

    public void setPreMatch(PreMatch preMatch) {
        this.preMatch = preMatch;
    }

    public Autonomous getAuto() {
        return auto;
    }

    public void setAuto(Autonomous auto) {
        this.auto = auto;
    }

    public TeleOp getTeleOp() {
        return teleOp;
    }

    public void setTeleOp(TeleOp teleOp) {
        this.teleOp = teleOp;
    }

    public PostMatch getPostMatch() {
        return postMatch;
    }

    public void setPostMatch(PostMatch postMatch) {
        this.postMatch = postMatch;
    }

	

	public void calculateDerivedStats() {
		autoKpa = auto.highGoals+auto.lowGoals/3;
		teleOpKpa = teleOp.highGoals/3+teleOp.lowGoals/9;
		autoScore = (auto.baselineCrossed ? 5 : 0)+auto.gearsDelivered*40+autoKpa;
		teleScore = teleOp.gearsDelivered*18+teleOpKpa+(teleOp.readyTakeoff ? 50 : 0);
		totalScore = autoScore+teleScore;
		if(teleOp.readyTakeoff)
			pointsPerCycle = ((double) teleScore-50)/teleOp.numCycles;
		else pointsPerCycle = ((double) teleScore)/teleOp.numCycles;
		
	}


}
