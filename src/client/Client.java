package client;

import java.io.*;
import java.net.*;
public class Client {
	private final static String STOP  = "STOP";
	
	public static void main(String[] args) throws Exception{
		InetAddress serveur = InetAddress.getByName("localhost");
		if(!serveur.isReachable(5000))
			throw new Exception("Le serveur a mis trop de temps � r�pondre.");
		
		String request, answer;

		//Cr�ation du scanner pour l'utilisateur
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        //Cr�ation de la socket client, demande de connexion
        Socket socket = new Socket("localhost", 4040);

        //Cr�ation du flux en sortie avec param true pour auto flush
        PrintWriter outToServer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

        //Cr�ation du flux en entr�e
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        System.out.println("Client connect�...");
        
        outToServer.println("FLOTTE_DISPONIBLE");
        System.err.println("FROM SERVER: " + inFromServer.readLine());

        
        //Traitement de la saisie de l'utilisateur
        while(!(request = inFromUser.readLine().toUpperCase()).equals(STOP)){
        	
	        //Emission des donn�es au serveur
	        outToServer.println(request);
	
	        //Lecture des donn�es arrivant du serveur
	        answer = inFromServer.readLine();	        
	        System.err.println("FROM SERVER: " + answer);
	        if(answer.equals("WAIT")){
		        answer = inFromServer.readLine();	 
		        System.err.println("FROM SERVER: " + answer);
	        }
        }
        outToServer.println(request);
        inFromUser.close();
        outToServer.close();
        socket.close();
	}

}
