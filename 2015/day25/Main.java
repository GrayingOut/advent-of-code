
public class Main {

    private static final int startCode = 20151125;
    private static final int multiplier = 252533;
    private static final int divisor = 33554393;

    private static final int goalRow = 2978;
    private static final int goalCol = 3083;

    public static void main(String[] args) {
        long currentCode = startCode;
        
        int row = 2;
        int col = 1;

        boolean found = false;

        while (!found) {
            int tmpRow = row;
            col = 1;

            while (tmpRow >= 1) {
                if (tmpRow == goalRow && col == goalCol) {
                    System.out.printf("%s,%s\n", tmpRow, col);
                }

                currentCode = (currentCode * multiplier) % divisor;

                if (tmpRow == goalRow && col == goalCol) {
                    found = true;
                    break;
                }

                col++;
                tmpRow--;
            }

            row++;
        }

        System.out.println(currentCode);
    }
}
