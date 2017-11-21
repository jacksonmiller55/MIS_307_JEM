import java.util.ArrayList;

/**
 * Code for Calendar.
 * @author Jackson Miller
 * @author Madison Fisher
 * @author Elias VanHorn
 *
 */
public class Calendar {
	private final int JANUARY = 31;
	private final int FEBUARY = 28;
	private final int MARCH = 31;
	private final int APRIL = 30;
	///////////////////////////
	private final int MAY = 31;
	private final int JUNE = 30;
	private final int JULY = 31;
	private final int AUGUST = 31;
	////////////////////////////
	private final int SEPTEMBER = 30;
	private final int OCTOBER = 31;
	private final int NOVEMBER = 30;
	private final int DECEMBER = 31;
	
	private ArrayList<ArrayList<RoomType>> Months = new ArrayList<ArrayList<RoomType>>();

	
	/**
	 * Constructs a an ArrayList that adds all 152 rooms for each day in each month. It then adds each month to the Calendar year ArrayList.
	 */
	public Calendar() {
		ArrayList<RoomType> january = new ArrayList<RoomType>();
		for (int a = 0; a < JANUARY; a++) {
			RoomType rooms = new RoomType();
			january.add(rooms);
		}
		ArrayList<RoomType> febuary = new ArrayList<RoomType>();
		for (int a = 0; a < FEBUARY; a++) {
			RoomType rooms = new RoomType();
			febuary.add(rooms);
		}
		ArrayList<RoomType> march = new ArrayList<RoomType>();
		for (int a = 0; a < MARCH; a++) {
			RoomType rooms = new RoomType();
			march.add(rooms);
		}
		ArrayList<RoomType> april = new ArrayList<RoomType>();
		for (int a = 0; a < APRIL; a++) {
			RoomType rooms = new RoomType();
			april.add(rooms);
		}
		ArrayList<RoomType> may = new ArrayList<RoomType>();
		for (int a = 0; a < MAY; a++) {
			RoomType rooms = new RoomType();
			may.add(rooms);
		}
		ArrayList<RoomType> june = new ArrayList<RoomType>();
		for (int a = 0; a < JUNE; a++) {
			RoomType rooms = new RoomType();
			june.add(rooms);
		}
		ArrayList<RoomType> july = new ArrayList<RoomType>();
		for (int a = 0; a < JULY; a++) {
			RoomType rooms = new RoomType();
			july.add(rooms);
		}
		ArrayList<RoomType> august = new ArrayList<RoomType>();
		for (int a = 0; a < AUGUST; a++) {
			RoomType rooms = new RoomType();
			august.add(rooms);
		}
		ArrayList<RoomType> september = new ArrayList<RoomType>();
		for (int a = 0; a < SEPTEMBER; a++) {
			RoomType rooms = new RoomType();
			september.add(rooms);
		}
		ArrayList<RoomType> october = new ArrayList<RoomType>();
		for (int a = 0; a < OCTOBER; a++) {
			RoomType rooms = new RoomType();
			october.add(rooms);
		}
		ArrayList<RoomType> november = new ArrayList<RoomType>();
		for (int a = 0; a < NOVEMBER; a++) {
			RoomType rooms = new RoomType();
			november.add(rooms);
		}
		ArrayList<RoomType> december = new ArrayList<RoomType>();
		for (int a = 0; a < DECEMBER; a++) {
			RoomType rooms = new RoomType();
			december.add(rooms);
		}

		Months.add(january);
		Months.add(febuary);
		Months.add(march);
		Months.add(april);
		Months.add(may);
		Months.add(june);
		Months.add(july);
		Months.add(august);
		Months.add(september);
		Months.add(october);
		Months.add(november);
		Months.add(december);
		
	}
	
	/**
	 * Checks the availability of the room for the given month, day and room.
	 * 
	 * @param month (int) Equals actual -1) Ranges from 0 - 11. Month selected to book.
	 * @param day (int) (Equals actual -1) Ranges from 0 - 30, 0 - 29, or 0 - 27 depending on the Calendar month. Day to be booked.
	 * @param room (int) Ranges from 101 to 152. Room selected to be booked.
	 * @return (boolean) returns the availability of the room
	 */
	public boolean checkRoomAvailable(int month, int day, int room) {
		boolean roomSelected = Months.get(month).get(day).getRoom(room);
		if (roomSelected) {
			return roomSelected;
		}
		else{
			return roomSelected;
		}
	}
	
	/**
	 * Books the room for the given month, day and room.
	 * 
	 * @param month (int) Equals actual -1) Ranges from 0 - 11. Month selected to book.
	 * @param day (int) (Equals actual -1) Ranges from 0 - 30, 0 - 29, or 0 - 27 depending on the Calendar month. Day to be booked.
	 * @param room (int) Ranges from 101 to 152. Room selected to be booked.
	 * @return (boolean) returns if room is booked or not.
	 */
	public boolean bookRoom(int month, int day, int room) {
		double roomSelected = Months.get(month).get(day).setRoom(room);
		if (roomSelected == 0) {
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Unbooks the room for the given month, day and room.
	 * 
	 * @param month (int) Equals actual -1) Ranges from 0 - 11. Month selected to unbook.
	 * @param day (int) (Equals actual -1) Ranges from 0 - 30, 0 - 29, or 0 - 27 depending on the Calendar month. Day to be unbooked.
	 * @param room (int) Ranges from 101 to 152. Room selected to be unbooked.
	 * @return (boolean) returns if room is unbooked or not.
	 */
	public boolean unbookRoom(int month, int day, int room) {
		String status = "";
		boolean roomSelected = Months.get(month).get(day).deselectRoom(room);
		if (roomSelected) {
			status = "Successful";
			System.out.println(status);
			System.out.println("Now Available");
			return roomSelected;
		}
		else{
			status = "Unsuccessful";
			System.out.println(status);
			System.out.println("Error");
			return roomSelected;
		}
	}
	
	/**
	 * Gets the price of the room.
	 * 
	 * @param month (int) Equals actual -1) Ranges from 0 - 11. Month selected to get the price of the room.
	 * @param day (int) (Equals actual -1) Ranges from 0 - 30, 0 - 29, or 0 - 27 depending on the Calendar month. Day selected to get the price of the room.
	 * @param room (int) Ranges from 101 to 152. Room selected to get price of room.
	 * @return (double) price of room.
	 */
	public double getRoomPrice(int month, int day, int room) {
		return Months.get(month).get(day).getRoomPrice(room);
		
	}
	
	
	/**
	 * Returns the number of days in a selected month.
	 * 
	 * @param month (int) month selected to return the number of days for that month,=.
	 * @return (int) the number of days in a selected month.
	 */
	public int getNumDays(int month) {
		if (month == 0) {
			return 31;
		}
		else if(month == 1) {
			return 28;
		}
		else if(month == 2) {
			return 31;
		}
		else if(month == 3) {
			return 30;
		}
		//////////////////////
		else if(month == 4) {
			return 31;
		}
		else if(month == 5) {
			return 30;
		}
		else if(month == 6) {
			return 31;
		}
		else if(month == 7) {
			return 31;
		}
		/////////////////////
		else if(month == 8) {
			return 30;
		}
		else if(month == 9) {
			return 31;
		}
		else if(month == 10) {
			return 30;
		}
		else return 31;
	}
}
