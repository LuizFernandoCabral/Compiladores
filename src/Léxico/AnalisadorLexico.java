/**
 * 
 */
package Léxico;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Luiz
 *
 */
public class AnalisadorLexico {

	protected final Decompositor Decomp;
	protected final preCat precat;
	protected final posCat poscat;
	protected final String writeD, writeP, writeA, in, regras;
	protected OutputStream out;
	protected List<String> TabSimb;
	protected Map<Integer, String> idAtomo; 
	protected Map<String, Double> numAtomo;
	protected Map<String, List<String>> ResAtomo;
	
	/**
	 * The lexical analyzer, that receives (by user input) the source file and
	 * the rules file for the pre-categorizer and 
	 * returns (by different methods) the different lists of different atoms
	 */
	public AnalisadorLexico() {
		TabSimb = new ArrayList<>();
		idAtomo = new HashMap<>();
		numAtomo = new HashMap<>();
		ResAtomo = new HashMap<>();
		Scanner sc = new Scanner(System.in);
		
		try {
			out = new FileOutputStream("outputAL.txt");
			out.write("Legenda:\nAtomos Identificadores = (Posição, String_ID)\n".getBytes());
			out.write("Atomos Numerais = (String_valor, Valor)\n".getBytes());
			out.write("Atomos Palavras Reservadas = (Palavra, linhas_contem_palavra)\n\n".getBytes());
			out.write("Por eventos:\n".getBytes());
		} catch (FileNotFoundException e) {
			System.out.println("Output file not Found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// User interface (on-off buttons)
		System.out.println("Deseja realizar rastro do Decompositor char-by-char(s/n)?");
		writeD = sc.next();
		System.out.println("Deseja realizar rastro do Pré-Categorizador simbolo-a-simbolo(s/n)?");
		writeP = sc.next();
		System.out.println("Deseja realizar o rastro final da criação dos atomos(s/n)?");
		writeA = sc.next();
		System.out.println("Nome arquivo regras para o categorizador (com a extensão)?");
		regras = sc.next();
		System.out.println("Nome arquivo fonte (com a extensão)?");
		in = sc.next();
		sc.close();
		
		// Other classes instances initialization
		if (writeD.contains("s"))
			Decomp = new Decompositor(in, "outputD.txt", true);
		else
			Decomp = new Decompositor(in, "outputD.txt", false);
		if (writeP.contains("s"))
			precat = new preCat(Decomp.getAll(), regras, "outputC.txt", true);
		else
			precat = new preCat(Decomp.getAll(), regras, "outputC.txt", false);
		poscat = new posCat(precat.getWords());
		
		createIdAtomo();
		createNumAtomo();
		createResAtomo();
	}
	
	/**
	 * Method to populate IdAtoms, which contain (position, id), 
	 * where position is the position in the symbol table
	 */
	public void createIdAtomo() {
		List<String> id;
		int i = 0;
		id = poscat.getId();
		
		for (String str : id) {
			if (!TabSimb.contains(str)){
				TabSimb.add(str);
				idAtomo.put(i, TabSimb.get(i));
				writeToFile("Atomo Identificador: (" + i + ", " + TabSimb.get(i) + ")");
				i++;
			}
		}
	}
	
	/**
	 * Method to populate NumAtoms, which contain (string_value, double_value), 
	 * where string_value is the string for the number, and 
	 * double_value the correspondent number in double
	 * @return a map {string_value = double_value} with all numeral atoms
	 */
	private void createNumAtomo() {
		List<String> num;
		num = precat.getNum();
		double value;
		
		for (String n : num){
			value = Double.parseDouble(n);
			numAtomo.put(n, value);
			writeToFile("Atomo Numeral: (" + n + ", " + value + ")");
		}
	}
	
	/**
	 * Method to populate NumAtoms, which contain (Reserved Words, List(Lines)), 
	 * where Reserved Words is the reserved word, and 
	 * List(Lines) is a list containing all lines where the word appear 
	 * (even if as complementing other words)
	 * @return a map {Reserved Words = "[" Line { "," Line } "]" } with all found Reserved Words
	 */
	private void createResAtomo() {
		List<String> pal = poscat.getPalRes();
		List<String> line = new ArrayList<>();
		Scanner sc;
		
		try {
		sc = new Scanner(new File(in));
		while(sc.hasNextLine()){
			line.add(sc.nextLine());
		}
		sc.close();
		
		for (String str : pal) {
			ResAtomo.put(str, new ArrayList<>());
			for (String linha : line) {
				if(linha.contains(str)){
					ResAtomo.get(str).add(linha);
				}
			}
			writeToFile("Atomo Palavra Reservada: (" + str + ", " + ResAtomo.get(str) + ")");
		}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to write a given string to output file
	 * @param str string to be written
	 */
	private void writeToFile(String str){
		if (writeA.contains("s")){
			try {
				out.write(str.getBytes());
				out.write('\n');
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// Getters
	public Map<Integer, String> getIdAtomo() {
		return idAtomo;
	}

	public Map<String, Double> getNumAtomo() {
		return numAtomo;
	}

	public Map<String, List<String>> getResAtomo() {
		return ResAtomo;
	}
}
