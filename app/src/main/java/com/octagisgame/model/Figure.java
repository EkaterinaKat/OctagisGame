package com.octagisgame.model;

class Figure {
    private boolean[][] shape;
    private int color;
    //горизонтальная координата направлена слева направо, вертикальная сверху вниз
    //координаты нижней левой клеточки фигуры
    private int x;
    private int y;

    Figure(boolean[][] shape, int color) {
        this.shape = shape;
        this.color = color;
        x = 0;
        y = 0;
    }

    void descend() {
        y++;
    }

    void left(){
        x--;
    }

    void right(){
        x++;
    }

    boolean[][] getShape() {
        return shape;
    }

    int getColor() {
        return color;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void rotate() {
        int figureSize = 4;
        boolean[][] tmp = copyArray(shape);
        for (int i = 0; i < figureSize; i++) {
            for (int j = 0; j < figureSize; j++) {
                shape[figureSize-1-j][i] = tmp [i][j];
            }
        }
    }

    static private boolean[][] copyArray(boolean[][] inArray){
        boolean[][] outArray = inArray.clone();
        for (int i = 0; i < outArray.length; i++)
            outArray[i] = inArray[i].clone();
        return outArray;
    }
}
