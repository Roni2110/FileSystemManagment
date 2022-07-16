//Roni Sedakah 203486824
import java.util.Comparator;

/**
 * hyponym comparator.
 */
public class HypoComparator implements Comparator<Hyponym> {

    @Override
    public int compare(Hyponym o1, Hyponym o2) {
        if (o1.getCounter() == o2.getCounter()) {
            return o1.getName().compareTo(o2.getName());
        }
        if (o1.getCounter() > o2.getCounter()) {
            return -1;
        }
        return 1;
    }
}
