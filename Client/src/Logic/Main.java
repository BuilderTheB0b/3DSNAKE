package Logic;

public class Main {
    public static void main(String[] args) {
        new Thread(new Controller("localhost", 6002)).start();

    }
}