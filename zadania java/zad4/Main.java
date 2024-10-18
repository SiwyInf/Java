package zad4;



public class Main {
    public static void main(String[] args) {
        Dog dog1 = new Dog(3);
        Dog dog2 = new Dog(5);

        Animal maxDog = Dog.findMax(dog1, dog2);

        System.out.println(maxDog.getAge());
    }
}