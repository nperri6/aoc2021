package aoc2021.day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import javaProject.Challenge;

public class Runner extends Challenge {

	
	
	Pair topPair;
	public Runner(String filename) {
		super(filename);
		// TODO Auto-generated constructor stub
		run();
	}
	
	public static void main(String[] args) {
		new Runner("2021inputDayEighteenTest.txt");
	}
	
	
	private void run() {
		readLines();
		runLogic();
	}
	
	private void runLogic() {
		
		System.out.println("MAGNITUDE: " + topPair.getMagnitude());
	}

	private void readLines() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line ;

 			int i = 0;
			while ((line = br.readLine()) != null) {
				
				String[] eles = line.split("");
				Stack<Pair> openPairs = new Stack<Pair>();
				List<Pair> allPairs = new ArrayList<>();
				Pair currPair;
				boolean focusLeft = true;
				for (String ele : eles) {
					if (ele.equals("[")) {
						Pair prevPair = openPairs.isEmpty() ? null : openPairs.peek();
						Pair p = new Pair(prevPair);
//						focusLeft = true;
						if (prevPair != null) {
							if (focusLeft) {
								prevPair.left = p;
							} else {
								prevPair.right = p;
							}						
						}
						focusLeft = true;
						openPairs.push(p);
						allPairs.add(p);
//						stack.push(ele);
					} else if (ele.equals("]")) {
						openPairs.pop();
						focusLeft = true;
					} else if (ele.equals(",")) {
						focusLeft = !focusLeft;
					} else {
						Pair p = openPairs.peek();
						if (focusLeft) {
							p.left = new Num(null, new Integer(ele));
						} else {
							p.right = new Num(null, new Integer(ele));
						}
					}
					
				}
				Pair initial = allPairs.get(0);				
				if (i != 0) {
					Pair prevPair = topPair; 
					topPair = new Pair(null);
					topPair.left = prevPair;
					topPair.right = initial;
					prevPair.parent = topPair;
					initial.parent = topPair;
				} else {
					topPair = initial;
				}
				i++;
				runSimulation();
				StringBuilder sb = new StringBuilder();
//				System.out.println(topPair.toString(sb).toString());
			}


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void runSimulation() {
		explode();
		boolean didSplit = split();
		if (didSplit) {
//			System.out.println("DID SPLIT: " + topPair.toString(new StringBuilder()));
			runSimulation();
		}
	}
	
	
	public boolean split() {
		return topPair.split();
//		return false;
	}
	
	public void explode() {
		boolean checkForExplosion = true;
		while (checkForExplosion) {
			checkForExplosion = topPair.checkExplode(0);
			if (checkForExplosion) {
//				System.out.println("EXPLODED: " + topPair.toString(new StringBuilder()));
			}
		}
	}
	
}
