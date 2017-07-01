package sample.impls.tree;

public class Node {
	private String linkValue;
	private String link;
	private boolean finishedNode;
	private int indexRow;
	private int indexColumn;
	private Node parent;
	private Node linkFirst;
	private Node linkSecond;
	private Node linkThird;
	private Node linkFourth;

	public Node () {
		
	}
	
	public Node (String linkValue, String link, int rowIndex, int columnIndex) {
		this.linkValue = linkValue;
		this.link = link;
		this.indexRow = rowIndex;
		this.indexColumn = columnIndex;
	}
	
	public void setLinkValue(String linkValue) {
		this.linkValue = linkValue;
	}
	public String getLinkValue() {
		return this.linkValue;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getLink() {
		return link;
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
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public Node getParent() {
		return this.parent;
	}
	public void setFirst(Node first) {
		this.linkFirst = first;
	}
	public Node getFirst() {
		return this.linkFirst;
	}
	public void setSecond(Node second) {
		this.linkSecond = second;
	}
	public Node getSecond() {
		return this.linkSecond;
	}
	public void setThird(Node third) {
		this.linkThird = third;
	}
	public Node getThird() {
		return this.linkThird;
	}
	public void setFourth(Node fourth) {
		this.linkFourth = fourth;
	}
	public Node getFourth() {
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
	
	public void setLink(Node link) {
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
						&& linkValue.equals(((Node) obj).getLinkValue())
						&& link.equals(((Node)obj).getLink())) {
					return true;
				}
			}
		}
		return false;
	}
}
