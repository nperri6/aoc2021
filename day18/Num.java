package aoc2021.day18;

public class Num extends Element {
	Integer value;
	
	public Num(Pair parent, Integer value) {
		super(parent);
		this.value = value;
	}
	
	public StringBuilder toString(StringBuilder sb) {
		sb.append(value);
		return sb;
	}
	public boolean isNum() {
		return true;
	}
}