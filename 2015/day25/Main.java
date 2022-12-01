
public final class Main {

    private static final int startCode = 20151125;
    private static final int multiplier = 252533;
    private static final int divisor = 33554393;

    private static final int goalRow = 2978;
    private static final int goalCol = 3083;

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        /* Initial code */
        long currentCode = startCode;
        
        /* Current row and col start positions */
        int row = 2;
        int col = 1;

        boolean found = false;

        /* Brute force the code until at row and col */
        while (!found) {
            /* Temp row for allowing manipulation */
            int tmpRow = row;
            col = 1;

            /* Repeats until reaches first row */
            while (tmpRow >= 1) {
                /* Calculate the next code */
                currentCode = (currentCode * multiplier) % divisor;

                /* Check if at target row and col */
                if (tmpRow == goalRow && col == goalCol) {
                    found = true;
                    break;
                }

                /* Decrement row and increment col (moves to next grid cell) */
                col++;
                tmpRow--;
            }

            /* Update the start row */
            row++;
        }

        long timeTaken = System.nanoTime() - startTime;

        /* Output result */
        System.out.printf("Time: %sms\n", timeTaken/1_000_000);
        System.out.printf("Code: %s\n", currentCode);
    }
}
