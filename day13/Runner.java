package aoc2021.day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javaProject.Challenge;

public class Runner extends Challenge {
	
	Set<Point> points = new HashSet<>();
	List<String> folds = new ArrayList<>();
	
	int maxX;
	int maxY;
	
	String orientation;
	Integer foldLine;

	public Runner(String filename) {
		super(filename);
		run();
	}
	
	public static void main(String[] args) {
		new Runner("2021inputDayThirteen.txt");
	}
	
	
	private void run() {
		readLines();
		runLogic();
	}
	
	private void runLogic() {
		determineMaxValues();
		foldLines();
		visualizePoints();
	}
	
	private void visualizePoints() {
		determineMaxValues();
		List<List<String>> map = new ArrayList<>();
		for (int i = 0; i <= maxY; i++) {
			List<String> line = new ArrayList<>();
			for (int j = 0; j <= maxX; j++) {
				line.add(".");
			}
			map.add(line);
		}
		
		for (Point p : points) {
			List<String> line = map.get(p.y);
			line.set(p.x, "#");
		}
		for (List<String> line : map) {
			System.out.println(line.stream().collect(Collectors.joining("")));			
		}
//		System.out.println(map);
	}
	
	private void determineMaxValues() {
		maxX = points.stream().sorted( (i1, i2) -> i2.x.compareTo(i1.x)).collect(Collectors.toList()).get(0).x;
		maxY = points.stream().sorted( (i1, i2) -> i2.y.compareTo(i1.y)).collect(Collectors.toList()).get(0).y;
		System.out.println("MAX X: " + maxX);
		System.out.println("MAX Y: " + maxY);
			
	}
	
	private void foldLines() {
		for (String line : folds) {
			determineFold(line);
			calculateFold();			
		}
	}
	
	private void calculateFold() {
		Set<Point> newPoints = new HashSet<>();
		for (Point p : points ) {
			if (orientation.equals("x")) {
				if (foldLine.equals(p.x)) {
					continue;					
				}
				if (p.x > foldLine) {
					p.x = foldLine - (p.x-foldLine); 
				}
				newPoints.add(p);
			}
			if (orientation.equals("y") ) {
				if (foldLine.equals(p.y)) {
					continue;					
				}
				if (p.y > foldLine) {
					p.y = foldLine - (p.y - foldLine); 
				}
				newPoints.add(p);
			}
		}
		points = newPoints;
		System.out.println("NEW POINTS: " + newPoints.size());
//		System.out.println(newPoints);
	}
	
	private void determineFold(String line) {
		String[] parts = line.split("=");
		orientation = parts[0];
		foldLine = new Integer(parts[1]);
	}
	
	
	private void readLines() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line ;
			
			while ((line = br.readLine()) != null) {
				if (line.contains("fold")) {
					folds.add(line.substring(11));
				} else if (!line.equals("")) {
					points.add(new Point(line));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private class Point {
		Integer x;
		Integer y;
		
		public Point(String line) {
			String[] eles = line.split(",");
			x = new Integer(eles[0]);
			y = new Integer(eles[1]);
		}
		
		 @Override
		    public int hashCode() 
		    {
		        return 10000*x + y*31;
		    }

		//equals Implementation    
		    @Override
		    public boolean equals(Object obj) 
		    {
		        if (this == obj)
		            return true;
		        if (obj == null)
		            return false;
		        if (getClass() != obj.getClass())
		            return false;
		        Point other = (Point) obj;
		        if (!this.y.equals(other.y))
		            return false;
		        if (!this.x.equals(other.x))
		            return false;
		        return true;
		    }
		    
		    @Override
		    public String toString() {
		    	return x+","+y;
		    }
		
	}


}