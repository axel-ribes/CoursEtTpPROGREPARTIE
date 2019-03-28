import java.io.IOException;

public class MainClientPOP3 {
    public static void main(String args[]) throws IOException {
    ClientPOP3 client = new ClientPOP3("139.124.187.23",110);
    client.getMail("ribes","ribes");
    }

}
