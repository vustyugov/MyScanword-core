package sample.impls.tree;

import java.util.*;

import sample.interfaces.Cell;

public class BinaryTree <T extends Cell> {
	Node<Cell> root;
	
	public BinaryTree() {
		root = null;
	}
	
	public Node<Cell> getRootNode() {
		return root;
	}
	
	public boolean find (Node<Cell> root, Node<Cell> node) {
		if (root.getRowIndex() == node.getRowIndex()
				&& root.getColumnIndex() == node.getColumnIndex()
				&&root.getCell().equals(node.getCell())) {
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
	
	public void insert (Node<Cell> rootNode, Node<Cell> childNode) {
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
		else if (this.find(root, childNode)) {
			return ;
		}

		else {
			Node<Cell> focusNode = rootNode;
			if (!focusNode.getCell().getClass().getName()
					.equals(childNode.getCell().getClass().getName())) {
				if (focusNode.getCountFreeLinks() > 0) {
					focusNode.setLink(childNode);
				}
			}
		}
	}

	public boolean remove (Node<Cell> root, Cell cell) {
		boolean lable = false;
		if (root != null) {
			if (root.getCell().equals(cell)) {
				root = null;
				lable = true;
			}
			else {
				if (root.getFirst()!= null) {
					lable = remove(root.getFirst(), cell);
				}
				if (root.getSecond()!= null) {
					lable = remove(root.getSecond(), cell);
				}
				if (root.getThird()!= null) {
					lable = remove(root.getThird(), cell);
				}
				if (root.getFourth()!= null) {
					lable = remove(root.getFourth(), cell);
				}
			}
		}
		return lable;
	}
	
	public int getCountLeaves(Node<Cell> node) {
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

	public Node<Cell>[] getLeavesTree(Node<Cell> node, Node<Cell>[] cells, int length) {
		if (node == null) {
			return null;
		}
		if (node.isLeaf()) {
			Node<Cell>[] tmp = new Node[cells.length];
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
	
	public List<List<Object[]>> getListBranchesTree(Node<Cell> node, List<List<Object[]>> list, List<Object[]> cells) {
		if (node != null) {
			List<Object[]> tmp = new ArrayList<Object[]>(cells);
			cells = new ArrayList<Object[]> (tmp.size()+1);
			cells.addAll(tmp);
			Object[] objs = {node.getRowIndex(), node.getColumnIndex(), node.getCell()};
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
		else {
			list.add(cells);
		}
		return list;
	}
	

}