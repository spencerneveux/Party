import sun.text.resources.iw.FormatData_iw_IL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Party {
    private int[][] matrix;
    private File file;
    private int size;
    private int k1, k2;

    public Party(String path) {
        size = 0;
        k1 = 0;
        k2 = 0;
        matrix = new int[0][0];
        file = new File(path);
    }

    public void findRequirements() {
        try (Scanner input = new Scanner(file)) {
            //Get k1, and k2 values
            //Consume carriage return
            k1 = input.nextInt();
            k2 = input.nextInt();
            input.nextLine();
            //Find size of the matrix
            while (input.hasNextLine()) {
                size++;
                input.nextLine();
            }
        }catch (FileNotFoundException e) {
            e.getMessage();
        }
    }

    public int[][] fillMatrix() {
        matrix = new int[size][size];
        try (Scanner input2 = new Scanner(file)) {
            //consume first two lines
            input2.nextLine();
            input2.nextLine();
            //Read file and fill in matrix
            for (int i = 0; i < size; i++) {
                String line = input2.nextLine();
                char[] values = line.toCharArray();
                for (int j = 0; j < size; j++) {
                    //subtract 48 from line value to change from ascii value
                    matrix[i][j] = values[j] - 48;
                }
            }
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
        return matrix;
    }

    public int[][] remove(int[][] data) {
        boolean removed = false;
        boolean done = false;
        int unknown = -1;
        int known = 0;

        // As long as we remove people we must recount
        while(!done) {
            done = true;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    // If this person was removed then don't bother checking
                    if (data[i][i] == -1) {
                        removed = true;
                        break;
                    }
                    // Otherwise get the data to count known/unknown people
                    int value = data[i][j];
                    switch (value) {
                        case 0: { unknown++; break; }
                        case 1: { known++; break; }
                    }
                }
                // If the person doesn't meet the requirements then remove them from the party invite list
                if (!removed && (known < k1 || unknown < k2)) {
                    int z = 0;
                    while (z < size) {
                        data[i][z] = -1;
                        data[z][i] = -1;
                        z++;
                    }
                    // make sure we recount the list
                    done = false;
                }
                // reset variables for next run through
                unknown = -1; known = 0; removed = false;
            }
        }
        return data;
    }

    public void printAnswer(int[][] data) {
        try (PrintWriter out = new PrintWriter("party.txt")) {
            // Print Answer to Code file
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length; j++) {
                    if (data[i][j] == -1) {
                        break;
                    } else out.println(i + 1);
                }
            }
        }catch (FileNotFoundException e) {
            e.getMessage();
        }
    }
}
