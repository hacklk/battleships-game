package battleship;

public class Player {
    private final Square square;

    public Player(Square square) {
        this.square = square;
    }

    public boolean player(int y, int x) {

        boolean sunken = false;
        String nameOfTheShip = "";

        for (String shipName : square.getShipCordsSave().keySet()) {

            for (int i = 0; i < square.getShipCordsSave().get(shipName).size(); i++) {
                if (y == square.getShipCordsSave().get(shipName).get(i)[0] && x == square.getShipCordsSave().get(shipName).get(i)[1]) {

                    nameOfTheShip = shipName;
                    break;
                }
            }

        }

        int cellCount = 0;

        if (square.getShipCordsSave().get(nameOfTheShip) != null) {

            for (Integer[] cords : square.getShipCordsSave().get(nameOfTheShip)) {

                if (square.isThereHit(cords[0], cords[1])) {
                    cellCount++;
                }

                if (cellCount == square.getShipCordsSave().get(nameOfTheShip).size()) {
                    square.getShipCordsSave().remove(nameOfTheShip);
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