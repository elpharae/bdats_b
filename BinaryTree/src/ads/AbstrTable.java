package ads;

import java.util.Iterator;
import enums.ETypIt;

public class AbstrTable<K extends Comparable<K>, V> implements IAbstrTable<K, V> {

    @Override
    public void zrus() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean jePrazdny() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public V najdi(K key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void vloz(K key, V value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public V odeber(K key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Iterator iterator(ETypIt typIterace) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
