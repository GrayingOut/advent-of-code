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
        int part2Solution = part2();

        long timeTaken = System.nanoTime() - startTime;

        /* Output results */
        System.out.printf("Time: %sms\n", timeTaken/1_000_000);
        System.out.printf("Fully Contained Pairs: %s\n", part1Solution);
        System.out.printf("Overlapped Pairs: %s\n", part2Solution);
    }

    /**
     * Part 2 of the problem
     * 
     * @return The solution
     */
    private static final int part2() {
        int overlappedPairs = 0;

        for (String line : lines) {
            String range1 = line.split(",")[0];
            String range2 = line.split(",")[1];

            /* Get the range boundaries */
            int range1Start = Integer.parseInt(range1.split("-")[0]);
            int range1End = Integer.parseInt(range1.split("-")[1]);
            int range2Start = Integer.parseInt(range2.split("-")[0]);
            int range2End = Integer.parseInt(range2.split("-")[1]);

            if ((range1Start <= range2End && range1End >= range2Start)) {
                overlappedPairs++;
                continue;
            }
        }

        return overlappedPairs;
    }

    /**
     * Part 1 of the problem
     * 
     * @return The solution
     */
    private static final int part1() {
        int containedPairs = 0;

        for (String line : lines) {
            String range1 = line.split(",")[0];
            String range2 = line.split(",")[1];

            /* Get the range boundaries */
            int range1Start = Integer.parseInt(range1.split("-")[0]);
            int range1End = Integer.parseInt(range1.split("-")[1]);
            int range2Start = Integer.parseInt(range2.split("-")[0]);
            int range2End = Integer.parseInt(range2.split("-")[1]);

            /* Check range 1 contained within range 2 */
            if (range1Start >= range2Start && range1End <= range2End) {
                containedPairs++;
                continue;
            }

            /* Check range 2 contained within range 1 */
            if (range2Start >= range1Start && range2End <= range1End) {
                containedPairs++;
                continue;
            }
        }

        return containedPairs;
    }
}
