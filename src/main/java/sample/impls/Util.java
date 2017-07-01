package sample.impls;

import java.util.*;

import org.apache.log4j.*;

import sample.impls.cell.*;
import sample.impls.tree.*;
import sample.interfaces.*;

public final class Util {

	private static Logger logger = Logger.getLogger(Util.class.getName());
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
			boolean isDirectionIndexInFirstCell = containsDirectionIndexInLinkCell(fCell, fDirectionIndex, 1) || containsDirectionIndexInLinkCell(fCell, fDirectionIndex, 2);
			boolean isDirectionIndexInSecondCell = containsDirectionIndexInLinkCell(sCell, sDirectionIndex, 1) || containsDirectionIndexInLinkCell(sCell, sDirectionIndex, 2);
			return isDirectionIndexInFirstCell && isDirectionIndexInSecondCell;
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
		
		private static int oppositeDirectionIndex(int directionIndex) {
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
			logger.debug("start execute method defineSimpleArrow");
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
			logger.debug("start execute method firstStepDefineArrows");
			defineSimpleArrow(array);
			logger.debug("end execute method defineSimpleArrow");
		}
		
		public static void secondStepDefineArrows(Cell[][] array) {
			logger.debug("start execute method secondStepDefineArrows");
			defineToDownArrow(array, 0, 0, 0, -1, 0, 1, "1.3", "5.5.3");//left-down
			defineToDownArrow(array, 0, 0, 1, 0, 0, -1, "5.3", "1.1.3");//right-down
			defineToDownArrow(array, 0, -1, 0, -1, 1, 1, "2.3", "6.6.3");//up-left-down
			defineToDownArrow(array, 0, -1, 1, 0, 1, -1, "4.3", "8.8.3");//up-right-down
			
			defineToRightArrow(array, 0, -1, 0, 0, 1, 0, "3.1", "7.7.1");//down-right
			defineToRightArrow(array, 1, 0, 0, 0, -1, 0, "7.1", "3.3.1");//up-right
			defineToRightArrow(array, 0, -1, 0, -1, 1, 1, "2.1", "6.6.1");//left-up-right
			defineToRightArrow(array, 1, 0, 0, -1, -1, 1, "8.1", "4.4.1");//left-down-right
		}
		
		private static BinaryTree getTree(Scanword scanword, int startRow, int startColumn, int level) {
			logger.debug("start execute method getTree. "
					+ "Creation tree, having pich with coordinate: "
					+ "row=" + startRow + " column=" + startColumn);
			BinaryTree tree = new BinaryTree();
			Cell startCell = scanword.getArrayElement(startRow, startColumn);
			if (startCell instanceof CommentCell && startCell.getCountFreeLink() == 2) {
				Cell cCell = startCell;
				Node rootNode = new Node ("0.0.0","first" , startRow, startColumn);
				tree.insert(null, rootNode);
				
				Node[] nodes = tree.getLeavesTree(rootNode, new Node[0], 0);
				int counter = -1;
				boolean flag = false;
				while (!flag & nodes.length != 0 & ++counter < 2*level) {
					for (Node node:nodes) {
						Cell cell = null;
						if (node == null) {
							continue;
						}
						else {
							cell = scanword.getArrayElement(node.getRowIndex(), node.getColumnIndex());
							if (cell instanceof CommentCell
									&& cell.getCountAvailableLink() == 2
									&& cell.getCountFreeLink() == 0) {
								flag = true;
								break;
							}
							if (cell instanceof ActiveCell
									&& cell.getCountFreeLink() > 0) {
								flag = true;
								break;
							}
							List<Integer> indexes = null;
							if (cell instanceof CommentCell) {
								 indexes = LinkUtils.getCountNeighbourCellsOtherType(scanword.getArray(), node.getRowIndex(), node.getColumnIndex(), CommentCell.class, ActiveCell.class);
							}
							else if (cell instanceof ActiveCell) {
								indexes = LinkUtils.getCountNeighbourCellsOtherType(scanword.getArray(), node.getRowIndex(), node.getColumnIndex(), ActiveCell.class, CommentCell.class);
							}
							for (int index = 0 ; index < indexes.size(); index++) {
								int[] positionNeighboureCell = LinkUtils.transformateDirectionIndexToDiffCoordinateCell(indexes.get(index));
								Cell newCell = scanword.getArrayElement(node.getRowIndex()+positionNeighboureCell[0], node.getColumnIndex()+positionNeighboureCell[1]);
								if (!(newCell.getFirstLink().equals("9.9.9")
										|| newCell.getFirstLink().equals("9.9"))) { 
									Node newNode = new Node(newCell.getFirstLink(), "first", 
											node.getRowIndex()+positionNeighboureCell[0], 
											node.getColumnIndex()+positionNeighboureCell[1]);
									if (newCell instanceof CommentCell 
											& newCell.getCountAvailableLink() == 2 
											& newCell.getCountFreeLink() == 0) {
										newNode.setFinishedNode(true);
									}
									if (newCell instanceof ActiveCell
											& newNode.getLinkValue().equals("0.0")) {
										newNode.setFinishedNode(true);
									}
									if (isLinkBetweenTwoNodes(node, newNode, scanword)) {
										if (node.getParent() == null) {
											tree.insert(node, newNode);
										}
										else {
											if (!node.getParent().equals(newNode)) {
												tree.insert(node, newNode);
											}
										}
									}
								}
								if (!(newCell.getSecondLink().equals("9.9.9")
										|| newCell.getSecondLink().equals("9.9"))) {
									Node newNode = new Node(newCell.getSecondLink(), "second", 
											node.getRowIndex()+positionNeighboureCell[0], 
											node.getColumnIndex()+positionNeighboureCell[1]);
									if (newCell instanceof CommentCell
											& newCell.getCountAvailableLink() == 2 
											& newCell.getCountFreeLink() == 0) {
										newNode.setFinishedNode(true);
									}
									if (newCell instanceof ActiveCell
											& newNode.getLinkValue().equals("0.0")) {
										newNode.setFinishedNode(true);
									}
									if (isLinkBetweenTwoNodes(node, newNode, scanword)) {
										if (node.getParent() == null) {
											tree.insert(node, newNode);
										}
										else {
											if (!node.getParent().equals(newNode)) {
												tree.insert(node, newNode);
											}
										}
									}
								}
							}
						}
					}
					if (counter > 1) {
						for (int index = counter-1; index >= 0; index--) {
							tree.removeAllLeavesByLevel(counter-1);
						}
					}
					nodes = tree.getLeavesTree(rootNode, new Node[0], 0);
				}
			}
			return tree;
		}
		
