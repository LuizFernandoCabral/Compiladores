/**
 * 
 */
package Léxico;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author Luiz
 *
 */
public class Decompositor {

	static InputStream in;
	static OutputStream out;
	static Reader reader;
	static Charset encoding;
	
	/**
	 * 
	 */
	Decompositor (String filein, String fileout) {
		 try {
			in = new FileInputStream(filein);
			out = new FileOutputStream(fileout);
		} catch (FileNotFoundException e) {
			System.out.println("File not Found");
		}
		 encoding = Charset.defaultCharset();
	}
	
	public void process () {
		createOutputLine(0, ' ', -1);
		handleFile();
		createOutputLine(0, ' ', 2);
	}
	
	/**
	 * 
	 * @param in
	 * @param encoding
	 */
    private static void handleFile() {
        reader = new InputStreamReader(in, encoding);
        // buffer for efficiency
        Reader buffer = new BufferedReader(reader); 
        handleCharacters(buffer);
    }

    /**
     * 
     * @param reader
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
    
    private static void createOutputLine (int r, char ch, int flag) {
    	String str = " ";
    	switch (flag){
    		case -1:
    			str += "READ FILE";
    			break;
    		case 0:
    			str += "READ LINE";
    			break;
    		case 1:
    			str += "READ CHARACTER: ";
    			switch (r){
    				case 9:
						str += "\\TAB		";
						break;
    				case 10:
    					str += "\\LINE_FEED	";
    					createOutputLine(0, ' ', 0);
    					break;
    				case 13:
    					str += "\\CAR_RETURN";
    					break;
    				case 32:
    					str += "\\space		";
    					break;
    				default:
    					str += ch + "			";
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
    	WriteOutFile(str);
    }
    
    private static void WriteOutFile(String str) {
    	try {
			out.write(str.getBytes());
		} catch (IOException e) {
			System.out.println("Could not write to Output File");
		}
    }
}
