package Léxico;

import org.junit.Before;
import org.junit.Test;

public class AnalisadorLexicoTest {
	
	protected AnalisadorLexico instance;

	@Before
	public void setUp() throws Exception {
		instance = new AnalisadorLexico();
	}

	@Test
	public void test() {
		System.out.println(instance.getIdAtomo());
		System.out.println(instance.getNumAtomo());
		System.out.println(instance.getResAtomo());
	}

}
