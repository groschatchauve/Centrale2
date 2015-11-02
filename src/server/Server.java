package server;

import java.io.*;
import java.util.*;

public class Server {

	private static List<Vehicule> flotte = new LinkedList<>();
	private static List<Employe> staff = new LinkedList<>();
	private static List<Reservation> reservations = new LinkedList<>();
	private final static int NB_MAX_EMPLOYE = 2;
	
	public static void main(String argv[]) throws Exception {
		
		//initialisation flotte
		flotte.add(new Vehicule("Audi A10", 100));
		flotte.add(new Vehicule("Citroen DS", 50));
		flotte.add(new Vehicule("Ford Mustang GT", 150));
		
		//initialisation equipe
		for(int i=0; i<NB_MAX_EMPLOYE; ++i)
			staff.add(new Employe());
		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		String newData = "";
		ThreadEcoute threadEcoute = new ThreadEcoute(flotte);
		threadEcoute.start();

		newData = inFromUser.readLine();
	}

}
