package server;

public class Vehicule {
		private String idVehicule;
		private Boolean isDisponible;
		private Reservation reservation;
		private int tempsPreparation;
		
		public Vehicule(){}
		
		public Vehicule(String idVehicule, int tempsPreparation){
			this.idVehicule = idVehicule;
			this.tempsPreparation=tempsPreparation;
			this.isDisponible = true;
			this.reservation = null;
		}
		
		


		//getters
		public int getTempsPreparation() {return tempsPreparation;}
		public Reservation getReservation() {return reservation;}
		public String getIdVehicule() {return idVehicule;}
		public Boolean getIsDisponible() {return isDisponible;}
		
		//setters
		public void setIdVehicule(String idVehicule) {this.idVehicule = idVehicule;}
		public void setReservation(Reservation reservation) {this.reservation = reservation;}
		public void setIsDisponible(Boolean isDisponible) {this.isDisponible = isDisponible;}
		public void setTempsPreparation(int tempsPreparation) {this.tempsPreparation = tempsPreparation;}


		@Override
		public String toString(){
			return idVehicule+" : "+isDisponible;
		}
}
