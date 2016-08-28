package Léxico;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class preCatTest {

	protected Decompositor Decomp;
	protected preCat instance;
	protected OutputStream out;
	
	@Before
	public void setUp() {
		try {
			out = new FileOutputStream("OutputPreCatTest.txt");
		} catch (FileNotFoundException e) {
			System.out.println("Output file not Found");
		};
		instance = new preCat("input.txt", "Regras.txt", out, false);
	}
	
	@Test
	public void testIterator() {
		int cont = 0;
		for (String str : instance){
			if (str == null);
			cont++;
		}
		assertEquals(63, cont);
	}
	
	@Test
	public void testWrite() {
		int cont = 0;
		Scanner sc = null;
		
		instance = new preCat("input.txt", "Regras.txt", out, true);
		
		for (String str : instance){
			if (str == null);
		}
		
		try {
			sc = new Scanner(new File("OutputPreCatTest.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		while (sc.hasNextLine()){
			sc.nextLine();
			cont++;
		}
		
		assertEquals(387, cont);
		
	}

}
