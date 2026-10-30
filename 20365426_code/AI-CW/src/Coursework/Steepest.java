package Coursework;

import java.util.Arrays;
import java.util.Random;

public class Steepest {
	
int objectiveValue;
int bestIndex;
int tournamentScore;
int initial;
Random random = new Random();
int mutation;
int deltaEv;

	public Steepest(Solution solution, Problem problem) {
		solution.value = Arrays.copyOf(solution.value, solution.prob.totalSets);

		boolean isFeasable=solution.feasable();
		boolean improved = false;
		initial = solution.objectiveValue();
		deltaEv = initial;
		
		for(int i = 0; i<solution.prob.totalSets; i++) {
			if(solution.value[i]==1) { //only flip bit if it is 1, 0 -> 1 would see no improvement
				solution.value[i]=0; 
				isFeasable = solution.feasable(); //check if new solution is feasable
				if(isFeasable==false) {
					solution.value[i]=1; //if not feasable, flip bit back to one
				}
				else{
					bestIndex=i;
						solution.value[i]=1;
						improved = true;
					}
				}
			}
        
		solution.value[bestIndex]=0;
		//objectiveValue = solution.objectiveValue();
		if(improved==true) {
			deltaEv-=1;
		}
		if(deltaEv<initial) {
			tournamentScore = 1-solution.prob.totalSets;
		}
		else {
			tournamentScore = 0-solution.prob.totalSets;
		}
		System.out.println("Steepest Solution:");
		for(int i = 0; i<solution.prob.totalSets;i++) {
			System.out.print(solution.value[i]);
		}
		System.out.println();

	}
	public int getTournamentScore() {
		return tournamentScore;
		
	}

}
