/**
 * 
 */
package Léxico;

/**
 * @author Luiz
 *
 */
public class posCat {
	
	
	/**
	 * Pos Categorizer, search whether a word is a reserved word or Id
	 */
	public posCat() {
	}
	
	/**
	 * Table with all reserved words
	 */
	public enum PalRes {
		A("PROGRAM"),
		B("END"),
		C("DECLARE"),
		D("num"),
		E("bool"),
		F("char"),
		G("void"),
		H("read"),
		I("print"),
		J("if"),
		K("else"),
		L("while"),
		M("goto"),
		N("call"),
		O("return"),
		P("true"),
		Q("false"),
		R("EE");
		
		private final String text;
		
		PalRes(String txt){
			text = txt;
		}
		
		public static boolean search (String other) {
			for (PalRes pal : values()) {
				if (pal.text.equals(other)){
					return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * Check whether a word is a reserved word or Id
	 * @param id the word to be checked
	 * @return {@code id.isPalRes();}
	 */
	public boolean isPalRes(String id){
		return PalRes.search(id);
	}
	
	
}
