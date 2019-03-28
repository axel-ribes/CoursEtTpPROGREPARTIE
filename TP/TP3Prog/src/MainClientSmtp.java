import java.io.IOException;

public class MainClientSmtp {
    public static void main(String args[]) throws IOException {

        ClientSmtp client = new ClientSmtp("EHLO Jupiter",25, "139.124.187.23");
        for(int i=0;i<30;i++){
            client.sendMail("ribes","gonand","Bonjour"+i, "hehehe");
        }

    }
}
