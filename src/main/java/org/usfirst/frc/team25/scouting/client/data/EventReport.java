package org.usfirst.frc.team25.scouting.client.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thebluealliance.api.v3.TBA;
import com.thebluealliance.api.v3.models.Match;
import com.thebluealliance.api.v3.models.MatchScoreBreakdown2018Alliance;
import org.usfirst.frc.team25.scouting.client.models.*;
import org.usfirst.frc.team25.scouting.client.ui.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Object model holding all data for an event
 *
 * @author sng
 */
public class EventReport {

    /**
     * Unsorted list of ScoutEntrys TODO create method to sort them
     */
    private final ArrayList<ScoutEntry> scoutEntries;
    private final String event;
    private final File directory;
    private final HashMap<Integer, TeamReport> teamReports = new HashMap<>();
    private String inaccuracyList = "";
    private File teamNameList;
    private HashMap<Integer, Integer> pickPoints;

    public EventReport(ArrayList<ScoutEntry> entries, String event, File directory) {
        scoutEntries = entries;
        this.event = event;
        this.directory = directory;
        fixInaccuraciesTBA();
        for (ScoutEntry entry : scoutEntries) {

            entry.calculateDerivedStats();


            int teamNum = entry.getPreMatch().getTeamNum();
            if (!teamReports.containsKey(teamNum))
                teamReports.put(teamNum, new TeamReport(teamNum));

            teamReports.get(teamNum).addEntry(entry);
        }

    }

