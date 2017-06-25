package sample.impls;

import java.util.*;

import sample.impls.cell.*;
import sample.impls.tree.*;
import sample.interfaces.*;

public final class Util {
	
	private static final int FIRST_LINK_CELL = 1;
	private static final int SECOND_LINK_CELL = 2;
	
	static class LinkUtil {
		private static boolean containsDirectionIndexInLinkCell(Cell cell, int directionIndex, int linkIndex) {
			String link = "";
			if (linkIndex == FIRST_LINK_CELL) {
				link = cell.getFirstLink();
			}
			else if (linkIndex == SECOND_LINK_CELL) {
				link = cell.getSecondLink();
			}
			
			String firstLinkElement = "";
			if (cell instanceof CommentCell) {
				firstLinkElement = link.substring(2,3);
			}
			else if (cell instanceof ActiveCell) {
				firstLinkElement = link.substring(0,1);
			}
			
			if (firstLinkElement.equals(String.valueOf(directionIndex))) {
				return true;
			}
			return false;
		}
		
		public static void createLinkBetweenTwoCells(Cell fCell, int directionCurrentCell, Cell sCell, int directionNeighbourCell) {
			if (directionCurrentCell == -1 && directionNeighbourCell == -1) {
	 			return ;
	 		}
	 		
			if (fCell instanceof CommentCell && sCell instanceof ActiveCell) {
				if (sCell.getFirstLink().equals("0.0")) {
					sCell.setFirstLink(directionCurrentCell+".1");
					fCell.setLink(directionNeighbourCell+"."+directionNeighbourCell+".1");
				}
				else {
					if (sCell.getSecondLink().equals("0.0")) {
						sCell.setSecondLink(directionCurrentCell+".3");
						fCell.setLink(directionNeighbourCell+"."+directionNeighbourCell+".3");
					}
				}
			}
		}
		
		public static boolean isLinkBetweenTwoCells(Cell fCell, int fDirectionIndex, Cell sCell, int sDirectionIndex) {
			return (containsDirectionIndexInLinkCell(fCell, fDirectionIndex, 1) || containsDirectionIndexInLinkCell(fCell, fDirectionIndex, 2))
					&& (containsDirectionIndexInLinkCell(sCell, sDirectionIndex, 1) || containsDirectionIndexInLinkCell(sCell, sDirectionIndex, 2)) ;
		}
		
	 	public static void breakLinkBetweenTwoCells(Cell fCell, int fDirectionIndex, Cell sCell, int sDirectionIndex) {
	 		if (containsDirectionIndexInLinkCell(fCell, fDirectionIndex, 1) 
	 				&& containsDirectionIndexInLinkCell(sCell, sDirectionIndex, 1)) {
	 			fCell.setFirstLink("0.0");
	 			sCell.setFirstLink("0.0.0");
	 			fCell.setFirstLink("0.0.0");
	 			sCell.setFirstLink("0.0");
	 		}
	 		else if (containsDirectionIndexInLinkCell(fCell, fDirectionIndex, 2) 
	 					&& containsDirectionIndexInLinkCell(sCell, sDirectionIndex, 2)) {
	 			fCell.setSecondLink("0.0");
	 			sCell.setSecondLink("0.0.0");
	 			fCell.setSecondLink("0.0.0");
	 			sCell.setSecondLink("0.0");
	 		}
	 		else if (containsDirectionIndexInLinkCell(fCell, fDirectionIndex, 1) 
 						&& containsDirectionIndexInLinkCell(sCell, sDirectionIndex, 2)) {
	 			fCell.setFirstLink("0.0");
	 			sCell.setSecondLink("0.0.0");
	 			fCell.setFirstLink("0.0.0");
	 			sCell.setSecondLink("0.0");
	 		}
	 		else if (containsDirectionIndexInLinkCell(fCell, fDirectionIndex, 2) 
 						&& containsDirectionIndexInLinkCell(sCell, sDirectionIndex, 1)) {
	 			fCell.setSecondLink("0.0");
	 			sCell.setFirstLink("0.0.0");
	 			fCell.setSecondLink("0.0.0");
	 			sCell.setFirstLink("0.0");
	 		}
	 	}
	}
	
