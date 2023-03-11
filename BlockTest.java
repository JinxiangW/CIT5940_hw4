import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BlockTest {

    IBlock blk;
    @Before
    public void setUp() throws Exception {
        blk = new Block(new Point(0, 0),
                new Point(8, 8),
                0, null);
    }

    @Test
    public void smash() {
        assertEquals(true, blk.isLeaf());
        blk.smash(3);
        assertEquals(false, blk.isLeaf());
        assertEquals(null, blk.getColor());
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
}