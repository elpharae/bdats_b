package pamatky;

import java.io.FileNotFoundException;
import java.util.Iterator;
import enums.ETypKlice;

public interface IPamatky<Zamek> {
    
    int importDatZTXT(String soubor) throws FileNotFoundException, PamatkyException;
    void vlozZamek(Zamek zamek);
    
    Zamek najdiZamek(String klic);
    Zamek odeberZamek(String klic);
    Zamek najdiNejbliz(String klic);

    void zrus();
    void prebuduj();
    void nastavKlic(ETypKlice typKlice);
    
    Iterator<Zamek> iterator();

}
