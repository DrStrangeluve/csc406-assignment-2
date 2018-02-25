import java.io.File;

public class MainKnapp1 {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: <fullFilePath>");
            System.exit(1);
        }
        else {
            new SudokuToSatReducerKnapp1(new File(args[0]));
        }
    }
}
