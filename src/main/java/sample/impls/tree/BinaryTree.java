package sample.impls.tree;

import java.util.*;

public class BinaryTree {
	Node root;
	
	public BinaryTree() {
		root = null;
	}
	
	public Node getRootNode() {
		return root;
	}
	
	public Node getParent(Node child) {
		return child.getParent();
	}
		
	public boolean isParent(Node parent, Node child) {
		return (child.getParent().equals(parent))?true:false;
	}
		
	public boolean find (Node root, Node node) {
		if (root.getRowIndex() == node.getRowIndex()
				&& root.getColumnIndex() == node.getColumnIndex()
				&& root.getLinkValue().equals(node.getLinkValue())
				&& root.getLink().equals(node.getLink())) {
			return true;
		}
		if (root.getFirst() != null) {
			if (find(root.getFirst(), node)) {
				return true;
			}
		}
		if (root.getSecond() != null) {
			if (find(root.getSecond(), node)) {
				return true;
			}
		}
		if (root.getThird() != null) {
			if (find(root.getThird(), node)) {
				return true;
			}
		}
		if (root.getFourth() != null) {
			if (find(root.getFourth(), node)) {
				return true;
			}
		}
		return false;
	}
	
	public void insert (Node rootNode, Node childNode) {
		if (root == null) {
			if (rootNode == null) {
				root = childNode;
			}
			else {
				root = rootNode;
			}
			root.setFirst(null);
			root.setSecond(null);
			root.setThird(null);
			root.setFourth(null);
		}

		else {
			Node focusNode = rootNode;
			if (!focusNode.equals(childNode)) {
				focusNode.setLink(childNode);
				childNode.setParent(focusNode);
			}
		}
	}
	
	public int getCountNodes(Node node) {
		if (node == null) {
			return 0;
		}
		return 1 + getCountNodes(node.getFirst())
				+ getCountNodes(node.getSecond())
				+ getCountNodes(node.getThird())
				+ getCountNodes(node.getFourth());
	}
	
	public int getCountLeaves(Node node) {
		if (node == null) {
			return 0;
		}
		else if (node.isLeaf()) {
			return 1;
		}
		else {
			return  getCountLeaves(node.getFirst()) 
					+ getCountLeaves(node.getSecond()) 
					+ getCountLeaves(node.getThird()) 
					+ getCountLeaves(node.getFourth());
		}
	}

	public Node[] getLeavesTree(Node node, Node[] cells, int length) {
		if (node == null) {
			return null;
		}
		if (node.isLeaf()) {
			Node[] tmp = new Node[cells.length];
			tmp = cells.clone();
			cells = new Node[++length];
			System.arraycopy(tmp, 0, cells, 0, tmp.length);
			cells[length-1] = node;
			if (node.isFinishedNode()
					|| length == 20) {
				return cells;
			}
		}
		else {
			if (node.getFirst() != null) {
				cells = getLeavesTree(node.getFirst(), cells, cells.length);
			}
			if (node.getSecond() != null) {
				cells = getLeavesTree(node.getSecond(), cells, cells.length);
			}
			if (node.getThird() != null) {
				cells = getLeavesTree(node.getThird(), cells, cells.length);
			}
			if (node.getFourth() != null) {
				cells = getLeavesTree(node.getFourth(), cells, cells.length);
			}
		}
		return cells;
	}
	
	public List<List<Object[]>> getListBranchesTree(Node node, List<List<Object[]>> list, List<Object[]> cells) {
		if (node != null) {
			List<Object[]> tmp = new ArrayList<Object[]>(cells);
			cells = new ArrayList<Object[]> (tmp.size()+1);
			cells.addAll(tmp);
			Object[] objs = {node.getRowIndex(), node.getColumnIndex(), node.getLinkValue(), node.getLink(), node.isFinishedNode()};
			cells.add(cells.size(), objs);
		
			if (node.isFinishedNode()) {
				list.add(cells);
				return list;
			}
			else {
				getListBranchesTree(node.getFirst(), list, cells);
				getListBranchesTree(node.getSecond(), list, new ArrayList<Object[]> (cells));
				getListBranchesTree(node.getThird(), list, new ArrayList<Object[]> (cells));
				getListBranchesTree(node.getFourth(), list, new ArrayList<Object[]> (cells));
			}
		}
		return list;
	}
	
	private void removeLeaf(Node node, int treeLevel, int level) {
		if (node == null) {
			return;
		}
		if (node.isLeaf()) {
			node = null;
		}
		else {
			treeLevel++;
			if (level > treeLevel) {
				removeLeaf(node.getFirst(), treeLevel, level);
				removeLeaf(node.getSecond(), treeLevel, level);
				removeLeaf(node.getThird(), treeLevel, level);
				removeLeaf(node.getFourth(), treeLevel, level);
			}
		}
	}
	
	public void removeAllLeavesByLevel(int level) {
		removeLeaf(root, 0, level);
	}
}