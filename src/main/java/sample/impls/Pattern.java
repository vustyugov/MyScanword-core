package sample.impls;

import java.util.*;

import sample.interfaces.Cell;

public final class Pattern {
	public static enum Position {
		LEFT_BOUNDARY, 
		TOP_BOUNDARY,
		BOTTOM_BOUNDARY,
		RIGHT_BOUNDARY,
		CENTRAL,
		LEFT_TOP_ANGLE,
		RIGHT_TOP_ANGLE,
		RIGHT_BOTTOM_ANGLE,
		LEFT_BOTTOM_ANGLE
	}
	
	private Position position = null;
	private List<int[]> indexesCellsWithoutFreeLinks = null;
	private List<int[]> indexesCellsWithOneFreeLink = null;
	private List<int[]> indexesCellsWithTwoFreeLinks = null;
	private Cell[][] array = null;
	
	public Pattern(Position position, List<int[]> indexesCellsWithoutFreeLinks, 
			List<int[]> indexesCellsWithOneFreeLinks, List<int[]> indexesCellsWithTwoFreeLinks, Cell[][] array) {
		this.position = position;
		this.array = array;
		if (indexesCellsWithoutFreeLinks != null) {
			this.indexesCellsWithoutFreeLinks = indexesCellsWithoutFreeLinks;
		}
		else {
			this.indexesCellsWithoutFreeLinks = new LinkedList<int[]>();
			
		}
		if (indexesCellsWithOneFreeLinks != null) {
			this.indexesCellsWithOneFreeLink = indexesCellsWithOneFreeLinks;
		}
		else {
			this.indexesCellsWithOneFreeLink = new LinkedList<int[]>();
		}
		if (indexesCellsWithTwoFreeLinks != null) {
			this.indexesCellsWithTwoFreeLinks = indexesCellsWithTwoFreeLinks;
		}
		else {
			this.indexesCellsWithTwoFreeLinks = indexesCellsWithTwoFreeLinks;
		}
	}
	
	public Position getPosition() {
		return position;
	}
	
	public List<int[]> getIndexesCellWithoutFreeLinks () {
		return indexesCellsWithoutFreeLinks;
	}
	
	public List<int[]> getIndexesCellWithOneFreeLinks () {
		return indexesCellsWithOneFreeLink;
	}
	
	public List<int[]> getIndexesCellWithTwoFreeLinks () {
		return indexesCellsWithTwoFreeLinks;
	}
	
	public Cell[][] getArray() {
		return array;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(position);
		buf.append("\n");
		for (int index = 0; index <indexesCellsWithoutFreeLinks.size(); index++) {
			buf.append(indexesCellsWithoutFreeLinks.get(index));
		}
    	buf.append("\n");
    	for (int index = 0; index <indexesCellsWithOneFreeLink.size(); index++) {
			buf.append(indexesCellsWithOneFreeLink.get(index));
		}
    	buf.append("\n");
    	for (int index = 0; index <indexesCellsWithTwoFreeLinks.size(); index++) {
			buf.append(indexesCellsWithTwoFreeLinks.get(index));
		}
    	buf.append("\n");
    	for (int indexR = 0; indexR < array.length; indexR++) {
    		for (int indexC = 0; indexC < array[0].length; indexC++) {
    			buf.append(String.format("%-24s", array[indexR][indexC]));
    		}
    		buf.append("\n");
    	}
    	return buf.toString();
	}
}