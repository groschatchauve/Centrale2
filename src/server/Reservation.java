package server;

public class Reservation {
	private int idReservation;
	private String idClient;
	private Vehicule vehicule;
	private EtatReservation etat;

	private static Integer compteur = 0;
	
	public Reservation(String idClient, Vehicule vehicule){
		this.idClient=idClient;
		this.vehicule=vehicule;
		this.idReservation = compteur = new Integer(++compteur);
		this.etat = EtatReservation.EN_ATTENTE;
	}

	//getters
	public int getIdReservation() {return idReservation;}
	public static Integer getCompteur() {return compteur;}
	public Vehicule getVehicule() {return vehicule;}
	public String getIdClient() {return idClient;}
	public EtatReservation getEtatReservation(){return this.etat;}

	//setters
	public void setIdClient(String idClient) {this.idClient = idClient;}
	public void setIdReservation(int idReservation) {this.idReservation = idReservation;}
	public void setVehicule(Vehicule vehicule) {this.vehicule = vehicule;}
	public static void setCompteur(Integer compteur) {Reservation.compteur = compteur;}
	public void setEtatReservation(EtatReservation etat){this.etat=etat;}



	
	
}
