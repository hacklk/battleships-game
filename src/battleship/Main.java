package battleship;

import java.text.ParseException;
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

        int shipLength = 5;
        int ship3CellSwitch = 0;

        while (shipLength > 1) {


            if (shipLength == 3) {

                if (ship3CellSwitch == 0) {
                    System.out.println("Enter the coordinates of the " + nameBind.get(shipLength) + " (3 cells): ");
                    ship3CellSwitch++;
                } else if (ship3CellSwitch == 1) {
                    System.out.println("Enter the coordinates of the " + nameBind.get(shipLength + 30) + " (3 cells): ");
                    ship3CellSwitch++;
                } else if (ship3CellSwitch == 2) {
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
                int getLetter1Int, getLetter2Int, getNum1, getNum2;

                try {
                    getLetter1Int = letterBind.get(input[0].replaceAll("\\s+", "").charAt(0)) - 1;
                    getLetter2Int = letterBind.get(input[1].replaceAll("\\s+", "").charAt(0)) - 1;

                    getNum1 = Integer.parseInt(input[0].substring(1)) - 1;
                    getNum2 = Integer.parseInt(input[1].substring(1)) - 1;
                } catch (Exception e) {
                    System.out.println("\nError");
                    continue;
                }

                int getLength = 0;

                int minNum = Math.min(getNum1, getNum2);
                int maxNum = Math.max(getNum1, getNum2);
                int minLetterInt = Math.min(getLetter1Int, getLetter2Int);
                int maxLetterInt = Math.max(getLetter1Int, getLetter2Int);

                boolean tooClose = false;

                if (getLetter1Int == getLetter2Int) {      //inserting ships horizontally
                    getLength = maxNum - minNum + 1;
                    if (shipLength == 3 && ship3CellSwitch == 2 && getLength != shipLength) {
                        System.out.println("\nError! Wrong length of the " + nameBind.get(33) + "! Try again:");
                        continue;
                    } else if (getLength != shipLength) {
                        System.out.println("\nError! Wrong length of the " + nameBind.get(shipLength) + "! Try again:");
                        continue;
                    }

                    //check if ship is too close to another
                    for (int i = minNum; i <= maxNum; i++) {
                        try {
                            if ("O".contains(field[getLetter1Int + 1][i]) || "O".contains(field[getLetter1Int - 1][i])
                                    || "O".contains(field[getLetter1Int][i + 1]) || "O".contains(field[getLetter1Int][i - 1])) {
                                System.out.println("\nError! You placed it too close to another one. Try again:");
                                tooClose = true;
                                break;
                            }
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                    }

                    if (tooClose) {
                        continue;
                    }

                    try {
                        for (int i = minNum; i <= maxNum; i++) {
                            field[getLetter1Int][i] = "O";
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("\nError");
                        continue;
                    }

                    break;
                } else if (getNum1 == getNum2) {         //inserting ships vertically
                    getLength = maxLetterInt - minLetterInt + 1;
                    if (shipLength == 3 && ship3CellSwitch == 2 && getLength != shipLength) {
                        System.out.println("\nError! Wrong length of the " + nameBind.get(33) + "! Try again:");
                        continue;
                    } else if (getLength != shipLength) {
                        System.out.println("\nError! Wrong length of the " + nameBind.get(shipLength) + "! Try again:");
                        continue;
                    }

                    //check if ship is too close to one another vertically
                    for (int i = minLetterInt; i <= maxLetterInt; i++) {
                        try {
                            if ("O".contains(field[i][getNum1 + 1]) || "O".contains(field[i][getNum1 - 1])
                                    || "O".contains(field[i + 1][getNum1]) || "O".contains(field[i - 1][getNum1])) {
                                System.out.println("\nError! You placed it too close to another one. Try again:");
                                tooClose = true;
                                break;
                            }
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                    }

                    if (tooClose) {
                        continue;
                    }

                    try {
                        for (int i = minLetterInt; i <= maxLetterInt; i++) {
                            field[i][getNum1] = "O";
                        }
                    }catch (Exception e) {
                        System.out.println("\nError");
                        continue;
                    }

                    break;
                } else {
                    System.out.println("\nError! Wrong ship location! Try again:");
                }

            }

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
