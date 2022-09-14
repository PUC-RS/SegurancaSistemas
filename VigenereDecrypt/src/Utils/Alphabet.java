package Utils;

import java.util.HashMap;

public class Alphabet {
    private final HashMap<Character, Integer> ALPHABET;

    public Alphabet() {
        ALPHABET = new HashMap<>();

        ALPHABET.put('a', 0);
        ALPHABET.put('b', 1);
        ALPHABET.put('c', 2);
        ALPHABET.put('d', 3);
        ALPHABET.put('e', 4);
        ALPHABET.put('f', 5);
        ALPHABET.put('g', 6);
        ALPHABET.put('h', 7);
        ALPHABET.put('i', 8);
        ALPHABET.put('j', 9);
        ALPHABET.put('k', 10);
        ALPHABET.put('l', 11);
        ALPHABET.put('m', 12);
        ALPHABET.put('n', 13);
        ALPHABET.put('o', 14);
        ALPHABET.put('p', 15);
        ALPHABET.put('q', 16);
        ALPHABET.put('r', 17);
        ALPHABET.put('s', 18);
        ALPHABET.put('t', 19);
        ALPHABET.put('u', 20);
        ALPHABET.put('v', 21);
        ALPHABET.put('w', 22);
        ALPHABET.put('x', 23);
        ALPHABET.put('y', 24);
        ALPHABET.put('z', 25);
    }

    public HashMap<Character, Integer> getALPHABET() {
        return ALPHABET;
    }
}
