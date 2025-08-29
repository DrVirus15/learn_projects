package com.shpp.p2p.cs.akoskovtsev.assignment7.namesurfer;

/*
 * File: NameSurferConstants.java
 * ------------------------------
 * This file declares several constants that are shared by the
 * different modules in the NameSurfer application.  Any class
 * that implements this interface can use these constants.
 */

import java.awt.*;
import java.util.ArrayList;

public interface NameSurferConstants {

    /**
     * The width of the application window
     */
    public static final int APPLICATION_WIDTH = 800;

    /**
     * The height of the application window
     */
    public static final int APPLICATION_HEIGHT = 600;

    /**
     * The name of the file containing the data
     */
    public static final String NAMES_DATA_FILE = "assets/names-data.txt";

    /**
     * The first decade in the database
     */
    public static final int START_DECADE = 1900;

    /**
     * The number of decades
     */
    public static final int NDECADES = 12;

    /**
     * The number of years in a decade
     */
    public static final int DECADE = 10;

    /**
     * The maximum rank in the database
     */
    public static final int MAX_RANK = 1000;

    /**
     * The number of pixels to reserve at the top and bottom
     */
    public static final int GRAPH_MARGIN_SIZE = 20;

    /**
     * The number of spaces to reserve for the name text field
     */
    public static final int NAME_TEXT_FIELD = 20;

    /**
     * The list of entries currently displayed  in the graph
     */
    public final ArrayList<NameSurferEntry> entries = new ArrayList<>();

    /**
     * The array of colors to cycle through for the graph lines and labels
     */
    public final Color[] colors = {Color.BLUE, Color.RED, Color.MAGENTA, Color.BLACK};

}
