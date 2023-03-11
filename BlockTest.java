import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BlockTest {

    IBlock blk;
    @Before
    public void setUp() throws Exception {
        blk = new Block(new Point(0, 0),
                new Point(8, 8),
                1, null);
    }

    @Test
    public void smash() {
        assertTrue(blk.isLeaf());
        blk.smash(3);
        assertFalse(blk.isLeaf());
        assertNull(blk.getColor());
    }

    @Test
    public void children() {
        List<IBlock> ls = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            ls.add(null);
        }
        assertEquals(ls, blk.children());
    }

    @Test
    public void rotate() {
        blk.smash(3);
        IBlock tl = blk.getTopLeftTree();
        tl.rotate();
        assertEquals(tl, blk.getTopRightTree());
    }

    @Test
    public void isLeaf() {
        assertTrue(blk.isLeaf());
        blk.smash(3);
        assertFalse(blk.isLeaf());
    }
    @Test
    public void depth() {
        assertEquals(1, blk.depth());
    }

    @Test
    public void getColor() {
        assertNull(blk.getColor());
    }

    @Test
    public void getTopLeft() {
        Point tl = new Point(0, 0);
        assertEquals(tl.getX(), blk.getTopLeft().getX());
        assertEquals(tl.getY(), blk.getTopLeft().getY());
    }

    @Test
    public void getBotRight() {
        Point br = new Point(8, 8);
        assertEquals(br.getX(), blk.getBotRight().getX());
        assertEquals(br.getY(), blk.getBotRight().getY());
    }

    @Test
    public void getTopLeftTree() {
        assertNull(blk.getTopLeftTree());
    }

    @Test
    public void getTopRightTree() {
        assertNull(blk.getTopRightTree());
    }

    @Test
    public void getBotLeftTree() {
        assertNull(blk.getBotLeftTree());
    }

    @Test
    public void getBotRightTree() {
        assertNull(blk.getBotRightTree());
    }

    @Test
    public void setColor() {
        blk.setColor(Color.BLUE);
        Color c = blk.getColor();
        assertEquals(Color.BLUE, c);
    }
}