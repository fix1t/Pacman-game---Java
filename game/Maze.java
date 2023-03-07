package ija.ija2022.homework1.game;

import ija.ija2022.homework1.common.Field;

public class Maze implements ija.ija2022.homework1.common.Maze {
    int cols;
    int rows;
    Field[][] fields;


    public Maze(int col, int row) {
        this.cols = col;
        this.rows = row;
    }

    public void setFields(Field[][] fields) {
        this.fields = fields;
    }

    @Override
    public int numCols() {
        return this.cols;
    }

    @Override
    public int numRows() {
        return this.rows;
    }

    @Override
    public Field getField(int row, int col) {
        if (row < 0 || row > this.numRows())
            return null;
        if (col < 0 || col > this.numCols())
            return null;
        return fields[row][col];
    }
}
