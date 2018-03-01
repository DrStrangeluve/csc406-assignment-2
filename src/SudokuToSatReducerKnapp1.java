import java.io.*;
import java.util.Scanner;

public class SudokuToSatReducerKnapp1 {
    private static final String outputFileName = "sudoku_output.cnf";
    private Writer writer;
    private SudokuBoardKnapp1 board;

    SudokuToSatReducerKnapp1(File inputFile) {
        createOutputFile(outputFileName);
        createBoard(inputFile);
        // optional, but shows the board on the cnf
        cnfWriteHeader();
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
            while(in.findInLine("c ") != null) {
                in.nextLine();
            }
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
        // but use the same counters, thus i can be used for all three
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int k = 1; k <= board.getBoardSize(); k++) {
                atleastOneInRow(i, k);
                atmostOneInRow(i, k);
                atleastOneInCol(i, k);
                atmostOneInCol(i, k);
                atleastOneInBox(i, k);
                atmostOneInBox(i, k);
            }
        }
        // Add prefilled cells as clauses
        // Lastly the cells need to be looped through for constraint
        for (int i = 0; i < board.numberOfCells(); i++) {
            prefilledValue(i);
            atleastOneInCell(i);
            atmostOneInCell(i);
        }
    }

    private void calcTime() {
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
        for (int j = 0; j < board.getBoardSize(); j++) {
            write(String.format("%d ", convertToSatFormat(row, j, value)));
        }
        write(String.format("0%n"));
    }

    private void atmostOneInRow(int row, int value) {
        // Loop through each column, and get each value down to the current col value
        for (int j = 0; j < board.getBoardSize(); j++) {
            for (int k = board.getBoardSize() - 1; k > j; k--) {
                write(String.format("-%d ", convertToSatFormat(row, j, value)));
                write(String.format("-%d 0%n", convertToSatFormat(row, k, value)));
            }
        }
    }

    private void atleastOneInCol(int col, int value) {
        // Loop through each row for the col and add the constraint, finally add a 0 and a new line
        for (int i = 0; i < board.getBoardSize(); i++) {
            write(String.format("%d ", convertToSatFormat(i, col, value)));
        }
        write(String.format("0%n"));
    }

    private void atmostOneInCol(int col, int value) {
        // Loop through each row, and get each value down to the current row value
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int k = board.getBoardSize() - 1; k > i; k--) {
                write(String.format("-%d ", convertToSatFormat(i, col, value)));
                write(String.format("-%d 0%n", convertToSatFormat(k, col, value)));
            }
        }
    }

    private void atleastOneInBox(int box, int value) {
    int[] boxArray = convertTo1DArray(box);
        for (int i = 0; i <= boxArray.length -1; i++) {
            write(String.format("%d ", convertToSatFormat(board.getRow(boxArray[i]), board.getColumn(boxArray[i]), value)));
        }
        write(String.format("0%n"));
    }

    private void atmostOneInBox(int box, int value) {
        int[] boxArray = convertTo1DArray(box);
        for (int i = 0; i <= boxArray.length -1; i++) {
            for (int j = boxArray.length - 1; j > i; j--) {
                write(String.format("-%d ", convertToSatFormat(board.getRow(boxArray[i]), board.getColumn(boxArray[i]), value)));
                write(String.format("-%d 0%n", convertToSatFormat(board.getRow(boxArray[j]), board.getColumn(boxArray[j]), value)));
            }
        }
    }

    private void atleastOneInCell(int cell) {
        // Loop through each k value for the row and col and add the constraint, finally add a 0 and a new line
        int cell_row = board.getRow(cell);
        int cell_col = board.getColumn(cell);
        for (int i = 1; i <= board.getBoardSize(); i++) {
            write(String.format("%d ", convertToSatFormat(cell_row, cell_col, i)));
        }
        write(String.format("0%n"));
    }

    private void atmostOneInCell(int cell) {
        // Loop through each k value minus one as it will be included in the formula
        int cell_row = board.getRow(cell);
        int cell_col = board.getColumn(cell);
        for (int i = 1; i < board.getBoardSize(); i++) {
            for (int k = board.getBoardSize(); k > i; k--) {
                write(String.format("-%d ", convertToSatFormat(cell_row, cell_col, i)));
                write(String.format("-%d 0%n", convertToSatFormat(cell_row, cell_col, k)));
            }
        }
    }

    private void prefilledValue(int cell) {
        // Add the prefilled values
        int prefilledValue = board.getValue(cell);
        if (prefilledValue != 0) {
            int cell_row = board.getRow(cell);
            int cell_col = board.getColumn(cell);
            write(String.format("%d 0%n", convertToSatFormat(cell_row, cell_col, prefilledValue)));
        }
    }

    private void cnfWriteHeader() {
        write("c Original Sudoku Input\n");
        write(String.format("c %d %d\n", board.getBoxHeight(), board.getBoxWidth()));
        String[] lines = board.boardToString().split(System.getProperty("line.separator"));
        for (String line: lines) {
            write(String.format("c %s\n", line));
        }
    }

    // Calculate the starting and ending row, col for a given box based on box size and box number
    // Convert box to 1D array for easy iterating
    private int[] convertTo1DArray(int box) {
        int[] returnArray = new int[board.getBoardSize()];
        int rowStart = ((box) / board.getBoxHeight()) * board.getBoxHeight();
        int rowEnd = rowStart + board.getBoxHeight() - 1;
        int colStart = ((box) % board.getBoxWidth()) * board.getBoxWidth();
        int colEnd = colStart + board.getBoxWidth() - 1;
        int counter = 0;
        for (int i = rowStart; i <= rowEnd; i++) {
            for (int j = colStart; j <= colEnd; j++) {
                returnArray[counter] = board.getCell(i, j);
                counter++;
            }
        }
        return returnArray;
    }

    private int convertToSatFormat(int row, int col, int value) {
        return (board.numberOfCells() * row) + (board.getBoardSize() * col) + value;
    }
}