		private static boolean isLinkBetweenTwoNodes(Node parentNode, Node childNode, Scanword scanword) {
			Cell fCell = scanword.getArrayElement(parentNode.getRowIndex(), parentNode.getColumnIndex());
			Cell sCell = scanword.getArrayElement(childNode.getRowIndex(), childNode.getColumnIndex());
			if (fCell instanceof CommentCell && sCell instanceof ActiveCell) {
				return true;
			}
			int fDirectionIndex = LinkUtils.transformateDiffCoordinateToDirectionIndex(childNode.getRowIndex() - parentNode.getRowIndex(), 
																		childNode.getColumnIndex() - parentNode.getColumnIndex());
			int sDirectionIndex = LinkUtils.oppositeDirectionIndex(fDirectionIndex);
			boolean isLinkBeetwenTwoCells = true;
			if (fCell instanceof ActiveCell && sCell instanceof CommentCell) {
				isLinkBeetwenTwoCells = LinkUtil.isLinkBetweenTwoCells(fCell, fDirectionIndex, sCell, sDirectionIndex);
			}
			int fDirectionIndexNode = -1;
			int sDirectionIndexNode = -1;
			if (parentNode.getLinkValue().length() == 3) {
				fDirectionIndexNode = Integer.valueOf(parentNode.getLinkValue().substring(0,1));
			}
			else if (parentNode.getLinkValue().length() == 5) {
				fDirectionIndexNode = Integer.valueOf(parentNode.getLinkValue().substring(2, 3));
			}
			if (childNode.getLinkValue().length() == 3) {
				sDirectionIndexNode = Integer.valueOf(childNode.getLinkValue().substring(0,1));
			}
			else if (childNode.getLinkValue().length() == 5) {
				sDirectionIndexNode = Integer.valueOf(childNode.getLinkValue().substring(2,3));
			}
			
			if (isLinkBeetwenTwoCells && fDirectionIndex == fDirectionIndexNode && sDirectionIndex == sDirectionIndexNode) {
				return true;
			}
			else {
				return false;
			}
		}
		
		private static void removeBadBranches(List<List<Object[]>> list) {
			if (list == null) { return;}
			Iterator<List<Object[]>> iterator = list.iterator();
			while(iterator.hasNext()) {
				List<Object[]> cells = iterator.next();
				int length = cells.size();
				if (length == 0) {return ;}
				if (!(boolean)cells.get(length-1)[4]) { 
					iterator.remove();
				}
			}
		}
		
