package utils;


public class AnimalKnowledgeTree {
    static class Node {
        String fact;
//        int value;
        Node parent;
        Node yes;
        Node no;

        public Node(String fact, Node parent) {
            this.parent = parent;
            this.fact = fact;
        }
    }

    Node root;
    Node current;

    public void startGame() {
        current = root;
    }

    public String getQuestion() {
        if (current.yes == null) {
            askAbout(current.fact);
        }
        return "";
    }

    private String askAbout(String fact) {
        return "Is it " + Util.getArticle(fact) + " " + fact;
    }

//    private void replace(Node a, Node b) {
//        if (a.parent == null) {
//            root = b;
//        } else if (a == a.parent.left) {
//            a.parent.left = b;
//        } else {
//            a.parent.right = b;
//        }
//        if (b != null) {
//            b.parent = a.parent;
//        }
//    }

    public void insert(String fact, boolean isTrue) {
        root = insert(root, null, fact, isTrue);
    }

    private Node insert(Node t, Node p, String fact, boolean isTrue) {
        if (t == null) {
            t = new Node(fact, p);
        } else {
            if (isTrue) {
                t.yes = insert(t.yes, t, fact, isTrue);
            } else {
                t.no = insert(t.no, t, fact, isTrue);
            }
        }
        return t;
    }

//    public Node search(String fact) {
//        return search(root, fact);
//    }
//
//    private Node search(Node t, String fact) {
//        if (t == null || t.fact == fact) {
//            // return null or node with key
//            return t;
//        }
//        if (fact < t.fact) {
//            return search(t.left, key);
//        } else {
//            return search(t.right, key);
//        }
//    }
}
