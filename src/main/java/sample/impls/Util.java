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

	private static int getCountFreeLinksInOneTypeCells(Scanword scanword, Class<? extends Cell> myClass) {
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
	
	public static boolean changeSimpleCellsToActiveCells (Scanword scanword) {
		if (getCountFreeLinksInOneTypeCells(scanword, CommentCell.class) == 0) {
			return false;
		}
		else {
			for (int row = 0; row < scanword.getRow(); row++) {
				for (int column = 0; column < scanword.getColumns();column++) {
					Cell cell = null;
					Cell afterCell = null;
					if (row == 0 ) {
						cell = scanword.getArrayElement(row, column);
						afterCell = scanword.getArrayElement(row + 1, column);
					}
					if (column == 0) {
						cell = scanword.getArrayElement(row, column);
						afterCell = scanword.getArrayElement(row, column + 1);
					}
					if (row != 0 && column != 0) {
						continue;
					}
					
					if ((cell.getClass().getName().equals(ActiveCell.class.getName())
							|| cell.getClass().getName().equals(SimpleCell.class.getName()))
						&& (afterCell.getClass().getName().equals(ActiveCell.class.getName())
								|| afterCell.getClass().getName().equals(SimpleCell.class.getName()))) {
						List<Integer[]> count = null;
						String position = "";
						if (column == 0 && row == 0) {
							position = "leftTop";
						}
						else if (column == scanword.getColumns()-1 && row == 0) {
							position = "rightTop";
						}
						else if (row == 0 && column < scanword.getColumns()-1 && column > 0){
							position = "top";
						}
						else if (row == scanword.getRow()-1 && column == 0) {
							position = "leftBottom";
						}
						else if (column == 0 && row > 0 && row < scanword.getRow()-1) {
							position = "left";
						}
						count = getCountNeighbourCellsOtherType(scanword, row, column, cell.getClass(), CommentCell.class, position); 
						if (count.size() > 0) {
							Cell newCell = new ActiveCell();
							newCell.setLetter(cell.getLetter());
							newCell.setFirstLink(cell.getFirstLink());
							newCell.setSecondLink(cell.getSecondLink());
							if (cell.getClass().getName().equals(ActiveCell.class.getName())) {
								((ActiveCell)newCell).setHDirection(((ActiveCell)cell).getHDirection());
								((ActiveCell)newCell).setVDirection(((ActiveCell)cell).getVDirection());
							}
							if (row == 0) {
								((ActiveCell)newCell).setHDirection(true);
								newCell.setFirstLink("0.0");
							}
							if (column == 0) {
								((ActiveCell)newCell).setVDirection(true);
								newCell.setSecondLink("0.0");
							}
							scanword.setArrayElement(newCell, row, column);
						}
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
						((ActiveCell)cell).setVDirection(true);
						if (centralCell.getClass().getName().equals(ActiveCell.class.getName())) {
							((ActiveCell)cell).setHDirection(((ActiveCell)centralCell).getHDirection());
						}
						cell.setSecondLink("0.0");
						scanword.setArrayElement(cell, row, column);
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
				if (scanword.getArrayElement(row+rowPosition, column+columnPosition).getClass().getName()
						.equals(pattern.getArray()[row][column].getClass().getName())) {
					flag &= true;
				}
				else {
					flag &= false;
				}
			}
		}
		return flag; 
	}
	
	public static boolean createArrawsInActiveAndCommentCells (Scanword scanword, Pattern pattern) {
		if (getCountFreeLinksInOneTypeCells(scanword, ActiveCell.class)==0) {
			System.out.println("Can't create, because ActiveCells does not exists.");
			return false;
		}
		else {
			for (int row = 0; row < scanword.getRow()-pattern.getArray().length+1; row++) {
				for (int column = 0; column < scanword.getColumns()-pattern.getArray()[0].length+1; column++) {
					if (pattern.getPosition() == Pattern.Position.CENTRAL
							&& row > 0 && row < scanword.getRow()-1
							&& column > 0 && column < scanword.getColumns()-1) {
						boolean label = equalsSubArrayToPattern(scanword, row, column, pattern);
						if (label) {
							for (int pRow = 0; pRow < pattern.getArray().length; pRow++) {
								for (int pColumn = 0; pColumn < pattern.getArray()[0].length; pColumn++) {
									Cell cell = scanword.getArrayElement(row+pRow, column+pColumn);
									cell.setFirstLink(pattern.getArray()[pRow][pColumn].getFirstLink());
									cell.setSecondLink(pattern.getArray()[pRow][pColumn].getSecondLink());
									scanword.setArrayElement(cell, row+pRow, column+pColumn);
								}
							}
						}
					}
					else if (pattern.getPosition() == Pattern.Position.LEFT_TOP_ANGLE
								&& row == 0 && column == 0) {
						boolean label = equalsSubArrayToPattern(scanword, row, column, pattern);
						if (label) {
							for (int pRow = 0; pRow < pattern.getArray().length; pRow++) {
								for (int pColumn = 0; pColumn < pattern.getArray()[0].length; pColumn++) {
									Cell cell = scanword.getArrayElement(row+pRow, column+pColumn);
									cell.setFirstLink(pattern.getArray()[pRow][pColumn].getFirstLink());
									cell.setSecondLink(pattern.getArray()[pRow][pColumn].getSecondLink());
									scanword.setArrayElement(cell, row+pRow, column+pColumn);
								}
							}
						}
					}
					else if (pattern.getPosition() == Pattern.Position.RIGHT_TOP_ANGLE
								&& column == scanword.getColumns()-pattern.getArray()[0].length
								&& row == 0) {
						boolean label = equalsSubArrayToPattern(scanword, row, column, pattern);
						if (label) {
							for (int pRow = 0; pRow < pattern.getArray().length; pRow++) {
								for (int pColumn = 0; pColumn < pattern.getArray()[0].length; pColumn++) {
									Cell cell = scanword.getArrayElement(row+pRow, column+pColumn);
									cell.setFirstLink(pattern.getArray()[pRow][pColumn].getFirstLink());
									cell.setSecondLink(pattern.getArray()[pRow][pColumn].getSecondLink());
									scanword.setArrayElement(cell, row+pRow, column+pColumn);
								}
							}
						}
					}
					else if (pattern.getPosition() == Pattern.Position.RIGHT_BOUNDARY
								&& row > 0 && row < scanword.getRow()-1
								&& column == scanword.getColumns()-pattern.getArray()[0].length) {
						boolean label = equalsSubArrayToPattern(scanword, row, column, pattern);
						if (label) {
							for (int pRow = 0; pRow < pattern.getArray().length; pRow++) {
								for (int pColumn = 0; pColumn < pattern.getArray()[0].length; pColumn++) {
									Cell cell = scanword.getArrayElement(row+pRow, column+pColumn);
									cell.setFirstLink(pattern.getArray()[pRow][pColumn].getFirstLink());
									cell.setSecondLink(pattern.getArray()[pRow][pColumn].getSecondLink());
									scanword.setArrayElement(cell, row+pRow, column+pColumn);
								}
							}
						}
						
					}
				}
			}
			return true;
		}
	}
	
	public static Map<String, List<String>> getWords(Scanword scanword) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		
		return null;
	}
}