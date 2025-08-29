package com.shpp.p2p.cs.akoskovtsev.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This program finds words in a dictionary that can be formed using a given set of letters.
 * The user inputs three letters, and the program searches for words that contain those letters
 * in the specified order, allowing for any characters in between.
 */

public class Assignment5Part3 extends TextProgram {
    @Override
    public void run() {
        try {
            ArrayList<String> allWords = new ArrayList<>(readTheFile("assets/en-dictionary.txt"));
            if (allWords.isEmpty()) {
                println("The dictionary file is empty or not found.");
                return;
            }
            while (true) {
                String letters = readLine("Enter the letters: ");
                if (letters.length() != 3 || !letters.matches("[a-zA-Z]+")) {
                    println("Invalid input. Please enter three letters.");
                    continue;
                }
                findWords(letters, allWords);
            }
        } catch (Exception e) {
            println("File not found or cannot be read.");
        }
    }

    /**
     * Finds words in the dictionary that can be formed using the given letters.
     * The letters must appear in the same order as they are given,
     * but there can be any characters in between them.
     *
     * @param letters  - the letters to search for in the dictionary.
     * @param allWords - the list of all words in the dictionary.
     */
    private void findWords(String letters, ArrayList<String> allWords) {
        // Create a regex pattern that matches the letters in order
        String patternString = letters.charAt(0) + ".*?" + letters.charAt(1) + ".*?" + letters.charAt(2);
        // Search for words that match the pattern
        int count = 0;
        for (String findWord : allWords) {
            Pattern pattern = Pattern.compile(patternString.toLowerCase());
            Matcher matcher = pattern.matcher(findWord);
            if (matcher.find()) {
                println("    " + letters + " - for this letters we find the word:    " + findWord);
                count++;
            }
        }
        if (count == 1) {
            println(count + " word found with the letters: " + letters);
        } else if(count > 1){
            println(count + " words found with the letters: " + letters);
        } else {
            println("There are no words with the letters: " + letters);
        }
    }

    /**
     * Reads a file and returns its content as an ArrayList of strings.
     *
     * @param fileName - the name of the file to read.
     * @return - returns an ArrayList containing the lines of the file.
     */
    private ArrayList<String> readTheFile(String fileName) {
        ArrayList<String> list = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            while (true) {
                String line = bufferedReader.readLine();
                if (line != null) {
                    list.add(line);
                } else {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
