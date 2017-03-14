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
        super.firstLink = "0.0";
        super.secondLink = "0.0";
        hDirection = false;
        vDirection = false;
        hWordLink = "";
        vWordLink = "";
        availibilityFirstLink = true;
        availibilitySecondLink = true;
    }

    @Override
    public void setLetter (String letter) {
        super.setLetter(letter);
    }
    
    @Override
    public boolean setLink(String link) {
    	if (super.firstLink.equals(link)
    			|| super.secondLink.equals(link)) {
    		return false;
    	}
    	else if (!super.firstLink.equals("0.0")
    			&& !super.secondLink.equals("0.0")) {
    		return false;
    	}
    	else if (!super.firstLink.equals("0.0")
    			&& super.secondLink.equals("0.0")) {
    		this.setSecondLink(link);
    		return true;
    	}
    	else if (super.firstLink.equals("0.0")
    			&& !super.secondLink.equals("0.0")){
    		this.setFirstLink(link);
    		return true;
    	}
    	else {
    		this.setFirstLink(link);
    		return true;
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
    	else {
    		return 0;
    	}
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
    public int hashCode() {
    	int result = (type + letter + firstLink + secondLink + hWordLink + vWordLink).hashCode();
    	result = 31 * result + (hDirection?1:0);
    	result = 31 * result + (vDirection?1:0);
    	return result;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj == null) {
    		return false;
    	}
    	if (this.getClass() != obj.getClass()) {
    		return false;
    	}
    	if (this.firstLink.equals(((ActiveCell)obj).firstLink)
    			&& this.secondLink.equals(((ActiveCell)obj).secondLink)
    			&& this.letter.equals(((ActiveCell)obj).letter)
    			&& this.type.equals(((ActiveCell)obj).type)
    			&& this.hDirection == (((ActiveCell)obj).hDirection)
    			&& this.vDirection == (((ActiveCell)obj).vDirection)) {
    		return true;
    	}
    	return false;
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
}
