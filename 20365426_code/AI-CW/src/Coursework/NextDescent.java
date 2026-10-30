package Coursework;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class NextDescent { //flip every bit, if improved then keep it
	
	//first get candidate solution and store in array
	int objectiveValue;
	int tournamentScore;
	Random randomNumber = new Random();
	int mutation;
	int bestOv;
	int deltaEv;
	Solution best;
	static int [] bestarr;
	
	public NextDescent(Solution solution, Problem problem, int iom, int dos) {
		solution.value = Arrays.copyOf(solution.value, solution.prob.totalSets);
		bestarr = new int[solution.prob.totalSets];
		best = new Solution (problem);
		boolean isFeasable=solution.feasable();
		
		//mutation
		for(int j = 0; j<iom; j++) {
			mutation = randomNumber.nextInt(solution.prob.totalSets); 
			if(solution.value[mutation]==1) {
				solution.value[mutation] = 0;
				
			}
			else if(solution.value[mutation]==0) {
				solution.value[mutation] = 1;
			}
		}
		deltaEv = solution.objectiveValue();
		for(int j = 0; j<dos;j++) {
			for(int i = 0; i<solution.prob.totalSets; i++) {
				if(solution.value[i]==1) { //only flip bit if it is 1, 0 -> 1 would see no improvement
					solution.value[i]=0; 
					isFeasable = solution.feasable(); //check if new solution is feasable
					if(isFeasable==false) {
						solution.value[i]=1; //if not feasable, flip bit back to one
						tournamentScore-=1;
					}
					else {
						deltaEv-=1;
						tournamentScore+=1;
					}
				}
				//objectiveValue = solution.objectiveValue(); //get objective value of next descent
			}
			
			if(bestOv==deltaEv) {
				break; //if no improvement over pass, then end
			}
			bestOv = deltaEv;
		}

		System.out.println("Next Descent Solution:");
		for(int i = 0; i<solution.prob.totalSets;i++) {
			System.out.print(solution.value[i]);
		}
		best.value = Arrays.copyOf(solution.value, solution.prob.totalSets);
		System.out.println();
		
	}
	
	public int getTournamentScore() {
		return tournamentScore;
		
	}

	public int [] bestSolution() {
			for(int i = 0; i<best.prob.totalSets;i++) {
				bestarr[i]=best.value[i];
			}
			return bestarr;
	}
}
