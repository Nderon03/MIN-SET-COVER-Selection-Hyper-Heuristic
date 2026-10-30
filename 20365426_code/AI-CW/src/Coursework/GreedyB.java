package Coursework;

public class GreedyB {
	
	int objectiveValue;
	int temp;
	Problem problem;
	int z;
	int best;
	public GreedyB(Problem prob) {
		this.problem = prob;
		Solution solution = new Solution(prob);
		int [] numbers = new int [prob.variables];
		
		for(int i = 0; i < prob.totalSets; i++) {
		    solution.value[i] = 0;
		}

		
		while(solution.feasable()==false) {
			temp = zeros(numbers);
			for(int i = 0; i<prob.totalSets;i++) {
				if(solution.value[i]==0) {
					solution.value[i]=1;
					fillArray(numbers,solution.value);
					z = zeros(numbers);
					
					if(z<=temp) {
						temp = z;
						best = i;
					}
					solution.value[i]=0;
				}
				emptyArray(numbers);
			}
			solution.value[best]=1; //flip the bit of the set that fills array the most
			
		}
		objectiveValue = solution.objectiveValue();
	}
	
	public int zeros(int[] arr) { //return number of 0s
		int zero=0;
		for (int i = 0; i<arr.length;i++) {
			if(arr[i]==0) {
				zero++;
			}
		}	
		return zero;
	}
	
	public void fillArray(int[] arr, int[] candidate) { //fill array
		
		for(int i = 0; i<candidate.length;i++) {
			if (candidate[i]==1) {
				for(int j = 0; j<arr.length;j++) {
					if(problem.arr[i][j]==1) {
						arr[j]=1;
					}
				}
			}	
		}
	}
	
	public void emptyArray (int[] arr) { //empty array
		for(int i = 0; i < arr.length; i++) {
		    arr[i] = 0;
		}
		
	}
}
