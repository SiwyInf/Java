package z1;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int c = a/b;
            System.out.println("Wynik: " + c);
        } catch (ArithmeticException e) {
            System.out.println("Dzielenie przez zero !");
            throw new ArithmeticException("Wariacie co ty");
        }
        catch (InputMismatchException e) {
            System.out.println("To nie liczba !!!");
            throw new InputMismatchException("E chlopie");
        }
    }
}
