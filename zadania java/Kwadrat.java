import java.util.Scanner;

public class Kwadrat {
    int bok;
    public Kwadrat(int bok){

        this.bok = bok;
    }
    public int getBok(){
        return bok;
    }
    public int getPole(){
        return bok * bok;
    }
}
