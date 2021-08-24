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

        System.out.println(Util.messages.getString("welcome"));

        Util.greetings();

        if (tree.isEmpty()) {
            System.out.println(Util.messages.getString("animal.wantLearn"));
            Animal animal = Util.enterAnimal(Util.messages.getString("animal.askFavorite"));
            tree.insertAnimal(animal);
        }
        System.out.println(Util.messages.getString("welcome"));
        whileLoop:
        while (true) {
            System.out.println(Util.messages.getString("menu.property.title"));
            System.out.println("1. " + Util.messages.getString("menu.entry.play"));
            System.out.println("2. " + Util.messages.getString("menu.entry.list"));
            System.out.println("3. " + Util.messages.getString("menu.entry.search"));
//            System.out.println("4. " + Util.bundle.getString("menu.entry.delete"));
            System.out.println("4. " + Util.messages.getString("menu.entry.statistics"));
            System.out.println("5. " + Util.messages.getString("menu.entry.print"));
            System.out.println("0. " + Util.messages.getString("menu.property.exit"));
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
//                case "4":
//                    delete();
//                    break;
                case "4":
                    calc();
                    break;
                case "5":
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
        System.out.println(Util.messages.getString("tree.stats.title"));
//        String animal = scanner.nextLine();
    }

    private static void print() {
        System.out.println(Util.messages.getString("tree.stats.title"));
    }

    private static void calc() {
        System.out.println(Util.messages.getString("tree.stats.title"));
        List<String> stats = tree.stats();
        System.out.println(
                stats.stream()
                        .collect(Collectors.joining("\n - ", " - ", "\n")));
    }

    private static void search() {
        System.out.println(Util.messages.getString("animal.prompt"));
        String animal = scanner.nextLine();
        List<String> facts = tree.searchFacts(animal);
        System.err.printf("facts.size=%d\n", facts.size());
        Collections.reverse(facts);
        MessageFormat mf = new MessageFormat(Util.messages.getString("tree.search.facts"));
        System.err.println(mf.toPattern());
        Object[] arg = {animal};
        System.out.println(mf.format(arg));
        System.out.println(
                facts.stream()
                .collect(Collectors.joining("\n - ", " - ", "\n")));
    }

    private static void list() {
        List<Animal> animals = tree.getAnimals();
        System.out.println(Util.messages.getString("tree.list.animals"));
        System.out.println(animals.stream()
                .map(it -> it.name)
                .sorted()
                .collect(Collectors.joining("\n - ", " - ", "\n")));
    }

    private static void play() {
        do {
            System.out.println(Util.messages.getString("game.think") + "\n" +
                    Util.messages.getString("game.enter"));
            scanner.nextLine();
            tree.startGame();
            while (true) {
                if (tree.processAnswer(Util.getAnswer(tree.getQuestion()))) {
                    break;
                }
            }
        } while (!Util.getAnswer(
                Util.getRandomLine(
                        Util.messages.getString("game.again").split("\f")
                )).equals("No"));
    }
}
