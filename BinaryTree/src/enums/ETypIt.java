package enums;

public enum ETypIt {
    
    SIROKA("Široká"),
    HLUBOKA("Hluboká");

    private String nazev;

    ETypIt(String nazev) {
        this.nazev = nazev;
    }

    @Override
    public String toString() {
        return this.nazev;
    }

}