	static class CellUtils {
		private static boolean isType(Cell cell, Class<? extends Cell> classType) {
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
		}
	}
	
	static class LinkUtils {
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
				int[] def = {0,0};
				return def;
			}
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
			for (int row = 0; row < scanword.getRow(); row++) {
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
		
		private static List<Integer> getCountNeighbourCellsOtherType(Cell[][] array, int row, int column, 
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
	
	static class ArrowUtils {
		
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
		
		public static void defineSimpleArrow(Cell[][] array) {
			for (int row = 0; row < array.length; row++) {
				for (int column = 0; column < array[0].length-2; column++) {
					if (CellUtils.isType(array[row][column], CommentCell.class)
							&& CellUtils.isType(array[row][column+1], ActiveCell.class)
							&& (CellUtils.isType(array[row][column+2], ActiveCell.class)
									|| CellUtils.isType(array[row][column+2], SimpleCell.class))) {
						array[row][column].setLink("1.1.1");
						array[row][column+1].setFirstLink("5.1");
					}
				}
			}
			for (int row = 0; row < array.length-2; row++) {
				for (int column = 0; column < array[0].length; column++) {
					if (CellUtils.isType(array[row][column], CommentCell.class)
							&& CellUtils.isType(array[row+1][column], ActiveCell.class)
							&& (CellUtils.isType(array[row+2][column], ActiveCell.class)
									|| CellUtils.isType(array[row+2][column], SimpleCell.class))) {
						array[row][column].setLink("3.3.3");
						array[row+1][column].setSecondLink("7.3");
					}
				}
			}
		}
		
		public static void defineToDownArrow(Cell[][] array, int startRow, int endRow, int startColumn, int endColumn, 
				int shiftRow, int shiftColumn, String linkInActiveCell, String linkInCommentCell) {
			for(int row = startRow; row < array.length + endRow; row++) {
				for (int column = startColumn; column < array[0].length + endColumn; column++) {
					Cell fCell = array[row][column];
					Cell sCell = array[row + shiftRow][column + shiftColumn];
					if (CellUtils.isType(fCell, ActiveCell.class) && CellUtils.isType(sCell, CommentCell.class) 
							&& fCell.getSecondLink().equals("0.0") && sCell.getCountFreeLink() > 0) {
						fCell.setSecondLink(linkInActiveCell);
						sCell.setLink(linkInCommentCell);
					}
				}
			}
		}

		public static void defineToRightArrow(Cell[][] array, int startRow, int endRow, int startColumn, int endColumn,
				int shiftRow, int shiftColumn, String linkInActiveCell, String linkInCommentCell) {
			for (int row = startRow; row < array.length + endRow; row++) {
				for (int column = startColumn; column < array[0].length + endColumn; column++) {
					Cell fCell = array[row][column];
					Cell sCell = array[row + shiftRow][column + shiftColumn];
					if (CellUtils.isType(fCell, ActiveCell.class) && CellUtils.isType(sCell, CommentCell.class) 
							&& fCell.getFirstLink().equals("0.0") && sCell.getCountFreeLink() > 0) {
						fCell.setFirstLink(linkInActiveCell);
						sCell.setLink(linkInCommentCell);
					}
				}
			}
		}
		
		public static void firstStepDefineArrows(Cell[][] array) {
			defineSimpleArrow(array);
		}
		
		public static void secondStepDefineArrows(Cell[][] array) {
			defineToDownArrow(array, 0, 0, 0, -1, 0, 1, "1.3", "5.5.3");//left-down
			defineToDownArrow(array, 0, 0, 1, 0, 0, -1, "5.3", "1.1.3");//right-down
			defineToDownArrow(array, 0, -1, 0, -1, 1, 1, "2.3", "6.6.3");//up-left-down
			defineToDownArrow(array, 0, -1, 1, 0, 1, -1, "4.3", "8.8.3");//up-right-down
			
			defineToRightArrow(array, 0, -1, 0, 0, 1, 0, "7.1", "3.3.1");//down-right
			defineToRightArrow(array, 1, 0, 0, 0, -1, 0, "3.1", "7.7.1");//up-right
			defineToRightArrow(array, 0, -1, 0, -1, 1, 1, "2.1", "6.6.1");//left-up-right
			defineToRightArrow(array, 1, 0, 0, -1, -1, 1, "8.1", "4.4.1");//left-down-right
		}
		
		private static BinaryTree<Cell> getTree(Scanword scanword, int startRow, int startColumn, int level) {
			BinaryTree<Cell> tree = new BinaryTree<Cell>();
			List<Cell> list = new LinkedList<Cell>();
			Cell startCell = scanword.getArrayElement(startRow, startColumn);
			if (startCell instanceof CommentCell && startCell.getCountFreeLink() == 2) {
				list.add(startCell);
				Cell cCell = startCell;
				Node<Cell> rootNode = new Node<Cell> (cCell, startRow, startColumn);
				tree.insert(null, rootNode);
			
				Node<Cell>[] nodes = tree.getLeavesTree(rootNode, new Node[0], 0);
				int counter = 0;
				boolean flag = false;
				while (!flag && nodes.length !=0 && counter < level) {
					for (Node<Cell> node:nodes) {
						if (node == null) {
							continue;
						}
						if ((node.getCell() instanceof CommentCell) 
								&& node.getCell().getCountAvailableLink() == 2 
								&& node.getCell().getCountFreeLink() == 0) {
							flag = true;
							break;
						}
						if ((node.getCell() instanceof ActiveCell)
								&& node.getCell().getCountFreeLink() > 0) {
							flag = true;
							break;
						}
						List<Integer> indexes = null;
						if (node.getCell() instanceof CommentCell) {
							 indexes = LinkUtils.getCountNeighbourCellsOtherType(scanword.getArray(), node.getRowIndex(), node.getColumnIndex(), CommentCell.class, ActiveCell.class);
						}
						else if (node.getCell() instanceof ActiveCell) {
							indexes = LinkUtils.getCountNeighbourCellsOtherType(scanword.getArray(), node.getRowIndex(), node.getColumnIndex(), ActiveCell.class, CommentCell.class);
						}
						for (int index = 0 ; index < indexes.size(); index++) {
							int[] positionNeighboureCell = LinkUtils.transformateDirectionIndexToDiffCoordinateCell(indexes.get(index));
							Cell newCell = scanword.getArrayElement(node.getRowIndex()+positionNeighboureCell[0], node.getColumnIndex()+positionNeighboureCell[1]);
							if (newCell instanceof ActiveCell 
									&& (newCell.isAvailableFirstLink() 
									|| newCell.isAvailableSecondLink())) {
									tree.insert(node, new Node<Cell> (newCell, 
											node.getRowIndex()+positionNeighboureCell[0], 
											node.getColumnIndex()+positionNeighboureCell[1]));
							}
							if (newCell instanceof CommentCell) {
								if (LinkUtil.isLinkBetweenTwoCells(node.getCell(), LinkUtils.transformateDiffCoordinateToDirectionIndex(node.getRowIndex(), node.getColumnIndex()), 
										newCell, LinkUtils.transformateDiffCoordinateToDirectionIndex(node.getRowIndex()+positionNeighboureCell[0], 
											node.getColumnIndex()+positionNeighboureCell[1]))) {
									Node<Cell> newNode = new Node<Cell> (newCell, node.getRowIndex()+positionNeighboureCell[0], node.getColumnIndex()+positionNeighboureCell[1]);
									if (newCell.getCountAvailableLink()==2 && newCell.getCountFreeLink()==0) {
										newNode.setFinishedNode(true);
									}
									tree.insert(node, newNode);
								}
							}
						}
					}
					counter++;
					nodes = tree.getLeavesTree(rootNode, new Node[0], 0);
				}
			}
			return tree;
		}
		
		private static void removeBadBranches(List<List<Object[]>> list) {
			Iterator<List<Object[]>> iterator = list.iterator();
			while(iterator.hasNext()) {
				List<Object[]> cells = iterator.next();
				int length = cells.size();
				if (cells.get(length-1)[2] instanceof CommentCell 
						&& ((Cell)cells.get(length-1)[2]).getCountFreeLink() == 0
						&& ((Cell)cells.get(length-1)[2]).getCountAvailableLink()==2) {
				}
				else if (cells.get(length-1)[2] instanceof ActiveCell
						&& ((Cell)cells.get(length-1)[2]).getCountFreeLink()>0) {
					
				}
				else {
					iterator.remove();
				}
			}
		}
		
		private static List<Object[]> selectListLinkedCells(List<List<Object[]>> resultList) {
			removeBadBranches(resultList);
			
			int countElementsInList = 0;
			
			for (List<Object[]> list: resultList) {
				if (list != null) {
					countElementsInList++;
				}
			}
			
			List<List<Object[]>> tmp = new ArrayList<List<Object[]>>(countElementsInList);
			for (int index = 0; index < countElementsInList; index++) {
				tmp.add(index, resultList.get(index));
			}
			resultList = tmp;
				
			countElementsInList = 0;
			
			List<Integer> rList = new ArrayList<Integer>(resultList.size());
			if (resultList.size() > 1) {
					for (int index = 0; index < resultList.size(); index++) {
						for (int iIndex = resultList.get(index).size()-1; iIndex >= 1 ; iIndex-=2) {
							List<Object[]> obj = resultList.get(index);
							Object[] fObj = resultList.get(index).get(iIndex);
							Object[] sObj = resultList.get(index).get(iIndex-1);
							if (LinkUtil.isLinkBetweenTwoCells(
									(Cell)fObj[2], LinkUtils.transformateDiffCoordinateToDirectionIndex((int)fObj[0], (int)fObj[1]), 
									(Cell)sObj[2], LinkUtils.transformateDiffCoordinateToDirectionIndex((int)sObj[0], (int)sObj[1]))) {
							}
							else {
								rList.add(index);
								break;
							}
						}
					}
				}
				for (int index = rList.size()-1; index >= 0; index--) {
					List<Object[]> obj = resultList.get(rList.get(index));
					resultList.remove(obj);
				}
				
				int controlIndex = 0;
				for (int index = 0; index < resultList.size(); index++) {
					if (resultList.get(index).size() > countElementsInList) {
						countElementsInList = resultList.get(index).size();
						controlIndex = index;
					}
				}
				if (resultList.size()!= 0) {
					return resultList.get(controlIndex);
				}
				else {
					return new ArrayList<Object[]> ();
				}
			}
		
		public static List<Object[]> getListLinkedCells(Scanword scanword, int startRow, int startColumn, int level) {
			return ArrowUtils.selectListLinkedCells(ArrowUtils.getTree(scanword, startRow, startColumn, level)
													.getListBranchesTree(ArrowUtils.getTree(scanword, startRow, startColumn, level).getRootNode(), 
													new ArrayList<List<Object[]>>(), new ArrayList<Object[]>()));				
		}
		
		public static void redefineCellsLink(List<Object[]> list) {
			Object[] currentObj = null;
			Object[] nextObj = null;
			for (int index = list.size()-1; index >= 1; index-=2) {
				currentObj = list.get(index);
				nextObj = list.get(index-1);
				if (LinkUtil.isLinkBetweenTwoCells((Cell)currentObj[2], LinkUtils.transformateDiffCoordinateToDirectionIndex((int)currentObj[0], (int)currentObj[1]), 
						(Cell)nextObj[2], LinkUtils.transformateDiffCoordinateToDirectionIndex((int)nextObj[0], (int)nextObj[1]))) {
					LinkUtil.breakLinkBetweenTwoCells((Cell)currentObj[2], LinkUtils.transformateDiffCoordinateToDirectionIndex((int)currentObj[0], (int)currentObj[1]),
							(Cell)nextObj[2], LinkUtils.transformateDiffCoordinateToDirectionIndex((int)nextObj[0], (int)nextObj[1]));
				}
			}
			for (int index = 0; index < list.size()-1; index+=2) {
				currentObj = list.get(index);
				nextObj = list.get(index+1);
				LinkUtil.createLinkBetweenTwoCells((Cell)currentObj[2], LinkUtils.transformateDiffCoordinateToDirectionIndex((int)currentObj[0], (int)currentObj[1]), 
						(Cell)nextObj[2], LinkUtils.transformateDiffCoordinateToDirectionIndex((int)nextObj[0], (int)nextObj[1]));
			}	
		}
	}
	
	static class ScanwordUtil {
		
		private static void changeFilledLinks(Scanword scanword) {
			for (int row = 0; row < scanword.getRow(); row ++) {
				for (int column = 0; column < scanword.getColumns(); column++) {
					Cell cell = scanword.getArrayElement(row, column);
					if (cell instanceof ActiveCell) {
						if (cell.getFirstLink().equals("2.1")
								&& cell.getSecondLink().equals("2.3")) {
							scanword.getArrayElement(row+1, column+1).setFirstLink("7.6.1.1");
							scanword.getArrayElement(row+1, column+1).setSecondLink("5.6.3.1");
						}
					}
					else if (cell instanceof CommentCell) {
						if (cell.getFirstLink().equals("2.2.3")) {
							if (scanword.getArrayElement(row, column+1) instanceof CommentCell) {
								if (!(scanword.getArrayElement(row+1, column) instanceof CommentCell)) {
									cell.setFirstLink("3.2.3");
								}
							}
						}
						else if (cell.getFirstLink().equals("2.2.1")) {
							if (scanword.getArrayElement(row+1, column) instanceof CommentCell) {
								if (!(scanword.getArrayElement(row, column+1) instanceof CommentCell)) {
									cell.setFirstLink("1.2.1");
								}
							}
						}
						else if (cell.getFirstLink().equals("4.4.1")) {
							if (scanword.getArrayElement(row, column-1) instanceof CommentCell) {
								cell.setFirstLink("3.4.1");
							}
							else if (!(scanword.getArrayElement(row, column-1) instanceof CommentCell)) {
								cell.setFirstLink("5.4.1");
							}
						}
						else if (cell.getFirstLink().equals("4.4.3")) {
							if (scanword.getArrayElement(row, column - 1) instanceof CommentCell) {
								if (!(scanword.getArrayElement(row+1, column) instanceof CommentCell)) {
									cell.setFirstLink("3.4.3");
								}
							}
						}
						else if (cell.getFirstLink().equals("6.6.1")) {
							if (scanword.getArrayElement(row, column - 1) instanceof CommentCell) {
								cell.setFirstLink("7.6.1");
							}
							else if (!(scanword.getArrayElement(row, column-1) instanceof CommentCell)) {
								cell.setFirstLink("5.6.1");
							}
						}
						else if (cell.getFirstLink().equals("6.6.3")) {
							if (scanword.getArrayElement(row - 1, column) instanceof CommentCell) {
								cell.setFirstLink("5.6.3");
							}
							else if (!(scanword.getArrayElement(row - 1, column) instanceof CommentCell)) {
								cell.setFirstLink("7.6.3");
							}
						}
						else if (cell.getFirstLink().equals("8.8.1")) {
							if (scanword.getArrayElement(row-1, column) instanceof CommentCell) {
								if (!(scanword.getArrayElement(row, column+1) instanceof CommentCell)) {
									cell.setFirstLink("1.8.1");
								}
							}
						}
						else if (cell.getFirstLink().equals("8.8.3")) {
							if (scanword.getArrayElement(row-1, column) instanceof CommentCell) {
								cell.setFirstLink("1.8.3");
							}
							else if (!(scanword.getArrayElement(row-1, column) instanceof CommentCell)) {
								cell.setFirstLink("7.8.3");
							}
						}

						if (cell.getSecondLink().equals("2.2.3")) {
							if (scanword.getArrayElement(row, column+1) instanceof CommentCell) {
								if (!(scanword.getArrayElement(row+1, column) instanceof CommentCell)) {
									cell.setSecondLink("3.2.3");
								}
							}
						}
						else if (cell.getSecondLink().equals("2.2.1")) {
							if (scanword.getArrayElement(row+1, column) instanceof CommentCell) {
								if (!(scanword.getArrayElement(row, column+1) instanceof CommentCell)) {
									cell.setSecondLink("1.2.1");
								}
							}
						}
						else if (cell.getSecondLink().equals("4.4.1")) {
							if (scanword.getArrayElement(row, column-1) instanceof CommentCell) {
								cell.setSecondLink("3.4.1");
							}
							else if (!(scanword.getArrayElement(row, column-1) instanceof CommentCell)) {
								cell.setSecondLink("5.4.1");
							}
						}
						else if (cell.getSecondLink().equals("4.4.3")) {
							if (scanword.getArrayElement(row, column - 1) instanceof CommentCell) {
								if (!(scanword.getArrayElement(row+1, column) instanceof CommentCell)) {
									cell.setSecondLink("3.4.3");
								}
							}
						}
						else if (cell.getSecondLink().equals("6.6.1")) {
							if (scanword.getArrayElement(row, column - 1) instanceof CommentCell) {
								cell.setSecondLink("7.6.1");
							}
							else if (!(scanword.getArrayElement(row, column-1) instanceof CommentCell)) {
								cell.setSecondLink("5.6.1");
							}
						}
						else if (cell.getSecondLink().equals("6.6.3")) {
							if (scanword.getArrayElement(row - 1, column) instanceof CommentCell) {
								cell.setSecondLink("5.6.3");
							}
							else if (!(scanword.getArrayElement(row - 1, column) instanceof CommentCell)) {
								cell.setSecondLink("7.6.3");
							}
						}
						else if (cell.getSecondLink().equals("8.8.1")) {
							if (scanword.getArrayElement(row-1, column) instanceof CommentCell) {
								if (!(scanword.getArrayElement(row, column+1) instanceof CommentCell)) {
									cell.setSecondLink("1.8.1");
								}
							}
						}
						else if (cell.getSecondLink().equals("8.8.3")) {
							if (scanword.getArrayElement(row-1, column) instanceof CommentCell) {
								cell.setSecondLink("1.8.3");
							}
							else if (!(scanword.getArrayElement(row-1, column) instanceof CommentCell)) {
								cell.setSecondLink("7.8.3");
							}
						}
					}
				}
			}
		}
				
		private static void removeAllFreeLinks(Scanword scanword) {
			for (int row = 0; row < scanword.getRow(); row++) {
				for (int column = 0; column < scanword.getColumns(); column++) {
					boolean flag = true;
					Cell cell = scanword.getArrayElement(row, column);
					List<Integer> indexes = new ArrayList<Integer>();
					if (cell instanceof CommentCell && cell.getCountFreeLink() > 0) {
						indexes = LinkUtils.getCountNeighbourCellsOtherType(scanword.getArray(), 
								row, column, CommentCell.class, ActiveCell.class);
						for (Integer index: indexes) {
							int[] diffCoordinate = LinkUtils.transformateDirectionIndexToDiffCoordinateCell(index);
							Cell neighbourCell = scanword.getArrayElement(row+diffCoordinate[0], column+diffCoordinate[1]);
							if (neighbourCell.getCountFreeLink() == 0) {
								continue;
							}
							flag = false;
						}
					}
					if (flag) {
						cell.setLink("9.9.9");
					}
				}
			}
		}

		private static void nextStepRedefineArrows(Scanword scanword, int level) {
			List<Object[]> list = null;
			for (int row = scanword.getRow()-1; row >= 0; row--) {
				for (int column = scanword.getColumns()-1; column >= 0; column--) {
					list = ArrowUtils.getListLinkedCells(scanword, row, column, level);
					if (list != null & list.size() > 0) {
						ArrowUtils.redefineCellsLink(list);
					}
				}
			}
		}

	}

	public static int[] getCountAvailableCells(Scanword scanword) {
		return LinkUtils.getCountAvailableCells(scanword);
	}
	
	public static boolean defineActiveCellsInScanword(Scanword scanword) {
		if (LinkUtils.getCountAvailableCells(scanword)[0] == 0) {
			return false;
		}
		else {
			CellUtils.defineActiveCell(scanword);
			CellUtils.defineInitLinksInActiveCell(scanword.getArray());
			return true;
		}
	}
	
	public static boolean defineArrowsForCommentCells(Scanword scanword) {
		ArrowUtils.firstStepDefineArrows(scanword.getArray());
		ArrowUtils.secondStepDefineArrows(scanword.getArray());
		for (int level = 1; level < 20; level++) {
			ScanwordUtil.nextStepRedefineArrows(scanword, level);
		}
		ScanwordUtil.removeAllFreeLinks(scanword);
		ScanwordUtil.changeFilledLinks(scanword);
		return true;
	}
			
	public static Map<String, List<String>> getWords(Scanword scanword) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		
		return map;
	}
}