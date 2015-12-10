package game_object;

public class Server {

    private final String ip;
    private final int port;

    private int playerCount;

    protected final int LAG_OUT_TIMER = 120;
    protected int lagOut = LAG_OUT_TIMER;

    public Server(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public Server(String ip, int port, int playerCount) {
        this.ip = ip;
        this.port = port;
        this.playerCount = playerCount;
    }

    public String getIP() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public void setPlayers(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getPlayerCounter() {
        return playerCount;
    }

    public void decrementCountdown() {
        lagOut--;
    }

    public void resetLagOutTimer() {
        lagOut = LAG_OUT_TIMER;
    }

    public int getLagOutTimer() {
        return lagOut;
    }
}
