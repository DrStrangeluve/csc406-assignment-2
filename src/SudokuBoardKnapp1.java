public class SudokuBoardKnapp1 {
    private int boxWidth;
    private int boxHeight;
    private int[] boardCells;

    SudokuBoardKnapp1(int inBoxWidth, int inBoxHeight) {
        boxWidth = inBoxWidth;
        boxHeight = inBoxHeight;
        boardCells = new int[(int) Math.pow((boxWidth * boxHeight), 2)];
    }

    public int getRow(int cellNumber) {
        return (cellNumber / getBoardSize());
    }

    public int getColumn(int cellNumber) {
        return (cellNumber % getBoardSize());
    }

    public int getValue(int cellNumber) {
        return boardCells[cellNumber];
    }

    public int getBox(int cellNumber) {
        return (getColumn(cellNumber) + boxWidth * getRow(cellNumber));
    }

    public int getBoxWidth() {
        return boxWidth;
    }

    public int getBoxHieght() {
        return boxHeight;
    }

    public int getBoardSize() {
        return (int) Math.sqrt(boardCells.length);
    }

    public int numberOfCells() {
        return boardCells.length;
    }

    public String boardToString() {
        String returnString = "";
        int i = 0;
        while (i < getBoardSize()) {
            int j = 0;
            while (j < getBoardSize()) {
                returnString = String.format("%s%s", returnString, String.format("%d", getValue(i * getBoardSize() + j)));
                j++;
            }
            returnString = String.format("%s%n", returnString);
            i++;
        }
        return returnString;
    }

    public void setValue(int cellNumber, int value) {
        boardCells[cellNumber] = value;
    }
}
