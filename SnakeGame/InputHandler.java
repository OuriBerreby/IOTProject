package il.co.lird.FS133.Projects.SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler extends JFrame implements KeyListener {
    private Game game;
    private final Snake snake;
    InputHandler(Game game){
        this.snake = game.getSnake();
    }

    public void initialize(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(10, 10));
        pack();
        setLocationRelativeTo(null);
        addKeyListener(this);
        setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (snake.getDirection() != Direction.DOWN) {
                    snake.setDirection(Direction.UP);
                }
                break;
            case KeyEvent.VK_DOWN:
                if (snake.getDirection() != Direction.UP) {
                    snake.setDirection(Direction.DOWN);
                }
                break;
            case KeyEvent.VK_LEFT:
                if (snake.getDirection() != Direction.RIGHT) {
                    snake.setDirection(Direction.LEFT);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (snake.getDirection() != Direction.LEFT) {
                    snake.setDirection(Direction.RIGHT);
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Ne pas utiliser dans ce cas
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Ne pas utiliser dans ce cas
    }
}
