package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

public class ThreadEcoute extends Thread {

	private final static int taille = 1024;
	private final static byte buffer[] = new byte[taille];
	private final static int port = 8532;

	private static List<Vehicule> flotte;

	public ThreadEcoute(List<Vehicule> flotte) {
		ThreadEcoute.flotte = flotte;
	}

	@Override
	public void run() {
		DatagramSocket socket;
		String listVehicules = "Liste des Véhicules disponibles : ";
		try {
			socket = new DatagramSocket(port);
			DatagramPacket data;
			DatagramPacket dataReceived = new DatagramPacket(buffer, buffer.length);
			while(true){
				socket.receive(dataReceived);
				for(Vehicule v : flotte){
					if(v.getIsDisponible())
						listVehicules += v.getIdVehicule() + ", ";
				}
				data = new DatagramPacket(listVehicules.getBytes(), listVehicules.length(), dataReceived.getAddress(), dataReceived.getPort());
				socket.send(data);
				socket.receive(dataReceived);
				String commande = new String(dataReceived.getData());
			}
		}catch (Exception e) {e.printStackTrace();}
	}
}

