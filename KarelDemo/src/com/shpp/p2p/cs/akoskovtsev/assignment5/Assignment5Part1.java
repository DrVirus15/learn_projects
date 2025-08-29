package com.shpp.p2p.cs.akoskovtsev.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.util.ArrayList;
/**
 * This program estimates the number of syllables in a word.
 * It prompts the user to enter a single word and prints the estimated syllable count.
 */
public class Assignment5Part1 extends TextProgram {
    @Override
    public void run() {
        /* Repeatedly prompt the user for a word and print out the estimated
         * number of syllables in that word.
         */
        while (true) {
            String word = readLine("Enter a single word: ");
            if (isWord(word) && !word.isEmpty()) {
                println("  Syllable count: " + syllablesInWord(word));
            } else {
                println("Invalid input. Enter a single word.");
            }
        }
    }


    /**
     * Checks if the given string is a valid word.
     * A valid word consists only of letters and is not empty.
     *
     * @param word The string to check.
     * @return true if the string is a valid word, false otherwise.
     */
    private boolean isWord(String word) {
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!Character.isLetter(ch)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Given a word, estimates the number of syllables in that word according to the
     * heuristic specified in the handout.
     *
     * @param word A string containing a single word.
     * @return An estimate of the number of syllables in that word.
     */
    private int syllablesInWord(String word) {
        int count = 0;
        boolean isDoubleVowelLetter = false;
        // Convert the word to lowercase to handle case insensitivity.
        word = word.toLowerCase();
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            // Check if the character is a vowel.
            if (isVowel(ch)) {
                // Check if the character is vowel and not the last character.
                if (!isDoubleVowelLetter) {
                    // Special case for 'e' at the end of the word.
                    if (ch == 'e') {
                        // If 'e' is the last character, it does not count as a syllable.
                        if (i != word.length() - 1) {
                            isDoubleVowelLetter = true;
                            count++;
                        }
                        continue;
                    }
                    count++;
                }
                isDoubleVowelLetter = true;
            } else isDoubleVowelLetter = false;
        }
        // If there are no vowels in the word, we count it as one syllable.
        if (count == 0) count++;
        return count;
    }

    /**
     * Checks if the given character is a vowel.
     * Vowels are defined as 'a', 'e', 'i', 'o', 'u', and 'y'.
     *
     * @param c The character to check.
     * @return true if the character is a vowel, false otherwise.
     */
    private boolean isVowel(char c) {
        ArrayList<Character> vowels = new ArrayList<>();
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
        vowels.add('y');
        return vowels.contains(c);
    }
}