package parkinglot;

/**
 * Represents a single floor that contains several parking spots.
 */
interface ParkingFloor {
  /**
   * Park a vehicle in a slot within this floor.
   *
   * @param vehicle The vehicle to be parked
   * @return A parking ticket upon successful parking; null otherwise
   * @throws IllegalArgumentException If the vehicle is null or is already parked in this floor
   */
  public ParkingTicket park(Vehicle vehicle) throws IllegalArgumentException;

  /**
   * Remove a vehicle from its slot within this floor.
   *
   * @param parkingTicket The parking ticket received upon parking the vehicle in this floor
   * @return True upon successful removal, false otherwise
   * @throws IllegalArgumentException If the parking ticket is null
   */
  public boolean unpark(ParkingTicket parkingTicket) throws IllegalArgumentException;

  /**
   * Obtain the number of free slots of a specific type available in this floor.
   *
   * @param slotType The type of vehicle the slots accommodate
   * @return The number of free slots
   * @throws IllegalArgumentException If the slot type is null
   */
  public int getFreeSlotCount(VehicleType slotType) throws IllegalArgumentException;

  /**
   * Retrieve the vehicle parked in this floor based on its registration number.
   *
   * @param regNo The registration number of the vehicle
   * @return The vehicle
   * @throws IllegalArgumentException If the vehicle is not parked in this floor, or regNo is null
   */
  public Vehicle getParkedVehicleByRegNo(String regNo) throws IllegalArgumentException;
}
