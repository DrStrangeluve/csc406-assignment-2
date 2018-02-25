import java.io.*;
import java.util.Scanner;

public class SudokuToSatReducerKnapp1 {
    private static final String outputFileName = "output/sudoku_output.cnf";
    private Writer writer;
    private SudokuBoardKnapp1 board;

    SudokuToSatReducerKnapp1(File inputFile) {
        createOutputFile(outputFileName);
        createBoard(inputFile);
        calcTime();

    }

    private void createOutputFile(String fileName) {
        try {
            writer = new PrintWriter(fileName);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createBoard(File inputFile) {
        try {
            Scanner in = new Scanner(inputFile);
            board = new SudokuBoardKnapp1(in.nextInt(), in.nextInt());
            int cellCounter = 0;
            while (in.hasNextInt()) {
                board.setValue(cellCounter, in.nextInt());
                cellCounter++;
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void reduceBoard() {
        write(String.format("p cnf %d %d%n", board.getVariableCount(), board.getClauseCount(4)));
        // Since the rows, columns and boxes are calculated separately,
        // but use the same counters,
        // i can be used for both values
        for (int i = 1; i <= board.getBoardSize(); i++) {
            // The values for k are then looped through
            for (int k = 1; k <= board.getBoardSize(); k++) {
                atleastOneInRow(i, k);
                atmostOneInRow(i, k);
                atleastOneInCol(i, k);
                atmostOneInCol(i, k);
                atleastOneInBox(i, k);
                atmostOneInBox(i, k);
            }
        }
        // Lastly the cells need to be looped through for constraint 4
        for (int i = 1; i < board.numberOfCells(); i++) {
            atleastOneInCell(i);
            atmostOneInCell(i);
        }
    }

    public void calcTime() {
        TimerKnapp1 timer = new TimerKnapp1();
        timer.start();
        reduceBoard();
        timer.stop();
        System.out.printf("\nreduceBoard took %d milliseconds.", (timer.getDuration() / 1000000));
    }

    private void write(String s) {
        try {
            writer.write(s);
            writer.flush();

        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void atleastOneInRow(int row, int value) {
        // Loop through each col for the row and add the constraint, finally add a 0 and a new line
        for (int i = 1; i <= board.getBoardSize(); i++) {
            write(String.format("%d%d%d ", row, i, value));
        }
        write(String.format("0%n"));
    }

    private void atmostOneInRow(int row, int value) {
        // Loop through each column value minus one as it will be included in the formula
        for (int i = 1; i < board.getBoardSize(); i++) {
            write(String.format("-%d%d%d ", row, i, value));
            write(String.format("-%d%d%d 0%n", row, i + 1, value));
        }
    }

    private void atleastOneInCol(int col, int value) {
        // Loop through each row for the col and add the constraint, finally add a 0 and a new line
        for (int i = 1; i <= board.getBoardSize(); i++) {
            write(String.format("%d%d%d ", i, col, value));
        }
        write(String.format("0%n"));
    }

    private void atmostOneInCol(int col, int value) {
        // Loop through each row value minus one as it will be included in the formula
        for (int i = 1; i < board.getBoardSize(); i++) {
            write(String.format("-%d%d%d ", i, col, value));
            write(String.format("-%d%d%d 0%n", i + 1, col, value));
        }
    }

    private void atleastOneInBox(int box, int value) {

    }

    private void atmostOneInBox(int box, int value) {

    }

    private void atleastOneInCell(int cell) {
        // Loop through each k value for the row and col and add the constraint, finally add a 0 and a new line
        // Due to 0 based add one to the variables
        int cell_row = board.getRow(cell) + 1;
        int cell_col = board.getColumn(cell) + 1;
        for (int i = 1; i <= board.getBoardSize(); i++) {
            write(String.format("%d%d%d ", cell_row, cell_col, i));
        }
        write(String.format("0%n"));
    }

    private void atmostOneInCell(int cell) {
        // Loop through each k value minus one as it will be included in the formula
        // Due to 0 based add one to the variables
        int cell_row = board.getRow(cell) + 1;
        int cell_col = board.getColumn(cell) + 1;
        for (int i = 1; i < board.getBoardSize(); i++) {
            write(String.format("-%d%d%d ", cell_row, cell_col, i));
            write(String.format("-%d%d%d 0%n", cell_row, cell_col, i + 1));
        }
    }
}
