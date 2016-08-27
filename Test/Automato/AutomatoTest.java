package Automato;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import Léxico.Decompositor;

public class AutomatoTest {
	
	protected Automato instance;
	
	@Before
	public void setUp() {
		 instance = new Automato("Regras.txt");
	}

	@Test
	public void test() {
		instance.Construct();
	}

}
