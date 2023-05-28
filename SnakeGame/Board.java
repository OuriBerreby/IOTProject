package il.co.lird.FS133.Projects.SnakeGame;

import java.awt.*;

public class Board {
    private final int sizeHeight;
    private final int sizeWidth;
    private final Snake snake;
    private Food food;

    public Board(int sizeHeight, int sizeWidth, Snake snake, Food food) {
        this.sizeHeight = sizeHeight;
        this.sizeWidth = sizeWidth;
        this.snake = snake;
        this.food = food;
    }

    public void display() {
        for (int row = 0; row < sizeWidth; row++) {
            for (int col = 0; col < sizeHeight; col++) {
                Point coordinates = new Point(row,col);
                Point headSnake = snake.getHead();

                if (!isInBoard(headSnake)  || isEatingHimself()){
                    if (snake.getDirection() != Direction.END){
                        snake.setDirection(Direction.END);
                    }
                }

                if (isSnakePoint(row, col)) {
                    System.out.print("8"); // Segment du serpent
                }
                else if (isFood(row, col)) {
                    System.out.print("@"); // Nourriture
                }
                else if (row == 0 || row == sizeWidth - 1 || col == 0 || col == sizeHeight - 1) {
                    System.out.print("#"); // Bordure
                }
                else {
                    System.out.print(" "); // Intérieur du plateau
                }

                if (isFood(snake.getHead().x,snake.getHead().y)){
                    snake.grow();
                    food = new Food(sizeHeight, sizeWidth);
                }
            }
            System.out.println();
        }
        clear();
    }

    private boolean isInBoard(Point p){
        return p.getX() > 0 && p.getX() < sizeWidth -1 && p.getY()> 0 && p.getY() < sizeHeight - 1;
    }

    private boolean isEatingHimself(){
        for (int i = 1; i < snake.getBody().size(); ++i){
            if (snake.getHead().equals(snake.getBody().get(i))){
                return true;
            }
        }
        return false;
    }

    private boolean
    isSnakePoint(int row, int col) {
        for (Point p : snake.getBody()){
            if (p.getX() == row && p.getY() == col){
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for (int i = 0; i <15; ++i) {
            System.out.println();
        }
        System.out.flush();
    }

    private boolean isFood(int row, int col) {
        if (food.getRow() == row && food.getCol() == col) {
            return true;
        }
        return false;
    }
}

