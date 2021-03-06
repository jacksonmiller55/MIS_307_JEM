/**
 * Code for RoomType. This class provides the structure for getting and setting
 * rooms. The room number determines the type of room.
 * 
 * @date 12/5/2017
 * 
 * @author Jackson Miller
 * @author Madison Fisher
 * @author Elias VanHorn
 *
 */
public class RoomType {
	private final int QUEEN_DOUBLE = 20;
	private final int SINGLE_KING = 20;
	private final int KITCHEN_SUITE = 10;
	private final int LUXURY_SUITE = 2;
	private double[] queenDoubleRooms = new double[QUEEN_DOUBLE];
	private double[] singleKingRooms = new double[SINGLE_KING];
	private double[] kitchenSuiteRooms = new double[KITCHEN_SUITE];
	private double[] luxurySuiteRooms = new double[LUXURY_SUITE];

	/**
	 * Gets the room number and the state of the room from the appropriate room type array.
	 * 
	 * @param room
	 *            Room number that the user would check the status of.
	 * @return The state of the room.
	 */
	public double getRoom(int room) {
		room -= 101;
		if ((room >= 0) && (room < 20)) {
			return getQueenDoubleRoom(room);
		} else if ((room >= 20) && (room < 40)) {
			room -= 20;
			return getSingleKingRoom(room);
		} else if ((room >= 40) && (room < 50)) {
			room -= 40;
			return getkitchenSuiteRoom(room);
		} else {
			room -= 50;
			return getLuxurySuiteRoom(room);
		}
	}

	/**
	 * Checks  the availability of a certain Double Queen room.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return The state of the room.
	 */
	private double getQueenDoubleRoom(int selectedRoom) {
		return queenDoubleRooms[selectedRoom];
	}

	/**
	 * Checks  the availability of a certain Single King room.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return The state of the room.
	 */
	private double getSingleKingRoom(int selectedRoom) {
		return singleKingRooms[selectedRoom];
	}

	/**
	 * Checks  the availability of a certain Kitchen Suite room.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return The state of the room.
	 */
	private double getkitchenSuiteRoom(int selectedRoom) {
		return kitchenSuiteRooms[selectedRoom];
	}

	/**
	 * Checks  the availability of a certain Luxury Suite room.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return The state of the room.
	 */
	private double getLuxurySuiteRoom(int selectedRoom) {
		return luxurySuiteRooms[selectedRoom];
	}

	/**
	 * Sets the room price in the appropriate room type array. This books the room.
	 * 
	 * @param room
	 *            Room number that the user would like to book.
	 * @return The price of the room is available.
	 */
	public double setRoom(int room) {
		room -= 101;
		if ((room >= 0) && (room < 20)) {
			return setQueenDoubleRoom(room);
		} else if ((room >= 20) && (room < 40)) {
			room -= 20;
			return setSingleKingRoom(room);
		} else if ((room >= 40) && (room < 50)) {
			room -= 40;
			return setkitchenSuiteRoom(room);
		} else {
			room -= 50;
			return setLuxurySuiteRoom(room);
		}
	}

	/**
	 * Books a room if it is still available and sets the price. It books the room
	 * number in the given room type.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return Price of the room that is booked.
	 */
	private double setQueenDoubleRoom(int selectedRoom) {
		if (queenDoubleRooms[selectedRoom] == 0) {
			queenDoubleRooms[selectedRoom] = 129.99;
		}
		return queenDoubleRooms[selectedRoom];
	}

	/**
	 * Books a room if it is still available and sets the price. It books the room
	 * number in the given room type.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return Price of the room that is booked.
	 */
	private double setSingleKingRoom(int selectedRoom) {
		if (singleKingRooms[selectedRoom] == 0) {
			singleKingRooms[selectedRoom] = 139.99;
		}
		return singleKingRooms[selectedRoom];
	}

	/**
	 * Books a room if it is still available and sets the price. It books the room
	 * number in the given room type.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return Price of the room that is booked.
	 */
	private double setkitchenSuiteRoom(int selectedRoom) {
		if (kitchenSuiteRooms[selectedRoom] == 0) {
			kitchenSuiteRooms[selectedRoom] = 159.99;
		}
		return kitchenSuiteRooms[selectedRoom];
	}

