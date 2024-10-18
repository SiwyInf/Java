package zad4;

public class Dog extends Animal {
    String nazwa;
    public Dog(int wiek) {
        super(wiek);
        this.nazwa = nazwa;
    }
    public String getName(){
        return nazwa;
    }

    public static <T extends Animal> T findMax(T element1, T element2) {
    if(element1.getAge() > element2.getAge()){
        return element1;
    }
    else{
        return element2;
    }
    }
}



