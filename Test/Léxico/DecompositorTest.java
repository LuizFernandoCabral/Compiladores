package Léxico;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DecompositorTest {

	protected Decompositor instance;
	
	@Before
	public void setUp() {
		 instance = new Decompositor("input.txt", "output.txt");
	}
	
	@Test
	public void testprocess() {
		instance.process();// method uses all other method if it's correct no need to test others
	}

}
