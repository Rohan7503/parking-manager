package parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ParkingFloorImpl implements ParkingFloor {
  private final int floorNo;
  private final Map<VehicleType, List<ParkingSlot>> freeSlotsByType;
  private final Map<Integer, ParkingSlot> slotsById;

  /**
   * Create an empty parking floor.
   *
   * @param floorNo The floor number of the floor to be created
   * @throws IllegalArgumentException If arguments are invalid
   */
  ParkingFloorImpl(int floorNo, Map<VehicleType, Integer> slotCountsByType)
      throws IllegalArgumentException {
    if (floorNo <= 0) {
      throw new IllegalArgumentException("Invalid floor number");
    }
    if (slotCountsByType == null) {
      throw new IllegalArgumentException("Invalid slot count map");
    }
    this.floorNo = floorNo;
    this.freeSlotsByType = new HashMap<>();
    this.slotsById = new HashMap<>();

    createSlotsByType(slotCountsByType);
  }

  @Override
  public ParkingTicket park(Vehicle vehicle) throws IllegalArgumentException {
    return null;
  }

  @Override
  public boolean unpark(ParkingTicket parkingTicket) throws IllegalArgumentException {
    return false;
  }

  @Override
  public int getFreeSlotCount(VehicleType slotType) throws IllegalArgumentException {
    return 0;
  }

  /**
   * Helper method to create slots for multiple vehicle types.
   *
   * @param slotCountsByType A map of vehicle types to number of slots to be created for that type
   */
  private void createSlotsByType(Map<VehicleType, Integer> slotCountsByType) {

    for (Map.Entry<VehicleType, Integer> entry : slotCountsByType.entrySet()) {
      VehicleType vehicleType = entry.getKey();
      int numSlots = entry.getValue();
      freeSlotsByType.put(vehicleType, new ArrayList<>());

      for (int slotId = 0; slotId < numSlots; slotId++) {
        ParkingSlot slot = new ParkingSlotImpl(slotId, vehicleType);
        slotsById.put(slotId, slot);
        freeSlotsByType.get(vehicleType).add(slot);
      }
    }
  }
}
