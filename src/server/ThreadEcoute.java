package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ThreadEcoute extends Thread {
	private final static String STOP  = "STOP";
	//uniquement depuis le serveur
	private final static String FLOTTE  = "FLOTTE";
	private final static String FLOTTE_EN_ATTENTE  = "FLOTTE_EN_ATTENTE";
	private final static String FLOTTE_DISPONIBLE  = "FLOTTE_DISPONIBLE";
	private final static String FLOTTE_SORTIE  = "FLOTTE_SORTIE";

	private final static String RESA_BEGIN  = "RESA_BEGIN";
	private final static String RESA_TAKE = "RESA_TAKE";
	private final static String RESA_END  = "RESA_END";
	
	private Socket socket;
	private BufferedReader inFromClient;
	private PrintWriter outToClient;

	public ThreadEcoute(Socket socket) throws Exception {
		this.socket = socket;
        //Création du flux en entrée attaché à la socket
        inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //Création du flux en sortie attaché à la socket
        outToClient = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
	}

	@Override
	public void run(){
		String request;
		int order;
		Random random = new Random();
		Vehicule vehicule;
		Reservation reservation;
		try{
        //Lecture des données arrivant du client (en majuscules)
	        while(!(request = inFromClient.readLine().toUpperCase()).equals(STOP)){
		        //Emission des données au client
		        switch(request){
		        	case FLOTTE:
		        		outToClient.println("Tous les vehicules : " + Server.getFlotte());
		    		break;        		
		        	case FLOTTE_EN_ATTENTE :
		        		outToClient.println("Vehicules en attente : " + Server.getFlotte(EtatVehicule.EN_ATTENTE));
		    		break;
		        	case FLOTTE_DISPONIBLE :
		        		outToClient.println("Vehicules disponibles : " + Server.getFlotte(EtatVehicule.DISPONIBLE));
		    		break;
		    		case FLOTTE_SORTIE :
		        		outToClient.println("Vehicules sortis : " + Server.getFlotte(EtatVehicule.SORTI));
		    		break;
		        	case RESA_END:
		        		outToClient.println("Veuillez entrez l'id de la réservation dont vous souhaitez retourner le véhicule.");
		        		request = inFromClient.readLine();
		        		order = Server.findReservationById(request);
		        		if(order < 0){
		        			outToClient.println("Cette réservation n'existe pas.");
		        		}
		        		else{
		        			reservation = Server.resas.get(order);
		        			outToClient.println("Pour confirmer votre identité, veuillez entrer l'id avec lequel vous avez réservé le véhicule.");
			        		request = inFromClient.readLine();
			        		if(!reservation.getIdClient().equals(request)){
			        			outToClient.println("L'id ne correspond pas.");
			        		}
			        		else{
			        			outToClient.println("Merci d'avoir retourner le véhicule "+reservation.getVehicule().getIdVehicule()+".");
			        			Server.resas.remove(reservation);
			        			reservation.getVehicule().setReservation(null);
			        			reservation.getVehicule().setEtatVehicule(EtatVehicule.DISPONIBLE);
			        			reservation = null;        			
			        		}
		        		}
		        	break;
		        	case RESA_BEGIN :
		        		outToClient.println("Veuillez entre l'id de la voiture que vous souhaitez réserver.");
		        		request = inFromClient.readLine();
		        		System.out.println(request);
		        		order = Server.findVehiculeById(request);
		        		if(order < 0){
		        			outToClient.println("Ce vehicule n'existe pas.");
		        		}
		        		else{ 
		        			vehicule = Server.flotte.get(order);
		        			if(vehicule.getEtatVehicule() == EtatVehicule.EN_ATTENTE)
		        				outToClient.println("Ce vehicule est déjà en cours de préparation.");
		        			else if(vehicule.getEtatVehicule() == EtatVehicule.SORTI)
	        					outToClient.println("Ce vehicule est déjà sorti.");
			        		else if(vehicule.getEtatVehicule() == EtatVehicule.DISPONIBLE){
		        				outToClient.println("Ce vehicule est disponible. Veuillez entrer votre id.");
			        			request = inFromClient.readLine().toUpperCase();
			        			reservation = new Reservation(request,vehicule);
			        			Server.resas.add(reservation);
			        			vehicule.setReservation(reservation);
			        			outToClient.println("WAIT");
			        			Server.staff.get(random.nextInt(Server.staff.size()-1)).setReservation(reservation);
			        			outToClient.println("Le véhicule " + vehicule.getIdVehicule() + " est prêt à être retiré."
			        					+ "Votre réservation porte le numéro suivant : "
			        					+vehicule.getReservation().getIdReservation()
			        					);
			        		}
		        		}
		        	break;
		        	case RESA_TAKE :
		        		outToClient.println("Veuillez entrez l'id de la réservation dont vous souhaitez retirer le véhicule.");
		        		request = inFromClient.readLine();
		        		order = Server.findReservationById(request);
		        		if(order < 0){
		        			outToClient.println("Cette réservation n'existe pas.");
		        		}
		        		else{
		        			reservation = Server.resas.get(order);
		        			outToClient.println("Pour confirmer votre identité, veuillez entrer l'id avec lequel vous avez réservé le véhicule.");
			        		request = inFromClient.readLine();
			        		if(!reservation.getIdClient().equals(request)){
			        			outToClient.println("L'id ne correspond pas.");
			        		}
			        		else{
			        			outToClient.println("Véhicule "+reservation.getVehicule().getIdVehicule()+" retiré.");
			        			reservation.getVehicule().setEtatVehicule(EtatVehicule.SORTI);
			        		}
		        		}	        		
	        		break;
		        	default :
		                outToClient.println("COMMANDE NON RECONNUE");
		        	break;
		        }
	        }
	        inFromClient.close();
	        outToClient.close();
			socket.close();
		} catch (Exception e) {e.printStackTrace();}
	}
}

