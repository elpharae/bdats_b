package pamatky;

import java.io.FileNotFoundException;
import java.util.Iterator;

import ads.AbstrTableException;
import enums.ETypKlice;
import enums.ETypProhlidky;

public interface IPamatky<Zamek> {
    
    int importDatZTXT(String soubor) throws FileNotFoundException, PamatkyException;
    void vlozZamek(Zamek zamek);
    
    Zamek najdiZamek(Object klic) throws PamatkyException, AbstrTableException;
    Zamek odeberZamek(Object klic) throws PamatkyException, AbstrTableException;
    Zamek najdiNejbliz(Object klic);

    void zrus();
    void prebuduj();
    void nastavKlic(ETypKlice typKlice);
    
    Iterator<Zamek> iterator(ETypProhlidky typProhlidky);

}
