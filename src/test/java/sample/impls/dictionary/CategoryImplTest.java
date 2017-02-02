package sample.impls.dictionary;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CategoryImplTest {
	private CategoryImpl category;
	
	@Before
	public void setUp() throws Exception {
		category = new CategoryImpl("����", "��������", new LinkedList<String>());
	}

	@After
	public void tearDown() throws Exception {
		category = null;
	}

	@Test
	public void getValue() {
		assertEquals(category.getValue(), "����");
	}
	
	@Test
	public void getDescription () {
		assertEquals(category.getDescription(), "��������");
		category.setDescription("�����");
		assertEquals(category.getDescription(), "�����");
	}
	
	@Test
	public void getChangeHistory () {
		assertEquals(category.getChangeHistory().size(), 0);
		category.setDescription("�����");
		assertEquals(category.getChangeHistory().size(), 1);
		assertNotEquals(category.getChangeHistory().size(), 0);
	}
	
}
