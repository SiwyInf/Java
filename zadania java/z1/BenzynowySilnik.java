package z1;

public class BenzynowySilnik implements Silnik{
    @Override
    public void uruchom() {
        System.out.println("Brum");
    }

    @Override
    public void zatrzymaj() {
        System.out.println("Prrr");
    }
}
