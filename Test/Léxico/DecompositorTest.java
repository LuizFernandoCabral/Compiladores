package Léxico;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class DecompositorTest {

	protected Decompositor instance;
	
	@Before
	public void setUp() {
		instance = new Decompositor("input.txt", null, false);
	}
	
	@Test
	public void testIterable() {
		int cont = 0;
		for (String str : instance){
			str.isEmpty();
			cont++;
		}
		assertEquals(143, cont);
	}
	
	@Test
	public void testOutput() {
		int cont = 0;
		Scanner sc = null;
		
		try {
			instance = new Decompositor("input.txt", new FileOutputStream("OutputDecompTest.txt"), true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for (String str : instance){
			str.replace("", "");
		}
		
		try {
			sc = new Scanner(new File("OutputDecompTest.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while (sc.hasNextLine()){
			sc.nextLine();
			cont++;
		}
		
		assertEquals(158, cont);
	}

}
