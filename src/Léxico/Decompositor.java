/**
 * 
 */
package Léxico;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

/**
 * @author Luiz
 * Class responsible to break a input file char-by-char
 */
public class Decompositor implements Iterable<String>{

	protected static InputStream in;
	protected static OutputStream out;
	protected static Reader reader;
	protected static Charset encoding;
	protected static List<String> allChar;// Contains all words in file separated by space
	protected static boolean write;
	
	
	/**
	 * Constructor set Input and Output file, and initialize class variables
	 * @param filein name for input file
	 * @param fileout name for output file
	 * @param toWrite whether to write to output or not
	 */
	Decompositor (String filein, OutputStream fileout, boolean toWrite) {
		encoding = Charset.defaultCharset();
		allChar = new ArrayList<>();
		write = toWrite;
		try {
			in = new FileInputStream(filein);
			if (write)
				out = fileout;
		} catch (FileNotFoundException e) {
			System.out.println("File not Found");
		}
	}
    
    /**
     * Create each line for output, depending on given char
     * @param r the char read in integer format
     * @param ch the char
     * @param flag differs to begin of file(-1), begin of line(0), 
     * regular characters(1), and end of file(2)
     */
    private static void createOutputLine (int r, char ch, int flag) {
    	String str = "Decompositor: ";
    	switch (flag){
    		case -1:
    			str += "BEGIN FILE\n";
    			break;
    		case 0:
    			str += "NEW LINE";
    			break;
    		case 1:
    			str += "CHARACTER: ";
    			switch (r){
    				case 9:
						str += "\\TAB		";
    					allChar.add("\\TAB");
						break;
    				case 10:
    					str += "\\LINE_FEED	";
    					createOutputLine(0, ' ', 0);
    					break;
    				case 13:
    					str += "\\CAR_RETURN ";
    					allChar.add("\\EndLine");
    					createOutputLine(0, ' ', 2);
    					break;
    				case 32:
    					str += "\\space		";
    					allChar.add("\\space");
    					break;
    				default:
    					str += ch + "			";
    					allChar.add("" + ch);
    					break;
    			}
    			str += " ASCII 0x";
    			str += String.format("%02X", r);
    			break;
    		case 2:
    			str += "END LINE";
    			break;
    		case 3:
    			str += "END OF FILE";
    			break;
    	}
    	str += "\n";
    	if (write)
    		WriteOutFile(str);
    }
    
    /**
     * A simple getter
     * @return a list of all chars found
     */
	public static List<String> getAllChar() {
		return allChar;
	}
    
    /**
     * Write a given string to output file
     * @param str the given string to write
     */
    private static void WriteOutFile(String str) {
    	try {
			out.write(str.getBytes());
		} catch (IOException e) {
			System.out.println("Could not write to Output File");
		}
    }

	@Override
	public Iterator<String> iterator() {
		return new DecompIterator();
	}
	
	 /**
	  * The nested Iterator for class Decompositor
	  */
	public class DecompIterator implements Iterator<String> {

		int r = 0;// keep each char read in int value
		boolean begin = true;// determines whether it is begining or if it has already begun
		Reader buffer = null;
		
		@Override
		public boolean hasNext() {
			return r!=-1;
		}

		@Override
		public String next() {
			char ch;
            String str;
            
			if (begin) {
				reader = new InputStreamReader(in, encoding);
		        // buffer for efficiency
		        buffer = new BufferedReader(reader);
				createOutputLine(0, ' ', -1);
				begin = false;
			}
			
			try {
        		r = buffer.read();
        	} catch (IOException e) {
        		System.out.println("Something in file is wrong while reading");
        	}
			
			if (r == -1){
				try {
					reader.close();
					buffer.close();
				} catch (IOException e) {
					System.out.println("Something in file is wrong while closing it");
				}
				createOutputLine(0, ' ', 3);
				return "";
			}
			
			ch = (char) r;
            createOutputLine(r, ch, 1);
            switch (r){
				case 9:
					str = "\\TAB";
					break;
				case 10:
					str = "";
					break;
				case 13:
					str = "\\EndLine";
					break;
				case 32:
					str = "\\space";
					break;
				default:
					str = "" + ch;
					break;
            }
            return str;
		}
		
	}
}
