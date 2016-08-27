/**
 * 
 */
package Léxico;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.omg.CosNaming.NamingContextPackage.NotFound;

import Automato.Automato;
import Automato.Maquina;

/**
 * @author Luiz
 *
 */
public class preCat {

	protected final List<String> chars;
	protected Automato precat;
	protected final List<String> words;
	protected final List<String> num;
	protected final List<String> specials;
	protected final boolean write;
	protected static OutputStream out;
	
	/**
	 * Initialize variables, and construct pre-categorization automato
	 */
	public preCat(List<String> chars, String pre, String fileout, boolean towrite) {
		this.chars = chars;
		write = towrite;
		System.out.println(chars);
		precat = new Automato(pre);
		precat.Construct();
		words = new ArrayList<>();
		num = new ArrayList<>();
		specials = new ArrayList<>();
		if (write){
			try {
				out = new FileOutputStream(fileout);
			} catch (FileNotFoundException e) {
				System.out.println("Output file not Found");
			}
		}
	}
	
	/**
	 * Uses the pre categorization automato to check the input file 
	 * and separate Id, numeral and special
	 */
	public void process() {
		String EstAtual, SyAtual, str = "", aux;
		Maquina AutoAtual = precat.AutoIni();
		
		EstAtual = AutoAtual.getInicial();
		while (!chars.isEmpty()){
			SyAtual = chars.get(0);
			aux = SyAtual;
			SyAtual = SyAtual.replaceAll("^[A-Za-z]", "\\\\letter");
			SyAtual = SyAtual.replaceAll("^[0-9]", "\\\\digit");
			SyAtual = SyAtual.replaceAll("^[^\\\\A-Za-z0-9.]", "\\\\others");
			
			try {
				EstAtual = AutoAtual.searchRule(EstAtual, SyAtual).getOut();
				WriteOutFile("Input: " + aux + "; Rule: " + AutoAtual.searchRule(EstAtual, SyAtual).toString());
				chars.remove(aux);
				str += aux;
			} catch (NotFound e) {
				if (AutoAtual.isFinal(EstAtual)){
					addtoLists(str);
					str = "";
					EstAtual = AutoAtual.getInicial();
					WriteOutFile("End of pre-atom.");
				}
				else {
					System.out.println("Input doesn't belong to specified rules");
					break;
				}
			}
		}
	}
	
	/**
	 * Classify the input into: ID; number; or special character
	 * @param str the input String to be classified
	 */
	private void addtoLists(String str) {
		str = str.replace("\\space", "");
		str = str.replace("\\EndLine", "");
		str = str.replace("\\TAB", "");
		if (str.matches("^[A-Za-z].*")) {
			words.add(str);
		}
		else if (str.matches("[0-9]*[.]?[0-9]*")) {
			num.add(str);
		}
		else {
			specials.add(str);
		}
	}

	
	/**
	 * The ID getter
	 * @return all IDs
	 */
	public List<String> getWords(){
		return words;
	}
	
	/**
	 * The numeral getter
	 * @return all numbers
	 */
	public List<String> getNum(){
		return num;
	}
	
	/**
	 * The special characters getter
	 * @return all special characters
	 */
	public List<String> getSpecials() {
		return specials;
	}
	
    /**
     * Write a given string to output file
     * @param str the given string to write
     */
    private void WriteOutFile(String str) {
    	if (write){
	    	try {
				out.write(str.getBytes());
				out.write('\n');
			} catch (IOException e) {
				System.out.println("Could not write to Output File");
			}
    	}
    }

}
