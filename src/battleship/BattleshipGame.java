package battleship;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class BattleshipGame {

    private Field field;
    private Scanner scanner;
    private HashMap<String, ArrayList<Integer[]>> shipCordsSave; // (Y and X Cords)(Ship Length)

    public BattleshipGame(Field field, Scanner scanner) {
        this.field = field;
        this.scanner = scanner;
        this.shipCordsSave = new HashMap<>();
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
                    getLetter1Int = field.getLetterBind().get(input[0].replaceAll("\\s+", "").toUpperCase().charAt(0)) - 1;
                    getLetter2Int = field.getLetterBind().get(input[1].replaceAll("\\s+", "").toUpperCase().charAt(0)) - 1;

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

                ArrayList<Integer[]> coordinatesHolder = new ArrayList<>();

                if (getLetter1Int == getLetter2Int) {      //inserting ships horizontally
                    getLength = maxNum - minNum + 1;
                    //checking if entered length of the ship is correct
                    if (getLength != ship.getLength()) { //checking if entered length of the ship is correct
                        System.out.println("\nError! Wrong length of the " + ship.getName() + "! Try again:");
                        continue;
                    }

                    //check if ship is too close to another horizontally
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
                        //insert
                        for (int i = minNum; i <= maxNum; i++) {
                            field.getField()[getLetter1Int][i] = "O";
                            coordinatesHolder.add(new Integer[]{getLetter1Int, i});
                            shipCordsSave.put(ship.getName(), coordinatesHolder);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("\nError");
                        continue;
                    }

                    break;
                } else if (getNum1 == getNum2) {         //inserting ships vertically
                    getLength = maxLetterInt - minLetterInt + 1;
                    //checking if entered length of the ship is correct
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
                        //insert
                        for (int i = minLetterInt; i <= maxLetterInt; i++) {
                            field.getField()[i][getNum1] = "O";
                            coordinatesHolder.add(new Integer[]{i, getNum1});
                            shipCordsSave.put(ship.getName(), coordinatesHolder);
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

        //      for printing coordinates for each ship
//        for (String x : shipCordsSave.keySet()) {
//            System.out.print(x+ " cell ship: ");
//            for (Integer[] cords : shipCordsSave.get(x)) {
//                System.out.print(Arrays.toString(cords) + ">> ");
//            }
//            System.out.println();
//        }


        Field fieldViewForAShooter = new Field();

        System.out.println("The game starts!\n");
        fieldViewForAShooter.printField();
        System.out.println("Take a shot!");
        System.out.println();

        while (true) {
            int letterInt = 0;
            int num = 0;
            boolean sunken = false;

            System.out.print("> ");
            String shotInput = scanner.nextLine().toUpperCase();

            try {
                try {
                    if (field.getLetterBind().get(shotInput.charAt(0)) != null) {
                        letterInt = field.getLetterBind().get(shotInput.charAt(0)) - 1;
                    } else {
                        continue;
                    }
                }catch (StringIndexOutOfBoundsException e) {
                    System.out.println("\nError\n");
                    continue;
                }
                num = Integer.parseInt(shotInput.substring(1)) - 1;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("\nError\n");
                continue;
            }

            try {
                //inserting X or M into enemy (user) table
                if (field.isThereShip(letterInt, num) || field.isThereHit(letterInt, num)) {

                    fieldViewForAShooter.getField()[letterInt][num] = "X";

                } else {
                    fieldViewForAShooter.getField()[letterInt][num] = "M";
                }

                fieldViewForAShooter.printField();

                //inserting X or M into the table who inserted ships
                if (field.isThereShip(letterInt, num) || field.isThereHit(letterInt, num)) {
                    field.getField()[letterInt][num] = "X";
                    if (isShipSunken(letterInt, num)) {
                        if (shipCordsSave.isEmpty()) {
                            System.out.println("You sank the last ship. You won. Congratulations!\n");
                            break;
                        }
                        System.out.println("You sank a ship! Specify a new target:\n");
                    }else {
                        System.out.println("You hit a ship!\n");
                    }
                } else if (field.isThereShip(letterInt, num) || field.isThereMiss(letterInt, num)) {
                    System.out.println("You missed!\n");
                    field.getField()[letterInt][num] = "M";
                }


            } catch (Exception e) {
                System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
            }
        }
    }

    public boolean isShipSunken(int y, int x) {

        boolean sunken = false;
        String nameOfTheShip = "";

        for (String shipName : shipCordsSave.keySet()) {

            for (int i = 0; i < shipCordsSave.get(shipName).size(); i++) {
                if (y == shipCordsSave.get(shipName).get(i)[0] && x == shipCordsSave.get(shipName).get(i)[1]) {

                    nameOfTheShip = shipName;
                    break;
                }
            }

        }

        int cellCount = 0;
        for (Integer[] cords : shipCordsSave.get(nameOfTheShip)) {

            if (field.isThereHit(cords[0], cords[1])) {
                cellCount++;
            }

            if (cellCount == shipCordsSave.get(nameOfTheShip).size()) {
                shipCordsSave.remove(nameOfTheShip);
                sunken = true;
                break;
            }
        }

        return sunken;
    }
}
