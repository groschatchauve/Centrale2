package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;

public class ThreadEcoute extends Thread {

	private final static int taille = 1024;
	private final static byte buffer[] = new byte[taille];
	private final static int port = 8532;

	private static LinkedList<Vehicule> flotte;

	public ThreadEcoute(LinkedList<Vehicule> flotte) {
		this.flotte = flotte;
	}

	@Override
	public void run() {
		DatagramSocket socket;
		try {
			socket = new DatagramSocket(port);
			DatagramPacket dataReceived = new DatagramPacket(buffer, buffer.length);
			while(true){
				socket.receive(dataReceived);
			}
		}catch (Exception e) {e.printStackTrace();}
	}
}

