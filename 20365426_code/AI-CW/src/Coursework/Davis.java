package Coursework;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;


public class Davis {

	//first get candidate solution and store in array
		int objectiveValue;
		int tournamentScore;
		Random random = new Random();
		int mutation;
		int bestOv;
		int deltaEv;
		Solution best;
		static int bestarr[];
		
		
		public Davis(Solution solution, Problem problem, int iom, int dos) {
			
			Random rand = new Random();
			solution.value = Arrays.copyOf(solution.value, solution.prob.totalSets);
			bestarr = new int[solution.prob.totalSets];
			best = new Solution (problem);
	        Solution aiPerm = new Solution(problem);
	    
	        int[] arr = new int[solution.prob.totalSets];
	        for (int i = 0; i < solution.prob.totalSets; i++) {
	            arr[i] = i;
	        }
	        
	        aiPerm.value = Arrays.copyOf(arr, arr.length);
			
	        for (int i = aiPerm.prob.totalSets-1; i > 0; i--) {
	            int j = rand.nextInt(i+1);
	            
	            int temp = aiPerm.value[i];
	            aiPerm.value[i] = aiPerm.value[j];
	            aiPerm.value[j] = temp;
	        }
	        

			boolean isFeasable=aiPerm.feasable();
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

	        for(int j = 0; j<dos;j++) {
				for(int i = 0; i<solution.prob.totalSets; i++) {
					aiPerm.value[i]=solution.value[i];
					for(int k = 0; k<1; k++) {
						deltaEv = aiPerm.objectiveValue();
					}
					if(aiPerm.value[i]==1) { //only flip bit if it is 1, 0 -> 1 would see no improvement
						aiPerm.value[i]=0; 
						isFeasable = aiPerm.feasable(); //check if new solution is feasable
						if(isFeasable==false) {
							aiPerm.value[i]=1; //if not feasable, flip bit back to one
							tournamentScore-=1;
						}
						else {
							deltaEv-=1;
							tournamentScore+=1;							
						}
					}
					//objectiveValue = aiPerm.objectiveValue();
					
				}
				if(bestOv==deltaEv) {
					break;
				}
				bestOv = deltaEv;
	        }
	        
	        System.out.println("Davis Solution:");
    		for(int i = 0; i<solution.prob.totalSets;i++) {
    			System.out.print(aiPerm.value[i]);
    		}
    		System.out.println();
    		best.value = Arrays.copyOf(aiPerm.value, aiPerm.prob.totalSets);
	        
	        
		}
		public int getTournamentScore() {
			return tournamentScore;
		}
		
		public int [] bestSolution() {
			for(int i = 0; i<best.prob.totalSets;i++) {
				this.bestarr[i]=best.value[i];
			}
			return bestarr;
	}
}
