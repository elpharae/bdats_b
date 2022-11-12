package pamatky;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

import ads.AbstrDoubleList;
import ads.AbstrTable;
import ads.AbstrTableException;
import enums.ETypKlice;
import enums.ETypProhlidky;

public class Pamatky implements IPamatky {

    private AbstrTable strom;
    private ETypKlice aktualniKlic;

    private static final float PREVOD_NA_STUPNE = 1/60f;

    static Zamek koren;

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

                GPS lokace = new GPS(sirka, delka, this.koren == null ? null : this.koren.getLokace());
                Zamek zamek = new Zamek(nazev, lokace);
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
        switch (this.aktualniKlic) {
            case GPS -> {
                if (this.strom.jePrazdny()) {
                    this.koren = zamek;
                    this.strom.vloz(0f, zamek);
                } else {
                    strom.vloz(zamek.getLokace().getVzdalenost(), zamek);
                }
            }
            case NAZEV -> {
                if (this.strom.jePrazdny()) this.koren = zamek;

                this.strom.vloz(zamek.getNazev(), zamek);
            }
        }
    }

    @Override
    public Zamek najdiZamek(String klic) throws PamatkyException, AbstrTableException {
        if (this.strom.jePrazdny()) throw new PamatkyException("Neni kde vyhledavat, strom je prazdny");
        if (klic == null) throw new NullPointerException("Spatne zadany klic");

        switch (this.aktualniKlic) {
            case GPS -> {
                return (Zamek) this.strom.najdi(((GPS) klic).getVzdalenost());
            }
            case NAZEV -> {
                return (Zamek) this.strom.najdi((String) klic);
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

        switch (aktualniKlic) {
            case GPS -> {
                return (Zamek) this.strom.odeber(((GPS) klic).getVzdalenost());
            }
            case NAZEV -> {
                return (Zamek) this.strom.odeber((String) klic);
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public Zamek najdiNejbliz(String klic) throws PamatkyException {
        if (this.aktualniKlic != ETypKlice.GPS) throw new PamatkyException("Spatne predvoleny typ klice");
        if (this.strom.jePrazdny()) throw new PamatkyException("Neni kde vyhledavat, strom je prazdny");
        
        if (klic == null) throw new NullPointerException("Spatne zadany klic");
        if (klic.isEmpty()) throw new IllegalArgumentException("Prazdny klic");

        Iterator<Zamek> it = this.strom.iterator(ETypProhlidky.HLUBOKA);
        // prvni prvek z iteratoru ma vzdy vzdalenost 0
        // nutno jej preskocit
        it.next();

        Zamek min = it.next();
        while (it.hasNext()) {
            Zamek x = it.next();

            float vzdX = x.getLokace().getVzdalenost();
            float vzdMin = min.getLokace().getVzdalenost();
            if (vzdX < vzdMin) {
                min = x;
            }
        }
        
        return min;
    }

    @Override
    public void zrus() {
        strom.zrus();
        this.koren = null;
        this.aktualniKlic = ETypKlice.NAZEV;
    }

    @Override
    public void prebuduj() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void nastavKlic(ETypKlice typKlice) {
        this.aktualniKlic = typKlice;
    }

    @Override
    public Iterator<Zamek> iterator(ETypProhlidky typProhlidky) {
        return strom.iterator(typProhlidky);
    }
    
}
