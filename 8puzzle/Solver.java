/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

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

    private final SearchNode originalOutcome;
    private final SearchNode twinOutcome = null;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        SearchNode initialSearchNode = new SearchNode(initial);

        MinPQ<SearchNode> originalQueue = new MinPQ<SearchNode>(BY_MANHATTAN_DISTANCE);
        originalQueue.insert(initialSearchNode);

        Board twinBoard = initial.twin();
        SearchNode twinSearchNode = new SearchNode(twinBoard);

        MinPQ<SearchNode> twinQueue = new MinPQ<SearchNode>(BY_MANHATTAN_DISTANCE);
        twinQueue.insert(twinSearchNode);

        this.originalOutcome = this.solve(originalQueue);
        // this.twinOutcome = this.solve(twinQueue);
        // StdOut.printf("Solution with %s moves --- :\n%s", this.originalOutcome.moves,
        //               this.originalOutcome.board);
    }

    private SearchNode solve(MinPQ<SearchNode> queue) {
        SearchNode node = queue.delMin();

        while (!node.board.isGoal()) {
            Iterable<Board> neighbors = node.board.neighbors();
            for (Board neighbor : neighbors) {
                if (node.previous != null && neighbor.equals(node.previous.board)) {
                    continue;
                }

                int newMovesCount = node.moves + 1;
                SearchNode nodeToInsert = new SearchNode(neighbor, newMovesCount, node);
                queue.insert(nodeToInsert);
            }

            node = queue.delMin();
        }

        return node;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.originalOutcome.board.isGoal();
    }

    // min number of moves to solve initial board
    public int moves() {
        return this.originalOutcome.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Stack<Board> solution = new Stack<Board>();

        SearchNode node = this.originalOutcome;
        while (node != null) {
            solution.push(node.board);
            node = node.previous;
        }

        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {
        int[][] tiles = new int[][] {
                { 0, 1, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 }
        };

        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);

        // create initial board from file
        // In in = new In(args[0]);
        // int n = in.readInt();
        // int[][] tiles = new int[n][n];
        // for (int i = 0; i < n; i++)
        //     for (int j = 0; j < n; j++)
        //         tiles[i][j] = in.readInt();
        // Board initial = new Board(tiles);
        //
        // // solve the puzzle
        // Solver solver = new Solver(initial);
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
