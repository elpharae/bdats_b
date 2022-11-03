package ads;

import java.util.Iterator;
import enums.ETypIt;

public interface IAbstrTable<K, V> {

    void zrus();
    boolean jePrazdny();

    V najdi(K key);
    void vloz(K key, V value);
    V odeber(K key);
    Iterator iterator(ETypIt typIterace);

}
