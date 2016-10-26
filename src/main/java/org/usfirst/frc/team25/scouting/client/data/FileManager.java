package org.usfirst.frc.team25.scouting.client.data;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.usfirst.frc.team25.scouting.client.models.ScoutEntry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FileManager {
	
	public static void outputFile(File file, String fileContents){
		
	}
	
	public static void outputFile(String fileName, String extension, String fileContents){
		
	}
	
	public static File selectFolder(JFrame frame, String dialogTitle){
		JFileChooser chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle(dialogTitle);
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);
	    if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) { 
	        System.out.println("getCurrentDirectory(): " 
	           +  chooser.getCurrentDirectory());
	        System.out.println("getSelectedFile() : " 
	           +  chooser.getSelectedFile());
	        
	        return chooser.getSelectedFile();
	        }
	      else {
	        System.out.println("No Selection ");
	        return null;
	        
	      }
	 }
	
	public static File[] getFilesFromDirectory(File directory){
		return directory.listFiles();
	}
	
	public static String getFileString(File file){
		String contents = null;
		try {
			contents = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return contents;
	}
	
	
	
	public static ArrayList<ScoutEntry> deserializeData(ArrayList<File> fileNames){
		ArrayList<ScoutEntry> allEntries =  new ArrayList<ScoutEntry>();
		for(File file : fileNames){
			ArrayList<ScoutEntry> fileEntries = new Gson().fromJson(getFileString(file), 
					new TypeToken<ArrayList<ScoutEntry>>(){}.getType());
			for(ScoutEntry entry: fileEntries)
				allEntries.add(entry);
		
		}
		return allEntries;
		
	}
}
