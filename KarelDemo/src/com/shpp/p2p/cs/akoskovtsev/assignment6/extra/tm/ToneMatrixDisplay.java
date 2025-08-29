package com.shpp.p2p.cs.akoskovtsev.assignment6.extra.tm;

import acm.graphics.GCanvas;
import acm.graphics.GRect;

import java.awt.*;
import java.awt.event.*;

public class ToneMatrixDisplay extends GCanvas implements MouseListener, MouseMotionListener, ComponentListener {
    private static final int GSCALE = 20;
    private static final Color LIT_COLOR = new Color(232, 232, 232);
    private static final Color LIT_GREEN_COLOR = Color.GREEN;
    private static final Color LIT_BLUE_COLOR = Color.BLUE;
    private static final Color UNLIT_COLOR = (new Color(134, 110, 255)).darker();

    private static final Color HIGHLIGHT_RECT_COLOR = new Color(255, 255, 0, 128);
    private static final Color BACKGROUND_COLOR;
    private int[][] matrix = new int[ToneMatrixConstants.size()][ToneMatrixConstants.size()];
    private GRect[][] matrixRects = new GRect[ToneMatrixConstants.size()][ToneMatrixConstants.size()];
    private double xScale;
    private double yScale;
    private double xTrans;
    private double yTrans;
    private boolean isMarking;
    private GRect highlightRect;
    private volatile int highlightedColumn = -1;

    static {
        BACKGROUND_COLOR = Color.GRAY;
    }

    public ToneMatrixDisplay() {
        this.setBackground(BACKGROUND_COLOR);

        for (int i = 0; i < ToneMatrixConstants.size(); ++i) {
            for (int j = 0; j < ToneMatrixConstants.size(); ++j) {
                this.matrixRects[i][j] = new GRect(0.0D, 0.0D, 0.0D, 0.0D);
                this.matrixRects[i][j].setFilled(true);
                this.matrixRects[i][j].setColor(Color.BLACK);
                this.matrixRects[i][j].setFillColor(UNLIT_COLOR);
                this.add(this.matrixRects[i][j]);
            }
        }
        this.highlightRect = new GRect(0.0D, 0.0D, 0.0D, 0.0D);
        this.highlightRect.setFilled(true);
        this.highlightRect.setColor(HIGHLIGHT_RECT_COLOR);
        this.add(this.highlightRect);
        this.setPreferredSize(new Dimension(ToneMatrixConstants.size() * 20, ToneMatrixConstants.size() * 20));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addComponentListener(this);
    }

    private void redrawAll() {
        for (int row = 0; row < ToneMatrixConstants.size(); ++row) {
            for (int col = 0; col < ToneMatrixConstants.size(); ++col) {
                this.matrixRects[row][col].setBounds(
                        this.xScale * (double) col + this.xTrans,
                        this.yScale * (double) row + this.yTrans,
                        this.xScale,
                        this.yScale
                );
                if (this.matrix[row][col] == 0) {
                    this.matrixRects[row][col].setFillColor(UNLIT_COLOR);
                } else if (this.matrix[row][col] == 1) {
                    this.matrixRects[row][col].setFillColor(LIT_COLOR);
                } else if (this.matrix[row][col] == 2) {
                    this.matrixRects[row][col].setFillColor(LIT_GREEN_COLOR);
                } else {
                    this.matrixRects[row][col].setFillColor(LIT_BLUE_COLOR);
                }
            }
        }

        if (this.highlightedColumn == -1) {
            this.highlightRect.setBounds(0.0D, 0.0D, 0.0D, 0.0D);
        } else {
            this.highlightRect.setBounds(
                    this.xScale * (double) this.highlightedColumn + this.xTrans,
                    this.yTrans,
                    this.xScale,
                    this.yScale * (double) ToneMatrixConstants.size()
            );
        }
    }

    private void recalculateGeometry() {
        this.xScale = (double) this.getWidth() / (double) ToneMatrixConstants.size();
        this.yScale = (double) this.getHeight() / (double) ToneMatrixConstants.size();
        this.xScale = this.yScale = Math.min(this.xScale, this.yScale);
        this.xTrans = ((double) this.getWidth() - this.xScale * (double) ToneMatrixConstants.size()) / 2.0D;
        this.yTrans = ((double) this.getHeight() - this.yScale * (double) ToneMatrixConstants.size()) / 2.0D;
    }

