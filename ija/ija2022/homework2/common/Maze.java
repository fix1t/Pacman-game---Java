package ija.ija2022.homework2.common;

public interface Maze {

    int numCols();

    int numRows();

    Field getField(int row, int col);
}
