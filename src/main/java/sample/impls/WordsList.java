package sample.impls;

import sample.impls.cell.*;
import sample.interfaces.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by VAUst on 07.01.2017.
 */
public class WordsList {
	private Map<String, List<String>> words;
    public List<Scanword> scanwords;
    
    private static enum Direction {
    	VERTICAL, HORIZONTAL;
    }
    
    private static class ScanwordUtil {    	
    
    	private static Map<String, Integer[][]> positions = null;
       
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
    
    	private static boolean isNotDisableOrCommentCell(Cell cell) {
	   	if (cell instanceof DisableCell 
	  			|| cell instanceof CommentCell) {
	   		return false;
	   	}
	   	else {
	   		return true;
	   	}
	}
	    
	private static void changeCellToActiveCell(Scanword scanword, int indexR, int indexC, Direction direction) {
		Cell cell = scanword.getArrayElement(indexR, indexC);
		if (!(cell instanceof ActiveCell)) {
	   		cell = new ActiveCell();
	   		cell.setLetter(scanword.getArrayElement(indexR, indexC).getLetter());
	   	}
	   	switch(direction) {
   		case VERTICAL:
   			((ActiveCell)cell).setVDirection(true);
   			cell.setSecondLink("0.0");
   			break;
   		case HORIZONTAL:
   			((ActiveCell)cell).setHDirection(true);
   			cell.setFirstLink("0.0");
   			break;
   		default:
   			break;
   		}
	   	scanword.setArrayElement(cell, indexR, indexC);
	  }
	    
	private static boolean changeToActiveCell(Scanword scanword,int indexR, int indexC) {
		boolean label = false;
	   	if (indexR == 0) {
	   		if (isNotDisableOrCommentCell(scanword.getArrayElement(indexR, indexC))
	   				&& isNotDisableOrCommentCell(scanword.getArrayElement(indexR+1, indexC))) {
	   			changeCellToActiveCell(scanword, indexR, indexC, Direction.VERTICAL);
	   			label = true;
	   		}
	   	}
	   	if (indexC == 0) {
	   		if (isNotDisableOrCommentCell(scanword.getArrayElement(indexR, indexC))
	   				&& isNotDisableOrCommentCell(scanword.getArrayElement(indexR, indexC+1))) {
	   			changeCellToActiveCell(scanword, indexR, indexC, Direction.HORIZONTAL);
	   			label = true;
	   		}
	   	}
	   	if (indexR > 0 && indexR < scanword.getRow()-1) {
	   		if (!isNotDisableOrCommentCell(scanword.getArrayElement(indexR-1, indexC))
	   				&& isNotDisableOrCommentCell(scanword.getArrayElement(indexR, indexC))
	   				&& isNotDisableOrCommentCell(scanword.getArrayElement(indexR+1, indexC))) {
	   			changeCellToActiveCell(scanword, indexR, indexC, Direction.VERTICAL);
	   			label = true;
	   		}
	   	}
	   	if (indexC > 0 && indexC < scanword.getColumns()-1) {
	   		if (!isNotDisableOrCommentCell(scanword.getArrayElement(indexR, indexC-1))
	   				&& isNotDisableOrCommentCell(scanword.getArrayElement(indexR, indexC))
	   				&& isNotDisableOrCommentCell(scanword.getArrayElement(indexR, indexC+1))) {
	   			changeCellToActiveCell(scanword, indexR, indexC, Direction.HORIZONTAL);
	   			label = true;
	   		}
	   	}
	   	if (label) {
	   		return true;
	   	}
	   	else {
	   		return false;
	   	}
	  }
	   	
