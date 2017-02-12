package org.usfirst.frc.team25.scouting.client.models;


/** Container holding data from the tele-operated period
 * Includes endgame data
 */
public class TeleOp {


    public int getLowGoals() {
        return lowGoals;
    }

    public void setLowGoals(int lowGoals) {
        this.lowGoals = lowGoals;
    }

    public int getHighGoals() {
        return highGoals;
    }

    public void setHighGoals(int highGoals) {
        this.highGoals = highGoals;
    }

    public int getGearsDelivered() {
        return gearsDelivered;
    }

    public void setGearsDelivered(int gearsDelivered) {
        this.gearsDelivered = gearsDelivered;
    }

    public int getHopppersUsed() {
        return hopppersUsed;
    }

    public void setHopppersUsed(int hopppersUsed) {
        this.hopppersUsed = hopppersUsed;
    }

    public int getRotorsStarted() {
        return rotorsStarted;
    }

    public void setRotorsStarted(int rotorsStarted) {
        this.rotorsStarted = rotorsStarted;
    }

    public boolean isAttemptTakeoff() {
        return attemptTakeoff;
    }

    public void setAttemptTakeoff(boolean attemptTakeoff) {
        this.attemptTakeoff = attemptTakeoff;
    }

    public boolean isReadyTakeoff() {
        return readyTakeoff;
    }

    public void setReadyTakeoff(boolean readyTakeoff) {
        this.readyTakeoff = readyTakeoff;
    }



    int lowGoals, highGoals, gearsDelivered, hopppersUsed,  rotorsStarted, numCycles;
    public int getNumCycles() {
		return numCycles;
	}

	public void setNumCycles(int numCycles) {
		this.numCycles = numCycles;
	}



	boolean attemptTakeoff, readyTakeoff;

    public TeleOp(int lowGoals, int highGoals, int gearsDelivered, int hopppersUsed, int rotorsStarted, 
    		boolean attemptTakeoff, boolean readyTakeoff, int numCycles) {
        this.lowGoals = lowGoals;
        this.highGoals = highGoals;
        this.gearsDelivered = gearsDelivered;
        this.hopppersUsed = hopppersUsed;
        this.rotorsStarted = rotorsStarted;
        this.attemptTakeoff = attemptTakeoff;
        this.readyTakeoff = readyTakeoff;
        this.numCycles = numCycles;
    }
}
