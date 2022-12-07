import java.io.File;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public final class Main {

    private static List<String> lines;

    private static HashMap<String, Integer> fs;

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
        System.out.printf("Sum of <=100,000: %s\n", part1Solution);
        System.out.printf("Smallest dir to delete: %s\n", part2Solution);
    }

    /**
     * Part 2 of the problem
     * 
     * @return The solution
     */
    private static final int part2() {
        /* Sort the directories in reverse order */
        List<String> sortedDirectories = fs.keySet().stream().sorted(new Comparator<String>() {
            @Override
            public int compare(String path1, String path2) {
                if (fs.get(path1) > fs.get(path2)) {
                    return -1;
                }

                if (fs.get(path1) < fs.get(path2)) {
                    return 1;
                }

                return 0;
            } 
        }).collect(Collectors.toList());

        /* Get the smallest directory that will free up
         * at least 30000000
         */

        int usedSpace = fs.get("/");
        int freeSpace = 70000000 - usedSpace;
        int neededSpace = 30000000 - freeSpace;

        String prevDir = "";
        for (String dir : sortedDirectories) {
            if (dir.equals("/")) {
                continue;
            }

            if (fs.get(dir) < neededSpace) {
                return fs.get(prevDir);
            }

            prevDir = dir;
        }

        return -1;
    }

    /**
     * Part 1 of the problem
     * 
     * @return The solution
     */
    private static final int part1() {

        /* HashMap of all file paths and their size */
        fs = new HashMap<>();
        fs.put("/", 0);
        String currentPath = "";

        boolean isListing = false;

        for (String line : lines) {
            if (line.startsWith("$")) {
                isListing = false;

                String[] commandParts = line.split(" ");
                if (commandParts[1].equals("ls")) {
                    /* Listing directory */
                    isListing = true;
                    continue;
                }
                /* Changing directory */
                if (commandParts[2].equals("..")) {
                    /* Move up */
                    currentPath = currentPath.substring(0, currentPath.lastIndexOf("/"));
                    continue;
                }
                /* Moving into directory */
                currentPath = (currentPath + "/" + commandParts[2]).replace("//", "/");
                fs.putIfAbsent(currentPath, 0);
                continue;
            }

            if (isListing) {
                String[] itemParts = line.split(" ");
                if (itemParts[0].equals("dir")) {
                    /* Directory */
                    continue;
                }
                
                /* Add size to current directory */
                fs.put(currentPath, fs.get(currentPath) + Integer.parseInt(itemParts[0]));
                
                /* Add size to upper directories */
                String pathTmp = currentPath;
                while (pathTmp.length() != 1) {
                    if (pathTmp.lastIndexOf("/") != 0) {
                        pathTmp = pathTmp.substring(0, pathTmp.lastIndexOf("/"));
                    } else {
                        pathTmp = "/";
                    }
                    fs.put(pathTmp, fs.get(pathTmp) + Integer.parseInt(itemParts[0]));
                }
            }
        }

        /* Get sum of <=100,000 */
        int total = 0;
        
        for (String key : fs.keySet()) {
            if (fs.get(key) <= 100_000) {
                total += fs.get(key);
            }
        }

        return total;
    }
}
