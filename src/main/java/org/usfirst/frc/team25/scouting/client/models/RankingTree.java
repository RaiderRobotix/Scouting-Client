package org.usfirst.frc.team25.scouting.client.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public class RankingTree {
	
	HashMap<Integer, Integer> ranks;
	int maxRank = 0;
	
	public RankingTree(){
		this.ranks = new HashMap<>();
		
	}
	
	public RankingTree(HashMap<Integer, Integer> ranks){
		this.ranks = ranks;
		
	}
	
	public HashMap<Integer, Integer> getTreeHashMap(){
		HashMap<Integer, Integer> mapCopy = (HashMap<Integer, Integer>) ranks.entrySet().stream()
			    .collect(Collectors.toMap(e -> e.getKey(), e ->  e.getValue()));
		return mapCopy;
		
	}
	
	public boolean containsNode(int teamNum){
		return this.ranks.containsKey(teamNum);
	}
	
	public void addNode(int teamNum){
		if(!containsNode(teamNum))
			ranks.put(teamNum, 0);
	}
	
	public void addNodeAlongside(int newNodeNum, int oldNode) throws Exception{
		if(!containsNode(newNodeNum))
			ranks.put(newNodeNum, getLevel(oldNode));
	}
	
	public void addNodeAbove(int newNodeNum, int oldNode) throws Exception{
		if(!containsNode(newNodeNum)){
			addNodeAlongside(newNodeNum, oldNode);
			promote(newNodeNum);
		}
			
	}
	
	public void addNodeBelow(int newNodeNum, int oldNode) throws Exception{
		if(!containsNode(newNodeNum)){
			addNodeAlongside(newNodeNum, oldNode);
			demote(newNodeNum);
		}
			
	}

	
	public void printTree(){
		for(int key : ranks.keySet()){
			try {
				System.out.println(key + ","+getLevel(key));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	public boolean isComparisonCompliant(Comparison comparison) throws Exception{
		if(comparison.getBetterTeam()==0)
			return getLevel(comparison.getHigherTeam())==getLevel(comparison.getLowerTeam());
		else return getLevel(comparison.getBetterTeam())>getLevel(comparison.getWorseTeam());
	}
	
	public double getCompliancePercent(ArrayList<Comparison> comparisons) {
		double compliant = 0.0;
		int validComparisons = 0;
		
		for(Comparison comparison : comparisons){
			try{
				if(isComparisonCompliant(comparison))
					compliant++;
				validComparisons++;
			} catch(Exception e){
				
			}
		}
		
		return compliant/validComparisons * 100;
	}
	
	public int getLevel(int teamNum) throws Exception{
		if(!containsNode(teamNum))
			throw new Exception("Invalid level request for" + teamNum);
		return this.ranks.get(teamNum);
	}
	
	public void setLevel(int teamNum, int level){
		this.ranks.put(teamNum, level);
		if(level > this.maxRank)
			this.maxRank = level;
	}
	
	public int getMaxRank(){
		return this.maxRank;
	}
	
	public void promote(int teamNum) throws Exception{
		setLevel(teamNum, getLevel(teamNum)+1);
	}
	
	public void demote(int teamNum) throws Exception{
		if(getLevel(teamNum)==0){ //lowest level is 0
			for(int key : ranks.keySet())
				if(key!=teamNum)
					promote(key);
		}
		
		setLevel(teamNum, getLevel(teamNum)-1);
		
	}

}
