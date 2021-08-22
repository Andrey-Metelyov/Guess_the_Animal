package animals;

import utils.Util;

import java.io.IOException;
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
        System.err.println(Arrays.toString(args));

        tree.loadFromFile(type);

        Util.greetings();

        if (tree.isEmpty()) {
            System.out.println("I want to learn about animals.");
            Animal animal = Util.enterAnimal("Which animal do you like most?");
            tree.insertAnimal(animal);
        }
        System.out.println("Welcome to the animal expert system!");
        whileLoop:
        while (true) {
            System.out.println("What do you want to do:\n" +
                    "\n" +
                    "1. Play the guessing game\n" +
                    "2. List of all animals\n" +
                    "3. Search for an animal\n" +
                    "4. Calculate statistics\n" +
                    "5. Print the Knowledge Tree\n" +
                    "0. Exit");
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

    private static void print() {
    }

    private static void calc() {
    }

    private static void search() {
        System.out.println("Enter the animal:");
        String animal = scanner.nextLine();
        List<String> facts = tree.searchFacts(animal);
        Collections.reverse(facts);
        System.out.println(
                facts.stream()
                .collect(Collectors.joining("\n - ", " - ", "\n")));
    }

    private static void list() {
        List<Animal> animals = tree.getAnimals();
        System.out.println("Here are the animals I know:");
        System.out.println(animals.stream()
                .map(it -> it.name)
                .sorted()
                .collect(Collectors.joining("\n - ", " - ", "\n")));
    }

    private static void play() {
        while (true) {
            System.out.println("You think of an animal, and I guess it.\n" +
                    "Press enter when you're ready.");
            scanner.nextLine();
            tree.startGame();
            while (true) {
                if (tree.processAnswer(Util.getAnswer(tree.getQuestion()))) {
                    break;
                }
            }
            if (Util.getAnswer("Would you like to play again?").equals("No")) {
                break;
            }
        }
    }
}
