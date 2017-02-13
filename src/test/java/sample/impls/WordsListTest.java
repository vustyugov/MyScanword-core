package sample.impls;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import sample.impls.cell.*;
import sample.impls.dictionary.WordImpl;
import sample.impls.scanword.SquaredScanword;
import sample.interfaces.*;

public class WordsListTest {
	private WordsList words;
	private List<Scanword> scanwords;
	
	
	@BeforeClass
	public void setUpClass() throws Exception {
		Cell[][] cells;
		Scanword scanword = null;
		String[][] array = {{"DC","DC","DC","DC","DC","DC","DC","DC","CC","SC","SC","SC","SC","SC","CC","SC","CC"},
				{"SC","CC","SC","CC","SC","CC","SC","SC","SC","SC","CC","SC","CC","SC","SC","SC","SC"},
				{"SC","SC","SC","SC","SC","SC","CC","SC","CC","SC","SC","SC","SC","SC","CC","SC","CC"},
				{"SC","CC","SC","CC","SC","SC","SC","SC","SC","SC","CC","SC","CC","SC","SC","SC","SC"},
				{"SC","SC","SC","SC","SC","SC","CC","SC","CC","SC","SC","SC","SC","CC","SC","CC","SC"},
				{"SC","CC","SC","CC","SC","SC","SC","SC","SC","CC","SC","CC","SC","SC","SC","SC","SC"},
				{"SC","SC","SC","SC","SC","CC","SC","CC","SC","SC","SC","SC","SC","CC","SC","CC","SC"},
				{"CC","SC","CC","SC","CC","SC","SC","SC","SC","CC","SC","CC","SC","SC","SC","SC","SC"},
				{"SC","SC","SC","SC","SC","CC","SC","CC","SC","SC","SC","SC","CC","SC","CC","SC","CC"},
				{"CC","CC","CC","SC","SC","SC","SC","SC","SC","CC","CC","SC","SC","SC","SC","SC","SC"},
				{"SC","SC","SC","SC","SC","CC","SC","CC","SC","SC","SC","SC","CC","SC","SC","SC","SC"},
				{"SC","CC","SC","CC","SC","SC","SC","SC","CC","SC","CC","SC","SC","CC","SC","SC","SC"},
				{"SC","SC","SC","SC","CC","SC","CC","SC","SC","SC","SC","CC","SC","CC","SC","CC","SC"},
				{"SC","CC","SC","CC","SC","SC","SC","SC","CC","SC","SC","SC","SC","SC","SC","SC","SC"},
				{"SC","SC","SC","SC","CC","SC","SC","SC","SC","CC","SC","CC","SC","CC","SC","SC","SC"},
				{"SC","CC","SC","CC","SC","SC","SC","SC","CC","SC","SC","SC","SC","SC","CC","SC","CC"},
				{"DC","DC","DC","DC","DC","DC","DC","DC","CC","SC","CC","SC","CC","SC","SC","SC","SC"},
				{"DC","DC","DC","DC","DC","DC","DC","DC","SC","SC","SC","SC","SC","SC","SC","CC","SC"},
				{"DC","DC","DC","DC","DC","DC","DC","DC","CC","SC","CC","SC","CC","SC","SC","SC","SC"},
				{"DC","DC","DC","DC","DC","DC","DC","DC","SC","SC","SC","SC","SC","CC","SC","CC","SC"},
				{"DC","DC","DC","DC","DC","DC","DC","DC","SC","CC","SC","CC","SC","SC","SC","SC","SC"},
				{"DC","DC","DC","DC","DC","DC","DC","DC","SC","CC","SC","CC","SC","CC","SC","SC","SC"},
				{"DC","DC","DC","DC","DC","DC","DC","DC","SC","SC","SC","SC","CC","SC","CC","SC","CC"},
				{"DC","DC","DC","DC","DC","DC","DC","DC","CC","SC","CC","SC","SC","SC","SC","CC","SC"},
				{"DC","DC","DC","DC","DC","DC","DC","DC","SC","SC","SC","SC","CC","SC","SC","SC","SC"},
				{"DC","DC","DC","DC","DC","DC","DC","DC","CC","SC","CC","SC","SC","SC","SC","SC","SC"}
				};
		cells = new Cell[array.length][array[0].length];
		for(int indexR = 0; indexR < array.length; indexR++) {
			for(int indexC = 0; indexC < array[0].length; indexC++) {
				Cell cell = null;
				switch(array[indexR][indexC]) {
				case "DC":
					cell = new DisableCell();
					break;
				case "CC":
					cell = new CommentCell();
					break;
				case "SC":
					cell = new SimpleCell();
					break;
					default:
						break;
				}
				cells[indexR][indexC] = cell;
			}
		}
		scanword = new SquaredScanword("01", cells.length, cells[0].length);
		scanword.setArray(cells);
		scanwords = new LinkedList<Scanword>();
		scanwords.add(scanword);
	}
	
	@Before
	public void setUp() throws Exception {
		words = new WordsList(scanwords);
	}

	@After
	public void tearDown() throws Exception {
		words = null;
	}
	
	@AfterClass
	public void tearDownClass() throws Exception {
		scanwords = null;
	}
}
