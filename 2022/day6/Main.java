import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;

public final class Main {

    private static String packet;

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();

        /* Open file and read lines */
        File file = new File("data.txt");
        packet = new String(Files.readAllBytes(file.toPath()));

        int part1Solution = part1();
        int part2Solution = part2();

        long timeTaken = System.nanoTime() - startTime;

        /* Output results */
        System.out.printf("Time: %sms\n", timeTaken/1_000_000);
        System.out.printf("Start-of-packet: %s\n", part1Solution);
        System.out.printf("Start-of-message: %s\n", part2Solution);
    }

    /**
     * Part 2 of the problem
     * 
     * @return The solution
     */
    private static final int part2() {
        int startIndex = 0;
        
        for (int i = 0; i < packet.length()-14; i++) {
            boolean hasRepeat = false;
            String packetSection = packet.substring(i, i+14);
            
            HashMap<Character, Character> characters = new HashMap<>();
            for (char c : packetSection.toCharArray()) {
                if (characters.get(c) != null) {
                    hasRepeat = true;
                    break;
                }

                characters.put(c, c);
            }

            if (!hasRepeat) {
                startIndex = i;
                break;
            }
        }

        return startIndex + 14;
    }

    /**
     * Part 1 of the problem
     * 
     * @return The solution
     */
    private static final int part1() {
        int startIndex = 0;
        
        for (int i = 0; i < packet.length()-4; i++) {
            boolean hasRepeat = false;
            String packetSection = packet.substring(i, i+4);
            
            HashMap<Character, Character> characters = new HashMap<>();
            for (char c : packetSection.toCharArray()) {
                if (characters.get(c) != null) {
                    hasRepeat = true;
                    break;
                }

                characters.put(c, c);
            }

            if (!hasRepeat) {
                startIndex = i;
                break;
            }
        }

        return startIndex + 4;
    }
}
