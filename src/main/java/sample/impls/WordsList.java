package sample.impls;

import sample.impls.cell.*;
import sample.interfaces.Cell;
import sample.interfaces.Scanword;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by VAUst on 07.01.2017.
 */
public class WordsList {

    private Map<String, List<String>> words;
    private List<Scanword> scanwords;
    private static final Integer[][] centralPositions = {{1, 0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};
    private static final Integer[][] leftPositions = {{1, 0},{1,1},{0,1},{0,-1},{1,-1}};
    private static final Integer[][] rightPositions = {{0,1},{-1,1},{-1,0},{-1,-1},{0,-1}};
    private static final Integer[][] topPositions = {{1, 0},{1,1},{0,1},{-1,1},{-1,0}};
    private static final Integer[][] bottomPositions = {{1, 0},{-1,0},{-1,-1},{0,-1},{1,-1}};
    private static final Integer[][] leftTopAngularPositions = {{1, 0},{1,1},{0,1}};
    private static final Integer[][] rightTopAngularPositions = {{0, 1},{-1,1},{-1,0}};
    private static final Integer[][] leftBottomAngularPositions = {{1, 0},{0,-1},{1,-1}};
    private static final Integer[][] rightBottomAngularPositions = {{-1, 0},{-1,-1},{0,-1}};
    
    public WordsList () {
        scanwords = new LinkedList<Scanword>();
        words = new HashMap<String, List<String>>();
    }
    
    public WordsList (List<Scanword> list) {
        scanwords = list.stream()
        			.filter(scanword->defineCellsInScanword(scanword))
        			.collect(Collectors.toList());
        for (Scanword scanword: scanwords) {
            words.put(scanword.getName(), getWordsByScanword(scanword));
        }
    }

    public void setScanwords (List<Scanword> list) {
        scanwords = list.stream()
        			.filter(s -> defineCellsInScanword(s))
        			.collect(Collectors.toList());
        for (Scanword scanword: scanwords) {
            words.put(scanword.getName(), getWordsByScanword(scanword));
        }
    }
    
    private boolean isDifferentNeighbourCells(Cell firstCell, Cell secondCell) {
    	if (firstCell instanceof ActiveCell && secondCell instanceof CommentCell
    			|| firstCell instanceof CommentCell && secondCell instanceof ActiveCell) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    private void setLink (Cell cell, String link) {
    	if (cell.getFirstLink().equals("") || cell.getSecondLink().equals("")
    			&& (!cell.getFirstLink().equals(link) && !cell.getSecondLink().equals(link))) {
    		if (cell.getFirstLink().equals("")) {
    			cell.setFirstLink(link);
    		}
    		else {
    			cell.setSecondLink(link);
    		}
    	}
    }
    
    private List<Integer[]> indexesValues (Scanword scanword, int row, int column, Integer[][] positions) {
    	List<Integer[]> indexes = new LinkedList<Integer[]> ();
    	for (Integer[] position:positions) {
			if (isDifferentNeighbourCells(scanword.getArrayElement(row, column), 
											scanword.getArrayElement(row+position[1], column+position[0]))) {
				Integer[] inPositions = {position[1]+row, position[0]+column};
				indexes.add(inPositions);
			}
		}
    	return indexes;
    }
    
    private List<Integer[]> getCellsAroundCurrentCell(Scanword scanword, int row, int column) {
    	if (scanword != null) {
    		if (row == 0 ) {
    			if (column == 0) { 
    				return indexesValues(scanword, row, column, leftTopAngularPositions);
    			}
    			else if (column == scanword.getColumns()-1) {
    				return indexesValues(scanword, row, column, rightTopAngularPositions);
    			}
    			else {
    				return indexesValues(scanword, row, column, topPositions);
    			}
    		}
    		else if (row == scanword.getRow()-1) {
    			if (column == 0) {
    				return indexesValues(scanword, row, column, leftBottomAngularPositions);
    			}
    			else if (column == scanword.getColumns()-1) {
    				return indexesValues(scanword, row, column, rightBottomAngularPositions);
    			}
    			else {
    				return indexesValues(scanword, row, column, bottomPositions);
    			}
    		}
    		else {
    			if (column == 0) {
    				return indexesValues(scanword, row, column, leftPositions);
    			}
    			else if (column == scanword.getColumns()-1) {
    				return indexesValues(scanword, row, column, rightPositions);
    			}
    			else {
    				return indexesValues(scanword, row, column, centralPositions);
    			}
    		}
    	}
    	return new LinkedList<Integer[]>();
    }
    
    private void defineCommentCellLinks(Scanword scanword) {
    	Thread right = new Thread(()->{
    		for (int indexR = 0; indexR < scanword.getRow(); indexR++) {
    			for (int indexC = 0; indexC < scanword.getColumns(); indexC++) {
    				List<Integer[]> indexes = getCellsAroundCurrentCell(scanword, indexR, indexC);
    				if (indexes.size()==1 && scanword.getArrayElement(indexR, indexC) instanceof CommentCell
    						&& indexes.get(0)[0]-indexR == 0 && indexes.get(0)[1] - indexC == 1) {
    					setLink(scanword.getArrayElement(indexR, indexC), "1.1.1");
    					setLink(scanword.getArrayElement(indexR, indexC-1),"5.5");
    				}
    			}
    		}
    	});
    	right.setName("CeationRightArrowForCommentCell");
    	
    	Thread bottom = new Thread(()->{
    		for (int indexR = 0; indexR < scanword.getRow(); indexR++) {
    			for (int indexC = 0; indexC < scanword.getColumns(); indexC++) {
    				List<Integer[]> indexes = getCellsAroundCurrentCell(scanword, indexR, indexC);
    				if (indexes.size()==1 && scanword.getArrayElement(indexR, indexC) instanceof CommentCell
    						&& indexes.get(0)[0]-indexR == 1 && indexes.get(0)[1] - indexC == 0) {
    					setLink(scanword.getArrayElement(indexR, indexC), "3.3.3");
    					setLink(scanword.getArrayElement(indexR-1, indexC), "7.7");
    				}
    			}
    		}
    	});
    	bottom.setName("CeationBottomArrowForCommentCell");
    	
    	right.start();
    	bottom.start();
    }
    
    private boolean defineCellsInScanword(Scanword scanword) {
    	defineCommentCellLinks(scanword);
   		return true;
    }

    public void setScanword (int index, Scanword scanword) {
        if (scanwords != null && (index >= 0 && index < scanwords.size())) {
            scanwords.set(index, scanword);
            words.remove(scanword.getName());
            words.put(scanword.getName(), getWordsByScanword(scanword));
        }
    }

    public List<String> getWordsByPatternAndCategory (String pattern, String Category) {
        return null;
    }

    public Map<String, List<String>> getWords () {
        return words;
    }

    private List<String> getWordsByScanword (Scanword scanword) {
    	List<String> list = new LinkedList<>(getHorizontalList(scanword));
    	list.addAll(getVerticalList(scanword));
        return list;
    }

    private List<String> getHorizontalList (Scanword scanword) {
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
    
    private List<String> getVerticalList (Scanword scanword) {
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