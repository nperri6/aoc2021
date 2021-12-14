package aoc2021.day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javaProject.Challenge;

public class Part2Runner extends Challenge {
	
	
	String template;
	
	Map<String, String> dict = new HashMap<>();
	Map<String, Long> pairs = new HashMap<>();
	Map<String, Long> newPairs = new HashMap<>();
	Map<String, Long> totals = new HashMap<>();
	
	String lowest = null;
	Long lowestCount = null;
	String highest = null;
	Long highestCount = null;
  
	public Part2Runner(String filename) {
		super(filename);
		run();
	}
	
	public static void main(String[] args) {
		new Part2Runner("2021inputDayFourteen.txt");
	}
	
	
	private void run() {
		readLines();
		runLogic();
	}
	
	private void iteratePairs() {
		try {
			pairs.entrySet().forEach(e -> {
				String newLetter = dict.get(e.getKey());
				if (newLetter == null) {
					String test = "test";
					test.toCharArray();
				};
				if (totals.containsKey(newLetter)) {
					totals.put(newLetter, totals.get(newLetter) + e.getValue());			
				}  else {
					totals.put(newLetter, e.getValue());
				}
				String[] letters = e.getKey().split("");
				if (newPairs.containsKey(letters[0] + newLetter)) {
					newPairs.put(letters[0] + newLetter, newPairs.get(letters[0] + newLetter) + e.getValue());			
				}  else {
					newPairs.put(letters[0] + newLetter, e.getValue());
				}
				if (newPairs.containsKey(newLetter + letters[1])) {
					newPairs.put(newLetter + letters[1], newPairs.get(newLetter + letters[1]) + e.getValue());			
				}  else {
					newPairs.put(newLetter + letters[1], e.getValue());
				}
			});
			pairs = newPairs;
			newPairs = new HashMap<>();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void runLogic() {
		String[] eles = template.split("");
		for (String ele : eles) {
			if (totals.containsKey(ele)) {
				totals.put(ele, totals.get(ele) + 1);			
			}  else {
				totals.put(ele, 1L);
			}
		}
		
		for (int i = 0; i < eles.length-1; i++) {
			String pair = eles[i]+eles[i+1];
			if (pairs.containsKey(pair)) {
				pairs.put(pair, pairs.get(pair)+1);
			} else {
				pairs.put(pair,  1L);
			}
		}
		
		for (int i = 0; i < 40; i++) {
			iteratePairs();
		}
		
		System.out.println("finished incrementing pairs");
		totals.entrySet().stream().forEach( e -> {
			if (lowestCount == null || e.getValue() < lowestCount) {
				lowest = e.getKey();
				lowestCount = e.getValue();
			}
			if (highestCount == null || e.getValue() > highestCount) {
				highest = e.getKey();
				highestCount = e.getValue();
			}
		});
		
		System.out.println("THE ANSWER::: " + (highestCount - lowestCount));
		System.out.println("HIGHEST: " +  highest + " : " + highestCount);
		System.out.println("LOWEST: " + lowest + " : " + lowestCount);
	}
	

	
	private void readLines() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line ;
			
			int i = 0;
			while ((line = br.readLine()) != null) {
				if (i == 0) {
					template = line;
				} else if (!line.equals("")) {
					String[] eles = line.split(" -> ");
					dict.put(eles[0], eles[1]);
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}