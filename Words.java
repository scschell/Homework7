import java.util.Scanner;

/**
    Count unique words.

    There's some basic timing code as well in case you're not
    able to use the UNIX time(1) command.
*/
public final class Words {
    private static Map<String, Integer> data;
    private static final double NANOS = 1e9;

    private Words() { /* hide constructor */ }

    /**
        Main method.

        @param args command line arguments
    */
    public static void main(String[] args) {
        data = new BinarySearchTreeMap<String, Integer>();
        Scanner scanner = new Scanner(System.in);

        long before = System.nanoTime();

        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            String[] words = s.split("[\\s\\W]+");
            for (String word: words) {
                if (word.length() <= 1) { continue; }
                if (data.has(word)) {
                    data.put(word, data.get(word) + 1);
                } else {
                    data.insert(word, 1);
                }
            }
        }

        for (String word: data) {
            System.out.println(word + ": " + data.get(word));
        }

        long duration = System.nanoTime() - before;
        System.err.println(duration / NANOS);
    }
}
