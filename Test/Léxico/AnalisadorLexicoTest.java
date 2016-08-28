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
		for (String str : instance)
			System.out.println(str);
	}

}
