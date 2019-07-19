/* *****************************************************************************
 *  Name: Igor Blinnikov
 *  Date: June 29 - 2019
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private int numberOfOpenSites;
    private final int fakeExitIndex;
    private boolean[] sites;
    private final WeightedQuickUnionUF unionFindTopBottom;
    private final WeightedQuickUnionUF unionFindTop;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be an positive integer");
        }

        this.n = n;
        this.numberOfOpenSites = 0;
        int unionFindSize = n * n + 2;

        this.sites = new boolean[n * n];
        this.unionFindTopBottom = new WeightedQuickUnionUF(unionFindSize);
        this.unionFindTop = new WeightedQuickUnionUF(unionFindSize - 1);
        this.fakeExitIndex = unionFindSize - 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkArguments(row, col);

        if (isOpen(row, col)) {
            return;
        }

        // open
        int siteIndex = this.getSiteIndex(row, col);
        this.sites[siteIndex] = true;

        // increase open count
        this.numberOfOpenSites++;

        // union with neighbours
        int currentUFIndex = siteIndex + 1;

        // fake enter
        if (row == 1) {
            this.unionFindTopBottom.union(currentUFIndex, 0);
            this.unionFindTop.union(currentUFIndex, 0);
        }

        // top
        if (row > 1) {
            int topRow = row - 1;
            if (this.isOpen(topRow, col)) {
                this.unionFindTopBottom
                        .union(currentUFIndex, currentUFIndex - this.n);
                this.unionFindTop.union(currentUFIndex, currentUFIndex - this.n);
            }
        }

        // right
        if (col < this.n) {
            int rightCol = col + 1;
            if (this.isOpen(row, rightCol)) {
                this.unionFindTopBottom.union(currentUFIndex, currentUFIndex + 1);
                this.unionFindTop.union(currentUFIndex, currentUFIndex + 1);
            }
        }

        // bottom
        if (row < this.n) {
            int bottomRow = row + 1;
            if (this.isOpen(bottomRow, col)) {
                this.unionFindTopBottom.union(currentUFIndex, currentUFIndex + this.n);
                this.unionFindTop.union(currentUFIndex, currentUFIndex + this.n);
            }

        }

        // fake exit
        if (row == this.n) {
            this.unionFindTopBottom.union(currentUFIndex, this.fakeExitIndex);
        }

        // left
        if (col > 1) {
            int leftCol = col - 1;
            if (this.isOpen(row, leftCol)) {
                this.unionFindTopBottom.union(currentUFIndex, currentUFIndex - 1);
                this.unionFindTop.union(currentUFIndex, currentUFIndex - 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkArguments(row, col);

        int siteIndex = this.getSiteIndex(row, col);
        return this.sites[siteIndex];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkArguments(row, col);

        if (!this.isOpen(row, col)) {
            return false;
        }

        int currentUnionFindIndex = this.getSiteIndex(row, col) + 1;
        return this.unionFindTop.connected(currentUnionFindIndex, 0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.unionFindTopBottom.connected(0, fakeExitIndex);
    }

    private void checkArguments(int row, int col) {
        if (row < 1 || row > this.n || col < 1 || col > this.n) {
            throw new IllegalArgumentException("row/col is outside of prescribed range");
        }
    }

    private int getSiteIndex(int row, int col) {
        return ((row - 1) * this.n) + (col - 1);
    }
}
