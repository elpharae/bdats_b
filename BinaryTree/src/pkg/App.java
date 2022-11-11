package pkg;

import java.io.FileNotFoundException;
import java.util.Iterator;

import enums.ETypProhlidky;
import pamatky.Pamatky;
import pamatky.PamatkyException;
import pamatky.Zamek;

public class App {

    public static void main(String[] args) {
        Pamatky pamatky = new Pamatky();

        try {
            System.out.println(pamatky.importDatZTXT(("D:/elpha/Desktop/GitRepos/bdats_b/data.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (PamatkyException e) {
            e.printStackTrace();
        }

        Iterator<Zamek> iteratorSiroky = pamatky.iterator(ETypProhlidky.SIROKA);
        while (iteratorSiroky.hasNext()) {
            System.out.println(iteratorSiroky.next());
        }

    }

}
