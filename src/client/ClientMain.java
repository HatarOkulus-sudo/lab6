package client;

public class ClientMain {
    private static final String HOST = "localhost";
    private static final int PORT = 1984;

    public static void main(String[] args) {
        ClientNetworkManager net = new ClientNetworkManager(HOST, PORT);
        ClConsole console = new ClConsole(net);
        console.run();

        net.close();
    }
}
