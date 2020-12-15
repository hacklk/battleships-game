package battleship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

class Field {

    private String[][] field;
    private StringBuilder stringBuilder;
    private char letters;
    private HashMap<Character, Integer> letterBind;
    private HashMap<String, ArrayList<Integer[]>> shipCordsSave;


    public Field() {
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
        String ship = ConsoleColors.GREEN_BOLD + "O" + ConsoleColors.RESET;
        return ship.contains(getField()[y][x]);
    }

    public boolean isThereHit(int y, int x) {
        String hit = ConsoleColors.RED + "X" + ConsoleColors.RESET;
        return hit.contains(getField()[y][x]);
    }

    public boolean isThereMiss(int y, int x) {
        String miss = ConsoleColors.CYAN_BOLD + "M" + ConsoleColors.RESET;
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

    public void printField() {
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

    public void shipInsertion(Scanner scanner) {

        for (Ship ship : Ship.values()) {

            System.out.println("Enter the coordinates of the " + ship.getName() + " (" + ship.getLength() + " cells): ");

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
                }catch (IndexOutOfBoundsException ignored) {}

                try {
                    getLetter1Int = getLetterBind().get(input[0].replaceAll("\\s+", "").toUpperCase().charAt(0)) - 1;
                    getLetter2Int = getLetterBind().get(input[1].replaceAll("\\s+", "").toUpperCase().charAt(0)) - 1;

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
                        if (isThereShipForAllSides(getLetter1Int, i)) {
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
                            getField()[getLetter1Int][i] = ConsoleColors.GREEN_BOLD + "O" + ConsoleColors.RESET;
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
                        if (isThereShipForAllSides(i, getNum1)) {
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
                            getField()[i][getNum1] = ConsoleColors.GREEN_BOLD + "O" + ConsoleColors.RESET;
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
            BattleshipGame.clsScreen();
            printField();
            System.out.println();

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

        if (shipCordsSave.get(nameOfTheShip) != null) {

            for (Integer[] cords : shipCordsSave.get(nameOfTheShip)) {

                if (isThereHit(cords[0], cords[1])) {
                    cellCount++;
                }

                if (cellCount == shipCordsSave.get(nameOfTheShip).size()) {
                    shipCordsSave.remove(nameOfTheShip);
                    sunken = true;
                    break;
                }
            }
        } else {
            sunken = true;
        }

        return sunken;
    }


}
