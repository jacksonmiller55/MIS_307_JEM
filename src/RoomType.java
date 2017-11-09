/**
 * Code for RoomType. This class provides the structure for getting and setting rooms. The room number determines the type of room. 
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
	private int [] queenDoubleRooms = new int [QUEEN_DOUBLE];
	private int [] singleKingRooms = new int [SINGLE_KING];
	private int [] kitchenSuiteRooms = new int [KITCHEN_SUITE];
	private int [] luxurySuiteRooms = new int [LUXURY_SUITE];
	
	/**
	 * Gets the room number from the appropriate room type array.
	 * @param room Room number that the user would like to book.
	 * @return True: if the room is available. False: if the room is not available.
	 */
	public boolean getRoom(int room) {
		room -= 101;
		if ((room >= 0) && (room <20)) {
			return getQueenDoubleRoom(room);
		}
		else if((room >= 20) && (room < 40)) {
			room -=20;
			return getSingleKingRoom(room);
		}
		else if((room >= 40) && (room < 50)) {
			room-=40;
			return getkitchenSuiteRoom(room);
		}
		else {
			room-=50;
			return getLuxurySuiteRoom(room);
		}
	}
	
	/** 
	 * Checks to see if a certain Double Queen room is available.
	 * @param selectedRoom Room number in the section of room types
	 * @return True: if available. False if unavailable.
	 */
	private boolean getQueenDoubleRoom(int selectedRoom) {
		boolean isAvailable = false;
			if (queenDoubleRooms[selectedRoom] == 0) {
				isAvailable = true;
			}
		return isAvailable;
	}
	
	/**
	 * Checks to see if a certain Single King room is available.
	 * @param selectedRoom Room number in the section of room types
	 * @return True: if available. False if unavailable.
	 */
	private boolean getSingleKingRoom(int selectedRoom) {
		boolean isAvailable = false;
		if (singleKingRooms[selectedRoom] == 0) {
			isAvailable = true;
		}
	return isAvailable;
	}
	
	/**
	 * Checks to see if a certain Kitchen Suite room is available.
	 * @param selectedRoom Room number in the section of room types
	 * @return True: if available. False if unavailable.
	 */
	private boolean getkitchenSuiteRoom(int selectedRoom) {
		boolean isAvailable = false;
		if (kitchenSuiteRooms[selectedRoom] == 0) {
			isAvailable = true;
		}
	return isAvailable;
	}
	
	/**
	 * Checks to see if a certain Luxury Suite room is available.
	 * @param selectedRoom Room number in the section of room types
	 * @return True: if available. False if unavailable.
	 */
	private boolean getLuxurySuiteRoom(int selectedRoom) {
		boolean isAvailable = false;
		if (luxurySuiteRooms[selectedRoom] == 0) {
			isAvailable = true;
		}
	return isAvailable;
	}
	
	/**
	 * Sets the room number in the appropriate room type array. This books the room.
	 * @param room Room number that the user would like to book.
	 * @return True: if the room is available. False: if the room is not available.
	 */
	public boolean setRoom(int room) {
		room -= 101;
		if ((room >= 0) && (room < 20)) {
			return setQueenDoubleRoom(room);
		}
		else if((room >= 20) && (room < 40)) {
			room -=20;
			return setSingleKingRoom(room);
		}
		else if((room >= 40) && (room < 50)) {
			room-=40;
			return setkitchenSuiteRoom(room);
		}
		else {
			room-=50;
			return setLuxurySuiteRoom(room);
		}
	}
	
	/**
	 * Books a room if it is still available. It books the room number in the given room type.
	 * @param selectedRoom Room number in the section of room types
	 * @return True: if successfully booked. False if not booked.
	 */
	private boolean setQueenDoubleRoom(int selectedRoom) {
		boolean bookRoom = false;
			if (queenDoubleRooms[selectedRoom] == 0) {
				queenDoubleRooms[selectedRoom] = 1;
				bookRoom = true;
			}
		return bookRoom;
	}
	
	/**
	 * Books a room if it is still available. It books the room number in the given room type.
	 * @param selectedRoom Room number in the section of room types
	 * @return True: if successfully booked. False if not booked.
	 */
	private boolean setSingleKingRoom(int selectedRoom) {
		boolean bookRoom = false;
		if (singleKingRooms[selectedRoom] == 0) {
			singleKingRooms[selectedRoom] = 1;
			bookRoom = true;
		}
	return bookRoom;
	}
	
	/**
	 * Books a room if it is still available. It books the room number in the given room type.
	 * @param selectedRoom Room number in the section of room types
	 * @return True: if successfully booked. False if not booked.
	 */
	private boolean setkitchenSuiteRoom(int selectedRoom) {
		boolean bookRoom = false;
		if (kitchenSuiteRooms[selectedRoom] == 0) {
			kitchenSuiteRooms[selectedRoom] = 1;
			bookRoom = true;
		}
	return bookRoom;
	}
	
	/**
	 * Books a room if it is still available. It books the room number in the given room type.
	 * @param selectedRoom Room number in the section of room types
	 * @return True: if successfully booked. False if not booked.
	 */
	private boolean setLuxurySuiteRoom(int selectedRoom) {
		boolean bookRoom = false;
		if (luxurySuiteRooms[selectedRoom] == 0) {
			luxurySuiteRooms[selectedRoom] = 1;
			bookRoom = true;
		}
	return bookRoom;
	}
	
	/**
	 * Selects the room and sets the value to show that it is no longer booked. The room number is from the appropriate room type array.
	 * @param room Room number that the user would like to no longer have booked.
	 * @return True: if the room is no longer booked. False: if the room was not booked in the first place.
	 */
	public boolean deselectRoom(int room) {
		room -= 101;
		if ((room >= 0) && (room < 20)) {
			return deselectQueenDoubleRoom(room);
		}
		else if((room >= 20) && (room <= 40)) {
			room -=20;
			return deselectSingleKingRoom(room);
		}
		else if((room >= 40) && (room < 50)) {
			room-=40;
			return deselectkitchenSuiteRoom(room);
		}
		else {
			room-=50;
			return deselectLuxurySuiteRoom(room);
		}
	}
	
	/**
	 * Sets a room to a status to show that it is no longer booked. The room number is used in the given room type.
	 * @param selectedRoom Room number in the section of room types
	 * @return True: if successfully booked. False if not booked.
	 */
	private boolean deselectQueenDoubleRoom(int selectedRoom) {
		boolean bookRoom = false;
			if (queenDoubleRooms[selectedRoom] == 1) {
				queenDoubleRooms[selectedRoom] = 0;
				bookRoom = true;
			}
		return bookRoom;
	}
	
	/**
	 * Sets a room to a status to show that it is no longer booked. The room number is used in the given room type.
	 * @param selectedRoom Room number in the section of room types
	 * @return True: if successfully booked. False if not booked.
	 */
	private boolean deselectSingleKingRoom(int selectedRoom) {
		boolean bookRoom = false;
		if (singleKingRooms[selectedRoom] == 1) {
			singleKingRooms[selectedRoom] = 0;
			bookRoom = true;
		}
	return bookRoom;
	}
	
	/**
	 * Sets a room to a status to show that it is no longer booked. The room number is used in the given room type.
	 * @param selectedRoom Room number in the section of room types
	 * @return True: if successfully booked. False if not booked.
	 */
	private boolean deselectkitchenSuiteRoom(int selectedRoom) {
		boolean bookRoom = false;
		if (kitchenSuiteRooms[selectedRoom] == 1) {
			kitchenSuiteRooms[selectedRoom] = 0;
			bookRoom = true;
		}
	return bookRoom;
	}
	
	/**
	 * Sets a room to a status to show that it is no longer booked. The room number is used in the given room type.
	 * @param selectedRoom Room number in the section of room types
	 * @return True: if successfully booked. False if not booked.
	 */
	private boolean deselectLuxurySuiteRoom(int selectedRoom) {
		boolean bookRoom = false;
		if (luxurySuiteRooms[selectedRoom] == 1) {
			luxurySuiteRooms[selectedRoom] = 0;
			bookRoom = true;
		}
	return bookRoom;
	}
}
