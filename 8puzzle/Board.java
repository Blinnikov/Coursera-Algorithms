/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.LinkedList;

public class Board {
    private final int n;
    private int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[tiles.length][tiles.length];

        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    private class Location {
        public int row;
        public int column;

        public Location(int order) {
            this.row = (order - 1) / n;
            this.column = order % n - 1;
            if (this.column == -1) {
                this.column = n - 1;
            }
        }

        public Location(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return this.n;
    }

    // number of tiles out of place
    public int hamming() {
        int result = this.n * this.n - 1;

        for (int row = 0; row < this.n; row++) {
            for (int column = 0; column < this.n; column++) {
                int tile = this.tiles[row][column];
                int goal = row * this.n + column + 1;
                if (tile != 0 && tile == goal) {
                    result--;
                }
            }
        }

        return result;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int result = 0;

        for (int row = 0; row < this.n; row++) {
            for (int column = 0; column < this.n; column++) {
                int tile = this.tiles[row][column];
                if (tile == 0) {
                    continue;
                }

                Location goal = new Location(tile);

                int distance = Math.abs(goal.row - row) + Math.abs(goal.column - column);
                result += distance;
            }
        }

        return result;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) {
            return false;
        }

        if (!y.getClass().isAssignableFrom(Board.class)) {
            return false;
        }

        Board anotherBoard = (Board) y;
        int[][] anotherTiles = anotherBoard.tiles;
        if (anotherTiles.length != anotherTiles[0].length) {
            return false;
        }

        if (this.n != anotherTiles.length) {
            return false;
        }

        return Arrays.deepEquals(this.tiles, anotherTiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        LinkedList<Board> availableNeighbors = new LinkedList<Board>();
        Location emptyTile = findEmptyTile();

        if (emptyTile.column > 0) {
            Location leftLocation = new Location(emptyTile.row, emptyTile.column - 1);
            Board leftNeighbor = this.createNeighborBoard(emptyTile, leftLocation);
            availableNeighbors.add(leftNeighbor);
        }

        if (emptyTile.row > 0) {
            Location topLocation = new Location(emptyTile.row - 1, emptyTile.column);
            Board topNeighbor = this.createNeighborBoard(emptyTile, topLocation);
            availableNeighbors.add(topNeighbor);
        }

        if (emptyTile.column < n - 1) {
            Location rightLocation = new Location(emptyTile.row, emptyTile.column + 1);
            Board rightNeighbor = this.createNeighborBoard(emptyTile, rightLocation);
            availableNeighbors.add(rightNeighbor);
        }

        if (emptyTile.row < n - 1) {
            Location bottomLocation = new Location(emptyTile.row + 1, emptyTile.column);
            Board bottomNeighbor = this.createNeighborBoard(emptyTile, bottomLocation);
            availableNeighbors.add(bottomNeighbor);
        }

        return availableNeighbors;
    }

    private Board createNeighborBoard(Location emptyTile, Location destination) {
        Board result = new Board(this.tiles);
        swapLocations(result.tiles, emptyTile, destination);
        return result;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Location emptyTile = this.findEmptyTile();
        Board result = new Board(this.tiles);

        int originOrder;
        int destinationOrder;
        int zeroTileOrder = emptyTile.row * this.n + emptyTile.column + 1;

        // StdOut.printf("Zero at [%s, %s](%s)\n", emptyTile.row, emptyTile.column, zeroTileOrder);

        final int maxOrder = this.n * this.n + 1;

        do {
            originOrder = StdRandom.uniform(1, maxOrder);
            destinationOrder = StdRandom.uniform(1, maxOrder);
        } while (originOrder == destinationOrder || originOrder == zeroTileOrder
                || destinationOrder == zeroTileOrder);


        Location origin = new Location(originOrder);
        // StdOut.printf("Origin at [%s, %s](%s)\n", origin.row, origin.column, originOrder);
        Location destination = new Location(destinationOrder);
        // StdOut.printf("Destination at [%s, %s](%s)\n", destination.row, destination.column,
        //               destinationOrder);
        swapLocations(result.tiles, origin, destination);

        return result;
    }

    private Location findEmptyTile() {
        int row;
        int column;

        for (row = 0; row < this.n; row++) {
            for (column = 0; column < this.n; column++) {
                if (this.tiles[row][column] == 0) {
                    return new Location(row, column);
                }
            }
        }

        return null;
    }

    private void swapLocations(int[][] tiles, Location origin, Location destination) {
        int temp = tiles[destination.row][destination.column];
        tiles[destination.row][destination.column] = tiles[origin.row][origin.column];
        tiles[origin.row][origin.column] = temp;
    }

    public static void main(String[] args) {
        // testImmutability();
        // testDistances();
        // testNeighbors();
        testTwins();
    }

    private static void testImmutability() {
        int[][] testTiles = {
                { 1, 2, 3 },
                { 4, 0, 5 },
                { 6, 7, 8 }
        };

        Board testBoard = new Board(testTiles);
        Board testBoardCloned = new Board(testBoard.tiles);

        StdOut.printf("Test tiles:\n%s\n", testBoard);
        StdOut.printf("Cloned tiles:\n%s\n", testBoardCloned);
        StdOut.printf("Changing zero...\n");
        testTiles[2][2] = 100;
        testBoard.tiles[1][1] = 500;
        StdOut.printf("Test tiles:\n%s\n", testBoard);
        StdOut.printf("Cloned tiles:\n%s\n", testBoardCloned);
    }

    private static void testDistances() {
        int[][] distanceTiles = {
                { 8, 1, 3 },
                { 4, 0, 2 },
                { 7, 6, 5 }
        };

        Board board = new Board(distanceTiles);
        StdOut.println(board);
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
    }

    private static void testNeighbors() {
        int[][] neighborTiles = {
                { 1, 0, 3 },
                { 4, 2, 5 },
                { 7, 8, 6 }
        };


        Board boardWithNeighbors = new Board(neighborTiles);
        StdOut.printf("\n\nChecking neighbors:\nOriginal board:\n%s", boardWithNeighbors);
        StdOut.printf("Its neighbors:\n");

        for (Board b : boardWithNeighbors.neighbors()) {
            StdOut.printf("%s\n", b);
        }
    }

    private static void testTwins() {
        int[][] tile2x2Unsolvable = {
                { 1, 0 },
                { 2, 3 }
        };

        Board board = new Board(tile2x2Unsolvable);
        Board twin = board.twin();

        StdOut.printf("Original board:\n%s\n\n", board);
        StdOut.printf("Twin board:\n%s\n\n", twin);
    }
}
