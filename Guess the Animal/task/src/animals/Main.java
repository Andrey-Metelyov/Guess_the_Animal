package animals;

import utils.Util;

import java.io.IOException;
import java.util.*;

public class Main {
    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        String type = "json";
        if (args.length > 1) {
            if (args[0].equals("-type")) {
                type = args[1];
            }
        }
        System.err.println(Arrays.toString(args));
        AnimalKnowledgeTree tree = new AnimalKnowledgeTree();

        tree.loadFromFile(type);

        Util.greetings();
        if (tree.isEmpty()) {
            System.out.println("I want to learn about animals.");
            Animal animal = Util.enterAnimal("Which animal do you like most?");
            tree.insertAnimal(animal);
            System.out.println("Wonderful! I've learned so much about animals!");
        } else {
            System.out.println("I know a lot about animals.");
        }
        while (true) {
            System.out.println("Let's play a game!\n" +
                    "You think of an animal, and I guess it.\n" +
                    "Press enter when you're ready.");
            tree.startGame();
            scanner.nextLine();
            while (true) {
                if (tree.processAnswer(Util.getAnswer(tree.getQuestion()))) {
                    break;
                }
            }
            if (Util.getAnswer("Would you like to play again?") == "No") {
                break;
            }
        }
        tree.saveToFile(type);
        Util.bye();
    }
}
