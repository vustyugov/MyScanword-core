package sample.impls.cell;

import static org.junit.Assert.*;

import org.junit.*;

public class ActiveCellTest {
	ActiveCell cell;

	@Before
	public void setUp() {
		cell = new ActiveCell();
	}
	
	@After
	public void turnOff() {
		cell = null;
	}
	
	@Test
	public void falseSetEnglishLetterTest() {
		cell.setLetter("A");
		assertNotEquals(cell.letter, "A");
	}
	
	@Test
	public void falseSetRussianSmallLetterTest() {
		cell.setLetter("щ");
		assertNotEquals(cell.letter, "щ");
	}
	
	@Test
	public void trueSetRussianBigLetterTest() {
		cell.setLetter("У");
		assertEquals(cell.letter, "У");
	}
	
	@Test
	public void failSetFirstLink () {
		cell.setFirstLink("1.1.1");
		assertNotEquals(cell.firstLink, "1.1.1");
		cell.setFirstLink("0.0");
		assertNotEquals(cell.firstLink, "0.0");
	}
	
	@Test
	public void setFirstLink () {
		cell.setFirstLink("0.0");
		assertEquals(cell.getFirstLink(), "-1.-1");
		cell.setFirstLink("1.1");
		assertEquals(cell.firstLink, "1.1");
	}

	@Test
	public void failSetSecondLink () {
		cell.setSecondLink("1.1.1");
		assertNotEquals(cell.secondLink, "1.1.1");
		cell.setSecondLink("0.0");
		assertNotEquals(cell.secondLink, "0.0");
	}
	
	@Test
	public void setSecondLink () {
		cell.setSecondLink("0.0");
		assertEquals(cell.secondLink, "-1.-1");
		cell.setSecondLink("1.1");
		assertEquals(cell.secondLink, "1.1");
	}

	@Test
	public void setHDirection () {
		cell.setHDirection(false);
		assertFalse(cell.hDirection);
		cell.setHDirection(true);
		assertTrue(cell.getHDirection());
	}
	
	@Test
	public void setVDirection () {
		cell.setVDirection(false);
		assertFalse(cell.getVDirection());
		cell.setVDirection(true);
		assertTrue(cell.getVDirection());
	}
	
	@Test
	public void setHWordLink () {
		cell.setHWordLink("test");
		assertEquals(cell.getHWordLink(), "test");
		cell.setHWordLink("тест");
		assertEquals(cell.getHWordLink(), "тест");
	}

	@Test
	public void setVWordLink () {
		cell.setVWordLink("test");
		assertEquals(cell.getVWordLink(), "test");
		cell.setVWordLink("тест");
		assertEquals(cell.getVWordLink(), "тест");
	}
}
