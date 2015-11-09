package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

	// TODO - Mettre listes non bloquantes
	public static List<Vehicule> flotte = new LinkedList<>();
	public static List<Employe> staff = new LinkedList<>();
	public static List<Reservation> reservations = new LinkedList<>();
	private static ServerSocket serverSocket;

	private final static int NB_MAX_EMPLOYES = 2;
	
	public static void main(String argv[]) throws Exception{
		System.out.println("Serveur démarré...");
		
		//initialisation flotte
		flotte.add(new Vehicule("Audi A10", 100));
		flotte.add(new Vehicule("Citroen DS", 50));
		flotte.add(new Vehicule("Ford Mustang GT", 150));
		
		//initialisation equipe
		for(int i=0; i<NB_MAX_EMPLOYES; ++i)
			staff.add(new Employe());
		
		
		 //Création de la socket d'accueil au port 4040
        serverSocket = new ServerSocket(4040);
        
        Socket socket;
        do{
            //Attente d'une demande de connexion sur la socket d'accueil
            socket = serverSocket.accept();
            
            //Déclenchement d'un thread spécifique au client
    		new ThreadEcoute(socket).start();

        }while(true); //boucle et attente de la connexion d'un nouveau client
        //if(!serverSocket.isClosed()) serverSocket.close();
	}
}