	/**
	 * Books a room if it is still available and sets the price. It books the room
	 * number in the given room type.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return Price of the room that is booked.
	 */
	private double setLuxurySuiteRoom(int selectedRoom) {
		if (luxurySuiteRooms[selectedRoom] == 0) {
			luxurySuiteRooms[selectedRoom] = 199.99;
		}
		return luxurySuiteRooms[selectedRoom];
	}

	/**
	 * Selects the room and sets the value to show that it is no longer booked. The
	 * room number is from the appropriate room type array.
	 * 
	 * @param room
	 *            Room number that the user would like to no longer have booked.
	 * @return True: if the room is no longer booked. False: if the room was not
	 *         booked in the first place.
	 */
	public boolean deselectRoom(int room) {
		room -= 101;
		if ((room >= 0) && (room < 20)) {
			return deselectQueenDoubleRoom(room);
		} else if ((room >= 20) && (room < 40)) {
			room -= 20;
			return deselectSingleKingRoom(room);
		} else if ((room >= 40) && (room < 50)) {
			room -= 40;
			return deselectkitchenSuiteRoom(room);
		} else {
			room -= 50;
			return deselectLuxurySuiteRoom(room);
		}
	}

	/**
	 * Sets a room to a status to show that it is no longer booked. The room number
	 * is used in the given room type.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return True: if successfully booked. False if not booked.
	 */
	private boolean deselectQueenDoubleRoom(int selectedRoom) {
		boolean bookRoom = false;
		if (queenDoubleRooms[selectedRoom] != 0) {
			queenDoubleRooms[selectedRoom] = 0;
			bookRoom = true;
		}
		return bookRoom;
	}

	/**
	 * Sets a room to a status to show that it is no longer booked. The room number
	 * is used in the given room type.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return True: if successfully booked. False if not booked.
	 */
	private boolean deselectSingleKingRoom(int selectedRoom) {
		boolean bookRoom = false;
		if (singleKingRooms[selectedRoom] != 0) {
			singleKingRooms[selectedRoom] = 0;
			bookRoom = true;
		}
		return bookRoom;
	}

	/**
	 * Sets a room to a status to show that it is no longer booked. The room number
	 * is used in the given room type.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return True: if successfully booked. False if not booked.
	 */
	private boolean deselectkitchenSuiteRoom(int selectedRoom) {
		boolean bookRoom = false;
		if (kitchenSuiteRooms[selectedRoom] != 0) {
			kitchenSuiteRooms[selectedRoom] = 0;
			bookRoom = true;
		}
		return bookRoom;
	}

	/**
	 * Sets a room to a status to show that it is no longer booked. The room number
	 * is used in the given room type.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return True: if successfully booked. False if not booked.
	 */
	private boolean deselectLuxurySuiteRoom(int selectedRoom) {
		boolean bookRoom = false;
		if (luxurySuiteRooms[selectedRoom] != 0) {
			luxurySuiteRooms[selectedRoom] = 0;
			bookRoom = true;
		}
		return bookRoom;
	}

	/**
	 * Gets the price of the room.
	 * 
	 * @param room
	 *            The selected room to check the price.
	 * @return price of the room.
	 */
	public double getRoomPrice(int room) {
		room -= 101;
		if ((room >= 0) && (room < 20)) {
			return getQueenDoubleRoomPrice(room);
		} else if ((room >= 20) && (room < 40)) {
			room -= 20;
			return getSingleKingRoomPrice(room);
		} else if ((room >= 40) && (room < 50)) {
			room -= 40;
			return getKitchenSuiteRoomPrice(room);
		} else {
			room -= 50;
			return getLuxurySuiteRoomPrice(room);
		}
	}

	/**
	 * Gets the price of the selected Double Queen room.
	 * 
	 * @param selectedRoom
	 *            Room selected to check the price.
	 * @return The price of the selected Double Queen room.
	 */
	public double getQueenDoubleRoomPrice(int selectedRoom) {
		return queenDoubleRooms[selectedRoom];
	}

	/**
	 * Gets the price of the selected Single King room.
	 * 
	 * @param selectedRoom
	 *            Room selected to check the price.
	 * @return The price of the selected Single King room.
	 */
	public double getSingleKingRoomPrice(int selectedRoom) {
		return singleKingRooms[selectedRoom];
	}

	/**
	 * Gets the price of the selected Kitchen Suite room.
	 * 
	 * @param selectedRoom
	 *            Room selected to check the price.
	 * @return The price of the selected Kitchen Suite room.
	 */
	public double getKitchenSuiteRoomPrice(int selectedRoom) {
		return kitchenSuiteRooms[selectedRoom];
	}

