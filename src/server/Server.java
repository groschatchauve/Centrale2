package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;

public class Server {

	private final static int port = 8532;
	final static int taille = 1024;
	final static byte buffer[] = new byte[taille];
	private static LinkedList<Vehicule> flotte;
	
	public static void main(String argv[]) throws Exception {
		
		//InetAddress serveur = InetAddress.getByName("localhost");  //à mettre dans le client pour qu'il ait l'adresse du serveur
		DatagramSocket socket = new DatagramSocket(port);
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		String newData = "";
		//monThread thread = new monThread();
		//thread.start();
		while(!newData.equals("STOP"))
		{
			DatagramPacket dataReceived = new DatagramPacket(buffer, buffer.length);
			socket.receive(dataReceived);
			newData = inFromUser.readLine();
			DatagramPacket dataSent = new DatagramPacket(newData.getBytes(),newData.length(),dataReceived.getAddress(),dataReceived.getPort());
			socket.send(dataSent);
		}
		socket.close();
	}

}
