import java.util.*;


/**
 * @author renchuang
 * @date 2024/8/24
 * @Description
 */
public class Mt_test3 {

    static class Item {
        int index;
        long shelfLife;
        int type;

        Item(int index, long shelfLife, int type) {
            this.index = index;
            this.shelfLife = shelfLife;
            this.type = type;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        long[] shelfLife = new long[m];
        for (int i = 0; i < m; i++) {
            shelfLife[i] = in.nextInt();
        }
        int[] types = new int[m];
        for (int i = 0; i < m; i++) {
            types[i] = in.nextInt();
        }
        boolean[] soldOut=new boolean[m];
        for (int i = 0; i < n; i++) {
            int l = in.nextInt() - 1;
            int r = in.nextInt() - 1;
            int t = in.nextInt();
            int k = in.nextInt();

            List<Item> items = new ArrayList<>();
            for (int j = l; j <= r; j++) {
                if (types[j] == t && !soldOut[j]) {
                    items.add(new Item(j + 1, shelfLife[j], types[j]));
                }
            }
            items.sort((a, b) -> {
                if (b.shelfLife != a.shelfLife) {
                    return Math.toIntExact(b.shelfLife - a.shelfLife);
                } else {
                    return a.index - b.index;
                }
            });
            List<Integer> res = new ArrayList<>();
            for (int j = 0; j < k && j < items.size(); j++) {
                res.add(items.get(j).index);
                soldOut[items.get(j).index - 1] =true;
            }
            if (res.size() < k) {
                res.add(-1);
            }
            for (int num : res) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}