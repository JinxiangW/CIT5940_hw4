import java.awt.*;
import java.util.List;

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
        return null;
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
        return null;
    }

    @Override
    public Point getTopLeft() {
        return null;
    }

    @Override
    public Point getBotRight() {
        return null;
    }

    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public IBlock getTopLeftTree() {
        return null;
    }

    @Override
    public IBlock getTopRightTree() {
        return null;
    }

    @Override
    public IBlock getBotLeftTree() {
        return null;
    }

    @Override
    public IBlock getBotRightTree() {
        return null;
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
