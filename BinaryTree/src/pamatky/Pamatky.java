package pamatky;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import ads.AbstrTable;
import enums.ETypKlice;
import enums.ETypProhlidky;

public class Pamatky implements IPamatky<Zamek> {

    private AbstrTable<Float, Zamek> strom;
    private Zamek koren;

    private static final float PREVOD_NA_STUPNE = 1/60f;

    public Pamatky() {
        this.strom = new AbstrTable<Float, Zamek>();
    }

    @Override
    public int importDatZTXT(String soubor) throws PamatkyException, FileNotFoundException {
        if (soubor.isEmpty()) throw new IllegalArgumentException("Vybrany soubor neexistuje");
        if (!Files.exists(Paths.get(soubor))) throw new FileNotFoundException("Vybrany soubor neexistuje");

        try (
            BufferedReader reader = new BufferedReader(new FileReader(soubor));
            ) 
        {
            strom.zrus();

            String radek;
            int pocet = 0;

            while ((radek = reader.readLine()) != null) {
                if (radek.length() == 0 || radek.isEmpty()) break;

                radek = radek.replaceAll("^", " ");
                String nazev = radek.substring(69, 91).trim();

                radek = radek.replaceAll("\\s+"," ").trim();
                String[] radekData = radek.split(" ");

                float sirkaS = Float.parseFloat(radekData[2].substring(1));
                float sirkaM = Float.parseFloat(radekData[3]);
                float delkaS = Float.parseFloat(radekData[4].substring(2));
                float delkaM = Float.parseFloat(radekData[5]);
 
                float sirka = sirkaS + sirkaM * PREVOD_NA_STUPNE;
                float delka = delkaS + delkaM * PREVOD_NA_STUPNE;

                GPS lokace = new GPS(sirka, delka);
                Zamek zamek = new Zamek(nazev, lokace);

                if (strom.jePrazdny()) {
                    this.koren = zamek;
                    strom.vloz(0f, zamek);
                } else {
                    Float vzdalenostOdKorene = zamek.getLokace().vzdalenostOd(this.koren.getLokace());
                    strom.vloz(vzdalenostOdKorene, zamek);
                }

                pocet++;
            }

            System.out.println("POCET ZAMKU V STROMU:" + strom.getPocet());
            
            return pocet;
        } catch (IOException | NumberFormatException e) {
            strom.zrus();
            e.printStackTrace();
            throw new PamatkyException("Chyba pri nacitani souboru");
        }
    }

    @Override
    public void vlozZamek(Zamek zamek) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Zamek najdiZamek(String klic) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Zamek odeberZamek(String klic) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Zamek najdiNejbliz(String klic) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void zrus() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void prebuduj() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void nastavKlic(ETypKlice typKlice) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Iterator<Zamek> iterator() {
        return strom.iterator(ETypProhlidky.HLUBOKA);
    }
    
}
