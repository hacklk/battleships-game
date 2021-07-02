package battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

class Square {

    private final Player player = new Player(this);
    private final BoardFactory boardFactory = new BoardFactory(this);
    private String[][] field;
    private StringBuilder stringBuilder;
    private char letters;
    private HashMap<Character, Integer> letterBind;
    private HashMap<String, ArrayList<Integer[]>> shipCordsSave;


    public Square() {
        letters = 'A';
        this.field = new String[10][10];
        this.letterBind = new HashMap<>();
        this.shipCordsSave = new HashMap<>();

        for (int i = 0; i < field.length; i++) {
            Arrays.fill(field[i], "~");
        }

        for (int i = 1; i <= 10; i++) {
            this.letterBind.put(letters, i);
            letters++;
        }

    }

    public String[][] getField() {
        return field;
    }

    public boolean isThereShip(int y, int x) {
        String ship = ConsoleColors.RESET + "O" + ConsoleColors.RESET;
        return ship.contains(getField()[y][x]);
    }

    public boolean isThereHit(int y, int x) {
        String hit = ConsoleColors.RED + "X" + ConsoleColors.RESET;
        return hit.contains(getField()[y][x]);
    }

    public boolean isThereMiss(int y, int x) {
        String miss = ConsoleColors.RESET + "M" + ConsoleColors.RESET;
        return miss.contains(getField()[y][x]);
    }

    public boolean isThereShipForAllSides(int y, int x) {
        if (y == 0) {
            switch (x) {
                case 0:
                    return isThereShip(y, x) || isThereShip(y + 1, x) || isThereShip(y, x + 1);
                case 9:
                    return isThereShip(y, x) || isThereShip(y + 1, x) || isThereShip(y, x - 1);
            }
            return isThereShip(y, x) || isThereShip(y + 1, x) || isThereShip(y, x - 1) || isThereShip(y, x + 1);
        } else if (y == 9) {
            switch (x) {
                case 0:
                    return isThereShip(y, x) || isThereShip(y, x + 1) || isThereShip(y - 1, x);
                case 9:
                    return isThereShip(y, x) || isThereShip(y, x - 1) || isThereShip(y - 1, x);
            }
            return isThereShip(y, x) || isThereShip(y - 1, x) || isThereShip(y, x - 1) || isThereShip(y, x + 1);
        } else if (x == 0) {
            return isThereShip(y + 1, x) || isThereShip(y - 1, x) || isThereShip(y, x) || isThereShip(y, x + 1);
        } else if (x == 9) {
            return isThereShip(y + 1, x) || isThereShip(y - 1, x) || isThereShip(y, x) || isThereShip(y, x - 1);
        } else {
            return isThereShip(y + 1, x) || isThereShip(y - 1, x) || isThereShip(y, x + 1) || isThereShip(y, x - 1);
        }
    }

    public HashMap<Character, Integer> getLetterBind() {
        return letterBind;
    }

    public HashMap<String, ArrayList<Integer[]>> getShipCordsSave() {
        return shipCordsSave;
    }

    public void display() {
        this.stringBuilder = new StringBuilder();
        this.stringBuilder.append(ConsoleColors.RED +"  1 2 3 4 5 6 7 8 9 10\n" + ConsoleColors.RESET);
        this.letters = 'A';

        for (int i = 0; i < getField().length; i++) {
            stringBuilder.append(ConsoleColors.RED + letters + ConsoleColors.RESET).append(" ");
            letters++;
            for (int j = 0; j < getField()[i].length; j++) {
                stringBuilder.append(getField()[i][j]).append(" ");
            }
            if (i < getField().length - 1) {
                stringBuilder.append("\n");
            }
        }
        System.out.println(stringBuilder);
    }

    public void board(Scanner scanner) {

        boardFactory.board(scanner);
    }

    public boolean player(int y, int x) {

        return player.player(y, x);
    }


}
