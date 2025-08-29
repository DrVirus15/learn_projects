package com.shpp.p2p.cs.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class WordsChain {
    private static final String FILE_PATH = "assets/en-dictionary.txt";

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH));
        String line = bufferedReader.readLine();
        HashMap<String, String> wordsMap = new HashMap<>();
        while (line != null) {
            if (line.length() > 2) {
                wordsMap.put(line.substring(0, 2), line.substring(2));
            }
            line = bufferedReader.readLine();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a start word: ");
        String word = scanner.nextLine();
        int flag = 0;
        while (flag == 0) {
            flag = 1;
            for (String key : wordsMap.keySet()) {
                String firstTwoLetters = word.toLowerCase().substring(word.length()-2);
                if (firstTwoLetters.equals(key)) {
                    word = firstTwoLetters + wordsMap.get(key);
                    System.out.println(word);
                    flag = 0;
                }
            }
        }
    }
}
