/*
 * IJA 2022/23: Úloha 2
 * Spuštění presentéru (vizualizace) implementace modelu bludiště.
 */
package ija.ija2022.homework2.tool.tests;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

//--- Importy z implementovaneho reseni ukolu
import ija.ija2022.homework2.game.MazeConfigure;
//---

//--- Importy z baliku dodaneho nastroje
import ija.ija2022.homework2.tool.MazePresenter;
import ija.ija2022.homework2.tool.common.CommonField;
import ija.ija2022.homework2.tool.common.CommonMaze;
import ija.ija2022.homework2.tool.common.CommonMazeObject;
import org.junit.Assert;

import javax.swing.*;
//---

/**
 * Třída spustí vizualizaci implementace modelu bludiště.
 * Prezentér je implementován třídou {@link MazePresenter}, dále využívá prostředky definované
 * v balíku ija.ija2022.homework2.common, který je součástí dodaného nástroje.
 * @author Radek Kočí
 */
public class Homework2 {

    public static void main(String... args) {
        MazeConfigure cfg = new MazeConfigure();
        cfg.startReading(4, 3);
        cfg.processLine("..G");
        cfg.processLine(".XT");
        cfg.processLine(".XK");
        cfg.processLine(".S.");
        cfg.stopReading();

        CommonMaze maze = cfg.createMaze();

        JFrame frame = new JFrame("Pacman Demo");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(350, 400);
        frame.setPreferredSize(new Dimension(750, 800));
        MazePresenter presenter = new MazePresenter(maze, frame, null);
        presenter.open();

        sleep(2000);

        CommonMazeObject obj = maze.getGhosts().get(0);

        obj.move(CommonField.Direction.LEFT);
        sleep(200);
        obj.move(CommonField.Direction.LEFT);
        sleep(200);
        obj.move(CommonField.Direction.DOWN);
        sleep(500);
        obj.move(CommonField.Direction.DOWN);
        sleep(500);
        obj.move(CommonField.Direction.DOWN);
        sleep(500);
        obj.move(CommonField.Direction.DOWN);
        sleep(500);
        obj.move(CommonField.Direction.RIGHT);
        sleep(500);
        Assert.assertNotNull(obj);
        Assert.assertEquals(maze.getField(1,3).get(),obj);
        obj.move(CommonField.Direction.LEFT);
        sleep(500);
        Assert.assertEquals(maze.getField(1,2).get(),obj);
    }

    /**
     * Uspani vlakna na zadany pocet ms.
     * @param ms Pocet ms pro uspani vlakna.
     */
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(Homework2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
