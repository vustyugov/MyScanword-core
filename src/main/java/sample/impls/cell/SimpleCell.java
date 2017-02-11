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

    public SimpleCell () {
        letterReg = "[\\u0410-\\u042F, \\0401]{1}";
        linkReg = "";
        type = "SC";
        letter = "";
        firstLink = "";
        secondLink = "";
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
	public void setLink(String link) {
		
	}
}
