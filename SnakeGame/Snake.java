package il.co.lird.FS133.Projects.SnakeGame;

import java.awt.*;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;

public class Snake {
    private Deque<Point> bodySnake;
    private int boardSizeHeight;
    private int boardSizeWidth;
    private Direction direction;
    private Point headSnake;

    public Snake(int boardSizeHeight, int boardSizeWidth) {
        this.boardSizeHeight = boardSizeHeight;
        this.boardSizeWidth = boardSizeWidth;
        this.bodySnake = new LinkedList<>();
        this.bodySnake.add(new Point(boardSizeHeight/2, boardSizeWidth/2));
        headSnake = bodySnake.getFirst();
        this.direction = Direction.RIGHT;
    }

    public void addPoint(Point point) {
        bodySnake.addFirst(point);
        headSnake = bodySnake.getFirst();
    }

    public void grow(){
        Point newHead = null;
        switch (direction) {
            case UP:
                newHead = new Point(headSnake.x-1, headSnake.y);
                break;
            case DOWN:
                newHead = new Point(headSnake.x+1, headSnake.y);
                break;
            case RIGHT:
                newHead = new Point(headSnake.x, headSnake.y+1);
                break;
            case LEFT:
                newHead = new Point(headSnake.x, headSnake.y-1);
                break;
        }
        addPoint(newHead);
    }

    public LinkedList<Point> getBody() {
        return (LinkedList<Point>) bodySnake;
    }

    public Point getHead(){
        return bodySnake.getFirst();
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
        }
    }
    private void shiftLocations(){
        bodySnake.removeLast();
    }
    private void moveUp(){
        shiftLocations();
        headSnake = new Point(headSnake.x-1, headSnake.y);
        bodySnake.addFirst(headSnake);
        //headSnake.setLocation(headSnake.x, headSnake.y-1);
    }

    private void moveDown(){
        shiftLocations();
        headSnake = new Point(headSnake.x+1, headSnake.y);
        bodySnake.addFirst(headSnake);
    }
    private void moveLeft(){
        shiftLocations();
        headSnake = new Point(headSnake.x, headSnake.y-1);
        bodySnake.addFirst(headSnake);
    }

    private void moveRight(){
        shiftLocations();
        headSnake = new Point(headSnake.x, headSnake.y+1);
        bodySnake.addFirst(headSnake);
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection(){
        return this.direction;
    }
}

