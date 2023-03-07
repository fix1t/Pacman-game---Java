package ija.ija2022.homework1.game;

import ija.ija2022.homework1.common.Field;

public class MazeConfigure {
    private static final int BORDER = 2;
    boolean pacmanPlaced;
    boolean started;
    int rows;
    int cols;
    boolean errorFlag;
    int currentRow;
    Field[][] fields;

    //constructor
    public MazeConfigure() {
        this.pacmanPlaced = false;
        this.rows = 0;
        this.cols = 0;
        this.currentRow = 0;
        this.started = false;
        this.errorFlag = false;
    }

    public void startReading(int rows, int cols) {
        this.rows = rows + BORDER;
        this.cols = cols + BORDER;
        this.started = true;
        this.fields = new Field[rows + BORDER][cols + BORDER];
    }

    public boolean processLine(String line) {
        if (!this.started || this.cols - BORDER != line.length()) {
            this.errorFlag = true;
            return false;
        }

        if (this.currentRow >= this.rows - BORDER) {
            this.errorFlag = true;
            return false;
        }
        this.currentRow++;
        for (int i = 0; i < line.length(); i++) {
            switch (line.charAt(i)) {
                case '.':
                    fields[this.currentRow][i + 1] = new PathField(this.currentRow, i + 1);
                    break;
                case 'X':
                    fields[this.currentRow][i + 1] = new WallField(this.currentRow, i + 1);
                    break;
                case 'S':
                    if (this.pacmanPlaced) {
                        this.errorFlag = true;
                        return false;
                    } else {
                        this.pacmanPlaced = true;
                        fields[this.currentRow][i + 1] = new PathField(this.currentRow, i + 1);
                        fields[this.currentRow][i + 1].put(new PacmanObject(fields[this.currentRow][i + 1]));
                    }
                    break;

                default:
                    this.errorFlag = true;
                    return false;
            }
        }

        return true;
    }

    public boolean stopReading() {
        return currentRow + 1 == this.rows && this.started && !this.errorFlag;
    }

    public ija.ija2022.homework1.common.Maze createMaze() {
        if (this.errorFlag)
            return null;

        Maze newMaze = new Maze(this.cols, this.rows);
        //create border wall
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (i == 0 || i == this.rows - 1) {
                    this.fields[i][j] = new WallField(i, j);
                } else if (j == 0 || j == this.cols - 1) {
                    this.fields[i][j] = new WallField(i, j);
                }
                this.fields[i][j].setMaze(newMaze);
            }
        }
        newMaze.setFields(fields);
        return newMaze;
    }
}
