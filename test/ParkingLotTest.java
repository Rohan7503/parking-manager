import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    Map<VehicleType, Integer> vehicleTypeCounts = new HashMap<>();
    vehicleTypeCounts.put(VehicleType.BIKE, 2);
    vehicleTypeCounts.put(VehicleType.CAR, 1);
    p1 = new ParkingLotImpl(0, 2, vehicleTypeCounts);

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

  @Test
  public void testParkingLotValidLarge() {
    Map<VehicleType, Integer> vehicleTypeCounts = new HashMap<>();
    vehicleTypeCounts.put(VehicleType.BIKE, 2000);
    vehicleTypeCounts.put(VehicleType.CAR, 3000);
    vehicleTypeCounts.put(VehicleType.TRUCK, 4000);
    ParkingLot p2 = new ParkingLotImpl(1, 10, vehicleTypeCounts);

    Map<VehicleType, List<ParkingTicket>> parkingTickets = new HashMap<>();
    parkingTickets.put(VehicleType.BIKE, new ArrayList<>());
    parkingTickets.put(VehicleType.CAR, new ArrayList<>());
    parkingTickets.put(VehicleType.TRUCK, new ArrayList<>());

    // Park vehicles
    ParkingTicket ticket;
    for (int i = 0; i < 2000; i++) {
      ticket = p2.park(new Vehicle(VehicleType.BIKE, "BIKE" + i));
      parkingTickets.get(VehicleType.BIKE).add(ticket);
      assertEquals("BIKE" + i, ticket.getRegNo());

      ticket = p2.park(new Vehicle(VehicleType.CAR, "CAR" + i));
      parkingTickets.get(VehicleType.CAR).add(ticket);
      assertEquals("CAR" + i, ticket.getRegNo());

      ticket = p2.park(new Vehicle(VehicleType.TRUCK, "TRUCK" + i));
      parkingTickets.get(VehicleType.TRUCK).add(ticket);
      assertEquals("TRUCK" + i, ticket.getRegNo());
    }
    for (int i = 2000; i < 3000; i++) {
      ticket = p2.park(new Vehicle(VehicleType.CAR, "CAR" + i));
      parkingTickets.get(VehicleType.CAR).add(ticket);
      assertEquals("CAR" + i, ticket.getRegNo());

      ticket = p2.park(new Vehicle(VehicleType.TRUCK, "TRUCK" + i));
      parkingTickets.get(VehicleType.TRUCK).add(ticket);
      assertEquals("TRUCK" + i, ticket.getRegNo());
    }
    for (int i = 3000; i < 4000; i++) {
      ticket = p2.park(new Vehicle(VehicleType.TRUCK, "TRUCK" + i));
      parkingTickets.get(VehicleType.TRUCK).add(ticket);
      assertEquals("TRUCK" + i, ticket.getRegNo());
    }

    // Unpark all
    for (Map.Entry<VehicleType, List<ParkingTicket>> entry : parkingTickets.entrySet()) {
      for (ParkingTicket parkingTicket : entry.getValue()) {
        p2.unpark(parkingTicket);
      }
    }
  }

  @Test
  public void testParkingTicket() {
    ParkingTicket ticketBike1 = p1.park(bike1);
    assertEquals("bike1", ticketBike1.getRegNo());
    System.out.println("Bike1 parking lot ID: " + ticketBike1.getParkingLotId());
    System.out.println("Bike1 floor no: " + ticketBike1.getFloorNo());
    System.out.println("Bike1 slot ID: " + ticketBike1.getSlotId());
    System.out.println();

    ParkingTicket ticketBike2 = p1.park(bike2);
    assertEquals("bike2", ticketBike2.getRegNo());
    System.out.println("Bike2 parking lot ID: " + ticketBike2.getParkingLotId());
    System.out.println("Bike2 floor no: " + ticketBike2.getFloorNo());
    System.out.println("Bike2 slot ID: " + ticketBike2.getSlotId());
    System.out.println();

    ParkingTicket ticketCar1 = p1.park(car1);
    assertEquals("car1", ticketCar1.getRegNo());
    System.out.println("Car1 parking lot ID: " + ticketCar1.getParkingLotId());
    System.out.println("Car1 floor no: " + ticketCar1.getFloorNo());
    System.out.println("Car1 slot ID: " + ticketCar1.getSlotId());
    System.out.println();
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
    ParkingTicket ticketBike1 = p1.park(bike1);
    p1.park(car1);
    p1.park(bike2);

    p1.unpark(ticketBike1);
    p1.unpark(ticketBike1);
  }

}
