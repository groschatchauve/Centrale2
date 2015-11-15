package server;

import java.util.Scanner;

public class ThreadAdmin extends Thread{
	private final static String STOP  = "STOP";

	@Override
	public void run(){
        Scanner scanner = new Scanner(System.in);
    	while(!scanner.nextLine().toUpperCase().equals(STOP));
		Server.serverOn = false;
		System.out.println("Serveur stoppé...");
    	scanner.close();
	}
}
