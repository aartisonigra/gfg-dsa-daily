 PROBLEAM STATEMNETS- 4)

Given an undirected graph with V vertices and E edges, represented as a 2D vector edges[][], where each entry edges[i] = [u, v] denotes an edge between vertices u and v, determine whether the graph contains a cycle or not.

Examples:

Input: V = 4, E = 4, edges[][] = [[0, 1], [0, 2], [1, 2], [2, 3]]
Output: true
Explanation: 
 
1 -> 2 -> 0 -> 1 is a cycle.
Input: V = 4, E = 3, edges[][] = [[0, 1], [1, 2], [2, 3]]
Output: false
Explanation: 
 
No cycle in the graph.

SOLUTION:

class Solution {
    // Find function with path compression
    private int find(int[] parent, int node) {
        if (parent[node] != node) {
            parent[node] = find(parent, parent[node]);
        }
        return parent[node];
    }

    // Union function
    private boolean union(int[] parent, int[] rank, int u, int v) {
        int parentU = find(parent, u);
        int parentV = find(parent, v);

        if (parentU == parentV) return true; // Cycle detected

        // Union by rank
        if (rank[parentU] < rank[parentV]) {
            parent[parentU] = parentV;
        } else if (rank[parentU] > rank[parentV]) {
            parent[parentV] = parentU;
        } else {
            parent[parentV] = parentU;
            rank[parentU]++;
        }

        return false;
    }

    public boolean isCycle(int V, int[][] edges) {
        int[] parent = new int[V];
        int[] rank = new int[V];

        // Initialize parent and rank arrays
        for (int i = 0; i < V; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        // Process each edge
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            if (union(parent, rank, u, v)) {
                return true; // Cycle found
            }
        }

        return false; // No cycle
    }
}
