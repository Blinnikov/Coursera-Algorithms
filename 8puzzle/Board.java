/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int n;
    private int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = tiles;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String firstLine = String.format("%s\n", this.n);
        sb.append(firstLine);

        for (int i = 0; i < this.n; i++) {
            StringBuilder row = new StringBuilder();

            for (int j = 0; j < this.n; j++) {
                String item = String.format(" %s", this.tiles[i][j]);
                row.append(item);
            }

            row.append("\n");
            sb.append(row);
        }

        return sb.toString();
    }

    // board dimension n
    public int dimension() {
        return this.n;
    }

    // number of tiles out of place
    public int hamming() {
        return -1;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return -1;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    public static void main(String[] args) {
        int[][] testTiles = {
                { 1, 2, 3 },
                { 4, 0, 5 },
                { 6, 7, 8 }
        };

        Board board = new Board(testTiles);
        StdOut.println(board);
    }
}
