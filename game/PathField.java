package ija.ija2022.homework1.game;

import ija.ija2022.homework1.common.Field;
import ija.ija2022.homework1.common.Maze;
import ija.ija2022.homework1.common.MazeObject;

public class PathField implements Field {
    int x;
    int y;
    MazeObject obj;

    public PathField(int x, int y) {
        this.x = x;
        this.y = y;
        this.obj = null;
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
    public Field nextField(Direction dirs) {
        return null;
    }

    @Override
    public boolean put(MazeObject object) {
        if (this.obj == null) {
            this.obj = object;
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return this.obj == null;
    }

    @Override
    public boolean remove(MazeObject object) {
        return false;
    }

    @Override
    public MazeObject get() {
        return null;
    }

    @Override
    public boolean canMove() {
        return true;
    }
}
