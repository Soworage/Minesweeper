package com.example.minesweeper.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinesweeperGame {
    private Random random = new Random();
    private Cell[][] cells;
    private final int maxBombCount = 30;

    private Cell[] cellBomb = new Cell[maxBombCount];
    public Cell[][] getCells() {
        return cells;
    }

    private boolean gameEnd = false; //bestimmt ob das Spiel beendet wird oder nicht

    public boolean isGameEnd() {
        return gameEnd;
    }
    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }


    private int cellRow;
    private int cellCol;

    public MinesweeperGame(int cellCol, int cellRow) {
        this.cellRow = cellRow;
        this.cellCol = cellCol;
        cells = new Cell[cellCol][cellRow];
        init();

    }

    public void unlockBombs() { //unlocked alle bomben
        for (int x = 0; x < cellCol; x++) {
            for (int y = 0; y < cellRow; y++) {
                Cell cell = cells[x][y];
                if (cell.getType() == CellType.BOMB) {
                    cell.setState(CellState.ACTIVATED);
                    setGameEnd(true);
                }
            }
        }
    }

    public void checkWin() {
        for (int x = 0; x < cellCol; x++) {
            for (int y = 0; y < cellRow; y++) {
                Cell cell = cells[x][y];

                // Wenn eine Nicht-Bomben-Zelle noch versteckt ist, kann das Spiel nicht gewonnen sein
                if (cell.getType() != CellType.BOMB && cell.getState() != CellState.ACTIVATED) {
                    return;
                }
            }
        }
        //Spieler hat gewonnen
        setGameEnd(true);
    }

    public void activate(int x, int y) {
        Cell cell = this.cells[x][y];
        unlockAllZeroNeighbors(cell);

        if(cell.getType() == CellType.BOMB){
            cell.setState(CellState.ACTIVATED);
            unlockBombs();
        } else {
            cell.setState(CellState.ACTIVATED);
            checkWin();
        }
    }

    public void flagActivate(int x, int y){
        Cell cell= this.cells[x][y];
        cell.setState(CellState.FLAGGED);
    }

    public void init() {

        for (int x = 0; x < cellCol; x++) {
            for (int y = 0; y < cellRow; y++) {
                cells[x][y] = new Cell(CellType.EMPTY, x, y);
            }
        }
        int bombCount = 0;

        while (bombCount < maxBombCount) { //bomben werden random platziert
            int x = random.nextInt(cellCol);
            int y = random.nextInt(cellRow);
            Cell cell = cells[x][y];
            if (cell.getType() != CellType.BOMB) {
                cell.setType(CellType.BOMB);
                bombCount++;
            }

        }
        for (int x = 0; x < cellCol; x++) {
            for (int y = 0; y < cellRow; y++) {
                int countBombs = 0;
                Cell cell = this.cells[x][y];
                List<Cell> neighbors = getNeighbors(x, y);
                for (Cell neighbor : neighbors) {
                    if (neighbor.getType() == CellType.BOMB) {
                        countBombs++;
                    }
                }
                cell.setSurreoundingBombs(countBombs);
            }
        }
    }

    public List<Cell> getNeighbors(int x, int y) {
        List<Cell> neighbors = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue; // Überspringen der aktuellen Zelle, also sich selber überspringen

                int nx = x + i;
                int ny = y + j;

                // Überprüfen, ob die Nachbarzelle innerhalb der Grenzen liegt
                if (nx >= 0 && nx < cellCol && ny >= 0 && ny < cellRow) {
                    neighbors.add(cells[nx][ny]);
                }
            }
        }

        return neighbors;
    }

    public void unlockAllZeroNeighbors(Cell cell) {
        if (cell.getSurreoundingBombs() == 0 && cell.getType() == CellType.EMPTY && cell.getState() == CellState.HIDDEN) {
            cell.setState(CellState.ACTIVATED);
            List<Cell> neighbors = getNeighbors(cell.getX(), cell.getY());
            for (Cell neighbor : neighbors) {
                if (neighbor.getState() == CellState.HIDDEN) {
                    if (neighbor.getSurreoundingBombs() == 0) {
                        unlockAllZeroNeighbors(neighbor);
                    } else {
                        unlockSingleCell(neighbor);
                    }
                }
            }
        }
    }

    public void unlockSingleCell(Cell cell) {
        if (cell.getType() != CellType.BOMB && cell.getState() == CellState.HIDDEN) {
            cell.setState(CellState.ACTIVATED);
        }
    }
}



