package client;

import static org.junit.Assert.*;
import org.junit.Test;
import server.*;

public class TestClient {

	@Test
	public void testGetVehicule() {
		Vehicule vehicule = new Vehicule("Audi A10", 100);
		Reservation reservation = new Reservation("Toto", vehicule);
		assertSame(vehicule, reservation.getVehicule());
	}
	
	

}
