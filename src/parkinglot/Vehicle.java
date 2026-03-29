package parkinglot;

/**
 * Represents a vehicle of a specific type with a registration number.
 */
public class Vehicle {
  private final VehicleType type;
  private final String regNo;

  /**
   * Create a new vehicle.
   *
   * @param type The type of the vehicle to create
   * @param regNo The registration number of the vehicle to create
   * @throws IllegalArgumentException If type or regNo are missing
   */
  public Vehicle(VehicleType type, String regNo) throws IllegalArgumentException {
    if (type == null) {
      throw new IllegalArgumentException("Vehicle type cannot be null");
    }
    if (regNo == null) {
      throw new IllegalArgumentException("Vehicle regNo cannot be null");
    }
    this.type = type;
    this.regNo = regNo;
  }

  public VehicleType getType() {
    return type;
  }
  public String getRegNo() {
    return regNo;
  }
}
