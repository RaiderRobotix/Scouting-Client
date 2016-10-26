package org.usfirst.frc.team25.scouting.client.models;


/** TeleOp object model to deserialize JSON data. 
 *  Modify as necessary for each season.
 */
public class TeleOp {

    public TeleOp(int highShots, int lowShots, boolean scoreOuterWorks, boolean scoreCourtyard,
                  boolean scoreBatter, boolean towerBreached, boolean towerScaled) {
        this.highShots = highShots;
        this.lowShots = lowShots;
        this.scoreOuterWorks = scoreOuterWorks;
        this.scoreCourtyard = scoreCourtyard;
        this.scoreBatter = scoreBatter;
        this.towerBreached = towerBreached;
        this.towerScaled = towerScaled;
    }

    int highShots, lowShots;
    boolean scoreOuterWorks, scoreCourtyard, scoreBatter, towerBreached, towerScaled;

    public int getHighShots() {
        return highShots;
    }

    public int getLowShots() {
        return lowShots;
    }

    public boolean isScoreOuterWorks() {
        return scoreOuterWorks;
    }

    public boolean isScoreCourtyard() {
        return scoreCourtyard;
    }

    public boolean isScoreBatter() {
        return scoreBatter;
    }

    public boolean isTowerBreached() {
        return towerBreached;
    }

    public boolean isTowerScaled() {
        return towerScaled;
    }


    public TeleOp(){
        //Default empty constructor for Jackson JSON parsing
    }
}
