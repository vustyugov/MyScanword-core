package sample.utils;

import java.util.*;

import org.apache.log4j.Logger;

import sample.impls.cell.*;
import sample.impls.tree.*;
import sample.interfaces.*;

public final class ArrowUtils {
	private static Logger logger = Logger.getLogger(ArrowUtils.class.getName());
	protected static Map<String, Integer[]> positions;
	
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
	
	private static BinaryTree getTreeWithCommentCellInRoot(Scanword scanword, int startRow, int startColumn, int level) {
		logger.debug("start execute method getTree. "
				+ "Creation tree, having pich with coordinate: "
				+ "row=" + startRow + " column=" + startColumn);
		BinaryTree tree = new BinaryTree();
			
		tree.insert(null, new Node ("0.0.0","first" , startRow, startColumn));
			
		Node[] nodes = tree.getLeavesTree(tree.getRootNode(), new Node[0], 0);
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
					if (CellUtils.isType(cell, CommentCell.class)
								&& cell.getCountAvailableLink() == 2
								&& cell.getCountFreeLink() == 0) {
							flag = true;
							break;
					}
					if (CellUtils.isType(cell, ActiveCell.class)
							&& cell.getCountFreeLink() > 0) {
						flag = true;
						break;
					}
					
					List<Integer> indexes = null;
					if (CellUtils.isType(cell, CommentCell.class)) {
						 indexes = LinkUtils.getCountNeighbourCellsOtherType(scanword.getArray(), 
								 node.getRowIndex(), node.getColumnIndex(), CommentCell.class, ActiveCell.class);
					}
					else if (CellUtils.isType(cell, ActiveCell.class)) {
						indexes = LinkUtils.getCountNeighbourCellsOtherType(scanword.getArray(), 
								node.getRowIndex(), node.getColumnIndex(), ActiveCell.class, CommentCell.class);
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
			nodes = tree.getLeavesTree(tree.getRootNode(), new Node[0], 0);
		}
		return tree;
	}
	
