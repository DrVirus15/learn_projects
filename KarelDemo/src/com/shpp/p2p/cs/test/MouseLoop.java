package com.shpp.p2p.cs.test;

import com.shpp.cs.a.graphics.WindowProgram;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import java.awt.*;
import java.awt.event.MouseEvent;

public class MouseLoop extends WindowProgram {
    private final int ROW_SQUARES = 6;
    private final int COL_SQUARES = 10;
    private final double SQUARE_SIZE = 30;
    private final Color MOUSELOOP_COLOR = Color.GREEN;
    private final Color BLUE_SQUARE = Color.BLUE;
    private final Color RED_SQUARE = Color.RED;
    private final int DELAY = 300;

    private GRect[][] gridSquares; // Масив всіх зелених квадратів
    private int blueRow, blueCol;  // Позиція синього квадрата
    private int redRow, redCol;    // Позиція червоного квадрата
    private int blueDirection = 1; // 1 = за годинниковою, -1 = проти
    private int redDirection = -1; // Червоний рухається в протилежну сторону
    private double startX, startY;
    private GPoint mousePosition;

    @Override
    public void run() {
        drawMouseloop();
        initializePositions();
        addMouseListeners();
        loopSquares();
    }

    /**
     * Ініціалізує стартові позиції квадратів
     */
    private void initializePositions() {
        startX = (getWidth() - SQUARE_SIZE * COL_SQUARES) / 2.0;
        startY = (getHeight() - SQUARE_SIZE * ROW_SQUARES) / 2.0;

        // Синій стартує з лівого верхнього кута
        blueRow = 0;
        blueCol = 0;

        // Червоний стартує з правого нижнього кута
        redRow = ROW_SQUARES - 1;
        redCol = COL_SQUARES - 1;

        // Встановлюємо початкові кольори
        updateSquareColors();
    }

    /**
     * Основний цикл анімації
     */
    private void loopSquares() {
        while (true) {
            // Перевіряємо чи курсор на наступній позиції синього квадрата
            int[] nextBluePos = getNextPosition(blueRow, blueCol, blueDirection);
            if (isMouseOverPosition(nextBluePos[0], nextBluePos[1])) {
                blueDirection *= -1;
                System.out.println("Синій квадрат змінив напрямок через курсор!");
            }

            // Перевіряємо чи курсор на наступній позиції червоного квадрата
            int[] nextRedPos = getNextPosition(redRow, redCol, redDirection);
            if (isMouseOverPosition(nextRedPos[0], nextRedPos[1])) {
                redDirection *= -1;
                System.out.println("Червоний квадрат змінив напрямок через курсор!");
            }

            // Рухаємо квадрати
            moveSquare(true);  // синій
            moveSquare(false); // червоний

            // Перевіряємо зіткнення квадратів
            if (blueRow == redRow && blueCol == redCol) {
                blueDirection *= -1;
                redDirection *= -1;
                System.out.println("Квадрати зіткнулись!");
            }

            // Оновлюємо кольори
            updateSquareColors();

            pause(DELAY);
        }
    }

    /**
     * Рухає квадрат по периметру
     */
    private void moveSquare(boolean isBlue) {
        if (isBlue) {
            int[] nextPos = getNextPosition(blueRow, blueCol, blueDirection);
            blueRow = nextPos[0];
            blueCol = nextPos[1];
        } else {
            int[] nextPos = getNextPosition(redRow, redCol, redDirection);
            redRow = nextPos[0];
            redCol = nextPos[1];
        }
    }

