package sample.utils;

import java.util.*;

import org.apache.log4j.*;

import sample.impls.cell.*;
import sample.interfaces.*;

public final class Util {

	private static Logger logger = Logger.getLogger(Util.class.getName());
		
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
		logger.info("start execute method defineArrowsForCommentCells "
				+ "for scanword: name=" + scanword.getName() + " rows=" + scanword.getRows()
				+ " columns=" + scanword.getColumns());
		ArrowUtils.firstStepDefineArrows(scanword.getArray());
		
		logger.debug("end execute method firstStepDefineArrows");
		ArrowUtils.secondStepDefineArrows(scanword.getArray());
		logger.debug("end execute method secondStepDefineArrows");		
		for (int index = 1; index < level; index++) {
			ScanwordUtil.nextStepRedefineArrows(scanword, index, 1);
			logger.debug("end execute method nextStepRedefineArrow");
		}
		for (int index = 1; index < level/2; index++) {
			ScanwordUtil.foundFreeLinkInActiveCellsAndToLinkWithCommentCells(scanword, index, 0);
		}
		ScanwordUtil.removeAllFreeLinks(scanword);
		logger.debug("end execute method removeAllFreeLinks");
		ScanwordUtil.changeFilledLinks(scanword);
		logger.debug("end execute method changeFilledLinks");
		logger.info("end execute method defineArrowsForCommentCells for scanword: name=" + scanword.getName());
		logger.info("Count cells contains free link: CommentCell="+ScanwordUtil.getCountCellsWithFreeLinks(scanword, CommentCell.class) + 
						" ActiveCell="+ScanwordUtil.getCountCellsWithFreeLinks(scanword, ActiveCell.class));
		return true;
	}
			
	public static Map<String, List<String>> getWords(Scanword scanword) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		
		return map;
	}
}