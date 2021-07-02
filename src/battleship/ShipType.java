package battleship;

enum ShipType {

    CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2),
    SUBMARINE("Submarine", 1);

    private final String name;
    private final int length;

    ShipType(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }
}
