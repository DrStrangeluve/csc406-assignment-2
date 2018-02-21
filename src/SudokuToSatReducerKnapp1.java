import java.io.File;

public class SudokuToSatReducerKnapp1 {

    SudokuToSatReducerKnapp1(File inputFile) {
        //temp for debug purposes
        SudokuBoardKnapp1 board = new SudokuBoardKnapp1(3, 3);
        int boardWidthOrHeight = board.getBoardSize();
        int cellCount = board.numberOfCells();
        int[] input_array = {5, 4, 0, 2, 0, 9, 0, 0, 1, 0, 0, 0, 5, 0, 0, 0, 0, 4, 0, 0, 7, 0, 0, 0, 9, 0, 0, 8, 0, 0, 0, 3, 0, 0, 6, 7, 0, 0, 0, 6, 0, 5, 0, 0, 0, 9, 3, 0, 0, 1, 0, 0, 0, 2, 0, 0, 1, 0, 0, 0, 6, 0, 0, 2, 0, 0, 0, 0, 6, 0, 0, 0, 3, 0, 0, 1, 0, 7, 0, 4, 9};
        int counter = 0;
        for (int item : input_array) {
            board.setValue(counter, item);
            counter++;
        }
        board.boardToString();
        int row1 = board.getRow(1);
        int row2 = board.getRow(79);
        int column1 = board.getColumn(1);
        int column2 = board.getColumn(79);
        int boxWidth = board.getBoxWidth();
        int boxHeight = board.getBoxHieght();
        int box1 = board.getBox(11);
        int box2 = board.getBox(15);
        int box3 = board.getBox(17);
        int box4 = board.getBox(38);
        int box5 = board.getBox(41);
        int box6 = board.getBox(44);
        int box7 = board.getBox(65);
        int box8 = board.getBox(68);
        int box9 = board.getBox(71);
        System.out.println("DEBUG");
    }
}
