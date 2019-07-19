/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double mean;
    private final double stddev;
    private final double lo;
    private final double hi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be a positive integer");
        }

        if (trials <= 0) {
            throw new IllegalArgumentException("number of trials must be a positive integer");
        }

        int totalNumberOfsites = n * n;
        double[] experiments = new double[trials];
        for (int i = 0; i < trials; i++) {
            int result = this.doExperiment(n);
            experiments[i] = result * 1.0 / totalNumberOfsites;
        }

        mean = StdStats.mean(experiments);
        stddev = StdStats.stddev(experiments);
        double delta = 1.96 * stddev / Math.sqrt(trials);
        lo = mean - delta;
        hi = mean + delta;
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return lo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return hi;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, trials);
        double mean = stats.mean();
        double stddev = stats.stddev();
        double lo = stats.confidenceLo();
        double hi = stats.confidenceHi();

        StdOut.printf("%-24s= %s\n", "mean", mean);
        StdOut.printf("%-24s= %s\n", "stddev", stddev);
        StdOut.printf("%-24s= [%s, %s]\n", "95% confidence interval", lo, hi);

    }

    private int doExperiment(int size) {
        Percolation percolation = new Percolation(size);

        while (!percolation.percolates()) {
            int row = StdRandom.uniform(1, size + 1);
            int col = StdRandom.uniform(1, size + 1);

            percolation.open(row, col);
        }

        return percolation.numberOfOpenSites();
    }
}
