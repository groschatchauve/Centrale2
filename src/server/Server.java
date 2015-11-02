package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {

	private final static int port = 8532;
	
	public static void main(String argv[]) throws Exception {
		InetAddress serveur = InetAddress.getByName("localhost");
		DatagramSocket socket = new DatagramSocket();
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		String newData = "";
		//monThread thread = new monThread();
		//thread.start();
		while(!newData.equals("STOP"))
		{
			newData = inFromUser.readLine();
			DatagramPacket dataSent = new DatagramPacket(newData.getBytes(),newData.length(), serveur, port);
			socket.send(dataSent);
		}
		socket.close();
	}

}
