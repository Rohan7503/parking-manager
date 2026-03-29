import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import parkinglot.ParkingLot;
import parkinglot.ParkingLotImpl;
import parkinglot.ParkingTicket;
import parkinglot.Vehicle;
import parkinglot.VehicleType;

/**
 * This class tests the parking lot functionality.
 */
public class ParkingLotTest {
  private ParkingLot p1;
  private Vehicle bike1;
  private Vehicle bike2;
  private Vehicle car1;
  private Vehicle car2;
  private Vehicle truck1;

  @Before
  public void setUp() {
    Map<VehicleType, Integer> vehicleTypeCounts1 = new HashMap<>();
    vehicleTypeCounts1.put(VehicleType.BIKE, 2);
    vehicleTypeCounts1.put(VehicleType.CAR, 1);
    p1 = new ParkingLotImpl(0, 3, vehicleTypeCounts1);

    bike1 = new Vehicle(VehicleType.BIKE, "bike1");
    bike2 = new Vehicle(VehicleType.BIKE, "bike2");
    car1 = new Vehicle(VehicleType.CAR, "car1");
    car2 = new Vehicle(VehicleType.CAR, "car2");
    truck1 = new Vehicle(VehicleType.TRUCK, "truck1");
  }

  @Test
  public void testParkingLotValid1() {
    ParkingTicket ticketBike1 = p1.park(bike1);
    ParkingTicket ticketCar1 = p1.park(car1);
    ParkingTicket ticketBike2 = p1.park(bike2);

    p1.unpark(ticketBike1);
    p1.unpark(ticketCar1);
    p1.unpark(ticketBike2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParkingLotInvalid1() {
    p1.park(truck1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParkingLotInvalid2() {
    p1.park(bike1);
    p1.park(car1);
    p1.park(car2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParkingLotInvalid3() {
    p1.park(bike1);
    p1.park(bike1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParkingLotInvalid4() {
    ParkingTicket ticketBike1 = p1.park(bike1);
    p1.park(car1);
    p1.park(bike2);

    p1.unpark(ticketBike1);
    p1.unpark(ticketBike1);
  }

}
