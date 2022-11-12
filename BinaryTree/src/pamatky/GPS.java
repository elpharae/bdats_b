package pamatky;

public class GPS implements Comparable<GPS> {
    
    private float sirka;
    private float delka;

    private GPS koren;

    public GPS(float sirka, float delka, GPS koren) {
        System.out.println(sirka);
        System.out.println(delka);


        if (sirka < 48f || sirka >= 52f || delka < 12f || delka >= 19f) {
            throw new IllegalArgumentException("Pouze Ceske uzemi");
        }

        this.sirka = sirka;
        this.delka = delka;
        this.koren = koren;
    }

    public float getSirka() {
        return sirka;
    }
    
    public float getDelka() {
        return delka;
    }

    public GPS getKoren() {
        return koren;
    }

    public void setKoren(GPS koren) {
        this.koren = koren;
    }

    private float vzdalenostOd(GPS gps) {
        if (gps == null) return 0f;

        final double POLOMER_ZEME = 6.37100;

        double f1 = this.sirka * Math.PI / 180;
        double f2 = gps.getSirka() *  Math.PI / 180;
        double df = (gps.getSirka() - this.sirka) * Math.PI / 180;
        double dd = (gps.getDelka() - this.delka) * Math.PI / 180;

        double a = Math.sin(df/2) * Math.sin(df/2) + Math.cos(f1) * Math.cos(f2) * Math.sin(dd/2) * Math.sin(dd/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return (float) (POLOMER_ZEME * c * 1000);
    }

    @Override
    public String toString() {
        return Float.toString(this.vzdalenostOd(this.koren));
    }

    @Override
    public int compareTo(GPS gps) {
        float vzdalenost1 = this.vzdalenostOd(this.koren);
        float vzdalenost2 = gps.vzdalenostOd(this.koren);

        if (vzdalenost1 < vzdalenost2) return -1;
        else if (vzdalenost1 > vzdalenost2) return 1;
        else return 0;
    }
    
}
