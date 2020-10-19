package net.htlgrieskirchen.pos3.sudoku;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/* Please enter here an answer to task four between the tags:
 * <answerTask4>
 *    Hier sollte die Antwort auf die Aufgabe 4 stehen.
 * </answerTask4>
 */
public class SudokuSolver implements ISodukoSolver {

    public SudokuSolver() {
        //initialize if necessary
    }

    @Override
    public final int[][] readSudoku(File file) {
        int[][] arr = new int[9][9];
//        int counter = 0;
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String line = br.readLine();
//            while (line != null) {
//                String[] tmp = line.split(";");
//                if (line.equals("")) {
//                    line = br.readLine();
//                    continue;
//                }
//                for (int i = 0; i < tmp.length - 1; i++) {
//                    arr[counter][i] = Integer.parseInt(tmp[i]);
//                }
//                line = br.readLine();
//                counter++;
//            }
//            br.close();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(SudokuSolver.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(SudokuSolver.class.getName()).log(Level.SEVERE, null, ex);
//        }
        try {
            arr = Files.lines(file.toPath())
                    .map(s -> s.split(";"))
                    .map(a -> new int[]{Integer.parseInt(a[0]),
                Integer.parseInt(a[1]),
                Integer.parseInt(a[2]),
                Integer.parseInt(a[3]),
                Integer.parseInt(a[4]),
                Integer.parseInt(a[5]),
                Integer.parseInt(a[6]),
                Integer.parseInt(a[7]),
                Integer.parseInt(a[8])})
                    .toArray(int[][]::new);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arr;

    }

    @Override
    public boolean checkSudoku(int[][] rawSudoku) {
        Set<Integer> set = new HashSet<>();
        boolean erg = true;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                erg = set.add(rawSudoku[i][j]);
                if (!erg) {
                    return erg;
                }
            }
            set.clear();
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                erg = set.add(rawSudoku[j][i]);
                if (!erg) {
                    return erg;
                }
            }
            set.clear();
        }

        for (int blockcolumn = 0; blockcolumn < 3; blockcolumn++) {
            for (int blockrow = 0; blockrow < 3; blockrow++) {
                for (int row = blockrow * 3; row < blockrow * 3 + 3; row++) {
                    for (int column = blockcolumn * 3; column < blockcolumn * 3 + 3; column++) {
                        erg = set.add(rawSudoku[row][column]);
                        if (!erg) {
                            return erg;
                        }
                    }
                }
                set.clear();
            }
        }

        return erg;
    }

    @Override
    public int[][] solveSudoku(int[][] rawSudoku) {

    }

    @Override
    public int[][] solveSudokuParallel(int[][] rawSudoku) {

    }

    public long benchmark(int[][] rawSudoku) {
        long durchschnitt = 0;
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            checkSudoku(rawSudoku);
            solveSudoku(rawSudoku);
            durchschnitt += System.currentTimeMillis() - start;
        }
        return durchschnitt / 10;
    }

    public long benchmarkParalell(int[][] rawSudoku) {
        long durchschnitt = 0;
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            checkSudoku(rawSudoku);
            solveSudokuParallel(rawSudoku);
            durchschnitt += System.currentTimeMillis() - start;
        }
        return durchschnitt / 10;
    }

}
