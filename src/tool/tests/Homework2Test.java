/*
 * IJA 2022/23: Úloha 2
 * Testovací třída.
 */
package src.tool.tests;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

//--- Importy z implementovaneho reseni ukolu
import src.game.MazeConfigure;
//---

//--- Importy z baliku dodaneho nastroje
import src.tool.MazeTester;
import src.tool.common.CommonField;
import src.tool.common.CommonMaze;
import src.tool.common.CommonMazeObject;
//---


/**
 * Testovací třída pro druhý úkol z předmětu IJA 2022/23.
 * @author Radek Kočí
 */
public class Homework2Test {

    private CommonMaze maze;

    /**
     * Vytvoří bludiště, nad kterým se provádějí testy.
     */
    @Before
    public void setUp() {
        MazeConfigure cfg = new MazeConfigure();
        cfg.startReading(4, 3);
        cfg.processLine("..G");
        cfg.processLine(".X.");
        cfg.processLine(".X.");
        cfg.processLine(".S.");
        cfg.stopReading();
        maze = cfg.createMaze();
    }

    /**
     * Test existence objektu, který reprezentuje ducha.
     * 2 body
     */
    @Test
    public void testGhosts() {
        List<CommonMazeObject> lstGhost = maze.getGhosts();
        Assert.assertEquals("Bludiste obsahuje jednoho ducha", 1, lstGhost.size());
        CommonMazeObject obj = lstGhost.remove(0);
        Assert.assertEquals("Bludiste obsahuje jednoho ducha", 1, maze.getGhosts().size());
        Assert.assertFalse("Objekt neni pacman", obj.isPacman());
        Assert.assertEquals("Objekt je na spravne pozici",
                maze.getField(1, 3),
                obj.getField());
    }

    /**
     * Test správného pohybu ducha po bludišti.
     * 2 body
     */
    @Test
    public void testGhostMoving() {
        // Ghost na pozici 1,3
        CommonMazeObject obj = maze.getGhosts().get(0);
        Assert.assertTrue("Presun na policko se podari.", obj.move(CommonField.Direction.DOWN));
        Assert.assertTrue("Presun na policko se podari.", obj.move(CommonField.Direction.DOWN));
        Assert.assertTrue("Presun na policko se podari.", obj.move(CommonField.Direction.DOWN));
        Assert.assertFalse("Presun na policko se nepodari.", obj.move(CommonField.Direction.RIGHT));
    }
      /**
     * Testování notifikací při přesunu objektu (ducha).
     * 5 bodů
     */
    @Test
    public void testNotificationGhostMoving() {
        MazeTester tester = new MazeTester(maze);

        // Ghost na pozici 1,3
        CommonMazeObject obj = maze.getGhosts().get(0);

        /* Testy, kdy se presun podari.
         * Dve prezentace policka (view) budou notifikovana o zmene (odebrani objektu a vlozeni objektu).
         * Kazde takove view bude notifikovano prave jednou.
         * Ostatni notifikovana nebudou.
         */
        testNotificationGhostMoving(tester, true, obj, CommonField.Direction.LEFT);
        testNotificationGhostMoving(tester, true, obj, CommonField.Direction.LEFT);
        testNotificationGhostMoving(tester, true, obj, CommonField.Direction.DOWN);

        /* Testy, kdy se presun nepodari (pokus vstoupit do zdi).
         * Nikdo nebude notifikovan.
         */
        testNotificationGhostMoving(tester, false, obj, CommonField.Direction.RIGHT);
    }

    /**
     * Pomocná metoda pro testování notifikací při přesunu objektu.
     * @param tester Tester nad bludištěm, který provádí vyhodnocení notifikací.
     * @param success Zda se má přesun podařit nebo ne
     * @param obj Přesouvaný objekt
     * @param dir Směr přesunu
     */
    private void testNotificationGhostMoving(MazeTester tester, boolean success, CommonMazeObject obj, CommonField.Direction dir) {
        StringBuilder msg;
        boolean res;

        // Policko, na kterem byl objekt pred zmenou
        CommonField previous = obj.getField();
        // Policko, na kterem ma byt objekt po zmene
        CommonField current = previous.nextField(dir);

        // Zadna notifikace zatim neexistuje
        res = tester.checkEmptyNotification();
        Assert.assertTrue("Zadna notifikace.", res);

        // Pokud se ma presun podarit
        if (success) {
            Assert.assertTrue("Presun na policko se podari.", obj.move(dir));
            msg = new StringBuilder();
            // Overeni spravnych notifikaci
            res = tester.checkNotification(msg, obj, current, previous);
            Assert.assertTrue("Test notifikace: " + msg, res);
        }
        // Pokud se nema presun podarit
        else {
            Assert.assertFalse("Presun na policko se nepodari.", obj.move(dir));
            // Zadne notifikace nebyly zaslany
            res = tester.checkEmptyNotification();
            Assert.assertTrue("Zadna notifikace.", res);
        }
    }
}