	public static boolean defineCells (Scanword scanword) {
		for (int row = 0; row < scanword.getRow(); row++) {
			for (int column = 0; column < scanword.getColumns(); column++) {
				changeToActiveCell(scanword, row, column);
			}
		}
		return true;
	}

    	
    	static {
//    	    	
//
//    	
//    	
//    	private static void setMapByPositionsNeighboursCells(Map<Integer, Cell> map, Scanword scanword, int row, int column,
//    			String cellPosition, Class<? extends Cell> neighbourCellClass) {
//    		for (int index = 0; index < positions.get(cellPosition).length; index++) {
//    			Cell cell = scanword.getArrayElement(row + positions.get(cellPosition)[index][0], 
//						 column + positions.get(cellPosition)[index][1]);
//				if (neighbourCellClass.isAssignableFrom(cell.getClass())) {
//					map.put(Integer.valueOf(index+1), 
//							scanword.getArrayElement(row + positions.get(cellPosition)[index][0],
//													 column + positions.get(cellPosition)[index][1]));
//				}
//			}
//    	}
//    	   	
//    	private static Map<Integer, Cell> getNeighbourCells(Scanword scanword, int indexR, int indexC, 
//    			Class<? extends Cell> currentCellClass, Class<? extends Cell> neighbourCellClass) {
//    		Map<Integer, Cell> map = new HashMap<Integer, Cell> ();
//    		if (scanword.getArrayElement(indexR, indexC).getClass().isAssignableFrom(currentCellClass)) {
//    			if (indexR > 0 && indexR < scanword.getRow()-1 && indexC > 0 && indexC < scanword.getColumns()-1) {
//    				setMapByPositionsNeighboursCells(map, scanword, indexR, indexC, "central", neighbourCellClass);
//    			}
//    			else if (indexR == 0 && indexC == 0) {
//    				setMapByPositionsNeighboursCells(map, scanword, indexR, indexC, "leftTop", neighbourCellClass);
//    			}
//    			else if (indexR == 0 && indexC == scanword.getColumns()-1) {
//    				setMapByPositionsNeighboursCells(map, scanword, indexR, indexC, "rightTop", neighbourCellClass);
//    			}
//    			else if (indexR == scanword.getRow()-1 && indexC == 0) {
//    				setMapByPositionsNeighboursCells(map, scanword, indexR, indexC, "leftBottom", neighbourCellClass);
//    			}
//    			else if (indexR == scanword.getRow()-1 && indexC == scanword.getColumns()-1) {
//    				setMapByPositionsNeighboursCells(map, scanword, indexR, indexC, "rightBottom", neighbourCellClass);
//    			}
//    			if (indexR == 0 && indexC > 0 && indexC < scanword.getColumns()-1) {
//    				setMapByPositionsNeighboursCells(map, scanword, indexR, indexC, "top", neighbourCellClass);
//    			}
//    			else if (indexR == scanword.getRow()-1 && indexC > 0 && indexC < scanword.getColumns()-1) {
//    				setMapByPositionsNeighboursCells(map, scanword, indexR, indexC, "bottom", neighbourCellClass);
//    			}
//    			else if (indexR > 0 && indexR < scanword.getRow()-1 && indexC == 0) {
//    				setMapByPositionsNeighboursCells(map, scanword, indexR, indexC, "left", neighbourCellClass);
//    			}
//    			else if (indexR > 0 && indexR < scanword.getRow()-1 && indexC == scanword.getColumns()-1) {
//    				setMapByPositionsNeighboursCells(map, scanword, indexR, indexC, "right", neighbourCellClass);
//    			}
//    		} 		
//    		return map;
//    	}
//    	
//        private static void setLink (Cell cell, String link) {
//        	String freeValueLink = "";
//        	if (cell instanceof ActiveCell) {
//        		freeValueLink = "0.0";
//        	}
//        	else if (cell instanceof CommentCell) {
//        		freeValueLink = "0.0.0";
//        	}
//        	else {
//        		freeValueLink = "";
//        	}
//        	if (cell.getCountFreeLink()>0 
//        			&& (!cell.getFirstLink().equals(link) 
//        					&& !cell.getSecondLink().equals(link))) {
//        		if (cell.getFirstLink().equals(freeValueLink)) {
//        			cell.setFirstLink(link);
//        		}
//        		else {
//        			cell.setSecondLink(link);
//        		}
//        	}
//        }
//        
//        private static void setLinkInCell (Integer index, Cell currentCell, Cell neighbourCell) {
//        	switch(index) {
//        	case 1:
//        		if (currentCell instanceof ActiveCell) {
//        			setLink(currentCell,"1.3");
//        			setLink(neighbourCell, "5.5.3");
//        		}
//        		else if (currentCell instanceof CommentCell) {
//        			if (((ActiveCell)neighbourCell).getHDirection()) {
//        				setLink(currentCell, "1.1.1");
//        				setLink(neighbourCell, "5.1");
//        			}
//        			else if (((ActiveCell)neighbourCell).getVDirection()) {
//        				setLink(currentCell, "1.1.3");
//        				setLink(neighbourCell, "5.3");
//        			}
//        		}
//        		break;
//        	case 2:
//        		if (currentCell instanceof ActiveCell) {
//        			if (((ActiveCell)currentCell).getHDirection()) {
//        				setLink(currentCell, "2.1");
//        				setLink(neighbourCell, "6.6.1");
//        			}
//        			else if (((ActiveCell)currentCell).getVDirection()) {
//        				setLink(currentCell, "2.3");
//        				setLink(neighbourCell, "6.6.3");
//        			}
//        		}
//        		else if (currentCell instanceof CommentCell) {
//        			if (((ActiveCell)neighbourCell).getHDirection()) {
//        				setLink(currentCell, "2.2.1");
//        				setLink(neighbourCell, "6.1");
//        			}
//        			if (((ActiveCell)neighbourCell).getVDirection()) {
//        				setLink(currentCell, "2.2.3");
//        				setLink(neighbourCell, "6.3");
//        			}
//        		}
//        		break;
//        	case 3:
//        		if (currentCell instanceof ActiveCell) {
//        			setLink(currentCell, "3.1");
//        			setLink(neighbourCell, "7.7.1");
//        		}
//        		else if (currentCell instanceof CommentCell) {
//        			if (((ActiveCell)neighbourCell).getHDirection()) {
//        				setLink(currentCell,"3.3.1");
//        				setLink(neighbourCell,"7.1");
//        			}
//        			if (((ActiveCell)neighbourCell).getVDirection()) {
//        				setLink(currentCell,"3.3.3");
//        				setLink(neighbourCell,"7.3");
//        			}
//        		}
//        		break;
//        	case 4:
//        		if (currentCell instanceof ActiveCell) {
//        			if (((ActiveCell)currentCell).getHDirection()) {
//        				setLink(currentCell,"4.1");
//        				setLink(neighbourCell,"8.8.1");
//        			}
//        			else if (((ActiveCell)currentCell).getVDirection()) {
//        				setLink(currentCell,"4.3");
//        				setLink(neighbourCell,"8.8.3");
//        			}
//        		}
//        		else if (currentCell instanceof CommentCell) {
//        			if (((ActiveCell)neighbourCell).getHDirection()) {
//        				setLink(currentCell,"4.4.1");
//        				setLink(neighbourCell,"8.1");
//        			}
//        			else if (((ActiveCell)neighbourCell).getVDirection()) {
//        				setLink(currentCell,"4.4.3");
//        				setLink(neighbourCell,"8.3");
//        			}
//        		}
//				break;
//        	case 5:
//        		if (currentCell instanceof ActiveCell) {
//        			if (((ActiveCell)currentCell).getHDirection()) {
//        				setLink(currentCell,"5.1");
//        				setLink(neighbourCell,"1.1.1");
//        			}
//        			else if (((ActiveCell)currentCell).getVDirection()) {
//        				setLink(currentCell,"5.3");
//        				setLink(neighbourCell,"1.1.3");
//        			}
//        		}
//        		else if (currentCell instanceof CommentCell) {
//       				setLink(currentCell,"5.5.3");
//       				setLink(neighbourCell,"1.3");
//        		}
//				break;
//        	case 6:
//        		if (currentCell instanceof ActiveCell) {
//        			if (((ActiveCell)currentCell).getHDirection()) {
//        				setLink(currentCell,"6.1");
//        				setLink(neighbourCell,"2.2.1");
//        			}
//        			else if (((ActiveCell)currentCell).getVDirection()) {
//        				setLink(currentCell,"6.3");
//        				setLink(neighbourCell,"2.2.3");
//        			}
//        		}
//        		else if (currentCell instanceof CommentCell) {
//        			if (((ActiveCell)neighbourCell).getHDirection()) {
//        				setLink(currentCell,"6.6.1");
//        				setLink(neighbourCell,"2.1");
//        			}
//        			else if (((ActiveCell)neighbourCell).getVDirection()) {
//        				setLink(currentCell,"6.6.3");
//        				setLink(neighbourCell,"2.3");
//        			}
//        		}
//				break;
//        	case 7:
//        		if (currentCell instanceof ActiveCell) {
//        			if (((ActiveCell)currentCell).getHDirection()) {
//        				setLink(currentCell,"7.1");
//        				setLink(neighbourCell,"3.3.1");
//        			}
//        			else if (((ActiveCell)currentCell).getVDirection()) {
//        				setLink(currentCell,"7.3");
//        				setLink(neighbourCell,"3.3.3");
//        			}
//        		}
//        		else if (currentCell instanceof CommentCell) {
//       				setLink(currentCell,"7.7.1");
//       				setLink(neighbourCell,"3.1");
//        		}
//				break;
//        	case 8:
//        		if (currentCell instanceof ActiveCell) {
//        			if (((ActiveCell)currentCell).getHDirection()) {
//        				setLink(currentCell,"8.8.1");
//        				setLink(neighbourCell,"4.1");
//        			}
//        			else if (((ActiveCell)currentCell).getVDirection()) {
//        				setLink(currentCell,"8.8.3");
//        				setLink(neighbourCell,"4.3");
//        			}
//        		}
//        		else if (currentCell instanceof CommentCell) {
//        			if (((ActiveCell)neighbourCell).getHDirection()) {
//        				setLink(currentCell,"8.8.1");
//        				setLink(neighbourCell,"4.1");
//        			}
//        			else if (((ActiveCell)neighbourCell).getVDirection()) {
//        				setLink(currentCell,"8.8.3");
//        				setLink(neighbourCell,"4.3");
//        			}
//        		}
//				break;
//        		default:
//        			break;
//        	}
//        }
//        
//        private static void fillNotusedLinks(Scanword scanword) {
//        	for (int indexR = 0; indexR < scanword.getRow(); indexR++) {
//        		for (int indexC = 0; indexC < scanword.getColumns(); indexC++) {
//        			Cell currentCell = scanword.getArrayElement(indexR, indexC);
//        			if (currentCell instanceof CommentCell) {
//        				Map<Integer, Cell> neighbourActiveCells = getNeighbourCells(scanword, indexR, indexC, CommentCell.class, ActiveCell.class);
//        				if (neighbourActiveCells.size() == 1) {
//        					for (Integer key: neighbourActiveCells.keySet()) {
//        						if (neighbourActiveCells.get(key).getCountFreeLink() == 0) {
//        							setLink(currentCell,"9.9.9");
//        						}
//        					}
//        				}
//        			}
//        		}
//        	}
//        }
//        
//        private static void secondStepForDependingLinks(Scanword scanword) {
//        	for (int indexR = scanword.getRow()-1; indexR >= 0; indexR--) {
//        		for (int indexC = scanword.getColumns()-1; indexC >= 0; indexC--) {
//        			Cell currentCell = scanword.getArrayElement(indexR, indexC);
//        			if (currentCell instanceof CommentCell) {
//        				Map<Integer, Cell> neighbourActiveCells = getNeighbourCells(scanword, indexR, indexC, CommentCell.class, ActiveCell.class);
//        				if (neighbourActiveCells.size() == 2) {
//    					int count = 0;
//    					Integer value = null;
//    					Cell cell = null;
//    					for (Integer key: neighbourActiveCells.keySet()) {
//    						if (neighbourActiveCells.get(key).getCountFreeLink() == 2) {
//    							count++;
//    							value = key;
//    							cell = neighbourActiveCells.get(key);
//    						}
//    					}
//    					if (count == 1) {
//    						setLinkInCell(value, cell, neighbourActiveCells.get(value));
//    					}
//    				}
//        				
//        			}
//        			else if (currentCell instanceof ActiveCell) {
//        				Map<Integer, Cell> neighbourCommentCells = getNeighbourCells(scanword, indexR, indexC, ActiveCell.class, CommentCell.class);
//        				if (neighbourCommentCells.size() == 2) {
//        					int count = 0;
//        					Integer value = null;
//        					Cell cell = null;
//        					if (currentCell.getCountFreeLink() == 2) {
//        						for(Integer key: neighbourCommentCells.keySet()) {
//        							setLinkInCell(key, currentCell, neighbourCommentCells.get(key));
//        						}
//        					}
//        					for (Integer key: neighbourCommentCells.keySet()) {
//        						if (neighbourCommentCells.get(key).getCountFreeLink() == 2) {
//        							count++;
//        							value = key;
//        							cell = neighbourCommentCells.get(key);
//        						}
//        					}
//        					if (count == 1) {
//        						setLinkInCell(value, currentCell, cell);
//        					}
//        				}
//        				
//        			}
//        		}
//        	}
//        }
//        
//        private static void firstStepIndefinindCellsLink(Scanword scanword) {
//        	for (int indexR = 0; indexR < scanword.getRow(); indexR++) {
//        		for (int indexC = 0; indexC < scanword.getColumns(); indexC++) {
//        			Cell currentCell = scanword.getArrayElement(indexR, indexC);
//        			if (currentCell instanceof CommentCell) {
//        				Map<Integer, Cell> neighbourActiveCells = getNeighbourCells(scanword, indexR, indexC, CommentCell.class, ActiveCell.class);
//        				if (neighbourActiveCells.size() == 1) {
//        					for (Integer key: neighbourActiveCells.keySet()) {
//        						if (neighbourActiveCells.get(key).getCountFreeLink() == 1) {
//        							setLinkInCell(key, currentCell, neighbourActiveCells.get(key));
//        						}
//        					}
//        				}
//        			}
//        			else if (currentCell instanceof ActiveCell) {
//        				Map<Integer, Cell> neighbourCommentCells = getNeighbourCells(scanword, indexR, indexC, ActiveCell.class, CommentCell.class);
//        				if (neighbourCommentCells.size() == 1) {
//        					Collection<Integer> keys = neighbourCommentCells.keySet();
//        					if (currentCell.getCountFreeLink() == 1) {
//        						for (Integer key:keys) {
//        							setLinkInCell(key, currentCell, neighbourCommentCells.get(key));
//        						}
//        					}
//        				}
//        			}
//        		}
//        	}
//        	
//        }
//        
//        private static int getCountFreeLinks(Scanword scanword) {
//        	int count = 0;
//        	for (int row = 0; row < scanword.getRow(); row++) {
//        		for (int column = 0; column < scanword.getColumns(); column++) {
//        			Cell cell = scanword.getArrayElement(row, column);
//        			if (cell instanceof ActiveCell || cell instanceof CommentCell) {
//        				count += cell.getCountFreeLink();
//        			}
//        		}
//        	}
//        	return count;
//        }
//    	
//    	public static boolean defineArrowsInCells(Scanword scanword) {
//
//			firstStepIndefinindCellsLink(scanword);
//
//			
//    		int count = getCountFreeLinks(scanword);
//    		int delta = count - 0;
//    		while (delta > 0) {
//    			count = getCountFreeLinks(scanword);
//    			secondStepForDependingLinks(scanword);
//    			delta = count - getCountFreeLinks(scanword);
//    			System.out.println("count:"+count+" current:"+getCountFreeLinks(scanword)+" delta:"+delta);
//    			fillNotusedLinks(scanword);
//    		}
//    		return true;
//    	}
    	}
    	
