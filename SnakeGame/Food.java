package il.co.lird.FS133.Projects.SnakeGame;

import java.awt.*;
import java.util.Random;

public class Food {
    private static final char SYMBOL = '@';
    private final int row;
    private final int col;

    public Food(int boardSizeHeight, int boardSizeWidth) {
        Random random = new Random();
        row = random.nextInt(boardSizeWidth - 2) + 1;
        col = random.nextInt(boardSizeHeight - 2) + 1;
    }

    public char getSymbol() {
        return SYMBOL;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Point getFoodCoordinate(){
        return new Point(row, col);
    }
}
