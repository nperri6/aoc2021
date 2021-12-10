package aoc2021.day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;


import javaProject.Challenge;

public class Runner extends Challenge {
	Map<String, Integer> pointsMap = new HashMap<>();
	Map<String, Integer> autoCompleteMap = new HashMap<>();
	Map<String, String> openingMap = new HashMap<>();
	Map<String, String> closingMap = new HashMap<>();
	List<Long> totals = new ArrayList<>();
	Integer total = 0;

	public Runner(String filename) {
		super(filename);
		run();
	}
	
	public static void main(String[] args) {
		new Runner("2021inputDayTen.txt");
//		new Runner("2021inputDayTenTest.txt");
	}
	
	
	private void run() {
		initMaps();
		readLines();
		runLogic();
	}
	
	private void initMaps() {
		pointsMap.put(")", 3);
		pointsMap.put("]", 57);
		pointsMap.put("}", 1197);
		pointsMap.put(">", 25137);
		
		autoCompleteMap.put(")", 1);
		autoCompleteMap.put("]", 2);
		autoCompleteMap.put("}", 3);
		autoCompleteMap.put(">", 4);
		
		openingMap.put("(", ")");
		openingMap.put("[", "]");
		openingMap.put("{", "}");
		openingMap.put("<", ">");
		
		closingMap.put(")", "(");
		closingMap.put("]", "[");
		closingMap.put("}", "{");
		closingMap.put(">", "<");
	}

	
	private void runLogic() {
		Collections.sort(totals);
		System.out.println(totals.get(totals.size()/2));
	}
	
	private void readLines() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line ;
			while ((line = br.readLine()) != null) {
				Stack<String> stack = new Stack<>();
				List<String> elements = Arrays.asList(line.split(""));
				boolean lineValid = true;
				for (String ele : elements) {
					if (openingMap.containsKey(ele)) {
						stack.push(ele);
					} else if (closingMap.containsKey(ele)) {
						if (!closingMap.get(ele).equals(stack.peek())) {
							lineValid = false;
							break;
						} else {
							stack.pop();
						}
						
					}
				}
				if (lineValid) {
					Long lineTotal = 0L;
					while (!stack.empty()) {
						lineTotal *= 5;
						lineTotal += autoCompleteMap.get(openingMap.get(stack.pop()));
					}
					if (lineTotal != 0) {
						totals.add(lineTotal);
					}
					
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void readLinesPart1() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line ;
			while ((line = br.readLine()) != null) {
				Stack<String> stack = new Stack<>();
				List<String> elements = Arrays.asList(line.split(""));
				for (String ele : elements) {
					if (openingMap.containsKey(ele)) {
						stack.push(ele);
					} else if (closingMap.containsKey(ele)) {
						if (stack.empty() || !closingMap.get(ele).equals(stack.peek())) {
							total += pointsMap.get(ele);
							break;
						} else {
							stack.pop();
						}
						
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}