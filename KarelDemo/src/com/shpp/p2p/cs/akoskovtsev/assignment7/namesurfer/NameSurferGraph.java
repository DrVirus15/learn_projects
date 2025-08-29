package com.shpp.p2p.cs.akoskovtsev.assignment7.namesurfer;

/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
        implements NameSurferConstants, ComponentListener {


    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    public NameSurferGraph() {
        addComponentListener(this);
    }


    /**
     * Clears the list of name surfer entries stored inside this class.
     */
    public void clear() {
        entries.clear();
    }

    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * Note that this method does not actually draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     */
    public void addEntry(NameSurferEntry entry) {
        if(!entries.contains(entry)) {
            entries.add(entry);
        } else {
            System.out.println("name " + entry.getName() + " is already exist.");
        }
    }


    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */
    public void update() {
        removeAll();
        drawTable();
        if (!entries.isEmpty()) {
            drawGraphics();
        }
    }

    /**
     * Draws the table with vertical lines for each decade,
     * a top line for the highest rank, and a bottom line for the lowest rank.
     * It also adds labels for each decade at the bottom of the graph.
     */
    private void drawTable() {
        // Draw vertical lines for each decade
        GLine[] verticalLines = new GLine[NDECADES];
        for (int i = 0; i < verticalLines.length; i++) {
            verticalLines[i] = new GLine(i * (getWidth() / (double) NDECADES), 0, i * (getWidth() / (double) NDECADES), getHeight());
            add(verticalLines[i]);
        }
        // Draw horizontal lines for the top and bottom margins
        GLine topLine = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
        add(topLine);
        GLine botLine = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
        add(botLine);
        // Draw labels for each decade at the bottom of the graph
        GLabel[] gLabels = new GLabel[NDECADES];
        for (int i = 0; i < gLabels.length; i++) {
            gLabels[i] = new GLabel(((START_DECADE + DECADE * i) + ""));
            gLabels[i].setFont("Verdana-18");
            add(gLabels[i], i * (getWidth() / (double) NDECADES) + 1, getHeight() - gLabels[i].getDescent());
        }
    }

    /**
     * Draws the graph for each NameSurferEntry in the entries list.
     * It draws the name and rank for each decade, and connects the points
     * with lines. The colors of the lines and labels cycle through a predefined set.
     */
    private void drawGraphics() {
        int colorCount = 0;
        ArrayList<GLabel> existingLabels = new ArrayList<>();
        for (NameSurferEntry entry : entries) {
            // If the colorCount exceeds the number of colors, reset it to 0
            if (colorCount % colors.length == 0) {
                colorCount = 0;
            }
            // Draw the name and rank for each decade
            for (int i = 0; i < NDECADES; i++) {
                String rank = " " + entry.getRank(i);
                double yPoint = (double) (getHeight() - GRAPH_MARGIN_SIZE * 2) / MAX_RANK * entry.getRank(i) + GRAPH_MARGIN_SIZE;
                // If the rank is 0, set the yPoint to the bottom of the graph
                if (entry.getRank(i) == 0) {
                    rank = " *";
                    yPoint = getHeight() - GRAPH_MARGIN_SIZE;
                }
                // Draw the name and rank.
                GLabel gLabel = new GLabel(entry.getName() + rank);
                gLabel.setColor(colors[colorCount]);
                gLabel.setLocation(i * (getWidth() / (double) NDECADES) + 2, yPoint - 1);
                // Check for intersection with existing labels
                for (GLabel label : existingLabels) {
                    // If the new label intersects with an existing label, move it up

                    if (gLabel.getBounds().intersects(label.getBounds())) {
                        gLabel.setLocation(gLabel.getX(), gLabel.getY() - gLabel.getHeight());
                    }
                }
                existingLabels.add(gLabel);
                add(gLabel);

                // Draw a line connecting the points for each decade
                if (i < NDECADES - 1) {
                    // Calculate the yPoint of the end of the line
                    double yPoint2 = (double) (getHeight() - GRAPH_MARGIN_SIZE * 2) / MAX_RANK * entry.getRank(i + 1) + GRAPH_MARGIN_SIZE;
                    // If the rank for the next decade is 0, set the yPoint2 to the bottom of the graph
                    if (entry.getRank(i + 1) == 0) {
                        yPoint2 = getHeight() - GRAPH_MARGIN_SIZE;
                    }
                    GLine gLine = new GLine(i * (getWidth() / (double) NDECADES), yPoint, (i + 1) * (getWidth() / (double) NDECADES), yPoint2);
                    gLine.setColor(colors[colorCount]);
                    add(gLine);
                }
            }
            // Increment the color count to use the next color for the next entry
            colorCount++;
        }
    }

    /**
     * This method is called whenever the component is resized.
     *
     * @param e the event to be processed
     */
    public void componentResized(ComponentEvent e) {
        update();
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }
}
