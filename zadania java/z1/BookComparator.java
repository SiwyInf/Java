package z1;

import java.util.Comparator;

public interface BookComparator extends Comparator<Ksiazka> {
    @Override
    public default int compare(Ksiazka book1, Ksiazka book2) {
        return Integer.compare(book1.dataWydania, book2.dataWydania);
    }
}

