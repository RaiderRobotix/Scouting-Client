package org.usfirst.frc.team25.scouting.client.data;

/** Class of static methods for general data analysis
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
	public static int sum(int[] dataset){
		int sum = 0;

		for(int i : dataset)
			sum+=i;
		return sum;
		
	}

	public static double sum(double[] dataset){
		int sum = 0;

		for(double i : dataset)
			sum+=i;
		return sum;
		
	}

	
	/** Calculates arithmetic mean of a dataset
	 * 
	 * @param dataset Array of numbers
	 * @return Average of entries in array
	 */
	public static double average(int[] dataset){	
		return ((double)sum(dataset))/dataset.length;
	}
	
	public static double average(double[] dataset){		
		return sum(dataset)/dataset.length;
	}
	
	/** Calculates the success rate of an event in all matches
	 * 
	 * @param success Array with number of successes 
	 * @param total Array with number of attempts
	 * @return Success percentage of event
	 */
	public static double successPercentage(int[] success, int[] total){
		return (((double)sum(success))/sum(total))*100;
	}
	
	/** Calculates the corrected standard deviation of an event
	 * 
	 * @param dataset Array with data points 
	 * @return Corrected standard deviation (length-1)
	 */
	public static double standardDeviation(int[] dataset){
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
	}
	
	/** Calculates the uncorrected standard deviation of an event
	 * 
	 * @param dataset Array with data points
	 * @return Uncorrected standard deviation
	 */
	public static double popStandardDeviation(int[] dataset){
		double average = average(dataset);
		double sumSquareDev = 0;
		for(int i : dataset)
			sumSquareDev+= Math.pow(i-average, 2);
		return Math.sqrt(sumSquareDev/dataset.length);
	}
	
	public static double popStandardDeviation(double[] dataset){
		double average = average(dataset);
		double sumSquareDev = 0;
		for(double i : dataset)
			sumSquareDev+= Math.pow(i-average, 2);
		return Math.sqrt(sumSquareDev/dataset.length);
	}
}