	private static BinaryTree getTreeWithActiveCellInRoot(Scanword scanword, int startRow, int startColumn, int level) {
		logger.debug("start execute method getTree. "
				+ "Creation tree, having pich with coordinate: "
				+ "row=" + startRow + " column=" + startColumn);
		BinaryTree tree = new BinaryTree();
		Cell startCell = scanword.getArrayElement(startRow, startColumn);
		tree.insert(null, new Node ("0.0", (startCell.getFirstLink().equals("0.0"))?"first":"second", startRow, startColumn));
			
		Node[] nodes = tree.getLeavesTree(tree.getRootNode(), new Node[0], 0);
		int counter = -1;
		boolean flag = false;
		while (!flag & nodes.length != 0 & ++counter < level) {
			for (Node node:nodes) {
				Cell cell = null;
				if (node == null) {
					continue;
				}
				else {
					cell = scanword.getArrayElement(node.getRowIndex(), node.getColumnIndex());
					if (CellUtils.isType(cell, CommentCell.class)
							&& cell.getCountAvailableLink() == 2
								&& cell.getCountFreeLink() > 0) {
						flag = true;
						break;
					}
					List<Integer> indexes = null;
					if (CellUtils.isType(cell, CommentCell.class)) {
						 indexes = LinkUtils.getCountNeighbourCellsOtherType(scanword.getArray(), 
								 node.getRowIndex(), node.getColumnIndex(), CommentCell.class, ActiveCell.class);
					}
					else if (CellUtils.isType(cell, ActiveCell.class)) {
						indexes = LinkUtils.getCountNeighbourCellsOtherType(scanword.getArray(), 
								node.getRowIndex(), node.getColumnIndex(), ActiveCell.class, CommentCell.class);
					}
					for (int index = 0 ; index < indexes.size(); index++) {
						int[] positionNeighboureCell = LinkUtils.transformateDirectionIndexToDiffCoordinateCell(indexes.get(index));
						Cell newCell = scanword.getArrayElement(node.getRowIndex()+positionNeighboureCell[0], node.getColumnIndex()+positionNeighboureCell[1]);
						if (!(newCell.getFirstLink().equals("9.9.9")
								|| newCell.getFirstLink().equals("9.9"))) { 
								Node newNode = new Node(newCell.getFirstLink(), "first", 
									node.getRowIndex()+positionNeighboureCell[0], 
									node.getColumnIndex()+positionNeighboureCell[1]);
							if (CellUtils.isType(newCell, CommentCell.class) 
									& newCell.getCountAvailableLink() == 2) {
								if (newCell.getCountFreeLink() > 0) {
									newNode.setFinishedNode(true);
								}
								if (node.getParent() == null) {
									tree.insert(node, newNode);
								}
								else {
									if (!node.getParent().equals(newNode)) {
										tree.insert(node, newNode);
									}
								}
							}
							if (CellUtils.isType(newCell, ActiveCell.class)) {
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
						if (!(newCell.getSecondLink().equals("9.9.9")
								|| newCell.getSecondLink().equals("9.9"))) { 
								Node newNode = new Node(newCell.getSecondLink(), "second", 
									node.getRowIndex()+positionNeighboureCell[0], 
									node.getColumnIndex()+positionNeighboureCell[1]);
							if (CellUtils.isType(newCell, CommentCell.class) 
									& newCell.getCountAvailableLink() == 2) {
								if (newCell.getCountFreeLink() > 0) {
									newNode.setFinishedNode(true);
								}
								if (node.getParent() == null) {
									tree.insert(node, newNode);
								}
								else {
									if (!node.getParent().equals(newNode)) {
										tree.insert(node, newNode);
									}
								}
							}
							if (CellUtils.isType(newCell, ActiveCell.class)) {
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
			}
			if (counter > 1) {
				for (int index = counter-1; index >= 0; index--) {
					tree.removeAllLeavesByLevel(counter-1);
				}
			}
			nodes = tree.getLeavesTree(tree.getRootNode(), new Node[0], 0);
		}
		return tree;

	}
	
	private static BinaryTree getTree(Scanword scanword, int startRow, int startColumn, int level) {
		BinaryTree tree = new BinaryTree();
		Cell rootCell = scanword.getArrayElement(startRow, startColumn);
		if (CellUtils.isType(rootCell, CommentCell.class)
				&& rootCell.getCountFreeLink() == 2) {
			tree = getTreeWithCommentCellInRoot(scanword, startRow, startColumn, level);
		}
		else if (CellUtils.isType(rootCell, ActiveCell.class)
				&& rootCell.getCountFreeLink() > 0) {
			tree = getTreeWithActiveCellInRoot(scanword, startRow, startColumn, level);
		}
		else {
			return tree;
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
	private static List<Object[]> selectListLinkedCells(List<List<Object[]>> resultList, Cell rootCell) {
		logger.debug("start execute method selectListLinkedCells. "
				+ "Count lines from linked cells: " + resultList.size());
		removeBadBranches(resultList);
		
		if (resultList.size() == 0 || resultList == null) {
			return null;
		}
		
		if (CellUtils.isType(rootCell, ActiveCell.class)) {
			List<List<Object[]>> innerList = new LinkedList<List<Object[]>> ();
			for (int index = 0; index < resultList.size(); index++) {
				List<Object[]> list = resultList.get(index);
				boolean existLinks = true;
				for (int innerIndex = 1; innerIndex < list.size()-1; innerIndex += 2) {
					int fDirectionIndex = Integer.valueOf(((String)list.get(innerIndex)[2]).substring(0, 1));
					int sDirectionIndex = Integer.valueOf(((String)list.get(innerIndex+1)[2]).substring(0, 1));
					if (sDirectionIndex == LinkUtils.oppositeDirectionIndex(fDirectionIndex)) {
						existLinks &= true;
					}
					else {
						existLinks = false;
						continue;
					}
				}
				if (existLinks) {
					innerList.add(list);
				}
			}
			for (int index = 0; index < innerList.size(); index++) {
				List<Object[]> list = innerList.get(index);
				if(((String)list.get(list.size()-1)[2]).equals("0.0.0")) {
					return list;
				}
			}
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
	
	protected static int getWeight (String link) {
		logger.debug("start execute method getWeight. input link: " + link);
		if (link.equals("2.1") || link.equals("2.3") || link.equals("8.1") || link.equals("4.3")
				|| link.equals("6.6.1") || link.equals("8.8.3") || link.equals("6.6.3") || link.equals("4.4.1")) {
			return 4;
		}
		else if (link.equals("1.3") || link.equals("5.3") || link.equals("3.1") || link.equals("7.1")
				|| link.equals("1.1.3") || link.equals("5.5.3") || link.equals("3.3.1") || link.equals("7.7.1")) {
			return 3;
		}
		else if (link.equals("6.3") || link.equals("6.1") || link.equals("8.3") || link.equals("4.1")
				|| link.equals("2.2.3") || link.equals("2.2.1") || link.equals("4.4.3") || link.equals("8.8.1")) {
			return 2;
		}
		else if (link.equals("5.1") || link.equals("7.3") || link.equals("1.1.1") || link.equals("3.3.3")) {
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
		List<Object[]> resultList = ArrowUtils.selectListLinkedCells(tree.getListBranchesTree(tree.getRootNode(), new ArrayList<List<Object[]>>(), new ArrayList<Object[]>()), scanword.getArrayElement(startRow, startColumn));
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
		Cell rootCell = scanword.getArrayElement((Integer)list.get(0)[0], (Integer)list.get(0)[1]);
		if (CellUtils.isType(rootCell, ActiveCell.class)) {
			for (int index = 1; index < list.size()-1; index+=2) {
				Cell cCell = scanword.getArrayElement((Integer)list.get(index)[0], (Integer)list.get(index)[1]);
				Cell aCell = scanword.getArrayElement((Integer)list.get(index+1)[0], (Integer)list.get(index+1)[1]);
				LinkUtil.breakLinkBetweenTwoCells(cCell, Integer.valueOf(String.valueOf(list.get(index)[2]).substring(0,1)), 
						aCell, Integer.valueOf(String.valueOf(list.get(index+1)[2]).substring(0,1)));
			}
			for (int index = 0; index < list.size(); index+=2) {
				int aRow = (Integer)list.get(index)[0];
				int aColumn = (Integer)list.get(index)[1];
				int cRow = (Integer)list.get(index+1)[0];
				int cColumn = (Integer)list.get(index+1)[1];
				int directionCurrentCell = LinkUtils.transformateDiffCoordinateToDirectionIndex(cRow-aRow, cColumn-aColumn);
				int directionNeighbourCell = LinkUtils.oppositeDirectionIndex(directionCurrentCell);
				LinkUtil.createLinkBetweenTwoCells(scanword.getArrayElement(aRow, aColumn), directionCurrentCell, 
						scanword.getArrayElement(cRow, cColumn), directionNeighbourCell);
			}
		}
		else if (CellUtils.isType(rootCell, CommentCell.class)) {
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
					cell.setFirstLink("0.0.0");
				}
				else {
					cell.setSecondLink("0.0.0");
				}
			}
		}
	}
}
