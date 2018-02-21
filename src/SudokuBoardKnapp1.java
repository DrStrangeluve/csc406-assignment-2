public class SudokuBoardKnapp1 {
    private int[] boardCells;

    SudokuBoardKnapp1(int boxWidth, int boxHeight) {
        boardCells = new int[(int) Math.pow((boxWidth * boxHeight), 2)];
    }

    public int getRow(int cellNumber) {
        return (cellNumber / getBoardSize()) + 1;
    }

    public int getColumn(int cellNumber) {
        return (cellNumber % getBoardSize());
    }

    public int getValue(int cellNumber) {
        return boardCells[cellNumber];
    }

    public int getBox(int cellNumber) {
        return ((((getRow(cellNumber) - 1) / 3) * 3) + ((getColumn(cellNumber) - 1) / 3) + 1);
    }

    public int getBoxWidth() {
        return (int) Math.sqrt(Math.sqrt(boardCells.length));
    }

    public int getBoxHieght() {
        return (int) Math.sqrt(Math.sqrt(boardCells.length));
    }

    public int getBoardSize() {
        return (int) Math.sqrt(boardCells.length);
    }

    public int numberOfCells() {
        return boardCells.length;
    }

    public void boardToString() {
        int i = 0;
        while (i < getBoardSize()) {
            int j = 0;
            while (j < getBoardSize()) {
                System.out.format(" %d ", getValue(i * getBoardSize() + j));
                j++;
            }
            System.out.format("%n");
            i++;
        }
    }

    public void setValue(int cellNumber, int value) {
        boardCells[cellNumber] = value;
    }
}
