package animals;

import utils.Util;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static final Scanner scanner = new Scanner(System.in);
    static AnimalKnowledgeTree tree = new AnimalKnowledgeTree();

    public static void main(String[] args) throws IOException {
        String type = "json";
        if (args.length > 1) {
            if (args[0].equals("-type")) {
                type = args[1];
            }
        }

        System.err.println("\n---------*---------*---------*---------*---------*---------*---------");
        System.err.println(Arrays.toString(args));

        tree.loadFromFile(type);

        System.out.println(Util.bundle.getString("welcome"));

        Util.greetings();

        if (tree.isEmpty()) {
            System.out.println(Util.bundle.getString("animal.wantLearn"));
            Animal animal = Util.enterAnimal(Util.bundle.getString("animal.askFavorite"));
            tree.insertAnimal(animal);
        }
        System.out.println(Util.bundle.getString("welcome"));
        whileLoop:
        while (true) {
            System.out.println(Util.bundle.getString("menu.property.title"));
            System.out.println("1. " + Util.bundle.getString("menu.entry.play"));
            System.out.println("2. " + Util.bundle.getString("menu.entry.list"));
            System.out.println("3. " + Util.bundle.getString("menu.entry.search"));
            System.out.println("4. " + Util.bundle.getString("menu.entry.delete"));
            System.out.println("5. " + Util.bundle.getString("menu.entry.statistics"));
            System.out.println("6. " + Util.bundle.getString("menu.entry.print"));
            System.out.println("0. " + Util.bundle.getString("menu.property.exit"));
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    play();
                    break;
                case "2":
                    list();
                    break;
                case "3":
                    search();
                    break;
                case "4":
                    delete();
                    break;
                case "5":
                    calc();
                    break;
                case "6":
                    print();
                    break;
                case "0":
                    break whileLoop;
            }
        }
        tree.saveToFile(type);
        Util.bye();
    }

    private static void delete() {
    }

    private static void print() {
    }

    private static void calc() {
        System.out.println(Util.bundle.getString("tree.stats.title"));
        List<String> stats = tree.stats();
        System.out.println(
                stats.stream()
                        .collect(Collectors.joining("\n - ", " - ", "\n")));
    }

    private static void search() {
        System.out.println(Util.bundle.getString("animal.prompt"));
        String animal = scanner.nextLine();
        List<String> facts = tree.searchFacts(animal);
        System.err.printf("facts.size=%d\n", facts.size());
        Collections.reverse(facts);
        MessageFormat format = new MessageFormat("");
        format.applyPattern(Util.bundle.getString("tree.search.facts"));
        System.out.println(format.format(animal));
        System.out.println(
                facts.stream()
                .collect(Collectors.joining("\n - ", " - ", "\n")));
    }

    private static void list() {
        List<Animal> animals = tree.getAnimals();
        System.out.println(Util.bundle.getString("tree.list.animals"));
        System.out.println(animals.stream()
                .map(it -> it.name)
                .sorted()
                .collect(Collectors.joining("\n - ", " - ", "\n")));
    }

    private static void play() {
        do {
            System.out.println(Util.bundle.getString("game.think") + "\n" +
                    Util.bundle.getString("game.enter"));
            scanner.nextLine();
            tree.startGame();
            while (true) {
                if (tree.processAnswer(Util.getAnswer(tree.getQuestion()))) {
                    break;
                }
            }
        } while (!Util.getAnswer(
                Util.getRandomLine(
                        Util.bundle.getStringArray("game.again")
                )).equals("No"));
    }
}
