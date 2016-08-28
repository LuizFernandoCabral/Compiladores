/**
 * 
 */
package Léxico;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luiz
 *
 */
public class posCat {
	
	protected final List<String> ID;
	
	/**
	 * Pos Categorizer, search whether a word is a reserved word or Id
	 */
	public posCat(List<String> id) {
		ID = id;
	}
	
	/**
	 * Get all reserved words
	 * @return List<String> all reserved words
	 */
	public List<String> getPalRes(){
		List<String> found = new ArrayList<>();
		for (String id : ID) {
			if (PalRes.search(id)&&!found.contains(id)){
				found.add(id);
			}
		}
		return found;
	}

	/**
	 * Get all ID
	 * @return List<String> of all Ids, as many times as they appear
	 */
	public List<String> getId(){
		List<String> notfound = new ArrayList<>();
		for (String id : ID) {
			if (!PalRes.search(id)){
				notfound.add(id);
			}
		}
		return notfound;
	}
}
