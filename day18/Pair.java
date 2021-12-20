package aoc2021.day18;

public class Pair extends Element{
	Element left;
	Element right;
	
	public Pair(Pair parent) {
		super(parent);
	}
	
	public Integer getMagnitude() {
		Integer leftMag;
		Integer rightMag;
		
		if (left.isNum()) {
			Num l = (Num) left;
			leftMag = l.value * 3;
		} else {
			Pair p = (Pair) left;
			leftMag = p.getMagnitude() * 3;
		}
		if (right.isNum()) {
			Num l = (Num) right;
			rightMag = l.value * 2;
		} else {
			Pair p = (Pair) right;
			rightMag = p.getMagnitude() * 2;
		}
		
		return leftMag + rightMag;
	}
	
	
	
	public StringBuilder toString(StringBuilder sb) {
		sb.append("[");
		left.toString(sb);
		sb.append(",");
		right.toString(sb);
		sb.append("]");
		return sb;
	}
	
	public boolean split() {
		if (left.isNum()) {
			Num l = (Num) left;
			if (l.value >= 10) {
				Integer value = l.value;
				Pair p = new Pair(this);
				Integer lft = new Double(Math.floor(new Double(value) / new Double(2))).intValue();
				Integer rght = new Double(Math.ceil(new Double(value) / new Double(2))).intValue();
				p.left = new Num(null, lft);
				p.right = new Num(null, rght); 
				left = p;
				return true;
			}
		} else {
			Pair l = (Pair) left;
			boolean split = l.split();
			if (split) {
				return true;
			}
		}
		if (right.isNum()) {
			Num l = (Num) right;
			if (l.value >= 10) {
				Integer value = l.value;
				Pair p = new Pair(this);
				Integer lft = new Double(Math.floor(new Double(value) / new Double(2))).intValue();
				Integer rght = new Double(Math.ceil(new Double(value) / new Double(2))).intValue();
				p.left = new Num(null, lft);
				p.right = new Num(null, rght); 
				right = p;
				return true;
			}
		} else {
			Pair l = (Pair) right;
			boolean split = l.split();
			if (split) {
				return true;
			}
		}
		return false;
	}
	
	
	public void explode() {
		boolean leftExploded = explodeLeft();
		boolean rightExploded = explodeRight();
		consolidateExplosion();
//		System.out.println("EXPLODED");
	}
	
	public void consolidateExplosion() {
		if (this.equals(parent.right)) {
			parent.right = new Num(null, 0);
		} else {
			parent.left = new Num(null, 0);
		}
	}
	
	public boolean explodeLeft() {
		Num left = findClosestLeft();
		if (left != null) {
			Num thisLeft = (Num) this.left;
			left.value = left.value + thisLeft.value;
			return true;
		}
		return false;
	}
	
	public Pair parentWhereRight() {
		if (parent == null) {
			return null;
		}
		if (this.equals(parent.right)) {
			return parent;
		} else {
			return parent.parentWhereRight();
		}
	}
	
	public Num findClosestLeft() {
		if (this.equals(parent.right)) {
			if (parent.left.isNum()) {
				return (Num) parent.left;
			} else {
				Pair left = (Pair) parent.left;
				return left.findLowestRight();
			}
		} else {
			/*
			 * 		G
			 * 	   / \
			 * 	 PL	  P
			 * 		 /
			 * 		 C   
			 * 
			 *  
			 *      	G
			 * 	       /
			 * 	 	  P
			 * 		 /
			 * 		 C    
			 *  
			 */
			
			Pair levelWhereRight = parentWhereRight();
			if (levelWhereRight == null) {
				return null;
			}
			if (levelWhereRight.left.isNum()) {
				return (Num) levelWhereRight.left;
			} 
			Pair left = (Pair) levelWhereRight.left;
			return left.findLowestRight();
			
		}
	}
	
	public Num findLowestRight() {
		if (this.right.isNum()) {
			return (Num) this.right;
		} else {
			Pair right = (Pair) this.right;
			return right.findLowestRight();
		}
	}
	
	public Num findLowestLeft() {
		if (this.left.isNum()) {
			return (Num) this.left;
		} else {
			Pair left = (Pair) this.left;
			return left.findLowestLeft();
		}
	}
	
	public Num findClosestRight() {
		if (this.equals(parent.left)) {
			if (parent.right.isNum()) {
				return (Num) parent.right;
			} else {
				Pair right = (Pair) parent.right;
				return right.findLowestLeft();
			}
		} else {
			
			Pair levelWhereLeft = parentWhereLeft();
			if (levelWhereLeft == null) {
				return null;
			}
			if (levelWhereLeft.right.isNum()) {
				return (Num) levelWhereLeft.right;
			} 
			Pair left = (Pair) levelWhereLeft.right;
			return left.findLowestLeft();
			
		}
	}
	
	public Pair parentWhereLeft() {
		if (parent == null) {
			return null;
		}
		if (this.equals(parent.left)) {
			return parent;
		} else {
			return parent.parentWhereLeft();
		}
	}
	
	public boolean explodeRight() {
		Num right = findClosestRight();
		if (right != null) {
			Num thisRight = (Num) this.right;
			right.value = right.value + thisRight.value;
			return true;
		}
		return false;
	}
	
	public boolean checkExplode(int level) {
		boolean exploded = false;
		if (level == 4) {
			explode();
			return true;
		}
		exploded = left.checkExplode(level + 1);
		if (!exploded) {
			exploded = right.checkExplode(level + 1);
		}
		return exploded;
	}
	
	public boolean isNum() {
		return false;
	}
}
