package parkinglot;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Represents a single parking lot that tracks the vehicles parked in it.
 */
public class ParkingLotImpl implements ParkingLot {
  private final int parkingLotId;
  private final Map<VehicleType, Queue<ParkingFloor>> floorsByType;
  private final Map<Integer, ParkingFloor> floorsById;

  /**
   * Create an empty parking lot.
   *
   * @param parkingLotId The ID of the parking lot to be created
   * @param numFloors The number of floors in the parking lot
   * @param numSlotsByType The total number of slots per type in the parking lot
   * @throws IllegalArgumentException If the arguments are invalid/missing
   */
  public ParkingLotImpl(int parkingLotId, int numFloors, Map<VehicleType, Integer> numSlotsByType)
      throws IllegalArgumentException {
    if (parkingLotId < 0) {
      throw new IllegalArgumentException("Parking lot id cannot be negative");
    }
    if (numFloors < 1) {
      throw new IllegalArgumentException("Number of floors cannot be negative");
    }
    if (numSlotsByType == null) {
      throw new IllegalArgumentException("Number of slots mapping cannot be null");
    }
    this.parkingLotId = parkingLotId;
    this.floorsByType = new HashMap<>();
    this.floorsById = new HashMap<>();

    createFloorsByType(numFloors, numSlotsByType);
  }

  @Override
  public ParkingTicket park(Vehicle vehicle) throws IllegalArgumentException {
    if (vehicle == null) {
      throw new IllegalArgumentException("Vehicle cannot be null");
    }
    if (!floorsByType.containsKey(vehicle.getType())) {
      throw new IllegalArgumentException("This parking lot does not support the given vehicle type");
    }
    ParkingFloor floor = floorsByType.get(vehicle.getType()).peek();
    if (floor == null) {
      throw new IllegalArgumentException("This parking lot does not support the given vehicle type");
    }

    ParkingTicket parkingTicket = floor.park(vehicle); // throws IllegalArgumentException
    if (parkingTicket == null) {
      throw new IllegalArgumentException("Cannot park vehicle");
    }

    floorsByType.get(vehicle.getType()).remove();
    floorsByType.get(vehicle.getType()).add(floor);

    return new ParkingTicket(
        parkingTicket.getRegNo(),
        parkingTicket.getSlotId(),
        parkingTicket.getFloorNo(),
        parkingLotId
    );
  }

  @Override
  public void unpark(ParkingTicket parkingTicket) throws IllegalArgumentException {
    if (parkingTicket == null) {
      throw new IllegalArgumentException("ParkingTicket cannot be null");
    }
    if (parkingTicket.getParkingLotId() != parkingLotId) {
      throw new IllegalArgumentException("Invalid parking ticket. Belongs to a different parking lot");
    }
    if (!floorsById.containsKey(parkingTicket.getFloorNo())) {
      throw new IllegalArgumentException("Invalid parking ticket. Floor does not exist");
    }
    ParkingFloor floor = floorsById.get(parkingTicket.getFloorNo());
    Vehicle parkedVehicle = floor.getParkedVehicleByRegNo(parkingTicket.getRegNo());
    boolean result = floor.unpark(parkingTicket);
    if (!result) {
      throw new IllegalArgumentException("Cannot unpark this vehicle");
    }
    floorsByType.get(parkedVehicle.getType()).remove(floor);
    floorsByType.get(parkedVehicle.getType()).add(floor);
  }

  /**
   * Helper method to create floors by dividing required number of slots between floors.
   *
   * @param numFloors The number of floors to be created
   * @param numSlotsByType The total number of slots per type
   */
  private void createFloorsByType(int numFloors, Map<VehicleType, Integer> numSlotsByType) {
    Map<VehicleType, Integer> remainder = new HashMap<>();
    Map<VehicleType, Integer> baseSlots = new HashMap<>();

    for (Map.Entry<VehicleType, Integer> entry : numSlotsByType.entrySet()) {
      VehicleType vType = entry.getKey();
      int totalSlots = entry.getValue();

      baseSlots.put(vType, totalSlots / numFloors);
      remainder.put(vType, totalSlots / numFloors + totalSlots % numFloors);

      floorsByType.put(
          vType,
          new PriorityQueue<>(
              numFloors,
              Comparator.comparingInt((ParkingFloor pf) -> pf.getFreeSlotCount(vType)).reversed()
          )
      );
    }
    for (int floorNo = 0; floorNo < numFloors - 1; floorNo++) {
      ParkingFloor floor = new ParkingFloorImpl(floorNo, baseSlots);
      floorsById.put(floorNo, floor);
      addFloorToAllQueues(floor);
    }
    ParkingFloor lastFloor = new ParkingFloorImpl(numFloors - 1, remainder);
    floorsById.put(numFloors - 1, lastFloor);
    addFloorToAllQueues(lastFloor);
  }

  /**
   * Helper method to add a floor to all vehicle type queues.
   *
   * @param floor The floor to add to all queues
   */
  private void addFloorToAllQueues(ParkingFloor floor) {
    for (Queue<ParkingFloor> queue : floorsByType.values()) {
      queue.add(floor);
    }
  }
}
