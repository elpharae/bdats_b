package pamatky;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import ads.AbstrTable;
import ads.AbstrTableException;
import enums.ETypKlice;
import enums.ETypProhlidky;

public class Pamatky implements IPamatky {

    private AbstrTable strom;
    private ETypKlice aktualniKlic;

    private Zamek koren;

    private static final float PREVOD_NA_STUPNE = 1/60f;

    public Pamatky() {
        this.strom = new AbstrTable();
        this.aktualniKlic = ETypKlice.NAZEV;
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

                if(pocet == 0) radek = radek.substring(3);

                radek = radek.replaceAll("^", " ");
                String nazev = radek.substring(69, 90).trim();

                radek = radek.replaceAll("\\s+"," ").trim();
                String[] radekData = radek.split(" ");

                float sirkaS = Float.parseFloat(radekData[2].substring(1));
                float sirkaM = Float.parseFloat(radekData[3]);
                float delkaS = Float.parseFloat(radekData[4].substring(2));
                float delkaM = Float.parseFloat(radekData[5]);
                float sirka = sirkaS + sirkaM * PREVOD_NA_STUPNE;
                float delka = delkaS + delkaM * PREVOD_NA_STUPNE;

                GPS lokace;
                Zamek zamek;
                if (pocet == 0) {
                    lokace = new GPS(sirka, delka, null);
                    zamek = new Zamek(nazev, lokace);
                    this.koren = zamek;
                } else {
                    lokace = new GPS(sirka, delka, this.koren.getLokace());
                    zamek = new Zamek(nazev, lokace);
                }

                vlozZamek(zamek);
                pocet++;
            }
            
            return pocet;
        } catch (IOException | NumberFormatException e) {
            strom.zrus();
            throw new PamatkyException("Chyba pri nacitani souboru");
        }
    }

    @Override
    public void vlozZamek(Zamek zamek) {
        if (this.strom.jePrazdny()) this.koren = zamek;

        switch (this.aktualniKlic) {
            case GPS -> this.strom.vloz(Float.parseFloat(zamek.getLokace().toString()), zamek);
            case NAZEV -> this.strom.vloz(zamek.getNazev(), zamek);
        }
    }

    @Override
    public Zamek najdiZamek(String klic) throws PamatkyException, AbstrTableException {
        if (this.strom.jePrazdny()) throw new PamatkyException("Neni kde vyhledavat, strom je prazdny");
        if (klic == null) throw new NullPointerException("Spatne zadany klic");
        if (klic.isEmpty()) throw new IllegalArgumentException("Prazdny klic");

        switch (this.aktualniKlic) {
            case GPS -> {
                return (Zamek) this.strom.najdi(Float.parseFloat(stringToGPS(klic).toString()));
            }
            case NAZEV -> {
                return (Zamek) this.strom.najdi(klic);
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public Zamek odeberZamek(String klic) throws PamatkyException, AbstrTableException {
        if (this.strom.jePrazdny()) throw new PamatkyException("Neni co odebirat, strom je prazdny");
        if (klic == null) throw new NullPointerException("Spatne zadany klic");
        if (klic.isEmpty()) throw new IllegalArgumentException("Prazdny klic");

        switch (this.aktualniKlic) {
            case GPS -> {
                return (Zamek) this.strom.odeber(Float.parseFloat(stringToGPS(klic).toString()));
            }
            case NAZEV -> {
                return (Zamek) this.strom.odeber(klic);
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public Zamek najdiNejbliz(String klic) throws PamatkyException, AbstrTableException {
        if (this.aktualniKlic != ETypKlice.GPS) throw new PamatkyException("Spatne predvoleny typ klice");
        if (this.strom.jePrazdny()) throw new PamatkyException("Neni kde vyhledavat, strom je prazdny");
        if (klic == null || klic.isEmpty()) throw new IllegalArgumentException("Prazdny klic");

        Iterator<Zamek> it = this.strom.iterator(ETypProhlidky.SIROKA);
        Zamek pocatek = new Zamek("Pocatecni lokace", stringToGPS(klic));

        if (pocatek == null) throw new PamatkyException("Spatne zadany pocatecni bod");

        Zamek min = null;
        while (it.hasNext()) {
            Zamek i = it.next();
            i.getLokace().setKoren(pocatek.getLokace());

            if (min == null) {
                min = i;
                continue;
            }

            if (i.getLokace().compareTo(min.getLokace()) < 0) min = i;
        }
        
        return min;
    }

    private GPS stringToGPS(String gps) {
        String[] data = gps.split(" ");
        if (data.length != 2) throw new IllegalArgumentException("Spatne zadana lokace, pouzijte format \"50.1234 50.1234\"");

        GPS lokace = new GPS(Float.parseFloat(data[0]), Float.parseFloat(data[1]), null);
        return lokace;
    }

    @Override
    public void zrus() {
        strom.zrus();
        this.koren = null;

        this.aktualniKlic = ETypKlice.GPS;
    }

    @Override
    public void prebuduj() {
        
    }

    @Override
    public void nastavKlic(ETypKlice typKlice) {
        this.aktualniKlic = typKlice;

        prebuduj();
    }

    @Override
    public Iterator<Zamek> iterator(ETypProhlidky typProhlidky) {
        return strom.iterator(typProhlidky);
    }
    
}
