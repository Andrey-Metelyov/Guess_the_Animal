import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        Map<Integer, Integer> tree = new HashMap<>();
        for (int i = 0; i < size; i++) {
            int parent = scanner.nextInt();
            int child = scanner.nextInt();
            if (tree.containsKey(parent)) {
                tree.put(parent, tree.get(parent) + 1);
            } else {
                tree.put(parent, 1);
            }
        }
        if (tree.values().stream().anyMatch(it -> it != 2)) {
            System.out.println("no");
        } else {
            System.out.println("yes");
        }
    }
}