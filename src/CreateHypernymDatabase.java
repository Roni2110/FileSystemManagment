//Roni Sedakah 203486824

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * create hypernym database.
 */
public class CreateHypernymDatabase {
    private final String docs;
    private final String output;
    private final DataBase dataBase;

    /**
     * constructor.
     * @param d - String.
     * @param o - String.
     */
    public CreateHypernymDatabase(String d, String o) {
        this.dataBase = new DataBase();
        this.docs = d;
        this.output = o;
    }

    /**
     * read the files given.
     */
    public void readDocs() {
        File file = new File(docs);
        File[] files = file.listFiles();
        BufferedReader br = null;
        if (files != null) {
            for (File file1 : files) {
                try {
                    //Scanner fileReader = new Scanner(file1);
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(file1)));
                    String line;
                    while ((line = br.readLine()) != null) {
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
                } catch (IOException e) {
                    System.out.println("Something went wrong while reading the file");
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
     * write to a file.
     */
    public void writeDocs() {
        OutputStreamWriter os = null;
        try {
            os = new OutputStreamWriter(new FileOutputStream(this.output));
            os.write(this.dataBase.toString());
        } catch (IOException e) {
            System.out.println("Error Writing");
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    System.out.println("Failed closing file!");
                }
            }
        }
    }

    /**
     * main.
     * @param args - args given.
     */
    public static void main(String[] args) {
        String docs = args[0];
        String output = args[1];
        CreateHypernymDatabase chd = new CreateHypernymDatabase(docs, output);
        chd.readDocs();
        chd.writeDocs();
    }

}
