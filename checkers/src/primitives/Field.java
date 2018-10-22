package primitives;

import primitives.Cell;

public class Field {
    private Cell[][] field;
    private int height;
    private int width;

    public Field(int height, int width) {
        this.height = height;
        this.width = width;
        this.field = new Cell[height][width];
        for (var i = 0; i < height; i++) {
            for (var j = 0; j < width; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 0)
                        field[i][j] = Cell.WHITE;
                    else
                        field[i][j] = Cell.BLACK;
                }
                else{
                    if(j % 2==0)
                        field[i][j] = Cell.BLACK;
                    else
                        field[i][j] = Cell.WHITE;
                }
            }
        }
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell getCell(int y, int x) {
        return field[y][x];
    }
}
