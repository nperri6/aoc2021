package aoc2021.day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javaProject.Challenge;

public class RunnerP2Comparor {
	
	Pair topPair;
	public RunnerP2Comparor(Pair pair, Pair pair2) {
		topPair = new Pair(null);
		topPair.left = pair;
		topPair.right = pair2;
		pair.parent = topPair;
		pair2.parent = topPair;
	}
	
	public Integer run() {
		runLogic();
		return topPair.getMagnitude();
	}
	
	private void runLogic() {
		runSimulation();
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