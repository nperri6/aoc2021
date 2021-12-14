package aoc2021.day14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javaProject.Challenge;

public class P2Runner extends Challenge {
	
	
	String template;
	String t1;
	String t2;
	String t3;
	String t4;
	
	StringBuilder tempBuilder = new StringBuilder();
	
	Map<String, String> dict = new HashMap<>();
	
	Map<String, Long> totals = new HashMap<>();
	
	String lowest = null;
	Long lowestCount = null;
	String highest = null;
	Long highestCount = null;
	
	Map<String, Long> letters = new HashMap<>();

	public P2Runner(String filename) {
		super(filename);
		run();
	}
	
	public static void main(String[] args) {
		new P2Runner("2021inputDayFourteen.txt");
	}
	
	
	private void run() {
		readLines();
		runLogic();
	}
	
	private void incrementPair(String pair, int i) {
		if (i >= 40) {
//			System.out.println("hit40");
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
		
		/**
		 * 	HIGHEST: B : 1749
		 *	LOWEST: H : 161
		 *  {B=1749, C=298, H=161, N=865}
		 *	1588
		 */
		System.out.println("THE ANSWER::: " + (highestCount - lowestCount));
		System.out.println("HIGHEST: " +  highest + " : " + highestCount);
		System.out.println("LOWEST: " + lowest + " : " + lowestCount);
//		System.out.println(totals);
//		System.out.println("1 SHOULD BE: " + Arrays.asList("NCNBCHB".split("")).stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
//		System.out.println("2 SHOULD BE: " + Arrays.asList("NBCCNBBBCBHCB".split("")).stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
//		System.out.println("3 SHOULD BE: " + Arrays.asList("NBBBCNCCNBBNBNBBCHBHHBCHB".split("")).stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
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
	
	
	
	
//	private void iterateTemplate(String t) {
//		String[] letters;
//		if (t.equals("t1")) {
//			letters = t1.split("");
//			
//		} else if (t.equals("t2")) {
//			letters = t2.split("");
//		}
//		else {
//			letters = t3.split("");
//		}
//		for (int i = 0; i < letters.length; i++) {
//			tempBuilder.append(letters[i]);
//			if (i < letters.length -1) {
//				tempBuilder.append(dict.get(letters[i] + letters[i+1]));
//			}
//		}
//		if (t.equals("t1")) {
//			t1 = tempBuilder.toString();
//		} else if (t.equals("t2")) {
//			t2 = tempBuilder.toString();
//		} else {
//			t3 = tempBuilder.toString();
//		}
//		tempBuilder = new StringBuilder();
//	}
	
	
	
//	t1 = "NN";
//	t2 = "NC";
//	t3 = "CB";
//	for (int i = 0; i < 10; i++) {
//		iterateTemplate("t1");
//	}
//	
//	for (int i = 0; i < 10; i++) {
//		iterateTemplate("t2");
//	}
//	for (int i = 0; i < 10; i++) {
//		iterateTemplate("t3");
//	}
//	System.out.println("T1: " + t1.length());
//	System.out.println(t1.substring(t1.length()-100));
//	
//	System.out.println("T2:" + t2.length());
//	System.out.println(t2);
//	System.out.println(t2.substring(0, 100));
//	
//	System.out.println("T3:");
//	System.out.println(t3);
//		
//	Map<String, Long> letters1 = Arrays.asList(t1.split("")).stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//	Map<String, Long> letters2 = Arrays.asList(t1.split("")).stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//	Map<String, Long> letters3 = Arrays.asList(t1.split("")).stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//	
//	letters1.entrySet().forEach(e -> {
//		if (letters.containsKey(e.getKey())) {
//			letters.put(e.getKey(), letters.get(e.getKey()) + e.getValue());
//		} else {
//			letters.put(e.getKey(), e.getValue());
//		}
//	});
//	
//	letters2.entrySet().forEach(e -> {
//		if (letters.containsKey(e.getKey())) {
//			letters.put(e.getKey(), letters.get(e.getKey()) + e.getValue());
//		} else {
//			letters.put(e.getKey(), e.getValue());
//		}
//	});
//	
//	letters3.entrySet().forEach(e -> {
//		if (letters.containsKey(e.getKey())) {
//			letters.put(e.getKey(), letters.get(e.getKey()) + e.getValue());
//		} else {
//			letters.put(e.getKey(), e.getValue());
//		}
//	});
//
//	
//	letters.entrySet().stream().forEach( e -> {
//		if (lowestCount == null || e.getValue() < lowestCount) {
//			lowest = e.getKey();
//			lowestCount = e.getValue();
//		}
//		if (highestCount == null || e.getValue() > highestCount) {
//			highest = e.getKey();
//			highestCount = e.getValue();
//		}
//	});
//	
//	/**
//	 * 	HIGHEST: B : 1749
//	 *	LOWEST: H : 161
//	 *	1588
//	 */
//	System.out.println("HIGHEST: " +  highest + " : " + highestCount);
//	System.out.println("LOWEST: " + lowest + " : " + lowestCount);
//	
//	System.out.println(highestCount - lowestCount);

}