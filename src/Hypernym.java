//Roni Sedakah 203486824
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * hypernym class.
 */
public class Hypernym {
    private final String name;
    private int sum;
    private final List<Hyponym> hyponyms;
    private final TreeMap<String, Hyponym> hyponymTreeMap;

    /**
     * constructor.
     * @param s - the name we want to find.
     */
    public Hypernym(String s) {
        this.name = s;
        this.sum = 0;
        this.hyponyms = new ArrayList<>();
        this.hyponymTreeMap = new TreeMap<>();
    }

    /**
     * getter.
     * @return - the name of the hypernym.
     */
    public String getName() {
        return this.name;
    }

    /**
     * setter.
     * @param newSum - the new total.
     */
    public void setSum(int newSum) {
        this.sum = newSum;
    }

    /**
     * getter.
     * @return - total.
     */
    public int getSum() {
        return this.sum;
    }

    /**
     * search if a lemma given is in the hyponyms list.
     * @param lemma - the lemma given.
     * @return - the hyponym found, otherwise null.
     */
    public Hyponym searchLemma(String lemma) {
        if (!this.hyponymTreeMap.isEmpty()) {
            if (this.hyponymTreeMap.containsKey(lemma)) {
                return this.hyponymTreeMap.get(lemma);
            }
        }
        return null;
    }

    /**
     * add hyponym to the hypernym list.
     * @param hypuStr - the given hyponym.
     */
    public void addHypo(String hypuStr) {
        Hyponym hyponym = this.searchLemma(hypuStr);
        if (hyponym != null) {
            hyponym.addToCounter();
        } else {
            hyponym = new Hyponym(hypuStr);
            this.hyponyms.add(hyponym);
            this.hyponymTreeMap.put(hypuStr, hyponym);
            this.sum++;
        }
    }

    @Override
    public String toString() {
        Comparator<Hyponym> comparator = new HypoComparator();
        this.hyponyms.sort(comparator);
        String output = this.name + ": ";
        for (int i = 0; i < this.hyponyms.size(); i++) {
            output = output + this.hyponyms.get(i).toString();
            if (i != this.hyponyms.size() - 1) {
                output = output + ", ";
            }
        }
        return output;
    }









}
