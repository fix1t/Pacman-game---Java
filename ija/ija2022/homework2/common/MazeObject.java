package ija.ija2022.homework2.common;

public interface MazeObject {
    boolean canMove(Field.Direction direction);

    boolean move(Field.Direction direction);
}
