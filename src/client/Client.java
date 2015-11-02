package client;

import java.io.*;
import java.net.*;
public class Client {
	
	public static void main(String[] args) throws Exception{
		InetAddress serveur = InetAddress.getByName("localhost");
		if(!serveur.isReachable(5000))
			throw new Exception("Le serveur a mis trop de temps � r�pondre.");
		
		String request, answer;

		//Cr�ation du scanner pour l'utilisateur
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        //Cr�ation de la socket client, demande de connexion
        Socket socket = new Socket("localhost", 8532);

        //Cr�ation du flux en sortie avec param true pour auto flush
        PrintWriter outToServer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

        //Cr�ation du flux en entr�e
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        request = inFromUser.readLine();

        //Emission des donn�es au serveur
        outToServer.println(request);

        //Lecture des donn�es arrivant du serveur
        answer = inFromServer.readLine();

        System.err.println("FROM SERVER: " + answer);

        socket.close();
	}

}
