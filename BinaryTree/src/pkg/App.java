package pkg;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

import ads.AbstrTableException;
import enums.ETypKlice;
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

        ArrayList<Zamek> ar = new ArrayList<Zamek>();
        Iterator<Zamek> iterator = pamatky.iterator(ETypProhlidky.SIROKA);
        while (iterator.hasNext()) {
            Zamek x = iterator.next();
            ar.add(x);
            //System.out.println(x.getNazev() + " ----- " + x.getLokace().toString() + " km");
        }

        ar.sort((o1, o2) -> {
            if (Float.parseFloat(o1.getLokace().toString()) > Float.parseFloat(o2.getLokace().toString())) return -1;
            else if (Float.parseFloat(o1.getLokace().toString()) < (Float.parseFloat(o2.getLokace().toString()))) return 1;
            else return 0;
        });

        //System.out.println(ar.get(ar.size() - 2).getLokace().toString());

        try {
            pamatky.nastavKlic(ETypKlice.GPS);
            System.out.println(pamatky.najdiNejbliz("50.0000 16.0000"));
        } catch (PamatkyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (AbstrTableException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
