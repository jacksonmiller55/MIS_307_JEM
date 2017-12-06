
/**
 * Code for RecordedRoomCharges. This class provides the structure for getting and setting
 * the charges for rooms. The room number determines the type of room.
 * 
 * @date 12/5/2017
 * 
 * @author Jackson Miller
 * @author Madison Fisher
 * @author Elias VanHorn
 */
public class RecordedRoomCharges {
	private final int QUEEN_DOUBLE = 20;
	private final int SINGLE_KING = 20;
	private final int KITCHEN_SUITE = 10;
	private final int LUXURY_SUITE = 2;
	private double[] queenDoubleRooms = new double[QUEEN_DOUBLE];
	private double[] singleKingRooms = new double[SINGLE_KING];
	private double[] kitchenSuiteRooms = new double[KITCHEN_SUITE];
	private double[] luxurySuiteRooms = new double[LUXURY_SUITE];

	/**
	 * Gets the charges assigned to the room from the appropriate room type array.
	 * 
	 * @param room
	 *            Room number that the user would get the charges that are assigned to that room.
	 * @return The charges assigned to the room.
	 */
	public double getRoomCharges(int room) {
		room -= 101;
		if ((room >= 0) && (room < 20)) {
			return getQueenDoubleRoomCharges(room);
		} else if ((room >= 20) && (room < 40)) {
			room -= 20;
			return getSingleKingRoomCharges(room);
		} else if ((room >= 40) && (room < 50)) {
			room -= 40;
			return getkitchenSuiteRoomCharges(room);
		} else {
			room -= 50;
			return getLuxurySuiteRoomCharges(room);
		}
	}

	/**
	 * Gets the charges assigned to the room from the appropriate room type array.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return The charges assigned to the room.
	 */
	private double getQueenDoubleRoomCharges(int selectedRoom) {
		return queenDoubleRooms[selectedRoom];
	}

	/**
	 * Gets the charges assigned to the room from the appropriate room type array.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return The charges assigned to the room.
	 */
	private double getSingleKingRoomCharges(int selectedRoom) {
		return singleKingRooms[selectedRoom];
	}

	/**
	 * Gets the charges assigned to the room from the appropriate room type array.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return The charges assigned to the room.
	 */
	private double getkitchenSuiteRoomCharges(int selectedRoom) {
		return kitchenSuiteRooms[selectedRoom];
	}

	/**
	 * Gets the charges assigned to the room from the appropriate room type array.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return The charges assigned to the room.
	 */
	private double getLuxurySuiteRoomCharges(int selectedRoom) {
		return luxurySuiteRooms[selectedRoom];
	}

	/**
	 * Sets the charges that are assigned to the room.
	 * 
	 * @param room
	 *            Room number that the user would like to book.
	 * @return The charge that is assigned to the room.
	 */
	public double setRoomCharges(int room, double charge) {
		room -= 101;
		if ((room >= 0) && (room < 20)) {
			return setQueenDoubleRoomCharges(room, charge);
		} else if ((room >= 20) && (room < 40)) {
			room -= 20;
			return setSingleKingRoomCharges(room, charge);
		} else if ((room >= 40) && (room < 50)) {
			room -= 40;
			return setkitchenSuiteRoomCharges(room, charge);
		} else {
			room -= 50;
			return setLuxurySuiteRoomCharges(room, charge);
		}
	}

	/**
	 *Sets the charges that are assigned to the room.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return The charge that is assigned to the room.
	 */
	private double setQueenDoubleRoomCharges(int selectedRoom, double charge) {
		return queenDoubleRooms[selectedRoom] = charge;
	}

	/**
	 * Sets the charges that are assigned to the room.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return The charge that is assigned to the room.
	 */
	private double setSingleKingRoomCharges(int selectedRoom, double charge) {
		return singleKingRooms[selectedRoom] = charge;
	}

	/**
	 * Sets the charges that are assigned to the room.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return The charge that is assigned to the room.
	 */
	private double setkitchenSuiteRoomCharges(int selectedRoom, double charge) {
		return kitchenSuiteRooms[selectedRoom] = charge;
	}

	/**
	 * Sets the charges that are assigned to the room.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return The charge that is assigned to the room.
	 */
	private double setLuxurySuiteRoomCharges(int selectedRoom, double charge) {
		return luxurySuiteRooms[selectedRoom] = charge;
	}
}
