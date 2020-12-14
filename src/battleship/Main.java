package battleship;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Field fieldPlayer1 = new Field();
        Field fieldPlayer2 = new Field();
        BattleshipGame battleshipGame = new BattleshipGame(fieldPlayer1, fieldPlayer2, scanner);
        battleshipGame.play();
    }
}
