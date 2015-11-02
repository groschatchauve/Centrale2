package server;

public class Vehicule {
		private String idVehicule;
		private Boolean isDisponible;
		private Reservation reservation;
		
		public Vehicule(){}
		
		public Vehicule(String idVehicule){
			this.idVehicule = idVehicule;
			this.isDisponible = true;
			this.reservation = null;
		}
		
		
		//getters
		public Reservation getReservation() {return reservation;}
		public String getIdVehicule() {return idVehicule;}
		public Boolean getIsDisponible() {return isDisponible;}
		
		//setters
		public void setIdVehicule(String idVehicule) {this.idVehicule = idVehicule;}
		public void setReservation(Reservation reservation) {this.reservation = reservation;}
		public void setIsDisponible(Boolean isDisponible) {this.isDisponible = isDisponible;}


		@Override
		public String toString(){
			return idVehicule+" : "+isDisponible;
		}
}
