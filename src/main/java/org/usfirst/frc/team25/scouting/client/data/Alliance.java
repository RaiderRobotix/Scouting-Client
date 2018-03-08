package org.usfirst.frc.team25.scouting.client.data;

import java.util.ArrayList;
import java.util.Arrays;

class Alliance {

    private final TeamReport teamOne;
    private final TeamReport teamTwo;
    private final TeamReport teamThree;

    public double atLeastOneBaselinePercent;
    public double atLeastTwoBaselinePercent;
    public double allBaselinePercent;
    public double autoGearPercent;
    public double autoKpa;
    public double teleopKpa;
    public double totalKpa;
    public double atLeastOneTakeoffPercent;
    public double atLeastTwoTakeoffPercent;
    public double allTakeoffPercent;
    public double totalGears;
    public double predictedScore;

    public Alliance(TeamReport teamOne, TeamReport teamTwo, TeamReport teamThree) {
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        this.teamThree = teamThree;
    }


    public void calculateStats() {
        atLeastOneBaselinePercent = Statistics.percentAtLeastOne(teamOne.autoRunPercentage / 100,
                teamTwo.autoRunPercentage / 100, teamThree.autoRunPercentage / 100);
        atLeastTwoBaselinePercent = Statistics.percentAtLeastTwo(teamOne.autoRunPercentage / 100,
                teamTwo.autoRunPercentage / 100, teamThree.autoRunPercentage / 100);
        allBaselinePercent = Statistics.percentAll(teamOne.autoRunPercentage / 100,
                teamTwo.autoRunPercentage / 100, teamThree.autoRunPercentage / 100);
        autoGearPercent = Statistics.percentAtLeastOne(teamOne.avgAutoGears,
                teamTwo.avgAutoGears, teamThree.avgAutoGears);
        autoKpa = teamOne.avgAutoKpa + teamTwo.avgAutoKpa + teamThree.avgAutoKpa;

        teleopKpa = teamOne.avgTeleOpKpa + teamTwo.avgTeleOpKpa + teamThree.avgTeleOpKpa;
        atLeastOneTakeoffPercent = Statistics.percentAtLeastOne(teamOne.takeoffPercentage / 100,
                teamTwo.takeoffPercentage / 100, teamThree.takeoffPercentage / 100);
        atLeastTwoTakeoffPercent = Statistics.percentAtLeastTwo(teamOne.takeoffPercentage / 100,
                teamTwo.takeoffPercentage / 100, teamThree.takeoffPercentage / 100);
        allTakeoffPercent = Statistics.percentAll(teamOne.takeoffPercentage / 100,
                teamTwo.takeoffPercentage / 100, teamThree.takeoffPercentage / 100);
        totalGears = teamOne.avgAutoGears + teamOne.avgTeleOpGears + teamTwo.avgAutoGears
                + teamTwo.avgTeleOpGears + teamThree.avgAutoGears + teamThree.avgTeleOpGears;
        totalKpa = autoKpa + teleopKpa;

        predictedScore = totalKpa;
        Double[] kpaSd = new Double[]{teamOne.sdAutoKpa + teamOne.sdTeleOpKpa, teamTwo.sdAutoKpa + teamTwo.sdTeleOpKpa, teamThree.sdAutoKpa + teamThree.sdTeleOpKpa};
        ArrayList<Double> temp = new ArrayList<>(Arrays.asList(kpaSd));
        double avgSdKpa = Statistics.average(temp);
        if (predictedScore >= 40 - avgSdKpa)
            predictedScore += 20;
        predictedScore += 60 * (autoGearPercent / 100);
        if (60 * (autoGearPercent / 100) < 40) //automatic 40 points
            predictedScore += 40 - 60 * (autoGearPercent / 100);
        predictedScore += 5 * (teamOne.autoRunPercentage / 100 + teamTwo.autoRunPercentage / 100 + teamThree.autoRunPercentage / 100);
        predictedScore += 50 * (teamOne.takeoffPercentage / 100 + teamTwo.takeoffPercentage / 100 + teamThree.takeoffPercentage / 100);
        if (totalGears >= 11.8) // four rotors
            predictedScore += 220;
        else if (totalGears >= 5.8) //three rotors
            predictedScore += 80;
        else if (totalGears >= 1.8) // two rotors
            predictedScore += 40;
    }
}
