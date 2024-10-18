package zad2;

import java.util.Comparator;

public class Item implements Comparable<Item> {
    String name;
    double weight;
    double price;
    public Item(String name, double weight,double price){
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    @Override
    public int compareTo(Item o) {
        return Double.compare(o.weight,  this.weight);
    }
}
