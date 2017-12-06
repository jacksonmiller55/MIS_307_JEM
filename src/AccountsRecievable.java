import java.util.ArrayList;

/**
 * This class provides the structure for accessing room based on the date that
 * they are selected. This class is used to get and set set the total room
 * charges based on the date selected.
 * 
 * 
 * @date 12/5/2017
 * 
 * @author Jackson Miller
 * @author Madison Fisher
 * @author Elias VanHorn
 *
 */
public class AccountsRecievable {
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

	private ArrayList<ArrayList<RecordedRoomCharges>> Months = new ArrayList<ArrayList<RecordedRoomCharges>>();

	/**
	 * Constructs a an ArrayList that adds all 152 rooms for each day in each month
	 * by utilizing the RecordedRoomCharges class. It then adds each month to the
	 * Calendar year ArrayList.
	 */
	public AccountsRecievable() {
		ArrayList<RecordedRoomCharges> january = new ArrayList<RecordedRoomCharges>();
		for (int a = 0; a < JANUARY; a++) {
			RecordedRoomCharges rooms = new RecordedRoomCharges();
			january.add(rooms);
		}
		ArrayList<RecordedRoomCharges> febuary = new ArrayList<RecordedRoomCharges>();
		for (int a = 0; a < FEBUARY; a++) {
			RecordedRoomCharges rooms = new RecordedRoomCharges();
			febuary.add(rooms);
		}
		ArrayList<RecordedRoomCharges> march = new ArrayList<RecordedRoomCharges>();
		for (int a = 0; a < MARCH; a++) {
			RecordedRoomCharges rooms = new RecordedRoomCharges();
			march.add(rooms);
		}
		ArrayList<RecordedRoomCharges> april = new ArrayList<RecordedRoomCharges>();
		for (int a = 0; a < APRIL; a++) {
			RecordedRoomCharges rooms = new RecordedRoomCharges();
			april.add(rooms);
		}
		ArrayList<RecordedRoomCharges> may = new ArrayList<RecordedRoomCharges>();
		for (int a = 0; a < MAY; a++) {
			RecordedRoomCharges rooms = new RecordedRoomCharges();
			may.add(rooms);
		}
		ArrayList<RecordedRoomCharges> june = new ArrayList<RecordedRoomCharges>();
		for (int a = 0; a < JUNE; a++) {
			RecordedRoomCharges rooms = new RecordedRoomCharges();
			june.add(rooms);
		}
		ArrayList<RecordedRoomCharges> july = new ArrayList<RecordedRoomCharges>();
		for (int a = 0; a < JULY; a++) {
			RecordedRoomCharges rooms = new RecordedRoomCharges();
			july.add(rooms);
		}
		ArrayList<RecordedRoomCharges> august = new ArrayList<RecordedRoomCharges>();
		for (int a = 0; a < AUGUST; a++) {
			RecordedRoomCharges rooms = new RecordedRoomCharges();
			august.add(rooms);
		}
		ArrayList<RecordedRoomCharges> september = new ArrayList<RecordedRoomCharges>();
		for (int a = 0; a < SEPTEMBER; a++) {
			RecordedRoomCharges rooms = new RecordedRoomCharges();
			september.add(rooms);
		}
		ArrayList<RecordedRoomCharges> october = new ArrayList<RecordedRoomCharges>();
		for (int a = 0; a < OCTOBER; a++) {
			RecordedRoomCharges rooms = new RecordedRoomCharges();
			october.add(rooms);
		}
		ArrayList<RecordedRoomCharges> november = new ArrayList<RecordedRoomCharges>();
		for (int a = 0; a < NOVEMBER; a++) {
			RecordedRoomCharges rooms = new RecordedRoomCharges();
			november.add(rooms);
		}
		ArrayList<RecordedRoomCharges> december = new ArrayList<RecordedRoomCharges>();
		for (int a = 0; a < DECEMBER; a++) {
			RecordedRoomCharges rooms = new RecordedRoomCharges();
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
	 * Returns the number of days in a selected month.
	 * 
	 * @param month
	 *            (int) month selected to return the number of days for that
	 *            month,=.
	 * @return (int) the number of days in a selected month.
	 */
	public int getNumDays(int month) {
		if (month == 0) {
			return 31;
		} else if (month == 1) {
			return 28;
		} else if (month == 2) {
			return 31;
		} else if (month == 3) {
			return 30;
		}
		//////////////////////
		else if (month == 4) {
			return 31;
		} else if (month == 5) {
			return 30;
		} else if (month == 6) {
			return 31;
		} else if (month == 7) {
			return 31;
		}
		/////////////////////
		else if (month == 8) {
			return 30;
		} else if (month == 9) {
			return 31;
		} else if (month == 10) {
			return 30;
		} else
			return 31;
	}

	/**
	 * Gets the amount that is charged to the room.
	 * 
	 * @param month
	 *            (int) Equals actual -1) Ranges from 0 - 11. Month selected to
	 *            book.
	 * @param day
	 *            (int) (Equals actual -1) Ranges from 0 - 30, 0 - 29, or 0 - 27
	 *            depending on the Calendar month. Day to be booked.
	 * @param room
	 *            (int) Ranges from 101 to 152. Room selected to be booked.
	 * 
	 * @return The charges assigned to the room.
	 */
	public double getChargesForRoom(int month, int day, int room) {
		return Months.get(month).get(day).getRoomCharges(room);
	}

	/**
	 * Sets the amount that is charged to the room.
	 * 
	 * @param month
	 *            (int) Equals actual -1) Ranges from 0 - 11. Month selected to
	 *            book.
	 * @param day
	 *            (int) (Equals actual -1) Ranges from 0 - 30, 0 - 29, or 0 - 27
	 *            depending on the Calendar month. Day to be booked.
	 * @param room
	 *            (int) Ranges from 101 to 152. Room selected to be booked.
	 * @return (boolean) true if the charges were set. false if not.
	 */
	public boolean setChargesForRoom(int month, int day, int room, double charge) {
		double roomSelected = Months.get(month).get(day).setRoomCharges(room, charge);
		if (roomSelected == 0) {
			return true;
		} else {
			return false;
		}
	}
}
