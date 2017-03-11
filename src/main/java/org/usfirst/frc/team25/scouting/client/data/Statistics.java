package org.usfirst.frc.team25.scouting.client.data;

import java.util.ArrayList;

/**
 * 
 * @author sng
 *
 */
public class Statistics {

	/** Calculates the sum of an array of numbers
	 * 
	 * @param dataset Array of numbers to be summed
	 * @return Sum of the elements in <code>dataset</code>
	 */
	public static double sum(ArrayList<Double> dataset){
		double sum = 0;

		for(double i : dataset)
			sum+=i;
		return sum;
		
	}
	
	public static ArrayList<Double> toDoubleArrayList(ArrayList<Integer> arr){
		ArrayList<Double> toReturn = new ArrayList<>();
		for(int i : arr)
			toReturn.add((double) i);
		return toReturn;
	}

	
	/** Calculates arithmetic mean of a dataset
	 * 
	 * @param dataset Array of numbers
	 * @return Average of entries in array
	 */
	public static double average(ArrayList<Double> dataset){		
		return sum(dataset)/dataset.size();
	}
	
	/** Calculates the success rate of an event in all matches
	 * 
	 * @param success Array with number of successes 
	 * @param total Array with number of attempts
	 * @return Success percentage of event
	 */
	/*public static double successPercentage(int[] success, int[] total){
		return (((double)sum(success))/sum(total))*100;
	}
	
	/** Calculates the corrected standard deviation of an event
	 * 
	 * @param dataset Array with data points 
	 * @return Corrected standard deviation (length-1)
	 */
	/*public static double standardDeviation(int[] dataset){
		double average = average(dataset);
		double sumSquareDev = 0;
		for(int i : dataset)
			sumSquareDev+= Math.pow(i-average, 2);
		return Math.sqrt(sumSquareDev/(dataset.length-1));
	}
	
	public static double standardDeviation(double[] dataset){
		double average = average(dataset);
		double sumSquareDev = 0;
		for(double i : dataset)
			sumSquareDev+= Math.pow(i-average, 2);
		return Math.sqrt(sumSquareDev/(dataset.length-1));
	}*/
	
	/** Calculates the uncorrected standard deviation of an event
	 * 
	 * @param dataset Array with data points
	 * @return Uncorrected standard deviation
	 */
	
	public static double popStandardDeviation(ArrayList<Double> dataset){
		double average = average(dataset);
		double sumSquareDev = 0;
		for(double i : dataset)
			sumSquareDev+= Math.pow(i-average, 2);
		return Math.sqrt(sumSquareDev/dataset.size());
	}

}
