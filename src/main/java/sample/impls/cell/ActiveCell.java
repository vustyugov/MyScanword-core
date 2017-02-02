package sample.impls.cell;

/**
 * Created by VAUst on 08.12.2016.
 */
public class ActiveCell extends SimpleCell {
	
	protected boolean hDirection;
	protected boolean vDirection;
	protected String hWordLink;
	protected String vWordLink;

    public ActiveCell () {
        linkReg = "[1-8]{1}.[1-8]{1}";
        type = "AC";
        super.letter = "";
        super.firstLink = "-1.-1";
        super.secondLink = "-1.-1";
        hDirection = false;
        vDirection = false;
        hWordLink = "";
        vWordLink = "";
    }

    @Override
    public void setLetter (String letter) {
        super.setLetter(letter);
    }

    @Override
    public void setFirstLink (String firstLink) {
        super.setFirstLink(firstLink);
    }

    @Override
    public void setSecondLink (String secondLink) {
        super.setSecondLink(secondLink);
    }

    public void setHDirection (boolean value) {
    	hDirection = value;
    }
    
    public boolean getHDirection () {
    	return hDirection;
    }
    
    public void setVDirection (boolean value) {
    	vDirection = value;
    }
    
    public boolean getVDirection () {
    	return vDirection;
    }
    
    public void setHWordLink (String word) {
    	hWordLink = word;
    }
    
    public String getHWordLink () {
    	return hWordLink;
    }

    public void setVWordLink (String word) {
    	vWordLink = word;
    }
    
    public String getVWordLink () {
    	return vWordLink;
    }

    
    @Override
    public String toString() {
        return super.toString();
    }
}
