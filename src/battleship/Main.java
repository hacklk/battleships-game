package battleship;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String stringTest = "F1 F5\n" +
                "B9 E9\n" +
                "J8 J10\n" +
                "A4 C4\n" +
                "F10 G10\n";

        Scanner scanner = new Scanner(System.in);
        Field field = new Field();
        BattleshipGame battleshipGame = new BattleshipGame(field, scanner);
        battleshipGame.play();
    }
}
