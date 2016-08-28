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
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * @author Luiz
 *
 */
public class AnalisadorLexico implements Iterable<String> {

	protected final preCat precat;
	protected final posCat poscat;
	protected final String write, in, regras;
	protected OutputStream out;
	protected List<String> TabSimb;
	protected List<String> line = new ArrayList<>();
	
	/**
	 * The lexical analyzer, that receives (by user input) the source file and
	 * the rules file for the pre-categorizer and 
	 * returns (by different methods) the different lists of different atoms
	 */
	public AnalisadorLexico() {
		TabSimb = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		
		try {
			out = new FileOutputStream("Output.txt");
			out.write("Legenda:\nAtomo Identificador = (Posição, String_ID)\n".getBytes());
			out.write("Atomo Numeral = (String, Valor)\n".getBytes());
			out.write("Atomo Palavra Reservada = (Palavra, linha_contem_palavra)\n".getBytes());
			out.write("Atomo Special = (String_special)\n\n".getBytes());
			out.write("Eventos:\n".getBytes());
		} catch (FileNotFoundException e) {
			System.out.println("Output file not Found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// User interface (on-off buttons)
		System.out.println("Deseja realizar rastro (s/n)?");
		write = sc.next();
		System.out.println("Nome arquivo regras para o categorizador (com a extensão)?");
		regras = sc.next();
		System.out.println("Nome arquivo fonte (com a extensão)?");
		in = sc.next();
		sc.close();
		
		// get all lines for later
		try {
			sc = new Scanner(new File(in));
			while(sc.hasNextLine()){
				line.add(sc.nextLine());
			}
			sc.close();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Other classes instances initialization
		if (write.contains("s"))
			precat = new preCat(in, regras, out, true);
		else
			precat = new preCat(in, regras, out, false);
		poscat = new posCat();
	}
	
	/**
	 * Method to write a given string to output file
	 * @param str string to be written
	 */
	protected void writeToFile(String str){
		if (write.contains("s")){
			try {
				out.write(str.getBytes());
				out.write('\n');
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param Sy
	 * @return
	 */
	private String createAtom(String Sy) {
		int pos;
		double value;
		String linha;
		
		if (Sy.matches("^[A-Za-z].*")) {
			if (poscat.isPalRes(Sy)){
				linha = getResLine(Sy);
				return "Palavra Reservada: (" + Sy + ", [" + linha + "])";
			}
			else if (!TabSimb.contains(Sy)){
				TabSimb.add(Sy);
				pos = TabSimb.size()-1;
			}
			else {
				pos = TabSimb.indexOf(Sy);
			}
			return "ID: (" + Sy + ", " + pos + ")";
		}
		
		else if (Sy.matches("^[0-9]+")||Sy.matches("^[.][0-9]+")) {
			value = Double.parseDouble(Sy);
			return "Numeral: (" + Sy + ", " + value + ")";
		}
		
		return "Special: (" + Sy + ")";
	}
	
	/**
	 * 
	 * @param Sy
	 * @return
	 */
	private String getResLine(String Sy) {
		for (String linha : line) {
			if(linha.contains(Sy)){
				line.remove(linha);
				return linha;
			}
		}
		return "";
	}

	@Override
	public Iterator<String> iterator() {
		return new ALIterator();
	}
	
	/**
	 * The nested Iterator for class AnalisadorLexico
	 */
	public class ALIterator implements Iterator<String> {
		
		Iterator<String>iter = precat.iterator();
		String Symbol;

		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public String next() {
			String ret;
			
			while (iter.hasNext()) {
				Symbol = iter.next();
				if (Symbol == null){
					continue;
				}
				Symbol = Symbol.replace("\\space", "");
				Symbol = Symbol.replace("\\TAB", "");
				ret = createAtom(Symbol);
				
				writeToFile("Atomo " + ret + ".\n");
				
				return ret;
			}
			return null;
		}
		
	}
}
