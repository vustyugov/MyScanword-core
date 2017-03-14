package sample.impls.tree;

public class Node <T> {
	private T cell;
	private boolean finishedNode;
	private int indexRow;
	private int indexColumn;
	private Node<T> linkFirst;
	private Node<T> linkSecond;
	private Node<T> linkThird;
	private Node<T> linkFourth;

	public Node () {
		
	}
	
	public Node (T node, int rowIndex, int columnIndex) {
		this.cell = node;
		this.indexRow = rowIndex;
		this.indexColumn = columnIndex;
	}
	
	public void setCell(T cell) {
		this.cell = cell;
	}
	public T getCell() {
		return this.cell;
	}
	public void setRowIndex(int value) {
		this.indexRow = value;
	}
	public int getRowIndex() {
		return this.indexRow;
	}
	public void setColumnIndex(int value) {
		this.indexColumn = value;
	}
	public int getColumnIndex() {
		return this.indexColumn;
	}
	public void setFirst(Node<T> first) {
		this.linkFirst = first;
	}
	public Node<T> getFirst() {
		return this.linkFirst;
	}
	public void setSecond(Node<T> second) {
		this.linkSecond = second;
	}
	public Node<T> getSecond() {
		return this.linkSecond;
	}
	public void setThird(Node<T> third) {
		this.linkThird = third;
	}
	public Node<T> getThird() {
		return this.linkThird;
	}
	public void setFourth(Node<T> fourth) {
		this.linkFourth = fourth;
	}
	public Node<T> getFourth() {
		return this.linkFourth;
	}
	
	public int getCountFreeLinks () {
		int count = 0;
		if (linkFirst == null) {
			count++;
		}
		if (linkSecond == null) {
			count++;
		}
		if (linkThird == null) {
			count++;
		}
		if (linkFourth == null) {
			count++;
		}
		return count;
	}
	
	public void setLink(Node<T> link) {
		if (linkFirst == null) {
			linkFirst = link;
			return ;
		}
		if (linkSecond == null) {
			linkSecond = link;
			return ;
		}
		if (linkThird == null) {
			linkThird = link;
			return ;
		}
		if (linkFourth == null) {
			linkFourth = link;
			return ;
		}
	}
	
	public boolean isLeaf() {
		if (linkFirst == null 
				&& linkSecond == null
				&& linkThird == null
				&& linkFourth == null) {
			return true;
		}
		else {
			return false;
		}
	}
		
	public void setFinishedNode(boolean value) {
		this.finishedNode = value;
	}
	
	public boolean isFinishedNode() {
		return finishedNode;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else {
			if (obj instanceof Node) {
				if (this.indexColumn == ((Node) obj).indexColumn 
						&& this.indexRow == ((Node)obj).indexRow
						&& cell.equals(((Node) obj).getCell())) {
					return true;
				}
			}
		}
		return false;
	}
}
