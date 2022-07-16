//Roni Sedakah 203486824
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * my database.
 */
public class DataBase {
    private static final String R1 = "<np>[^>]+</np>[ ,]*such[ ,]*as[ ]*<np>[^>]+</np>"
            + "([ ,]*((<np>[^>]+</np>)[ ,]*)*((or|and)[ ]*<np>[^>]+</np>)?)?";
    private static final String R2 = "such <np>[^>]+</np>[ ,]*as[ ]*<np>[^>]+</np>"
            + "(([ ,]*(<np>[^>]+</np>)*[ ,]*)*(or|and)*[ ]*<np>[^>]+</np>)?";
    private static final String R3 = "<np>[^>]+</np>[ ,]*including[ ]*<np>[^>]+</np>"
            + "(([ ,]*(<np>[^>]+</np>)*[ ,]*)*(or|and)*[ ,]*<np>[^>]+</np>)?";
    private static final String R4 = "<np>[^>]+</np>[ ,]*especially[ ,]*<np>[^>]+</np>"
            + "(([ ,]*(<np>[^>]+</np>)*[ ,]*)*(or|and)*[ ,]*<np>[^>]+</np>)?";
    private static final String R5 = "<np>[^>]+</np>[ ,]*which[ , ]*is[ ,]*"
            + "((an[ ,]*example|a[ ,]*kind|a[ ,]*class)[ ,]*of[ ,]*)?<np>[^>]+</np>";

    private final List<Hypernym> hypernymList;
    private final TreeMap<String, Hypernym> hyperTree;



    /**
     * constructor.
     */
    public DataBase() {
       this.hypernymList = new ArrayList<>();
       this.hyperTree = new TreeMap<>();
    }

    /**
     * getter.
     * @return - hypernym list.
     */
    public List<Hypernym> getHypernymList() {
        return hypernymList;
    }

    /**
     * remove hypernym fron the list.
     * @param h - the hypernym to remove.
     */
    public void removeHyper(Hypernym h) {
        this.hypernymList.remove(h);
    }

    /**
     * add hypernym to the list and map.
     * @param name - the name we want to add.
     * @return - the hypernym.
     */
    private Hypernym addHyper(String name) {
        Hypernym hypernym = null;
        if (!this.hyperTree.isEmpty()) {
            if (this.hyperTree.containsKey(name.toLowerCase())) {
                hypernym = this.hyperTree.get(name);
            }
        }
        if (hypernym == null) {
            hypernym = new Hypernym(name);
            this.hypernymList.add(hypernym);
            this.hyperTree.put(name, hypernym);
        }
        return hypernym;
    }

    /**
     * checks for a regex in a line. if found add hypernym and hyponym.
     * @param line - String.
     * @param regex - the regex we're looking for.
     */
    private void checkRegex(String line, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String r = line.substring(matcher.start(), matcher.end());
            String[] split = r.split("<np>");
            String[] nameOf = split[1].split("</np>");
            Hypernym h = this.addHyper(nameOf[0]);
            this.hyperTree.put(h.getName(), h);

            for (int i = 2; i < split.length; i++) {
                nameOf = split[i].split("</np>");
                h.addHypo(nameOf[0]);
            }
        }
    }

    /**
     * check for a regex that has substring "especially".
     * @param line - String.
     */
    public void checkEspeciallyReg(String line) {
        this.checkRegex(line, R4);
    }

    /**
     * check for a regex that has substring "including".
     * @param line - String.
     */
    public void checkInclugingReg(String line) {
        this.checkRegex(line, R3);
    }

    /**
     * check for a regex that has substring "such as".
     * @param line - String.
     */
    public void checkSuchReg(String line) {
        this.checkRegex(line, R1);
        this.checkRegex(line, R2);
    }

    /**
     * check for a regex that has substring "which is".
     * @param line - String.
     */
    public void checkWhichReg(String line) {
        this.checkFor5thReg(line, R5);
    }

    /**
     * secrch for the 5th regex.
     * @param line - String.
     * @param regex - the regex.
     */
    private void checkFor5thReg(String line, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String r = line.substring(matcher.start(), matcher.end());
            String[] split = r.split("<np>");
            String[] nameOf = split[1].split("</np>");
            String h = nameOf[0];

            nameOf = split[2].split("</np>");
            Hypernym hypernym = this.addHyper(nameOf[0]);
            this.hyperTree.put(hypernym.getName(), hypernym);
            hypernym.addHypo(h);
        }
    }

    @Override
    public String toString() {
        String output = "";
        if (!this.hypernymList.isEmpty()) {
            Comparator<Hypernym> hypernymComparator = new HyperComparator();
            this.hypernymList.sort(hypernymComparator);
            for (Hypernym h : this.hypernymList) {
                if (h.getSum() > 2) {
                    output = output + ("\n" + h);
                }
            }
        }
        return output;
    }
}
