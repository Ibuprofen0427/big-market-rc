import java.util.Scanner;

public class MinOperationsToTransform {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int t = scanner.nextInt();
        while (t-- > 0) {
            int n = scanner.nextInt();
            int[] b = new int[n];
            for (int i = 0; i < n; i++) {
                b[i] = scanner.nextInt();
            }

            // 计算最小操作次数
            System.out.println(minOperations(b));
        }

        scanner.close();
    }

    private static int minOperations(int[] b) {
        int operations = 0;

        // 遍历从低到高的每一位
        while (true) {
            boolean found = false;
            int maxBit = 0;

            for (int value : b) {
                if (value > 0) {
                    found = true;
                    int bit = 0;
                    while ((value & 1) == 0) {
                        value >>= 1;
                        bit++;
                    }
                    maxBit = Math.max(maxBit, bit);
                }
            }

            if (!found) break;

            // 对当前位进行操作
            operations++;

            // 将所有值向下调整
            for (int i = 0; i < b.length; i++) {
                b[i] >>= maxBit;
            }
        }

        return operations;
    }
}