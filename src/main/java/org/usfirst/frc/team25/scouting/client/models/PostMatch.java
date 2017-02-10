package org.usfirst.frc.team25.scouting.client.models;

import java.util.ArrayList;

/** Qualitative reflection on the robot's performance after a match
 *  Not to be used for end game actions
 */
public class PostMatch {


    public PostMatch(String robotComment, String pilotComment) {
        this.robotComment = robotComment;
        this.pilotComment = pilotComment;

    }

    String robotComment, pilotComment;


    

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



}



