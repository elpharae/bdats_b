package pamatky;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ads.AbstrTable;
import ads.AbstrTableException;
import enums.ETypKlice;
import enums.ETypProhlidky;

public class Pamatky implements IPamatky {

    private AbstrTable strom;
    private ETypKlice aktualniKlic;

    private static final float PREVOD_NA_STUPNE = 1/60f;

    public Pamatky() {
        this.strom = new AbstrTable();
        this.aktualniKlic = ETypKlice.GPS;
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

                GPS lokace = new GPS(sirka, delka);
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
            case GPS -> this.strom.vloz(zamek.getLokace(), zamek);
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
                return (Zamek) this.strom.najdi(stringToGPS(klic).toString());
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
                return (Zamek) this.strom.odeber(stringToGPS(klic));
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
        ArrayList<Zamek> vsechnyZamky = new ArrayList<Zamek>();
        while (it.hasNext()) vsechnyZamky.add(it.next());

        GPS lokace = stringToGPS(klic);

        Zamek nejblizsi = vsechnyZamky.stream()
            .sorted((z1, z2) -> {
                if (z1.getLokace().vzdalenostOd(lokace) < z2.getLokace().vzdalenostOd(lokace)) return -1;
                else if (z1.getLokace().vzdalenostOd(lokace) > z2.getLokace().vzdalenostOd(lokace)) return 1;
                else return 0;
            })
            .findFirst()
            .get();

        return nejblizsi;
    }

    private GPS stringToGPS(String gps) {
        String[] data = gps.split(" ");
        if (data.length != 2) throw new IllegalArgumentException("Spatne zadana lokace, pouzijte format \"12.123456 12.123456\"");

        GPS lokace = new GPS(Float.parseFloat(data[0]), Float.parseFloat(data[1]));
        return lokace;
    }

    @Override
    public void zrus() {
        strom.zrus();

        this.aktualniKlic = ETypKlice.GPS;
    }

    @Override
    public void prebuduj() {
        Iterator<Zamek> it = this.strom.iterator(ETypProhlidky.SIROKA);
        ArrayList<Zamek> vsechnyZamky = new ArrayList<Zamek>();
        while (it.hasNext()) {
            vsechnyZamky.add(it.next());
        }

        ArrayList<Zamek> serazene = new ArrayList<Zamek>(vsechnyZamky.stream()
        .sorted((z1, z2) -> {
            if (this.aktualniKlic == ETypKlice.GPS) return z1.getLokace().compareTo(z2.getLokace());
            else return z1.getNazev().compareTo(z2.getNazev());
        }).toList());

        AbstrTable novyStrom = new AbstrTable();
        Zamek koren = serazene.get((int) (serazene.size() / 2));

        if (aktualniKlic == ETypKlice.GPS) novyStrom.vloz(koren.getLokace(), koren);
        else novyStrom.vloz(koren.getNazev(), koren);

        ArrayList<Zamek> serazeneMensi = new ArrayList<Zamek>(serazene.subList(0, (int) (serazene.size() / 2)));
        ArrayList<Zamek> serazeneVetsi = new ArrayList<Zamek>(serazene.subList((int) (serazene.size() / 2) + 1, serazene.size()));

        while (!serazeneMensi.isEmpty() || !serazeneVetsi.isEmpty()) {
            Zamek vkladanyMensi = null;
            Zamek vkladanyVetsi = null;

            if (!serazeneMensi.isEmpty()) {
                vkladanyMensi = serazeneMensi.remove((int) (serazeneMensi.size() / 2));
            }

            if (!serazeneVetsi.isEmpty()) {
                vkladanyVetsi = serazeneVetsi.remove((int) (serazeneVetsi.size() / 2));
            }

            if (vkladanyMensi != null) {
                if (aktualniKlic == ETypKlice.GPS) novyStrom.vloz(vkladanyMensi.getLokace(), vkladanyMensi);
                else novyStrom.vloz(vkladanyMensi.getNazev(), vkladanyMensi);
            }

            if (vkladanyVetsi != null) {
                if (aktualniKlic == ETypKlice.GPS) novyStrom.vloz(vkladanyVetsi.getLokace(), vkladanyVetsi);
                else novyStrom.vloz(vkladanyVetsi.getNazev(), vkladanyVetsi);
            }
        }
        
        zrus();

        this.strom = novyStrom;
    }

    @Override
    public void nastavKlic(ETypKlice typKlice) {
        this.aktualniKlic = typKlice;
    }

    @Override
    public Iterator<Zamek> iterator(ETypProhlidky typProhlidky) {
        return (Iterator<Zamek>) strom.iterator(typProhlidky);
    }
    
}
