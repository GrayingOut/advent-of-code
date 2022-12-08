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
        System.out.printf("Visible Trees: %s\n", part1Solution);
        System.out.printf("Highest Scenic Score: %s\n", part2Solution);
    }

    /**
     * Part 2 of the problem
     * 
     * @return The solution
     */
    private static final int part2() {
        int size = lines.size();

        /* Store the scenic scores - outer = y-axis, inner = x-axis */
        int highestScenicScore = -1;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int treeHeight = getTreeHeightAtXY(x, y);

                /* Tree -> Right */
                int rightScore = 0;
                for (int right = x+1; right < size; right++) {
                    int height = getTreeHeightAtXY(right, y);
                    if (height >= treeHeight) {
                        rightScore++;
                        break;
                    }
                    rightScore++;
                }

                /* Tree -> Left */
                int leftScore = 0;
                for (int left = x-1; left > -1; left--) {
                    int height = getTreeHeightAtXY(left, y);
                    if (height >= treeHeight) {
                        leftScore++;
                        break;
                    }
                    leftScore++;
                }

                /* Tree -> Up */
                int upScore = 0;
                for (int up = y-1; up > -1; up--) {
                    int height = getTreeHeightAtXY(x, up);
                    if (height >= treeHeight) {
                        upScore++;
                        break;
                    }
                    upScore++;
                }

                /* Tree -> Down */
                int downScore = 0;
                for (int down = y+1; down < size; down++) {
                    int height = getTreeHeightAtXY(x, down);
                    if (height >= treeHeight) {
                        downScore++;
                        break;
                    }
                    downScore++;
                }
                
                int scenicScore = rightScore * leftScore * upScore * downScore;
                if (scenicScore > highestScenicScore) {
                    highestScenicScore = scenicScore;
                }
            }
        }

        return highestScenicScore;
    }

    /**
     * Part 1 of the problem
     * 
     * @return The solution
     */
    private static final int part1() {
        int size = lines.size();

        /* Keeps track of current trees that are not visible */
        int notVisibleCount = 0;

        /* Outer hashmap is the y-axis, inner hashmap is the x-axis */
        HashMap<Integer, HashMap<Integer, Integer>> notVisible = new HashMap<>();

        /* Left to right */
        int tallest;
        for (int y = 0; y < size; y++) {
            tallest = -1;
            notVisible.putIfAbsent(y, new HashMap<>());

            for (int x = 0; x < size; x++) {
                int treeHeight = getTreeHeightAtXY(x, y);

                /* Check size against current tallest */
                if (treeHeight <= tallest) {
                    /* Not visible */
                    notVisibleCount++;
                    HashMap<Integer, Integer> xHashMap = notVisible.get(y);
                    xHashMap.put(x, x);
                    notVisible.put(y, xHashMap);
                    continue;
                }

                /* Visible */
                tallest = treeHeight;
            }
        }

        /* Right to left */
        for (int y = 0; y < size; y++) {
            tallest = -1;

            for (int x = size-1; x > -1; x--) {
                int treeHeight = getTreeHeightAtXY(x, y);

                /* Check size against current tallest */
                if (treeHeight <= tallest) {
                    /* Not visible */
                    continue;
                }
                
                /* Check if currently not visible */
                if (notVisible.get(y).get(x) != null) {
                    notVisible.get(y).remove(x);
                    notVisibleCount--;
                }

                /* Visible */
                tallest = treeHeight;
            }
        }

        /* Top to bottom */
        for (int x = 0; x < size; x++) {
            tallest = -1;
            for (int y = 0; y < size; y++) {
                String c = lines.get(y).substring(x, x+1);
                int treeHeight = Integer.parseInt(c);

                /* Check size against current tallest */
                if (treeHeight <= tallest) {
                    /* Not visible */
                    continue;
                }
                
                /* Check if currently not visible */
                if (notVisible.get(y).get(x) != null) {
                    notVisible.get(y).remove(x);
                    notVisibleCount--;
                }

                /* Visible */
                tallest = treeHeight;
            }
        }

        /* Bottom to top */
        for (int x = 0; x < size; x++) {
            tallest = -1;
            for (int y = size-1; y > -1; y--) {
                int treeHeight = getTreeHeightAtXY(x, y);

                /* Check size against current tallest */
                if (treeHeight <= tallest) {
                    /* Not visible */
                    continue;
                }
                
                /* Check if currently not visible */
                if (notVisible.get(y).get(x) != null) {
                    notVisible.get(y).remove(x);
                    notVisibleCount--;
                }

                /* Visible */
                tallest = treeHeight;
            }
        }

        int visible = size*size - notVisibleCount;

        return visible;
    }


    /**
     * Gets the height of a tree in the grid at (x,y)
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return THe height
     */
    private static final int getTreeHeightAtXY(int x, int y) {
        return Integer.parseInt(lines.get(y).substring(x, x+1));
    }
}