		private static List<List<Object[]>> getListElementsWithMinimalLength(List<List<Object[]>> list) {
			logger.debug("start execute method getListElementsWithMinimalLength. Input size list: " + list.size());
			List<List<Object[]>> resultList = new ArrayList<List<Object[]>>();
			int length = list.get(0).size();
			
			for (int index = 1; index < list.size(); index++) {
				if (list.get(index).size() <= length) {
					length = list.get(index).size();
				}
			}
						
			for (int index = 0; index < list.size(); index++) {
				if (list.get(index).size() == length) {
					resultList.add(list.get(index));
				}
			}
			return resultList;
		}
		private static List<List<Object[]>> getListElementsWithCorrectLinks(List<List<Object[]>> list) {
			logger.debug("start execute method getListElementsWithCorrectLinks. Input size list: " + list.size());
			List<List<Object[]>> resultList = new ArrayList<List<Object[]>> ();
			for (int index = 0; index < list.size(); index++) {
				if(list.get(index).size()%2 == 1) {
					List<Object[]> innerList = list.get(index);
					if (String.valueOf(innerList.get(innerList.size()-1)[2]).substring(4)
							.equals(String.valueOf(innerList.get(innerList.size()-2)[2]).substring(2))) {
						resultList.add(list.get(index));
					}
				}
			}
			return resultList;
		}
		private static List<Object[]> selectListLinkedCells(List<List<Object[]>> resultList) {
			logger.debug("start execute method selectListLinkedCells. "
					+ "Count lines from linked cells: " + resultList.size());
			removeBadBranches(resultList);
			
			if (resultList.size() == 0 || resultList == null) {
				return null;
			}
			
			List<List<Object[]>> list = getListElementsWithMinimalLength(resultList);
			logger.debug("end execute method getListElementsWithMinimalLength. "
					+ "Size list after executing this method: " + list.size());
			list = getListElementsWithCorrectLinks(list);
			logger.debug("end execute method getListElementsWithCorrectLink. "
					+ "Size list after executing this method: " + list.size());
			if (list.size() == 0) {
				return null;
			}
			else if (list.size() == 1) {
				return list.get(0);
			}
			else {
				int index = getIndexElementWithMaxWeight(list);
				logger.debug("end execute method getIndexElementWithMaxWeight. "
						+ "Index show line is more relevant: " + index);
				
				return list.get(index);
			}
		}
		
		private static int getWeight (String link) {
			logger.debug("start execute method getWeight. input link: " + link);
			if (link.equals("2.1") || link.equals("2.3") || link.equals("8.1") || link.equals("4.3")) {
				return 4;
			}
			else if (link.equals("1.3") || link.equals("5.3") || link.equals("3.1") || link.equals("7.1")) {
				return 3;
			}
			else if (link.equals("6.3") || link.equals("6.1") || link.equals("8.3") || link.equals("4.1")) {
				return 2;
			}
			else if (link.equals("5.1") || link.equals("7.3")) {
				return 1;
			}
			else {
				return 0;
			}
		}
		
		private static int getIndexWithMaxWeight(List<String> list) {
			logger.debug("start execute method getIndexWithMaxWeight. input list size: " + list.size());
			int listIndex = -1;
			int weight = -1;
			for (int index = 0; index < list.size(); index++) {
				String element = list.get(index);
				if (weight < getWeight(element)) {
					weight = getWeight(element);
					logger.debug("end execute method getWeight. "
							+ "Result: weight=" + weight + " index=" + index);
					listIndex = index;
				}
			}
			return listIndex;
		}
		
		private static int getIndexElementWithMaxWeight(List<List<Object[]>> list) {
			logger.debug("start execute method getIndexElementWithMaxWeight. input list size: " + list.size());
			List<String> listLastElements = new LinkedList<String>();
			int length = 0;
			if (list.size()%2 == 0) {
				length = 2;
			}
			else {
				length = 1;
			}
			for (int index = 0; index < list.size(); index++) {
				List<Object[]> element = list.get(index);
				listLastElements.add(String.valueOf(element.get(element.size()-length)[2]));
			}
			int index = getIndexWithMaxWeight(listLastElements);
			logger.debug("end execute method getIndexWithMaxWeight. "
					+ "The most relevant index: " + index);
			return index;
		}
						
		public static List<Object[]> getListLinkedCells(Scanword scanword, int startRow, int startColumn, int level) {
			logger.debug("start execute method getListLinkedCells with parameters:"
					+ " row=" + startRow + " column=" + startColumn + " level=" + level);
			BinaryTree tree = ArrowUtils.getTree(scanword, startRow, startColumn, level);
			logger.debug("end execute method getTree");
			List<Object[]> resultList = ArrowUtils.selectListLinkedCells(tree.getListBranchesTree(tree.getRootNode(), new ArrayList<List<Object[]>>(), new ArrayList<Object[]>()));
			logger.debug("end execute method selectListLinkedCells.");
			if (resultList != null) {
				logger.debug("Result is list whith one line linked cells. "
							+ "Size list: " + resultList.size());
			}
			return resultList;
		}
		
