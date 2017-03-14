package sample.impls;

import java.util.*;

import sample.impls.cell.*;
import sample.impls.tree.*;
import sample.interfaces.*;

public final class Util {
	
	public static int CELL_WITHOUT_LINK = 0;
	public static int CELL_WITH_ONE_LINK = 1;
	public static int CELL_WITH_TWO_LINK = 2;
	
	static class LinkUtil {
		
		public static void createLinkBetweenTwoCells(Cell fCell, int fRowIndex, int fColumnIndex, Cell sCell, int sRowIndex, int sColumnIndex) {
	 		int directionCurrentCell = SimpleProcessingWithCellsUtil.transformateDiffCoordinateToDirectionIndex(fRowIndex-sRowIndex, fColumnIndex-sColumnIndex);
	 		int directionNeighbourCell = SimpleProcessingWithCellsUtil.transformateDiffCoordinateToDirectionIndex(sRowIndex-fRowIndex, sColumnIndex-fColumnIndex);

	 		if (directionCurrentCell == -1 && directionNeighbourCell == -1) {
	 			return ;
	 		}
	 		
			if (fCell instanceof CommentCell && sCell instanceof ActiveCell) {
				
			}
		}
		
	 	public static boolean isLinkBetweenTwoCells(Cell fCell, int fRowIndex, int fColumnIndex, Cell sCell, int sRowIndex, int sColumnIndex) {
	 		int directionCurrentCell = SimpleProcessingWithCellsUtil.transformateDiffCoordinateToDirectionIndex(fRowIndex-sRowIndex, fColumnIndex-sColumnIndex);
	 		int directionNeighbourCell = SimpleProcessingWithCellsUtil.transformateDiffCoordinateToDirectionIndex(sRowIndex-fRowIndex, sColumnIndex-fColumnIndex);

	 		if (directionCurrentCell == -1 || directionNeighbourCell == -1) {
	 			return false;
	 		}
	 		
	 		if (fCell instanceof ActiveCell & sCell instanceof CommentCell) {
				if (fCell.getFirstLink().substring(0, 1).equals(String.valueOf(directionCurrentCell))
						&& sCell.getFirstLink().substring(2, 3).equals(String.valueOf(directionNeighbourCell))) {
					return true;
				}
				else if (fCell.getFirstLink().substring(0, 1).equals(String.valueOf(directionCurrentCell))
						&& sCell.getSecondLink().substring(2, 3).equals(String.valueOf(directionNeighbourCell))) {
					return true;
				}
				else if (fCell.getSecondLink().substring(0, 1).equals(String.valueOf(directionCurrentCell))
						&& sCell.getFirstLink().substring(2, 3).equals(String.valueOf(directionNeighbourCell))) {
					return true;
				}
				else if (fCell.getSecondLink().substring(0, 1).equals(String.valueOf(directionCurrentCell))
						&& sCell.getSecondLink().substring(2, 3).equals(String.valueOf(directionNeighbourCell))) {
					return true;
				}
			}
			if (fCell instanceof CommentCell & sCell instanceof ActiveCell) {
				if (sCell.getFirstLink().substring(0, 1).equals(String.valueOf(directionCurrentCell))
						&& fCell.getFirstLink().substring(2, 3).equals(String.valueOf(directionNeighbourCell))) {
					return true;
				}
				else if (sCell.getFirstLink().substring(0, 1).equals(String.valueOf(directionCurrentCell))
						&& fCell.getSecondLink().substring(2, 3).equals(String.valueOf(directionNeighbourCell))) {
					return true;
				}
				else if (sCell.getSecondLink().substring(0, 1).equals(String.valueOf(directionCurrentCell))
						&& fCell.getFirstLink().substring(2, 3).equals(String.valueOf(directionNeighbourCell))) {
					return true;
				}
				else if (sCell.getSecondLink().substring(0, 1).equals(String.valueOf(directionCurrentCell))
						&& fCell.getSecondLink().substring(2, 3).equals(String.valueOf(directionNeighbourCell))) {
					return true;
				}
			}
			return false;
		}
		
