package com.example.minesweeper.logic;

public class Cell {

    private int surreoundingBombs;

    private CellState state;
    private int x, y;

    public Cell(CellType type, int x, int y) {
        this.state = CellState.HIDDEN;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public int getSurreoundingBombs() {
        return surreoundingBombs;
    }
    public void setSurreoundingBombs(int surreoundingBombs) {
        this.surreoundingBombs = surreoundingBombs;
    }

    public CellState getState() {
        return state;
    }

    public void setState(CellState state) {
        this.state = state;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    private CellType type;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }





}

