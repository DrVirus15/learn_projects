package com.shpp.p2p.cs.akoskovtsev.assignment7.namesurfer;

/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import java.util.*;

public class NameSurferEntry implements NameSurferConstants {
    // Name of the person.
    public final String name;
    // Array giving the popularity
    // of that name for each decade stretching back to 1900
    public final Integer[] values;

    /**
     * Creates a new NameSurferEntry from a data line as it appears
     * in the data file.  Each line begins with the name, which is
     * followed by integers giving the rank of that name for each
     * decade.
     */
    public NameSurferEntry(String line) {
        values = new Integer[NDECADES];
        // split the line into parts
        String[] elem = line.split(" ");
        // the first part is the name of the person
        name = elem[0];
        // parse the rest of the parts as integers
        for (int i = 1; i < elem.length; i++) {
            try {
                values[i - 1] = Integer.parseInt(elem[i]);
            } catch (Exception e) {
                System.out.println("Fail to parse integer");
            }
        }
    }

    /**
     * Returns the name associated with this entry.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the rank associated with an entry for a particular
     * decade.  The decade value is an integer indicating how many
     * decades have passed since the first year in the database,
     * which is given by the constant START_DECADE.  If a name does
     * not appear in a decade, the rank value is 0.
     */
    public int getRank(int decade) {
        if (decade < 0 || decade >= NDECADES) {
            return 0;
        } else {
            return values[decade];
        }
    }

    /**
     * Returns a string that makes it easy to see the value of a
     * NameSurferEntry.
     */
    public String toString() {
        return name + " " + Arrays.toString(values);
    }
}

