//Roni Sedakah 203486824
import java.util.Comparator;

/**
 * hypernym comparator.
 */
public class HyperComparator implements Comparator<Hypernym> {

    @Override
    public int compare(Hypernym o1, Hypernym o2) {
        return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
    }
}
