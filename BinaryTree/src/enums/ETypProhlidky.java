package enums;

public enum ETypProhlidky {
    
    SIROKA("Široká"),
    HLUBOKA("Hluboká");

    private String nazev;

    ETypProhlidky(String nazev) {
        this.nazev = nazev;
    }

    @Override
    public String toString() {
        return this.nazev;
    }

}
