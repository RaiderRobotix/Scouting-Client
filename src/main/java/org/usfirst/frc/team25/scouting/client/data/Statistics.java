package org.usfirst.frc.team25.scouting.client.data;

<<<<<<< HEAD
/** Class of static methods for general data analysis
=======
/** Collection of static methods for data processing
>>>>>>> origin/master
 * 
 * @author sng
 *
 */
public class Statistics {
<<<<<<< HEAD

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
=======
	
	public static double sum(double[] nums){
		double sum = 0;
		for(double num : nums)
			sum+=num;
		return sum;
	}
	
	public static double average(double[] nums){
		return sum(nums)/nums.length;
	}
	
	/** Returns the percent success of a data point
	 * 
	 * @param success Entries of a data point being successful in a ScoutEntry
	 * @param attempt Entries of number of attempts of a data point  in a ScoutEntry
	 * @return Sum of success over sum of attempts, as a percentage
	 */
	public static double percentSuccess(double[] success, double[] attempt){
		return (sum(success)/sum(attempt))*100.0;
	}
	
	/** Calculates uncorrected standard deviation
	 * 
	 * @param nums Entries of a data point for which SD is calculated
	 * @return Population standard deviation
	 */
	public static double standardDeviation(double[] nums){
		double mean = average(nums);
		double[] meanDevs = new double[nums.length];
		
		for(int i = 0; i < nums.length; i++)
			meanDevs[i]=Math.pow(nums[i]-mean, 2);
		
		return Math.sqrt(sum(meanDevs)/nums.length); 
	}
	
	
	
	
	
	

>>>>>>> origin/master
}
