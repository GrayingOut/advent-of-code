import java.io.File;
import java.nio.file.Files;
import java.util.List;

public final class Main {

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();

        /* Open file and read lines */
        File file = new File("data.txt");
        List<String> lines = Files.readAllLines(file.toPath());

        int score = 0;

        for (String line : lines) {
            /* Get score of play */
            int opponentPlayScore = line.charAt(0)-64;
            int gameEnd = line.charAt(2) - 87;
            int ourPlayScore = 0;

            /* Calculate our play */
            switch (gameEnd) {
                case 1: {
                    /* Need a lose */
                    ourPlayScore = opponentPlayScore == 1 ? 3 : opponentPlayScore - 1;
                    break;
                }
                case 2: {
                    /* Need a draw */
                    ourPlayScore = opponentPlayScore;
                    break;
                }
                case 3: {
                    /* Need a win */
                    ourPlayScore = opponentPlayScore == 3 ? 1 : opponentPlayScore + 1;
                }
            }

            score += ourPlayScore;

            /* Rock - Scissors */
            if (opponentPlayScore == 1 && ourPlayScore == 3) {
                /* Lose */
                score += 0;
                continue;
            }

            /* Scissors - Rock */
            if (opponentPlayScore == 3 && ourPlayScore == 1) {
                /* Win */
                score += 6;
                continue;
            }
            
            /* Rock - Rock, Paper - Paper, Scissors - Scissors */
            if (opponentPlayScore == ourPlayScore) {
                /* Draw */
                score += 3;
                continue;
            }
            
            /* Rock - Paper, Paper - Scissors */
            if (opponentPlayScore < ourPlayScore) {
                /* Win */
                score += 6;
                continue;
            }
            
            /* Lose */
            score += 0;
        }

        long timeTaken = System.nanoTime() - startTime;

        /* Output results */
        System.out.printf("Time: %sms\n", timeTaken/1_000_000);
        System.out.printf("Score: %s\n", score);
    }
}
