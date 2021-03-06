package org.usfirst.frc.team25.scouting.client.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thebluealliance.api.v3.models.Match;
import org.usfirst.frc.team25.scouting.client.models.ScoutEntry;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Class of static methods used for file I/O
 *
 * @author sng
 */
public class FileManager {

    private static final String FILE_EXTENSION_REGEX = "\\.(?=[^\\.]+$)";

    /**
     * Writes a string to a output target file
     *
     * @param file         File object containing the output filename and directory
     * @param fileContents String contents of output file
     */
    public static void outputFile(File file, String fileContents) {
        try {
            PrintWriter outputFile = new PrintWriter(file);
            outputFile.write(fileContents);
            outputFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a string to a output target file
     *
     * @param fileName     File name of output file
     * @param extension    File extension, without the dot ('.')
     * @param fileContents String contents of output file
     */
    public static void outputFile(String fileName, String extension, String fileContents) throws FileNotFoundException {
        PrintWriter outputFile = new PrintWriter(fileName + "." + extension);
        outputFile.write(fileContents);
        outputFile.close();

    }

    /**
     * Launches a GUI file explorer to select an output folder
     *
     * @param frame       JFrame used to launch GUI
     * @param dialogTitle Title of selection dialog
     * @return File object containing file path. null if window is closed before selection
     */
    public static File selectFolder(JFrame frame, String dialogTitle) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.home") + "\\Documents\\FRC Data")); //GUI dialog opens at user path, documents, at a folder called FRC Data
        chooser.setDialogTitle(dialogTitle);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION)
            return chooser.getSelectedFile();

        else return null;


    }

    /**
     * Gets list of files from directory
     *
     * @param directory Directory that filenames are read from
     * @return Array of Files
     */
    public static File[] getFilesFromDirectory(File directory) {
        return directory.listFiles();
    }

    /**
     * Gets the contents of a file as a string
     *
     * @param file File object of the file to be parsed
     * @return Contents of the file as a string
     */
    public static String getFileString(File file) {
        String contents = "";
        try {
            contents = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        } catch (IOException e) {

            e.printStackTrace();
        }

        return contents;
    }

    private static void deleteFile(String filePath) {
        try {
            File file = new File(filePath);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes a command line statement to generate a LaTeX file
     * Deletes excess files as well
     *
     * @param outputDirectory Directory for the output PDF file
     * @param filePath        Path to TeX source file
     */
    public static void compilePdfLatex(String outputDirectory, String filePath) {
        try {
            Runtime.getRuntime().exec("pdflatex -output-directory=" + outputDirectory + " " + filePath);
            String[] directories = filePath.split("\\\\");
            String fileName = directories[directories.length - 1].split(FILE_EXTENSION_REGEX)[0];

            Thread.sleep(500); //Needed to register files into filesystem

            deleteFile(outputDirectory + "\\" + fileName + ".aux");
            deleteFile(outputDirectory + "\\" + fileName + ".log");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    /**
     * Deserializes and combines contents of JSON files exported by the Android scouting app
     *
     * @param fileNames List of JSON File objects to be parsed
     * @return ArrayList of all ScoutEntrys in the JSON files
     */
    public static ArrayList<ScoutEntry> deserializeData(ArrayList<File> fileNames) {
        ArrayList<ScoutEntry> allEntries = new ArrayList<>();

        for (File file : fileNames) {

            ArrayList<ScoutEntry> fileEntries = new Gson().fromJson(getFileString(file),
                    new TypeToken<ArrayList<ScoutEntry>>() {
                    }.getType());
            allEntries.addAll(fileEntries);

        }

        return allEntries;

    }

    public static ArrayList<Match> deserializeScoreBreakdown(File fileName) {
        Gson gson = new Gson();
        return gson.fromJson(FileManager.getFileString(fileName),
                new TypeToken<ArrayList<Match>>() {
                }.getType());
    }
}
