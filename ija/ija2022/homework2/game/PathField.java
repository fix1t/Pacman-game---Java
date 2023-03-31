package ija.ija2022.homework2.game;

import ija.ija2022.homework2.common.Field;
import ija.ija2022.homework2.common.MazeObject;
import ija.ija2022.homework2.common.Maze;

public class PathField implements Field {
    int x;
    int y;
    MazeObject obj;
    Maze maze;

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
        this.maze = maze;
    }
    @Override
    public Field nextField(Direction dirs) {
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
    public MazeObject get() {
        return this.obj;
    }

    @Override
    public boolean canMove() {
        return true;
    }
}
