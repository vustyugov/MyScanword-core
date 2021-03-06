package sample.impls.cell;

import sample.interfaces.Cell;

import java.util.regex.Pattern;

/**
 * Created by VAUst on 08.12.2016.
 */
public class SimpleCell implements Cell {
    protected String letterReg;
    protected String linkReg;
    protected String type;
    protected String letter;
    protected String firstLink;
    protected String secondLink;
    protected boolean availibilityFirstLink;
    protected boolean availibilitySecondLink;

    public SimpleCell () {
        letterReg = "[\\u0410-\\u044F\\u0401\\u0451]{1}";
        linkReg = "";
        type = "SC";
        letter = "";
        firstLink = "";
        secondLink = "";
        availibilityFirstLink = true;
        availibilitySecondLink = true;
    }

    @Override
    public void setLetter(String letter) {
        if (Pattern.matches(letterReg, letter)) {
            this.letter = letter;
        }
    }

    @Override
    public String getLetter() {
        return letter;
    }

    @Override
    public String getType() {
    	return type;
    }
    
    @Override
    public void setFirstLink(String firstLink) {
        if (Pattern.matches(linkReg, firstLink)) {
            this.firstLink = firstLink;
        }
    }

    @Override
    public String getFirstLink() {
        return firstLink;
    }
    
    @Override
    public void setAvailableFirstLink(boolean availability) {
    	this.availibilityFirstLink = availability;
    }
    
    @Override
    public boolean isAvailableFirstLink() {
    	return this.availibilityFirstLink;
    }

    @Override
    public void setAvailableSecondLink(boolean availability) {
    	this.availibilitySecondLink = availability;
    }
    
    @Override
    public boolean isAvailableSecondLink() {
    	return this.availibilitySecondLink;
    }
    
    @Override
    public void setSecondLink(String secondLink) {
        if (Pattern.matches(linkReg, secondLink)) {
            this.secondLink = secondLink;
        }
    }

    @Override
    public String getSecondLink() {
        return secondLink;
    }

    @Override
    public String toString () {
        StringBuilder buf = new StringBuilder("[");
        buf.append("[");
        buf.append(type);
        buf.append("][");
        if (letter.equals("")) {
        	buf.append(" ");
        }
        else {
        	buf.append(letter);
        }
        buf.append("][");
        if (firstLink.equals("")) {
        	buf.append("");
        }
        else {
        	buf.append(firstLink);
        }
        buf.append("][");
        if (secondLink.equals("")) {
        	buf.append("");
        }
        else {
        	buf.append(secondLink);
        }
        buf.append("]]");
        return buf.toString();
    }

	@Override
	public int getCountAvailableLink() {
		return 0;
	}

	@Override
	public int getCountFreeLink() {
		return 0;
	}

	@Override
	public boolean setLink(String link) {
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
    		return false;
    	}
    	if (this.getClass() != obj.getClass()) {
    		return false;
    	}
    	if (firstLink.equals(((CommentCell)obj).firstLink)
    			&& secondLink.equals(((CommentCell)obj).secondLink)
    			&& letter.equals(((CommentCell)obj).letter)
    			&& type.equals(((CommentCell)obj).type)) {
    		return true;
    	}
    	return false;
	}
	
	@Override
	public int hashCode() {
		return (type + letter + firstLink + secondLink).hashCode();
	}
}
