package ija.ija2022.homework2.common;

public interface Field {
    int x = -1;
    int y = -1;
    int getX();

    void setX(int x);

    int getY();

    void setY(int x);

    void setMaze(Maze maze);

    Field nextField(Field.Direction dirs);

    boolean put(MazeObject object);

    boolean isEmpty();

    boolean remove(MazeObject object);

    MazeObject get();

    boolean canMove();

    enum Direction {
        D,
        L,
        R,
        U
    }

}
