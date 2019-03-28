import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.logging.SocketHandler;

public class ClientPOP3 {
    private String hostname;
    private int port;
    private Socket socket;
    private  Socket clientSocket = new Socket();

    public ClientPOP3(String hostname, int port) throws IOException {
        this.hostname = hostname;
        this.port = port;
    }

    public boolean getMail(String user,String pass) throws IOException {
        this.clientSocket.connect(new InetSocketAddress(this.hostname,this.port));
        BufferedReader in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

        String readbuff = in.readLine();

        if (!readbuff.substring(0,3).equals("+OK")){
            System.out.println("erreur de connexion");
            return false;
        }
        out.write("USER "+ user);
        out.newLine();
        out.flush();
        readbuff = in.readLine();
        if (!readbuff.substring(0,3).equals("+OK")){
            System.out.println("erreur de user");
            return false;
        }
        out.write("PASS "+ pass);
        out.newLine();
        out.flush();
        readbuff = in.readLine();
        if (!readbuff.substring(0,3).equals("+OK")){
            System.out.println("erreur de mot de passe");
            return false;
        }
        out.write("LIST" );
        out.newLine();
        out.flush();
        readbuff = in.readLine();
        if (!readbuff.substring(0,3).equals("+OK")){
            System.out.println("erreur de liste mails");
            return false;
        }
        while(true){
            readbuff = in.readLine();
            System.out.println(readbuff);
            if (readbuff.charAt(0)== '.') {
                break;
            }
        }
        System.out.println("selection email");
        Scanner scan = new Scanner(System.in);
        readbuff =scan.next();
        scan.close();
        out.write("RETR " + readbuff);
        out.newLine();
        out.flush();
        readbuff = in.readLine();
        if (!readbuff.substring(0,3).equals("+OK")){
            System.out.println("erreur de selection email");
            return false;
        }
        while(true){
            readbuff = in.readLine();

            if(readbuff.equals(".")) {
                break;
            }
            System.out.println(readbuff);
        }

        out.write("QUIT");

        out.newLine();
        out.flush();
        readbuff= in.readLine();
        System.out.println(readbuff);
        if (!readbuff.substring(0,3).equals("+OK")){
            System.out.println("erreur de deconnexion");
            return false;
        }
        return true;
    }
}
