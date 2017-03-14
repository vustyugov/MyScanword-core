package sample.impls.cell;

/**
 * Created by VAUst on 08.12.2016.
 */
public class CommentCell extends SimpleCell {

    public CommentCell() {
        linkReg = "[0-9]{1}.[0-9]{1}.[0,1,3,9]{1}";
        letterReg = "";
        super.type = "CC";
        super.letter = "";
        super.firstLink = "0.0.0";
        super.secondLink = "0.0.0";
    }

    @Override
    public void setLetter (String letter) {
        super.setLetter(letter);
    }

    @Override
    public boolean setLink (String link) {
    	if (super.firstLink.equals(link)
    			|| super.secondLink.equals(link)) {
    		return false;
    	}
    	else if (!super.firstLink.equals("0.0.0")
    			&& !super.secondLink.equals("0.0.0")) {
    		return false;
    	}
    	else if (!super.firstLink.equals("0.0.0")
    			&& super.secondLink.equals("0.0.0")) {
    		this.setSecondLink(link);
    		return true;
    	}
    	else if (super.firstLink.equals("0.0.0")
    			&& !super.secondLink.equals("0.0.0")){
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
    	if (firstLink.equals("9.9.9") && secondLink.equals("9.9.9")) {
    		return 0;
    	}
    	else if (firstLink.equals("9.9.9") && !secondLink.equals("9.9.9")) {
    		return 1;
    	}
    	else if (!firstLink.equals("9.9.9") && secondLink.equals("9.9.9")) {
    		return 1;
    	}
    	else {
    		return 2;
    	}
    }
    
    @Override
    public int getCountFreeLink() {
    	if (firstLink.equals("0.0.0") && secondLink.equals("0.0.0")) {
    		return 2;
    	}
    	else if ((firstLink.equals("0.0.0") && !secondLink.equals("0.0.0"))
    			|| (!firstLink.equals("0.0.0") && secondLink.equals("0.0.0"))) {
    		return 1;
    	}
    	return 0;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj == null) {
    		return false;
    	}
    	if (this.getClass() != obj.getClass()) {
    		return false;
    	}
    	if (this.firstLink.equals(((CommentCell)obj).firstLink)
    			&& this.secondLink.equals(((CommentCell)obj).secondLink)
    			&& this.letter.equals(((CommentCell)obj).letter)
    			&& this.type.equals(((CommentCell)obj).type)) {
    		return true;
    	}
    	return false;
    }
    
    @Override
    public int hashCode() {
    	return (type + letter + firstLink + secondLink).hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
