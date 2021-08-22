package utils;

import animals.Animal;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class Util {
    public static String getArticle(String word) {
        String article;
        if ("aoeiu".contains(word.substring(0, 1))) {
            article = "an";
        } else {
            article = "a";
        }
        return article;
    }

    public static Animal enterAnimal(String prompt) {
        Scanner scanner = new Scanner(System.in);
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

    public static String getAnswer(String question) {
        Scanner scanner = new Scanner(System.in);
        List<String> positiveAnswers = Arrays.asList(
                "y", "yes", "yeah", "yep", "sure", "right",
                "affirmative", "correct", "indeed", "you bet",
                "exactly", "you said it");
        List<String> negativeAnswers = Arrays.asList(
                "n", "no", "no way", "nah", "nope", "negative",
                "i don't think so", "yeah no"
        );
        String[] dontUnderstands = {
                "I'm not sure I caught you: was it yes or no?",
                "Funny, I still don't understand, is it yes or no?",
                "Oh, it's too complicated for me: just tell me yes or no.",
                "Could you please simply say yes or no?",
                "Oh, no, don't try to confuse me: say yes or no."
        };
        System.out.println(question);
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
            System.out.println(Util.getRandomLine(dontUnderstands));
        }
    }

    public static String getRandomLine(String[] lines) {
        return lines[(int) (Math.random() * lines.length)];
    }

    public static String question(String verb) {
        if (verb.equals("has")) return "does";
        return verb;
    }

    public static String negative(String verb) {
        if (verb.equals("can")) return "can't";
        if (verb.equals("has")) return "doesn't have";
        return "isn't";
    }

    public static void greetings() {
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

    public static void bye() {
        String[] byes = {
                "Have a nice day!",
                "See you soon!",
                "Bye!",
                "Пока",
                "Aufwiedesein"
        };
        System.out.println(getRandomLine(byes));
    }
}