		public static void brakeLinkBetweenTwoCells(Cell fCell, int fRowIndex, int fColumnIndex, Cell sCell, int sRowIndex, int sColumnIndex) {
			int directionCurrentCell = SimpleProcessingWithCellsUtil.transformateDiffCoordinateToDirectionIndex(fRowIndex-sRowIndex, fColumnIndex-sColumnIndex);
	 		int directionNeighbourCell = SimpleProcessingWithCellsUtil.transformateDiffCoordinateToDirectionIndex(sRowIndex-fRowIndex, sColumnIndex-fColumnIndex);

	 		if (fCell instanceof ActiveCell & sCell instanceof CommentCell) {
				if (fCell.getFirstLink().substring(0, 1).equals(String.valueOf(directionCurrentCell))
						&& sCell.getFirstLink().substring(2, 3).equals(String.valueOf(directionNeighbourCell))) {
					fCell.setFirstLink("0.0");
					sCell.setFirstLink("0.0.0");
				}
				else if (fCell.getFirstLink().substring(0, 1).equals(String.valueOf(directionCurrentCell))
						&& sCell.getSecondLink().substring(2, 3).equals(String.valueOf(directionNeighbourCell))) {
					fCell.setFirstLink("0.0");
					sCell.setSecondLink("0.0.0");
				}
				else if (fCell.getSecondLink().substring(0, 1).equals(String.valueOf(directionCurrentCell))
						&& sCell.getFirstLink().substring(2, 3).equals(String.valueOf(directionNeighbourCell))) {
					fCell.setSecondLink("0.0");
					sCell.setFirstLink("0.0.0");
				}
				else if (fCell.getSecondLink().substring(0, 1).equals(String.valueOf(directionCurrentCell))
						&& sCell.getSecondLink().substring(2, 3).equals(String.valueOf(directionNeighbourCell))) {
					fCell.setSecondLink("0.0");
					sCell.setSecondLink("0.0.0");
				}
			}
			if (fCell instanceof CommentCell & sCell instanceof ActiveCell) {
				if (sCell.getFirstLink().substring(0, 1).equals(String.valueOf(directionCurrentCell))
						&& fCell.getFirstLink().substring(2, 3).equals(String.valueOf(directionNeighbourCell))) {
					sCell.setFirstLink("0.0");
					fCell.setFirstLink("0.0.0");
				}
				else if (sCell.getFirstLink().substring(0, 1).equals(String.valueOf(directionCurrentCell))
						&& fCell.getSecondLink().substring(2, 3).equals(String.valueOf(directionNeighbourCell))) {
					sCell.setFirstLink("0.0");
					fCell.setSecondLink("0.0.0");
				}
				else if (sCell.getSecondLink().substring(0, 1).equals(String.valueOf(directionCurrentCell))
						&& fCell.getFirstLink().substring(2, 3).equals(String.valueOf(directionNeighbourCell))) {
					sCell.setSecondLink("0.0");
					fCell.setFirstLink("0.0.0");
				}
				else if (sCell.getSecondLink().substring(0, 1).equals(String.valueOf(directionCurrentCell))
						&& fCell.getSecondLink().substring(2, 3).equals(String.valueOf(directionNeighbourCell))) {
					sCell.setSecondLink("0.0");
					fCell.setSecondLink("0.0.0");
				}
			}
		}
	}
	
	static class SimpleProcessingWithCellsUtil {
		
		private static Map<String, Integer[]> positions;
		
		static {
			positions = new HashMap<String, Integer[]> (9);
	    	Integer[] central = {1,2,3,4,5,6,7,8};
	    	positions.put("central", central);
	    	Integer[] left = {1,2,3,7,8};
	    	positions.put("left", left);
	    	Integer[] right = {3,4,5,6,7};
	    	positions.put("right", right);
	    	Integer[] bottom = {1,5,6,7,8};
	    	positions.put("bottom", bottom);
	    	Integer[] top = {1,2,3,4,5};
	    	positions.put("top", top);
	    	Integer[] rightTop = {3,4,5};
	    	positions.put("rightTop", rightTop);
	    	Integer[] rightBottom = {5,6,7};
	    	positions.put("rightBottom", rightBottom);
	    	Integer[] leftTop = {1,2,3};
	    	positions.put("leftTop", leftTop);
	    	Integer[] leftBottom = {1,7,8};
	    	positions.put("leftBottom",leftBottom);
		}

