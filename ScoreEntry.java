package find_and_flip;

public class ScoreEntry implements Comparable<ScoreEntry> {
    public float timeTaken;
    public String difficulty;

    public ScoreEntry(float timeTaken, String difficulty) {
        this.timeTaken = timeTaken;
        this.difficulty = difficulty;
    }

    @Override
    public int compareTo(ScoreEntry o) {
        return Float.compare(this.timeTaken, o.timeTaken); // ascending order
    }

    @Override
    public String toString() {
        return difficulty + " - " + timeTaken + "s";
    }
}
//public class ScoreEntry implements Comparable<ScoreEntry> {
//    public float timeTaken;
//    public String difficulty;
//
//    public ScoreEntry(float timeTaken, String difficulty) {
//        this.timeTaken = timeTaken;
//        this.difficulty = difficulty;
//    }
//
//    @Override
//    public int compareTo(ScoreEntry o) {
//        return Float.compare(this.timeTaken, o.timeTaken);
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%.1fs - %s", timeTaken, difficulty);
//    }
//}
