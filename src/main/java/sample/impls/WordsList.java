package sample.impls;

import sample.interfaces.*;

import java.util.*;

/**
 * Created by VAUst on 07.01.2017.
 */
public class WordsList {
	private Map<String, List<String>> words;
    public List<Scanword> scanwords;

    
    public WordsList () {
        scanwords = new LinkedList<Scanword>();
        words = new HashMap<String, List<String>>();
    }
    
    public WordsList (List<Scanword> list) {
    	this();
    }

    
    public void setScanwords (List<Scanword> list) {
    	
    }
    
    public void setScanword (int index, Scanword scanword) {
        
    }
    
    public List<String> getWordsByPatternAndCategory (String pattern, String Category) {
        return null;
    }

	
    public Map<String, List<String>> getWords () {
        return words;
    }
}