package battleship;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String[][] field = new String[10][10];
        HashMap<Character, Integer> letterBind = new HashMap<>();
        HashMap<Integer, String> nameBind = new HashMap<>();
        letterBind.put('A', 1);
        letterBind.put('B', 2);
        letterBind.put('C', 3);
        letterBind.put('D', 4);
        letterBind.put('E', 5);
        letterBind.put('F', 6);
        letterBind.put('G', 7);
        letterBind.put('H', 8);
        letterBind.put('I', 9);
        letterBind.put('J', 10);

        nameBind.put(5, "Aircraft Carrier");
        nameBind.put(4, "Battleship");
        nameBind.put(3, "Submarine");
        nameBind.put(33, "Cruiser");
        nameBind.put(2, "Destroyer");


        for (int i = 0; i < field.length; i++) {
            Arrays.fill(field[i], "~");
        }
        printField(field);

        int ship3CellSwitch = 0;
        int shipLength = 5;

        while (shipLength > 1) {


            if (shipLength == 3) {

                if (ship3CellSwitch == 0) {
                    System.out.println("Enter the coordinates of the " + nameBind.get(3) + " (3 cells): ");
                    ship3CellSwitch++;
                } else if (ship3CellSwitch == 1) {
                    System.out.println("Enter the coordinates of the " + nameBind.get(33) + " (3 cells): ");
                    ship3CellSwitch++;
                } else if (ship3CellSwitch == 2){
                    shipLength--;
                    continue;
                }
            } else {
                System.out.println("Enter the coordinates of the " + nameBind.get(shipLength) + " (" + shipLength + " cells): ");
            }


            while (true) {
                System.out.println();
                System.out.print("> ");
                String[] input = scanner.nextLine().trim().split(" ");
                int getLetter1Int = letterBind.get(input[0].replaceAll("\\s+", "").charAt(0)) - 1;
                int getLetter2Int = letterBind.get(input[1].replaceAll("\\s+", "").charAt(0)) - 1;

                int getNum1 = Integer.parseInt(input[0].substring(1)) - 1;
                int getNum2 = Integer.parseInt(input[1].substring(1)) - 1;

                int getLength = 0;

                if (getLetter1Int == getLetter2Int) {
                    getLength = Math.max(getNum1, getNum2) - Math.min(getNum1, getNum2) + 1;
                    if (shipLength == 3 && ship3CellSwitch == 2 && getLength != shipLength) {
                        System.out.println("\nError! Wrong length of the " + nameBind.get(33) + "! Try again:");
                        continue;
                    } else if (getLength != shipLength) {
                        System.out.println("\nError! Wrong length of the " + nameBind.get(shipLength) + "! Try again:");
                        continue;
                    }

                    for (int i = Math.min(getNum1, getNum2); i <= Math.max(getNum1, getNum2); i++) {
                        field[getLetter1Int][i] = "O";
                    }
                    break;
                }else if (getNum1 == getNum2) {
                    getLength = Math.max(getLetter1Int, getLetter2Int) - Math.min(getLetter1Int, getLetter2Int) + 1;
                    if (shipLength == 3 && ship3CellSwitch == 2 && getLength != shipLength) {
                        System.out.println("\nError! Wrong length of the " + nameBind.get(33) + "! Try again:");
                        continue;
                    } else if (getLength != shipLength) {
                        System.out.println("\nError! Wrong length of the " + nameBind.get(shipLength) + "! Try again:");
                        continue;
                    }

                    for (int i = Math.min(getLetter1Int, getLetter2Int); i <= Math.max(getLetter1Int, getLetter2Int); i++) {
                        field[i][getNum1] = "O";
                    }
                    break;

                }else { System.out.println("\nError! Wrong ship location! Try again:"); }

            }

            //F3 F7 - [6][3], [6][7]

            printField(field);

            if (shipLength > 3 || shipLength == 2) {
                shipLength--;
            }
        }

    }

    public static void printField(String[][] field) {
        char[] letters = "ABCDEFGHIJ".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  1 2 3 4 5 6 7 8 9 10\n");

        for (int i = 0; i < field.length; i++) {
            stringBuilder.append(letters[i]).append(" ");
            for (int j = 0; j < field[i].length; j++) {
                stringBuilder.append(field[i][j]).append(" ");
            }
            stringBuilder.append("\n");
        }

        System.out.println(stringBuilder);
    }

    public static void print2dArray(String[][] table) {
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }
}
