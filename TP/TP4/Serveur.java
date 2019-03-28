import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Serveur {

	private 
	int port ;int nbClients; int nbThreads ; 
	
	public Serveur(int port, int nbClients, int nbThreads) {
		this.port =  port ;
		this.nbClients = nbClients;
		this.nbThreads = nbThreads;
	}
	
	public void lancer() {
		try {
			ServerSocket serveur = new ServerSocket(port);
			ExecutorService pool = Executors.newFixedThreadPool(nbThreads);
			
			for (int i = 1; i<=nbClients; i++) {
				
				Socket client = serveur.accept();
				TaskServeurFichier task = new TaskServeurFichier(client);
				pool.execute(task);
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Serveur(2000, 10000,100).lancer();

	}

}