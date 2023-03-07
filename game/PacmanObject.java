package ija.ija2022.homework1.game;

import ija.ija2022.homework1.common.Field;
import ija.ija2022.homework1.common.MazeObject;

public class PacmanObject implements MazeObject {
    Field currentField;
    public PacmanObject(Field field) {
        this.currentField = field;
    }

    @Override
    public boolean canMove(Field.Direction direction) {
        return this.currentField.nextField(direction).canMove();
    }

    @Override
    public boolean move(Field.Direction direction) {
        if (this.canMove(direction)) {
            //place pacman to the next field
            this.currentField.nextField(direction).put(this.currentField.get());
            //remove pacman from this field
            this.currentField.remove(this.currentField.get());
            //change field
            this.currentField = this.currentField.nextField(direction);
            return true;
        } else {
            return false;
        }
    }
}
