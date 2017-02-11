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
        linkReg = "[0-9]{1}.[0-9]{1}";
        type = "AC";
        super.letter = "";
        super.firstLink = "9.9";
        super.secondLink = "9.9";
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
    public void setLink(String link) {
    	if (!super.firstLink.equals("0.0")
    			&& !super.secondLink.equals("0.0")) {
    		return;
    	}
    	if (!super.firstLink.equals("0.0")) {
    		this.setSecondLink(link);
    	}
    	else {
    		this.setFirstLink(link);
    	}
    }

    @Override
    public void setFirstLink (String firstLink) {
        super.setFirstLink(firstLink);
    }

    @Override
    public void setSecondLink (String secondLink) {
        super.setSecondLink(secondLink);
    }
    
    @Override
    public int getCountAvailableLink() {
    	if(hDirection && vDirection) {
    		return 2;
    	}
    	else if (hDirection && !vDirection
    			|| !hDirection && vDirection) {
    		return 1;
    	}
   		return 0;
    }
    
    @Override
    public int getCountFreeLink() {
    	if (firstLink.equals("0.0") && secondLink.equals("0.0")) {
    		return 2;
    	}
    	if ((firstLink.equals("0.0") && !secondLink.equals("0.0"))
    			|| (!firstLink.equals("0.0") && secondLink.equals("0.0"))) {
    		return 1;
    	}
    	return 0;
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
