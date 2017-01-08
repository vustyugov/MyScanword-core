package sample.impls.cell;

/**
 * Created by VAUst on 08.12.2016.
 */
public class ActiveCell extends SimpleCell {

    public ActiveCell () {
        linkReg = "[1-8]{1}.[1-8]{1}";
        type = "AC";
        super.letter = "";
        super.firstLink = "-1.-1";
        super.secondLink = "-1.-1";
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
