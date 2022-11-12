package pkg;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

import ads.AbstrTableException;
import enums.ETypKlice;
import enums.ETypProhlidky;
import pamatky.GPS;
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
            return o1.getLokace().compareTo(o2.getLokace()) * -1;
        });

        System.out.println(ar.get(ar.size() - 1).getLokace().toString());

        try {
            pamatky.nastavKlic(ETypKlice.GPS);
            Zamek x = pamatky.najdiNejbliz("49.0000 13.0000");
            GPS lokace = new GPS(49.0000f, 13.0000f, x.getLokace());
            System.out.println(x.toString());
            System.out.println(lokace.toString() + " km");

        } catch (PamatkyException e) {
            e.printStackTrace();
        } catch (AbstrTableException e) {
            e.printStackTrace();
        }
        
    }

}
