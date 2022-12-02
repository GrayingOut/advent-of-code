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

        int part1Score = part1();
        int part2Score = part2();

        long timeTaken = System.nanoTime() - startTime;

        /* Output results */
        System.out.printf("Time: %sms\n", timeTaken/1_000_000);
        System.out.printf("Part 1 Score: %s\n", part1Score);
        System.out.printf("Part 2 Score: %s\n", part2Score);
    }

    /**
     * Part 1 of the problem
     * 
     * @return The solution
     */
    private static final int part1() {
        int score = 0;
        
        for (String line : lines) {
            /* Get score of play */
            int opponentPlay = line.charAt(0)-64;
            int ourPlay = line.charAt(2) - 87;

            score += ourPlay;
            score += getGameScore(opponentPlay, ourPlay);
        }

        return score;
    }

    /**
     * Part 2 of the problem
     * 
     * @return The solution
     */
    private static final int part2() {
        int score = 0;
        
        for (String line : lines) {
            /* Get score of play */
            int opponentPlay = line.charAt(0)-64;
            int gameEnd = line.charAt(2) - 87;
            int ourPlay = getOurPlay(opponentPlay, gameEnd);

            score += ourPlay;
            score += getGameScore(opponentPlay, ourPlay);
        }

        return score;
    }

    /**
     * Gets the play we need to play to end the game
     * as needed
     * 
     * @param opponentPlay The opponent's play
     * @param gameEnd      The needed game end
     * @return Our play
     */
    private static final int getOurPlay(int opponentPlay, int gameEnd) {
        switch (gameEnd) {
            case 1: {
                /* Need a lose */
                return opponentPlay == 1 ? 3 : opponentPlay - 1;
            }
            case 2: {
                /* Need a draw */
                return opponentPlay;
            }
            case 3: {
                /* Need a win */
                return opponentPlay == 3 ? 1 : opponentPlay + 1;
            }
        }

        return -1;
    }

    /**
     * Returns the score of the game
     * 
     * @param opponentPlay The play of the opponent
     * @param ourPlay      Our play
     * @return The game score
     */
    private static final int getGameScore(int opponentPlay, int ourPlay) {
        /* Rock - Rock, Paper - Paper, Scissors - Scissors */
        if (opponentPlay == ourPlay) {
            /* Draw */
            return 3;
        }

        /* Rock - Paper, Paper - Scissors, Scissors - Rock */
        if (!(opponentPlay == 1 && ourPlay == 3) && (opponentPlay < ourPlay || opponentPlay == 3 && ourPlay == 1)) {
            /* Win */
            return 6;
        }

        /* Paper - Rock, Rock - Scissors, Scissors - Paper */
        return 0;
    }
}
