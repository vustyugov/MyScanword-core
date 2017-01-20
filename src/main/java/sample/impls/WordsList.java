package sample.impls;

import sample.interfaces.Scanword;

import java.util.*;

/**
 * Created by VAUst on 07.01.2017.
 */
public class WordsList {

    private Map<String, List<String>> words;
    private List<Scanword> scanwords;

    public WordsList () {
        scanwords = new LinkedList<Scanword>();
        words = new HashMap<String, List<String>>();
    }

    public WordsList (List<Scanword> list) {
        scanwords = list;
    }

    public void setScanwords (List<Scanword> list) {
        scanwords = list;
    }

    public void setScanword (int index, Scanword scanword) {
        if (scanwords != null && (index >= 0 && index < scanwords.size())) {
            scanwords.set(index, scanword);
        }
    }

    public List<String> getWordsByPatternAndCategory (String pattern, String Category) {
        return null;
    }

    public Map<String, List<String>> getWords () {
        for (Scanword scanword: scanwords) {
            words.put(scanword.getName(), getWordsByScanword(scanword));
        }
        return words;
    }

    private List<String> getWordsByScanword (Scanword scanword) {
        return null;
    }

    private List<String> getHorizontalList (Scanword scanword) {
    }

}
