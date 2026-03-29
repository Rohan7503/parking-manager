package parkinglot;

/**
 * Represents a single parking slot that accommodates one vehicle of a specific vehicle type.
 */
interface ParkingSlot {
  /**
   * Park a vehicle in this parking slot.
   *
   * @param vehicle The vehicle to be parked
   * @return True if the vehicle can be parked, false otherwise
   * @throws IllegalArgumentException If the vehicle is null
   */
  public boolean park(Vehicle vehicle) throws IllegalArgumentException;

  /**
   * Remove a vehicle parked in this parking slot.
   *
   * @param vehicle The vehicle to be removed
   * @return True if the vehicle is removed successfully, false otherwise
   * @throws IllegalArgumentException If the vehicle is null
   */
  public boolean unpark(Vehicle vehicle) throws IllegalArgumentException;

  /**
   * Return the type of vehicle that the slot can accommodate
   * @return The vehicle type that the slot can accommodate
   */
  public VehicleType getSlotType();
}
