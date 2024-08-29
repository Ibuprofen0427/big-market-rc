package com.rc.domain;

import java.util.*;

/**
 * @author renchuang
 * @date 2024/8/24
 * @Description
 */
public class MT_test1 {
    static final int INF = Integer.MAX_VALUE;
    static int[][] dist;
    static int[] bottleX, bottleY;
    static int n;


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int a = in.nextInt();
        int b = in.nextInt();
        int c = in.nextInt();
        int d = in.nextInt();
        n = in.nextInt();
        bottleX = new int[n];
        bottleY = new int[n];
        for (int i = 0; i < n; i++) {
            bottleX[i] = in.nextInt();
            bottleY[i] = in.nextInt();
        }
        dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                dist[i][j] = Math.abs(bottleX[i] - bottleX[j] + Math.abs(bottleY[i] - bottleY[j]));
            }
        }
        int[][] dp = new int[1 << n][n];
        for (int[] row : dp) {
            Arrays.fill(row, INF);
        }
        for (int i = 0; i < n; i++) {
            dp[1 << i][i] = Math.abs(a - bottleX[i]) + Math.abs(b - bottleY[i]);
        }
        for (int mask = 0; mask < (1 << n); mask++) {
            for (int u = 0; u < n; u++) {
                if ((mask & (1 << u)) == 0) continue;
                for (int v = 0; v < n; v++) {
                    if((mask&(1 << v))!=0) continue;
                    int newMask=mask | (1 << v);
                    dp[newMask][v] = Math.min(dp[newMask][v],dp[mask][u]+dist[u][v]);
                }
            }
        }
        int minCost=INF;
        for(int i=0;i<n;i++){
            minCost= Math.min(minCost, dp[(1 << n) - 1][i] + Math.abs(c - bottleX[i]) + Math.abs(d - bottleY[i]));
        }
        System.out.println(minCost);
    }
}
