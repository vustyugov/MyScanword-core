package sample.impls;

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
	private int[] keyValue = {-1,-1};
	private Cell[][] array = null;
	
	public Pattern(Position position, int[] keyValue, Cell[][] array) {
		this.position = position;
		this.array = array;
		if (keyValue != null) {
			this.keyValue = keyValue;
		}
	}
	
	public Position getPosition() {
		return position;
	}
	
	public int[] getKeyValue () {
		return keyValue;
	}
	
	public Cell[][] getArray() {
		return array;
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append(position);
		buf.append("\n");
    	buf.append(keyValue);
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