    	private static boolean compareCells(Cell initCell, Cell templateCell) {
    		if (initCell.getCountFreeLink() == templateCell.getCountFreeLink()) {
    			
    		}
    	}
    	
    	private static boolean compateArrays (Cell[][] array, Cell[][] template) {
    		boolean flag = true;
    		for (int row = 0; row < array.length; row++) {
    			for (int column = 0; column < array[0].length; column++) {
    				if (array[row][column].getClass().getName().equals(template[row][column].getClass().getName())) {
    					flag &= true;
    				}
    			}
    		}
    		if (flag) {
    			return true;
    		}
    		else {
    			return false;
    		}
    	}
    	
    	private static void modefiScanword(Scanword scanword, Cell[][] template) {
    		for (int row = 0; row < scanword.getRow()-template.length; row++) {
    			for (int column = 0; column < scanword.getColumns()-template[0].length; column++) {
    				Cell[][] array = new Cell[template.length][template[0].length];
    				for (int iRow = 0; iRow < template.length; iRow++) {
    					for (int iColumn = 0; iColumn < template[0].length; iColumn++) {
    						array[iRow][iColumn] = scanword.getArrayElement(row+iRow, column+iColumn);
    					}
    				}
    				compareArrays(array, template);
    			}
    		}
    	}
    	
    }

