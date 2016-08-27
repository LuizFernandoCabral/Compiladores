/**
 * 
 */
package Léxico;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luiz
 * Class responsible to break a input file char-by-char
 */
public class Decompositor {

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
	Decompositor (String filein, String fileout, boolean toWrite) {
		encoding = Charset.defaultCharset();
		allChar = new ArrayList<>();
		write = toWrite;
		try {
			in = new FileInputStream(filein);
			if (write)
				out = new FileOutputStream(fileout);
		} catch (FileNotFoundException e) {
			System.out.println("File not Found");
		}
	}
	
	/**
	 * Separate char by char from input file and 
	 * write to output file
	 */
	public void process () {
		createOutputLine(0, ' ', -1);
		handleFile();
		createOutputLine(0, ' ', 2);
	}
	
	/**
	 * Simple getter
	 * @return allchar String Contains all words in file separated by space
	 */
	public List<String> getAll(){
		return allChar;
	}
	
	/**
	 * Start reading input file
	 */
    private static void handleFile() {
        reader = new InputStreamReader(in, encoding);
        // buffer for efficiency
        Reader buffer = new BufferedReader(reader); 
        handleCharacters(buffer);
    }

    /**
     * Get char by char
     * @param reader the whole input file, buffered
     */
    private static void handleCharacters(Reader reader){
        int r = 0;
        while (r != -1) {
        	try {
        		r = reader.read();
        	} catch (IOException e) {
        		System.out.println("Something in file is wrong");
        	}
        	if (r == -1) {
        		break;
        	}
            char ch = (char) r;
            createOutputLine(r, ch, 1);
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
    	String str = " ";
    	switch (flag){
    		case -1:
    			str += "BEGIN FILE";
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
    			str += "END OF FILE";
    			break;
    	}
    	str += "\n";
    	if (write)
    		WriteOutFile(str);
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
}
