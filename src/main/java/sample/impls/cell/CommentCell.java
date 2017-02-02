package sample.impls.cell;

/**
 * Created by VAUst on 08.12.2016.
 */
public class CommentCell extends SimpleCell {

    public CommentCell() {
        linkReg = "[1-8]{1}.[1-8]{1}.[1,3]{1}";
        letterReg = "";
        super.type = "CC";
        super.letter = "";
        super.firstLink = "-1.-1.-1";
        super.secondLink = "-1.-1.-1";
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

    @Override
    public String toString() {
        return super.toString();
    }
}
