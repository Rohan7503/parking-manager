package parkinglot;

/**
 * Represents a single parking lot that contains several parking floors.
 */
public interface ParkingLot {
  /**
   * Park a vehicle in a floor within this parking lot.
   *
   * @param vehicle The vehicle to be parked
   * @return A parking ticket upon successful parking
   * @throws IllegalArgumentException If vehicle is null or cannot be parked
   */
  public ParkingTicket park(Vehicle vehicle) throws IllegalArgumentException;

  /**
   * Remove a vehicle from this parking lot.
   *
   * @param parkingTicket The parking ticket received upon successful parking of the vehicle
   * @throws IllegalArgumentException If the parking ticket is null, or vehicle cannot be removed
   */
  public void unpark(ParkingTicket parkingTicket) throws IllegalArgumentException;
}
