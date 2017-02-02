package sample.impls.cell;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SimpleCellTest {
	private SimpleCell cell;
	
	@Before
	public void setUp() throws Exception {
		cell = new SimpleCell();
	}

	@After
	public void tearDown() throws Exception {
		cell = null;
	}
	
	@Test
	public void failSetEnglishLetter() {
		cell.setLetter("A");
		assertNotEquals(cell.letter, "A");
		assertNotEquals(cell.getLetter(), "A");
	}
	
	@Test
	public void failSetCyrilicSmallLetter() {
		cell.setLetter("ù");
		assertNotEquals(cell.letter, "ù");
		assertNotEquals(cell.getLetter(), "ù");
	}
	
	@Test
	public void setCyrilicLetter () {
		cell.setLetter("Ô");
		assertEquals(cell.letter, "Ô");
		assertEquals(cell.getLetter(), "Ô");
	}
	
	@Test
	public void failSetFirstLink () {
		cell.setFirstLink("1.1");
		assertNotEquals(cell.firstLink,"1.1");
		cell.setFirstLink("1.1.1");
		assertNotEquals(cell.firstLink,"1.1.1");
	}
	
	@Test
	public void setFirstLink() {
		cell.setFirstLink("");
		assertEquals(cell.firstLink,"");
	}
	
	@Test
	public void failSetSecondLink () {
		cell.setSecondLink("1.1");
		assertNotEquals(cell.secondLink,"1.1");
		cell.setSecondLink("1.1.1");
		assertNotEquals(cell.secondLink,"1.1.1");
	}
	
	@Test
	public void setSecondLink() {
		cell.setSecondLink("");
		assertEquals(cell.secondLink,"");
	}

}
