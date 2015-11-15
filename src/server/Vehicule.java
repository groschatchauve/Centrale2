package server;

public class Vehicule implements Comparable<Vehicule>{
		private String idVehicule; //unique
		private EtatVehicule etat;
		private Reservation reservation;
		private int tempsPreparation;
		
		public Vehicule(){}
		
		public Vehicule(String idVehicule, int tempsPreparation){
			this.idVehicule = idVehicule;
			this.tempsPreparation=tempsPreparation;
			this.etat = EtatVehicule.DISPONIBLE;
			this.reservation = null;
		}
		
		//getters
		public int getTempsPreparation() {return tempsPreparation;}
		public Reservation getReservation() {return reservation;}
		public String getIdVehicule() {return idVehicule;}
		public EtatVehicule getEtatVehicule(){return etat;}
		
		//setters
		public void setIdVehicule(String idVehicule) {this.idVehicule = idVehicule;}
		public void setReservation(Reservation reservation) {this.reservation = reservation;}
		public void setEtatVehicule(EtatVehicule etat){this.etat=etat;}
		public void setTempsPreparation(int tempsPreparation) {this.tempsPreparation = tempsPreparation;}


		@Override
		public String toString(){
			return idVehicule+" : "+etat;
		}

		@Override
		public int compareTo(Vehicule vehicule) {
			return vehicule.getIdVehicule().compareTo(idVehicule);
		}
}
