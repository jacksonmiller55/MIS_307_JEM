
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
////////////////////////
	/**
	 * Sets the charges that are assigned to the room.
	 * 
	 * @param room
	 *            Room number that the user would like to book.
	 * @return The charge that is assigned to the room.
	 */
	public double setRoom(int room, double charge) {
		room -= 101;
		if ((room >= 0) && (room < 20)) {
			return setQueenDoubleRoom(room, charge);
		} else if ((room >= 20) && (room < 40)) {
			room -= 20;
			return setSingleKingRoom(room, charge);
		} else if ((room >= 40) && (room < 50)) {
			room -= 40;
			return setkitchenSuiteRoom(room, charge);
		} else {
			room -= 50;
			return setLuxurySuiteRoom(room, charge);
		}
	}

	/**
	 *Sets the charges that are assigned to the room.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return The charge that is assigned to the room.
	 */
	private double setQueenDoubleRoom(int selectedRoom, double charge) {
		return queenDoubleRooms[selectedRoom] = charge;
	}

	/**
	 * Sets the charges that are assigned to the room.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return The charge that is assigned to the room.
	 */
	private double setSingleKingRoom(int selectedRoom, double charge) {
		return singleKingRooms[selectedRoom] = charge;
	}

	/**
	 * Sets the charges that are assigned to the room.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return The charge that is assigned to the room.
	 */
	private double setkitchenSuiteRoom(int selectedRoom, double charge) {
		return kitchenSuiteRooms[selectedRoom] = charge;
	}

	/**
	 * Sets the charges that are assigned to the room.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return The charge that is assigned to the room.
	 */
	private double setLuxurySuiteRoom(int selectedRoom, double charge) {
		return luxurySuiteRooms[selectedRoom] = charge;
	}
}
