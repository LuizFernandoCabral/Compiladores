package Léxico;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class posCatTest {
	
	protected posCat instance;
	protected String writeD, writeP, in, out;
	
	@Before
	public void setUp() throws Exception {
		instance = new posCat();
	}

	@Test
	public void testtrue() {
		assertEquals(true, instance.isPalRes("PROGRAM"));
	}
	
	@Test
	public void testfalse() {
		assertEquals(false, instance.isPalRes(""));
	}

}
