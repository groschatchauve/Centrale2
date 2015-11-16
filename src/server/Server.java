package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	private final static String RESAS = "RESAS";
	private final static String RESA_VALIDE = "RESA_VALIDE";
	private final static String RESA_INVALIDE = "RESA_INVALIDE";
	private final static String FLOTTE = "FLOTTE";
	private final static String FLOTTE_EN_ATTENTE = "FLOTTE_EN_ATTENTE";
	private final static String FLOTTE_DISPONIBLE = "FLOTTE_DISPONIBLE";
	private final static String FLOTTE_SORTIE = "FLOTTE_SORTIE";
	private final static String STAFF = "STAFF";
	private final static String STOP = "STOP";
	
	public static Boolean serverOn = true;

	// TODO - Mettre listes non bloquantes
	public static List<Vehicule> flotte = new LinkedList<>();
	public static List<Employe> staff = new LinkedList<>();
	public static List<Reservation> resas = new LinkedList<>();
	
	//private static ServerSocket serverSocket;

	private final static int NB_MAX_EMPLOYES = 2;
	
	public static void main(String argv[]) throws IOException{
		System.out.println("Serveur démarré...");
		
		//Empêcher le proxy de bloquer la connexion d'une socket à un port
		System.setProperty("java.net.useSystemProxies", "true");
		
		//initialisation flotte
		flotte.add(new Vehicule("AUDI_A10", 100));
		flotte.add(new Vehicule("CITROEN_DS", 50));
		flotte.add(new Vehicule("FORD_MUSTANG_GT", 150));
		
		//initialisation equipe
		for(int i=0; i<NB_MAX_EMPLOYES; ++i)
			staff.add(new Employe());
		
		
		 //Création de la socket d'accueil au port 4040
        ServerSocket serverSocket = new ServerSocket(4040);
        SocketFactory socketFactory = new SocketFactory(serverSocket);
        Thread thread = new Thread(socketFactory);
        thread.start();
        
        //Communication côté serveur pour l'admin
        Scanner scanner = new Scanner(System.in);
        String adminRequest;
        int order;
        Reservation reservation;
        System.out.println("Admin can write here.");
        while(!(adminRequest = scanner.nextLine().toUpperCase()).equals(STOP)){
        	switch(adminRequest){
        		case FLOTTE :
        			System.err.println("FROM SERVER : "+ getFlotte());
    			break;       		
	        	case FLOTTE_EN_ATTENTE :
	        		System.err.println("FROM SERVER : Vehicules en attente : " + getFlotte(EtatVehicule.EN_ATTENTE));
	    		break;
	        	case FLOTTE_DISPONIBLE :
	        		System.err.println("FROM SERVER : Vehicules disponibles : " + getFlotte(EtatVehicule.DISPONIBLE));
	    		break;
	    		case FLOTTE_SORTIE :
	        		System.err.println("FROM SERVER : Vehicules sortis : " + getFlotte(EtatVehicule.SORTI));
	    		break;
        		case STAFF :
        			System.err.println("FROM SERVER : "+ getStaff());
    			break;
        		case RESAS :
        			System.err.println("FROM SERVER : "+ getResas());
				break;
        		case RESA_VALIDE :
        			System.err.println("FROM SERVER : "+ "Veuillez indiquer l'id de la réservation à valider");
        			adminRequest = scanner.nextLine();
        			order = findReservationById(adminRequest);
        			if(order < 0){
            			System.err.println("FROM SERVER : Aucune réservation ne porte ce numéro.");
        			}
        			else{
        				reservation = resas.get(order);
        				reservation.setEtatReservation(EtatReservation.VALIDE);
        				System.err.println("FROM SERVER : La réservation "+reservation+" a été validée.");
        			}
				break;
        		case RESA_INVALIDE :
        			System.err.println("FROM SERVER : "+ "Veuillez indiquer l'id de la réservation à invalider");
        			adminRequest = scanner.nextLine();
        			order = findReservationById(adminRequest);
        			if(order < 0){
            			System.err.println("FROM SERVER : Aucune réservation ne porte ce numéro.");
        			}
        			else{
        				reservation = resas.get(order);
        				reservation.setEtatReservation(EtatReservation.INVALIDE);
        				System.err.println("FROM SERVER : La réservation "+reservation+" a été invalidée.");
        			}
    			break;
				default :
					System.err.println("COMMANDE NON RECONNUE");
				break;
        	}
        }
        scanner.close();
        socketFactory.closeAllSockets();
        thread.interrupt();
		serverSocket.close();
        System.out.println("Serveur arrêté...");
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
	
	//Retourne une chaine représentant la liste des réservations
	private static String getResas(){
		String string = "";
		for(Reservation reservation : resas){
			string += reservation.toString() + ", ";
		}
		return string;
	}
	
	//Retourne une chaine représentant le staff
	private static String getStaff(){
		String string = "";
		for(Employe employe : staff){
			string += employe.toString() + ", ";
		}
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