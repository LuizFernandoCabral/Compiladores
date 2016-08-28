package Léxico;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class posCatTest {

	protected Decompositor Decomp;
	protected preCat precat;
	protected posCat instance;
	protected String writeD, writeP, in, out;
	
	@Before
	public void setUp() throws Exception {
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
		if (writeP.contains("s"))
			precat = new preCat(Decomp.getAll(), "Regras.txt", "outputP.txt", true);
		else
			precat = new preCat(Decomp.getAll(), "Regras.txt", "outputP.txt", false);
		instance = new posCat(precat.getWords());
	}

	@Test
	public void test() {
		System.out.println(precat.getWords());
		System.out.println(instance.getPalRes());
		System.out.println(instance.getId());
	}

}
