package ija.ija2022.homework1.common;

public interface Maze {

    int numCols();

    int numRows();

    Field getField(int row, int col);
}
