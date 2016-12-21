package org.usfirst.frc.team25.scouting.client.data;

/** Collection of static methods for data processing
 * 
 * @author sng
 *
 */
public class Statistics {
	
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
	
	
	
	
	
	

}
