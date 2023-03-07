package ija.ija2022.homework1.common;

public interface MazeObject {
    boolean canMove(Field.Direction direction);

    boolean move(Field.Direction direction);
}
