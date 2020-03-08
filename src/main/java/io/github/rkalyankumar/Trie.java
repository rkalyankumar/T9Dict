package io.github.rkalyankumar;

import java.util.HashMap;
import java.util.Map;

public class Trie {

    static class Node {
        char ch;
        final Map<Character, Node> children = new HashMap<>();
        boolean endOfWord = false;

        public Node() {}

        public Node(char ch) {
            this.ch = ch;
        }

        public void setEndOfWord() {
            endOfWord = true;
        }

        public boolean isEndOfWord() {
            return endOfWord;
        }

        public Node getChild(char ch) {
            if (children.containsKey(ch))
                return children.get(ch);
            return null;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("");
            sb.append(ch + "\n");
            children.entrySet().forEach(e->sb.append(e.getValue() + ", "));
            sb.append("\n");
            return children.toString();
        }
    }

    public Trie() {
        root = new Node();
    }

    public void add(String word) {
        Map<Character, Node> children = root.children;
        Node node = null;
        for (int i = 0; i < word.length(); ++i) {
            char ch = word.charAt(i);
            if (children.containsKey(ch)) {
                node = children.get(ch);
            } else {
                node = new Node(ch);
                children.put(ch, node);
            }
            children = node.children;
            if (i == word.length() - 1) node.setEndOfWord();
        }
    }

    public boolean contains(String word) {
        Node node = search(word);
        if (node != null && node.isEndOfWord()) return true;
        return false;
    }

    public boolean startsWith(String prefix) {
        return search(prefix) != null;
    }

    private Node search(String word) {
        Map<Character, Node> children = root.children;
        Node node = null;
        for (int i = 0; i < word.length(); ++i) {
            if (children.containsKey(word.charAt(i))) {
                node = children.get(word.charAt(i));
                children = node.children;
            } else {
                return null;
            }
        }
        return node;
    }

    @Override
    public String toString() {
        return root.toString();
    }

    public Node getRoot() {
        return root;
    }

    private Node root;
}