    /**
     * Обчислює наступну позицію на периметрі
     */
    private int[] getNextPosition(int currentRow, int currentCol, int direction) {
        int newRow = currentRow;
        int newCol = currentCol;

        if (direction == 1) { // За годинниковою стрілкою
            // Верхній рядок - рух вправо
            if (currentRow == 0 && currentCol < COL_SQUARES - 1) {
                newCol++;
            }
            // Правий стовпець - рух вниз
            else if (currentCol == COL_SQUARES - 1 && currentRow < ROW_SQUARES - 1) {
                newRow++;
            }
            // Нижній рядок - рух вліво
            else if (currentRow == ROW_SQUARES - 1 && currentCol > 0) {
                newCol--;
            }
            // Лівий стовпець - рух вгору
            else if (currentCol == 0 && currentRow > 0) {
                newRow--;
            }
        } else { // Проти годинникової стрілки
            // Верхній рядок - рух вліво
            if (currentRow == 0 && currentCol > 0) {
                newCol--;
            }
            // Лівий стовпець - рух вниз
            else if (currentCol == 0 && currentRow < ROW_SQUARES - 1) {
                newRow++;
            }
            // Нижній рядок - рух вправо
            else if (currentRow == ROW_SQUARES - 1 && currentCol < COL_SQUARES - 1) {
                newCol++;
            }
            // Правий стовпець - рух вгору
            else if (currentCol == COL_SQUARES - 1 && currentRow > 0) {
                newRow--;
            }
        }

        return new int[]{newRow, newCol};
    }

    /**
     * Перевіряє чи курсор над заданою позицією
     */
    private boolean isMouseOverPosition(int row, int col) {
        if (mousePosition == null) return false;

        double squareX = startX + col * SQUARE_SIZE;
        double squareY = startY + row * SQUARE_SIZE;

        return mousePosition.getX() >= squareX && mousePosition.getX() <= squareX + SQUARE_SIZE && mousePosition.getY() >= squareY && mousePosition.getY() <= squareY + SQUARE_SIZE;
    }

    /**
     * Оновлює кольори квадратів
     */
    private void updateSquareColors() {
        // Спочатку всі зелені
        for (int row = 0; row < ROW_SQUARES; row++) {
            for (int col = 0; col < COL_SQUARES; col++) {
                if (isPerimeterSquare(row, col)) {
                    gridSquares[row][col].setFillColor(MOUSELOOP_COLOR);
                }
            }
        }

        // Встановлюємо синій квадрат
        if (isPerimeterSquare(blueRow, blueCol)) {
            gridSquares[blueRow][blueCol].setFillColor(BLUE_SQUARE);
        }

        // Встановлюємо червоний квадрат
        if (isPerimeterSquare(redRow, redCol)) {
            gridSquares[redRow][redCol].setFillColor(RED_SQUARE);
        }
    }

    /**
     * Перевіряє чи квадрат належить периметру
     */
    private boolean isPerimeterSquare(int row, int col) {
        return row == 0 || row == ROW_SQUARES - 1 || col == 0 || col == COL_SQUARES - 1;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        blueDirection *= -1;
        redDirection *= -1;
        System.out.println("Клік миші - квадрати змінили напрямок!");
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePosition = new GPoint(e.getX(), e.getY());
    }

    /**
     * Малює зелену рамку з квадратів
     */
    private void drawMouseloop() {
        gridSquares = new GRect[ROW_SQUARES][COL_SQUARES];
        startX = (getWidth() - SQUARE_SIZE * COL_SQUARES) / 2.0;
        startY = (getHeight() - SQUARE_SIZE * ROW_SQUARES) / 2.0;

        for (int row = 0; row < ROW_SQUARES; row++) {
            for (int col = 0; col < COL_SQUARES; col++) {
                if (isPerimeterSquare(row, col)) {
                    GRect square = drawSquare(startX + col * SQUARE_SIZE,
                            startY + row * SQUARE_SIZE,
                            SQUARE_SIZE, MOUSELOOP_COLOR);
                    gridSquares[row][col] = square;
                    add(square);
                }
            }
        }
    }

    /**
     * Створює квадрат з заданими параметрами
     */
    private GRect drawSquare(double x, double y, double size, Color color) {
        GRect gRect = new GRect(x, y, size, size);
        gRect.setFilled(true);
        gRect.setFillColor(color);
        gRect.setColor(Color.BLACK);
        return gRect;
    }
}
