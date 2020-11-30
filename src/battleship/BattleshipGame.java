package battleship;

import java.util.Scanner;

public class BattleshipGame {

    private Field field;
    private Scanner scanner;

    public BattleshipGame(Field field, Scanner scanner) {
        this.field = field;
        this.scanner = scanner;
    }

    public void play() {
        this.field.printField();

        for (Ship ship : Ship.values()) {

            System.out.println("Enter the coordinates of the " + ship.getName() + " (" + ship.getLength() + " cells): ");

            while (true) {
                System.out.println();
                System.out.print("> ");
                String[] input = scanner.nextLine().trim().split(" ");
                int getLetter1Int, getLetter2Int, getNum1, getNum2;

                try {
                    getLetter1Int = field.getLetterBind().get(input[0].replaceAll("\\s+", "").charAt(0)) - 1;
                    getLetter2Int = field.getLetterBind().get(input[1].replaceAll("\\s+", "").charAt(0)) - 1;

                    getNum1 = Integer.parseInt(input[0].substring(1)) - 1;
                    getNum2 = Integer.parseInt(input[1].substring(1)) - 1;
                } catch (Exception e) {
                    System.out.println("\nError");
                    continue;
                }

                int getLength;

                int minNum = Math.min(getNum1, getNum2);
                int maxNum = Math.max(getNum1, getNum2);
                int minLetterInt = Math.min(getLetter1Int, getLetter2Int);
                int maxLetterInt = Math.max(getLetter1Int, getLetter2Int);
                boolean tooClose = false;

                if (getLetter1Int == getLetter2Int) {      //inserting ships horizontally
                    getLength = maxNum - minNum + 1;
                    if (getLength != ship.getLength()) {
                        System.out.println("\nError! Wrong length of the " + ship.getName() + "! Try again:");
                        continue;
                    }

                    //check if ship is too close to another
                    for (int i = minNum; i <= maxNum; i++) {
                        if (field.isThereShipForAllSides(getLetter1Int, i)) {
                            System.out.println("\nError! You placed it too close to another one. Try again:");
                            tooClose = true;
                            break;
                        }
                    }

                    if (tooClose) {
                        continue;
                    }

                    try {
                        for (int i = minNum; i <= maxNum; i++) {
                            field.getField()[getLetter1Int][i] = "O";
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("\nError");
                        continue;
                    }

                    break;
                } else if (getNum1 == getNum2) {         //inserting ships vertically
                    getLength = maxLetterInt - minLetterInt + 1;
                    if (getLength != ship.getLength()) {
                        System.out.println("\nError! Wrong length of the " + ship.getName() + "! Try again:");
                        continue;
                    }

                    //check if ship is too close to one another vertically
                    for (int i = minLetterInt; i <= maxLetterInt; i++) {
                            if (field.isThereShipForAllSides(i, getNum1)) {
                                System.out.println("\nError! You placed it too close to another one. Try again:");
                                tooClose = true;
                                break;
                            }
                    }

                    if (tooClose) {
                        continue;
                    }

                    try {
                        for (int i = minLetterInt; i <= maxLetterInt; i++) {
                            field.getField()[i][getNum1] = "O";
                        }
                    } catch (Exception e) {
                        System.out.println("\nError");
                        continue;
                    }

                    break;
                } else {
                    System.out.println("\nError! Wrong ship location! Try again:");
                }

            }
            System.out.println();
            field.printField();
        }
    }
}
