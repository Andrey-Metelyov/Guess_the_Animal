package animals;

import java.util.*;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        greetings();
        String animal = enterAnimal();
        System.out.println("Is it " + animal + "?");
        String answer = getAnswer();
        System.out.println("You answered: " + answer);
        bye();
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

    private static String addArticle(String word) {
        String article;
        if ("aoeiu".contains(word.substring(0, 1))) {
            article = "an";
        } else {
            article = "a";
        }
        return article + " " + word;
    }

    private static String enterAnimal() {
        System.out.println("Enter an animal:");
        String[] input = scanner.nextLine().toLowerCase().split(" ", 2);
        String animal;
        if (input.length > 1) {
            if (input[0].equals("a") || input[0].equals("an")) {
                animal = input[0] + " " + input[1];
            } else if (input[0].equals("the")) {
                animal = addArticle(input[1]);
            } else {
                animal = addArticle(input[0] + " " + input[1]);
            }
        } else {
            animal = addArticle(input[0]);
        }
        return animal;
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
