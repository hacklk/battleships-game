package battleship;

import java.util.ArrayList;
import java.util.Scanner;

public class BoardFactory {
    private final Square square;

    public BoardFactory(Square square) {
        this.square = square;
    }

    public void board(Scanner scanner) {

        for (ShipType shipType : ShipType.values()) {

            System.out.println("Enter the coordinates of the " + shipType.getName() + " (" + shipType.getLength() + " cells): ");

            while (true) {
                System.out.println();
                System.out.print("> ");
                String[] input = scanner.nextLine().trim().split(" ");
                int getLetter1Int, getLetter2Int, getNum1, getNum2;

                try {

                    if ('?' == input[0].charAt(0) || "help".equals(input[0].substring(0, 4).toLowerCase())) {
                        System.out.println("EXAMPLE How to enter coordinates:");
                        System.out.println("> F1 F5 (press enter to accept)");
                        continue;
                    } else if ("rules".equals(input[0].substring(0, 5).toLowerCase())) {
                        System.out.println("The game is played on four grids, two for each player. " +
                                "\nThe grids are typically square – usually 10×10 – and the individual squares in the grid are identified by letter and number." +
                                "\nOn one grid the player arranges ships and records the shots by the opponent. " +
                                "\nOn the other grid the player records their own shots.\n" +
                                "\nBefore play begins, each player secretly arranges their ships on their primary grid. " +
                                "\nEach ship occupies a number of consecutive squares on the grid, arranged either horizontally or vertically. " +
                                "\nThe number of squares for each ship is determined by the type of the ship. " +
                                "\nThe ships cannot overlap (i.e., only one ship can occupy any given square in the grid). " +
                                "\nThe types and numbers of ships allowed are the same for each player. \nThese may vary depending on the rules. ");
                        continue;
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }

                try {
                    getLetter1Int = square.getLetterBind().get(input[0].replaceAll("\\s+", "").toUpperCase().charAt(0)) - 1;
                    getLetter2Int = square.getLetterBind().get(input[1].replaceAll("\\s+", "").toUpperCase().charAt(0)) - 1;

                    getNum1 = Integer.parseInt(input[0].substring(1)) - 1;
                    getNum2 = Integer.parseInt(input[1].substring(1)) - 1;
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("\nError");
                    continue;
                }
                int getLength;

                int minNum = Math.min(getNum1, getNum2);
                int maxNum = Math.max(getNum1, getNum2);
                int minLetterInt = Math.min(getLetter1Int, getLetter2Int);
                int maxLetterInt = Math.max(getLetter1Int, getLetter2Int);
                boolean tooClose = false;

                ArrayList<Integer[]> coordinatesHolder = new ArrayList<Integer[]>();

                if (getLetter1Int == getLetter2Int) {      //inserting ships horizontally
                    getLength = maxNum - minNum + 1;
                    //checking if entered length of the ship is correct
                    if (getLength != shipType.getLength()) { //checking if entered length of the ship is correct
                        System.out.println("\nError! Wrong length of the " + shipType.getName() + "! Try again:");
                        continue;
                    }

                    //check if ship is too close to another horizontally
                    for (int i = minNum; i <= maxNum; i++) {
                        if (square.isThereShipForAllSides(getLetter1Int, i)) {
                            System.out.println("\nError! You placed it too close to another one. Try again:");
                            tooClose = true;
                            break;
                        }
                    }

                    if (tooClose) {
                        continue;
                    }


                    try {
                        //insert
                        for (int i = minNum; i <= maxNum; i++) {
                            square.getField()[getLetter1Int][i] = ConsoleColors.RESET + "O" + ConsoleColors.RESET;
                            coordinatesHolder.add(new Integer[]{getLetter1Int, i});
                            square.getShipCordsSave().put(shipType.getName(), coordinatesHolder);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("\nError");
                        continue;
                    }

                    break;
                } else if (getNum1 == getNum2) {         //inserting ships vertically
                    getLength = maxLetterInt - minLetterInt + 1;
                    //checking if entered length of the ship is correct
                    if (getLength != shipType.getLength()) {
                        System.out.println("\nError! Wrong length of the " + shipType.getName() + "! Try again:");
                        continue;
                    }

                    //check if ship is too close to one another vertically
                    for (int i = minLetterInt; i <= maxLetterInt; i++) {
                        if (square.isThereShipForAllSides(i, getNum1)) {
                            System.out.println("\nError! You placed it too close to another one. Try again:");
                            tooClose = true;
                            break;
                        }
                    }

                    if (tooClose) {
                        continue;
                    }

                    try {
                        //insert
                        for (int i = minLetterInt; i <= maxLetterInt; i++) {
                            square.getField()[i][getNum1] = ConsoleColors.RESET + "O" + ConsoleColors.RESET;
                            coordinatesHolder.add(new Integer[]{i, getNum1});
                            square.getShipCordsSave().put(shipType.getName(), coordinatesHolder);
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
            Game.clsScreen();
            square.display();
            System.out.println();

        }
    }
}