package pkg;

import ads.AbstrTable;

public class App {

    public static void main(String[] args) {
        AbstrTable<Integer, String> bst = new AbstrTable<>();

        bst.vloz(60, "60");
        bst.vloz(70, "70");
        bst.vloz(80, "80");
        bst.vloz(20, "20");
        bst.vloz(30, "30");
        bst.vloz(40, "40");

        System.out.println("pred");
        bst.odeber(30);
        System.out.println("po");

    }

}