    private static class WordsUtil {

    	public static List<String> getWords (Scanword scanword) {
        	List<String> list = new LinkedList<>(getHorizontalList(scanword));
        	list.addAll(getVerticalList(scanword));
            return list;
        }

        private static List<String> getHorizontalList (Scanword scanword) {
        	List<String> list = new LinkedList<String> ();
        	for(int indexR = 0; indexR < scanword.getRow(); indexR++) {
        		for (int indexC = 0; indexC < scanword.getColumns(); indexC++) {
        			Cell cell = scanword.getArrayElement(indexR, indexC);
        			if (cell instanceof ActiveCell && ((ActiveCell)cell).getHDirection()) {
        				list.add(((ActiveCell) cell).getHWordLink());
        			}
        		}
        	}
        	return list;
        }
        
        private static List<String> getVerticalList (Scanword scanword) {
        	List<String> list = new LinkedList<String> ();
        	for(int indexC = 0; indexC < scanword.getColumns(); indexC++) {
        		for (int indexR = 0; indexR < scanword.getRow(); indexR++) {
        			Cell cell = scanword.getArrayElement(indexR, indexC);
        			if (cell instanceof ActiveCell && ((ActiveCell) cell).getVDirection()) {
        				list.add(((ActiveCell) cell).getVWordLink());
        			}
        		}
        	}
        	return list;
        }
    
    }
    
