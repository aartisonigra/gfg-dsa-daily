PROBLEAM STATEMENTS -3)
Given a matrix mat[][] of dimension n * m where each cell in the matrix can have values 0, 1 or 2 which has the following meaning:
0 : Empty cell
1 : Cell have fresh oranges
2 : Cell have rotten oranges

We have to determine what is the earliest time after which all the oranges are rotten. A rotten orange at index (i, j) can rot other fresh orange at indexes (i-1, j), (i+1, j), (i, j-1), (i, j+1) (up, down, left and right) in a unit time.

Note: Your task is to return the minimum time to rot all the fresh oranges. If not possible returns -1.

Examples:

Input: mat[][] = [[0, 1, 2], [0, 1, 2], [2, 1, 1]]
Output: 1
Explanation: Oranges at positions (0,2), (1,2), (2,0) will rot oranges at (0,1), (1,1), (2,2) and (2,1) in unit time.
Input: mat[][] = [[2, 2, 0, 1]]
Output: -1
Explanation: Oranges at (0,0) and (0,1) can't rot orange at (0,3).
Input: mat[][] = [[2, 2, 2], [0, 2, 0]]
Output: 0
Explanation: There is no fresh orange. 


SOLUTION:
class Solution {
    public int orangesRotting(int[][] mat) {
        if (mat == null || mat.length == 0) return -1;

        int rows = mat.length;
        int cols = mat[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int freshOranges = 0;
        int time = 0;

        // Step 1: Add all rotten oranges to queue and count fresh oranges
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (mat[i][j] == 2) {
                    queue.add(new int[]{i, j}); // Add rotten orange position
                } else if (mat[i][j] == 1) {
                    freshOranges++; // Count fresh oranges
                }
            }
        }

        // If no fresh oranges, return 0 (No time needed)
        if (freshOranges == 0) return 0;

        // Step 2: Define the 4 possible directions (Up, Down, Left, Right)
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Step 3: Perform BFS
        while (!queue.isEmpty()) {
            int size = queue.size();
            boolean rotted = false; // Flag to check if any fresh orange got rotted in this round

            for (int i = 0; i < size; i++) {
                int[] rotten = queue.poll();
                int r = rotten[0], c = rotten[1];

                // Try to rot adjacent fresh oranges
                for (int[] dir : directions) {
                    int newRow = r + dir[0];
                    int newCol = c + dir[1];

                    // Check if new position is within bounds and is a fresh orange
                    if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols && mat[newRow][newCol] == 1) {
                        mat[newRow][newCol] = 2; // Make it rotten
                        queue.add(new int[]{newRow, newCol});
                        freshOranges--; // Reduce count of fresh oranges
                        rotted = true;
                    }
                }
            }

            // Increase time only if we rotted some oranges in this round
            if (rotted) time++;
        }

        // If all fresh oranges are rotten, return time, otherwise return -1
        return (freshOranges == 0) ? time : -1;
    }
}
 