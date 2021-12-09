package aoc2021.day9;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import javaProject.Challenge;

public class Runner extends Challenge {

	List<List<Integer>> grid = new ArrayList<>();
	List<Integer> lowPointsP1 = new ArrayList<>();
	List<Integer> basins = new ArrayList<>();
	List<LowPoint> lowPoints = new ArrayList<>();


	public Runner(String filename) {
		super(filename);
		run();
	}
	
	public static void main(String[] args) {
		new Runner("2021inputDayNine.txt");
	}
	
	
	private void run() {
		readLines();
		runLogic();
		findBasins();
	}
	
	private void findBasins() {
		for (LowPoint point : lowPoints) {
			Set<String> basin = new HashSet<String>();
			addNeighbors(basin, point.row, point.col);
			basins.add(basin.size());
		}
		Collections.sort(basins);
		System.out.println(basins.subList(basins.size()-3, basins.size()).stream().reduce(1, (i, j) -> i * j));
	}
	
	private Set<String> addNeighbors(Set<String> points, Integer row, Integer col) {
		if (col > 0) {
			Integer left = grid.get(row).get(col-1);
			if (left < 9 && !points.contains(row+","+(col-1))) {
				points.add(row+","+(col-1));
				points.addAll(addNeighbors(points, row, col-1));
			}
		}
		if (col < grid.get(row).size()-1) {
			Integer right = grid.get(row).get(col+1);
			if (right < 9 && !points.contains(row+","+(col+1))) {
				points.add(row+","+(col+1));
				points.addAll(addNeighbors(points, row, col+1));
			}
		}
		if (row > 0) {
			Integer top = grid.get(row-1).get(col);
			if (top < 9 && !points.contains((row-1)+","+col)) {
				points.add((row-1)+","+col);
				points.addAll(addNeighbors(points, row-1, col));
			}
		}
		if (row < grid.size()-1) {
			Integer bottom = grid.get(row+1).get(col);
			if (bottom < 9 && !points.contains((row+1)+","+col)) {
				points.add((row+1)+","+col);
				points.addAll(addNeighbors(points, row+1, col));
			}
		}
		return points;
	}
	
	private void runLogic() {
		for (int row = 0; row < grid.size(); row++) {
			for (int col = 0; col < grid.get(row).size(); col++) {
				if (isRowLow(row, col) && isColLow(row, col)) {
					lowPoints.add(new LowPoint(grid.get(row).get(col), row, col));
				}
			}
		}
	}
	
	
	private class LowPoint {
		
		public Integer lowPoint;
		public Integer row;
		public Integer col;
		
		public LowPoint(Integer lowPoint, Integer row, Integer col) {
			this.lowPoint = lowPoint;
			this.row = row;
			this.col = col;
		}
	}
		
	private void runLogicPart1() {
		for (int row = 0; row < grid.size(); row++) {
			for (int col = 0; col < grid.get(row).size(); col++) {
				if (isRowLow(row, col) && isColLow(row, col)) {
					lowPointsP1.add(grid.get(row).get(col)); // PART 1
				}
			}
		}
		Integer total = lowPointsP1.stream().map(i -> i + 1).reduce(0, (i,j) -> i + j); // Part 1
		System.out.println(total);
	}
	
	private boolean isRowLow(Integer row, Integer col) {
		Integer cell = grid.get(row).get(col);
		List<Integer> focusRow = grid.get(row);
		
		// Min to the left
		if (col > 0) {
			boolean hasOneLeftHigher = false;
			for (int i = col-1; i >= 0; i-- ) {
				if (focusRow.get(i) > cell) {
					if (!hasOneLeftHigher) {
						hasOneLeftHigher = true;
					} 
				} else {
					if (!hasOneLeftHigher) {
						return false;
					}
				}
			}
		}
		
		// Min to the right
		if (col < focusRow.size()-1) {
			boolean hasOneRightHigher = false;
			for (int i = col+1; i < focusRow.size(); i++ ) {
				if (focusRow.get(i) > cell) {
					if (!hasOneRightHigher) {
						hasOneRightHigher = true;
					}
				} else {
					if (!hasOneRightHigher) {
						return false;
					}
				}
			}		
		}
		
		return true;
	}
	
	private boolean isColLow(Integer row, Integer col) {
		Integer cell = grid.get(row).get(col);
		
		// Min to the top
		if (row > 0) {
			boolean hasOneTopHigher = false;
			for (int i = row-1; i >= 0; i-- ) {
				if (grid.get(i).get(col) > cell) {
					if (!hasOneTopHigher) {
						hasOneTopHigher = true;
					} 
				} else {
					if (!hasOneTopHigher) {
						return false;
					}
				}
			}
		}
		
		// Min to the bottom
		if (row < grid.size() - 1) {
			boolean hasOneBottomHigher = false;
			for (int i = row + 1; i < grid.size(); i++ ) {
				if (grid.get(i).get(col) > cell) {
					if (!hasOneBottomHigher) {
						hasOneBottomHigher = true;
					}
				} else {
					if (!hasOneBottomHigher) {
						return false;
					}
				}
			}
		}
		
		return true;
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
