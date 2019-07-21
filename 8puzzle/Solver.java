/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {

    private class SearchNode {
        private Board board;
        private int moves;
        private SearchNode previous;

        public SearchNode(Board board) {
            this(board, 0, null);
        }

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }
    }

    private static final Comparator<SearchNode> BY_MANHATTAN_DISTANCE
            = new ByManhattanDistance();

    private static class ByManhattanDistance implements Comparator<SearchNode> {
        public int compare(SearchNode first, SearchNode second) {
            int manhattanOne = first.board.manhattan() + first.moves;
            int manhattanTwo = second.board.manhattan() + second.moves;
            return manhattanOne - manhattanTwo;
        }
    }

    private final SearchNode outcome;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        SearchNode initialSearchNode = new SearchNode(initial);

        MinPQ<SearchNode> originalQueue = new MinPQ<SearchNode>(BY_MANHATTAN_DISTANCE);
        originalQueue.insert(initialSearchNode);

        Board twinBoard = initial.twin();
        SearchNode twinSearchNode = new SearchNode(twinBoard);

        MinPQ<SearchNode> twinQueue = new MinPQ<SearchNode>(BY_MANHATTAN_DISTANCE);
        twinQueue.insert(twinSearchNode);

        this.outcome = this.solve(originalQueue, twinQueue);
    }

    private SearchNode solve(MinPQ<SearchNode> queue, MinPQ<SearchNode> twinQueue) {
        SearchNode node = queue.delMin();
        SearchNode twinNode = twinQueue.delMin();

        while (!node.board.isGoal() && !twinNode.board.isGoal()) {
            this.insertNeighbors(queue, node);
            this.insertNeighbors(twinQueue, twinNode);

            node = queue.delMin();
            twinNode = twinQueue.delMin();
        }

        return node;
    }

    private void insertNeighbors(MinPQ<SearchNode> queue, SearchNode node) {
        Iterable<Board> neighbors = node.board.neighbors();
        for (Board neighbor : neighbors) {
            if (node.previous != null && neighbor.equals(node.previous.board)) {
                continue;
            }

            int newMovesCount = node.moves + 1;
            SearchNode nodeToInsert = new SearchNode(neighbor, newMovesCount, node);
            queue.insert(nodeToInsert);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.outcome.board.isGoal();
    }

    // min number of moves to solve initial board
    public int moves() {
        if (this.isSolvable()) {
            return this.outcome.moves;
        }

        return -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Stack<Board> solution = new Stack<Board>();

        SearchNode node = this.outcome;
        while (node != null) {
            solution.push(node.board);
            node = node.previous;
        }

        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        //
        // // solve the puzzle
        Solver solver = new Solver(initial);
        //
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }
}
