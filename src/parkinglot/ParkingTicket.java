package parkinglot;

/**
 * Represents a parking ticket received upon parking a vehicle in a parking lot.
 */
public class ParkingTicket {
  private final String regNo;
  private final int slotId;
  private final int floorNo;
  private final int parkingLotId;

  /**
   * Create a parking ticket.
   *
   * @param regNo The registration number of the vehicle that is parked in the parking lot
   * @param slotId The ID of the slot within the floor in which the vehicle is parked
   * @param floorNo The floorNo (0 indexed) within the parking lot the vehicle is parked in
   * @param parkingLotId The ID of the parking lot  the vehicle is parked in
   * @throws IllegalArgumentException If any of the arguments are missing/invalid
   */
  public ParkingTicket(String regNo, int slotId, int floorNo, int parkingLotId) throws IllegalArgumentException {
    if (regNo == null) {
      throw new IllegalArgumentException("Vehicle regNo cannot be null");
    }
    if (slotId < 0) {
      throw new IllegalArgumentException("slotId cannot be negative");
    }
    if (floorNo < 0) {
      throw new IllegalArgumentException("FloorNo cannot be negative");
    }
    if (parkingLotId < 0) {
      throw new IllegalArgumentException("ParkingLotId cannot be negative");
    }
    this.regNo = regNo;
    this.slotId = slotId;
    this.floorNo = floorNo;
    this.parkingLotId = parkingLotId;
  }

  public String getRegNo() {
    return regNo;
  }
  public int getSlotId() {
    return slotId;
  }
  public int getFloorNo() {
    return floorNo;
  }
  public int getParkingLotId() {
    return parkingLotId;
  }
}
