package parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ParkingFloorImpl implements ParkingFloor {
  private final int floorNo;
  private final Map<VehicleType, List<ParkingSlot>> freeSlotsByType;
  private final Map<Integer, ParkingSlot> slotsById;
  private final Map<String, Vehicle> parkedVehiclesByRegNo;

  /**
   * Create an empty parking floor.
   *
   * @param floorNo The floor number of the floor to be created
   * @throws IllegalArgumentException If arguments are invalid
   */
  ParkingFloorImpl(int floorNo, Map<VehicleType, Integer> slotCountsByType)
      throws IllegalArgumentException {
    if (floorNo < 0) {
      throw new IllegalArgumentException("Invalid floor number");
    }
    if (slotCountsByType == null) {
      throw new IllegalArgumentException("Invalid slot count map");
    }
    this.floorNo = floorNo;
    this.freeSlotsByType = new HashMap<>();
    this.slotsById = new HashMap<>();
    this.parkedVehiclesByRegNo = new HashMap<>();

    createSlotsByType(slotCountsByType);
  }

  @Override
  public ParkingTicket park(Vehicle vehicle) throws IllegalArgumentException {
    if (vehicle == null) {
      throw new IllegalArgumentException("Vehicle cannot be null");
    }
    if (!freeSlotsByType.containsKey(vehicle.getType())) {
      return null;
    }
    if (parkedVehiclesByRegNo.containsKey(vehicle.getRegNo())) {
      throw new IllegalArgumentException("Vehicle is already parked in this floor");
    }
    List<ParkingSlot> freeSlots = freeSlotsByType.get(vehicle.getType());
    if (freeSlots.isEmpty()) {
      return null;
    }
    ParkingSlot slot = freeSlots.get(freeSlots.size() - 1);

    boolean result = slot.park(vehicle);
    if (!result) {
      return null;
    }
    ParkingTicket ticket = new ParkingTicket(vehicle.getRegNo(), slot.getSlotId(), floorNo, 0);
    parkedVehiclesByRegNo.put(vehicle.getRegNo(), vehicle);
    freeSlots.remove(freeSlots.size() - 1);
    return ticket;
  }

  @Override
  public boolean unpark(ParkingTicket parkingTicket) throws IllegalArgumentException {
    if (parkingTicket == null) {
      throw new IllegalArgumentException("Ticket cannot be null");
    }
    if (parkingTicket.getFloorNo() != floorNo) {
      throw new IllegalArgumentException("Parking ticket invalid. Invalid floor number");
    }
    if (!slotsById.containsKey(parkingTicket.getSlotId())) {
      return false;
    }
    if (!parkedVehiclesByRegNo.containsKey(parkingTicket.getRegNo())) {
      return false;
    }
    ParkingSlot slot = slotsById.get(parkingTicket.getSlotId());
    Vehicle vehicle = parkedVehiclesByRegNo.get(parkingTicket.getRegNo());

    boolean result = slot.unpark(vehicle);
    if (!result) {
      return false;
    }
    freeSlotsByType.get(vehicle.getType()).add(slot);
    parkedVehiclesByRegNo.remove(vehicle.getRegNo());
    return true;
  }

  @Override
  public int getFreeSlotCount(VehicleType slotType) throws IllegalArgumentException {
    return freeSlotsByType.get(slotType).size();
  }

  @Override
  public Vehicle getParkedVehicleByRegNo(String regNo) throws IllegalArgumentException {
    if (regNo == null) {
      throw new IllegalArgumentException("RegNo cannot be null");
    }
    if (!parkedVehiclesByRegNo.containsKey(regNo)) {
      throw new IllegalArgumentException("Vehicle is not parked in this floor");
    }
    return parkedVehiclesByRegNo.get(regNo);
  }

  /**
   * Helper method to create slots for multiple vehicle types.
   *
   * @param slotCountsByType A map of vehicle types to number of slots to be created for that type
   */
  private void createSlotsByType(Map<VehicleType, Integer> slotCountsByType) {

    int slotId = 0;
    for (Map.Entry<VehicleType, Integer> entry : slotCountsByType.entrySet()) {
      VehicleType vehicleType = entry.getKey();
      int numSlots = entry.getValue();
      freeSlotsByType.put(vehicleType, new ArrayList<>());

      for (int i = 0; i < numSlots; i++) {
        ParkingSlot slot = new ParkingSlotImpl(slotId, vehicleType);
        slotsById.put(slotId, slot);
        freeSlotsByType.get(vehicleType).add(slot);
        slotId++;
      }
    }
  }
}
