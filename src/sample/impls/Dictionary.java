package sample.impls;

import sample.interfaces.Category;
import sample.interfaces.Word;

import java.util.List;

/**
 * Created by VAUst on 31.12.2016.
 */
public class Dictionary {
    private static Dictionary insrance;
    private List<Word> words;
    private List<Category> categories;

    public List<String> getWordsByTemplate (String Template, List<Category> categories) {
        return null;
    }

    public List<Word> getWords () {
        return words;
    }

    public List<Category> getCategories() {
        return categories;
    }

}
