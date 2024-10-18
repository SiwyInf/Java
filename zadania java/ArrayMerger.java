import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class ArrayMerger {
    public static List<Integer> mergeArrays(int[] array1, int[] array2) {
        List<Integer> mergedList = new ArrayList<>();

        // Dodaj elementy z pierwszej tablicy
        for (int element : array1) {
            mergedList.add(element);
        }

        // Dodaj elementy z drugiej tablicy
        for (int element : array2) {
            mergedList.add(element);
        }

        return mergedList;
    }
}
