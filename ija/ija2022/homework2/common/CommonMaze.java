package ija.ija2022.homework2.common;

public interface CommonMaze {

    int numCols();

    int numRows();

    CommonField getField(int row, int col);
}
