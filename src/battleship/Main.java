package battleship;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Square squarePlayer1 = new Square();
        Square squarePlayer2 = new Square();
        Game game = new Game(squarePlayer1, squarePlayer2, scanner);
        game.play();
    }
}
