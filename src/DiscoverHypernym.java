//Roni Sedakah 203486824

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;

/**
 * discover hypernym.
 */
public class DiscoverHypernym {
    private final DataBase dataBase;
    private final String lemma;
    private final String input;

    /**
     * constructor.
     * @param path - path to the docs given.
     * @param lemmaName - lemma given.
     */
    public DiscoverHypernym(String path, String lemmaName) {
        this.input = path;
        this.dataBase = new DataBase();
        this.lemma = lemmaName;
    }

    /**
     * read the docs.
     */
    public void readDocs() {
        File file = new File(this.input);
        File[] files = file.listFiles();
        String lemma = "<np>" + this.lemma + "</np>";
        BufferedReader br = null;
        if (files != null) {
            for (File file1 : files) {
                try {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (line.toLowerCase().contains(lemma.toLowerCase())) {
                            if (line.contains("especially")) {
                                this.dataBase.checkEspeciallyReg(line);
                            } else if (line.contains("including")) {
                                this.dataBase.checkInclugingReg(line);
                            } else if (line.contains("such")) {
                                this.dataBase.checkSuchReg(line);
                            } else if (line.contains("which")) {
                                this.dataBase.checkWhichReg(line);
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println("something went wrong while reading the file");
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            System.out.println("Failed closing the file");
                        }
                    }
                }
            }
        }
    }

    /**
     * delete from the list the hypernym that doesn't contain the lemma.
     * update the sum field according to the number of times the lemma exist.
     */
    public void rearrangeData() {
        for (int i = 0; i < this.dataBase.getHypernymList().size(); i++) {
            Hyponym hyponym = this.dataBase.getHypernymList().get(i).searchLemma(this.lemma);
            if (hyponym == null) {
                this.dataBase.removeHyper(this.dataBase.getHypernymList().get(i));
                i--;
            } else {
                this.dataBase.getHypernymList().get(i).setSum(hyponym.getCounter());
            }
            Comparator<Hypernym> comparator = new LemmaComparator();
            this.dataBase.getHypernymList().sort(comparator);
        }
    }

    /**
     * find the lemma given in the list.
     */
    public void findLemma() {
        this.rearrangeData();
        if (this.dataBase.getHypernymList().isEmpty()) {
            System.out.println("The lemma doesn't appear in the corpus.");
        }
        for (int i = 0; i < this.dataBase.getHypernymList().size(); i++) {
            Hypernym hypernym = this.dataBase.getHypernymList().get(i);
            System.out.println(hypernym.getName() + ":" + " (" + hypernym.getSum() + ")");
        }
    }

    /**
     * main.
     * @param args - args given.
     */
    public static void main(String[] args) {
        String docs = args[0];
        String lemma = args[1];
        for (int i = 2; i < args.length; i++) {
            lemma = lemma + " " + args[i];
        }
        DiscoverHypernym discoverHypernym = new DiscoverHypernym(docs, lemma);
        discoverHypernym.readDocs();
        discoverHypernym.findLemma();
    }
}
