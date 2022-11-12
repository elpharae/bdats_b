package pkg;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

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

        
        ArrayList<Zamek> zamky = new ArrayList<Zamek>();
        Iterator<Zamek> iterator = pamatky.iterator(ETypProhlidky.SIROKA);
        while (iterator.hasNext()) {
            Zamek x = iterator.next();
            System.out.println(x.getNazev() + ": " + x.getLokace().getVzdalenost() + " km");
            zamky.add(x);
        }
 
        zamky.sort((z1, z2) -> {
            if (z1.getLokace().getVzdalenost() > z2.getLokace().getVzdalenost()) return -1;
            else if (z1.getLokace().getVzdalenost() < z2.getLokace().getVzdalenost()) return 1;
            else return 0;
        });

        System.out.println("Nejmensi vzdalenost v datech: " + zamky.get(zamky.size() - 2).getLokace().getVzdalenost());
        System.out.println("Nejmensi vzdalenost diky algoritmu: " + pamatky.najdiNejbliz());

    }

}
