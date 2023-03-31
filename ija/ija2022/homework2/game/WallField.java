package ija.ija2022.homework2.game;

import ija.ija2022.homework2.common.Field;
import ija.ija2022.homework2.common.Maze;
import ija.ija2022.homework2.common.MazeObject;

public class WallField implements Field {
    int x;
    int y;

    public WallField(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setMaze(Maze maze) {

    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() == obj.getClass()) {
            Field f1 = (Field) obj;
            if (f1.getX() == this.getX())
                return f1.getY() == this.getY();
            return false;
        } else {
            return false;
        }
    }

    @Override
    public Field nextField(Direction dirs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean put(MazeObject object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean remove(MazeObject object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public MazeObject get() {
        return null;
    }

    @Override
    public boolean canMove() {
        return false;
    }
}
