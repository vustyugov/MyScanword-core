package sample.impls.cell;

import static org.junit.Assert.*;

import org.junit.*;

public class DisableCellTest {
	private DisableCell cell;

	@Before
	public void setUp() throws Exception {
		cell = new DisableCell();
	}

	@After
	public void tearDown() throws Exception {
		cell = null;
	}

	@Test
	public void setLetter() {
		cell.setLetter("D");
		assertEquals(cell.getLetter(),"");
		cell.setLetter("Û");
		assertEquals(cell.getLetter(),"");
	}
	
	@Test
	public void failSetLetter () {
		cell.setLetter("D");
		assertNotEquals(cell.getLetter(),"D");
		cell.setLetter("Û");
		assertNotEquals(cell.getLetter(),"Û");
	}
	
	@Test
	public void setFirstLink() {
		cell.setFirstLink("1.1");
		assertEquals(cell.getFirstLink(),"");
		cell.setFirstLink("1.1.1");
		assertEquals(cell.getFirstLink(),"");
	}

	@Test
	public void failSetFirstLink () {
		cell.setFirstLink("1.1");
		assertNotEquals(cell.getFirstLink(),"1.1");
		cell.setFirstLink("1.1.1");
		assertNotEquals(cell.getFirstLink(),"1.1.1");
	}

	@Test
	public void setSecondLink() {
		cell.setSecondLink("1.1");
		assertEquals(cell.getSecondLink(),"");
		cell.setSecondLink("1.1.1");
		assertEquals(cell.getSecondLink(),"");
	}
	
	@Test
	public void failSetSecondLink () {
		cell.setSecondLink("1.1");
		assertNotEquals(cell.getSecondLink(),"1.1");
		cell.setSecondLink("1.1.1");
		assertNotEquals(cell.getSecondLink(),"1.1.1");
	}
}