/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private final Board initialBoard;
    private final MinPQ<Board> minPQ;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.initialBoard = initial;
        this.minPQ = new MinPQ<Board>();
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.initialBoard.manhattan() == 0;
    }

    // min number of moves to solve initial board
    public int moves() {
        return -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return null;
    }

    // test client (see below)
    public static void main(String[] args) {

    }
}
