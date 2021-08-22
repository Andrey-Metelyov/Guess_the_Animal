package animals;

import utils.Util;

import java.io.IOException;
import java.util.*;

public class Main {
    static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        AnimalKnowledgeTree tree = new AnimalKnowledgeTree();
        Util.greetings();
        System.out.println("I want to learn about animals.");
        Animal animal = Util.enterAnimal("Which animal do you like most?");
        tree.insertAnimal(animal);
        System.out.println("Wonderful! I've learned so much about animals!");
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
        tree.saveFile();
        Util.bye();
    }

}
