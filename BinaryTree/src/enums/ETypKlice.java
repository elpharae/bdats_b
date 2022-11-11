package enums;

public enum ETypKlice {
    
    NAZEV("Název"),
    GPS("GPS");

    private String nazev;

    ETypKlice(String nazev) {
        this.nazev = nazev;
    }

    @Override
    public String toString() {
        return this.nazev;
    }

}
