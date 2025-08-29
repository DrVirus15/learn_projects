package com.shpp.p2p.cs.akoskovtsev.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reads a CSV file and extracts a specified column from it.
 */
public class Assignment5Part4 extends TextProgram {
    @Override
    public void run() {
        try {
            ArrayList<String> printList = new ArrayList<>(extractColumn("assets/Test.txt", 1));
            for (String str : printList) {
                println(str);
            }
        } catch (Exception e) {
            println("Null");
            println("File not found.");
        }
    }

    /**
     * Extracts a specified column from a CSV file.
     *
     * @param filename    - the name of the CSV file to read.
     * @param columnIndex - the index of the column to extract.
     * @return - returns an ArrayList containing the values from the specified column.
     */
    private ArrayList<String> extractColumn(String filename, int columnIndex) {

        ArrayList<String> csvFile = new ArrayList<>(readTheFile(filename));
        ArrayList<String> columnArray = new ArrayList<>();
        int count = 1;
        for (String str : csvFile) {
            try {
                // add the data from the specified column to the ArrayList.
                columnArray.add(fieldsIn(str).get(columnIndex));
                count++;
            } catch (Exception e) {
                println("We have a problem with the CSV file in line: " + count);
                count++;
            }
        }
        return columnArray;
    }

    /**
     * Reads a file and returns its content as an ArrayList of strings.
     *
     * @param fileName - the name of the file to read.
     * @return - returns an ArrayList containing the lines of the file.
     * If the file cannot be read, returns null.
     */
    private ArrayList<String> readTheFile(String fileName) {
        ArrayList<String> fileLines = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                fileLines.add(line);
            }
        } catch (IOException e) {
            return null;
        }
        return fileLines;
    }

    /**
     * Splits a line of text into fields based on commas, handling quoted strings correctly.
     *
     * @param line - the line of text to split.
     * @return - returns an ArrayList containing the fields extracted from the line.
     */
    private ArrayList<String> fieldsIn(String line) {
        ArrayList<String> resultArray = new ArrayList<>();
        String oneCell = line;
        int endIndex = 0;
        // add the cells to the ArrayList until there are no more commas.
        while (endIndex != -1) {
            // finds the last index of a cell in the string.
            endIndex = findLastIndexOfOneCell(oneCell);
            // if there is a comma, we add the cell to the ArrayList.
            if (endIndex != -1) {
                // adds the cell to the ArrayList, deleting quotes if they exist.
                resultArray.add(deleteQuotes(oneCell.substring(0, endIndex)));
                endIndex++;
                // deletes the cell from the string.
                oneCell = oneCell.substring(endIndex);
                // if there is no comma, we add the last cell to the ArrayList.
            } else {
                resultArray.add(deleteQuotes(oneCell));
            }
        }
        return resultArray;
    }

    /**
     * Finds the last index of a cell in a string, considering quoted strings.
     *
     * @param string - the string to search in.
     * @return - returns the index of the last comma before the end of the cell.
     */
    private int findLastIndexOfOneCell(String string) {
        String tempStr;
        int index;
        int quoteIndex;
        index = string.indexOf(',');
        quoteIndex = string.indexOf('"');
        // if quote starts before comma, we need to find the last quote.
        if (quoteIndex < index && quoteIndex != -1) {
            quoteIndex++;
            char ch = string.charAt(quoteIndex);
            // finds next quote in the string.
            while (ch != '"') {
                quoteIndex++;
                ch = string.charAt(quoteIndex);
            }
            quoteIndex++;
            // deletes the quotes with data from the string.
            tempStr = string.substring(quoteIndex);
            // if quote is found, we do the same method for the rest of the string, to find the last comma.
            if (findLastIndexOfOneCell(tempStr) != -1) {
                index = quoteIndex + findLastIndexOfOneCell(tempStr);
            } else {
                index = -1;
            }
        }
        return index;
    }

    /**
     * Deletes quotes from a string if they exist.
     * If the string starts and ends with quotes, they are removed.
     *
     * @param oneCellString - the string from which to delete quotes.
     * @return - returns the string without quotes.
     * If the string contains double quotes, they are replaced with a single quote.
     */
    private String deleteQuotes(String oneCellString) {
        // deletes quotes at the start and end of the string.
        if (oneCellString.length() > 1) {
            if (oneCellString.charAt(0) == '"' && oneCellString.charAt(oneCellString.length() - 1) == '"') {
                oneCellString = oneCellString.substring(1, oneCellString.length() - 1);
            }
        }
        // replaces double quotes with a single quote.
        if (oneCellString.length() > 2) {
            oneCellString = oneCellString.replaceAll("\"\"", "\"");
        }
        return oneCellString;
    }

}