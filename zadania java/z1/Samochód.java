package z1;

public class Samochód {
    private Silnik silnik;
    public Samochód(Silnik silnik){
        this.silnik = silnik;
    }
    public void start(){
        silnik.uruchom();
    }
    public void stop(){
        silnik.zatrzymaj();
    }
}
