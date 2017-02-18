package sample.impls.dictionary;

import sample.interfaces.*;

import java.util.*;

/**
 * Created by VAUst on 31.12.2016.
 */
public class WordImpl implements Word{
    private String value;
    private String description;
    private List<Category> categories;
    private List<String> changeHistory;

    public WordImpl(String value) {
    	this.value = value;
    	changeHistory = new LinkedList<String>();
    }
    
    public WordImpl(String value, String description) {
    	this(value);
        this.description = description;
    }
    
    public WordImpl(String value, String description, List<Category> categories) {
    	this(value, description);
        if (categories != null) {
            this.categories = categories;
        }
        else {
            categories = new LinkedList<Category>();
        }
    }
    
    public WordImpl(String value, String description, List<Category> categories, List<String> changeHistory) {
        this(value, description, categories);
        if (changeHistory != null) {
            this.changeHistory = changeHistory;
        }
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public void setChangeHistory(Date changeHistory, String value, String description, List<Category> categories) {

    }

    @Override
    public void addCategory(Category category) {
        if (categories != null && categories.contains(category)) {
            categories.add(category);
        }
        else {
            categories = new LinkedList<Category>();
            categories.add(category);
        }
    }

    @Override
    public void removeCategory(Category category) {
        categories.remove(category);
    }

    @Override
    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

	@Override
	public boolean containsCategory(Category category) {
		return categories.stream()
				.anyMatch(c-> c.equals(category));
	}

	@Override
	public boolean containsCategory(String categoryName) {
		return categories.stream()
				.anyMatch(c -> c.getValue().equals(categoryName));
	}
	
	@Override
	public boolean equals (Object obj) {
		if (obj instanceof Word) {
			if (value.equals(((Word) obj).getValue())) {
				return true;
			}
			return false;
		}
		else {
			return false;
		}
	}
}
