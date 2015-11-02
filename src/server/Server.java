package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Server {

	private static LinkedList<Vehicule> flotte;
	
	public static void main(String argv[]) throws Exception {
		
		//InetAddress serveur = InetAddress.getByName("localhost");  //à mettre dans le client pour qu'il ait l'adresse du serveur
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		String newData = "";
		ThreadEcoute threadEcoute = new ThreadEcoute(flotte);
		threadEcoute.start();

		newData = inFromUser.readLine();

	}

}
