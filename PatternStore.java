import java.io.*;
import java.net.*;
import java.util.*;

public class PatternStore {
    private ArrayList<Pattern> patterns = new ArrayList<>();

    // Constructor for PatternStore
    public PatternStore(String source) throws IOException {
        if (source.startsWith("http://") || source.startsWith("https://")) {
            loadFromURL(source);
        } else {
            loadFromDisk(source);
        }
    }

    // Constructor for PatternStore
    public PatternStore(Reader source) throws IOException {
        load(source);
    }

    /** Reads each line from 'r' and prints it to the screen. */
    private void load(Reader r) throws IOException {
        BufferedReader br = new BufferedReader(r);
        String line;
        while ((line = br.readLine()) != null) {
            try {
                patterns.add(new Pattern(line));
                System.out.println(line);
            } catch (Exception e) {
                System.out.print("Warning: a malformed line.   ");
                System.out.println(e.getMessage());
            }
        }
        br.close();
    }

    /** Reads patterns from a file over the web given a URL. */
    private void loadFromURL(String url) throws IOException {
        URL destination = new URL(url);
        URLConnection conn = destination.openConnection();
        Reader r = new InputStreamReader(conn.getInputStream());
        load(r);
    }

    /** Reads patterns from a file. */
    private void loadFromDisk(String filename) throws IOException {
        Reader r = new FileReader(filename);
        load(r);
    }

    /**
     * @return an ArrayList containing the patterns in sorted order.
     */
    public ArrayList<Pattern> getPatternsNameSorted() {
        ArrayList<Pattern> copy = new ArrayList<>(patterns);
        Collections.sort(copy);
        return copy;
    }

    /**
     * @return an array of patterns sorted according to their names.
     */
    public Pattern[] getPatterns() {
        ArrayList<Pattern> sortedPatterns = getPatternsNameSorted();
        Pattern[] patternArray = new Pattern[patterns.size()];
        for (int i = 0; i < patternArray.length; i++) {
            patternArray[i] = sortedPatterns.get(i);
        }
        return patternArray;
    }
}
