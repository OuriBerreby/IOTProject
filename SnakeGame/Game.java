package il.co.lird.FS133.Projects.SnakeGame;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class Game {
    private final Board board;
    private final Snake snake;
    private Direction direction;
    private final Food food;
    private boolean isGameOver;

    public Game(int boardSizeHeight, int boardSizeWidth) {
        this.snake = new Snake(boardSizeHeight, boardSizeWidth);
        this.food = new Food(boardSizeHeight, boardSizeWidth);
        this.board = new Board(boardSizeHeight, boardSizeWidth, snake, food);
        this.direction = Direction.RIGHT;
        this.isGameOver = false;
    }

    public void play() {

        while (!isGameOver) {

            switch (snake.getDirection()){
                case UP:
                    snake.move(Direction.UP);
                    break;
                case RIGHT:
                    snake.move(Direction.RIGHT);
                    break;
                case DOWN:
                    snake.move(Direction.DOWN);
                    break;
                case LEFT:
                    snake.move(Direction.LEFT);
                    break;
                case END:
                    isGameOver = true;
                    break;
            }

            if (isGameOver()) {
                System.out.println("Game Over!");
                break;
            }
            try {
                Thread.sleep(300); // Pause de 200 millisecondes entre chaque déplacement
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            board.display();
        }
    }
    private boolean isGameOver() {
        return isGameOver;
    }

    public Snake getSnake(){
        return snake;
    }
}