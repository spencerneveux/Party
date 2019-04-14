import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        String path = "src/description1.txt";
        Party party = new Party(path);
        party.findRequirements();
        int[][] filledMatrix = party.fillMatrix();
        int[][] result_matrix = party.remove(filledMatrix);
        party.printAnswer(result_matrix);
    }
}
