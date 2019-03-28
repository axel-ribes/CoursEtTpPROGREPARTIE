import java.io.IOException;

public class MainClientSmtp {
    public static void main(String args[]) throws IOException {

        ClientSmtp client = new ClientSmtp(,25, );
        for(int i=0;i<30;i++){
            client.sendMail(,,, );
        }

    }
}
