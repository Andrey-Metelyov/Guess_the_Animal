import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        Set<Integer> all = new HashSet<>();
        Set<Integer> parents = new HashSet<>();
        for (int i = 0; i < size; i++) {
            int parent = scanner.nextInt();
            int child = scanner.nextInt();
            all.add(parent);
            all.add(child);
            parents.add(parent);
        }
        all.removeAll(parents);
        System.out.println(all.size());
        System.out.println(all.stream().sorted().map(Object::toString).collect(Collectors.joining(" ")));
    }
}