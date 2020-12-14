package battleship;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BattleshipGame {

    private Field fieldPlayer1;
    private Field fieldPlayer2;
    private Scanner scanner;

    public BattleshipGame(Field fieldPlayer1, Field fieldPlayer2, Scanner scanner) {
        this.fieldPlayer1 = fieldPlayer1;
        this.fieldPlayer2 = fieldPlayer2;
        this.scanner = scanner;
    }

    public void play() {
        System.out.println("\nFor game rules and description info enter \"rules\".");
        System.out.println("Enter \"?\" or \"help\" if you don't know how to enter coordinates.\n");

        System.out.println("Player 1, place your ships on the game field\n");
        fieldPlayer1.printField();
        System.out.println();
        fieldPlayer1.shipInsertion(scanner);
        promptEnterKey();

        System.out.println("Player 2, place your ships to the game field\n");
        fieldPlayer2.printField();
        fieldPlayer2.shipInsertion(scanner);

        Field enemyFieldViewForPlayer1 = new Field();
        Field enemyFieldViewForPlayer2 = new Field();

        int whoseTurn = 0;

        while (true) {

            int letterInt = 0;
            int num = 0;
            boolean sunken = false;

            promptEnterKey();

            if (whoseTurn % 2 == 0) {

                System.out.println("Player 1, it's your turn:");
                System.out.println();
                enemyFieldViewForPlayer1.printField();
                System.out.println("---------------------");
                fieldPlayer1.printField();


                while (true) {
                    System.out.print("\n> ");
                    String shotInput = scanner.nextLine().toUpperCase();
                    try {
                        try {
                            if (fieldPlayer1.getLetterBind().get(shotInput.charAt(0)) != null) {
                                letterInt = fieldPlayer1.getLetterBind().get(shotInput.charAt(0)) - 1;
                            } else {
                                continue;
                            }
                        } catch (StringIndexOutOfBoundsException e) {
                            System.out.println("\nError");
                            continue;
                        }
                        num = Integer.parseInt(shotInput.substring(1)) - 1;
                    } catch (Exception e) {
                        System.out.println("\nError");
                        continue;
                    }


                    try {
                        //inserting X or M into enemy (user) table
                        if (fieldPlayer2.isThereShip(letterInt, num) || fieldPlayer2.isThereHit(letterInt, num)) {

                            enemyFieldViewForPlayer1.getField()[letterInt][num] = "X";

                        } else {
                            enemyFieldViewForPlayer1.getField()[letterInt][num] = "M";
                        }


                        //inserting X or M into the table who inserted ships
                        if (fieldPlayer2.isThereShip(letterInt, num) || fieldPlayer2.isThereHit(letterInt, num)) {
                            fieldPlayer2.getField()[letterInt][num] = "X";
                            if (fieldPlayer2.isShipSunken(letterInt, num)) {
                                if (fieldPlayer2.getShipCordsSave().isEmpty()) {
                                    System.out.println("\nPlayer 1 sank the last ship. You won. Congratulations!");
                                    return;
                                }
                                System.out.println("\nYou sank a ship!\n");
                            } else {
                                System.out.println("\nYou hit a ship!");
                            }
                            break;
                        } else {
                            System.out.println("\nYou missed!\n");
                            fieldPlayer2.getField()[letterInt][num] = "M";
                            break;
                        }

                    } catch (Exception e) {
                        System.out.println("\nError! You entered the wrong coordinates! Try again:");
                    }
                }

            } else {

                System.out.println("Player 2, it's your turn:");
                System.out.println();
                enemyFieldViewForPlayer2.printField();
                System.out.println("---------------------");
                fieldPlayer2.printField();

                while (true) {
                    System.out.print("\n> ");
                    String shotInput = scanner.nextLine().toUpperCase();
                    try {
                        try {
                            if (fieldPlayer2.getLetterBind().get(shotInput.charAt(0)) != null) {
                                letterInt = fieldPlayer2.getLetterBind().get(shotInput.charAt(0)) - 1;
                            } else {
                                continue;
                            }
                        } catch (StringIndexOutOfBoundsException e) {
                            System.out.println("\nError");
                            continue;
                        }
                        num = Integer.parseInt(shotInput.substring(1)) - 1;
                    } catch (Exception e) {
                        System.out.println("\nError");
                        continue;
                    }


                    try {
                        //inserting X or M into enemy (user) table
                        if (fieldPlayer1.isThereShip(letterInt, num) || fieldPlayer1.isThereHit(letterInt, num)) {

                            enemyFieldViewForPlayer2.getField()[letterInt][num] = "X";

                        } else {
                            enemyFieldViewForPlayer2.getField()[letterInt][num] = "M";
                        }


                        //inserting X or M into the table who inserted ships
                        if (fieldPlayer1.isThereShip(letterInt, num) || fieldPlayer1.isThereHit(letterInt, num)) {
                            fieldPlayer1.getField()[letterInt][num] = "X";
                            if (fieldPlayer1.isShipSunken(letterInt, num)) {
                                if (fieldPlayer1.getShipCordsSave().isEmpty()) {
                                    System.out.println("\nPlayer 2 sank the last ship. You won. Congratulations!\n");
                                    return;
                                }
                                System.out.println("\nYou sank a ship!\n");
                            } else {
                                System.out.println("\nYou hit a ship!");
                            }
                            break;
                        } else {
                            System.out.println("\nYou missed!\n");
                            fieldPlayer1.getField()[letterInt][num] = "M";
                            break;
                        }


                    } catch (Exception e) {
                        System.out.println("\nError! You entered the wrong coordinates! Try again:");
                    }
                }

            }
            whoseTurn++;
        }
    }

    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void clrscr() {

        //Clears Screen

        try {

            if (System.getProperty("os.name").contains("Windows"))

                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

            else

                Runtime.getRuntime().exec("clear");

        } catch (IOException | InterruptedException ignored) {
        }

    }
}


