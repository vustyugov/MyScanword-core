package sample.utils;

import java.util.*;

import sample.impls.cell.*;
import sample.interfaces.*;

public final class LinkUtils {
	protected static int[] transformateDirectionIndexToDiffCoordinateCell(int directionIndex) {
		switch(directionIndex) {
		case 1:
			int[] first = {0, 1};
			return first;
		case 2:
			int[] second = {1, 1};
			return second;
		case 3:
			int[] third = {1, 0};
			return third;
		case 4:
			int[] fourth = {1, -1};
			return fourth;
		case 5:
			int[] fifth = {0, -1};
			return fifth;
		case 6:
			int[] sixth = {-1, -1};
			return sixth;
		case 7:
			int[] seventh = {-1, 0};
			return seventh;
		case 8:
			int[] eighth = {-1, 1};
			return eighth;
		default:
			int[] def = {0,0};
			return def;
		}
	}
	
	protected static int oppositeDirectionIndex(int directionIndex) {
		return (directionIndex + 4) - 8*(directionIndex/5);
	}
	
	public static int transformateDiffCoordinateToDirectionIndex(int dRow, int dColumn) {
		if (dRow == 0 && dColumn == 1) {
 			return 1;
 		}
 		else if (dRow == 1 && dColumn == 1) {
 			return 2;
 		}
 		else if (dRow == 1 && dColumn == 0) {
 			return 3;
 		}
 		else if (dRow == 1 && dColumn == -1) {
 			return 4;
 		}
 		else if (dRow == 0 && dColumn == -1) {
 			return 5;
 		}
 		else if (dRow == -1 && dColumn == -1) {
 			return 6;
 		}
 		else if (dRow == -1 && dColumn == 0) {
 			return 7;
 		}
 		else if (dRow == -1 && dColumn == 1) {
 			return 8;
 		}
 		else {
 			return -1;
 		}
	}

	private static String getLocationCurrentCell (int countRows, int countColumns, int row, int column) {
		if (row == 0) {
			if (column == 0) {
				return "leftTop";
			}
			else if (column == countColumns-1) {
				return "rightTop";
			}
			else {
				return "top";
			}
			
		}
		else if (row == countRows-1) {
			if (column == 0) {
				return "leftBottom";
			}
			else if (column == countColumns-1) {
				return "rightBottom";
			}
			else {
				return "bottom";
			}
		}
		else {
			if (column == 0) {
				return "left";
			}
			else if (column == countColumns - 1) {
				return "right";
			}
			else {
				return "central";
			}
		}
	}
	
	public static int[] getCountAvailableCells(Scanword scanword) {
		int cCount = 0;
		int aCount = 0;
		int[] count = new int[2];
		for (int row = 0; row < scanword.getRows(); row++) {
			for (int column = 0; column < scanword.getColumns(); column++) {
				Cell cell = scanword.getArrayElement(row, column);
				if (cell instanceof ActiveCell && cell.getCountFreeLink() > 0) {
					aCount++;
				}
				if (cell instanceof CommentCell && cell.getCountFreeLink() == 2) {
					cCount++;
				}
			}
		}
		count[0] = cCount;
		count[1] = aCount;
		return count;
	}
	
	protected static List<Integer> getCountNeighbourCellsOtherType(Cell[][] array, int row, int column, 
			Class<? extends Cell> currentCellType, Class<? extends Cell> neighbourCellType) {
		List<Integer> list = new LinkedList<Integer> ();
		Integer[] direction = ArrowUtils.positions.get(getLocationCurrentCell(array.length, array[0].length, row, column));
				
		for (int index = 0; index < direction.length; index++) {
			int[] indexes = transformateDirectionIndexToDiffCoordinateCell(direction[index]);
			if (CellUtils.isType(array[row][column], currentCellType)
					&& CellUtils.isType(array[row+indexes[0]][column+indexes[1]], neighbourCellType)) {
				list.add(direction[index]);
			}
		}
		return list;
	}
}