		private static boolean isType(Cell cell, Class<? extends Cell> type) {
			if (cell.getClass().getName().equals(type.getName())) {
				return true;
			}
			else {
				return false;
			}
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
		
		private static int[] transformateDirectionIndexToDiffCoordinateCell(int directionIndex) {
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
				int[] def = {-2,-2};
				return def;
			}
		}
				
		private static String transformatePositionCellToStringPosition (Scanword scanword, int rowIndex, int columnIndex) {
			String position = "";
			if(rowIndex > 0 && rowIndex < scanword.getRow()-1) {
				if (columnIndex > 0 && columnIndex < scanword.getColumns()-1) {
					position = "central";
				}
				else if (columnIndex == 0) {
					position = "left";
				}
				else if (columnIndex == scanword.getColumns()-1) {
					position = "right";
				}
			}
			else if (rowIndex == 0) {
				if (columnIndex > 0 && columnIndex < scanword.getColumns()-1) {
					position = "top";
				}
				else if (columnIndex == 0) {
					position = "leftTop";
				}
				else if (columnIndex == scanword.getColumns()-1) {
					position = "rightTop";
				}
			}
			else if (rowIndex == scanword.getRow()-1) {
				if (columnIndex > 0 && columnIndex < scanword.getColumns()-1) {
					position = "bottom";
				}
				else if (columnIndex == 0) {
					position = "leftBottom";
				}
				else if (columnIndex == scanword.getColumns()-1) {
					position = "rightBottom";
				}
			}
			return position;
		}
		
		private static int transformateDiffCoordinateToDirectionIndex(int dRow, int dColumn) {
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
		
		private static List<Integer> getIndexesDirectionByBusyLinked (Scanword scanword, int row, int column) {
			List<Integer> list = new LinkedList<Integer> ();
			Cell cell = scanword.getArrayElement(row, column);
			if (cell.getClass().getName().equals(ActiveCell.class.getName())) {
				if (!cell.getFirstLink().equals("0.0")
						&& !cell.getFirstLink().equals("9.9")) {
					list.add(Integer.valueOf(cell.getFirstLink().substring(0, 1)));
				}
				if (!cell.getSecondLink().equals("0.0")
						&& !cell.getSecondLink().equals("9.9")) {
					list.add(Integer.valueOf(cell.getSecondLink().substring(0,1)));
				}
			}
			else if (scanword.getArrayElement(row, column).getClass().getName().equals(CommentCell.class.getName())) {
				if (!cell.getFirstLink().equals("0.0.0")
						&& !cell.getFirstLink().equals("9.9.9")) {
					list.add(Integer.valueOf(cell.getFirstLink().substring(2,3)));
				}
				if (!cell.getSecondLink().equals("0.0.0")
						&& !cell.getSecondLink().equals("9.9.9")) {
					list.add(Integer.valueOf(cell.getSecondLink().substring(2,3)));
				}
			}
			return list;
		}
		
		private static List<Integer> getCountNeighbourCellsOtherType(Scanword scanword, int row, int column, 
				Class<? extends Cell> currentCellType, Class<? extends Cell> neighbourCellType, String position) {
			List<Integer> list = new LinkedList<Integer> ();
			Integer[] direction = positions.get(position);
					
			for (int index = 0; index < direction.length; index++) {
				int[] indexes = transformateDirectionIndexToDiffCoordinateCell(direction[index]);
				Cell currentCell = scanword.getArrayElement(row, column);
				Cell neighboureCell = scanword.getArrayElement(row+indexes[0], column+indexes[1]);
				if (currentCell.getClass().getName().equals(currentCellType.getName())
						&& neighboureCell.getClass().getName().equals(neighbourCellType.getName())) {
					list.add(direction[index]);
				}
			}
			return list;
		}
	
	}

