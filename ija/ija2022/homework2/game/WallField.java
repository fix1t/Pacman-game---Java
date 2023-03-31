package ija.ija2022.homework2.game;

import ija.ija2022.homework2.common.CommonField;
import ija.ija2022.homework2.common.CommonMaze;
import ija.ija2022.homework2.common.CommonMazeObject;

public class WallField implements CommonField {
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
    public void setMaze(CommonMaze maze) {

    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() == obj.getClass()) {
            CommonField f1 = (CommonField) obj;
            if (f1.getX() == this.getX())
                return f1.getY() == this.getY();
            return false;
        } else {
            return false;
        }
    }

    @Override
    public CommonField nextField(Direction dirs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean put(CommonMazeObject object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean remove(CommonMazeObject object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CommonMazeObject get() {
        return null;
    }

    @Override
    public boolean canMove() {
        return false;
    }
}
