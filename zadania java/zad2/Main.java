package zad2;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Item Tablica[] = new Item[5];
        Tablica[0] = new Item("pierdek", 32, 24);
        Tablica[1] = new Item("pierdek1", 33, 24);
        Tablica[2] = new Item("pierdek2", 34, 24);
        Tablica[3] = new Item("pierdek3", 35, 24);
        Tablica[4] = new Item("pierdek4", 36, 24);
        Arrays.sort(Tablica);
        for(Item item : Tablica){
            System.out.println(item.weight);
        }
    }
}
