package sample.utils;

import sample.impls.cell.*;
import sample.interfaces.Cell;

public final class LinkUtil {

	private static final int FIRST_LINK_CELL = 1;
	private static final int SECOND_LINK_CELL = 2;
	
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
 		
		if (CellUtils.isType(fCell, CommentCell.class) && CellUtils.isType(sCell, ActiveCell.class)) {
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
		if (CellUtils.isType(fCell, ActiveCell.class) && CellUtils.isType(sCell, CommentCell.class)) {
			if (fCell.getFirstLink().equals("0.0")) {
				fCell.setFirstLink(directionCurrentCell+".1");
				if (sCell.getFirstLink().equals("0.0.0")) {
					sCell.setFirstLink(directionNeighbourCell+"."+directionNeighbourCell+".1");
				}
				else if (sCell.getSecondLink().equals("0.0.0")) {
					sCell.setSecondLink(directionNeighbourCell+"."+directionNeighbourCell+".1");
				}
			}
			else if (fCell.getSecondLink().equals("0.0")) {
				fCell.setSecondLink(directionCurrentCell+".3");
				if (sCell.getFirstLink().equals("0.0.0")) {
					sCell.setFirstLink(directionNeighbourCell+"."+directionNeighbourCell+".3");
				}
				else if (sCell.getSecondLink().equals("0.0.0")) {
					sCell.setSecondLink(directionNeighbourCell+"."+directionNeighbourCell+".3");
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
