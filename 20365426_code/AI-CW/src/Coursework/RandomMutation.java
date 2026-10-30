package Coursework;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class RandomMutation {

int objectiveValue;
int randomNumber;
int tournamentScore;
Random random = new Random();
int mutation;
int bestOv;
int deltaEv;
Solution best;
static int bestarr[];


	public RandomMutation(Solution solution, Problem problem, int iom, int dos) {
		Random rand = new Random();
		solution.value = Arrays.copyOf(solution.value, solution.prob.totalSets);
		bestarr = new int[solution.prob.totalSets];
		best = new Solution (problem);
        Solution aiRand = new Solution(problem);
        aiRand.value = Arrays.copyOf(solution.value, solution.prob.totalSets);
        
      //mutation
        for(int j = 0; j<iom; j++) {
        	mutation = random.nextInt(solution.prob.totalSets); 
        	if(solution.value[mutation]==1) {
        		solution.value[mutation] = 0;
      				
        	}
        	else if(solution.value[mutation]==0) {
      			solution.value[mutation] = 1;
        	}
        }
      
        for (int i = 0; i<solution.prob.totalSets;i++) {
        	randomNumber = random.nextInt(solution.prob.totalSets); //assign each index a value of 0 or 1
        	aiRand.value[i] = randomNumber;
            
        }
        
		boolean isFeasable=solution.feasable();
		for(int j = 0; j<dos;j++) {
			for(int i = 0; i<solution.prob.totalSets; i++) {
				aiRand.value[i]=solution.value[i];
				for(int k = 0; k<1; k++) {
					deltaEv = aiRand.objectiveValue();
				}
				if(aiRand.value[i]==1) { //only flip bit if it is 1, 0 -> 1 would see no improvement
					aiRand.value[i]=0; 
					isFeasable = aiRand.feasable(); //check if new solution is feasable
					if(isFeasable==false) {
						aiRand.value[i]=1; //if not feasable, flip bit back to one
						tournamentScore-=1;
					}
					else {
						deltaEv-=1;
						tournamentScore+=1;
					}
					
				}
				//objectiveValue = aiRand.objectiveValue();
			}
			if(bestOv==deltaEv) {
				break;
			}
			bestOv = deltaEv;
		}
		
		System.out.println("Random Mutation Solution:");
		for(int i = 0; i<solution.prob.totalSets;i++) {
			System.out.print(aiRand.value[i]);
		}
		System.out.println();
		best.value = Arrays.copyOf(aiRand.value, aiRand.prob.totalSets);
		
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
