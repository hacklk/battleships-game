package battleship;

import java.io.IOException;
import java.util.Scanner;

class Game {

    private Square squarePlayer1;
    private Square squarePlayer2;
    private Scanner scanner;

    public Game(Square squarePlayer1, Square squarePlayer2, Scanner scanner) {
        this.squarePlayer1 = squarePlayer1;
        this.squarePlayer2 = squarePlayer2;
        this.scanner = scanner;
    }

    public void play() {
        clsScreen();
        System.out.println(ConsoleColors.RESET + "\nFor game rules and description info enter \"rules\".");
        System.out.println("Enter \"?\" or \"help\" if you don't know how to enter coordinates.\n" + ConsoleColors.RESET);

        System.out.println(ConsoleColors.RESET + "Player 1, place your ships on the game field\n" + ConsoleColors.RESET);
        squarePlayer1.display();
        System.out.println();
        squarePlayer1.board(scanner);

        clsScreen();
        promptEnterToAnotherTurn();
        clsScreen();

        System.out.println(ConsoleColors.RESET + "Player 2, place your ships to the game field\n" + ConsoleColors.RESET);
        squarePlayer2.display();
        squarePlayer2.board(scanner);


        Square enemySquareViewForPlayer1 = new Square();
        Square enemySquareViewForPlayer2 = new Square();

        int whoseTurn = 0;

        while (true) {

            int letterInt = 0;
            int num = 0;

            promptEnterToAnotherTurn();
            clsScreen();
            promptEnter();
            clsScreen();


            if (whoseTurn % 2 == 0) {

                System.out.println(ConsoleColors.RESET + "Player 1, it's your turn:" + ConsoleColors.RESET);
                System.out.println();
                enemySquareViewForPlayer1.display();
                System.out.println("---------------------");
                squarePlayer1.display();


                while (true) {
                    System.out.print("\n> ");
                    String shotInput = scanner.nextLine().toUpperCase();
                    try {
                        if (squarePlayer1.getLetterBind().get(shotInput.charAt(0)) != null) {
                            letterInt = squarePlayer1.getLetterBind().get(shotInput.charAt(0)) - 1;
                        } else {
                            continue;
                        }

                        if (letterInt > 9) {
                            throw new IndexOutOfBoundsException("Error: Please enter letters A-J");
                        }

                        num = Integer.parseInt(shotInput.substring(1)) - 1;
                    } catch (Exception e) {
                        System.out.println("\nError");
                        continue;
                    }


                    try {
                        //inserting X or M into enemy (user) table
                        if (squarePlayer2.isThereShip(letterInt, num) || squarePlayer2.isThereHit(letterInt, num)) {

                            enemySquareViewForPlayer1.getField()[letterInt][num] = ConsoleColors.RED + "X" + ConsoleColors.RESET;

                        } else {
                            enemySquareViewForPlayer1.getField()[letterInt][num] = ConsoleColors.RESET + "M" + ConsoleColors.RESET;
                        }


                        //inserting X or M into the table who inserted ships
                        if (squarePlayer2.isThereShip(letterInt, num) || squarePlayer2.isThereHit(letterInt, num)) {
                            squarePlayer2.getField()[letterInt][num] = ConsoleColors.RED + "X" + ConsoleColors.RESET;
                            if (squarePlayer2.player(letterInt, num)) {
                                if (squarePlayer2.getShipCordsSave().isEmpty()) {
                                    System.out.println(ConsoleColors.RESET + "\nPlayer 1 sank the last ship. You won. Congratulations!" + ConsoleColors.RESET);
                                    System.out.println("\nPress enter to exit the program...");
                                    scanner.nextLine();
                                    return;
                                }
                                System.out.println(ConsoleColors.RESET + "\nYou sank a ship!\n" + ConsoleColors.RESET);
                            } else {
                                System.out.println(ConsoleColors.RESET + "\nYou hit a ship!" + ConsoleColors.RESET);
                            }
                        } else {
                            System.out.println(ConsoleColors.RESET + "\nYou missed!" + ConsoleColors.RESET);
                            squarePlayer2.getField()[letterInt][num] = ConsoleColors.RESET + "M" + ConsoleColors.RESET;
                        }
                        break;

                    } catch (Exception e) {
                        System.out.println("\nError! You entered the wrong coordinates! Try again:");
                    }
                }

            } else {

                System.out.println(ConsoleColors.RESET + "Player 2, it's your turn:" + ConsoleColors.RESET);
                System.out.println();
                enemySquareViewForPlayer2.display();
                System.out.println("---------------------");
                squarePlayer2.display();

                while (true) {
                    System.out.print("\n> ");
                    String shotInput = scanner.nextLine().toUpperCase();
                    try {
                        if (squarePlayer2.getLetterBind().get(shotInput.charAt(0)) != null) {
                            letterInt = squarePlayer2.getLetterBind().get(shotInput.charAt(0)) - 1;
                        } else {
                            continue;
                        }

                        num = Integer.parseInt(shotInput.substring(1)) - 1;
                    } catch (Exception e) {
                        System.out.println("\nError");
                        continue;
                    }


                    try {
                        //inserting X or M into enemy (user) table
                        if (squarePlayer1.isThereShip(letterInt, num) || squarePlayer1.isThereHit(letterInt, num)) {

                            enemySquareViewForPlayer2.getField()[letterInt][num] = ConsoleColors.RED + "X" + ConsoleColors.RESET;

                        } else {
                            enemySquareViewForPlayer2.getField()[letterInt][num] = ConsoleColors.RESET + "M" + ConsoleColors.RESET;
                        }


                        //inserting X or M into the table who inserted ships
                        if (squarePlayer1.isThereShip(letterInt, num) || squarePlayer1.isThereHit(letterInt, num)) {
                            squarePlayer1.getField()[letterInt][num] = ConsoleColors.RED + "X" + ConsoleColors.RESET;
                            if (squarePlayer1.player(letterInt, num)) {
                                if (squarePlayer1.getShipCordsSave().isEmpty()) {
                                    System.out.println(ConsoleColors.RESET + "\nPlayer 2 sank the last ship. You won. Congratulations!\n" + ConsoleColors.RESET);
                                    System.out.println("\nPress enter to exit the program...");
                                    scanner.nextLine();
                                    return;
                                }
                                System.out.println(ConsoleColors.RESET + "\nYou sank a ship!\n" + ConsoleColors.RESET);
                            } else {
                                System.out.println(ConsoleColors.RESET + "\nYou hit a ship!" + ConsoleColors.RESET);
                            }
                            break;
                        } else {
                            System.out.println(ConsoleColors.RESET + "\nYou missed!" + ConsoleColors.RESET);
                            squarePlayer1.getField()[letterInt][num] = ConsoleColors.RESET + "M" + ConsoleColors.RESET;
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


    public void promptEnterToAnotherTurn() {
        System.out.println("Press Enter and pass the move to another player");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public void promptEnter() {
        System.out.println("Press Enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public static void clsScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd.exe","/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");

        } catch (IOException | InterruptedException ignored) {
        }

    }

}


