import java.io.*;
import java.net.Socket;

public class ClientSmtp {

    private String seveurSmtp;
    private int port;
    private String hostname;

    public ClientSmtp(String seveurSmtp, int port, String hostname) {
        this.seveurSmtp = seveurSmtp;
        this.port = port;
        this.hostname = hostname;
    }

    public boolean sendMail(String from, String to, String subject, String body) throws IOException {
        String modifiedSentence = "";



        Socket clientSocket = new Socket(this.hostname,this.port);

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));


        out.write(this.seveurSmtp);
        modifiedSentence = getString(modifiedSentence, inFromServer, out);
        if (modifiedSentence.equals("erreur"))
        {
            return false;
        }

        out.write("MAIL FROM: "+from);
        System.out.println("MAIL FROM: "+from);
        modifiedSentence = getString(modifiedSentence, inFromServer, out);
        if (modifiedSentence.equals("erreur"))
        {
            return false;
        }

        out.write("RCPT TO: "+to);
        System.out.println("RCPT TO: "+to);
        modifiedSentence = getString(modifiedSentence, inFromServer, out);
        if (modifiedSentence.equals("erreur"))
        {
            return false;
        }
        out.write("DATA");
        System.out.println("DATA");
        modifiedSentence = getString(modifiedSentence, inFromServer, out);
        if (modifiedSentence.equals("erreur"))
        {
            return false;
        }
        out.write("FROM: "+from);
        System.out.println("FROM: "+from);
        modifiedSentence = getString(modifiedSentence, inFromServer, out);
        if (modifiedSentence.equals("erreur"))
        {
            return false;
        }
        out.write("TO: "+to);
        System.out.println("TO: "+to);
        modifiedSentence = getString(modifiedSentence, inFromServer, out);

        out.write("SUBJECT: "+subject);
        System.out.println("SUBJECT: "+subject);
        modifiedSentence = getString(modifiedSentence, inFromServer, out);

        out.write(body);
        System.out.println(body);
        modifiedSentence = getString(modifiedSentence, inFromServer, out);

        out.write(".");
        System.out.println(".");
        out.newLine();

        out.write("QUIT");
        System.out.println("QUIT");
        out.newLine();


        out.flush();


        System.out.println(modifiedSentence);
        modifiedSentence = inFromServer.readLine();


        while (modifiedSentence != null)
        {
            System.out.println(modifiedSentence);
            modifiedSentence = inFromServer.readLine();
        }
        return true;
    }

    private static String getString(String modifiedSentence, BufferedReader inFromServer, BufferedWriter out) throws IOException {
        out.newLine();

        out.flush();
        while(true)
        {
            String temp = inFromServer.readLine();
            modifiedSentence = modifiedSentence + "\n" + temp;
            if(!temp.substring(2,3).equals("-"))
            {
                //System.out.println("Debut message : " + temp.substring(0,1));

                if(temp.substring(0,1).equals("4") || temp.substring(0,1).equals("5"))
                {
                    System.out.println(modifiedSentence);
                    modifiedSentence = "erreur";

                }
                break;
            }

        }
        return modifiedSentence;
    }

}