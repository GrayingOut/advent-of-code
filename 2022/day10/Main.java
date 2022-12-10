import java.io.File;
import java.nio.file.Files;
import java.util.List;

public final class Main {

    private static List<String> lines;

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();

        /* Open file and read lines */
        File file = new File("data.txt");
        lines = Files.readAllLines(file.toPath());

        int part1Solution = part1();
        String part2Solution = part2();

        long timeTaken = System.nanoTime() - startTime;

        /* Output results */
        System.out.printf("Time: %sms\n", timeTaken/1_000_000);
        System.out.printf("Six signal strengths: %s\n", part1Solution);
        System.out.printf("Screen:\n%s\n", part2Solution);
    }

    /**
     * Part 2 of the problem
     * 
     * @return The solution
     */
    private static final String part2() {
        int regX = 1;
        int cycle = 1;
        boolean[][] screen = new boolean[6][40]; /* The screen */

        int increment = 0;
        boolean isAddOp = false;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            int drawingX = cycle;
            int drawingY = 0;
            while (drawingX > 40) {
                drawingX -= 40;
                drawingY++;
            }
            if (Math.abs((drawingX-1) - regX) < 2) {
                screen[drawingY][drawingX-1] = true;
            }

            if (isAddOp) {
                isAddOp = false;
                regX += increment;
                i--;
            } else if (!line.equals("noop")) {
                increment = Integer.parseInt(line.split(" ")[1]);
                isAddOp = true;
            }

            cycle++;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < screen.length; i++) {
            for (int j = 0; j < screen[i].length; j++) {
                if (screen[i][j]) {
                    builder.append("#");
                    continue;
                }
                builder.append(".");
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    /**
     * Part 1 of the problem
     * 
     * @return The solution
     */
    private static final int part1() {
        int cycle = 1;
        int regX = 1;
        int increment = 0;

        int signalStrengthTotals = 0;

        boolean isAddOp = false;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if (isAddOp) {
                isAddOp = false;
                regX += increment;
                i--;
            } else if (!line.equals("noop")) {
                increment = Integer.parseInt(line.split(" ")[1]);
                isAddOp = true;
            }

            cycle++;

            if (cycle == 20 || cycle == 60 || cycle == 100 || cycle == 140 || cycle == 180 || cycle == 220) {
                int signalStrength = cycle*regX;
                signalStrengthTotals += signalStrength;
            }
        }

        return signalStrengthTotals;
    }
}
