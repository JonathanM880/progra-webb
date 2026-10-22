package example.servicios;

public class Main {
    static void main() {
        Services s = new Services();

        s.delete(1);

        s.findAll().forEach(System.out::println);


    }
}
