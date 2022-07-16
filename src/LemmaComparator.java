//Roni Sedakah 203486824
import java.util.Comparator;

/**
 * finding lemma comparator.
 */
public class LemmaComparator implements Comparator<Hypernym> {
    @Override
    public int compare(Hypernym o1, Hypernym o2) {
        if (o1.getSum() == o2.getSum()) {
            return o1.getName().compareTo(o2.getName());
        }
        if (o1.getSum() > o2.getSum()) {
            return -1;
        }
        return 1;
    }
}
