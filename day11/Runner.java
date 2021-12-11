package aoc2021.day11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import javaProject.Challenge;

public class Runner extends Challenge {

	Integer total = 0;
	List<List<Integer>> grid = new ArrayList<>();

	public Runner(String filename) {
		super(filename);
		run();
	}
	
	public static void main(String[] args) {
		new Runner("2021inputDayEleven.txt");
	}
	
	
	private void run() {
		readLines();
		runLogic();
	}
	
	private void runLogic() {
		for (int i = 0; i < 100000; i++) {
			iterateGrid();
			if (checkForAllZeros()) {  // Part 2 logic 
				System.out.println("ALL ZEROS " + (i+1));
				break;
			};
		}
		System.out.println(total);
	}
	
	private void iterateGrid() {
		for (int row = 0; row < grid.size(); row++) {
			for (int col = 0; col < grid.get(row).size(); col++) {
				blowUpBombs(row, col);
			}
		}
		resetBlownToZero();
//		System.out.println(grid); // Part 1
	}
	
	private boolean checkForAllZeros() {
		for (int row = 0; row < grid.size(); row++) {
			for (int col = 0; col < grid.get(row).size(); col++) {
				if (grid.get(row).get(col) > 0) {
					return false;
				}
			}
		} 
		return true;
	}
	
	private void blowUpBombs(Integer row, Integer col) {
		if (row < 10 && row >= 0 && col < 10 && col >= 0) { // 10x10 grid
			Integer currValue = grid.get(row).get(col);
			if (currValue < 10) {
				currValue += 1;
				grid.get(row).set(col, currValue);
				if (currValue > 9) {
					total += 1;
					blowUpBombs(row+1, col);
					blowUpBombs(row+1, col+1);
					blowUpBombs(row+1, col-1);
					
					blowUpBombs(row, col+1);
					blowUpBombs(row, col-1);
					
					blowUpBombs(row-1, col);
					blowUpBombs(row-1, col-1);
					blowUpBombs(row-1, col+1);	
				}
				
			}
		}
	}
	
	private void resetBlownToZero() {
		for (int row = 0; row < grid.size(); row++) {
			for (int col = 0; col < grid.get(row).size(); col++) {
				Integer val = grid.get(row).get(col);
				if (val > 9) {
					grid.get(row).set(col, 0);
				}
				
			}
		}
	}
	
	private void readLines() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line ;
			while ((line = br.readLine()) != null) {
				grid.add(Arrays.asList(line.split(""))
						.stream().map(i -> new Integer(i))
						.collect(Collectors.toList()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}