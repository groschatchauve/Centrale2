package client;

import static org.junit.Assert.*;
import org.junit.Test;
import server.*;

public class TestClient {

	@Test
	public void testGetVehicule() {
		//GIVEN
		Vehicule vehicule = new Vehicule("Audi A10", 100);
		//WHEN
		Reservation reservation = new Reservation("Toto", vehicule);
		//THEN
		assertSame(vehicule, reservation.getVehicule());
	}
	
	@Test
	public void testSetDisponibilite(){
		//GIVEN
		Vehicule vehicule = new Vehicule("Audi A10", 100);
		Reservation reservation = new Reservation("Toto", vehicule);
		//WHEN
		Employe employe = new Employe();
		employe.setReservation(reservation);
		//THEN
		long efficacite = employe.getEfficacite();
		long tempsPreparation = employe.getReservation().getVehicule().getTempsPreparation();
		assertSame(reservation, employe.getReservation());
		assertSame(vehicule, employe.getReservation().getVehicule());
		assertEquals(efficacite*tempsPreparation, efficacite * vehicule.getTempsPreparation(), 0.2);
	}
	
	
	

}
