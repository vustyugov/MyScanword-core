package sample.impls.dictionary;

import sample.interfaces.Category;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by VAUst on 31.12.2016.
 */
public class CategoryImpl implements Category {
    private String value;
    private String description;
    private List<String> changeHistory;

    public CategoryImpl(String value, String description, List<String> changeHistory) {
        this.value = value;
        this.description = description;
        if (changeHistory != null) {
            this.changeHistory = changeHistory;
        }
        else {
            changeHistory = new LinkedList<String>();
            changeHistory();
        }
    }

    private void changeHistory () {
        StringBuilder buf = new StringBuilder((new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(new Date()).toString());
        buf.append("; ");
        buf.append(value);
        buf.append("; ");
        buf.append(description);
        changeHistory.add(buf.toString());
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
        changeHistory();
    }

    @Override
    public List<String> getChangeHistory() {
        return changeHistory;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public boolean equals (Object obj) {
    	if (obj instanceof Category) {
    		return (value.equals(((Category) obj).getValue()))?true:false;
    	}
    	else {
    		return false;
    	}
    }
}
