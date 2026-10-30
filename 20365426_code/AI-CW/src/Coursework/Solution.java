package Coursework;

import java.util.Arrays;
import java.util.Random;

public class Solution {

	public Solution(Problem problem) {
		this.prob = problem;
		this.value = new int [prob.totalSets];
	}
	
	Random random = new Random();
    int randomNumber;
	int value[];
	Problem prob;
	
	public int [] randInitial() { //randomly initialise solution
		
		for(int i = 0; i < prob.totalSets; i++) {
			randomNumber = random.nextInt(2); //assign each index a value of 0 or 1
			value[i] = randomNumber;			
		}
		return value;
	}
		
	public boolean feasable() { //check if solution is feasable, all 500 variables used
		
		int[] numbers = new int[prob.variables]; //array to store values

		 for(int i=0; i<prob.totalSets; i++) {
		   if(value[i]==1) {
			   for(int j = 0; j<prob.variables; j++) {
				   if(prob.arr[i][j]==1) {
					   numbers[j]=1;
				   }
				   
			   }			   
		   }
		}

		 for(int k = 0; k<prob.variables;k++) {
			 if(numbers[k]==0) {
				 return false;
			 }
		 }
		
		 return true;
	}
	
	int objectiveValue;
	public int objectiveValue() {
		objectiveValue=0; //reset objective value
		for(int i = 0; i<prob.totalSets;i++){
			if(value[i]==1){
				objectiveValue+=1;	 //increment objective value when 1 found	
			}
		}
		return objectiveValue;
	}
	public void copy (Solution solution) {
		this.value = Arrays.copyOf(solution.value, solution.prob.totalSets);
		
	}
	
}
