//Roni Sedakah 203486824

/**
 * hyponym class.
 */
public class Hyponym {
    private final String name;
    private int counter;

    /**
     * constructor.
     * @param s - the name we want to find.
     */
    public Hyponym(String s) {
        this.name = s;
        this.counter = 1;
    }

    /**
     * getter.
     * @return - the name of the hyponym.
     */
    public String getName() {
        return this.name;
    }

    /**
     * getter.
     * @return - the counter.
     */
    public int getCounter() {
        return counter;
    }

    /**
     * add to counter.
     */
    public void addToCounter() {
        this.counter++;
    }

    @Override
    public String toString() {
        return name + " (" + counter + ")";
    }

}
