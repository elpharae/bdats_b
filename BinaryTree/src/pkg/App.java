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
            System.out.println(pamatky.importDatZTXT(("D:/elpha/Desktop/GitRepos/bdats_b/data_copy.txt")));
 /* 
            Zamek Z1 = new Zamek("Z1", new GPS(0, 0, null));
            Zamek Z2 = new Zamek("Z2", new GPS(49.443863f, 14.03388f, Z1.getLokace()));
            Zamek Z3 = new Zamek("Z3", new GPS(49.9776f, 15.156138f, Z1.getLokace()));
            Zamek Z4 = new Zamek("Z4", new GPS(49.612026f, 14.501385f, Z1.getLokace()));
            Zamek Z5 = new Zamek("Z5", new GPS(49.373245f, 13.94068f, Z1.getLokace()));
            Zamek Z6 = new Zamek("Z6", new GPS(49.75646f, 15.487268f, Z1.getLokace()));
            Zamek Z7 = new Zamek("Z7", new GPS(49.6458f, 14.29848f, Z1.getLokace()));
            Zamek Z8 = new Zamek("Z8", new GPS(49.601513f, 14.619665f, Z1.getLokace()));
            Zamek Z9 = new Zamek("Z9", new GPS(49.29196f, 14.8071f, Z1.getLokace()));
            Zamek Z10 = new Zamek("Z10", new GPS(49.86615f, 15.579f, Z1.getLokace()));
            Zamek Z11 = new Zamek("Z11", new GPS(49.554455f, 14.302092f, Z1.getLokace()));
            Zamek Z12 = new Zamek("Z12", new GPS(49.18474f, 15.450811f, Z1.getLokace()));
            Zamek Z13 = new Zamek("Z13", new GPS(50.193764f, 13.339151f, Z1.getLokace()));
*/          
            pamatky.nastavKlic(ETypKlice.NAZEV);
            
            Iterator<Zamek> it = pamatky.iterator(ETypProhlidky.HLUBOKA);
            while (it.hasNext()) {
                Zamek x = it.next();
                //System.out.println(x);
            }

            pamatky.prebuduj();

            it = pamatky.iterator(ETypProhlidky.HLUBOKA);
            while (it.hasNext()) {
                Zamek x = it.next();
                System.out.println(x);
            }
/* 
            String lokace = "50 17";
            Zamek nalezeny = pamatky.najdiNejbliz(lokace);
            System.out.println(nalezeny.toString());
            System.out.println("Nachazi se " + nalezeny.getLokace().vzdalenostOd(new GPS(50, 17)) + " km od lokace: " + lokace);
*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (PamatkyException e) {
            e.printStackTrace();
        }
    }
}