    /**
     * Helper method to prevent manual comments with commas
     * from changing CSV format
     *
     * @param s String to be processed
     * @return String without commas
     */
    private String removeCommas(String s) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ',')
                newString.append(s.charAt(i));
            else newString.append("; ");
        }
        return newString.toString();
    }

    public boolean isTeamPlaying(int teamNum) {
        for (int i : teamReports.keySet())
            if (teamNum == i)
                return true;
        return false;
    }


    // TODO Update this
    public String quickTeamReport(int teamNum) {
        StringBuilder formatString = new StringBuilder("<html>");
        TeamReport report = teamReports.get(teamNum);

        formatString.append("<h2>Team ").append(teamNum);
        if (report.getTeamName() != null)
            formatString.append(" - ").append(report.getTeamName());
        formatString.append("</h2><h3>Auto</h3>");


//        formatString.append("Cross baseline: ").append(Statistics.round(report.autoRunPercentage, 2)).append("% (").append(report.totalReachBaseline).append("/").append(report.entries.size()).append(")").append("<br>");
        formatString.append("<h3>Tele-Op</h3>");

//        formatString.append("Avg. gears: ").append(Statistics.round(report.avgTeleOpGears, 2)).append("<br>");
//        formatString.append("Gear counts: ");
//

        formatString.append("</html>");
        return formatString.toString();

    }

    //TODO update this
    public String allianceReport(int t1, int t2, int t3) {
        String formatString = "<html>";
        TeamReport r1 = teamReports.get(t1), r2 = teamReports.get(t2), r3 = teamReports.get(t3);

        Alliance a = new Alliance(r1, r2, r3);
        a.calculateStats();

        formatString += "<h2>" + t1 + ", " + t2 + ", " + t3 + "</h2><h3>Auto</h3>";

//        formatString += "1+ BL cross: "
//                + Statistics.round(a.atLeastOneBaselinePercent, 2)
//                + "%<br>";
//        formatString += "2+ BL cross: "
//                + Statistics.round(a.atLeastTwoBaselinePercent, 2)
//                + "%<br>";
//        formatString += "3 BL cross: "
//                + Statistics.round(a.allBaselinePercent, 2)
//                + "%<br>";
//        formatString += "Place gear: "
//                + Statistics.round(a.autoGearPercent, 2)
//                + "%<br>";
//        formatString += "Avg. kPa: " + Statistics.round(a.autoKpa, 2) + "<br>";

        formatString += "<h3>Tele-Op</h3>";
//        formatString += "Avg. kPa: " + Statistics.round(a.teleopKpa, 2) + "<br>";
//        formatString += "1+ takeoff: "
//                + Statistics.round(a.atLeastOneTakeoffPercent, 2)
//                + "%<br>";
//        formatString += "2+ takeoff: "
//                + Statistics.round(a.atLeastTwoTakeoffPercent, 2)
//                + "%<br>";
//        formatString += "3 takeoff: "
//                + Statistics.round(a.allTakeoffPercent, 2)
//                + "%<br>";
//        formatString += "<h3>Overall</h3>";
//        formatString += "Total gears: " + Statistics.round(a.totalGears, 2) + "<br>";
//        formatString += "Total kPa: " + Statistics.round(a.totalKpa, 2) + "<br>";
//        formatString += "Avg. score (predicted): " + Statistics.round(a.predictedScore, 2) + "<br>";

        formatString += "</html>";
        return formatString;

    }

    private void fixInaccuraciesTBA() {
        TBA tba;

        String apiKey = Window.apiKeyFetch();

        if (!apiKey.isEmpty()) {
            tba = new TBA(apiKey);
            try {
                BlueAlliance.downloadEventMatchData("2018" + event, tba, directory);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

        try {

            ArrayList<Match> matchData = FileManager.deserializeScoreBreakdown(
                    new File(directory.getAbsoluteFile() + "\\ScoreBreakdown - 2018" + event + ".json"));


            for (ScoutEntry entry : scoutEntries) {
                try {
                    String prefix = "Q" + entry.getPreMatch().getMatchNum() + "-" + entry.getPreMatch().getScoutPos() + "-" +
                            entry.getPreMatch().getScoutName() + ": ";
                    String inaccuracies = "";
                    Match match = matchData.get(entry.getPreMatch().getMatchNum() - 1);
                    MatchScoreBreakdown2018Alliance sb;
                    boolean correctTeamRed = entry.getPreMatch().getScoutPos().contains("Red") && match.getRedAlliance()
                            .getTeamKeys()[Integer.parseInt(entry.getPreMatch().getScoutPos().split(" ")[1]) - 1].equals("frc" + entry.getPreMatch().getTeamNum());
                    boolean correctTeamBlue = entry.getPreMatch().getScoutPos().contains("Blue") && match.getBlueAlliance()
                            .getTeamKeys()[Integer.parseInt(entry.getPreMatch().getScoutPos().split(" ")[1]) - 1].equals("frc" + entry.getPreMatch().getTeamNum());
                    if (correctTeamBlue || correctTeamRed) {

                        if (entry.getPreMatch().getScoutPos().contains("Red"))
                            sb = match.getScoreBreakdown().getRed();
                        else sb = match.getScoreBreakdown().getBlue();

                        if (!entry.getTeleOp().getFieldLayout().equals(sb.getTba_gameData())) {
                            inaccuracies += "plate lighting, ";
                            entry.getTeleOp().setFieldLayout(sb.getTba_gameData());
                        }

                        boolean actualAutoRun = false;
                        boolean actualClimb = false;
                        boolean actualLevitate = false;
                        boolean actualPark = false;
                        boolean partnersClimb = false;


                        if (entry.getPreMatch().getScoutPos().contains("1")) {
                            actualAutoRun = sb.getAutoRobot1().equals("AutoRun");
                            actualClimb = sb.getEndgameRobot1().equals("Climbing");
                            actualLevitate = sb.getEndgameRobot1().equals("Levitate");
                            actualPark = sb.getEndgameRobot1().equals("Parking");
                            partnersClimb = sb.getEndgameRobot2().equals("Climbing") && sb.getEndgameRobot3().equals("Climbing");
                        } else if (entry.getPreMatch().getScoutPos().contains("2")) {
                            actualAutoRun = sb.getAutoRobot2().equals("AutoRun");
                            actualClimb = sb.getEndgameRobot2().equals("Climbing");
                            actualLevitate = sb.getEndgameRobot2().equals("Levitate");
                            actualPark = sb.getEndgameRobot2().equals("Parking");
                            partnersClimb = sb.getEndgameRobot1().equals("Climbing") && sb.getEndgameRobot3().equals("Climbing");
                        } else if (entry.getPreMatch().getScoutPos().contains("3")) {
                            actualAutoRun = sb.getAutoRobot3().equals("AutoRun");
                            actualClimb = sb.getEndgameRobot3().equals("Climbing");
                            actualLevitate = sb.getEndgameRobot3().equals("Levitate");
                            actualPark = sb.getEndgameRobot3().equals("Parking");
                            partnersClimb = sb.getEndgameRobot2().equals("Climbing") && sb.getEndgameRobot1().equals("Climbing");
                        }

                        if (actualAutoRun != entry.getAuto().isAutoLineCross()) {
                            inaccuracies += "auto run, ";
                            entry.getAuto().setAutoLineCross(actualAutoRun);
                        }

                        if (actualClimb != entry.getTeleOp().isSuccessfulRungClimb() && actualClimb != entry.getTeleOp().isOtherRobotClimb()) {
                            inaccuracies += "is actually climbing (manually check), ";
                        }
                        //not completely accurate due to random nature of levitate
                        if (actualPark != entry.getTeleOp().isParked() && !actualLevitate) {
                            inaccuracies += "parking, ";
                            entry.getTeleOp().setParked(actualPark);
                        }
                        if (actualLevitate && partnersClimb && !entry.getPostMatch().robotQuickCommentSelections.get("Climb/park unneeded (levitate used and others climbed)")) {
                            entry.getPostMatch().robotQuickCommentSelections.put("Climb/park unneeded (levitate used and others climbed)", true);
                            inaccuracies += "climb/park unneeded, ";
                        }
                        if (!actualClimb && (entry.getTeleOp().isSuccessfulRungClimb() || entry.getTeleOp().isOtherRobotClimb())) {
                            entry.getTeleOp().setOtherRobotClimb(false);
                            entry.getTeleOp().setOtherRobotClimbType("");
                            entry.getTeleOp().setSuccessfulRungClimb(false);
                            inaccuracies += "not actually climbing, ";
                        }

                        if (!inaccuracies.isEmpty())
                            inaccuracyList += prefix + inaccuracies + "\n";
                    }
                } catch (ArrayIndexOutOfBoundsException e) {

                }

            }
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public void processTeamReports() {

        for (Integer key : teamReports.keySet()) {

            TeamReport report = teamReports.get(key);
            if (teamNameList != null) {
                report.autoGetTeamName(teamNameList);

            }
            report.calculateStats();


            teamReports.put(key, report);

        }


    }


    public void setTeamNameList(File list) {
        teamNameList = list;
    }

    public void generateTeamReportSpreadsheet(File outputDirectory) {
        final String COMMA = ",";

//        StringBuilder fileContents = new StringBuilder("teamNum,teamName,avgAutoScore,sdAutoScore,avgTeleOpScore,sdTeleOpScore,avgMatchScore,sdMatchScore,reachBaselinePercentage,avgAutoKpa,sdAutoKpa,avgTeleOpKpa,sdTeleOpKpa,avgAutoGears,sdAutoGears,autoGearAttemptSuccessPercent,autoGearPegLoc,leftPegPercent,rightPegPercent,centerPegPercent,totalLeftPegSuccess,totalRightPegSuccess,totalCenterPegSuccess,avgTeleOpGears,sdTeleOpGears,avgDroppedGears,avgHighGoals,sdHighGoals,avgLowGoals,sdLowGoals,avgHoppers,avgPointsPerCycle,sdPointsPerCycle,avgCycles,sdCycles,takeoffPercentage,takeoffAttemptPercentage,takeoffAttemptSuccessPercentage,pilotPlayPercentage,avgTeleOpKpaFuelFocus,avgTeleOpGearsGearFocus,fuelFocusPercent,gearFocusPercent,hasPickup,hasIntake,isActive,doNotPick,frequentRobotCommentStr,frequentPilotCommentStr,allComments,\n");
//        for (int key : teamReports.keySet()) {
//            TeamReport report = teamReports.get(key);
//            fileContents.append(report.teamNum).append(COMMA).append(report.teamName).append(COMMA).append(report.avgAutoScore).append(COMMA).append(report.sdAutoScore).append(COMMA).append(report.avgTeleOpScore).append(COMMA).append(report.sdTeleOpScore).append(COMMA).append(report.avgMatchScore).append(COMMA).append(report.sdMatchScore).append(COMMA).append(report.autoRunPercentage).append(COMMA).append(report.avgAutoKpa).append(COMMA).append(report.sdAutoKpa).append(COMMA).append(report.avgTeleOpKpa).append(COMMA).append(report.sdTeleOpKpa).append(COMMA).append(report.avgAutoGears).append(COMMA).append(report.sdAutoGears).append(COMMA).append(report.autoGearAttemptSuccessPercent).append(COMMA).append(report.autoGearPegLoc).append(COMMA).append(report.leftPegPercent).append(COMMA).append(report.rightPegPercent).append(COMMA).append(report.centerPegPercent).append(COMMA).append(report.totalLeftPegSuccess).append(COMMA).append(report.totalRightPegSuccess).append(COMMA).append(report.totalCenterPegSuccess).append(COMMA).append(report.avgTeleOpGears).append(COMMA).append(report.sdTeleOpGears).append(COMMA).append(report.avgDroppedGears).append(COMMA).append(report.avgHighGoals).append(COMMA).append(report.sdHighGoals).append(COMMA).append(report.avgLowGoals).append(COMMA).append(report.sdLowGoals).append(COMMA).append(report.avgHoppers).append(COMMA).append(report.avgPointsPerCycle).append(COMMA).append(report.sdPointsPerCycle).append(COMMA).append(report.avgCycles).append(COMMA).append(report.sdCycles).append(COMMA).append(report.takeoffPercentage).append(COMMA).append(report.takeoffAttemptPercentage).append(COMMA).append(report.takeoffAttemptSuccessPercentage).append(COMMA).append(report.pilotPlayPercentage).append(COMMA).append(report.avgTeleOpKpaFuelFocus).append(COMMA).append(report.avgTeleOpGearsGearFocus).append(COMMA).append(report.fuelFocusPercent).append(COMMA).append(report.gearFocusPercent).append(COMMA).append(report.hasPickup).append(COMMA).append(report.hasIntake).append(COMMA).append(report.isActive).append(COMMA).append(report.doNotPick).append(COMMA).append(report.frequentRobotCommentStr).append(COMMA).append(report.frequentPilotCommentStr).append(COMMA).append(report.allComments).append(COMMA).append('\n');
//
//        }
//
//
//        try {
//            FileManager.outputFile(outputDirectory.getAbsolutePath() + "\\TeamReports - " + event, "csv", fileContents.toString());
//        } catch (FileNotFoundException e) {
//            //
//            e.printStackTrace();
//        }
    }

    /**
     * Generates summary and team Excel spreadsheets
     *
     * @param outputDirectory Output directory for generated fields
     */
    public void generateRawSpreadsheet(File outputDirectory) {
        final String COMMA = ",";
        StringBuilder header = new StringBuilder("Scout Name,Match Num,Scouting Pos,Team Num,Starting Pos,Field Layout,"
                + "Near Switch Auto,Far Switch Auto,Near Scale Auto,Far Scale Auto,Center Switch Auto,Center Scale Auto,"
                + "Auto Switch Cubes,Auto Scale Cubes,Auto Exchange Cubes,Auto PCP Pickup,"
                + "Auto Switch Adj Pickup,"
                + "Auto Cubes Dropped,Auto Line Cross,Auto Null Territory Foul,"
                + "Auto Drop Opponent Switch,Auto Drop Opponent Scale,"
                + "Tele First Cube Time,Tele Own Switch Cubes,Tele Scale Cubes,"
                + "Tele Opponent Switch Cubes,Tele Exchange Cubes,Tele Cubes Dropped,"
                + "Climbs Assisted,Parked,Attempt Rung Climb,Success Rung Climb,"
                + "Climb on Other Robot,Other Robot Climb Type,"
                + "Focus,Robot Comment,Robot Quick Comment Str,Pick Points,");

        ArrayList<String> keys = new ArrayList<>();


        for (String key : scoutEntries.get(0).getPostMatch().getRobotQuickCommentSelections().keySet()) {
            header.append(removeCommas(key)).append(",");
            keys.add(key);
        }


        StringBuilder fileContents = new StringBuilder(header + "\n");
        for (ScoutEntry entry : scoutEntries) {
            PreMatch pre = entry.getPreMatch();
            Autonomous auto = entry.getAuto();
            TeleOp tele = entry.getTeleOp();
            PostMatch post = entry.getPostMatch();

            fileContents.append(pre.getScoutName()).append(COMMA).append(pre.getMatchNum()).append(COMMA).append(pre.getScoutPos()).append(COMMA).append(pre.getTeamNum()).append(COMMA).append(pre.getStartingPos()).append(COMMA).append(tele.getFieldLayout()).append(COMMA).append(entry.isNearSwitchAuto()).append(COMMA).append(entry.isFarSwitchAuto()).append(COMMA).append(entry.isNearScaleAuto()).append(COMMA).append(entry.isFarScaleAuto()).append(COMMA).append(entry.isCenterSwitchAuto()).append(COMMA).append(entry.isCenterScaleAuto()).append(COMMA);
            fileContents.append(auto.getSwitchCubes()).append(COMMA).append(auto.getScaleCubes()).append(COMMA).append(auto.getExchangeCubes()).append(COMMA).append(auto.getPowerCubePilePickup()).append(COMMA).append(auto.getSwitchAdjacentPickup()).append(COMMA).append(auto.getCubesDropped()).append(COMMA).append(auto.isAutoLineCross()).append(COMMA).append(auto.isNullTerritoryFoul()).append(COMMA).append(auto.isCubeDropOpponentSwitchPlate()).append(COMMA).append(auto.isCubeDropOpponentScalePlate()).append(COMMA);

            fileContents.append(tele.getFirstCubeTime()).append(COMMA).append(tele.getOwnSwitchCubes()).append(COMMA).append(tele.getScaleCubes()).append(COMMA).append(tele.getOpponentSwitchCubes()).append(COMMA).append(tele.getExchangeCubes()).append(COMMA).append(tele.getCubesDropped()).append(COMMA).append(tele.getClimbsAssisted()).append(COMMA).append(tele.isParked()).append(COMMA).append(tele.isAttemptRungClimb()).append(COMMA).append(tele.isSuccessfulRungClimb()).append(COMMA).append(tele.isOtherRobotClimb()).append(COMMA).append(tele.getOtherRobotClimbType()).append(COMMA);
            fileContents.append(post.getFocus()).append(COMMA).append(post.getRobotComment()).append(COMMA).append(post.getRobotQuickCommentStr()).append(COMMA).append(post.getPickNumber()).append(COMMA);

            for (String key : keys)
                fileContents.append(post.getRobotQuickCommentSelections().get(key)).append(COMMA);


            fileContents.append('\n');
        }


        try {
            FileManager.outputFile(outputDirectory.getAbsolutePath() + "\\Data - All - " + event, "csv", fileContents.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Serializes the ArrayList of all ScoutEntrys into a JSON file
     *
     * @param outputDirectory
     * @return true if operation is successful, false otherwise
     */
    public boolean generateCombineJson(File outputDirectory) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(scoutEntries);
        try {
            FileManager.outputFile(outputDirectory.getAbsolutePath() + "\\Data - All - " + event, "json", jsonString);
        } catch (FileNotFoundException e) {

            return false;
        }
        return true;
    }


    /**
     * Serializes the HashMap of all TeamReports
     *
     * @param outputDirectory
     */
    public void generateTeamReportJson(File outputDirectory) {

        Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().create();

        ArrayList<TeamReport> teamReportList = new ArrayList<>();

        for (int key : teamReports.keySet())
            teamReportList.add(teamReports.get(key));

        String jsonString = gson.toJson(teamReportList);
        try {
            FileManager.outputFile(outputDirectory.getAbsolutePath() + "\\TeamReports - " + event, "json", jsonString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void generateInaccuracyList(File outputDirectory) {
        if (!inaccuracyList.isEmpty())
            FileManager.outputFile(new File(outputDirectory.getAbsolutePath() + "\\inaccuracies.txt"), inaccuracyList);
    }

    public void generatePicklists(File outputDirectory) {
        PicklistGenerator pg = new PicklistGenerator(scoutEntries, outputDirectory);
        pg.generateBogoCompareList();
        pg.generateComparePointList();
        pg.generatePickPointList();
    }

    public TeamReport getTeamReport(int teamNum) {
        return teamReports.get(teamNum);
    }

}
