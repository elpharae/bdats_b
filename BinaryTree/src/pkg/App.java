package pkg;

import java.io.FileNotFoundException;
import java.util.Iterator;

import ads.AbstrTable;
import enums.ETypProhlidky;
import pamatky.GPS;
import pamatky.Pamatky;
import pamatky.PamatkyException;
import pamatky.Zamek;

public class App {

    public static void main(String[] args) {
        /* 
        bst.vloz(10, "10");
        bst.vloz(2, "2");
        bst.vloz(12, "12");
        bst.vloz(1, "1");
        bst.vloz(5, "5");
        bst.vloz(14, "14"); 


        Iterator<String> it = bst.iterator(ETypProhlidky.HLUBOKA);
        while (it.hasNext()) {
            System.out.println(it.next());
        }


        Zamek chyse = new Zamek("Chyse", new GPS(50.10585333f, 13.24659333f));
        Zamek slatinany = new Zamek("Slatinany", new GPS(49.91786167f, 15.81089333f));

        System.out.println(chyse.getLokace().vzdalenostOd(slatinany.getLokace()));
*/

        Pamatky pamatky = new Pamatky();

        try {
            System.out.println(pamatky.importDatZTXT(("D:/elpha/Desktop/GitRepos/bdats_b/data.txt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (PamatkyException e) {
            e.printStackTrace();
        }

        Iterator<Zamek> iteratorSiroky = pamatky.iterator();
        while (iteratorSiroky.hasNext()) {
            System.out.println(iteratorSiroky.next());
        }

    }

}
