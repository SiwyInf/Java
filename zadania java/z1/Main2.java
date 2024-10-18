package z1;

import java.util.ArrayList;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        Ksiazka ksiazka1 = new Ksiazka("Siema", 23,2000);
        Ksiazka ksiazka2 = new Ksiazka("Siema1", 23,2001);
        Ksiazka ksiazka3 = new Ksiazka("Siema2", 23,2002);
        Ksiazka ksiazka4 = new Ksiazka("Siema3", 23,2003);
        Ksiazka ksiazka5 = new Ksiazka("Siema4", 23,2004);
        List<Ksiazka> listaKsiazek = new ArrayList<>();
        listaKsiazek.add(ksiazka1);
        listaKsiazek.add(ksiazka2);
        listaKsiazek.add(ksiazka3);
        listaKsiazek.add(ksiazka4);
        listaKsiazek.add(ksiazka5);
        for(int i =0;i<listaKsiazek.size();i++){
            System.out.println(listaKsiazek.get(i).dataWydania);
        }
    }
    }


