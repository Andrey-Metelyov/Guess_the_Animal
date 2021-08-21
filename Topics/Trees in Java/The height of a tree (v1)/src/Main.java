import java.util.Scanner;

public class Main {
    static int[] depthes;
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        depthes = new int[size];
        int[] parents = new int[size];
        for (int i = 0; i < size; i++) {
            parents[i] = scanner.nextInt();
            depthes[i] = -1;
        }
        int max = -1;
        for (int i = 0; i < size; i++) {
            int depth = depth(i, parents);
            if (depth > max) {
                max = depth;
            }
        }
        System.out.println(max + 1);
    }
    
    public static int depth(int node, int[] parents) {
        if (depthes[node] != -1) {
            return depthes[node];
        }
        if (parents[node] == -1) {
            depthes[node] = 0;
            return 0;
        }
        int res = 1 + depth(parents[node], parents);
        depthes[node] = res;
        return res;
    }
}
