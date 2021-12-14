package aoc2021.day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javaProject.Challenge;

public class Runner extends Challenge {
	
	
	String template;
	StringBuilder tempBuilder = new StringBuilder();
	
	Map<String, String> dict = new HashMap<>();
	
	Map<String, Integer> totals = new HashMap<>();
	
	String lowest = null;
	Long lowestCount = null;
	String highest = null;
	Long highestCount = null;

	public Runner(String filename) {
		super(filename);
		run();
	}
	
	public static void main(String[] args) {
		new Runner("2021inputDayFourteenTest.txt");
	}
	
	
	private void run() {
		readLines();
		runLogic();
	}
	
	private void runLogic() {
		for (int i = 0; i < 10; i++) {
			iterateTemplate();
			System.out.println("iteration number: " + i);
		}
		Map<String, Long> letters = Arrays.asList(template.split("")).stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		
		letters.entrySet().stream().forEach( e -> {
			if (lowestCount == null || e.getValue() < lowestCount) {
				lowest = e.getKey();
				lowestCount = e.getValue();
			}
			if (highestCount == null || e.getValue() > highestCount) {
				highest = e.getKey();
				highestCount = e.getValue();
			}
		});
		
		
		
		System.out.println("HIGHEST: " +  highest + " : " + highestCount);
		System.out.println("LOWEST: " + lowest + " : " + lowestCount);
		System.out.println(letters);
		System.out.println(highestCount - lowestCount);
		
		
	}
	
	private void iterateTemplate() {
		String[] letters = template.split("");
		for (int i = 0; i < letters.length; i++) {
			tempBuilder.append(letters[i]);
			if (i < letters.length -1) {
				tempBuilder.append(dict.get(letters[i] + letters[i+1]));
			}
		}
		template = tempBuilder.toString();
		tempBuilder = new StringBuilder();
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