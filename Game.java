import java.awt.*;
import java.util.*;
import java.util.List;

public class Game implements IGame {

    private int    maxDepth;
    private Color target;
    private IBlock root;
    private List<IBlock> blocks;
    public Game(int maxDepth, Color target) {
        this.maxDepth = maxDepth;
        this.target = target;
        this.root = randomInit();
        this.blocks = new ArrayList<>();
        updateIDs();
    }

    public Game(int maxDepth, Color target, IBlock root) {
        this.maxDepth = maxDepth;
        this.target = target;
        this.root = root;
        this.blocks = new ArrayList<>();
        updateIDs();
    }

    /**
     * @return the maximum dept of this blocky board.
     */
    @Override
    public int maxDepth() {
        return maxDepth;
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
        IBlock root = new Block(new Point(tx, ty), new Point(bx, by), depth, null);
        IBlock tmp = root;
        tmp.smash(maxDepth);
        Random rd = new Random();
        while (depth < maxDepth) {
            depth++;
            int id = rd.nextInt(4);
            switch (id) {
                case (0): {
                    tmp = tmp.getTopLeftTree();
                    tmp.smash(maxDepth);
                    break;
                }
                case (1): {
                    tmp = tmp.getTopRightTree();
                    tmp.smash(maxDepth);
                    break;
                }
                case (2): {
                    tmp = tmp.getBotLeftTree();
                    tmp.smash(maxDepth);
                    break;
                }
                case (3): {
                    tmp = tmp.getBotRightTree();
                    tmp.smash(maxDepth);
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
        updateIDs();
        return blocks.get(pos);
    }

    public void updateIDs() {
        blocks.clear();
        Queue<IBlock> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            IBlock front = q.poll();
            blocks.add(front);
            if (front.isLeaf()) {
                continue;
            }
            for (IBlock i : front.children()) {
                q.add(i);
            }
        }
    }

    public int getSize() {
        updateIDs();
        return blocks.size();
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
        IBlock f = getBlock(x), s = getBlock(y),
                fp = ((Block) f).getParent(),
                sp = ((Block) s).getParent();
        int fID = ((Block) f).getID(), sID = ((Block) s).getID();
        if (f.depth() != s.depth()) {
            return;
        }
        Point ft = f.getTopLeft(), st = s.getTopLeft();
        int dx = ft.getX() - st.getX();
        int dy = ft.getY() - st.getY();
        swapHelper(f, -dx, -dy);
        swapHelper(s, dx, dy);
        ((Block) fp).setChild(fID, s);
        ((Block) sp).setChild(sID, f);
    }

    public void swapHelper(IBlock blk, int dx, int dy) {
        Point tl = blk.getTopLeft(),
                br = blk.getBotRight();
        tl.setX(tl.getX() + dx);
        tl.setY(tl.getY() + dy);
        br.setX(br.getX() + dx);
        br.setY(br.getY() + dy);
        if (!blk.isLeaf()) {
            for (IBlock i : blk.children()) {
                swapHelper(i, dx, dy);
            }
        }
    }

    /**
     * Turns (flattens) the quadtree into a 2D-array of blocks.
     * Each cell in the array represents a unit cell.
     * This method should not mutate the tree.
     * @return a 2D array of the tree
     */
    @Override
    public IBlock[][] flatten() {
        Point tl = root.getTopLeft(), br = root.getBotRight();
        int m = br.getX() - tl.getX();
        int unit = (m / (int) Math.pow(2, maxDepth));
        IBlock[][] matrix = new IBlock[m][m];
        for (IBlock i : root.children()) {
            flattenRec(matrix, i, unit);
        }
        return matrix;
    }

    public void flattenRec(IBlock[][] matrix, IBlock blk, int unit) {
        if (blk.isLeaf()) {
            flattenHelper(matrix, blk, unit);
            return;
        }
        for (IBlock i : blk.children()) {
            flattenRec(matrix, i, unit);
        }
    }

    public void flattenHelper(IBlock[][] matrix, IBlock blk, int unit) {
        Point tl = blk.getTopLeft(), br = blk.getBotRight();
        for (int i = tl.getX(); i < br.getX(); ++i) {
            for (int j = tl.getY(); j < br.getY(); ++j) {
                matrix[j / unit][i / unit] = blk;
            }
        }
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
        IBlock[][] matrix = flatten();
        int score = 0, m = matrix.length;
        for (int i = 1; i < m - 1; ++i) {
            if (matrix[0][i].getColor() == target) {
                score++;
            }
            if (matrix[i][m - 1].getColor() == target) {
                score++;
            }
            if (matrix[m - 1][m - 1 - i].getColor() == target) {
                score++;
            }
            if (matrix[m - 1 - i][0].getColor() == target) {
                score++;
            }
        }
        if (matrix[0][0].getColor() == target) {
            score += 2;
        }
        if (matrix[0][m - 1].getColor() == target) {
            score += 2;
        }
        if (matrix[m - 1][m - 1].getColor() == target) {
            score += 2;
        }
        if (matrix[m - 1][0].getColor() == target) {
            score += 2;
        }
        return score;
    }

    /**
     * This method will be useful to test your code
     * @param root the root of this blocky board
     */
    @Override
    public void setRoot(IBlock root) {
        this.root = root;
    }
}
