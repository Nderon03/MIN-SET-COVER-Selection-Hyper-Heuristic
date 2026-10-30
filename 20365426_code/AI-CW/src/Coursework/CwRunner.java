package Coursework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class CwRunner {
	//store tournament scores
	static int NDTS, DTS, STS, RTS; //store tournament score for each iteration
	static int totalNDTS, totalDTS, totalSTS, totalRTS; //store total tournament score
	static int bestTS; //store best score
	
	static int [] bestSolution; //store best solution of each trial
	
	static int maxNumberOf=100; //maximum number of steps at each trial //USER SPECIFIES
	
	static NextDescent nextDescent;
	static Davis davis;
	static Steepest steepest;
	static RandomMutation randomMutation;
	
	static int least; //least objective value at each iteration
	static int minimum = 3000; //lowest objective value of trial
	
	static int current=3000; //current objective value said to large amount
	
	static int iteration = 1; //iteration number specified
	
	static boolean isDavis=false, isND=false, isRM=false; //which stores best solution
	
	//store time of each heuristic and total time
	static long startTime, endTime;
	static long timeOfND, timeOfD, timeOfSD, timeOfRM;
	static long totalTimeND, totalTimeD, totalTimeSD, totalTimeRM;
	
	static Solution solution;
	
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		//Problem instance
		String fileName = "src\\outputs\\d1_50_500_output.txt"; //USER SPECIFIES
		//Where to store output
		Problem problem = new Problem("src\\test_instances\\test_instances\\d1_50_500.txt"); //USER SPECIFIES
		long t= System.currentTimeMillis();	
		long end = t+20000; //how long it will run for //USER SPECIFIES
		
		while(System.currentTimeMillis() < end && iteration<=maxNumberOf) { //run for end seconds and max steps

            solution = new Solution(problem);
            Solution solution1 = new Solution(problem); //ND
            Solution solution2 = new Solution(problem); //Davis
            Solution solution3 = new Solution(problem); //Steepest
            Solution solution4 = new Solution(problem); //Random
            
    		int [] initialSolution = solution.randInitial();//initialise random solution
    		boolean feas = solution.feasable(); //check if it is feasable
    		while(feas==false) { //if not feasable keep intialising a new solution until it is feasable
    			initialSolution = solution.randInitial();
    			feas = solution.feasable();
    		}
    		
    		System.out.println();
    		System.out.println("Iteration #"+iteration); //show trial number
    		
    		
    		//print candidate solution + its objective value
    		System.out.println("Candidate Solution: " + solution.objectiveValue()); //print out the objective value of the candidate solution
    		for(int i = 0; i<solution.prob.totalSets;i++) {
    			System.out.print(solution.value[i]);
    		}
    		System.out.println();
    		
    		//ensure all hill climbing use same candidate solution
    		solution1.copy(solution);
    		solution2.copy(solution);
    		solution3.copy(solution);
    		solution4.copy(solution);
    		
    		System.out.println();
    		
    		
    		//run heuristics    		
    		startTime = System.currentTimeMillis(); //get start time of when heuristic is called	    
    		nextDescent = new NextDescent(solution1,problem,IoM(),DoS());
    		endTime = System.currentTimeMillis(); //get end time of when heuristic is finished
    		//System.out.println("Next Descent Objective Value: " + nextDescent.objectiveValue); //print out the objective value of next descent
    		System.out.println("Next Descent Objective Value: " + nextDescent.deltaEv);
    		NDTS = nextDescent.getTournamentScore(); //get tournament score of this trial
    		//System.out.println("Next Descent Tournament Score: "+ NDTS);
    		totalNDTS += NDTS; //add to total tournament score for specific heuristic
    		timeOfND = (endTime - startTime); //store total time of this trial
    		//System.out.println("Next Descent run for: "+timeOfND +"ms"); //show how long heuristic is run for
    		totalTimeND+=timeOfND; //add to total time for this specific heuristic
    		System.out.println();
    		
    		startTime = System.currentTimeMillis();
    		davis = new Davis(solution2,problem,IoM(),DoS());
    		endTime = System.currentTimeMillis();
    		//System.out.println("Davis Objective Value: " + davis.objectiveValue);
    		System.out.println("Davis Objective Value: " + davis.deltaEv);
    		DTS = davis.getTournamentScore();
    		//System.out.println("Davis Tournament Score: "+ DTS);
    		totalDTS += DTS;
    		timeOfD = (endTime - startTime);
    		//System.out.println("Davis run for: "+timeOfD +"ms");
    		totalTimeD+=timeOfD;
    		System.out.println();
    		
    		startTime = System.currentTimeMillis();
    		steepest = new Steepest(solution3,problem);
    		endTime = System.currentTimeMillis();
    		//System.out.println("Steepest Objective Value: " + steepest.objectiveValue);
    		System.out.println("Steepest Objective Value: " + steepest.deltaEv);
    		STS = steepest.getTournamentScore();
    		//System.out.println("Steepest Tournament Score: "+ STS);
    		timeOfSD = (endTime - startTime);
    		totalSTS += STS;
    		//System.out.println("Steepest run for: "+timeOfSD +"ms");
    		totalTimeSD+=timeOfSD;
    		System.out.println();
    		
    		startTime = System.currentTimeMillis();
    		randomMutation = new RandomMutation(solution4,problem,IoM(),DoS());
    		//System.out.println("Random Mutation Objective Value: " + randomMutation.objectiveValue);
    		System.out.println("Random Mutation Objective Value: " + randomMutation.deltaEv);
    		endTime = System.currentTimeMillis();
    		RTS = randomMutation.getTournamentScore();
    		//System.out.println("Random Mutation Tournament Score: "+ RTS);
    		totalRTS += RTS;
    		timeOfRM = (endTime - startTime);
    		//System.out.println("Random Mutation run for: "+timeOfRM +"ms");
    		totalTimeRM+=timeOfRM;
    		System.out.println();
    		
    		
    		if (davis.deltaEv < minimum) {
				minimum = davis.deltaEv;
				isDavis = true;
				isRM = false;
				isND = false;
				bestSolution = davis.bestSolution();
				
	        }
			if (randomMutation.deltaEv < minimum) {
	        	minimum = randomMutation.deltaEv;
				isDavis = false;
				isRM = true;
				isND = false;
				bestSolution = randomMutation.bestSolution();
	        }
			if(minimum==nextDescent.deltaEv) {
    			isDavis = false;
				isRM = false;
				isND = true;
				bestSolution = nextDescent.bestSolution();
	        }
			least = getMin(nextDescent.deltaEv,davis.deltaEv,randomMutation.deltaEv);
			if(least<current) {
				current = least;
			}
    		GreedyA greedyA = new GreedyA(problem);
    		System.out.println("Solution using Greedy A: " + greedyA.objectiveValue); //print out the objective value of Greedy A
    		GreedyB greedyB = new GreedyB(problem);
    		System.out.println("Solution using Greedy B: " + greedyB.objectiveValue); //print out the objective value of Greedy B
    		
    		ranking(); //show which heuristic performs best via reinforcement learning
    		try {
    			FileWriter fileWriter = new FileWriter(fileName,true);
    			fileWriter.write("Iteration #" +iteration+ "\n");
    			fileWriter.write("best "+current + " current "+ least + "\n");
    			fileWriter.close();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}		
    		iteration++; //increment trial for next run
    		
    }
		System.out.println();
		System.out.println();
		System.out.println("CONCLUSION");
		//overall quickest and slowest heuristic
		mostEfficient();
		
		//overall best and worst reinforcement learning score
		reinforcementScore();
		
		long finish = System.currentTimeMillis();
		System.out.println();
		System.out.print("This took ");
		System.out.print((finish-start)/1000);
		System.out.print(" Seconds");
		
		try {
			FileWriter fileWriter = new FileWriter(fileName,true);
			fileWriter.write("\n"+minimum +"\n");
			if (isDavis == true) {
				for(int i = 0; i<solution.prob.totalSets;i++) {		
					fileWriter.write(Integer.toString(bestSolution[i]));
				}
				fileWriter.write("\n");
	        }
			else if (isRM==true) {
				for(int i = 0; i<solution.prob.totalSets;i++) {		
					fileWriter.write(Integer.toString(bestSolution[i]));
				}
				fileWriter.write("\n");
	        }
			else if (isND==true) {
				for(int i = 0; i<solution.prob.totalSets;i++) {		
					fileWriter.write(Integer.toString(bestSolution[i]));
				}
				fileWriter.write("\n");
	        }
	       
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	
	
	public static int IoM() { //generate intensity of mutation
		int x=0;
		double randomNum = Math.round(Math.random() * 10) / 10.0; //get number from 0.0 to 1.0, 1d.p
		if(0.0<=randomNum && randomNum <0.2) {
			x=1;
		}
		else if(0.2<=randomNum && randomNum <0.4) {
			x=2;
		}
		else if(0.4<=randomNum && randomNum <0.6) {
			x=3;
		}
		else if(0.6<=randomNum && randomNum <0.8) {
			x=4;
		}
		else if(0.8<=randomNum && randomNum <1.0) {
			x=5;
		}
		else if(randomNum == 1.0) {
			x=6;
		}
		return x; //return integer based on 1d.p number
	}
	
	public static int DoS() { //generate depth of search
		int x=0;
		double randomNum = Math.round(Math.random() * 10) / 10.0;
		if(0.0<=randomNum && randomNum <0.2) {
			x=1;
		}
		else if(0.2<=randomNum && randomNum <0.4) {
			x=2;
		}
		else if(0.4<=randomNum && randomNum <0.6) {
			x=3;
		}
		else if(0.6<=randomNum && randomNum <0.8) {
			x=4;
		}
		else if(0.8<=randomNum && randomNum <1.0) {
			x=5;
		}
		else if(randomNum == 1.0) {
			x=6;
		}
		return x;
	}
	
	
	public static void ranking() { //rank heuristics based off tournament score
		int max,max1,max2;
		max1 = Math.max(NDTS, DTS);
		max2 = Math.max(RTS, STS);
		max=Math.max(max1, max2);
		
		int min, min1, min2;
		min1 = Math.min(NDTS, DTS);
		min2 = Math.min(RTS, STS);
		min=Math.min(min1, min2);
				
		System.out.println();
		System.out.println("Best reinforcement learning score for this trial:");
		if(max==DTS) {
			System.out.println("Davis");
		}
		if(max==RTS) {
			System.out.println("Random Mutation");
		}
		if(max==NDTS) {
			System.out.println("Next Descent");
		}
		if(max==STS) {
			System.out.println("Steepest");
		}

		System.out.println("Worst reinforcement learning score for this trial:");
		if(min==DTS) {
			System.out.println("Davis");
		}
		if(min==RTS) {
			System.out.println("Random Mutation");
		}
		if(min==NDTS) {
			System.out.println("Next Descent");
		}
		if(min==STS) {
			System.out.println("Steepest");
		}

	}
	
	public static void reinforcementScore() { //rank heuristics based off OVERALL tournament score
		int max,max1,max2;
		max1 = Math.max(totalNDTS, totalDTS);
		max2 = Math.max(totalRTS,totalSTS);
		max=Math.max(max1, max2);
		
		int min, min1, min2;
		min1 = Math.min(totalNDTS, totalDTS);
		min2 = Math.min(totalRTS,totalSTS);
		min=Math.min(min1, min2);
		
		System.out.println();
		System.out.println("Overall Worst Reinforcement Learning score:");
		if(min==totalDTS) {
			System.out.println("Davis");
		}
		if(min==totalRTS) {
			System.out.println("Random Mutation");
		}
		if(min==totalNDTS) {
			System.out.println("Next Descent");
		}
		if(min==totalSTS) {
			System.out.println("Steepest");
		}
		
		
		System.out.println("Overall Best Reinforcement Learning score:");
		if(max==totalDTS) {
			System.out.println("Davis");
			System.out.println("Selected Heuristic: Davis");
		}
		if(max==totalRTS) {
			System.out.println("Random Mutation");
			System.out.println("Selected Heuristic: Random Mutation");
		}
		if(max==totalNDTS) {
			System.out.println("Next Descent");
			System.out.println("Selected Heuristic: Next Descent");
		}
		if(max==totalSTS) {
			System.out.println("Steepest");
			System.out.println("Selected Heuristic: Steepest");
		}


	}
	
	public static void mostEfficient() { //rank heuristics based off time taken
		int max,max1,max2;
		max1 = Math.max((int)totalTimeND, (int)totalTimeD);
		max2 = Math.max((int)totalTimeRM, (int)totalTimeSD);
		max=Math.max(max1, max2);
		
		int min, min1, min2;
		min1 = Math.min((int)totalTimeND, (int)totalTimeD);
		min2 = Math.min((int)totalTimeRM, (int)totalTimeSD);
		min=Math.min(min1, min2);
				
		System.out.println("Overall Slowest Heuristic:");
		if(max==totalTimeD) {
			System.out.println("Davis");
		}
		if(max==totalTimeRM) {
			System.out.println("Random Mutation");
		}
		if(max==totalTimeND) {
			System.out.println("Next Descent");
		}
		if(max==totalTimeSD) {
			System.out.println("Steepest");
		}

		System.out.println("Overall Fastest Heuristic:");
		if(min==totalTimeD) {
			System.out.println("Davis");
		}
		if(min==totalTimeRM) {
			System.out.println("Random Mutation");
		}
		if(min==totalTimeND) {
			System.out.println("Next Descent");
		}
		if(min==totalTimeSD) {
			System.out.println("Steepest");
		}

	}
	public static int getMin(int a, int b, int c) {
		int min = a;
		if(b<min) {
			min = b;
		}
		if(c<min) {
			min = c;
		}
		return min;
	}
	
}
