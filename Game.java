import java.awt.*;
import java.util.Random;

public class Game implements IGame {

    private int    max_depth;
    private Color  target;
    private IBlock root;


    public Game(int max_depth, Color target)
    {
        this.max_depth = max_depth;
        this.target = target;
        this.root = randomInit();
//        System.out.println(root.getTopLeft() + " " + root.getBotRight() + " " + root.getColor());
    }


    /**
     * @return the maximum dept of this blocky board.
     */
    @Override
    public int maxDepth() {
        return max_depth;
    }

    /**
     * initializes a random board game. Details about how to approach
     * this are available in the assignment instructions; there is no
     * specific output that you need to generate, but calls to this
     * method should generally result in "interesting" game boards.
     *
     * @return the root of the tree of blocks
     */
    @Override
    public IBlock randomInit() {
        int depth = 0, tx = 0, ty = 0, bx = 8, by = 8;
        IBlock root = new Block(new Point(tx, ty), new Point(bx, by), depth, null), tmp = root;
        tmp.smash(max_depth);
        Random rd = new Random();
        while (depth < max_depth) {
            System.out.println(root.getTopLeft() + ", " + root.getBotRight());
            System.out.println(tmp.getTopLeft() + ", " + tmp.getBotRight());
            depth++;
            int id = rd.nextInt(4);
            switch(id) {
                case (0): {
                    tmp = tmp.getTopLeftTree();
                    tmp.smash(max_depth);
                    break;
                }
                case (1): {
                    tmp = tmp.getTopRightTree();
                    tmp.smash(max_depth);
                    break;
                }
                case (2): {
                    tmp = tmp.getBotLeftTree();
                    tmp.smash(max_depth);
                    break;
                }
                case (3): {
                    tmp = tmp.getBotRightTree();
                    tmp.smash(max_depth);
                    break;
                }
            }
        }
        return root;
    }

    /**
     * Traverse the tree of blocks to find a sub block based on its id
     *
     * @param pos the id of the block
     * @return the block with id pos or null
     */
    @Override
    public IBlock getBlock(int pos) {
        return null;
    }

    /**
     * @return the root of the quad tree representing this
     * blockly board
     *
     * @implNote getRoot() == getBlock(0)
     */
    @Override
    public IBlock getRoot() {
        return root;
    }

    /**
     * The two blocks  must be at the same level / have the same size.
     * We should be able to swap a block with no sub blocks with
     * one with sub blocks.
     *
     *
     * @param x the block to swap
     * @param y the other block to swap
     */
    @Override
    public void swap(int x, int y) {

    }

    /**
     * Turns (flattens) the quadtree into a 2D-array of blocks.
     * Each cell in the array represents a unit cell.
     * This method should not mutate the tree.
     * @return a 2D array of the tree
     */
    @Override
    public IBlock[][] flatten() {
        return new IBlock[0][];
    }

    /**
     * computes the scores based on perimeter blocks of the same color
     * as the target color.
     * The quadtree must be flattened first
     *
     * @return the score of the user (corner blocs count twice)
     */
    @Override
    public int perimeterScore() {
        return 0;
    }

    /**
     * This method will be useful to test your code
     * @param root the root of this blocky board
     */
    @Override
    public void setRoot(IBlock root) {

    }
}
