package il.co.lird.FS133.Projects.SnakeGame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Game game = new Game(15, 10); // Création du jeu avec un plateau de taille 10x10
        InputHandler inputHandler = new InputHandler(game);
        SwingUtilities.invokeLater(inputHandler::initialize);
        game.play();
    }
}
