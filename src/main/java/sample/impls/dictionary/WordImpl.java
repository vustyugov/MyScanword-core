package sample.impls.dictionary;

import sample.interfaces.Category;
import sample.interfaces.Word;

import java.util.*;

/**
 * Created by VAUst on 31.12.2016.
 */
public class WordImpl implements Word{
    private String value;
    private String description;
    private List<Category> categories;
    private List<String> changeHistory;

    public WordImpl(String value, String description, List<Category> categories, List<String> changeHistory) {
        this.value = value;
        this.description = description;
        if (categories != null) {
            this.categories = categories;
        }
        else {
            categories = new LinkedList<Category>();
        }
        if (changeHistory != null) {
            this.changeHistory = changeHistory;
        }
        else {
            changeHistory = new LinkedList<String>();
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
}
