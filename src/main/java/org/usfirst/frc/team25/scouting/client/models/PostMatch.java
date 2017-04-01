package org.usfirst.frc.team25.scouting.client.models;

import java.util.ArrayList;
import java.util.HashMap;

/** Qualitative reflection on the robot's performance after a match
 *  Not to be used for end game actions
 */
public class PostMatch {
	

    
    HashMap<String, Boolean> robotQuickCommentSelections = new HashMap<>();
    HashMap<String, Boolean>pilotQuickCommentSelections = new HashMap<>();


    public HashMap<String, Boolean> getRobotQuickCommentSelections() {
		return robotQuickCommentSelections;
	}

	public void setRobotQuickCommentSelections(HashMap<String, Boolean> robotQuickCommentSelections) {
		this.robotQuickCommentSelections = robotQuickCommentSelections;
	}

	public HashMap<String, Boolean> getPilotQuickCommentSelections() {
		return pilotQuickCommentSelections;
	}

	public void setPilotQuickCommentSelections(HashMap<String, Boolean> pilotQuickCommentSelections) {
		this.pilotQuickCommentSelections = pilotQuickCommentSelections;
	}

	public PostMatch(String robotComment, String pilotComment, HashMap<String, Boolean> robotQuickCommentSelections
    		, HashMap<String, Boolean> pilotQuickCommentSelections) {
        this.robotComment = robotComment;
        this.pilotComment = pilotComment;
        this.robotQuickCommentSelections = robotQuickCommentSelections;
        this.pilotQuickCommentSelections = pilotQuickCommentSelections;

    }

    public String robotComment, pilotComment;
    public transient String robotQuickCommentStr, pilotQuickCommentStr;


    

    public String getRobotComment() {
        return robotComment;
    }

    public void setRobotComment(String robotComment) {
        this.robotComment = robotComment;
    }

    public String getPilotComment() {
        return pilotComment;
    }

    public void setPilotComment(String pilotComment) {
        this.pilotComment = pilotComment;
    }
    
    void generateQuickCommentStr(){
    	robotQuickCommentStr = pilotQuickCommentStr = "";
    	for(String comment : robotQuickCommentSelections.keySet())
    		if(robotQuickCommentSelections.get(comment))
    			robotQuickCommentStr+=comment+"; ";
    	for(String comment : pilotQuickCommentSelections.keySet())
    		if(pilotQuickCommentSelections.get(comment))
    			pilotQuickCommentStr+=comment+"; ";
    }



}



