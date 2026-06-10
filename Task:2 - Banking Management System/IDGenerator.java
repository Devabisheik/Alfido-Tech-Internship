import java.util.Random;

public class IDGenerator {
    private static Random random = new Random();

    public static String generateID() {
        int number = 1000 + random.nextInt(9000);

        return "C" + number;
    }

    public static long generateAccountNumber() {
        return 1000000000L +
                (long) (random.nextDouble() * 9000000000L);
    }
}
