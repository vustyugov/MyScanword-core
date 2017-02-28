package sample.impls;

import java.util.*;

import sample.impls.cell.*;
import sample.interfaces.*;

public final class Util {
	private static Map<String, Integer[][]> positions;
	
	static {
		positions = new HashMap<String, Integer[][]> (9);
    	Integer[][] central = {{0, 1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
    	positions.put("central", central);
    	Integer[][] left = {{0, 1},{1,1},{1,0},{-1,0},{-1,1}};
    	positions.put("left", left);
    	Integer[][] right = {{1,0},{1,-1},{0,-1},{-1,-1},{-1,0}};
    	positions.put("right", right);
    	Integer[][] bottom = {{0, 1},{0,-1},{-1,-1},{-1,0},{-1,1}};
    	positions.put("bottom", bottom);
    	Integer[][] top = {{0, 1},{1,1},{1,0},{1,-1},{0,-1}};
    	positions.put("top", top);
    	Integer[][] rightTop = {{1, 0},{1,-1},{0,-1}};
    	positions.put("rightTop", rightTop);
    	Integer[][] rightBottom = {{0, -1},{-1,-1},{-1,0}};
    	positions.put("rightBottom", rightBottom);
    	Integer[][] leftTop = {{0,1},{1,1},{1,0}};
    	positions.put("leftTop", leftTop);
    	Integer[][] leftBottom = {{0,1},{-1,0},{-1,1}};
    	positions.put("leftBottom",leftBottom);
	}

	public static int getCountFreeLinksInOneTypeCells(Scanword scanword, Class<? extends Cell> myClass) {
		int count = 0;
		for (int row = 0; row < scanword.getRow(); row++) {
			for (int column = 0; column < scanword.getColumns(); column++) {
				if (scanword.getArrayElement(row, column).getClass().getName()
						.equals(myClass.getName())) {
					count += scanword.getArrayElement(row, column).getCountFreeLink();
				}
			}
		}
		return count;
	}
	
	private static List<Integer[]> getCountNeighbourCellsOtherType(Scanword scanword, int row, int column, 
			Class<? extends Cell> currentCell, Class<? extends Cell> neighbourCell, String position) {
		List<Integer[]> list = new LinkedList<Integer[]> ();
		Integer[][] indexes = positions.get(position);
		int size = positions.get(position).length;
		
		for (int index = 0; index < size; index++) {
			if (scanword.getArrayElement(row+indexes[index][0], column+indexes[index][1])
					.getClass().getName().equals(neighbourCell.getName())) {
				list.add(indexes[index]);
			}
		}
		return list;
	}
	
	private static Cell getChangedCell(Scanword scanword, int row, 
			int column,	Cell currentCell, Cell neighboureCell, String position, String direction) {
		Cell cell = null;
		if ( getCountNeighbourCellsOtherType(scanword, row, column, ActiveCell.class, CommentCell.class, position).size()>0
				|| getCountNeighbourCellsOtherType(scanword, row, column, SimpleCell.class, CommentCell.class, position).size()>0) {
			if  (neighboureCell.getClass().getName().equals(ActiveCell.class.getName())
					|| neighboureCell.getClass().getName().equals(SimpleCell.class.getName())) {
				cell = new ActiveCell();
				cell.setLetter(currentCell.getLetter());
				cell.setFirstLink(currentCell.getFirstLink());
				cell.setSecondLink(currentCell.getSecondLink());
				if (direction.equals("horizontal")) {
					((ActiveCell)cell).setHDirection(true);
					if (currentCell.getClass().getName().equals(ActiveCell.class.getName())) {
						((ActiveCell)cell).setVDirection(((ActiveCell)currentCell).getVDirection());
					}
				}
				else if (direction.equals("vertical")) {
					((ActiveCell)cell).setVDirection(true);
					if (currentCell.getClass().getName().equals(ActiveCell.class.getName())) {
						((ActiveCell)cell).setHDirection(((ActiveCell)currentCell).getHDirection());
					}
				}
			}
			else {
				cell = currentCell;
			}
		}
		return cell;
	}
	
	public static boolean changeSimpleCellsToActiveCells (Scanword scanword) {
		if (getCountFreeLinksInOneTypeCells(scanword, CommentCell.class) == 0) {
			return false;
		}
		else {
			for (int row = 0; row < scanword.getRow(); row++) {
				for (int column = 0; column < scanword.getColumns();column++) {
					if (scanword.getArrayElement(row, column).getClass().getName().equals(CommentCell.class.getName())
							|| scanword.getArrayElement(row, column).getClass().getName().equals(DisableCell.class.getName())) {
						continue;
					}
					if (row == 0) {
						if (column == 0 ) {
							scanword.setArrayElement(getChangedCell(scanword, row, column, scanword.getArrayElement(row, column), scanword.getArrayElement(row+1, column), "leftTop", "vertical"), row, column);
							scanword.setArrayElement(getChangedCell(scanword, row, column, scanword.getArrayElement(row, column), scanword.getArrayElement(row, column+1), "leftTop", "horizontal"), row, column);
						}
						else if (column == scanword.getColumns()-1 ) {
							scanword.setArrayElement(getChangedCell(scanword, row, column, scanword.getArrayElement(row, column), scanword.getArrayElement(row+1, column), "rightTop", "vertical"), row, column);
						}
						else {
							scanword.setArrayElement(getChangedCell(scanword, row, column, scanword.getArrayElement(row, column), scanword.getArrayElement(row+1, column), "top", "vertical"), row, column);
						}
					}
					else if (row == scanword.getRow()-1 && column == 0) {
						scanword.setArrayElement(getChangedCell(scanword, row, column, scanword.getArrayElement(row, column), scanword.getArrayElement(row, column+1), "leftBottom", "horizontal"), row, column);
					}
					else if (row > 0 && row < scanword.getRow()-1 && column == 0) {
						scanword.setArrayElement(getChangedCell(scanword, row, column, scanword.getArrayElement(row, column), scanword.getArrayElement(row, column+1), "left", "horizontal"), row, column);
					}
				}
			}
					
			
			for (int row = 1; row < scanword.getRow()-1;row++) {
				for (int column = 0; column < scanword.getColumns(); column++) {
					Cell upperCell = scanword.getArrayElement(row-1, column);
					Cell centralCell = scanword.getArrayElement(row, column);
					Cell bottomCell = scanword.getArrayElement(row+1, column); 
					if (upperCell.getClass().getName().equals(CommentCell.class.getName())
							&& (centralCell.getClass().getName().equals(SimpleCell.class.getName())
									|| centralCell.getClass().getName().equals(ActiveCell.class.getName()))
							&& (bottomCell.getClass().getName().equals(SimpleCell.class.getName())
									|| bottomCell.getClass().getName().equals(ActiveCell.class.getName()))) {
						Cell cell = new ActiveCell();
						cell.setLetter(centralCell.getLetter());
						cell.setFirstLink(centralCell.getFirstLink());
						cell.setSecondLink(centralCell.getSecondLink());
						((ActiveCell)cell).setVDirection(true);
						if (centralCell.getClass().getName().equals(ActiveCell.class.getName())) {
							((ActiveCell)cell).setHDirection(((ActiveCell)centralCell).getHDirection());
						}
						cell.setFirstLink("0.0");
						scanword.setArrayElement(cell, row, column);
					}
				}
			}

			for (int row = 0; row < scanword.getRow(); row++) {
				for (int column = 1; column < scanword.getColumns()-1; column++) {
					Cell leftCell = scanword.getArrayElement(row, column-1);
					Cell centralCell = scanword.getArrayElement(row, column);
					Cell rightCell = scanword.getArrayElement(row, column+1);
					if (leftCell.getClass().getName().equals(CommentCell.class.getName())
							&& (centralCell.getClass().getName().equals(SimpleCell.class.getName())
									|| centralCell.getClass().getName().equals(ActiveCell.class.getName()))
							&& (rightCell.getClass().getName().equals(SimpleCell.class.getName())
									|| rightCell.getClass().getName().equals(ActiveCell.class.getName()))) {
						Cell cell = new ActiveCell();
						cell.setLetter(centralCell.getLetter());
						cell.setFirstLink(centralCell.getFirstLink());
						cell.setSecondLink(centralCell.getSecondLink());
						((ActiveCell)cell).setHDirection(true);
						if (centralCell.getClass().getName().equals(ActiveCell.class.getName())) {
							((ActiveCell)cell).setVDirection(((ActiveCell)centralCell).getVDirection());
						}
						cell.setSecondLink("0.0");
						scanword.setArrayElement(cell, row, column);
					}
				}
			}
			for (int row = 0; row < scanword.getRow(); row++) {
				for (int column = 0; column < scanword.getColumns(); column++) {
					Cell cell = scanword.getArrayElement(row, column);
					if (cell instanceof ActiveCell) {
						if (!((ActiveCell)cell).getHDirection()) {
							cell.setFirstLink("9.9");
						}
						if (!((ActiveCell)cell).getVDirection()) {
							cell.setSecondLink("9.9");
						}
					}
				}
			}
			return true;
		}
	}
	
	private static boolean equalsSubArrayToPattern(Scanword scanword, int rowPosition, int columnPosition, Pattern pattern) {
		boolean flag = true;
		for (int row = 0; row < pattern.getArray().length; row++) {
			for (int column = 0; column < pattern.getArray()[0].length; column++) {
				Cell sCell = scanword.getArrayElement(row+rowPosition, column+columnPosition);
				Cell pCell = pattern.getArray()[row][column];
				if (sCell.getClass().getName()
						.equals(pCell.getClass().getName())) {
					flag &= true;
				}
				else {
					flag &= false;
				}
			}
		}
		return flag; 
	}
	
	private static boolean equalsCellWithOneFreeLinks (Scanword scanword, int row, int column, Pattern pattern) {
		if ((row+pattern.getArray().length)>scanword.getRow()
				|| (column+pattern.getArray()[0].length)>scanword.getColumns()) {
			return false;
		}
		if (pattern.indexesCellWithOneFreeLinks().size() == 0) {
			return true;
		}
		else {
			boolean lable = equalsSubArrayToPattern(scanword, row, column, pattern);
			if (lable) {
				boolean flag = true;
				for (int index = 0; index < pattern.indexesCellWithOneFreeLinks().size(); index++) {
					if (scanword.getArrayElement(row + pattern.indexesCellWithOneFreeLinks().get(index)[0], 
													column + pattern.indexesCellWithOneFreeLinks().get(index)[1]).getCountFreeLink()==1) {
						flag &= true;
					}
					else {
						flag = false;
					}
				}
				return flag;
			}
			else {
				return false;
			}
		}
	}
	
	private static boolean equalsCellWithoutFreeLink (Scanword scanword, int row, int column, Pattern pattern) {
		if ((row+pattern.getArray().length)>scanword.getRow()
				|| (column+pattern.getArray()[0].length)>scanword.getColumns()) {
			return false;
		}
		if (pattern.indexesCellWithoutFreeLinks().size() == 0) {
			return true;
		}

		else {
			boolean lable = equalsSubArrayToPattern(scanword, row, column, pattern);
			if (lable) {
				boolean flag = true;				
				for (int index = 0; index < pattern.indexesCellWithoutFreeLinks().size(); index++) {
					if (scanword.getArrayElement(row + pattern.indexesCellWithoutFreeLinks().get(index)[0], 
													column + pattern.indexesCellWithoutFreeLinks().get(index)[1]).getCountFreeLink() == 0) {
						flag &= true;
					}
					else {
						flag = false;
					}
				}
				return flag;
			}
			else {
				return false;
			}
		}
	}
	
	private static boolean equalsCellWithTwoFreeLink (Scanword scanword, int row, int column, Pattern pattern) {
		if ((row+pattern.getArray().length)>scanword.getRow()
				|| (column+pattern.getArray()[0].length)>scanword.getColumns()) {
			return false;
		}
		if (pattern.indexesCellWithTwoFreeLinks().size() == 0) {
			return true;
		}

		else {
			boolean lable = equalsSubArrayToPattern(scanword, row, column, pattern);
			if (lable) {
				boolean flag = true;				
				for (int index = 0; index < pattern.indexesCellWithTwoFreeLinks().size(); index++) {
					if (scanword.getArrayElement(row + pattern.indexesCellWithTwoFreeLinks().get(index)[0], 
													column + pattern.indexesCellWithTwoFreeLinks().get(index)[1]).getCountFreeLink() == 2) {
						flag &= true;
					}
					else {
						flag = false;
					}
				}
				return flag;
			}
			else {
				return false;
			}
		}
	}
	
	public static boolean createArrawsInActiveAndCommentCells (Scanword scanword, Pattern pattern) {
/*		if (getCountFreeLinksInOneTypeCells(scanword, ActiveCell.class)==0) {
			System.out.println("Can't create, because ActiveCells does not exists.");
			return false;
		}
		else {
			for (int row = 0; row < scanword.getRow(); row++) {
				for (int column = 0; column < scanword.getColumns(); column++) {
					if (equalsCellWithoutFreeLink(scanword, row, column, pattern)
							&& equalsCellWithOneFreeLinks(scanword, row, column, pattern)
							&& equalsCellWithTwoFreeLink(scanword, row, column, pattern)) {
						if (pattern.getPosition() == Pattern.Position.CENTRAL
								&& row > 0 && row <= scanword.getRow()-pattern.getArray().length
								&& column > 0 && column <= scanword.getColumns()-pattern.getArray()[0].length) {
							setSubArray(scanword, pattern, row, column);
						}
						else if (pattern.getPosition() == Pattern.Position.LEFT_TOP_ANGLE
								&& row == 0 && column == 0) {
							setSubArray(scanword, pattern, row, column);
						}
						else if (pattern.getPosition() == Pattern.Position.RIGHT_TOP_ANGLE
								&& column == scanword.getColumns()-pattern.getArray()[0].length
								&& row == 0) {
							setSubArray(scanword, pattern, row, column);
						}
						else if (pattern.getPosition() == Pattern.Position.RIGHT_BOUNDARY
								&& row > 0 && row < scanword.getRow()-pattern.getArray().length
								&& column == scanword.getColumns()-pattern.getArray()[0].length) {
							setSubArray(scanword, pattern, row, column);
						}
						else if (pattern.getPosition() == Pattern.Position.BOTTOM_BOUNDARY
								&& column > 0 && column < scanword.getColumns()-pattern.getArray()[0].length
								&& row == scanword.getRow()-pattern.getArray().length) {
							setSubArray(scanword, pattern, row, column);
						}
						else if (pattern.getPosition() == Pattern.Position.LEFT_BOUNDARY
								&& row > 0 && row < scanword.getRow()-pattern.getArray().length
								&& column == 0) {
							setSubArray(scanword, pattern, row, column);
						}
						else if (pattern.getPosition() == Pattern.Position.TOP_BOUNDARY
								&& column > 0 && column < scanword.getColumns()-pattern.getArray()[0].length
								&& row == 0) {
							setSubArray(scanword, pattern, row, column);
						}
						else if (pattern.getPosition() == Pattern.Position.LEFT_BOTTOM_ANGLE
								&& row == scanword.getRow()-pattern.getArray().length
								&& column == 0) {
							setSubArray(scanword, pattern, row, column);
						}
						else if (pattern.getPosition() == Pattern.Position.RIGHT_BOTTOM_ANGLE
								&& row == scanword.getRow()-pattern.getArray().length
								&& column == scanword.getColumns()-pattern.getArray()[0].length) {
							setSubArray(scanword, pattern, row, column);
						}
					}
				}
			}
			return true;
		}
*/
		initialStep(scanword);
	return true;
	}
	
	private static void initialStep(Scanword scanword) {
		setLinks(scanword, 0, scanword.getRow()-1, 0, scanword.getColumns(), 1, 0, ActiveCell.class, 2, "7.3", 0, 0, CommentCell.class, 0, "3.3.3");
		setLinks(scanword, 0, scanword.getRow(), 0, scanword.getColumns()-1, 0, 1, ActiveCell.class, 1, "5.1", 0, 0, CommentCell.class, 0, "1.1.1");
		setLinks(scanword, 0, scanword.getRow()-1, 0, 1, 1, 0, ActiveCell.class, 1, "7.1", 0, 0, CommentCell.class, 0, "3.3.1");
		setLinks(scanword, 0, scanword.getRow()-1, 0, 1, 0, 0, ActiveCell.class, 1, "3.1", 1, 0, CommentCell.class, 0, "7.7.1");
		setLinks(scanword, 0, scanword.getRow()-1, 0, 1, 1, 0, ActiveCell.class, 1, "8.1", 0, 1, CommentCell.class, 0, "5.4.1");
		setLinks(scanword, 0, scanword.getRow()-1, 0, 1, 0, 0, ActiveCell.class, 1, "2.1", 1, 1, CommentCell.class, 0, "5.6.1");
		setLinks(scanword, 0, 1, 0, scanword.getColumns()-1, 0, 1, ActiveCell.class, 2, "5.3", 0, 0, CommentCell.class, 0, "1.1.3");
		setLinks(scanword, 0, 1, 0, scanword.getColumns()-1, 0, 0, ActiveCell.class, 2, "1.3", 0, 1, CommentCell.class, 0, "5.5.3");
		setLinks(scanword, 0, 1, 0, scanword.getColumns()-1, 0, 1, ActiveCell.class, 2, "4.3", 1, 0, CommentCell.class, 0, "7.8.3");
		setLinks(scanword, 0, 1, 0, scanword.getColumns()-1, 0, 0, ActiveCell.class, 2, "2.3", 1, 1, CommentCell.class, 0, "7.6.3");
		
		setAvailableCells(scanword, 1, scanword.getRow()-1, 1, scanword.getColumns()-1, "central");
		setAvailableCells(scanword, 1, scanword.getRow()-1, scanword.getColumns()-1, scanword.getColumns(), "right");
		setAvailableCells(scanword, scanword.getRow()-1, scanword.getRow(), 1, scanword.getColumns()-1, "bottom");
		setAvailableCells(scanword, 1, scanword.getRow()-1, 0, 1, "left");
		setAvailableCells(scanword, 0, 1, 1, scanword.getColumns()-1, "top");
		setAvailableCells(scanword, 0, 1, 0, 1, "leftTop");
		setAvailableCells(scanword, 0, 1, scanword.getColumns()-1, scanword.getColumns(), "rightTop");
		setAvailableCells(scanword, scanword.getRow()-1, scanword.getRow(), 0, 1, "leftBottom");
		setAvailableCells(scanword, scanword.getRow()-1, scanword.getRow(), scanword.getColumns()-1, scanword.getColumns(), "rightBottom");
	}
	
	private static void setAvailableCells(Scanword scanword, int startRow, int endRow, int startColumn, int endColumn, String position) {
		for (int row = startRow; row < endRow;row++) {
			for (int column = startColumn; column < endColumn; column++) {
				Cell currentCell = scanword.getArrayElement(row, column);
				List<Integer[]> neighboureCells = getCountNeighbourCellsOtherType(scanword, row, column, CommentCell.class, ActiveCell.class, position);
				if (neighboureCells.size() == 1) {
					Cell cell = scanword.getArrayElement(row+neighboureCells.get(0)[0], column + neighboureCells.get(0)[1]);
					if( cell.getCountAvailableLink() == 1) {
						if (!currentCell.getFirstLink().equals("0.0.0")
								&& !currentCell.getFirstLink().equals("9.9.9")) {
							currentCell.setAvailableFirstLink(false);
							currentCell.setSecondLink("9.9.9");
						}
						else if (!currentCell.getSecondLink().equals("0.0.0")
								&& !currentCell.getSecondLink().equals("9.9.9")) {
							currentCell.setAvailableSecondLink(false);
							currentCell.setFirstLink("9.9.9");
						}
					cell.setAvailableFirstLink(false);
					cell.setAvailableSecondLink(false);
					}
				}
			}
		}
	}
	
 	private static void setLinks(Scanword scanword, int startRow, int endRow, 
			int startColumn, int endColumn, int startPositionFirstCellRow, 
			int startPositionFirstCellColumn, Class<? extends Cell> firstCellType,
			int firstIndexDirection, String firstCellPattern,
			int startPositionSecondCellRow, int startPositionSecondCellColumn, 
			Class<? extends Cell> secondCellType, int secondIndexDirection,
			String secondCellPattern) {
		for (int row = startRow; row < endRow; row++) {
			for (int column = startColumn; column < endColumn; column++) {
				boolean flag = false;
				Cell fCell = scanword.getArrayElement(row+startPositionFirstCellRow, column+startPositionFirstCellColumn);
				Cell sCell = scanword.getArrayElement(row+startPositionSecondCellRow, column+ startPositionSecondCellColumn);
				if (isType(fCell, ActiveCell.class) & isType(sCell, CommentCell.class)
						&& firstCellPattern.length() == 3 && secondCellPattern.length() == 5) {
					if (firstCellType.getName().equals(ActiveCell.class.getName())
							&& secondCellType.getName().equals(CommentCell.class.getName())) {
						if (firstIndexDirection == 1 && ((ActiveCell)fCell).getHDirection()) {
							flag = true;
						}
						if (firstIndexDirection == 2 && ((ActiveCell)fCell).getVDirection()) {
							flag = true;
						}
					}
				}
				else if (isType(fCell, CommentCell.class) & isType(sCell, ActiveCell.class)
						&& firstCellPattern.length() == 5 && secondCellPattern.length() == 3) {
					if (firstCellType.getName().equals(CommentCell.class.getName()) 
							&& secondCellType.getName().equals(ActiveCell.class.getName())) {
						if (secondIndexDirection == 1 && ((ActiveCell)sCell).getHDirection()) {
							flag = true;
						}
						if (secondIndexDirection == 2 && ((ActiveCell)sCell).getVDirection()) {
							flag = true;
						}
					}
				}
				if (flag) {
					if (fCell.setLink(firstCellPattern)
							&&sCell.setLink(secondCellPattern)) {
						
					}
				}
			}
		}
	}
	
	private static boolean isType(Cell cell, Class<? extends Cell> type) {
		if (cell.getClass().getName().equals(type.getName())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private static void setSubArray(Scanword scanword, Pattern pattern, int row, int column) {
		boolean label = equalsSubArrayToPattern(scanword, row, column, pattern);
		if (label) {
			for (int pRow = 0; pRow < pattern.getArray().length; pRow++) {
				for (int pColumn = 0; pColumn < pattern.getArray()[0].length; pColumn++) {
					Cell cell = scanword.getArrayElement(row+pRow, column+pColumn);
					if (cell.getFirstLink().equals("0.0")||cell.getFirstLink().equals("0.0.0")) {
						cell.setFirstLink(pattern.getArray()[pRow][pColumn].getFirstLink());
					}
					if (cell.getSecondLink().equals("0.0.0") || cell.getSecondLink().equals("0.0")) {
						cell.setSecondLink(pattern.getArray()[pRow][pColumn].getSecondLink());
					}
					scanword.setArrayElement(cell, row+pRow, column+pColumn);
				}
			}
		}
	}
	
	public static Map<String, List<String>> getWords(Scanword scanword) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		
		return null;
	}

	public static void changeLinkFromFreeToBusyValue(Scanword scanword) {
		
	}
}