package org.usfirst.frc.team25.scouting.client.ui;

import com.thebluealliance.api.v3.TBA;
import org.usfirst.frc.team25.scouting.client.data.BlueAlliance;
import org.usfirst.frc.team25.scouting.client.data.Constants;
import org.usfirst.frc.team25.scouting.client.data.EventReport;
import org.usfirst.frc.team25.scouting.client.data.FileManager;
import org.usfirst.frc.team25.scouting.client.models.ScoutEntry;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main class; used to initialize the GUI
 *
 * @author sng
 */

public class Window {


    private static final double VERSION = 2.1;
    //Regular expression to split filename into name and extension
    private static final String FILE_EXTENSION_REGEX = "\\.";
    private static TBA tba;
    /**
     * When processing data, dataDirectory must have the qualified <code>short_name</code> for the event.
     * Downloaded data from TBA should be in same folder as well.
     * Output spreadsheets/summary reports to be written to same folder
     */
    private static File dataDirectory;

    /**
     * Processes data
     * TODO: Make a better GUI with user feedback
     *
     * @param frame JFrame to be modified
     */
    private static void processData(JFrame frame) {
        JLabel introText = new JLabel("<html><h1>Processing data</h1><br>Processing...</html>");
        introText.setHorizontalAlignment(JLabel.CENTER);
        introText.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.setContentPane(introText);
        frame.setVisible(true);

        String eventName = dataDirectory.getName();


        ArrayList<File> jsonFileList = new ArrayList<>();
        File teamNameList = null;


        //Gets a list of data files in directory. JSON is required, csv is optional
        for (File file : FileManager.getFilesFromDirectory(dataDirectory)) {
            String fileName = file.getName();


            try {
                if (fileName.split(FILE_EXTENSION_REGEX)[1].equals("json") && fileName.contains(eventName) && fileName.contains("Data")) {
                    jsonFileList.add(file);

                }
                if (fileName.split(FILE_EXTENSION_REGEX)[1].equals("csv") && fileName.contains(eventName) && fileName.contains("TeamNames"))
                    teamNameList = file;
            } catch (ArrayIndexOutOfBoundsException e) {

            }//Caused when there is only a directory, no file
        }

        ArrayList<ScoutEntry> scoutEntries = FileManager.deserializeData(jsonFileList);


        if (scoutEntries.size() == 0) {
            JOptionPane.showMessageDialog(addIcon(new JFrame()), "No JSON data files found or root folder not named after event", "Error", JOptionPane.PLAIN_MESSAGE);
            introText.setText("<html><h1>Processing data</h1><br>Error!</html>");
            initializeGUI();
            return;
        }

        EventReport report = new EventReport(scoutEntries, eventName, dataDirectory);

        if (teamNameList != null)
            report.setTeamNameList(teamNameList);


        report.generateRawSpreadsheet(dataDirectory);
        report.processTeamReports();

        if (report.generateCombineJson(dataDirectory)) //combined JSON file successfully generated
            for (File file : jsonFileList)
                if (!file.getName().contains("All"))
                    file.delete();

        //report.generateTeamReportJson(dataDirectory);
        //report.generateTeamReportSpreadsheet(dataDirectory);
        report.generatePicklists(dataDirectory);
        report.generateInaccuracyList(dataDirectory);

        introText.setText("<html><h1>Processing data</h1><br>Done!</html>");

        frame.setVisible(false);
        initializeAnalyzer(report);

    }

    private static boolean isValidTeamNum(EventReport report, String teamNumStr) {
        try {
            int teamNum = Integer.parseInt(teamNumStr);
            return report.isTeamPlaying(teamNum);

        } catch (NumberFormatException exc) {
            return false;
        }
    }

