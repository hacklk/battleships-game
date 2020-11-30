package battleship;

import java.util.Arrays;
import java.util.HashMap;

public class Field {

    private String[][] field;
    private StringBuilder stringBuilder;
    private char[] letters;
    private HashMap<Character, Integer> letterBind;

    public Field() {
        this.letters = "ABCDEFGHIJ".toCharArray();
        this.field = new String[10][10];
        for (int i = 0; i < field.length; i++) {
            Arrays.fill(field[i], "~");
        }

        this.letterBind = new HashMap<>();
        for (int i = 0; i < letters.length; i++) {
            this.letterBind.put(letters[i], i + 1);
        }
    }

    public String[][] getField() {
        return field;
    }

    public HashMap<Character, Integer> getLetterBind() {
        return letterBind;
    }

    public void printField() {
        this.stringBuilder = new StringBuilder();
        stringBuilder.append("  1 2 3 4 5 6 7 8 9 10\n");

        for (int i = 0; i < getField().length; i++) {
            stringBuilder.append(letters[i]).append(" ");
            for (int j = 0; j < getField()[i].length; j++) {
                stringBuilder.append(getField()[i][j]).append(" ");
            }
            stringBuilder.append("\n");
        }

        System.out.println(stringBuilder);
    }
}
