package sample.impls.cell;

/**
 * Created by VAUst on 08.12.2016.
 */
public class DisableCell extends SimpleCell {

    public DisableCell() {
        super();
        linkReg = "";
        letterReg = "";
        type = "DC";
    }

    @Override
    public void setLetter(String letter) {
        super.setLetter(letter);
    }

    @Override
    public void setFirstLink(String firstLink) {
        super.setFirstLink(firstLink);
    }

    @Override
    public void setSecondLink(String secondLink) {
        super.setSecondLink(secondLink);
    }

    @Override
    public boolean equals (Object obj) {
        if (obj instanceof DisableCell) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return super.toString().replaceFirst("SC","DC");
    }
}
