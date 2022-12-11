import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class Main {

    private static final BigInteger LCM = new BigInteger("9699690");
    private static final BigInteger THREE = new BigInteger("3");

    private static List<String> lines;

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();

        /* Open file and read lines */
        File file = new File("data.txt");
        lines = Files.readAllLines(file.toPath());

        long part1Solution = part1();
        long part2Solution = part2();

        long timeTaken = System.nanoTime() - startTime;

        /* Output results */
        System.out.printf("Time: %sms\n", timeTaken/1_000_000);
        System.out.printf("Monkey business (20): %s\n", part1Solution);
        System.out.printf("Monkey business (10000): %s\n", part2Solution);
    }

    /**
     * Part 2 of the problem
     * 
     * @return The solution
     */
    private static final long part2() {
        return getMonkeyBusiness(10_000, false);
    }

    /**
     * Part 1 of the problem
     * 
     * @return The solution
     */
    private static final long part1() {
        return getMonkeyBusiness(20, true);
    }

    /**
     * Gets the monkey business after a number of rounds
     * 
     * @param rounds Number of rounds
     * @return The monkey business
     */
    private final static long getMonkeyBusiness(int rounds, boolean divideByThree) {
        Monkey[] monkeys = parseMonkeys();

        /* Time to pass the parcel for 'rounds' rounds */
        for (int round = 0; round < rounds; round++) {
            for (int j = 0; j < monkeys.length; j++) {
                int itemsSize = monkeys[j].items.size();

                for (int k = 0; k < itemsSize; k++) {
                    monkeys[j].inspections++;
                    BigInteger item = monkeys[j].items.remove(0);
                    item = performMonkeyOperation(monkeys[j].operation, item);
                    
                    if (divideByThree) {
                        item = item.divide(THREE);
                    }

                    /* Divide by LCM of all divisors first (from repl.it discord) */
                    item = item.remainder(LCM);

                    if (item.remainder(monkeys[j].divisibleTest) == BigInteger.ZERO) {
                        monkeys[monkeys[j].successMonkey].items.add(item);
                        continue;
                    }

                    monkeys[monkeys[j].failMonkey].items.add(item);
                }
            }
        }

        /* Get monkeys with most inspections (sort in reverse order) */
        List<Monkey> topMonkeys = Arrays.stream(monkeys).sorted(new Comparator<Monkey>() {
            @Override
            public int compare(Monkey m1, Monkey m2) {
                if (m1.inspections > m2.inspections) {
                    return -1;
                }
                if (m1.inspections < m2.inspections) {
                    return 1;
                }
                return 0;
            }
        }).limit(20).collect(Collectors.toList());

        return topMonkeys.get(0).inspections*topMonkeys.get(1).inspections;
    }

    /**
     * Method to execute the monkey's operation on the item
     * worry level
     * 
     * @param operation The operation
     * @param item      The item worry level
     * @return The new item worry level
     */
    private static BigInteger performMonkeyOperation(String operation, BigInteger item) {
        switch (operation.charAt(0)) {
            case '*': {
                if (operation.indexOf("old") != -1) {
                    return item.multiply(item);
                }
                return item.multiply(new BigInteger(operation.split(" ")[1]));
            }
            case '+': {
                if (operation.indexOf("old") != -1) {
                    return item.add(item);
                }
                return item.add(new BigInteger(operation.split(" ")[1]));
            }
        }
        return null;
    }

    /**
     * Method to parse the input into {@code Monkey} objects
     * 
     * @return The {@code Monkey} objects
     */
    private static final Monkey[] parseMonkeys() {
        List<Monkey> monkeys = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            Monkey monkey = new Monkey();
            monkeys.add(monkey);

            monkey.inspections = 0;

            /* First line is monkey declaration */
            line = lines.get(++i);

            /* Second line is items */
            monkey.items = Arrays.stream(
                line.substring(line.indexOf(':')+1, line.length())
                    .replaceAll(" ", "")
                    .split(","))
                .map(BigInteger::new)
                .collect(Collectors.toList());
            line = lines.get(++i);

            /* Third line is operation */
            monkey.operation = line.substring(line.indexOf("d")+2, line.length());
            line = lines.get(++i);

            /* Fourth line is divisible test */
            monkey.divisibleTest = new BigInteger(line.substring(line.lastIndexOf(" ")+1, line.length()));
            line = lines.get(++i);

            /* Fifth line is true monkey */
            monkey.successMonkey = Integer.parseInt(line.substring(line.length()-1, line.length()));
            line = lines.get(++i);

            /* Sixth line is false monkey */
            monkey.failMonkey = Integer.parseInt(line.substring(line.length()-1, line.length()));
            i++;
        }

        return monkeys.toArray(new Monkey[] {});
    }

    /**
     * A class to represent the data in the input
     */
    private static final class Monkey {
        List<BigInteger> items;
        String operation;
        long inspections;
        BigInteger divisibleTest;
        int successMonkey;
        int failMonkey;

        @Override
        public String toString() {
            return " "+inspections;
        }
    }
}
