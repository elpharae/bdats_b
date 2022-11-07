package ads;

import java.util.Iterator;
import enums.ETypProhlidky;

public class AbstrTable<K extends Comparable<K>, V> implements IAbstrTable<K, V> {

    private class Node {

        private K klic;
        private V hodnota;
        private Node leva;
        private Node prava;

        public Node(K klic, V hodnota) {
            this.klic = klic;
            this.hodnota = hodnota;
            this.leva = null;
            this.prava = null;
        }

    }

    private Node koren;

    @Override
    public void zrus() {
        this.koren = null;
    }

    @Override
    public boolean jePrazdny() {
        return this.koren == null;
    }

    @Override
    public V najdi(K klic) {
        return najdiRekurze(this.koren, klic);
    }

    private V najdiRekurze(Node koren, K klic) {
        // pokud neexistuje korenovy prvek
        // neni co hledat
        if (koren == null) return null;

        // pokud je poskytnuty klic mensi nez klic korenoveho prvku
        // hledani rekurzivne pokracuje v levem prvku korenu
        if (klic.compareTo(koren.klic) < 0) return najdiRekurze(koren.leva, klic);

        // pokud je poskytnuty klic vetsi nez klic korenoveho prvku
        // hledani rekurzivne pokracuje v pravem prvku korenu
        if (klic.compareTo(koren.klic) > 0) return najdiRekurze(koren.prava, klic);

        // pokud je poskytnuty klic stejny jako klic korenoveho prvku
        // tak je vracena hodnota prvku
        return koren.hodnota;
    }

    @Override
    public void vloz(K klic, V hodnota) {
        this.koren = vlozRekurze(this.koren, klic, hodnota);
    }

    private Node vlozRekurze(Node koren, K klic, V hodnota) {
        // pokud neexistuje korenovy prvek
        // je vytvoren s poskytnutymi daty
        if (koren == null) koren = new Node(klic, hodnota);

        // pokud je poskytnuty klic mensi nez klic korenoveho prvku
        // hledani vhodneho mista na vlozeni rekurzivne pokracuje po leve strane korenoveho prvku
        if (klic.compareTo(koren.klic) < 0) koren.leva = vlozRekurze(koren.leva, klic, hodnota);

        // pokud je poskytnuty klic vetsi nez klic korenoveho prvku
        // hledani vhodneho mista na vlozeni rekurzivne pokracuje po prave strane korenoveho prvku
        if (klic.compareTo(koren.klic) > 0) koren.prava = vlozRekurze(koren.prava, klic, hodnota);

        return koren;
    }

    @Override
    public V odeber(K klic) {
        return odeberRekurze(this.koren, klic).hodnota;
    }

    private Node odeberRekurze(Node koren, K klic) {
        if (koren == null) return koren;

        if (klic.compareTo(koren.klic) < 0) koren.leva = odeberRekurze(koren.leva, klic);
        else if (klic.compareTo(koren.klic) > 0) koren.prava = odeberRekurze(koren.prava, klic);
        else {
            if (koren.leva == null) return koren.prava;
            else if (koren.prava == null) return koren.leva;

            K min = koren.klic;

            while (koren.leva != null) {
                min = koren.leva.klic;
                koren = koren.leva;
            }

            koren.klic = min;
            koren.prava = odeberRekurze(koren.prava, koren.klic);
        }

        return koren;
    }

    @Override
    public Iterator iterator(ETypProhlidky typProhlidky) {
        return null;
    }
    
}
