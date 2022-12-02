import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;

public final class Main {

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();

        /* Open file and read data */
        File file = new File("data.txt");
        String content = new String(Files.readAllBytes(file.toPath()));

        ArrayList<Integer> totals = new ArrayList<>();

        /* Total up each block */
        for (String block : content.split("\r\n\r\n")) {
            int total = 0;

            for (String line : block.split("\r\n")) {
                total += Integer.parseInt(line);
            }

            totals.add(total);
        }

        /* Sort and get top total and sum of top 3 total */
        totals.sort(Comparator.reverseOrder());
        
        int highestTotal = totals.get(0);
        int top3Total = totals.get(0) + totals.get(1) + totals.get(2);

        long timeTaken = System.nanoTime() - startTime;

        /* Output results */
        System.out.printf("Time: %sms\n", timeTaken/1_000_000);
        System.out.printf("Highest: %s\n", highestTotal);
        System.out.printf("Top 3: %s\n", top3Total);
    }
}
