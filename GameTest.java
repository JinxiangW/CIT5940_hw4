import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class GameTest {
    private Game game;
    @Before
    public void setUp() {
        Block root = new Block(new Point(0, 0), new Point(8, 8), 0, null);
        Block tl = new Block(new Point(0, 0), new Point(4, 4), 0, null);
        tl.setColor(Color.BLUE);
        tl.setParent(root);
        Block tr = new Block(new Point(4, 0), new Point(8, 4), 0, null);
        tr.setColor(Color.RED);
        tr.setParent(root);
        Block bl = new Block(new Point(0, 4), new Point(4, 8), 0, null);
        bl.setColor(Color.YELLOW);
        bl.setParent(root);
        Block br = new Block(new Point(4, 4), new Point(8, 8), 0, null);
        br.setColor(Color.GRAY);
        br.setParent(root);
        root.setTopLeftTree(tl);
        root.setTopRightTree(tr);
        root.setBotLeftTree(bl);
        root.setBotRightTree(br);
        game = new Game(3, Color.RED, root);
    }

    @Test
    public void getBlock() {
        IBlock blk = game.getBlock(4);
        assertEquals(blk, game.getRoot().getBotLeftTree());
    }

    @Test
    public void swap() {
        IBlock tl = game.getBlock(1);
        IBlock br = game.getBlock(3);
        game.swap(1, 3);
        assertEquals(tl, game.getBlock(3));
    }
}