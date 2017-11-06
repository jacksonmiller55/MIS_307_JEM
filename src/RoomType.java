
public class RoomType {
	private final int QUEEN_DOUBLE = 20;
	private final int SINGLE_KING = 20;
	private final int KITCHEN_SUITE = 10;
	private final int LUXURY_SUITE = 2;
	private int [] queenDoubleRooms = new int [QUEEN_DOUBLE];
	private int [] singleKingRooms = new int [SINGLE_KING];
	private int [] kitchenSuiteRooms = new int [KITCHEN_SUITE];
	private int [] luxurySuiteRooms = new int [LUXURY_SUITE];
	
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
	
	private boolean getQueenDoubleRoom(int selectedRoom) {
		boolean isAvailable = false;
			if (queenDoubleRooms[selectedRoom] == 0) {
				isAvailable = true;
			}
		return isAvailable;
	}
	
	private boolean getSingleKingRoom(int selectedRoom) {
		boolean isAvailable = false;
		if (singleKingRooms[selectedRoom] == 0) {
			isAvailable = true;
		}
	return isAvailable;
	}
	
	private boolean getkitchenSuiteRoom(int selectedRoom) {
		boolean isAvailable = false;
		if (kitchenSuiteRooms[selectedRoom] == 0) {
			isAvailable = true;
		}
	return isAvailable;
	}
	
	private boolean getLuxurySuiteRoom(int selectedRoom) {
		boolean isAvailable = false;
		if (luxurySuiteRooms[selectedRoom] == 0) {
			isAvailable = true;
		}
	return isAvailable;
	}
	
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
	
	private boolean setQueenDoubleRoom(int selectedRoom) {
		boolean bookRoom = false;
			if (queenDoubleRooms[selectedRoom] == 0) {
				queenDoubleRooms[selectedRoom] = 1;
				bookRoom = true;
			}
		return bookRoom;
	}
	
	private boolean setSingleKingRoom(int selectedRoom) {
		boolean bookRoom = false;
		if (singleKingRooms[selectedRoom] == 0) {
			singleKingRooms[selectedRoom] = 1;
			bookRoom = true;
		}
	return bookRoom;
	}
	
	private boolean setkitchenSuiteRoom(int selectedRoom) {
		boolean bookRoom = false;
		if (kitchenSuiteRooms[selectedRoom] == 0) {
			kitchenSuiteRooms[selectedRoom] = 1;
			bookRoom = true;
		}
	return bookRoom;
	}
	
	private boolean setLuxurySuiteRoom(int selectedRoom) {
		boolean bookRoom = false;
		if (luxurySuiteRooms[selectedRoom] == 0) {
			luxurySuiteRooms[selectedRoom] = 1;
			bookRoom = true;
		}
	return bookRoom;
	}
	
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
	
	private boolean deselectQueenDoubleRoom(int selectedRoom) {
		boolean bookRoom = false;
			if (queenDoubleRooms[selectedRoom] == 1) {
				queenDoubleRooms[selectedRoom] = 0;
				bookRoom = true;
			}
		return bookRoom;
	}
	
	private boolean deselectSingleKingRoom(int selectedRoom) {
		boolean bookRoom = false;
		if (singleKingRooms[selectedRoom] == 1) {
			singleKingRooms[selectedRoom] = 0;
			bookRoom = true;
		}
	return bookRoom;
	}
	
	private boolean deselectkitchenSuiteRoom(int selectedRoom) {
		boolean bookRoom = false;
		if (kitchenSuiteRooms[selectedRoom] == 1) {
			kitchenSuiteRooms[selectedRoom] = 0;
			bookRoom = true;
		}
	return bookRoom;
	}
	
	private boolean deselectLuxurySuiteRoom(int selectedRoom) {
		boolean bookRoom = false;
		if (luxurySuiteRooms[selectedRoom] == 1) {
			luxurySuiteRooms[selectedRoom] = 0;
			bookRoom = true;
		}
	return bookRoom;
	}
}
