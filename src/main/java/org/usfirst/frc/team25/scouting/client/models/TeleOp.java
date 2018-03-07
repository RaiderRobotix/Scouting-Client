package org.usfirst.frc.team25.scouting.client.models;


/** Container holding data from the tele-operated period
 * Includes endgame data
 */
public class TeleOp {

	private float firstCubeTime;
    private int ownSwitchCubes, scaleCubes, opponentSwitchCubes,
            exchangeCubes, cubesDropped, climbsAssisted;
    private boolean parked, attemptRungClimb, successfulRungClimb, otherRobotClimb;
    private String otherRobotClimbType, fieldLayout;
	
    public float getFirstCubeTime() {
        return firstCubeTime;
    }

    public int getOwnSwitchCubes() {
        return ownSwitchCubes;
    }

    public int getScaleCubes() {
        return scaleCubes;
    }

    public int getOpponentSwitchCubes() {
        return opponentSwitchCubes;
    }

    public int getExchangeCubes() {
        return exchangeCubes;
    }

    public int getCubesDropped() {
        return cubesDropped;
    }

    public int getClimbsAssisted() {
        return climbsAssisted;
    }

    public boolean isParked() {
        return parked;
    }

    public boolean isAttemptRungClimb() {
        return attemptRungClimb;
    }

    public boolean isSuccessfulRungClimb() {
        return successfulRungClimb;
    }

    public boolean isOtherRobotClimb() {
        return otherRobotClimb;
    }

    public String getOtherRobotClimbType() {
        return otherRobotClimbType;
    }

    public String getFieldLayout() {
        return fieldLayout;
    }

    public TeleOp(float firstCubeTime, int ownSwitchCubes, int scaleCubes, int opponentSwitchCubes, 
    		int exchangeCubes, int cubesDropped, int climbsAssisted, boolean parked,
            boolean attemptRungClimb, boolean successfulRungClimb, boolean otherRobotClimb, String otherRobotClimbType, String fieldLayout) {
        this.firstCubeTime = firstCubeTime;
        this.ownSwitchCubes = ownSwitchCubes;
        this.scaleCubes = scaleCubes;
        this.opponentSwitchCubes = opponentSwitchCubes;
        this.exchangeCubes = exchangeCubes;
        this.cubesDropped = cubesDropped;
        this.climbsAssisted = climbsAssisted;
        this.parked = parked;
        this.attemptRungClimb = attemptRungClimb;
        this.successfulRungClimb = successfulRungClimb;
        this.otherRobotClimb = otherRobotClimb;
        this.otherRobotClimbType = otherRobotClimbType;
        this.fieldLayout = fieldLayout;
    }

    

}
