package battleship;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Field field = new Field();
        BattleshipGame battleshipGame = new BattleshipGame(field, scanner);
        battleshipGame.play();

    }
}