    private static void initializeAnalyzer(EventReport eventReport) {
        JLabel introText = new JLabel("<html><h1>Analyze data</h1><br>Retrieve alliance or team information</html>");
        introText.setHorizontalAlignment(JLabel.CENTER);
        introText.setFont(new Font("Arial", Font.PLAIN, 16));

        JFrame frame = addIcon(new JFrame("Team 25 Scouting Client"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.getContentPane().add(introText, BorderLayout.CENTER);


        JButton teamButton = new JButton("Team report");
        JButton allianceButton = new JButton("Alliance report");
        JPanel panel = new JPanel(new GridLayout(1, 2, 1, 1));
        panel.add(teamButton);
        panel.add(allianceButton);

        frame.getContentPane().add(panel, BorderLayout.SOUTH);

        frame.setSize(500, 200);

        //Sets window to launch at the center of screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        frame.setVisible(true);

        teamButton.addActionListener(e -> {
            JFrame teamNumPrompt = addIcon(new JFrame());

            String userInput = JOptionPane.showInputDialog(teamNumPrompt,
                    "Enter the team number to retrieve stats", "Enter team number", JOptionPane.PLAIN_MESSAGE);
            if (!isValidTeamNum(eventReport, userInput))
                JOptionPane.showMessageDialog(teamNumPrompt, "Invalid team number for event. Please try again", "Error", JOptionPane.PLAIN_MESSAGE);
            else
                JOptionPane.showMessageDialog(teamNumPrompt, eventReport.quickTeamReport(Integer.parseInt(userInput)), "Team " + userInput, JOptionPane.PLAIN_MESSAGE);

        });

        allianceButton.addActionListener(e -> {
            JFrame teamNumPrompt = addIcon(new JFrame());
            JTextField t1 = new JTextField(5);
            JTextField t2 = new JTextField(5);
            JTextField t3 = new JTextField(5);

            JPanel panel1 = new JPanel();
            panel1.add(new JLabel("Team #1:"));
            panel1.add(t1);

            panel1.add(new JLabel("Team #2:"));

            panel1.add(t2);
            panel1.add(new JLabel("Team #3:"));
            panel1.add(t3);

            int input = JOptionPane.showOptionDialog(teamNumPrompt, panel1, "Enter alliance", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE
                    , null, null, null);
            if (input == JOptionPane.OK_OPTION) {
                if (!isValidTeamNum(eventReport, t1.getText()) || !isValidTeamNum(eventReport, t2.getText()) || !isValidTeamNum(eventReport, t3.getText()))
                    JOptionPane.showMessageDialog(teamNumPrompt, "Invalid team number(s) for event. Please try again", "Error", JOptionPane.PLAIN_MESSAGE);
                else
                    JOptionPane.showMessageDialog(teamNumPrompt, eventReport.allianceReport(Integer.parseInt(t1.getText()), Integer.parseInt(t2.getText()), Integer.parseInt(t3.getText()))
                            , t1.getText() + ", " + t2.getText() + ", " + t3.getText()
                            , JOptionPane.PLAIN_MESSAGE);
            }

        });
    }

    /**
     * Adds the Team 25 logo to the window
     *
     * @param frame JFrame to be modified
     * @return Modified JFrame
     */
    private static JFrame addIcon(JFrame frame) {
        try {
            frame.setIconImage(ImageIO.read(new File("res/logo.png")));
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        return frame;
    }


    private static void initializeGUI() {

        JLabel introText = new JLabel("<html><h1>Team 25 Scouting Client - v" + VERSION + "</h1><br>Press start to select data folder</html>");

        introText.setHorizontalAlignment(JLabel.CENTER);
        introText.setFont(new Font("Arial", Font.PLAIN, 16));

        JFrame frame = addIcon(new JFrame("Team 25 Scouting Client"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        frame.getContentPane().add(introText, BorderLayout.CENTER);


        JButton startButton = new JButton("Start");
        JButton downloadButton = new JButton("Download");
        JPanel panel = new JPanel(new GridLayout(1, 2, 1, 1));
        panel.add(startButton);
        panel.add(downloadButton); //Elements are added to the JFrame. TODO Improve readability?
        startButton.setSize(100, 70);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);

        frame.setSize(500, 200);

        //Sets window to launch at the center of screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        frame.setVisible(true);


        startButton.addActionListener(e -> {
            dataDirectory = FileManager.selectFolder(frame, "Select data folder");
            if (dataDirectory != null) {
                frame.setVisible(false);
                processData(frame);
            }


        });


        downloadButton.addActionListener(e -> {
            JFrame eventCodePrompt = addIcon(new JFrame());

            String apiKey = apiKeyFetch();

            if (apiKey.isEmpty())
                return;
            else tba = new TBA(apiKey);

            File outputDirectory = FileManager.selectFolder(eventCodePrompt, "Select output folder");
            if (outputDirectory == null)
                return;
            eventCodePrompt.setIconImage(null);

            String userInput = JOptionPane.showInputDialog(eventCodePrompt,
                    "Enter the event code, \"25\" (for current calendar year), or \"25+[YYYY]\" to download data for all events", "Enter event code", JOptionPane.PLAIN_MESSAGE);

            if (userInput.contains("25") && !userInput.contains("2025")) {
                String[] splitInput = userInput.split("\\+");
                if (splitInput.length == 1)
                    BlueAlliance.downloadRaiderEvents(outputDirectory, tba);
                else BlueAlliance.downloadRaiderEvents(outputDirectory, Integer.parseInt(splitInput[1]), tba);

            } else if (!BlueAlliance.downloadEventTeamData(outputDirectory, userInput, tba)) //Invalid event code
                JOptionPane.showMessageDialog(eventCodePrompt, "Invalid event code. Please try again", "Error", JOptionPane.PLAIN_MESSAGE);


        });
    }

    public static String apiKeyFetch() {
//        JFrame apiKeyPrompt = addIcon(new JFrame());
//
//        String apiKey = FileManager.getFileString(new File("apikey.txt"));
//        System.out.println(apiKey);
//        if (apiKey == null)
//            apiKey = JOptionPane.showInputDialog(apiKeyPrompt,
//                    "Enter The Blue Alliance API key", "Enter API key", JOptionPane.PLAIN_MESSAGE);
//        //test if API key is valid
//        tba = new TBA(apiKey);
//
//
//        try {
//            if (tba.dataRequest.getDataTBA("/status").getResponseCode() == 401) {
//                JOptionPane.showMessageDialog(apiKeyPrompt, "Invalid API key. Please try again", "Error", JOptionPane.PLAIN_MESSAGE);
//                return "";
//            }
//        } catch (Exception e1) {
//            e1.printStackTrace();
//            return "";
//        }
//        return apiKey;
        return Constants.API_KEY;
    }

    public static void main(String[] args) {
        initializeGUI();
    }
}