	static class PatternScanwordUtil {
		
		private static boolean isEqualPatternAndSubArrayByLinksCell(Cell[][] scanwordArray, Cell[][] patternArray, List<int[]> indexes) {
			boolean flag = false;
			for (int[] index: indexes) {
				flag = true;
				if (scanwordArray[index[0]][index[1]].getCountFreeLink() == patternArray[index[0]][index[1]].getCountFreeLink()) {
					flag &= true;
				}
				else {
					return flag = false;
				}
			}
			return flag;
		}
		
		private static boolean isEqualPatternAndSubArray(Cell[][] array, Pattern pattern) {
			boolean flag = true;
			if (array.length != pattern.getArray().length && array[0].length != pattern.getArray()[0].length) {
				return false;
			}
			else {
				for (int row = 0; row < array.length; row++) {
					for (int column = 0; column < array[0].length; column++) {
						if (array[row][column].getClass().getName()
								.equals(pattern.getArray()[row][column].getClass().getName())) {
							flag &= true;
						}
						else {
							return false;
						}
					}
				}
				if (pattern.getIndexesCellWithoutFreeLinks().size() != 0 
						& isEqualPatternAndSubArrayByLinksCell(array, pattern.getArray(), 
								pattern.getIndexesCellWithoutFreeLinks())) {
					flag &= true;
				}
				if (pattern.getIndexesCellWithOneFreeLinks().size() != 0 
						& isEqualPatternAndSubArrayByLinksCell(array, pattern.getArray(), 
								pattern.getIndexesCellWithOneFreeLinks())) {
					flag &= true;
				}
				if (pattern.getIndexesCellWithTwoFreeLinks().size() != 0 
						& isEqualPatternAndSubArrayByLinksCell(array, pattern.getArray(), 
								pattern.getIndexesCellWithTwoFreeLinks())) {
					flag &= true;
				}
			}
			return flag;
		}
		
		private static void setSubArray(Cell[][] array, Pattern pattern) {
			if (isEqualPatternAndSubArray(array, pattern)) {
				for (int row = 0; row < pattern.getArray().length; row++) {
					for (int column = 0; column < pattern.getArray()[0].length; column++) {
						Cell cell = array[row][column];
						cell.setLink(pattern.getArray()[row][column].getFirstLink());
						cell.setLink(pattern.getArray()[row][column].getSecondLink());
					}
				}
			}
			else {
				return;
			}
		}
	}
	
	static class ScanwordUtil {
			
		private static Cell getChangedCell(Scanword scanword, int row, 
				int column,	Cell currentCell, Cell neighboureCell, String position, String direction) {
			Cell cell = currentCell;
			if ( SimpleProcessingWithCellsUtil.getCountNeighbourCellsOtherType(scanword, row, column, ActiveCell.class, CommentCell.class, position).size()>0
					|| SimpleProcessingWithCellsUtil.getCountNeighbourCellsOtherType(scanword, row, column, SimpleCell.class, CommentCell.class, position).size()>0) {
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
			}
			return cell;
		}
		
