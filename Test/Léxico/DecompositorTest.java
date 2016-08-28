package Léxico;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class DecompositorTest {

	protected Decompositor instance;
	protected String write, in, out;
	
	@Before
	public void setUp() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Deseja realizar rastro do Decompositor char-by-char(s/n)?");
		write = sc.next();
		System.out.println("Nome arquivo fonte (com a extensão)?");
		in = sc.next();
		if (write.contains("s"))
			instance = new Decompositor(in, "output.txt", true);
		else
			instance = new Decompositor(in, "output.txt", false);
		sc.close();
	}
	
	@Test
	public void testprocess() {
		for (char ch : instance){
			System.out.println(ch);
		}
	}

}
