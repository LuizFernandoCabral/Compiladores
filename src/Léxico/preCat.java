/**
 * 
 */
package Léxico;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import Automato.Automato;
import Automato.Maquina;

/**
 * @author Luiz
 *
 */
public class preCat implements Iterable<String> {

	protected String in;
	protected Automato precat;
	protected final boolean write;
	protected static OutputStream out;
	protected final Decompositor decomp;
	
	/**
	 * Initialize variables, and construct pre-categorization automato
	 */
	public preCat(String input, String pre, OutputStream fileout, boolean towrite) {
		in = input;
		write = towrite;
		precat = new Automato(pre);
		precat.Construct();
		
		if (write){
			out = fileout;
			decomp = new Decompositor(in, out, true);
		}
		else
			decomp = new Decompositor(in, null, false);
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

	@Override
	public Iterator<String> iterator() {
		return new PreIterator();
	}
	
	/**
	 * The nested Iterator for class PreCat
	 */
	public class PreIterator implements Iterator<String> {

		String EstAtual, SyAtual, str = "", aux;
		Maquina AutoAtual = precat.AutoIni();
		Iterator<String>iter = decomp.iterator();
		boolean next = true;
		
		@Override
		public boolean hasNext() {
			return iter.hasNext();
		}

		@Override
		public String next() {
			
			str = "";
			
			EstAtual = AutoAtual.getInicial();
			while (iter.hasNext()) {
				if (next) {
					SyAtual = iter.next();
					aux = SyAtual;
				}
				next = true;
				SyAtual = SyAtual.replaceAll("^[A-Za-z]", "\\\\letter");
				SyAtual = SyAtual.replaceAll("^[0-9]", "\\\\digit");
				SyAtual = SyAtual.replaceAll("^[^\\\\A-Za-z0-9.]", "\\\\others");
				
				try {
					WriteOutFile("PreCat: " + aux + "; Rule: " + AutoAtual.searchRule(EstAtual, SyAtual).toString());
					EstAtual = AutoAtual.searchRule(EstAtual, SyAtual).getOut();
					str += aux;
				} catch (NotFound e) {
					if (AutoAtual.isFinal(EstAtual)){
						EstAtual = AutoAtual.getInicial();
						next = false;
						WriteOutFile("PreCat: End of pre-atom.\n");
						return str;
					}
					else {
						break;
					}
				}
			}
			return null;
		}
		
	}

}
