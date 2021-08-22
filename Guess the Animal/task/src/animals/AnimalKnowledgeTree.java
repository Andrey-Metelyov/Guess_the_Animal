package animals;


import animals.Animal;
import utils.Util;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnimalKnowledgeTree {
    static class Node {
        String fact;
        Animal animal;
        Node parent;
        Node yes;
        Node no;

        public Node(String fact, Node parent) {
            this.parent = parent;
            this.fact = fact;
            this.animal = null;
        }
        public Node(Animal animal, Node parent) {
            this.parent = parent;
            this.fact = null;
            this.animal = animal;
        }

        @Override
        public String toString() {
            return "fact='" + fact + '\'' + ", animal=" + animal;
        }
    }

    Node root;
    Node current;

    void enterFact(Animal animal2) {
        Scanner scanner = new Scanner(System.in);
        String result = "";
        Animal animal1 = current.animal;
        while (true) {
            System.out.println("Specify a fact that distinguishes " +
                    animal1.nameWithArticle() + " from " + animal2.nameWithArticle() + ".\n" +
                    "The sentence should be of the format: 'It can/has/is ...'.");
            String input = scanner.nextLine().trim().toLowerCase();
            Pattern pattern = Pattern.compile("^it\\s+(can|has|is)\\s+(.+)");
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                String verb = matcher.group(1);
                String fact = matcher.group(2);
                if (verb.equals("has") || verb.equals("is")) {
//                    fact = getArticle(fact);
                }
                String answer = Util.getAnswer("Is it correct for " + animal2.nameWithArticle() + "?");
                verb = Util.question(verb);
                result = verb.substring(0, 1).toUpperCase() + verb.substring(1) + " it " + (verb.equals("does") ? "have " : "") + fact + "?";
                System.out.println("I learned the following facts about animals:");
                if (answer.equals("Yes")) {
                    System.out.println("- The " + animal1.name + " " + Util.negative(verb) + " " + fact + ".");
                    System.out.println("- The " + animal2.name + " " + verb + " " + fact + ".");
                } else {
                    System.out.println("- The " + animal1.name + " " + verb + " " + fact + ".");
                    System.out.println("- The " + animal2.name + " " + Util.negative(verb) + " " + fact + ".");
                }

                insertFactAndNewAnimal(result, animal2, answer.equals("Yes"));

                System.out.println("I can distinguish these animals by asking the question:");
                System.out.println("- " + result);
                System.out.println("Nice! I've learned so much about animals!");
                break;
            } else {
                System.out.println("The examples of a statement:\n" +
                        " - It can fly\n" +
                        " - It has horn\n" +
                        " - It is a mammal");
            }
        }
    }

    public boolean processAnswer(String answer) {
        System.err.println(current + " -> " + answer);
        if (answer.equals("Yes")) {
            System.err.println(current.yes);
            if (current.animal != null) {
                // game over
                return true;
            } else if (current.yes == null) {
                Animal animal2 = Util.enterAnimal("I give up. What animal do you have in mind?");
                enterFact(animal2);
                return true;
            } else if (current.yes.animal != null) {
//                System.out.println(askAboutAnimal(current.yes.animal));
                current = current.yes;
                return false;
            } else {
                // next question
//                System.out.println(askQuestion(current.yes.fact));
                current = current.yes;
                return false;
            }
        } else {
            System.err.println(current.no);
            if (current.no == null) {
                Animal animal2 = Util.enterAnimal("I give up. What animal do you have in mind?");
                enterFact(animal2);
                return true;
            } else if (current.no.animal != null) {
//                System.out.println(askAboutAnimal(current.no.animal));
                current = current.no;
                return false;
            } else {
                // next question
//                System.out.println(askQuestion(current.no.fact));
                current = current.no;
                return false;
            }
        }
    }

    public void startGame() {
        System.err.println("Start new game");
        current = root;
        if (current == null) {
            System.err.println("empty tree");
        } else if (current.fact != null) {
            System.err.println("fact: " + current.fact);
        } else if (current.animal != null) {
            System.err.println("animal: " + current.animal.name);
        }
    }

    public String getQuestion() {
        String result = "";
        if (current.yes == null) {
            result = askAboutAnimal(current.animal);
        } else {
            result = askQuestion(current.fact);
        }
        return result;
    }

    private String askQuestion(String fact) {
        return fact;
    }

    private String askAboutAnimal(Animal animal) {
        return "Is it " + animal.article + " " + animal.name + "?";
    }

    public void insertFactAndNewAnimal(String fact, Animal secondAnimal, boolean isTrue) {
        Node newFact = new Node(fact, current.parent);
        if (isTrue) {
            newFact.yes = new Node(secondAnimal, newFact);
            newFact.no = current;
        } else {
            newFact.no = new Node(secondAnimal, newFact);
            newFact.yes = current;
        }
        if (current.parent == null) {
            root = newFact;
        } else {
            if (current.parent.yes == current) {
                current.parent.yes = newFact;
            } else {
                current.parent.no = newFact;
            }
        }
        current.parent = newFact;
    }

    public void insertAnimal(Animal animal) {
        root = new Node(animal, null);
    }


}
