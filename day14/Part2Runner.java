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
	
	private void incrementPair(String pair, int i) {
		if (i >= 40) {
			return;
		}
		String newLetter = dict.get(pair);
		if (totals.containsKey(newLetter)) {
			totals.put(newLetter, totals.get(newLetter) + 1);			
		}  else {
			totals.put(newLetter, 1L);
		}
		String[] eles = pair.split("");
		incrementPair(eles[0]+newLetter, i + 1);
		incrementPair(newLetter + eles[1], i + 1);
		
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
			incrementPair(eles[i]+eles[i+1], 0);
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