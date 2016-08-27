package Léxico;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class preCatTest {

	protected Decompositor Decomp;
	protected preCat instance;
	protected String writeD, writeP, in, out;
	
	@Before
	public void setUp() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Deseja realizar rastro do Decompositor char-by-char(s/n)?");
		writeD = sc.next();
		System.out.println("Deseja realizar rastro do Pré-Categorizador simbolo-a-simbolo(s/n)?");
		writeP = sc.next();
		System.out.println("Nome arquivo fonte (com a extensão)?");
		in = sc.next();
		if (writeD.contains("s"))
			Decomp = new Decompositor(in, "outputD.txt", true);
		else
			Decomp = new Decompositor(in, "outputD.txt", false);
		sc.close();
		Decomp.process();
		if (writeP.contains("s"))
			instance = new preCat(Decomp.getAll(), "Regras.txt", "outputP.txt", true);
		else
			instance = new preCat(Decomp.getAll(), "Regras.txt", "outputP.txt", false);
	}
	
	@Test
	public void test() {
		instance.process();
		System.out.println(instance.getWords());
		System.out.println(instance.getNum());
		System.out.println(instance.getSpecials());
	}

}
