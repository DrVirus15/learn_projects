package com.shpp.p2p.cs.akoskovtsev.assignment7.namesurfer;

/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import com.shpp.cs.a.simple.SimpleProgram;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

public class NameSurfer extends SimpleProgram implements NameSurferConstants {

    private static JTextField childName;
    private static JButton graphButton, clearButton;
    private static NameSurferDataBase dataBase;
    private static NameSurferGraph nsGraph;

    /**
     * This method has the responsibility for reading in the database
     * and initializing the interactors at the top of the window.
     */
    public void init() {
        try {
            dataBase = new NameSurferDataBase(NameSurferConstants.NAMES_DATA_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Create and add the label "Name:" and the text field for entering names.
        JLabel nameLabel = new JLabel("Name:");
        add(nameLabel, NORTH);

        // Create and add the text field for entering names.
        childName = new JTextField(NAME_TEXT_FIELD);
        add(childName, NORTH);
        childName.setActionCommand("EnterPressed");
        childName.addActionListener(this);

        // Create and add the Graph button.
        graphButton = new JButton("Graph");
        add(graphButton, NORTH);
        graphButton.addActionListener(this);

        // Create and add the Clear button.
        clearButton = new JButton("Clear");
        add(clearButton, NORTH);
        clearButton.addActionListener(this);

        // Create and add the NameSurferGraph object.
        nsGraph = new NameSurferGraph();
        add(nsGraph);
        addActionListeners();
    }

    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        // If the user presses Enter in the text field or clicks the Graph button
        // then we will search for the name in the database and add it to the graph.
        if (!childName.getText().isEmpty() && (command.equals("EnterPressed") || e.getSource() == graphButton)) {
            NameSurferEntry nameSurferEntry = dataBase.findEntry(childName.getText());
            // If the name is found in the database, we add it to the graph.
            if (nameSurferEntry != null) {
                nsGraph.addEntry(nameSurferEntry);
                childName.setText("");
                nsGraph.update();
            } else { // If name is not found in the database, we add a message to  the console.
                System.out.println("name " + childName.getText() + " is not found.");
            }
        } else if (e.getSource() == clearButton) { // If the user clicks the Clear button, we clear the graph.
            nsGraph.clear();
            nsGraph.update();
        }
    }
}
