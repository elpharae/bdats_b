package pamatky;

public class Zamek implements Comparable<Zamek> {
    
    private static int count = 1;

    private int id;
    private String nazev;
    private GPS lokace;

    public Zamek(String nazev, GPS lokace) {
        if (nazev == null || lokace == null) throw new NullPointerException();
        if (nazev.isEmpty()) throw new IllegalArgumentException();

        this.id = count++;
        this.nazev = nazev;
        this.lokace = lokace;
    }

    public int getId() {
        return id;
    }

    public String getNazev() {
        return nazev;
    }

    public GPS getLokace() {
        return lokace;
    }

    @Override
    public int compareTo(Zamek zamek) {
        return 0;
    }

    @Override
    public String toString() {
        return this.id + " " + this.nazev + " " + this.lokace.getSirka() + " " +  this.lokace.getDelka();
    }

}
