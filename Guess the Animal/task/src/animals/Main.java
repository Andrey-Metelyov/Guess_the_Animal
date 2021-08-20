package animals;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Animal {
    String article;
    String name;

    Animal(String article, String name) {
        this.article = article;
        this.name = name;
    }

    public String nameWithArticle() {
        return article + " " + name;
    }
}

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        greetings();
        Animal animal1 = enterAnimal("Enter the first animal:");
        Animal animal2 = enterAnimal("Enter the second animal:");
        String fact = enterFact(animal1, animal2);
//        System.out.println("Is it " + animal + "?");
//        String answer = getAnswer();
//        System.out.println("You answered: " + answer);
        bye();
    }

    private static String enterFact(Animal animal1, Animal animal2) {
        String result = "";
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
                System.out.println("Is it correct for " + animal2.nameWithArticle() + "?");
                String answer = getAnswer();
                System.out.println("I learned the following facts about animals:");
                if (answer.equals("Yes")) {
                    System.out.println("- The " + animal1.name + " " + negative(verb) + " " + fact + ".");
                    System.out.println("- The " + animal2.name + " " + verb + " " + fact + ".");
                } else {
                    System.out.println("- The " + animal1.name + " " + verb + " " + fact + ".");
                    System.out.println("- The " + animal2.name + " " + negative(verb) + " " + fact + ".");
                }
                System.out.println("I can distinguish these animals by asking the question:");
                verb = question(verb);
                result = verb.substring(0, 1).toUpperCase() + verb.substring(1) + " it " + (verb.equals("does") ? "have " : "") + fact + "?";
                System.out.println("- " + result);
                break;
            } else {
                System.out.println("The examples of a statement:\n" +
                        " - It can fly\n" +
                        " - It has horn\n" +
                        " - It is a mammal");
            }
        }
        return result;
    }

    private static String question(String verb) {
        if (verb.equals("has")) return "does";
        return verb;
    }

    private static String negative(String verb) {
        if (verb.equals("can")) return "can't";
        if (verb.equals("has")) return "doesn't have";
        return "isn't";
    }

    private static void bye() {
        String[] byes = {
                "Have a nice day!",
                "See you soon!",
                "Bye!",
                "Пока",
                "Aufwiedesein"
        };
        System.out.println(getRandomLine(byes));
    }

    private static String getAnswer() {
        List<String> positiveAnswers = Arrays.asList(
                "y", "yes", "yeah", "yep", "sure", "right",
                "affirmative", "correct", "indeed", "you bet",
                "exactly", "you said it");
        List<String> negativeAnswers = Arrays.asList(
                "n", "no", "no way", "nah", "nope", "negative",
                "I don't think so", "yeah no"
        );
        String[] dontUnderstands = {
                "I'm not sure I caught you: was it yes or no?",
                "Funny, I still don't understand, is it yes or no?",
                "Oh, it's too complicated for me: just tell me yes or no.",
                "Could you please simply say yes or no?",
                "Oh, no, don't try to confuse me: say yes or no."
        };
        while (true) {
            String answer = scanner.nextLine()
                    .trim()
                    .toLowerCase()
                    .replaceAll("[.!]$", "");
            if (positiveAnswers.contains(answer)) {
                return "Yes";
            } else if (negativeAnswers.contains(answer)) {
                return "No";
            }
            System.out.println(getRandomLine(dontUnderstands));
        }
    }

    private static String getRandomLine(String[] lines) {
        return lines[(int) (Math.random() * lines.length)];
    }

    private static String getArticle(String word) {
        String article;
        if ("aoeiu".contains(word.substring(0, 1))) {
            article = "an";
        } else {
            article = "a";
        }
        return article;
    }

    private static Animal enterAnimal(String prompt) {
        System.out.println(prompt);
        String[] input = scanner.nextLine().toLowerCase().split(" ", 2);
        String animal;
        String article;
        if (input.length > 1) {
            if (input[0].equals("a") || input[0].equals("an")) {
                article = input[0];
                animal = input[1];
            } else if (input[0].equals("the")) {
                article = getArticle(input[1]);
                animal = input[1];
            } else {
                article = getArticle(input[0]);
                animal = input[0] + " " + input[1];
            }
        } else {
            article = getArticle(input[0]);
            animal = input[0];
        }
        return new Animal(article, animal);
    }

    private static void greetings() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        if (hours >= 5 && hours < 12) {
            System.out.println("Good morning");
        } else if (hours > 12 && hours < 18) {
            System.out.println("Good afternoon");
        } else {
            System.out.println("Good evening");
        }
    }
}
