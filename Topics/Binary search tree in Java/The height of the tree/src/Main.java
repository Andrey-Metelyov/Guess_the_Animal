import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Integer.max;

class Main {
    public static void main(String[] args) {
        Tree tree = new Tree();
        Scanner scanner = new Scanner(System.in);
        int size = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < size; i++) {
            tree.insert(scanner.nextInt(), 0);
        }
        System.out.println(tree.height() - 1);
    }
}

class Tree {
    static class Node {
        int key;
        int value;
        Node parent;
        Node left;
        Node right;

        public Node(int key, int value, Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }
    }
    Node root;

    public int height() {
        return height(root);
    }

    private int height(Node t) {
        if (t == null) {
            return 0;
        }
        return max(height(t.left), height(t.right)) + 1;
    }

    public void insert(int key, int value) {
        root = insert(root, null, key, value);
    }

    List<Integer> traverse() {
        List<Integer> list = new ArrayList<>();
        traverse(list, root);
        return list;
    }

    private void traverse(List<Integer> list, Node t) {
        if (t == null) {
            return;
        }
        if (t.left != null) {
            traverse(list, t.left);
        }
        list.add(t.value);
        traverse(list, t.right);
    }

    public void remove(int key) {
        remove(root, key);
    }

    private void remove(Node t, int key) {
        if (t == null) {
            return;
        }
        if (key < t.key) {
            remove(t.left, key);
        } else if (key > t.key) {
            remove(t.right, key);
        } else if (t.left != null && t.right != null) {
            Node m = t.right;
            while (m.left != null) {
                m = m.left;
            }
            t.key = m.key;
            t.value = m.value;
            replace(m, m.right);
        } else if (t.left != null) {
            replace(t, t.left);
        } else if (t.right != null) {
            replace(t, t.right);
        } else {
            replace(t, null);
        }
    }

    private void replace(Node a, Node b) {
        if (a.parent == null) {
            root = b;
        } else if (a == a.parent.left) {
            a.parent.left = b;
        } else {
            a.parent.right = b;
        }
        if (b != null) {
            b.parent = a.parent;
        }
    }

    private Node insert(Node t, Node p, int key, int value) {
        if (t == null) {
            t = new Node(key, value, p);
        } else {
            if (key < t.key) {
                t.left = insert(t.left, t, key, value);
            } else {
                t.right = insert(t.right, t, key, value);
            }
        }
        return t;
    }

    public Node search(int key) {
        return search(root, key);
    }

    private Node search(Node t, int key) {
        if (t == null || t.key == key) {
            // return null or node with key
            return t;
        }
        if (key < t.key) {
            return search(t.left, key);
        } else {
            return search(t.right, key);
        }
    }
}
