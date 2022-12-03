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
        System.out.printf("Priority Total 1: %s\n", part1Solution);
        System.out.printf("Priority Total 2: %s\n", part2Solution);
    }

    /**
     * Part 2 of the problem
     * 
     * @return The solution
     */
    private static final int part2() {
        int priorityTotal = 0;
        
        for (int i = 0; i < lines.size(); i += 3) {
            char commonChar = findCommonChar(lines.get(i), lines.get(i+1), lines.get(i+2));

            priorityTotal += getCharacterPriority(commonChar);
        }
        
        return priorityTotal;
    }

    /**
     * Part 1 of the problem
     * 
     * @return The solution
     */
    private static final int part1() {
        int priorityTotal = 0;

        for (String line : lines) {
            String compartment1 = line.substring(0, line.length()/2);
            String compartment2 = line.substring(line.length()/2, line.length());

            char commonChar = findCommonChar(compartment1, compartment2);
            
            /* Get the character priority */
            priorityTotal += getCharacterPriority(commonChar);
        }

        return priorityTotal;
    }

    /**
     * Gets the priority of the character using ASCII codes
     * 
     * @param ch The character
     * @return The priority
     */
    private static final int getCharacterPriority(int ch) {
        /* A: 65, a: 97 */
        if (ch >= 97) {
            return ch - 96;
        } else {
            return ch - 38;
        }
    }

    /**
     * Finds the common character between the 3 rucksacks
     * 
     * @param r1 Rucksack 1
     * @param r2 Rucksack 2
     * @param r3 Rucksack 3
     * @return The common character
     */
    private static final char findCommonChar(String r1, String r2, String r3) {
        HashMap<Character, Character> rucksack1Characters = new HashMap<>();
        HashMap<Character, Character> rucksack2Characters = new HashMap<>();

        for (char c : r1.toCharArray()) {
            rucksack1Characters.put(c, c);
        }

        for (char c : r2.toCharArray()) {
            rucksack2Characters.put(c, c);
        }
        
        for (char c : r3.toCharArray()) {
            if (rucksack1Characters.get(c) != null && rucksack2Characters.get(c) != null) {
                return c;
            }
        }

        return 0;
    }

    /**
     * Finds the common character between the two compartments
     * 
     * @param s1 Compartment 1
     * @param s2 Compartment 2
     * @return The common character
     */
    private static final char findCommonChar(String c1, String c2) {
        HashMap<Character, Character> compartment1Characters = new HashMap<>();

        for (char c : c1.toCharArray()) {
            compartment1Characters.put(c, c);
        }

        for (char c : c2.toCharArray()) {
            if (compartment1Characters.get(c) != null) {
                return c;
            }
        }

        return 0;
    }
}
