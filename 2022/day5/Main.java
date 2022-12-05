import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public final class Main {

    private static List<String> lines;

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();

        /* Open file and read lines */
        File file = new File("data.txt");
        lines = Files.readAllLines(file.toPath());

        String part1Solution = part1();
        String part2Solution = part2();

        long timeTaken = System.nanoTime() - startTime;

        /* Output results */
        System.out.printf("Time: %sms\n", timeTaken/1_000_000);
        System.out.printf("Stack Tops: %s\n", part1Solution);
        System.out.printf("Overlapped Pairs: %s\n", part2Solution);
    }

    /**
     * Part 2 of the problem
     * 
     * @return The solution
     */
    private static final String part2() {
        getStacks();
        List<Stack<Character>> stacks = getStacks();

        /* I like to move it move it */
        for (int i = 10; i < lines.size(); i++) {
            String[] move = lines.get(i).split(" ");

            int count = Integer.parseInt(move[1]);
            int from = Integer.parseInt(move[3]) - 1;
            int to = Integer.parseInt(move[5]) - 1;

            Stack<Character> tmp = new Stack<>();

            for (int j = 0; j < count; j++) {
                char crate = stacks.get(from).pop();
                tmp.push(crate);
            }

            for (int j = 0; j < count; j++) {
                stacks.get(to).push(tmp.pop());
            }
        }

        /* Get the tops of the stacks */
        StringBuilder stackTops = new StringBuilder();
        for (Stack<Character> stack : stacks) {
            stackTops.append(stack.peek());
        }

        return stackTops.toString();
    }

    /**
     * Part 1 of the problem
     * 
     * @return The solution
     */
    private static final String part1() {
        List<Stack<Character>> stacks = getStacks();

        /* I like to move it move it */
        for (int i = 10; i < lines.size(); i++) {
            String[] move = lines.get(i).split(" ");

            int count = Integer.parseInt(move[1]);
            int from = Integer.parseInt(move[3]) - 1;
            int to = Integer.parseInt(move[5]) - 1;

            for (int j = 0; j < count; j++) {
                char crate = stacks.get(from).pop();
                stacks.get(to).push(crate);
            }
        }

        /* Get the tops of the stacks */
        StringBuilder stackTops = new StringBuilder();
        for (Stack<Character> stack : stacks) {
            stackTops.append(stack.peek());
        }

        return stackTops.toString();
    }

    /**
     * Parses the file into its stacks
     * 
     * @return The stacks
     */
    private static final List<Stack<Character>> getStacks() {
        List<Stack<Character>> stacks = new ArrayList<>();
        
        /* Create the empty stacks */
        String stackNumbersLine = lines.get(8);
        for (int i = 0; i < stackNumbersLine.length(); i++) {
            if (stackNumbersLine.charAt(i) != ' ') {
                stacks.add(new Stack<>());
            }
        }

        /* Populate the stacks */
        for (int i = 7; i > -1; i--) {
            String line = lines.get(i);

            for (int j = 1; j < line.length(); j += 4) {
                char c = line.charAt(j);
                if (c != ' ') {
                    stacks.get((j-1)/4).push(c);
                }
            }
        }

        return stacks;
    }
}
