package animals;

import utils.Util;

public class Fact {
    public String verb;
    public String fact;

    public Fact(String verb, String fact) {
        this.verb = verb;
        this.fact = fact;
    }

    @Override
    public String toString() {
        return "Fact{" +
                "verb='" + verb + '\'' +
                ", fact='" + fact + '\'' +
                '}';
    }

    public Fact() {

    }

    public String formatQuestion() {
        return verb.substring(0, 1).toUpperCase() + verb.substring(1) + " it " + (verb.equals("does") ? "have " : "") + fact + "?";
    }

    public String formatFact(Animal animal, boolean isTrue) {
        String result;
        if (animal != null) {
            result = "The " + animal.name + " " +
                    (isTrue ? verb : Util.negative(verb)) + " " +
                    (verb.equals("does") ? "have " : "") +
                    fact + ".";
        } else {
            result = "It " +
                    (isTrue ? verb : Util.negative(verb)) + " " +
                    (verb.equals("does") ? "have " : "") +
                    fact + ".";
        }
        System.err.println(animal + " " + isTrue + " (" + this + ") -> " + result);
        return result;
    }
}
