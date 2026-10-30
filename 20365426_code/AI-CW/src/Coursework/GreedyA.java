package Coursework;

import java.util.Arrays;
import java.util.Random;

public class GreedyA {
	
	int objectiveValue;
	boolean feasable = false;
	public GreedyA(Problem prob) {
		Solution solution = new Solution(prob);
		for(int i = 0; i < prob.totalSets; i++) {
		    solution.value[i] = 0;
		}
		
		for(int i = 0; i<prob.totalSets;i++) {
			solution.value[i]=1; //flip each bit to 1 until solution is feasable
			feasable = solution.feasable();
			if (feasable==true) {
				break;
			}
		}
		objectiveValue = solution.objectiveValue();
	}
}
