package parkinglot;

/**
 * Represents a parking slot that can accommodate a single vehicle of a particular vehicle type.
 */
class ParkingSlotImpl implements ParkingSlot {
  private final int slotId;
  private final VehicleType slotType;
  private Vehicle parkedVehicle;

  /**
   * Create an empty slot that accommodates a vehicle of a particular type
   * @param slotId The ID of the slot
   * @param slotType The vehicle type that it must accommodate
   * @throws IllegalArgumentException If any argument is missing
   */
  ParkingSlotImpl(int slotId, VehicleType slotType) throws IllegalArgumentException {
    if (slotId < 0) {
      throw new IllegalArgumentException("slotId cannot be negative");
    }
    if (slotType == null) {
      throw new IllegalArgumentException("slotType cannot be null");
    }
    this.slotId = slotId;
    this.slotType = slotType;
    this.parkedVehicle = null;
  }

  @Override
  public boolean park(Vehicle vehicle) throws IllegalArgumentException {
    if (vehicle == null) {
      throw new IllegalArgumentException("vehicle cannot be null");
    }
    if (parkedVehicle != null) {
      return false;
    }
    if (slotType != vehicle.getType()) {
      return false;
    }
    parkedVehicle = vehicle;
    return true;
  }

  @Override
  public boolean unpark(Vehicle vehicle) throws IllegalArgumentException {
    if (vehicle == null) {
      throw new IllegalArgumentException("vehicle cannot be null");
    }
    if (parkedVehicle == null) {
      return false;
    }
    if (slotType != vehicle.getType()) {
      return false;
    }
    parkedVehicle = null;
    return true;
  }

  @Override
  public VehicleType getSlotType() {
    return slotType;
  }

  @Override
  public int getSlotId() {
    return slotId;
  }
}
