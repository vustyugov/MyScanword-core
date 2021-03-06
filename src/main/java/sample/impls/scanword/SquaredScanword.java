package sample.impls.scanword;

import sample.interfaces.Cell;
import sample.interfaces.Scanword;

import java.util.Date;

/**
 * Created by VAUst on 08.12.2016.
 */
public class SquaredScanword implements Scanword {

    private String name;
    private Cell[][] array;
    private Date creationTime;
    private Date changingTime;

    public SquaredScanword (String name) {
    	this.name = name;
        creationTime = new Date ();
        changingTime = new Date ();
    }
    
    public SquaredScanword (String name, int rows, int columns) {
    	this(name);
        array = new Cell[rows][columns];
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getRows() {
        return array.length;
    }

    @Override
    public int getColumns() {
        return array[0].length;
    }

    @Override
    public void setArray(Cell[][] array) {
        this.array = array;
    }

    @Override
    public Cell[][] getArray() {
        return array;
    }

    @Override
    public void setArrayElement(Cell cell, int rowIndex, int columnIndex) {
        try {
            array[rowIndex][columnIndex] = cell;
        } catch (ArrayIndexOutOfBoundsException err) {
            err.getCause();
        }
    }

    @Override
    public Cell getArrayElement(int rowIndex, int columnIndex) {
        if (rowIndex < getRows() && rowIndex >= 0
                && columnIndex < getColumns() && columnIndex >= 0) {
            return array[rowIndex][columnIndex];
        }
        else {
            return null;
        }
    }

    @Override
    public void setChangingTime(Date time) {
        this.changingTime = time;
    }

    @Override
    public Date getChangingTime() {
        return changingTime;
    }

    @Override
    public Date getCreationTime() {
        return creationTime;
    }
    
    @Override 
    public String toString() {
    	StringBuffer buf = new StringBuffer();
    	buf.append(name);
    	buf.append("\n");
    	buf.append(array.length);
    	buf.append(" ");
    	buf.append(array[0].length);
    	buf.append("\n");
    	for (int indexR = 0; indexR < array.length; indexR++) {
    		for (int indexC = 0; indexC < array[0].length; indexC++) {
    			buf.append(String.format("%-28s", array[indexR][indexC]));
    		}
    		buf.append("\n");
    	}
    	return buf.toString();
    }
}
