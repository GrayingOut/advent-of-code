import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
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
        System.out.printf("Visited Positions: %s\n", part1Solution);
        System.out.printf("Visited Positions: %s\n", part2Solution);
    }

    /**
     * Part 2 of the problem
     * 
     * @return The solution
     */
    private static final int part2() {

        /* Store the position of the head and ropes 0=head, 1-9=rope */
        int[] ropeRow = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] ropeCol = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        int tailVisitedCount = 1;
        HashMap<Integer, HashMap<Integer, Integer>> tailVisitedPosition = new HashMap<>();
        tailVisitedPosition.put(0, new HashMap<>());
        tailVisitedPosition.get(0).put(0, 0);

        for (String line : lines) {
            String direction = line.split(" ")[0];
            int count = Integer.parseInt(line.split(" ")[1]);

            for (int i = 0; i < count; i++) {
                /* Move head in direction */
                switch (direction) {
                    case "U": ropeRow[0]++; break;
                    case "D": ropeRow[0]--; break;
                    case "L": ropeCol[0]--; break;
                    case "R": ropeCol[0]++; break;
                }

                /* Check every rope */
                for (int j = 1; j < 10; j++) {
                    /* Check previous rope in range */
                    if (Math.abs(ropeCol[j]-ropeCol[j-1]) < 2 && Math.abs(ropeRow[j] - ropeRow[j-1]) < 2) {
                        continue;
                    }

                    /* Check if on same row */
                    if (ropeRow[j] == ropeRow[j-1]) {
                        if (ropeCol[j] < ropeCol[j-1]) {
                            ropeCol[j]++;
                        } else if (ropeCol[j] > ropeCol[j-1]) {
                            ropeCol[j]--;
                        }
                    /* Check if in same col */
                    } else if (ropeCol[j] == ropeCol[j-1]) {
                        if (ropeRow[j] < ropeRow[j-1]) {
                            ropeRow[j]++;
                        } else if (ropeRow[j] > ropeRow[j-1]) {
                            ropeRow[j]--;
                        }
                    /* Not in same col or row */
                    } else {
                        if (ropeRow[j] < ropeRow[j-1]) {
                            ropeRow[j]++;
                        } else {
                            ropeRow[j]--;
                        }
    
                        if (ropeCol[j] < ropeCol[j-1]) {
                            ropeCol[j]++;
                        } else {
                            ropeCol[j]--;
                        }
                    }

                    /* Check if last rope */
                    if (j == 9) {
                        /* Add new position, and check if it didn't exist */
                        tailVisitedPosition.putIfAbsent(ropeRow[9], new HashMap<>());
                        if (tailVisitedPosition.get(ropeRow[9]).putIfAbsent(ropeCol[9], ropeCol[9]) == null) {
                            tailVisitedCount++;
                        }
                    }
                }

            }
        }

        return tailVisitedCount;
    }

    /**
     * Part 1 of the problem
     * 
     * @return The solution
     */
    private static final int part1() {

        int visitedCount = 1;
        HashMap<Integer, HashMap<Integer, Integer>> visitedPositions = new HashMap<>();

        visitedPositions.put(0, new HashMap<>());
        visitedPositions.get(0).put(0, 0);

        /* Store positions */
        int headCol = 0;
        int headRow = 0;
        int tailCol = 0;
        int tailRow = 0;

        for (String line : lines) {
            String direction = line.split(" ")[0];
            int count = Integer.parseInt(line.split(" ")[1]);

            for (int i = 0; i < count; i++) {
                /* Move head in direction */
                switch (direction) {
                    case "U": headRow--; break;
                    case "D": headRow++; break;
                    case "L": headCol--; break;
                    case "R": headCol++; break;
                }

                /* Check tail in range */
                if (Math.abs(headCol-tailCol) < 2 && Math.abs(headRow - tailRow) < 2) {
                    continue;
                }

                /* Check if on same row */
                if (tailRow == headRow) {
                    if (tailCol < headCol) {
                        tailCol++;
                    } else if (tailCol > headCol) {
                        tailCol--;
                    }
                /* Check if in same col */
                } else if (tailCol == headCol) {
                    if (tailRow < headRow) {
                        tailRow++;
                    } else if (tailRow > headRow) {
                        tailRow--;
                    }
                /* Not in same col or row */
                } else {
                    if (tailRow < headRow) {
                        tailRow++;
                    } else {
                        tailRow--;
                    }

                    if (tailCol < headCol) {
                        tailCol++;
                    } else {
                        tailCol--;
                    }
                }

                /* Add new position, and check if it didn't exist */
                visitedPositions.putIfAbsent(tailRow, new HashMap<>());
                if (visitedPositions.get(tailRow).putIfAbsent(tailCol, tailCol) == null) {
                    visitedCount++;
                }
            }
        }

        return visitedCount;
    }
}
