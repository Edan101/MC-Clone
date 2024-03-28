import java.util.Random;

public class StartClient {
    public static void main(String[] args) {
        MatthiasGame game = new MatthiasGame(101);
        Thread main = new Thread(game);
        main.start();
    }
}
