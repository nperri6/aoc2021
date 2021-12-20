package aoc2021.day18;

public class Element {		
	Pair parent;
	public Element(Pair parent) {
		this.parent = parent;
	}
	
	public StringBuilder toString(StringBuilder sb) {
		return sb;
	}
	
	public boolean checkExplode(int level) {
		return false;
	}
	
	public boolean isNum() {
		return false;
	}
}
