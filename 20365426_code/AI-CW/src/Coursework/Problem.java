package Coursework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Problem {
	

	public int[] setSizesArray;
	public int totalSets;
	public int variables;
	public int eachSetSize;
	public int arr[][];
	public int temp;
	public int[] temp2;
	

	
	public Problem(String filename) {
			
		
		try {
            File inputFile = new File(filename);
            Scanner scanner = new Scanner(inputFile);
            totalSets = Integer.valueOf(scanner.next()); //total number of sets
            variables = Integer.valueOf(scanner.next()); //total number of variables
            this.setSizesArray = new int[totalSets]; //create array which stores each number of set
            this.arr = new int [totalSets][variables]; //create array which stores file
            
            for(int i = 0; i<totalSets; i++) {
            	eachSetSize = Integer.valueOf(scanner.next()); //get value of each set size
            	setSizesArray[i]=eachSetSize; //store each set size in array
            	for(int j = 0; j < eachSetSize; j++) { //loop through each set
            		arr[i][Integer.valueOf(scanner.next())-1]=1; 
            	}
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		//sort sets now in decreasing now for greedy heuristic B
		/*for(int x = 0; x < totalSets; x++) {
		    for(int y = 0; y < totalSets - x - 1; y++) {
		        if(setSizesArray[y] < setSizesArray[y+1]) {
		            // swap setSizesArray
		            int temp = setSizesArray[y];
		            setSizesArray[y] = setSizesArray[y+1];
		            setSizesArray[y+1] = temp;
		            
		            // swap arr
		            temp2 = arr[y];
		            arr[y] = arr[y+1];
		            arr[y+1] = temp2;
		        }
		    }
		}*/
	}
}