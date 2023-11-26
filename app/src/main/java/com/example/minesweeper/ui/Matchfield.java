package com.example.minesweeper.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.minesweeper.logic.Cell;
import com.example.minesweeper.logic.CellState;
import com.example.minesweeper.logic.CellType;
import com.example.minesweeper.logic.MinesweeperGame;

public class Matchfield extends Canvas {
    private final int cellRow = 16;
    private final int cellCol = 16;

    private final int fieldHeight = 80;
    private final int fieldWidth = 80;
    private Paint paint;

    public Matchfield(Bitmap bitmap, Paint paint) {
        super(bitmap);
        this.paint = paint;
    }

    private int getCellColor(Cell cell) {
        if (cell.getState() == CellState.HIDDEN) {
            return Color.GRAY;
        }
        if (cell.getState() == CellState.FLAGGED) {
            return Color.RED;
        }
        if (cell.getType() == CellType.BOMB) {
            return Color.BLACK;
        }
        return Color.WHITE;
    }


    private void drawNumber(int number, float x, float y) {
        paint.setColor(Color.BLACK); // Setzt die Textfarbe
        paint.setTextSize(30); // Setzt die Textgröße
        paint.setTextAlign(Paint.Align.CENTER); // Setzt die Ausrichtung des Texts

        // Konvertieren der Zahl in einen String
        String text = String.valueOf(number);

        // Zeichnen des Texts auf dem Canvas
        drawText(text, x, y, paint);
    }

    private MinesweeperGame game = new MinesweeperGame(cellCol, cellRow);

    public void Click(int posX, int posY) {
        int x = posX / fieldWidth;
        int y = posY / fieldHeight;
        game.activate(x, y);
        drawField();
    }

    public void drawField() {
        Cell[][] cells = game.getCells();
        for (int x = 0; x < cellCol; x++) {
            for (int y = 0; y < cellRow; y++) {
                Cell cell = cells[x][y];
                float startX = x * fieldWidth;
                float endX = (x + 1) * fieldWidth;
                float startY = y * fieldHeight;
                float endY = (y + 1) * fieldHeight;

                // Füllen des Rechtecks
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(getCellColor(cell));
                drawRect(startX, startY, endX, endY, paint);

                // Zeichnen der Umrandung
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.BLACK); // Farbe der Umrandung
                paint.setStrokeWidth(2); // Breite der Umrandung
                drawRect(startX, startY, endX, endY, paint);

                if(cell.getState() == CellState.ACTIVATED && cell.getType() == CellType.EMPTY){
                    drawNumber(cell.getSurreoundingBombs(), startX + fieldWidth / 2, startY + fieldHeight / 2);
                }
            }
        }

    }


}
