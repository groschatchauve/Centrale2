package server;

import java.util.Random;

public class Employe {
	private Reservation reservation;
	private long efficacite;
	//efficacit� de chaque employ� fix�e al�atoirement
	private static Random random = new Random();

	public Employe(){
		this.reservation = null;
		this.efficacite = random.nextInt(100) ;
	}

	public void setVehiculeDisponibilite() throws InterruptedException{
		//wait temps pr�paration v�hicule
		Thread.sleep(this.efficacite * this.reservation.getVehicule().getTempsPreparation());
		//set disponibilite vehicule
		this.reservation.getVehicule().setIsDisponible(true);
		//on desaffecte la reservation � l'employ�
		this.reservation = null;
	}


	//getters
	public long getEfficacite() {return efficacite;}
	public static Random getRandom() {return random;}
	//permet de tester si l'employ� est disponible
	public Reservation getReservation() {return reservation;}

	//setters
	public void setEfficacite(long efficacite) {this.efficacite = efficacite;}
	public void setReservation(Reservation reservation) {this.reservation = reservation;}
	public static void setRandom(Random random) {Employe.random = random;}


}
