package ija.ija2022.homework2.tool.common;

import ija.ija2022.homework2.game.resources.ObjecType;

public interface CommonMazeObject {
  boolean canMove(CommonField.Direction var1);

  boolean move(CommonField.Direction var1);

  default boolean isPacman() {
    return false;
  }

  CommonField getField();

  int getLives();

  ObjecType getType();
}
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
