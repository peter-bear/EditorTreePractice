package editortrees;

import java.util.ArrayList;

/**
 * A node in a height-balanced binary tree with rank. Except for the NULL_NODE,
 * one node cannot belong to two different trees.
 * 
 * @author <<You>>
 */
public class Node {

	enum Code {
		SAME, LEFT, RIGHT;

		// Used in the displayer and debug string
		public String toString() {
			switch (this) {
			case LEFT:
				return "/";
			case SAME:
				return "=";
			case RIGHT:
				return "\\";
			default:
				throw new IllegalStateException();
			}
		}
	}

	// The fields would normally be private, but for the purposes of this class,
	// we want to be able to test the results of the algorithms in addition to the
	// "publicly visible" effects

	char data;
	Node left, right; // subtrees
	int rank; // inorder position of this node within its own subtree.
	Code balance;

	// Feel free to add other fields that you find useful.
	// You probably want a NULL_NODE, but you can comment it out if you decide
	// otherwise.
	// The NULL_NODE uses the "null character", \0, as it's data and null children,
	// but they could be anything since you shouldn't ever actually refer to them in
	// your code.
	static final Node NULL_NODE = new Node('\0', null, null);
	// Node parent; You may want parent, but think twice: keeping it up-to-date
	// takes effort too, maybe more than it's worth.

	public Node(char data, Node left, Node right) {
		// TODO: write this.
		this.data = data;
		this.left = left;
		this.right = right;
		this.balance = Code.SAME;
		this.rank = 0;
	}

	public Node(char data) {
		// Make a leaf
		this(data, NULL_NODE, NULL_NODE);
	}

	// Provided to you to enable testing, please don't change.
	int slowHeight() {
		if (this == NULL_NODE) {
			return -1;
		}
		return Math.max(left.slowHeight(), right.slowHeight()) + 1;
	}

	// Provided to you to enable testing, please don't change.
	public int slowSize() {
		if (this == NULL_NODE) {
			return 0;
		}
		return left.slowSize() + right.slowSize() + 1;
	}
	
	
	// You will probably want to add more constructors and many other
	// recursive methods here. I added 47 of them - most were tiny helper methods
	// to make the rest of the code easy to understand. My longest method was
	// delete(): 20 lines of code other than } lines. Other than delete() and one of
	// its helpers, the others were less than 10 lines long. Well-named helper
	// methods are more effective than comments in writing clean code.
	
	public String inOrderPrint() {
		if(this == NULL_NODE)
			return "";
		
		return this.left.inOrderPrint() + this.data + this.right.inOrderPrint();
	
	}
	
	// TODO: By the end of milestone 1, consider if you want to use the graphical debugger. See
	// the unit test throwing an error and the README.txt file.
	
	public Node add(char ch, int pos) {
		if(this == NULL_NODE) {
			return new Node(ch);
		}
		
		if(this.rank >= pos) {
			this.rank++;
			this.balance = Code.LEFT;
			this.left = this.left.add(ch, pos);
		}else if(this.rank < pos) {
			this.balance = Code.RIGHT;
			this.right = this.right.add(ch, pos-(this.rank+1));
		}
		
//		if(this.left != NULL_NODE) {
//			//single right rotate
//			if(this.balance == Code.LEFT && this.left.balance == Code.LEFT) {
//				return this.singleRightRotate();
//			}
//			//double right rotate (rotate left and rotate right)
//			else if(this.balance == Code.LEFT && this.left.balance == Code.RIGHT) {
//				return this.doubleRightRotate();
//			}
//		}
//		if(this.right != NULL_NODE) {
//			//single left rotate
//			if(this.balance == Code.RIGHT && this.right.balance == Code.RIGHT) {
//				return this.singleLeftRotate();
//			}
//			//double left rotate (rotate right and rotate left)
//			else if(this.balance == Code.RIGHT && this.right.balance == Code.LEFT) {
//				return this.doubleLeftRotate();
//			}
//		}
//		
//		if(this.left != NULL_NODE && this.right != NULL_NODE) {
//			if(this.left.balance == Code.SAME && this.right.balance == Code.SAME)
//				this.balance = Code.SAME;
//		}
		
		return this;
	}
	
	private Node singleLeftRotate() {
		Node tmp = this.right;
		this.right = tmp.left;
		tmp.left = this;
		tmp.balance = Code.SAME;
		this.balance = Code.SAME;
		
		return tmp;
	}
	
	private Node singleRightRotate() {
		Node tmp = this.left;
		this.left = tmp.right;
		tmp.right = this;
		tmp.balance = Code.SAME;
		this.balance = Code.SAME;
		
		return tmp;
	}
	
	private Node doubleLeftRotate() {
		this.right = this.right.singleRightRotate();
		return this.singleLeftRotate();
	}
	
	private Node doubleRightRotate() {
		this.left = this.left.singleLeftRotate();
		return this.singleRightRotate();
	}

	public String toRankString() {
		// TODO Auto-generated method stub
		if(this == NULL_NODE)
			return "";
		return this.data+""+this.rank+", " +this.left.toRankString() + this.right.toRankString();
	}

	public char get(int pos) {
		// TODO Auto-generated method stub
		if(this.rank == pos)
			return this.data;
		else if(this.rank > pos)
			return this.left.get(pos);
		else
			return this.right.get(pos-this.rank-1);
	}

	public boolean rankMatch() {
		// TODO Auto-generated method stub
		if(this == NULL_NODE)
			return true;
		
		boolean cur = (this.rank == this.left.slowSize());
		boolean left = this.left.rankMatch() ;
		boolean right = this.right.rankMatch();
		return cur && left && right;
	}
	
	
}