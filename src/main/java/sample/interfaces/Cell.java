package sample.interfaces;

/**
 * Created by VAUst on 08.12.2016.
 */
public interface Cell {
    void setLetter (String letter);
    String getLetter ();
    void setLink(String link);
    void setFirstLink (String firstLink);
    String getFirstLink ();
    void setSecondLink (String secondLink);
    String getSecondLink ();
    int getCountAvailableLink();
    int getCountFreeLink();
}
