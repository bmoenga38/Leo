public class Stat {
    public static int cacheHits = 0;
    public static int diskReads = 0;
    public static int diskWrites = 0;

    public static void reset() {
        cacheHits = 0;
        diskReads = 0;
        diskWrites = 0;
    }
}
