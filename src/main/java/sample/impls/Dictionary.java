package sample.impls;

import sample.interfaces.Category;
import sample.interfaces.Word;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by VAUst on 31.12.2016.
 */
public class Dictionary {
    private static Dictionary insrance;
    private List<Word> words;
    private List<Category> categories;

    public void setDictionary(List<Word> words) {
    	this.words = words;
    }
    
    public void setCategories(List<Category> categories) {
    	this.categories = categories;
    }
    
    public List<Word> getWordsByTemplate (String Template, List<Category> categories) {
    	return words.parallelStream()
    	.filter(w->categories.stream()
    				.filter(c->w.getCategories()
    						.contains(c))
    				.count()>=1)
    	.collect(Collectors.toList());
    }

    public List<Word> getWords () {
        return words;
    }

    public List<Category> getCategories() {
        return categories;
    }

}
