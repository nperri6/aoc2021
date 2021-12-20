package aoc2021.day18;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import javaProject.Challenge;

public class RunnerPart2 extends Challenge {

	Integer largestMagnitude = 0;
	
	List<String> lines = new ArrayList<>();
	
	Pair topPair;
	public RunnerPart2(String filename) {
		super(filename);
		run();
	}
	
	public static void main(String[] args) {
		new RunnerPart2("2021inputDayEighteen.txt");
	}
	
	
	private void run() {
		readLines();
		runLogic();
	}
	
	private void runLogic() {
		for (int i = 0; i < lines.size(); i++) {
			for (int j = 0; j < lines.size(); j++) {
				if (i != j) {
					Pair iPair = createPair(lines.get(i));
					Pair jPair = createPair(lines.get(j));
					RunnerP2Comparor r = new RunnerP2Comparor(iPair, jPair);
					Integer magnitude = r.run();
					if (magnitude > this.largestMagnitude) {
						this.largestMagnitude = magnitude;
					}
				}
			}
		}
		System.out.println("TOTAL MAGNITUDE " + largestMagnitude);
	}

	private void readLines() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line ;

			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private Pair createPair(String line) {
		String[] eles = line.split("");
		Stack<Pair> openPairs = new Stack<Pair>();
		List<Pair> allPairs = new ArrayList<>();
		boolean focusLeft = true;
		for (String ele : eles) {
			if (ele.equals("[")) {
				Pair prevPair = openPairs.isEmpty() ? null : openPairs.peek();
				Pair p = new Pair(prevPair);
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
		return initial;
	}
}