package sample.impls.cell;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommentCellTest {
	private CommentCell cell;
	
	@Before
	public void setUp() throws Exception {
		cell = new CommentCell ();
	}

	@After
	public void tearDown() throws Exception {
		cell = null;
	}
	
	@Test
	public void failSetLetter () {
		cell.setLetter("Ô");
		assertNotEquals(cell.getLetter(), "Ô");
		cell.setLetter("D");
		assertNotEquals(cell.getLetter(), "D");
	}
	
	@Test
	public void setLetter () {
		cell.setLetter("A");
		assertEquals(cell.getLetter(),"");
	}
	
	@Test
	public void setFirstLink () {
		cell.setFirstLink("3.5.3");
		assertEquals(cell.getFirstLink(), "3.5.3");
	}
	
	@Test
	public void failSetFirstLink() {
		cell.setFirstLink("3.5.4");
		assertNotEquals(cell.getFirstLink(), "3.5.4");
		cell.setFirstLink("0.0.0");
		assertNotEquals(cell.getFirstLink(), "0.0.0");
		cell.setFirstLink("3.3");
		assertNotEquals(cell.getFirstLink(), "3.3");
	}

	@Test
	public void setSecondLink () {
		cell.setSecondLink("3.5.3");
		assertEquals(cell.getSecondLink(), "3.5.3");
	}
	
	@Test
	public void failSetecondLink() {
		cell.setSecondLink("3.5.4");
		assertNotEquals(cell.getSecondLink(), "3.5.4");
		cell.setSecondLink("0.0.0");
		assertNotEquals(cell.getSecondLink(), "0.0.0");
		cell.setSecondLink("3.3");
		assertNotEquals(cell.getSecondLink(), "3.3");
	}

}
