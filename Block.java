import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Block implements IBlock{

    /**
     * ===Representation Invariants ===
     *
     * - children.size() == 0 or children.size() == 4
     *
     * - If this Block has children,
     *      - their max_depth is the same as that of this Block,
     *      - their size is half that of this Block,
     *      - their level is one greater than that of this Block,
     *      - their position is determined by the position and
     *        size of this Block, as defined in the Assignment handout, and
     *      - this Block's color is null
     *
     * - If this Block has no children,
     *      - its color is not null
     *      - level <= max_depth
     */

    //  The top left and bottom right points delimiting this block
    private Point topLeft;
    private Point botRight;


    /**
     *  If this block is not subdivided, <color> stores its color.
     *   Otherwise, <color> is <null> and this  block's sublocks
     *   store their individual colors.
     */
    private Color color;

    // The level of this block within the overall block structure.
    //    * The outermost block corresponding to the root of the tree is at level/depth zero.
    //    * If a block is at level i, its children are at level i+1.
    private int depth;

    private IBlock topLeftTree;
    private IBlock topRightTree;
    private IBlock botLeftTree;
    private IBlock botRightTree;

    private IBlock parent;

    /**
     * No-argument constructor. This should:
     * - Initialize the top left and bottom right to two dummy points at (0, 0)
     * - Set the depth to be 0
     * - Set all parent and child pointers to null
     *
     * Even if you don't use this constructor anywhere, it may be useful for testing.
     */
    public Block() {
        topLeft = new Point(0, 0);
        botRight = new Point(0, 0);
        depth = 0;
        topLeftTree = topRightTree =
                botLeftTree = botRightTree =
                        parent = null;
    }

    // ----------------------------------------------------------
    /**
     * Create a new Quad object.
     *
     * @param topL   top left point / bound of this block
     * @param botR   bottom right point / bound of this block
     * @param depth  of this block
     * @param parent of this block
     */
    public Block(Point topL, Point botR, int depth, Block parent)
    {
        topLeft = topL;
        botRight = botR;
        this.depth = depth;
        topLeftTree = topRightTree =
                botLeftTree = botRightTree = null;
        this.parent = parent;
    }

    @Override
    public int depth() {
        return depth;
    }

    /**
     * smash this block into 4 sub block of random color. the depth of the new
     * blocks should be less than maximum depth
     *
     * @param maxDepth the max depth of this board/quadtree
     */
    @Override
    public void smash(int maxDepth) {
        if (depth == maxDepth) return;
        int tx = topLeft.getX(), ty = topLeft.getY(),
                bx = botRight.getX(), by = botRight.getY();

        topLeftTree = new Block(topLeft,
                new Point((tx + bx) / 2, (ty + by) / 2),
                depth + 1,
                this);
        topRightTree = new Block(new Point((tx + bx) / 2, ty),
                new Point(bx, (ty + by) / 2),
                depth + 1,
                this);
        botLeftTree = new Block(new Point(tx, (ty + by) / 2),
                new Point((tx + bx) / 2, by),
                depth + 1,
                this);
        botRightTree = new Block(new Point((tx + bx) / 2, (ty + by) / 2),
                botRight,
                depth + 1,
                this);

        this.color = null;
        Random rd = new Random();

        topLeftTree.setColor(COLORS[rd.nextInt(8)]);
        topRightTree.setColor(COLORS[rd.nextInt(8)]);
        botLeftTree.setColor(COLORS[rd.nextInt(8)]);
        botRightTree.setColor(COLORS[rd.nextInt(8)]);


    }

    /**
     * used by {@link IGame#randomInit()} random_init
     * to keep track of sub blocks.
     *
     * The children are returned in this order:
     * upper-left child (NE),
     * upper-right child (NW),
     * lower-right child (SW),
     * lower-left child (SE).
     *
     * @return the list of all the children of this block (clockwise order,
     *         starting with top left block)
     */
    @Override
    public List<IBlock> children() {
        List<IBlock> ret = new ArrayList<>();
        ret.add(topLeftTree);
        ret.add(topRightTree);
        ret.add(botRightTree);
        ret.add(botLeftTree);
        return ret;
    }

    /**
     * rotate this block clockwise.
     *
     *  To rotate, first move the children's pointers
     *  then recursively update the top left and
     *  bottom right points of each child.
     *
     *  You may want to write a helper method that
     *  takes in a Block and its new topLeft and botRight and
     *  sets these values for the current Block before calculating
     *  the values for its children and recursively setting them.
     */
    @Override
    public void rotate() {
        IBlock top = parent;
        if (top == null) return;
        int dist = topLeft.getX() - botRight.getX();
        ((Block) top.getTopLeftTree()).rotateHelper(dist, 0);
        ((Block) top.getTopRightTree()).rotateHelper(0, dist);
        ((Block) top.getBotLeftTree()).rotateHelper(-dist, 0);
        ((Block) top.getBotRightTree()).rotateHelper(0, -dist);
    }

    public void rotateHelper(int dx, int dy) {
        if (!isLeaf()) {
            for (IBlock i : this.children()) {
                ((Block) i).rotateHelper (dx, dy);
            }
        }
        Point tl = this.topLeft,
                br = this.botRight;
        tl.setX(tl.getX() + dx);
        tl.setY(tl.getY() + dy);
        br.setX(br.getX() + dx);
        br.setY(br.getY() + dy);
    }



    /*
     * ========================
     *  Block getters
     *  You should implement these yourself.
     *  The implementations should be very simple.
     * ========================
     */

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Point getTopLeft() {
        return topLeft;
    }

    @Override
    public Point getBotRight() {
        return botRight;
    }

    @Override
    public boolean isLeaf() {
        for (IBlock i : children()) {
            if (i != null) return false;
        }
        return true;
    }

    @Override
    public IBlock getTopLeftTree() {
        return topLeftTree;
    }

    @Override
    public IBlock getTopRightTree() {
        return topRightTree;
    }

    @Override
    public IBlock getBotLeftTree() {
        return botLeftTree;
    }

    @Override
    public IBlock getBotRightTree() {
        return botRightTree;
    }


    /*
     * ========================
     *  Provided setters
     *  Don't delete these!
     *  Necessary for testing.
     * ========================
     */

    @Override
    public void setColor(Color c) {
        this.color = c;
    }

    public void setTopLeftTree(IBlock topLeftTree) {
        this.topLeftTree = topLeftTree;
    }

    public void setTopRightTree(IBlock topRightTree) {
        this.topRightTree = topRightTree;
    }

    public void setBotLeftTree(IBlock botLeftTree) {
        this.botLeftTree = botLeftTree;
    }

    public void setBotRightTree(IBlock botRightTree) {
        this.botRightTree = botRightTree;
    }

    public void setParent(IBlock parent) {
        this.parent = parent;
    }
}
