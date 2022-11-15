package gui;

import java.util.ArrayList;
import java.util.Iterator;

import ads.AbstrTable;
import enums.ETypKlice;
import enums.ETypProhlidky;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pamatky.GPS;
import pamatky.Pamatky;
import pamatky.PamatkyException;
import pamatky.Zamek;

public class App extends Application {

    static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        App.primaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("AppFXML.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Pamatky");

        primaryStage.show();
    }

    public static void main(String[] args) {
        //launch(args);

        ArrayList<Zamek> nazvy = new ArrayList<Zamek>();
        Pamatky p = new Pamatky();
        p.nastavKlic(ETypKlice.NAZEV);
        nazvy.add(new Zamek("z30", new GPS(50, 14)));
        nazvy.add(new Zamek("z5", new GPS(50, 14)));
        nazvy.add(new Zamek("z9", new GPS(50, 14)));
        nazvy.add(new Zamek("z35", new GPS(50, 14)));
        nazvy.add(new Zamek("z4", new GPS(50, 14)));
        nazvy.add(new Zamek("z7", new GPS(50, 14)));
        nazvy.add(new Zamek("z91", new GPS(50, 14)));
        nazvy.add(new Zamek("z25", new GPS(50, 14)));
        nazvy.add(new Zamek("z32", new GPS(50, 14)));
        nazvy.add(new Zamek("z6", new GPS(50, 14)));
        nazvy.add(new Zamek("z10", new GPS(50, 14)));
        nazvy.add(new Zamek("z13", new GPS(50, 14)));
        nazvy.add(new Zamek("z15", new GPS(50, 14)));
        nazvy.add(new Zamek("z17", new GPS(50, 14)));
        nazvy.add(new Zamek("z21", new GPS(50, 14)));
        nazvy.add(new Zamek("z19", new GPS(50, 14)));


        nazvy.forEach(o -> {
            try {
                p.vlozZamek(o);
            } catch (PamatkyException e) {
                e.printStackTrace();
            }
        });

        try {
            p.prebuduj();


            System.out.println("SIROKA");
            Iterator<Zamek> it = p.iterator(ETypProhlidky.SIROKA);
            while (it.hasNext()) System.out.println(it.next());
        } catch (PamatkyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

         
  

    }

}
