package client;

import java.io.*;
import java.net.*;
public class Client {
	private final static String STOP  = "STOP";
	
	public static void main(String[] args) throws Exception{
		InetAddress serveur = InetAddress.getByName("localhost");
		if(!serveur.isReachable(5000))
			throw new Exception("Le serveur a mis trop de temps à répondre.");
		
		String request, answer, line;

		//Création du scanner pour l'utilisateur
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        //Création de la socket client, demande de connexion
        Socket socket = new Socket("localhost", 4040);

        //Création du flux en sortie avec param true pour auto flush
        PrintWriter outToServer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

        //Création du flux en entrée
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        System.out.println("Client connecté...");
        
        //Traitement de la saisie de l'utilisateur
        while(!(request = inFromUser.readLine()).toUpperCase().equals(STOP)){

	        //Emission des données au serveur
	        outToServer.println(request);
	
	        //Lecture des données arrivant du serveur
	        answer = "";
	        while((line = inFromServer.readLine()) != STOP) // TODO
	        	answer += line;
	        
	        System.err.println("FROM SERVER: " + answer);
        }
        outToServer.println(request);
        inFromUser.close();
        outToServer.close();
        socket.close();
	}

}
