package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	public static Boolean serverOn = true;

	// TODO - Mettre listes non bloquantes
	public static List<Vehicule> flotte = new LinkedList<>();
	public static List<Employe> staff = new LinkedList<>();
	public static List<Reservation> resas = new LinkedList<>();
	
	private static ServerSocket serverSocket;

	private final static int NB_MAX_EMPLOYES = 2;
	
	public static void main(String argv[]) throws Exception{
		System.out.println("Serveur démarré...");
		
		//initialisation flotte
		flotte.add(new Vehicule("AUDI_A10", 100));
		flotte.add(new Vehicule("CITROEN_DS", 50));
		flotte.add(new Vehicule("FORD_MUSTANG_GT", 150));
		
		//initialisation equipe
		for(int i=0; i<NB_MAX_EMPLOYES; ++i)
			staff.add(new Employe());
		
		
		 //Création de la socket d'accueil au port 4040
        serverSocket = new ServerSocket(4040);
        //new ThreadAdmin().start();
        Socket socket;
    		//Le scanner permet à l'administrateur d'intéragir avec le serveur
        	while(serverOn){
	            //Attente d'une demande de connexion sur la socket d'accueil
	            socket = serverSocket.accept();
	            
	            //Déclenchement d'un thread spécifique au client
	    		new ThreadEcoute(socket).start();	    		
    		}//boucle et attente de la connexion d'un nouveau client        
    		
    		//Si l'administrateur écrit STOP le serveur s'arrête proprement
        	//La serverSocket est libérée
			serverSocket.close();
	}	
	
	//Retourne une chaine représentant la liste de tous les véhicules
	public static String getFlotte(){
		String string = "";
		for(Vehicule vehicule : flotte)
			string += vehicule.getIdVehicule() + ", ";
		return string;
	}
	
	
	
	//Retourne une chaine représentant la liste des véhicules dans l'état indiqué
	public static String getFlotte(EtatVehicule etat){
		String string = "";
		for(Vehicule vehicule : flotte)
			if(vehicule.getEtatVehicule() == etat)
				string += vehicule.getIdVehicule() + ", ";
		return string;
	}

	//Retourne l'emplacement d'un vehicule dans la flotte
	public static int findVehiculeById(String id){
		for(Vehicule vehicule : flotte){
			if(vehicule.getIdVehicule().equals(id))
				return flotte.indexOf(vehicule);
		}
		return -1;
	}
	
	//Retourne l'emplacement d'une réservation dans la liste
	public static int findReservationById(String idTmp){
		if(idTmp.isEmpty())
			return -1;
		int id = Integer.valueOf(idTmp);
		for(Reservation reservation : resas){
			if(reservation.getIdReservation()==id)
				return resas.indexOf(reservation);
		}
		return -1;
	}	
}