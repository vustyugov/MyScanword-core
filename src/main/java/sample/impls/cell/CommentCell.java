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
    public void setLink (String link) {
    	if (!super.firstLink.equals("0.0.0")
    			&& !super.secondLink.equals("0.0.0")) {
    		return;
    	}
    	if (!super.firstLink.equals("0.0.0")) {
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
    	return 2;
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
    public String toString() {
        return super.toString();
    }
}