	/**
	 * Gets the price of the selected Luxury Suite Room.
	 * 
	 * @param selectedRoom
	 *            Room selected to check the price.
	 * @return The price of the Luxury Suite Room.
	 */
	public double getLuxurySuiteRoomPrice(int selectedRoom) {
		return luxurySuiteRooms[selectedRoom];
	}

	//////////DIRTY//////////
	/**
	 * Sets the room price to -1 in the appropriate room type array. This sets the room to a dirty state.
	 * 
	 * @param room
	 *            Room number that the user would like to set to dirty.
	 * @return -1 to show that the room is dirty.
	 */
	public double setDirtyRoom(int room) {
		room -= 101;
		if ((room >= 0) && (room < 20)) {
			return setDirtyQueenDoubleRoom(room);
		} else if ((room >= 20) && (room < 40)) {
			room -= 20;
			return setDirtySingleKingRoom(room);
		} else if ((room >= 40) && (room < 50)) {
			room -= 40;
			return setDirtykitchenSuiteRoom(room);
		} else {
			room -= 50;
			return setDirtyLuxurySuiteRoom(room);
		}
	}

	/**
	 * Sets the room -1 to show that it is dirty in the given room type array.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return -1 to show that the room is dirty.
	 */
	private double setDirtyQueenDoubleRoom(int selectedRoom) {
		return queenDoubleRooms[selectedRoom] = -1;
	}

	/**
	 * Sets the room -1 to show that it is dirty in the given room type array.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return -1 to show that the room is dirty.
	 */
	private double setDirtySingleKingRoom(int selectedRoom) {
		return singleKingRooms[selectedRoom] = -1;
	}

	/**
	 * Sets the room -1 to show that it is dirty in the given room type array.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return -1 to show that the room is dirty.
	 */
	private double setDirtykitchenSuiteRoom(int selectedRoom) {
		return kitchenSuiteRooms[selectedRoom] = -1;
	}

	/**
	 * Sets the room -1 to show that it is dirty in the given room type array.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types
	 * @return -1 to show that the room is dirty.
	 */
	private double setDirtyLuxurySuiteRoom(int selectedRoom) {
		return luxurySuiteRooms[selectedRoom] = -1;
	}
	
	/**
	 * Sets the room to clean in the appropriate room type array. This sets the room to a clean state.
	 * 
	 * @param room
	 *            Room number that the user would like to set to clean.
	 * @return Clean room state for the room selected.
	 */
	public double setCleanRoom(int room) {
		room -= 101;
		if ((room >= 0) && (room < 20)) {
			return setCleanQueenDoubleRoom(room);
		} else if ((room >= 20) && (room < 40)) {
			room -= 20;
			return setCleanSingleKingRoom(room);
		} else if ((room >= 40) && (room < 50)) {
			room -= 40;
			return setCleankitchenSuiteRoom(room);
		} else {
			room -= 50;
			return setCleanLuxurySuiteRoom(room);
		}
	}

	/**
	 * Sets the room to clean in the appropriate room type array. This sets the room to a clean state.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types that they would like to set to clean.
	 * @return Clean room state for the room selected.
	 */
	private double setCleanQueenDoubleRoom(int selectedRoom) {
		return queenDoubleRooms[selectedRoom] = 0;
		
	}

	/**
	 * Sets the room to clean in the appropriate room type array. This sets the room to a clean state.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types that they would like to set to clean.
	 * @return Clean room state for the room selected.
	 */
	private double setCleanSingleKingRoom(int selectedRoom) {
		return singleKingRooms[selectedRoom] = 0;
			
	}

	/**
	 * Sets the room to clean in the appropriate room type array. This sets the room to a clean state.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types that they would like to set to clean.
	 * @return Clean room state for the room selected.
	 */
	private double setCleankitchenSuiteRoom(int selectedRoom) {
		return kitchenSuiteRooms[selectedRoom] = 0;
	}

	/**
	 * Sets the room to clean in the appropriate room type array. This sets the room to a clean state.
	 * 
	 * @param selectedRoom
	 *            Room number in the section of room types that they would like to set to clean.
	 * @return Clean room state for the room selected.
	 */
	private double setCleanLuxurySuiteRoom(int selectedRoom) {
		return luxurySuiteRooms[selectedRoom] = 0;
	}
}
