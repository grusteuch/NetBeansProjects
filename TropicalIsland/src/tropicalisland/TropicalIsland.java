package tropicalisland;

import java.util.Scanner;

/**
 * The TropicalIsland class implements an application that
 * calculates the water volume for the islands.
 */
public class TropicalIsland {

    private static final int MIN_ALTITUDE = 1;
    private static final int MAX_ALTITUDE = 1000;
    private static final int MIN_SIZE = 1;
    private static final int MAX_SIZE = 50;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int numberOfIslands = reader.nextInt();
        int[] waterVolumes = new int[numberOfIslands]; 
        for (int i = 0; i < numberOfIslands; i++) {
            int n = reader.nextInt();
            if (n < MIN_SIZE || n > MAX_SIZE)
                throw new RuntimeException("N is not in valid interval!");
            int m = reader.nextInt();
            if (m < MIN_SIZE || m > MAX_SIZE)
                throw new RuntimeException("M is not in valid interval!");
            int[][] altitudes = new int[n][m];
            for (int k = 0; k < n; k++) {
                for (int j = 0; j < m; j++) {
                    altitudes[k][j] = reader.nextInt();
                    if (altitudes[k][j] < MIN_ALTITUDE || altitudes[k][j] > MAX_ALTITUDE)
                        throw new RuntimeException("Altitude is not in valid interval!");
                }
            }
            waterVolumes[i] = waterVolumeAfterRain(altitudes);
        }       
        for (int volume : waterVolumes) {
            System.out.println(volume);
        }
    }
    
    private static int waterVolumeAfterRain(int[][] altitudes) {
        int n = altitudes.length;
        int m = altitudes[0].length;
        int[][] changes = new int[n][m];
        boolean[][] flowing = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < m; k++) {
                if (i == 0 || i == n-1 || k == 0 || k == m-1) {
                    flowing[i][k] = true;
                    continue;
                }
                int h = altitudes[i][k];
                int minFlowingNeighbor = MAX_ALTITUDE;
                if (flowing[i-1][k])
                    minFlowingNeighbor = altitudes[i-1][k];
                if (flowing[i+1][k] && minFlowingNeighbor > altitudes[i+1][k])
                    minFlowingNeighbor = altitudes[i+1][k];
                if (flowing[i][k-1] && minFlowingNeighbor > altitudes[i][k-1])
                    minFlowingNeighbor = altitudes[i][k-1];
                if (flowing[i][k+1] && minFlowingNeighbor > altitudes[i][k+1])
                    minFlowingNeighbor = altitudes[i][k+1];
                if (minFlowingNeighbor > h) {
                    changes[i][k] = minFlowingNeighbor - h;
                    altitudes[i][k] = minFlowingNeighbor;
                }
                flowing[i][k] = true;
            }
        }
        while (waterFlow(altitudes, changes)) {
        }
        int totalChange = 0;
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < m; k++) {
                totalChange += changes[i][k];
            }
        }
        return totalChange;
    }
      
    private static boolean waterFlow(int[][] altitudes, int[][] changes) {
        int n = altitudes.length;
        int m = altitudes[0].length;
        boolean changed = false;
        for (int i = 1; i < n-1; i++) {
            for (int j = 1; j < m-1; j++) {
                int h = altitudes[i][j];
                int c = changes[i][j];
                if (altitudes[i-1][j] < h && c > 0) {
                    if (h - c < altitudes[i-1][j]) {
                        altitudes[i][j] = altitudes[i-1][j];
                        changes[i][j] = altitudes[i][j] + c - h; 
                    } else {
                        changes[i][j] = 0;
                        altitudes[i][j] = h - c;
                    }
                    changed = true;
                    h = altitudes[i][j];
                    c = changes[i][j];
                }
                if (altitudes[i+1][j] < h && c > 0) {
                    if (h - c < altitudes[i+1][j]) {
                        altitudes[i][j] = altitudes[i+1][j];
                        changes[i][j] = altitudes[i][j] + c - h; 
                    } else {
                        changes[i][j] = 0;
                        altitudes[i][j] = h - c;
                    }
                    changed = true;
                    h = altitudes[i][j];
                    c = changes[i][j];
                }
                if (altitudes[i][j-1] < h && c > 0) {
                    if (h - c < altitudes[i][j-1]) {
                        altitudes[i][j] = altitudes[i][j-1];
                        changes[i][j] = altitudes[i][j] + c - h; 
                    } else {
                        changes[i][j] = 0;
                        altitudes[i][j] = h - c;
                    }
                    changed = true;
                    h = altitudes[i][j];
                    c = changes[i][j];
                }
                if (altitudes[i][j+1] < h && c > 0) {
                    if (h - c < altitudes[i][j+1]) {
                        altitudes[i][j] = altitudes[i][j+1];
                        changes[i][j] = altitudes[i][j] + c - h; 
                    } else {
                        changes[i][j] = 0;
                        altitudes[i][j] = h - c;
                    }
                    changed = true;
                }
            }
        }
        return changed;
    }
    
}