		public static boolean changeSimpleCellsToActiveCells (Scanword scanword) {
			if (SimpleProcessingWithCellsUtil.getCountFreeLinksInOneTypeCells(scanword, CommentCell.class) == 0) {
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
								cell.setAvailableFirstLink(false);
							}
							if (!((ActiveCell)cell).getVDirection()) {
								cell.setSecondLink("9.9");
								cell.setAvailableSecondLink(false);
							}
						}
					}
				}
				return true;
			}
		}
		
		public static boolean createArrawsInActiveAndCommentCells (Scanword scanword, Pattern pattern) {
			initialStep(scanword, pattern);
			
			firstStep(scanword);
		return true;
		}
		
		private static void initialStep(Scanword scanword, Pattern pattern) {
			for (int row = 0; row < scanword.getRow()-pattern.getArray().length; row++) {
				for (int column = 0; column < scanword.getColumns()-pattern.getArray()[0].length; column++) {
					Cell[][] subArray = new Cell[pattern.getArray().length][pattern.getArray()[0].length];
					for (int iRow = 0; iRow < pattern.getArray().length; iRow++) {
						for (int iColumn = 0; iColumn < pattern.getArray()[0].length; iColumn++) {
							subArray[iRow][iColumn] = scanword.getArray()[row+iRow][column+iColumn];
						}
					}
					PatternScanwordUtil.setSubArray(subArray, pattern);
				}
			}
		}
		
		private static void firstStep(Scanword scanword) {
			setLinks(scanword, 0, scanword.getRow()-1, 0, scanword.getColumns(), 1, 0, ActiveCell.class, 2, "7.3", 0, 0, CommentCell.class, 0, "3.3.3");
			setLinks(scanword, 0, scanword.getRow(), 0, scanword.getColumns()-1, 0, 1, ActiveCell.class, 1, "5.1", 0, 0, CommentCell.class, 0, "1.1.1");
		
			setAvailableCells(scanword, 0, scanword.getRow(), 0, scanword.getColumns());
		}
					
		private static List<Object[]> getListLinkedCells(Scanword scanword, int startRow, int startColumn) {
			BinaryTree<Cell> tree = new BinaryTree<Cell>();
			List<Cell> list = new LinkedList<Cell>();
			Cell startCell = scanword.getArrayElement(startRow, startColumn);
			if (startCell instanceof CommentCell && startCell.getCountFreeLink()==2) {
				list.add(startCell);
				
				Cell cCell = startCell;
				Node<Cell> rootNode = new Node<Cell> (cCell, startRow, startColumn);
				tree.insert(null, rootNode);
			
				Node<Cell>[] nodes = tree.getLeavesTree(rootNode, new Node[0], 0);
				int counter = 0;
				while (nodes.length !=0 && counter < 20) {
					for (Node<Cell> node:nodes) {
						if (node == null) {
							continue;
						}
						List<Integer> indexes = null;
						if (node.getCell() instanceof CommentCell) {
							 indexes = SimpleProcessingWithCellsUtil.getCountNeighbourCellsOtherType(scanword, node.getRowIndex(), node.getColumnIndex(), CommentCell.class, 
									ActiveCell.class, SimpleProcessingWithCellsUtil.transformatePositionCellToStringPosition(scanword, node.getRowIndex(), node.getColumnIndex()));
						}
						else if (node.getCell() instanceof ActiveCell) {
							indexes = SimpleProcessingWithCellsUtil.getCountNeighbourCellsOtherType(scanword, node.getRowIndex(), node.getColumnIndex(), ActiveCell.class, 
									CommentCell.class, SimpleProcessingWithCellsUtil.transformatePositionCellToStringPosition(scanword, node.getRowIndex(), node.getColumnIndex()));
						}
						for (int index = 0 ; index < indexes.size(); index++) {
							int[] positionNeighboureCell = SimpleProcessingWithCellsUtil.transformateDirectionIndexToDiffCoordinateCell(indexes.get(index));
							Cell newCell = scanword.getArrayElement(node.getRowIndex()+positionNeighboureCell[0], node.getColumnIndex()+positionNeighboureCell[1]);
							if (newCell instanceof ActiveCell 
									&& (newCell.isAvailableFirstLink() 
									|| newCell.isAvailableSecondLink())) {
								tree.insert(node, new Node<Cell> (newCell, node.getRowIndex()+positionNeighboureCell[0], node.getColumnIndex()+positionNeighboureCell[1]));
							}
							if (newCell instanceof CommentCell) {
								Node<Cell> newNode = new Node<Cell> (newCell, node.getRowIndex()+positionNeighboureCell[0], node.getColumnIndex()+positionNeighboureCell[1]);
								if (newCell.getCountAvailableLink()==2 && newCell.getCountFreeLink()==0) {
									newNode.setFinishedNode(true);
								}
								tree.insert(node, newNode);
							}
						}
					}
					counter++;
					nodes = tree.getLeavesTree(rootNode, new Node[0], 0);
				}
				List<List<Object[]>> resultList = tree.getListBranchesTree(rootNode, new ArrayList<List<Object[]>> (10), new ArrayList<Object[]> (0));
				
				Iterator<List<Object[]>> iterator = resultList.iterator();
				while(iterator.hasNext()) {
					List<Object[]> cells = iterator.next();
					int length = cells.size();
					if (cells.get(length-1)[2] instanceof CommentCell 
							&& ((Cell)cells.get(length-1)[2]).getCountFreeLink() == 0
							&& ((Cell)cells.get(length-1)[2]).getCountAvailableLink()==2) {
					}
					else {
						iterator.remove();
					}
				}
				int count = 0;
				for (List<Object[]> cells: resultList) {
					if (cells != null) {
						count++;
					}
				}
				List<List<Object[]>> tmp = new ArrayList<List<Object[]>>(count);
				for (int index = 0; index < count;index++) {
					tmp.add(index, resultList.get(index));
				}
				resultList = tmp;
				
				count = 0;
				int controlIndex = 0;
				for (int index = 0; index < resultList.size(); index++) {
					if (resultList.get(index).size() > count) {
						count = resultList.get(index).size();
						controlIndex = index;
					}
				}
				if (resultList.size()!=0) {
					return resultList.get(controlIndex);
				}
				else {
					return new ArrayList<Object[]> ();
				}
			}
			else {
				return new ArrayList<Object[]> ();
			}
	}
		
		private static void setAvailableCells(Scanword scanword, int startRow, int endRow, int startColumn, int endColumn) {
			for (int row = startRow; row < endRow;row++) {
				for (int column = startColumn; column < endColumn; column++) {
					Cell currentCell = scanword.getArrayElement(row, column);
					String position = SimpleProcessingWithCellsUtil.transformatePositionCellToStringPosition(scanword, row, column);
					List<Integer> indexes = SimpleProcessingWithCellsUtil.getCountNeighbourCellsOtherType(scanword, row, column, CommentCell.class, ActiveCell.class, position);
					List<Cell> neighboureCells = new LinkedList<Cell>();
					for (int index = 0; index < indexes.size(); index++) {
						int[] positionsNeighboureCells = SimpleProcessingWithCellsUtil.transformateDirectionIndexToDiffCoordinateCell(indexes.get(index));
						neighboureCells.add(scanword.getArrayElement(row + positionsNeighboureCells[0], column + positionsNeighboureCells[1]));
					}
					
					if (neighboureCells.size() == 1) {
						Cell cell = neighboureCells.get(0);
						if( cell.getCountAvailableLink() == 1) {
							if (!currentCell.getFirstLink().equals("0.0.0")
									&& !currentCell.getFirstLink().equals("9.9.9")) {
								currentCell.setAvailableFirstLink(false);
								currentCell.setSecondLink("9.9.9");
							}
							if (!currentCell.getSecondLink().equals("0.0.0")
									&& !currentCell.getSecondLink().equals("9.9.9")) {
								currentCell.setAvailableSecondLink(false);
								currentCell.setFirstLink("9.9.9");
							}
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
					if (SimpleProcessingWithCellsUtil.isType(fCell, ActiveCell.class) & SimpleProcessingWithCellsUtil.isType(sCell, CommentCell.class)
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
					else if (SimpleProcessingWithCellsUtil.isType(fCell, CommentCell.class) & SimpleProcessingWithCellsUtil.isType(sCell, ActiveCell.class)
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
	}
	
	public static boolean defineActiveCellsInScanword(Scanword scanword) {
		return ScanwordUtil.changeSimpleCellsToActiveCells(scanword);
	}
	
	public static boolean defineArrowsForCommentCells(Scanword scanword, Pattern pattern) {
		return ScanwordUtil.createArrawsInActiveAndCommentCells(scanword, pattern);
	}
		
	public static Map<String, List<String>> getWords(Scanword scanword) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		
		return map;
	}
}