package server;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class SocketFactory implements Runnable {
	private ServerSocket serverSocket;
	private List<ThreadEcoute> threads;
	
	public SocketFactory(ServerSocket serverSocket){
		assert(!serverSocket.isClosed());
		this.serverSocket=serverSocket;
		this.threads = new LinkedList<>();
	}

	@Override
	public void run() {
		Boolean serverIsDown = false;
        Socket socket;
        	while(!serverIsDown){
	            //Attente d'une demande de connexion sur la socket d'accueil
	            try {
					socket = serverSocket.accept();
		            //Déclenchement d'un thread spécifique au client
		    		threads.add(new ThreadEcoute(socket));	
		    		threads.get(threads.size()-1).start();
				} catch (Exception e) {
					serverIsDown = true;
				}    		
    		}//boucle et attente de la connexion d'un nouveau client        
	}
	
	//Fermer les sockets non fermées pour libérer les ports
	public void closeAllSockets() throws IOException{
		for(ThreadEcoute thread : threads){
			if(thread.isAlive())
				if(!thread.getSocket().isClosed()){
					thread.getSocket().close();
				thread.interrupt();
			}
		}
	}
}
