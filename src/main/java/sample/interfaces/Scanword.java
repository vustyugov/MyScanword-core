package sample.interfaces;

import java.util.Date;

/**
 * Created by VAUst on 08.12.2016.
 */
public interface Scanword {

    String getName ();
    int getRows ();
    int getColumns();
    void setArray (Cell[][] array);
    Cell[][] getArray ();
    void setArrayElement (Cell cell, int rowIndex, int columnIndex);
    Cell getArrayElement (int rowIndex, int columnIndex);
    void setChangingTime (Date time);
    Date getChangingTime ();
    Date getCreationTime ();

}
