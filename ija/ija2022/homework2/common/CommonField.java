package ija.ija2022.homework2.common;

public interface CommonField {
    int x = -1;
    int y = -1;
    int getX();

    void setX(int x);

    int getY();

    void setY(int x);

    void setMaze(CommonMaze maze);

    CommonField nextField(CommonField.Direction dirs);

    boolean put(CommonMazeObject object);

    boolean isEmpty();

    boolean remove(CommonMazeObject object);

    CommonMazeObject get();

    boolean canMove();

    enum Direction {
        D,
        L,
        R,
        U
    }

}
