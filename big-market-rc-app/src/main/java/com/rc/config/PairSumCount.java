package com.rc.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PairSumCount {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 读取输入
        int N = scanner.nextInt();
        int X = scanner.nextInt();
        int[] a = new int[N];

        for (int i = 0; i < N; i++) {
            a[i] = scanner.nextInt();
        }

        // 关闭扫描器
        scanner.close();

        // 计算满足 ai + aj = X 的 (i, j) 对的数量
        System.out.println(countPairs(a, X));
    }

    private static int countPairs(int[] a, int X) {
        Map<Integer, Integer> countMap = new HashMap<>();
        int pairCount = 0;

        // 填充哈希表
        for (int num : a) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        // 计算符合条件的对数
        for (int num : a) {
            int complement = X - num;
            if (countMap.containsKey(complement)) {
                pairCount += countMap.get(complement);

                // 如果 num 和 complement 是同一个元素，则需要减去自对的情况
                if (complement == num) {
                    pairCount--;
                }
            }
        }

        // 每对 (i, j) 被计算了两次 (一次是 i, j，另一次是 j, i)，需要除以2
        return pairCount / 2;
    }
}