    private boolean inBounds(int row, int col) {
        return row >= 0 && col >= 0 && row < ToneMatrixConstants.size() && col < ToneMatrixConstants.size();
    }

    private void markAt(int row, int col, boolean set) {
        if (!inBounds(row, col)) return;

        if (set) {
            this.matrix[row][col] = (this.matrix[row][col] + 1) % 4;
        }

        Color color;
        switch (this.matrix[row][col]) {
            case 1:  color = LIT_COLOR; break;       // білий
            case 2:  color = LIT_GREEN_COLOR; break; // зелений
            case 3:  color = LIT_BLUE_COLOR; break;  // голубий
            default: color = UNLIT_COLOR;            // вимкнено
        }
        this.matrixRects[row][col].setFillColor(color);
    }

    public void clear() {
        for (int i = 0; i < ToneMatrixConstants.size(); ++i) {
            for (int j = 0; j < ToneMatrixConstants.size(); ++j) {
                this.markAt(i, j, false);
            }
        }
        System.out.println("Clear button is not implemented in this version.");
    }

    public void mouseDragged(MouseEvent e) {
        int x = (int) (((double) e.getX() - this.xTrans) / this.xScale);
        int y = (int) (((double) e.getY() - this.yTrans) / this.yScale);

        markAt(y, x, true);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        int x = (int) (((double) e.getX() - this.xTrans) / this.xScale);
        int y = (int) (((double) e.getY() - this.yTrans) / this.yScale);
        if (this.inBounds(y, x)) {
            markAt(y, x, true);
        }
        redrawAll();
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mouseReleased(MouseEvent arg0) {
    }

    public void mouseMoved(MouseEvent arg0) {
    }

    public void componentHidden(ComponentEvent arg0) {
    }

    public void componentMoved(ComponentEvent arg0) {
    }

    public void componentShown(ComponentEvent arg0) {
    }

    public void componentResized(ComponentEvent arg0) {
        this.recalculateGeometry();
        this.redrawAll();
    }

    public int[][] getMatrix() {
        int[][] result = new int[ToneMatrixConstants.size()][ToneMatrixConstants.size()];

        for (int i = 0; i < ToneMatrixConstants.size(); ++i) {
            for (int j = 0; j < ToneMatrixConstants.size(); ++j) {
                result[i][j] = this.matrix[ToneMatrixConstants.size() - 1 - i][j]; // LIT
            }
        }
        return result;
    }

    public void setMatrix(int[][] newMatrix) {
        if (newMatrix == null) {
            throw new NullPointerException("setMatrix cannot be called on a null matrix.");
        } else if (newMatrix.length != ToneMatrixConstants.size()) {
            throw new IllegalArgumentException("Incorrect size for setMatrix");
        } else {
            int row;
            for (row = 0; row < newMatrix.length; ++row) {
                if (newMatrix[row] == null) {
                    throw new IllegalArgumentException("Inner array is null.");
                }

                if (newMatrix[row].length != ToneMatrixConstants.size()) {
                    throw new IllegalArgumentException("Incorrect size for setMatrix");
                }
            }

            for (row = 0; row < ToneMatrixConstants.size(); ++row) {
                for (int col = 0; col < ToneMatrixConstants.size(); ++col) {
                    if (newMatrix[ToneMatrixConstants.size() - 1 - row][col] != 0) {
                        if (newMatrix[ToneMatrixConstants.size() - 1 - row][col] == 1) {
                            this.matrix[row][col] = 1; // LIT
                        } else if (newMatrix[ToneMatrixConstants.size() - 1 - row][col] == 2) {
                            this.matrix[row][col] = 2; // LIT_GREEN
                        } else if (newMatrix[ToneMatrixConstants.size() - 1 - row][col] == 3) {
                            this.matrix[row][col] = 3; // LIT_BLUE
                        } else {
                            this.matrix[row][col] = 0; // UNLIT
                        }
                    }
                }

                this.redrawAll();
            }
        }
    }

    public void highlightColumn(int column) {
        if (column < 0 || column >= ToneMatrixConstants.size()) {
            column = -1;
        }

        this.highlightedColumn = column;
        this.redrawAll();
    }
}
