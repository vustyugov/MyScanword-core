package sample.utils;

import java.util.*;

import org.apache.log4j.Logger;

import sample.impls.cell.*;
import sample.interfaces.*;

public final class ScanwordUtil {
	private static final int ACTIVE_CELL_INDEX = 0;
	private static final int COMMENT_CELL_INDEX = 1;
	private static Logger logger = Logger.getLogger(ScanwordUtil.class.getName());
	
	protected static void changeFilledLinks(Scanword scanword) {
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
					if (cell.getFirstLink().equals("4.1")
							&& cell.getSecondLink().equals("2.3")) {
						Cell ldCell = scanword.getArrayElement(row+1, column-1);
						Cell rdCell = scanword.getArrayElement(row+1, column+1);
						if (ldCell.getFirstLink().equals("8.8.1")) {
							ldCell.setFirstLink("1.8.1.1");
							if (rdCell.getFirstLink().equals("6.6.3")) {
								rdCell.setFirstLink("7.6.3.2");
							}
							else if (rdCell.getSecondLink().equals("6.6.3")) {
								rdCell.setSecondLink("7.6.3.2");
							}
						}
						else if (ldCell.getSecondLink().equals("8.8.1")) {
							ldCell.setSecondLink("1.8.1.1");
							if (rdCell.getFirstLink().equals("6.6.3")) {
								rdCell.setFirstLink("7.6.3.2");
							}
							else if (rdCell.getSecondLink().equals("6.6.3")) {
								rdCell.setSecondLink("7.6.3.2");
							}
						}
					}
				}
				else if (cell instanceof CommentCell) {
					if (row > 0 && row < scanword.getRows()-1 && column < scanword.getColumns()-1) {
						Cell sCCell = scanword.getArrayElement(row, column+1);
						Cell fACell = scanword.getArrayElement(row-1, column+1);
						Cell sACell = scanword.getArrayElement(row+1, column);
						int initDirectionIndexForCurrentCell = LinkUtils.transformateDiffCoordinateToDirectionIndex(-1, 1);
						int initDirectionIndexForSCCell = LinkUtils.transformateDiffCoordinateToDirectionIndex(1, -1);
						int newDirectionIndexForCurrentCell = LinkUtils.transformateDiffCoordinateToDirectionIndex(1, 0);
						int newDirectionIndexForSCCell = LinkUtils.transformateDiffCoordinateToDirectionIndex(1, 0);
						if (LinkUtil.isLinkBetweenTwoCells(cell, initDirectionIndexForCurrentCell, fACell, LinkUtils.oppositeDirectionIndex(initDirectionIndexForCurrentCell))
								&& LinkUtil.isLinkBetweenTwoCells(sCCell, initDirectionIndexForSCCell, sACell, LinkUtils.oppositeDirectionIndex(initDirectionIndexForSCCell))) {
							LinkUtil.breakLinkBetweenTwoCells(cell, initDirectionIndexForCurrentCell, fACell, LinkUtils.oppositeDirectionIndex(initDirectionIndexForCurrentCell));
							LinkUtil.breakLinkBetweenTwoCells(sCCell, initDirectionIndexForSCCell, sACell, LinkUtils.oppositeDirectionIndex(initDirectionIndexForSCCell));
						}
						LinkUtil.createLinkBetweenTwoCells(cell, newDirectionIndexForCurrentCell, sACell, LinkUtils.oppositeDirectionIndex(newDirectionIndexForCurrentCell));
						LinkUtil.createLinkBetweenTwoCells(sCCell, newDirectionIndexForSCCell, fACell, LinkUtils.oppositeDirectionIndex(newDirectionIndexForSCCell));
					}
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
			
	protected static void removeAllFreeLinks(Scanword scanword) {
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

	protected static void nextStepRedefineArrows(Scanword scanword, int level, int cellTypeIndex) {
		logger.debug("start execute method nextStepRedefineArrow");
		List<Object[]> list = null;
		for (int row = scanword.getRows()-1; row >= 0; row--) {
			for (int column = scanword.getColumns()-1; column >= 0; column--) {
				if (cellTypeIndex == COMMENT_CELL_INDEX 
						&& CellUtils.isType(scanword.getArrayElement(row, column), CommentCell.class)) {
					list = ArrowUtils.getListLinkedCells(scanword, row, column, level);
				}
				logger.debug("end execute method getListLinkedCells");
				if (list != null && list.size() > 0) {
					ArrowUtils.redefineCellsLink(scanword, list);
					logger.debug("end execute method redefineCellsLink");
				}
			}
		}
	}

	protected static void foundFreeLinkInActiveCellsAndToLinkWithCommentCells(Scanword scanword, int level, int cellTypeIndex) {
		List<Object[]> list = null;
		for (int row = 0; row < scanword.getRows(); row++) {
			for (int column = 0; column < scanword.getColumns(); column++) {
				if (cellTypeIndex == ACTIVE_CELL_INDEX
						&& CellUtils.isType(scanword.getArrayElement(row, column), ActiveCell.class)) {
					list = ArrowUtils.getListLinkedCells(scanword, row, column, level);
				}
				if (list != null && list.size() > 0) {
					ArrowUtils.redefineCellsLink(scanword, list);
				}
			}
		}
	}
	
	protected static int getCountCellsWithFreeLinks(Scanword scanword, Class<? extends Cell> cellClass) {
		int count = 0;
		for (int row = 0; row < scanword.getRows(); row++) {
			for (int column = 0; column < scanword.getColumns(); column++) {
				if (CellUtils.isType(scanword.getArrayElement(row, column), cellClass)
						&& cellClass.getName().equals(CommentCell.class.getName())
						&& scanword.getArrayElement(row, column).getCountFreeLink() > 0) {
					count++;
				}
				else if (CellUtils.isType(scanword.getArrayElement(row, column), cellClass)
						&& cellClass.getName().equals(ActiveCell.class.getName())
						&& scanword.getArrayElement(row, column).getCountFreeLink() > 0) {
					count++;
				}
			}
		}
		return count;
	}
}
