package pamatky;

import java.io.FileNotFoundException;
import java.util.Iterator;

import ads.AbstrTableException;
import enums.ETypKlice;
import enums.ETypProhlidky;

public interface IPamatky {
    
    int importDatZTXT(String soubor) throws FileNotFoundException, PamatkyException;
    void vlozZamek(Zamek zamek);
    
    Zamek najdiZamek(String klic) throws PamatkyException, AbstrTableException;
    Zamek odeberZamek(String klic) throws PamatkyException, AbstrTableException;
    Zamek najdiNejbliz(String klic) throws PamatkyException;

    void zrus();
    void prebuduj();
    void nastavKlic(ETypKlice typKlice);
    
    Iterator<Zamek> iterator(ETypProhlidky typProhlidky);

}
