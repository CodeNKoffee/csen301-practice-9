@SuppressWarnings("rawtypes")

class Node {

	public Comparable data;
	public Node left,right;

	public Node (Comparable data){
		this(data, null, null);
	}

	public Node(Comparable data, Node left, Node right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}		
}

class BTree {
	private Node root;

	public BTree() {
		root = null;
	}

	public void add(Comparable key) {
		Node current = root, parent = null;
		while (current != null) {
			if (key.compareTo(current.data) < 0) {
				parent = current;
				current = current.left;
			}

			else{
				parent = current;
				current = current.right;
			}
		}

		if (parent == null)
			root = new Node(key);

		else {
			if (key.compareTo(parent.data) < 0)
				parent.left = new Node(key);

			else
				parent.right = new Node(key);
		}
	}

	public boolean delete(Comparable key) {
        if (root == null)
					return false;
        Node current = root;
        Node parent = root;
        boolean right = true;
        // searching for the node to be deleted
        while (key.compareTo(current.data) != 0) {  
					if (key.compareTo(current.data) < 0) {         right = false;
						parent = current;
						current = current.left;
					} else {
						right = true;
						parent = current;
						current = current.right;
					}
					if (current == null)
						return false;
        }

        Node substitute = null;
        //  case 1: Node to be deleted has no children
        if (current.left == null && current.right == null)
					substitute = null;

        //  case 2: Node to be deleted has one child
        else if (current.left == null)
					substitute = current.right;
        else if (current.right == null)
					substitute = current.left;
        else { // case 3: Node to be deleted has two children
					Node successor = current.right;
					Node successorParent = current;
					//  searching for the inorder successor of the node to be deleted
					while (successor.left != null) {
						successorParent = successor;
						successor = successor.left;
					}
					substitute = successor;
					if (successorParent == current) {
						if (successor.right == null)
									successorParent.right = null;
						else
									successorParent.right = successor.right;
					} else {
						if (successor.right == null)
							successorParent.left = null;
						else
							successorParent.left = successor.right;
					}
					successor.right = current.right;
					successor.left = current.left;
					substitute = successor;
				} // case 3 done
				if (current == root) // Replacing the deleted node
					root = substitute;
				else if (right)
					parent.right = substitute;
				else
					parent.left = substitute;
				return true;
	}

	public void displayTree() {
		java.util.Stack<Node> globalStack = new java.util.Stack<Node>();
		globalStack.push(root);
		int nBlanks = 32;
		boolean isRowEmpty = false;
		System.out.println(
		"......................................................");
		while(isRowEmpty==false) {
			java.util.Stack<Node> localStack = new java.util.Stack<Node>();
			isRowEmpty = true;

			for(int j=0; j<nBlanks; j++)
				System.out.print(' ');

			while(globalStack.isEmpty()==false) {
				Node temp = globalStack.pop();
				if(temp != null) {
					System.out.print(temp.data);
					localStack.push(temp.left);
					localStack.push(temp.right);

					if(temp.left != null ||
							temp.right != null)
						isRowEmpty = false;
				}
				else {
					System.out.print("--");
					localStack.push(null);
					localStack.push(null);
				}
				for(int j=0; j<nBlanks*2-2; j++)
					System.out.print(' ');

			}  // end while globalStack not empty
			System.out.println();
			nBlanks /= 2;
			while(localStack.isEmpty()==false)
				globalStack.push( localStack.pop() );
		}  // end while isRowEmpty is false
		System.out.println(
		"......................................................");
	}

	// EXERCISE 9-5

	// a) Count the number of nodes in a tree
	public int size() {
		return size(root);
	}

	private int size(Node node) {
		if (node == null)
			return 0;
		return 1 + size(node.left) + size(node.right);
	}

	// b) Count the number of leaves in the tree
	public int numLeaves() {
		return numLeaves(root);
	}

	private int numLeaves(Node node) {
		if (node == null)
			return 0;

		if (node.left == null && node.right == null) 
			return 1;

		return numLeaves(node.left) + numLeaves(node.right);
	}

	// c) Calculate the sum of all nodes in the tree of integers
	public int sum() {
		return sum(root);
	}

	private int sum(Node node) {
		if (node == null)
			return 0;
		
		return (int) node.data + sum(node.left) + sum(node.right);
	}

	// d) Check if the tree is a binary search tree
	public boolean isBST() {
		return isBST(root, null, null);
	}

	private boolean isBST(Node node, Comparable min, Comparable max) {
		if (node == null)
			return true;

		if (min != null && node.data.compareTo(min) <= 0 || max != null && node.data.compareTo(max) >= 0)
			return false;

		return isBST(node.left, min, node.data) && isBST(node.right, node.data, max);
	}

	// e) Count the number of nodes having a left child and no right child
	public int numLeftChildNodes() {
		return numLeftChildNodes(root);
	}

	private int numLeftChildNodes(Node node) {
		if (node == null) {
			return 0;
		}
		int count = 0;
		if (node.left != null && node.right == null) 
			count = 1; 

		return count + numLeftChildNodes(node.left) + numLeftChildNodes(node.right);
	}

	// f) Count the occurrences of nodes with the given key
	public int countOccur(Comparable key) {
		return countOccur(root, key);
	}

	private int countOccur(Node node, Comparable key) {
		if (node == null) {
			return 0;
		}
		int count = 0;
		if (node.data.equals(key)) {
			count = 1;
		}
		return count + countOccur(node.left, key) + countOccur(node.right, key);
	}

	// g) Check if the tree has duplicates of the given key
	public boolean hasDup(Comparable key) {
		return countOccur(key) > 1;
	}

	// h) Convert the binary tree into its mirror
	public void mirror() {
		mirror(root);
	}

	private void mirror(Node node) {
		if (node != null) {
			Node temp = node.left;
			node.left = node.right;
			node.right = temp;
			mirror(node.left);
			mirror(node.right);
		}
	}

	// i) Get a sequence of odd numbers in the tree as a String
	public String oddNodes() {
		StringBuilder result = new StringBuilder();
		oddNodes(root, result);
		return result.toString();
	}

	private void oddNodes(Node node, StringBuilder result) {
		if (node != null) {
			if ((int) node.data % 2 != 0) {
				result.append(node.data).append(" ");
			}
			oddNodes(node.left, result);
			oddNodes(node.right, result);
		}
	}

	public static void main(String[] args) {
		BTree bt = new BTree();
		bt.root.data = 1;
		bt.root.left.data = 2;
		bt.root.left.left.data = 10;
		bt.root.right.data = 3;
		bt.root.right.left.data = 4;
		bt.root.right.right.data = 5;
	}
}