import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class SudokuToSatReducerKnapp1 {

    SudokuToSatReducerKnapp1(File inputFile) {
        File outputFile = createOutputFile(inputFile.getAbsolutePath().substring(0, inputFile.getAbsolutePath().lastIndexOf((File.separator))) + File.separator + inputFile.getName().split("\\.")[0] + "_dimacs." + inputFile.getName().split("\\.")[1]);
        SudokuBoardKnapp1 board = createBoard(inputFile);

    }

    public File createOutputFile(String fileName) {
        File file = new File(fileName);
        try {
            file.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public SudokuBoardKnapp1 createBoard(File inputFile) {
        SudokuBoardKnapp1 board = null;
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
        return board;
    }

    public void reduceBoard() {

    }

    public void calcTime() {
        TimerKnapp1 timer = new TimerKnapp1();
        timer.start();
        reduceBoard();
        timer.stop();
        System.out.printf("\nreduceBoard took %d milliseconds.", (timer.getDuration() / 1000000));
    }


}
