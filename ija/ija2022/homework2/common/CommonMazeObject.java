package ija.ija2022.homework2.common;

public interface CommonMazeObject {
    boolean canMove(CommonField.Direction direction);

    boolean move(CommonField.Direction direction);
}