		private static void fillLinkCell(Cell fCell, Cell sCell, String fCellLinkName, String sCellLinkName, int fDirectionIndex, int sDirectionIndex) {
			StringBuilder fBuf = new StringBuilder();
			StringBuilder sBuf = new StringBuilder();
			fBuf.append(sDirectionIndex);
			fBuf.append(".");
			fBuf.append(sDirectionIndex);
			fBuf.append(".");
			sBuf.append(fDirectionIndex);
			sBuf.append(".");
			if (sCellLinkName.equals("first")) {
				fBuf.append(1);
				sBuf.append(1);
				sCell.setFirstLink(sBuf.toString());
			}
			else {
				fBuf.append(3);
				sBuf.append(3);
				sCell.setSecondLink(sBuf.toString());
			}
			if (fCellLinkName.equals("first")) {
				fCell.setFirstLink(fBuf.toString());
			}
			else {
				fCell.setSecondLink(fBuf.toString());
			}
		}
		
		public static void redefineCellsLink(Scanword scanword, List<Object[]> list) {
			logger.debug("start execute method redefineCellsLink");
			Object[] currentObj = null;
			Cell currentCell = null;
			Object[] nextObj = null;
			Cell nextCell = null;
			for(int index = 0; index < list.size()-1; index+=2) {
				currentObj = list.get(index);
				currentCell = scanword.getArrayElement((Integer)currentObj[0], (Integer)currentObj[1]);
				nextObj = list.get(index+1);
				nextCell = scanword.getArrayElement((Integer)nextObj[0],(Integer)nextObj[1]);
				int directionIndex =  LinkUtils.transformateDiffCoordinateToDirectionIndex((Integer)currentObj[0]-(Integer)nextObj[0], (Integer)currentObj[1] - (Integer)nextObj[1]);
				int oppositeDirectionIndex = LinkUtils.oppositeDirectionIndex(directionIndex);
				fillLinkCell(currentCell, nextCell, String.valueOf(currentObj[3]), String.valueOf(nextObj[3]), directionIndex, oppositeDirectionIndex);
			}
			
			if (list.size()%2 == 1) {
				Object[] lastObj = list.get(list.size()-1);
				Cell cell = scanword.getArrayElement((Integer)lastObj[0], (Integer)lastObj[1]);
				if (lastObj[3].equals("first")) {
					cell.setFirstLink("9.9.9");
				}
				else {
					cell.setSecondLink("9.9.9");
				}
			}
		}
	}
	
	static class ScanwordUtil {
		
		private static void changeFilledLinks(Scanword scanword) {
			logger.debug("start execute method changeFilledLinks");
			for (int row = 0; row < scanword.getRows(); row ++) {
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
			logger.debug("start execute method removeAllFreeLinks");
			for (int row = 0; row < scanword.getRows(); row++) {
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
			logger.debug("start execute method nextStepRedefineArrow");
			List<Object[]> list = null;
			for (int row = scanword.getRows()-1; row >= 0; row--) {
				for (int column = scanword.getColumns()-1; column >= 0; column--) {
					list = ArrowUtils.getListLinkedCells(scanword, row, column, level);
					logger.debug("end execute method getListLinkedCells");
					if (list != null && list.size() > 0) {
						ArrowUtils.redefineCellsLink(scanword, list);
						logger.debug("end execute method redefineCellsLink");
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
	
	public static boolean defineArrowsForCommentCells(Scanword scanword, int level) {
		logger.debug("start execute method defineArrowsForCommentCells "
				+ "for scanword: name=" + scanword.getName() + " rows=" + scanword.getRows()
				+ " columns=" + scanword.getColumns());
		ArrowUtils.firstStepDefineArrows(scanword.getArray());
		logger.debug("end execute method firstStepDefineArrows");
		ArrowUtils.secondStepDefineArrows(scanword.getArray());
		logger.debug("end execute method secondStepDefineArrows");		
		for (int index = 1; index < level; index++) {
			ScanwordUtil.nextStepRedefineArrows(scanword, index);
			logger.debug("end execute method nextStepRedefineArrow");
		}
		ScanwordUtil.removeAllFreeLinks(scanword);
		logger.debug("end execute method removeAllFreeLinks");
		ScanwordUtil.changeFilledLinks(scanword);
		logger.debug("end execute method changeFilledLinks");
		logger.debug("end execute method defineArrowsForCommentCells for scanword: name=" + scanword.getName());
		return true;
	}
			
	public static Map<String, List<String>> getWords(Scanword scanword) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		
		return map;
	}
}