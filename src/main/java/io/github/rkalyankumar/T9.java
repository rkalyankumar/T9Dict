package io.github.rkalyankumar;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class T9 {

    public static final Map<Integer, Set<Character>> T9_KEY_MAPPINGS = new HashMap<>();

    static {
        T9_KEY_MAPPINGS.put(2, new HashSet<>(Arrays.asList('a', 'b', 'c')));
        T9_KEY_MAPPINGS.put(3, new HashSet<>(Arrays.asList('d', 'e', 'f')));
        T9_KEY_MAPPINGS.put(4, new HashSet<>(Arrays.asList('g', 'h', 'i')));
        T9_KEY_MAPPINGS.put(5, new HashSet<>(Arrays.asList('j', 'k', 'l')));
        T9_KEY_MAPPINGS.put(6, new HashSet<>(Arrays.asList('m', 'n', 'o')));
        T9_KEY_MAPPINGS.put(7, new HashSet<>(Arrays.asList('p', 'q', 'r', 's')));
        T9_KEY_MAPPINGS.put(8, new HashSet<>(Arrays.asList('t', 'u', 'v')));
        T9_KEY_MAPPINGS.put(9, new HashSet<>(Arrays.asList('w', 'x', 'y', 'z')));
    }

    public static Set<Character> getT9KeyMapping(int key) {
        if (T9_KEY_MAPPINGS.containsKey(key)) {
            return T9_KEY_MAPPINGS.get(key);
        }
        return null;
    }

    public T9() {
        trie = new Trie();
        loadSysDictionary();
    }

    public void loadSysDictionary() {
        try {
            URI uri = getClass().getClassLoader().getResource("american-english").toURI();
            List<String> words = Files.readAllLines(Paths.get(Paths.get(uri).toString()));
            words.forEach(trie::add);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public Set<String> getMatches(int[] inputKeys) {
        return getMatches(trie.getRoot(),inputKeys, 0);
    }

    private Set<String> getMatches(Trie.Node node, int[] inputKeys, int keyIdx) {
        Set<String> matches = new HashSet<>();
        if (keyIdx >= inputKeys.length) {
            if (node.isEndOfWord()) matches.add("");
            return matches;
        }
        int inputKey = inputKeys[keyIdx];
        Trie.Node childNode = null;
        for (char ch : getT9KeyMapping(inputKey)) {
            childNode = node.getChild(ch);
            if (childNode != null) {
                Set<String> suffixes = getMatches(childNode, inputKeys, keyIdx + 1);
                suffixes.forEach(suffix->matches.add("" + ch + suffix));
            }
        }
        return matches;
    }

    private Trie trie;

    public static void main(String[] args) {
        int[] keys = new int[] { 2, 2, 5, 5 };
        T9 t9 = new T9();
        System.out.println(t9.getMatches(keys));
    }
}
