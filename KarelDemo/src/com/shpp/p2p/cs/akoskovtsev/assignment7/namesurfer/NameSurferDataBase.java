package com.shpp.p2p.cs.akoskovtsev.assignment7.namesurfer;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class NameSurferDataBase implements NameSurferConstants {
    // A map to store the names and their corresponding NameSurferEntry
    private static HashMap<String, NameSurferEntry> nameMap;
    private static NameSurferEntry entry;


    /**
     * Creates a new NameSurferDataBase and initializes it using the
     * data in the specified file.  The constructor throws an error
     * exception if the requested file does not exist or if an error
     * occurs as the file is being read.
     */
    public NameSurferDataBase(String filename) throws IOException {
        nameMap = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            // Read the first line to skip the header
            String line = bufferedReader.readLine();
            while (line != null) {
                // Create a new NameSurferEntry and add it to the map
                entry = new NameSurferEntry(line);
                nameMap.put(entry.getName(), entry);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new IOException("Unable read data from file. File is empty, or file not found." + e);
        }
    }

    /**
     * Returns the NameSurferEntry associated with this name, if one
     * exists.  If the name does not appear in the database, this
     * method returns null.
     */
    public NameSurferEntry findEntry(String name) {

        String formattedName = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        return nameMap.get(formattedName);
    }
}

