package animals;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import utils.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnimalKnowledgeTree {
    static final String fileName = "animals";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    static class Node {
        public Fact fact;
        public Animal animal;
        @JsonIgnore
        public Node parent;
        public Node yes;
        public Node no;

        public Node() { }

        public Node(Fact fact, Animal animal, Node parent, Node yes, Node no) {
            this.fact = fact;
            this.animal = animal;
            this.parent = parent;
            this.yes = yes;
            this.no = no;
        }

        public Node(Fact fact, Node parent) {
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

    public List<String> searchFacts(String animal) {
//        List<Fact> facts = new ArrayList<>();
        System.err.printf("searchFacts('%s')\n", animal);
        List<String> result = new ArrayList<>();
        Node n = search(root, animal);
        System.err.printf("found node: %s\n", n);
        System.err.printf("node.parent: %s\n", n.parent);
        while (n.parent != null) {
            if (n.parent.fact != null) {
//                facts.add(n.parent.fact);
                result.add(n.parent.fact.formatFact(null, n.parent.yes == n));
            }
            n = n.parent;
        }
        return result;
    }

    public List<String> stats() {
        List<String> result = new ArrayList<>();
        List<NodeStats> nodes = new ArrayList<>();
        treeToList(root, 0, nodes);
        result.add("root node                    " +
                root.fact.formatFact(null, true));
        result.add("total number of nodes        " +
                nodes.size());
        long animalsCount = nodes.stream().filter(it -> it.node.animal != null).count();
        result.add("total number of animals      " +
                animalsCount);
        result.add("total number of statements   " +
                nodes.stream().filter(it -> it.node.animal == null).count());
        result.add("height of the tree           " +
                nodes.stream().reduce((a, b) -> a.depth > b.depth ? a : b).get().depth);
        result.add("minimum animal's depth       " +
                nodes.stream().filter(it -> it.node.animal != null)
                        .reduce((a, b) -> a.depth < b.depth ? a : b).get().depth);
        result.add("average animal's depth       " +
                String.format("%.1f", nodes.stream().filter(it -> it.node.animal != null)
                        .map(it -> it.depth)
                        .reduce(0, Integer::sum).doubleValue() / animalsCount));
        return result;
    }

    private void treeToList(Node t, int depth, List<NodeStats> list) {
        if (t == null) {
            return;
        }
        list.add(new NodeStats(t, depth));
        treeToList(t.yes, depth + 1, list);
        treeToList(t.no, depth + 1, list);
    }

    private Node search(Node t, String animal) {
//        System.err.printf("search in %s %s\n", t, animal);
        if (t == null || t.animal != null && t.animal.name.equals(animal)) {
            // return null or node with key
//            System.err.printf("search return: %s\n", t);
            return t;
        }
        Node n = search(t.no, animal);
        if (n == null) {
            return search(t.yes, animal);
        } else {
            return n;
        }
    }

    public List<Animal> getAnimals() {
        List<Animal> list = new ArrayList<>();
        dfs(root, list);
        return list;
    }

    private void dfs(Node t, List<Animal> list) {
        if (t == null) {
            return;
        }
        if (t.animal != null) {
            list.add(t.animal);
        }
        dfs(t.yes, list);
        dfs(t.no, list);
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void loadFromFile(String type) {
        String file = fileName + "." + type;
        System.err.println("loadFromFile " + file);
        ObjectMapper objectMapper;
        switch (type) {
            case "json":
                objectMapper = new JsonMapper();
                break;
            case "xml":
                objectMapper = new XmlMapper();
                break;
            case "yaml":
                objectMapper = new YAMLMapper();
                break;
            default:
                return;
        }
        try {
            root = objectMapper.readValue(new File(file), Node.class);
            fixParents(root);
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fixParents(Node t) {
        if (t.yes != null) {
            t.yes.parent = t;
            fixParents(t.yes);
        }
        if (t.no != null) {
            t.no.parent = t;
            fixParents(t.no);
        }
    }

    public void saveToFile(String type) throws IOException {
        String file = fileName + "." + type;
        System.err.println("Saving file " + file);
        ObjectMapper objectMapper;
        switch (type) {
            case "json":
                objectMapper = new JsonMapper();
                break;
            case "xml":
                objectMapper = new XmlMapper();
                break;
            case "yaml":
                objectMapper = new YAMLMapper();
                break;
            default:
                return;
        }
        objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(new File(file), root);
    }

    void enterFact(Animal animal2) {
        Scanner scanner = new Scanner(System.in);
        Animal animal1 = current.animal;
        while (true) {
            assert animal1 != null;
            System.out.println("Specify a fact that distinguishes " +
                    animal1.nameWithArticle() + " from " + animal2.nameWithArticle() + ".\n" +
                    "The sentence should be of the format: 'It can/has/is ...'.");
            String input = scanner.nextLine().trim().toLowerCase();
            Pattern pattern = Pattern.compile("^it\\s+(can|has|is)\\s+(.+)");
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                String verb = matcher.group(1);
                String fact = matcher.group(2);
                String answer = Util.getAnswer("Is the statement correct for " + animal2.nameWithArticle() + "?");
//                verb = Util.question(verb);
                Fact result = new Fact(verb, fact);
                System.out.println("I learned the following facts about animals:");
                if (answer.equals("Yes")) {
                    System.out.println(result.formatFact(animal2, true));
                    System.out.println(result.formatFact(animal1, false));
                } else {
                    System.out.println(result.formatFact(animal1, true));
                    System.out.println(result.formatFact(animal2, false));
                }

                insertFactAndNewAnimal(result, animal2, answer.equals("Yes"));

                System.out.println("I can distinguish these animals by asking the question:");
                System.out.println("- " + result.formatQuestion());
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
        String result;
        if (current.yes == null) {
            assert current.animal != null;
            result = askAboutAnimal(current.animal);
        } else {
            result = askQuestion(current.fact.formatQuestion());
        }
        return result;
    }

    private String askQuestion(String fact) {
        return fact;
    }

    private String askAboutAnimal(Animal animal) {
        return "Is it " + animal.article + " " + animal.name + "?";
    }

    public void insertFactAndNewAnimal(Fact fact, Animal secondAnimal, boolean isTrue) {
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

    private static class NodeStats {
        Node node;
        int depth;

        public NodeStats(Node node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }

//    public static void main(String[] args) throws IOException {
//        AnimalKnowledgeTree tree = new AnimalKnowledgeTree();
//        Node root = new Node("fact", null, null, null, null);
//        tree.root = root;
////        root = new Node();
//        tree.root.yes = new Node(new Animal("a", "cat"), tree.root);
//        tree.root.no = new Node("another fact", tree.root);
//        tree.root.no.yes = new Node(new Animal("a", "doggy"), tree.root.no);
//        tree.root.no.no = new Node(new Animal("a", "batty"), tree.root.no);
//        String fileName = "AnimalKnowledgeTree.json";
//        ObjectMapper objectMapper = new JsonMapper();
//
//        objectMapper
//                .writerWithDefaultPrettyPrinter()
//                .writeValue(new File(fileName), tree.root);
//    }
}
