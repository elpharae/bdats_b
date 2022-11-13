package pamatky;

public class Zamek {
    
    static int count = 1;

    private int id;
    private String nazev;
    private GPS lokace;

    public Zamek(String nazev, GPS lokace) {
        if (nazev == null || lokace == null) throw new NullPointerException("Spatne poskytnuty nazev nebo lokace");
        if (nazev.isEmpty()) throw new IllegalArgumentException("Nazev je prazdny");

        this.id = count++;
        this.nazev = nazev.trim();
        this.lokace = lokace;
    }

    public void setId(int id) {
        this.id = id;
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
    public String toString() {
        return this.id + ": " + this.lokace.getSirka() + " " +  this.lokace.getDelka() + " " + this.nazev;
    }

}
