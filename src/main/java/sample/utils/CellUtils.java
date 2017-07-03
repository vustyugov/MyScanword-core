package sample.utils;

import sample.impls.cell.ActiveCell;
import sample.impls.cell.CommentCell;
import sample.impls.cell.DisableCell;
import sample.impls.cell.SimpleCell;
import sample.interfaces.Cell;
import sample.interfaces.Scanword;

public final class CellUtils {
	protected static boolean isType(Cell cell, Class<? extends Cell> classType) {
		if (cell.getClass().getName().equals(classType.getName())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private static Cell simpleCellToActiveCell(Cell cell) {
		if (cell instanceof SimpleCell) {
			Cell result = new ActiveCell();
			result.setLetter(cell.getLetter());
			result.setFirstLink(cell.getFirstLink());
			result.setSecondLink(cell.getSecondLink());
			return result;
		}
		else {
			return cell;
		}
	}
	
	public static void defineActiveCell(Scanword scanword) {
		Cell[][] array = scanword.getArray();
		for(int row = 0; row < array.length; row++) {
			for(int column = 0; column < array[0].length; column++) {
				if (column == 0) {
					if (isType(array[row][column], SimpleCell.class) 
							&& (isType(array[row][column+1], SimpleCell.class)
									|| isType(array[row][column+1], ActiveCell.class))) {
						scanword.setArrayElement(simpleCellToActiveCell(array[row][column]), row, column);
					}
				}
				if (row == 0) {
					if (isType(array[row][column], SimpleCell.class)
							&& (isType(array[row+1][column], SimpleCell.class)
									|| isType(array[row+1][column], ActiveCell.class))) {
						scanword.setArrayElement(simpleCellToActiveCell(array[row][column]), row, column);
					}
				}
				if (column > 0 && column < array[0].length-1) {
					if (isType(array[row][column], SimpleCell.class)
							&& (isType(array[row][column-1], CommentCell.class)
									|| isType(array[row][column-1], DisableCell.class))
							&& (isType(array[row][column+1], SimpleCell.class)
									|| isType(array[row][column+1], ActiveCell.class))) {
						scanword.setArrayElement(simpleCellToActiveCell(array[row][column]), row, column);
					}
				}
				if (row > 0 && row < array.length-1) {
					if (isType(array[row][column], SimpleCell.class)
							&& (isType(array[row-1][column],CommentCell.class)
									|| isType(array[row-1][column], DisableCell.class))
							&& (isType(array[row+1][column], SimpleCell.class)
									|| isType(array[row+1][column], ActiveCell.class))) {
						scanword.setArrayElement(simpleCellToActiveCell(array[row][column]), row, column);
					}
				}
			}
		}
	}

	public static void defineInitLinksInActiveCell(Cell[][] array) {
		for (int row = 0; row < array.length; row++) {
			for(int column = 0; column < array[0].length-1; column++) {
				if ((isType(array[row][column], SimpleCell.class)
						|| isType(array[row][column], ActiveCell.class))
						&& isType(array[row][column+1], ActiveCell.class)) {
					array[row][column+1].setFirstLink("9.9");
				}
				else if (isType(array[row][column], ActiveCell.class)
						&& (isType(array[row][column+1], CommentCell.class)
								|| isType(array[row][column+1], DisableCell.class))) {
					array[row][column].setFirstLink("9.9");
				}
			}
		}
		for (int row = 0; row < array.length; row++) {
			if ( isType(array[row][array[0].length-1], ActiveCell.class)
					&& (isType(array[row][array[0].length-2], CommentCell.class)
							|| isType(array[row][array[0].length-2], DisableCell.class))) {
				array[row][array[0].length-1].setFirstLink("9.9");
			}
		}
		for (int row = 0; row < array.length-1; row++) {
			for(int column = 0; column < array[0].length; column++) {
				if ((isType(array[row][column], SimpleCell.class)
						|| isType(array[row][column], ActiveCell.class))
						&& isType(array[row+1][column], ActiveCell.class)) {
					array[row+1][column].setSecondLink("9.9");
				}
				else if (isType(array[row][column], ActiveCell.class)
						&& (isType(array[row+1][column], CommentCell.class)
								|| isType(array[row+1][column], DisableCell.class))) {
					array[row][column].setSecondLink("9.9");
				}
			}
		}
		for (int column = 0; column < array[0].length; column++) {
			if ( isType(array[array.length-1][column], ActiveCell.class)
					&& (isType(array[array.length-2][column], CommentCell.class)
							|| isType(array[array.length-2][column], DisableCell.class))) {
				array[array.length-1][column].setSecondLink("9.9");
			}
		}
	}

	protected static boolean isActiveCellWithFreeLinks(Scanword scanword) {
		return (getActiveCellWithFreeLink(scanword.getArray()) != null) ? true : false;
	}

	protected static Cell getActiveCellWithFreeLink(Cell[][] cells) {
		for (int row = 0; row < cells.length; row++) {
			for (int column = 0; column < cells[0].length; column++) {
				if (cells[row][column] instanceof ActiveCell 
						&& cells[row][column].getCountFreeLink()>0) {
					return cells[row][column];
				}
			}
		} 
		return null;
	}
}
