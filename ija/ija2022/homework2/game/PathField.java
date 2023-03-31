package ija.ija2022.homework2.game;

import ija.ija2022.homework2.common.CommonField;
import ija.ija2022.homework2.common.CommonMazeObject;
import ija.ija2022.homework2.common.CommonMaze;

public class PathField implements CommonField {
    int x;
    int y;
    CommonMazeObject obj;
    CommonMaze maze;

    public PathField(int x, int y) {
        this.x = x;
        this.y = y;
        this.obj = null;
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
        this.maze = maze;
    }
    @Override
    public CommonField nextField(Direction dirs) {
        switch (dirs){
            case D -> {
                return this.maze.getField(this.getX() + 1, this.getY());
            }
            case L -> {
                return this.maze.getField(this.getX(), this.getY() - 1);
            }
            case R -> {
                return this.maze.getField(this.getX(), this.getY() + 1);
            }
            case U -> {
                return this.maze.getField(this.getX() - 1, this.getY());
            }
            default -> throw new UnsupportedOperationException("Unexpected value: " + dirs);
        }
    }

    @Override
    public boolean put(CommonMazeObject object) {
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
    public boolean remove(CommonMazeObject object) {
        if (this.obj == null)
            return false;
        if (object.getClass() == this.obj.getClass()){
            this.obj = null;
            return true;
        }else{
            return false;
        }
    }

    @Override
    public CommonMazeObject get() {
        return this.obj;
    }

    @Override
    public boolean canMove() {
        return true;
    }
}
