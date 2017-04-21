package org.usfirst.frc.team25.scouting.client.models;





/** Object model for autonomous period of a match
 *
 */
public class Autonomous {

    public Autonomous(boolean baselineCrossed, boolean useHoppers, int highGoals, int lowGoals, int rotorsStarted, int gearsDelivered,
            boolean shootsFromKey, String gearPeg, boolean attemptGear, boolean successGear) {
		this.baselineCrossed = baselineCrossed;
		this.useHoppers = useHoppers;
		this.highGoals = highGoals;
		this.lowGoals = lowGoals;
		this.gearPeg = gearPeg;
		this.attemptGear = attemptGear;
		this.successGear = successGear;
    }


    public boolean isBaselineCrossed() {
        return baselineCrossed;
    }

    public void setBaselineCrossed(boolean baselineCrossed) {
        this.baselineCrossed = baselineCrossed;
    }

    public boolean isUseHoppers() {
        return useHoppers;
    }

    public void setUseHoppers(boolean useHoppers) {
        this.useHoppers = useHoppers;
    }

    public int getHighGoals() {
        return highGoals;
    }

    public void setHighGoals(int highGoals) {
        this.highGoals = highGoals;
    }

    public int getLowGoals() {
        return lowGoals;
    }

    public void setLowGoals(int lowGoals) {
        this.lowGoals = lowGoals;
    }



    

    boolean baselineCrossed;
    boolean useHoppers;



    public boolean isAttemptGear() {
		return attemptGear;
	}


	public void setAttemptGear(boolean attemptGear) {
		this.attemptGear = attemptGear;
	}


	public boolean isSuccessGear() {
		return successGear;
	}


	public void setSuccessGear(boolean successGear) {
		this.successGear = successGear;
	}





	boolean attemptGear, successGear;
    int highGoals, lowGoals;

    public String getGearPeg() {
        return gearPeg;
    }

    public void setGearPeg(String gearPeg) {
        this.gearPeg = gearPeg;
    }

    public String gearPeg;



}
