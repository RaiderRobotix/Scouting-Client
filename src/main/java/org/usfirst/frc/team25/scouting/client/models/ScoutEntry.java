package org.usfirst.frc.team25.scouting.client.models;


import java.io.Serializable;

/** ScoutEntry object model to deserialize JSON data. 
 *  Probably doesn't need to be modified
 */

public class ScoutEntry implements Serializable {

    PreMatch preMatch;
    Autonomous auto;
    TeleOp teleOp;
    PostMatch postMatch;
    int score;

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
    
    public int getScore(){
    	return score;
    }

    public void approximateScore(){
    	score = (auto.reached ? 1:0)*5+(auto.crossed ? 1:0)*10+auto.lowShots*5+auto.highShots*10
    			+teleOp.highShots*5+teleOp.lowShots*5+(teleOp.towerBreached?1:0)*5+(teleOp.towerScaled?1:0)*15;
    }

}
