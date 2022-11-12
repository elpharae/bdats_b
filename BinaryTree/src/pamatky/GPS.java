package pamatky;

public class GPS implements Comparable<GPS> {
    
    private float sirka;
    private float delka;

    public GPS(float sirka, float delka) {
        this.sirka = sirka;
        this.delka = delka;
    }

    public float getSirka() {
        return sirka;
    }
    
    public float getDelka() {
        return delka;
    }

    public float getVzdalenost() {
        return vzdalenostOd(Pamatky.koren.getLokace());
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
    public int compareTo(GPS gps) {
        GPS koren = Pamatky.koren.getLokace();

        float vzdalenost1 = this.vzdalenostOd(koren);
        float vzdalenost2 = gps.vzdalenostOd(koren);

        if (vzdalenost1 < vzdalenost2) return -1;
        else if (vzdalenost1 > vzdalenost2) return 1;
        else return 0;
    }


    
}