    public WordsList () {
        scanwords = new LinkedList<Scanword>();
        words = new HashMap<String, List<String>>();
    }
    
    public WordsList (List<Scanword> list) {
    	this();
    	
        scanwords = list.stream()
        			.filter(s->ScanwordUtil.defineCells(s))
        			.filter(s->ScanwordUtil.defineArrowsInCells(s))
        			.collect(Collectors.toList());
        for (Scanword scanword: scanwords) {
            words.put(scanword.getName(), WordsUtil.getWords(scanword));
        }
    }

    
    public void setScanwords (List<Scanword> list) {
        scanwords = list.stream()
        			.filter(s -> ScanwordUtil.defineCells(s))
        			.filter(s -> ScanwordUtil.defineArrowsInCells(s))
        			.collect(Collectors.toList());
        for (Scanword scanword: scanwords) {
            words.put(scanword.getName(), WordsUtil.getWords(scanword));
        }
    }
    
    public void setScanword (int index, Scanword scanword) {
        if (scanwords != null && (index >= 0 && index < scanwords.size())) {
            scanwords.set(index, scanword);
            words.remove(scanword.getName());
            words.put(scanword.getName(), WordsUtil.getWords(scanword));
        }
    }
    
    public List<String> getWordsByPatternAndCategory (String pattern, String Category) {
        return null;
    }

	
    public Map<String, List<String>> getWords () {
        return words;
    }
}