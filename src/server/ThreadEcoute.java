package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ThreadEcoute extends Thread {
	private final static String STOP  = "STOP";
	private final static String FLOTTE  = "FLOTTE";
	private final static String STAFF  = "STAFF";
	private final static String RESA  = "RESERVATION";

	//private final static int taille = 1024;
	//private final static byte buffer[] = new byte[taille];
	//private final static int port = 8532;
	
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
		try{
        //Lecture des données arrivant du client
	        while(!(request = inFromClient.readLine()).toUpperCase().equals(STOP)){
		        //Emission des données au client
		        switch(request){
		        	case FLOTTE :
		        		outToClient.println(Server.flotte);
		        		outToClient.println(STOP);
		    		break;
		        	case STAFF :
		        		outToClient.println(Server.staff);
		        		outToClient.println(STOP);
		        	break;
		        	case RESA :
		        		outToClient.println(Server.reservations);
		        		outToClient.println(STOP);
		        	break;
		        	default :
		                outToClient.println("COMMANDE NON RECONNUE");
		        		outToClient.println(STOP);
		        	break;
		        }
	        }
	        inFromClient.close();
	        outToClient.close();
			socket.close();
		} catch (Exception e) {e.printStackTrace();}
	}
	/*
	@Override
	public void run() {
		outToClient.println("Start run");
		DatagramSocket socket;
		String listVehicules = "Liste des Véhicules disponibles : ";
		try {
			socket = new DatagramSocket(port);
			DatagramPacket data;
			DatagramPacket dataReceived = new DatagramPacket(buffer, buffer.length);
			//while(true){
				socket.receive(dataReceived);
				for(Vehicule v : Server.flotte){
					if(v.getIsDisponible())
						listVehicules += v.getIdVehicule() + ", ";
				}
				data = new DatagramPacket(listVehicules.getBytes(), listVehicules.length(), dataReceived.getAddress(), dataReceived.getPort());
				socket.send(data);
				socket.receive(dataReceived);
				String commande = new String(dataReceived.getData());
			//}
			socket.close();
		}catch (Exception e) {e.printStackTrace();}
	}
	*/
}

