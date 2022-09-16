package Utils;

import java.util.*;

public class KeyUtils {

    private static final int MAX_KEY_LENGTH = 20;
    private ArrayList<String> keysPt;
    private ArrayList<String> keysEn;

    private HashMap<Integer, Double> coincidenceIndexPt;

    private HashMap<Integer, Double> coincidenceIndexEn;

    private HashMap<Character, Integer> ALPHABET;

    private String encryptedText;

    public KeyUtils(final String text) {
        encryptedText = text;
        keysPt = new ArrayList<>();
        keysEn = new ArrayList<>();
        coincidenceIndexPt = new HashMap<>();
        coincidenceIndexEn = new HashMap<>();
        ALPHABET = new Alphabet().getALPHABET();
    }

    public ArrayList<String> getKeysPt() {
        return keysPt;
    }

    public ArrayList<String> getKeysEn() {
        return keysEn;
    }

    public void findKeys() {
        calculateCoincidenceIndex();

        findKeysPt();

        findKeysEn();
    }

    private void findKeysPt() {
        if (!coincidenceIndexPt.isEmpty()) {
            final int indexOfO = ALPHABET.get('o');
            for (final int i : coincidenceIndexPt.keySet()) {
                final ArrayList<String> textsByKey = splitText(i);
                ArrayList<HashMap<Character, Integer>> keysFrequencies = calculateFrequency(textsByKey);

                final StringBuilder keyBuilder = new StringBuilder();
                for (final HashMap<Character, Integer> keyFrequency : keysFrequencies) {
                    keyFrequency.remove('@');
                    final LinkedHashMap<Character, Integer> sortedMap = new LinkedHashMap<>();
                    keyFrequency.entrySet()
                            .stream()
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));

                    char thirdMostFrequentChar = sortedMap.keySet().stream().toList().get(2);

                    final int encryptedIndexOfO = ALPHABET.get(thirdMostFrequentChar);
                    final int letterShift = encryptedIndexOfO - indexOfO;
                    try {
                        final char keyChar = ALPHABET.entrySet()
                                .stream()
                                .filter(characterIntegerEntry -> characterIntegerEntry.getValue() == letterShift)
                                .map(Map.Entry::getKey)
                                .findFirst()
                                .get();

                        keyBuilder.append(keyChar);
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }

                    keysPt.add(keyBuilder.toString());
                }
            }
        }
    }

    private void findKeysEn() {
        if (!coincidenceIndexEn.isEmpty()) {
            final int indexOfE = ALPHABET.get('e');
            for (final int i : coincidenceIndexEn.keySet()) {
                final ArrayList<String> textsByKey = splitText(i);
                ArrayList<HashMap<Character, Integer>> keysFrequencies = calculateFrequency(textsByKey);

                final StringBuilder keyBuilder = new StringBuilder();
                for (final HashMap<Character, Integer> keyFrequency : keysFrequencies) {
                    keyFrequency.remove('@');
                    int biggestFrequency = 0;
                    char mostFrequentChar = '#';

                    for (final char c : keyFrequency.keySet()) {
                        final long letterFrequency = keyFrequency.get(c);
                        if (letterFrequency > biggestFrequency) {
                            biggestFrequency = (int) letterFrequency;
                            mostFrequentChar = c;
                        }
                    }

                    final int encryptedIndexOfE = ALPHABET.get(mostFrequentChar);
                    final int letterShift = encryptedIndexOfE - indexOfE;
                    try {
                        final char keyChar = ALPHABET.entrySet()
                                .stream()
                                .filter(characterIntegerEntry -> characterIntegerEntry.getValue() == letterShift)
                                .map(Map.Entry::getKey)
                                .findFirst()
                                .get();

                        keyBuilder.append(keyChar);
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
                keysEn.add(keyBuilder.toString());
            }
        }
    }

    private void calculateCoincidenceIndex() {
        for (int keySize = 2; keySize < MAX_KEY_LENGTH; keySize++) {
            final ArrayList<String> encryptedSplitText = splitText(keySize);

            final ArrayList<HashMap<Character, Integer>> frequencies = calculateFrequency(encryptedSplitText);

            for (final HashMap<Character, Integer> frequency : frequencies) {
                final long subStringSize = frequency.remove('@');
                final long n = subStringSize * (subStringSize - 1);
                long sum = 0;

                for (final char c : frequency.keySet()) {
                    final long letterFrequency = frequency.get(c);
                    sum += letterFrequency * (letterFrequency - 1);
                }

                final double coincidenceIndex = (double) sum / n;

                if (coincidenceIndex > 0.07 && coincidenceIndex < 0.078)
                    coincidenceIndexPt.put(keySize, coincidenceIndex);
                else if (coincidenceIndex > 0.062 && coincidenceIndex < 0.07) {
                    coincidenceIndexEn.put(keySize, coincidenceIndex);
                }
            }
        }
    }

    private ArrayList<String> splitText(final int keySize) {
        final ArrayList<String> textSplit = new ArrayList<>();
        StringBuilder builder;

        for (int i = 0; i < keySize; i++) {
            builder = new StringBuilder();
            for (int j = i; j < encryptedText.length(); j += keySize) {
                builder.append(encryptedText.charAt(j));
            }
            textSplit.add(builder.toString());
        }

        return textSplit;
    }

    private ArrayList<HashMap<Character, Integer>> calculateFrequency(final ArrayList<String> slipText) {
        final ArrayList<HashMap<Character, Integer>> frequencies = new ArrayList<>();
        for (final String aux : slipText) {
            final HashMap<Character, Integer> analysis = new HashMap<>();
            analysis.put('@', aux.length());

            for (int i = 0; i < aux.length(); i++) {
                final char letter = aux.charAt(i);
                if (analysis.containsKey(letter)) {
                    analysis.put(letter, analysis.get(letter) + 1);
                } else {
                    analysis.putIfAbsent(letter, 1);
                }
            }
            frequencies.add(analysis);
        }

        return frequencies;
    }
}
