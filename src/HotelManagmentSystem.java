import java.util.InputMismatchException;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Code for HotelManagementSystem This class provides the main interface that
 * the user interacts with in order to select a room number and date that they
 * would like to book, check to see if it is vacant, deselect, or check out.
 * 
 * @date 11/20/2017
 * 
 * @author Jackson Miller
 * @author Madison Fisher
 * @author Elias VanHorn
 */
public class HotelManagmentSystem {
	static int sMonth;
	static int sDay;
	static int sRoom;

	public static void main(String[] args) {
		String selection = null;
		Scanner input = new Scanner(System.in);
		boolean firstRun = true;
		boolean continueToRun = true;
		Calendar year = loadData();

		while (continueToRun) {
			// Adds 2 lines for spacing after the first time the program is run.
			if (!firstRun) {
				System.out.println();
				System.out.println();
			}
			selection = printSelectionOptions(input);

			if (selection.equals("Q")) {
				continueToRun = quitProgram(year);
			} else if (selection.equals("B")) {
				bookSpecificRoomNumber(year, input);
			} else if (selection.equals("D")) {
				deselctSpecificRoomNumber(year, input);
			} else if (selection.equals("T")) {
				bookRoomType(year, input);
			} else if (selection.equals("C")) {
				checkout(year, input);
			}

			// Tells the program that this is no longer the first run.
			firstRun = false;
		}
		input.close();

	}

	/**
	 * Loads the saved data from "save.txt". Inserts the booked rooms into a new
	 * Calendar object. Creates a new Calendar object if no data is loaded in.
	 * 
	 * @return new Calendar year.
	 */
	private static Calendar loadData() {
		Calendar year = new Calendar();
		File save = new File("src\\save.txt");
		try {
			Scanner load = new Scanner(save);
			while (load.hasNextLine()) {
				sMonth = load.nextInt();
				sDay = load.nextInt();
				sRoom = load.nextInt();
				String isBooked = load.next();

				if (isBooked.trim().equals("B")) {
					year.bookRoom(sMonth, sDay, sRoom);
				}
			}
			return year;

		} catch (FileNotFoundException e) {
			return year;
		}
	}

	/**
	 * Quits the program and saves the all of the room states if they are booked or
	 * not. Rooms are saved in the file "save.text".
	 * 
	 * @return False quit the program.
	 */
	public static boolean quitProgram(Calendar year) {
		System.out.println("You have quit the program.");

		File save = new File("src\\save.txt");
		try {
			PrintWriter out = new PrintWriter(save);
			for (int month = 0; month < 12; month++) {
				for (int day = 0; day < year.getNumDays(month); day++) {
					for (int room = 101; room < 153; room++) {
						if (year.checkRoomAvailable(month, day, room)) {
							out.print(month + "\t" + day + "\t" + room + "\t" + "A");
						} else if (!year.checkRoomAvailable(month, day, room)) {
							out.print(month + "\t" + day + "\t" + room + "\t" + "B");
						}
						if (!(month == 11 && day == 30 && room == 152)) {
							out.println();
						}
					}
				}
			}
			out.close();
			return false;
		} catch (FileNotFoundException e) {
			System.out.println("Print writer did not output to a file.");
			return true;
		}
	}

	/**
	 * Prints a selection of the items for the user to choose. User selects the
	 * option of their choice.
	 * 
	 * @param input
	 *            Scanner(System.in);
	 * @return Character selection.
	 */
	public static String printSelectionOptions(Scanner input) {
		String selection = null;
		boolean correctInput = false;

		System.out.println("Select from the following options");
		System.out.println("(Q) Quit the program");
		System.out.println("(B) Book Specific Room Number");
		System.out.println("(T) Book Room Type");
		System.out.println("(D) Deselect Specific Room Number because of error");
		System.out.println("(C) Checkout");

		selection = input.next();

		while (!correctInput) {

			if (selection.length() > 2) {
				System.out.println("Please enter only one character to select the appropriat option.");
				selection = input.next();
			} else if (!Character.isAlphabetic(selection.charAt(0))) {
				System.out.println(
						"Please enter only the selected character to select the appropriat option. Numbers will not be accepted.");
				selection = input.next();
			} else if ((selection.toUpperCase().equals("Q")) || (selection.toUpperCase().equals("B"))
					|| (selection.toUpperCase().equals("D")) || (selection.toUpperCase().equals("T"))
					|| (selection.toUpperCase().equals("C"))) {
				correctInput = true;
			} else {
				System.out.println("The Character must match one of the appropriat options.");
				selection = input.next();
			}
		}
		return selection.toUpperCase();
	}

	/**
	 * Books a specific room number for the given month, date, and room number.
	 * 
	 * @param year
	 *            Calendar object. Used to reference month, date, and room number.
	 * @param input
	 *            Scanner(System.in);
	 */
	public static void bookSpecificRoomNumber(Calendar year, Scanner input) {

		sMonth = monthInput(input);

		sDay = dayInput(input);

		sRoom = roomInput(input);

		boolean isRoomAvailable = false;
		isRoomAvailable = year.checkRoomAvailable(sMonth, sDay, sRoom);

		if (isRoomAvailable) {
			year.bookRoom(sMonth, sDay, sRoom);
			System.out.printf("Room %d was booked for %d-%d", sRoom, sMonth + 1, sDay + 1);

		} else {
			System.out.printf("Room %d is unavailable for %d-%d", sRoom, sMonth + 1, sDay + 1);
		}
	}

	/**
	 * Allows the user to de-select a room so that it is available to book.
	 * 
	 * @param year
	 *            Calendar object. Used to reference month, date, and room number.
	 * @param input
	 *            Scanner(System.in);
	 */
	public static boolean deselctSpecificRoomNumber(Calendar year, Scanner input) {

		sMonth = monthInput(input);

		sDay = dayInput(input);

		sRoom = roomInput(input);

		// Calendar calendarToReturn = new Calendar(year);

		boolean isRoomAvailable = false;
		isRoomAvailable = year.checkRoomAvailable(sMonth, sDay, sRoom);

		if (!isRoomAvailable) {
			year.unbookRoom(sMonth, sDay, sRoom);
			System.out.printf("Room %d was unselected for %d-%d", sRoom, sMonth + 1, sDay + 1);
			System.out.println();
			return true;

		} else {
			System.out.printf("Room %d was not unselected for %d-%d", sRoom, sMonth + 1, sDay + 1);
			System.out.println();
			return false;
		}
	}

	/**
	 * Gets the user input for the month that the user would like to book.
	 * 
	 * @param input
	 *            Scanner(System.in);
	 * @param month
	 *            Month that the user would like to book.
	 * @return month number
	 */
	public static int monthInput(Scanner input) {
		System.out.print("Month: ");
		boolean isOKinput = false;

		sMonth = input.nextInt();
		while (!isOKinput) {
			if ((sMonth >= 1) && (sMonth <= 12)) {
				sMonth -= 1;
				isOKinput = true;
			} else {
				System.out.println("The month must be between the numbers 1 and 12");
				sMonth = input.nextInt();
			}
		}
		return sMonth;
	}

	/**
	 * Gets the user input for the day that the user would like to book.
	 * 
	 * @param input
	 *            Scanner(System.in);
	 * @param month
	 *            The month that the user would like to book.
	 * @param day
	 *            The day that the user would like to book.
	 * @return room number
	 */
	public static int dayInput(Scanner input) {
		System.out.print("Day: ");
		boolean isOKinput = false;

		sDay = input.nextInt();

		// months with the amount of days to match the arrays
		// 0,2,4,6,7,9,11 = 31
		// 1 = 28
		// 3,5,8,10 =30

		if ((sMonth == 0) && (sMonth == 2) && (sMonth == 4) && (sMonth == 6) && (sMonth == 7) && (sMonth == 9)
				&& (sMonth == 11)) {
			while (!isOKinput) {
				if ((sDay >= 1) && (sDay <= 31)) {
					sDay -= 1;
					isOKinput = true;
				} else {
					System.out.println("The day must be between the numbers 1 and 31");
					sDay = input.nextInt();
				}
			}
			return sDay;
		}

		else if ((sMonth == 3) && (sMonth == 5) && (sMonth == 8) && (sMonth == 10)) {
			while (!isOKinput) {
				if ((sDay >= 1) && (sDay <= 30)) {
					sDay -= 1;
					isOKinput = true;
				} else {
					System.out.println("The day must be between the numbers 1 and 30");
					sDay = input.nextInt();
				}
			}
			return sDay;
		} else {
			while (!isOKinput) {
				if ((sDay >= 1) && (sDay <= 28)) {
					sDay -= 1;
					isOKinput = true;
				} else {
					System.out.println("The day must be between the numbers 1 and 28");
					sDay = input.nextInt();
				}
			}
			return sDay;
		}
	}

	/**
	 * Gets the room input from the user.
	 * 
	 * @param input
	 *            Scanner(System.in);
	 * @param room
	 *            room that the user would like to select.
	 * @return room number.
	 */
	public static int roomInput(Scanner input) {
		System.out.print("Room Number: ");
		boolean isOKinput = false;

		sRoom = input.nextInt();
		while (!isOKinput) {
			if ((sRoom >= 101) && (sRoom <= 152)) {
				isOKinput = true;
			} else {
				System.out.println("The room number must be between the numbers 101 and 152");
				sRoom = input.nextInt();
			}
		}
		return sRoom;
	}

	/**
	 * Gets the user input for the room that the user would like to book.
	 * 
	 * @param year
	 *            Calendar object. Used to reference month, date, and room number.
	 * @param input
	 *            Scanner(System.in);s
	 */
	public static void bookRoomType(Calendar year, Scanner input) {
		String selection = null;
		boolean correctInput = false;

		sMonth = monthInput(input);
		sDay = dayInput(input);

		System.out.println("What type of room would you like to book?");
		System.out.println("(D)ouble Queen");
		System.out.println("(S)ingle King");
		System.out.println("(K)itchen Suite");
		System.out.println("(L)uxury Suite");

		selection = input.next();

		while (!correctInput) {

			if (selection.length() > 2) {
				System.out.println("Please enter only one character to select the appropriat option.");
				selection = input.next();
			} else if (!Character.isAlphabetic(selection.charAt(0))) {
				System.out.println(
						"Please enter only the selected character to select the appropriat option. Numbers will not be accepted.");
				selection = input.next();
			} else if ((selection.toUpperCase().equals("D")) || (selection.toUpperCase().equals("S"))
					|| (selection.toUpperCase().equals("K")) || (selection.toUpperCase().equals("L"))) {
				correctInput = true;
			} else {
				System.out.println("The Character must match one of the appropriat options.");
				selection = input.next();
			}
		}

		if (selection.equals("D")) {
			System.out.println("You have selected Queen Double");

			sRoom = 101;
			boolean isRoomAvailable = false;
			while ((!isRoomAvailable) && (sRoom <= 120)) {
				isRoomAvailable = year.checkRoomAvailable(sMonth, sDay, sRoom);
				sRoom += 1;
			}
			if ((sRoom == 121) && (!isRoomAvailable)) {
				System.out.println("There are no rooms of this style available.");
			} else {
				sRoom -= 1;
				year.bookRoom(sMonth, sDay, sRoom);
				System.out.printf("Room %d was booked for %d-%d", sRoom, sMonth + 1, sDay + 1);
			}

		} else if (selection.equals("S")) {
			System.out.println("You have selected Single King");

			sRoom = 121;
			boolean isRoomAvailable = false;
			while ((!isRoomAvailable) && (sRoom <= 140)) {
				isRoomAvailable = year.checkRoomAvailable(sMonth, sDay, sRoom);
				sRoom += 1;
			}
			if ((sRoom == 141) && (!isRoomAvailable)) {
				System.out.println("There are no rooms of this style available.");
			} else {
				sRoom -= 1;
				year.bookRoom(sMonth, sDay, sRoom);
				System.out.printf("Room %d was booked for %d-%d", sRoom, sMonth + 1, sDay + 1);
			}

		} else if (selection.equals("K")) {
			System.out.println("You have selected Kitchen Suite");

			sRoom = 141;
			boolean isRoomAvailable = false;
			while ((!isRoomAvailable) && (sRoom <= 150)) {
				isRoomAvailable = year.checkRoomAvailable(sMonth, sDay, sRoom);
				sRoom += 1;
			}
			if ((sRoom == 151) && (!isRoomAvailable)) {
				System.out.println("There are no rooms of this style available.");
			} else {
				sRoom -= 1;
				year.bookRoom(sMonth, sDay, sRoom);
				System.out.printf("Room %d was booked for %d-%d", sRoom, sMonth + 1, sDay + 1);
			}

		} else if (selection.equals("L")) {
			System.out.println("You have selected Luxury Suite");

			sRoom = 151;
			boolean isRoomAvailable = false;
			while ((!isRoomAvailable) && (sRoom <= 152)) {
				isRoomAvailable = year.checkRoomAvailable(sMonth, sDay, sRoom);
				sRoom += 1;
			}
			if ((sRoom == 153) && (!isRoomAvailable)) {
				System.out.println("There are no rooms of this style available.");
			} else {
				sRoom -= 1;
				year.bookRoom(sMonth, sDay, sRoom);
				System.out.printf("Room %d was booked for %d-%d", sRoom, sMonth + 1, sDay + 1);
			}

		}

	}

	/**
	 * Checks the user out of the room and creates a that adds up the extra expenses
	 * and price of the room.
	 * 
	 * @param year
	 *            (Calendar) year that contains booked and unbooked rooms.
	 * @param input
	 *            (Scanner) that takes in user input for room to checkout.
	 * @return Total price of room to checkout.
	 */
	public static double checkout(Calendar year, Scanner input) {
		double roomPrice = year.getRoomPrice(sMonth, sDay, sRoom);
		if (deselctSpecificRoomNumber(year, input)) {
			double totalPrice = roomPrice + calculateOtherExpenses(year, input);
			System.out.println("Room Price: \t\t\t\t" + roomPrice);
			System.out.println("Total Price: \t\t\t\t" + totalPrice);
			return totalPrice;
		} else {
			return 0;
		}
	}

	/**
	 * Calculates the cost of additional expenses including the number of movies
	 * rented and the mini bar items.
	 * 
	 * @param year
	 *            (Calendar) object that contains the booked and unbooked rooms.
	 * @param input
	 *            (Scanner) that takes in the number of movies and items that were
	 *            taken from the mini bar.
	 * @return calculated additional expenses from movies and the mini bar.
	 */
	public static double calculateOtherExpenses(Calendar year, Scanner input) {
		int nummberOfMovies = 0;
		int numberOfMiniBarItems = 0;
		double moviePrices = 0.0;
		double miniBarPrices = 0.0;

		System.out.print("Number of movies watched: ");
		boolean isOKinput = false;
		while (!isOKinput) {
			try {
				nummberOfMovies = input.nextInt();
				isOKinput = true;
				moviePrices = nummberOfMovies * 10;
			} catch (InputMismatchException e) {
				System.out.println("Input must be an integer.");
				break;
			}
		}

		isOKinput = false;
		System.out.print("Number of Mini bar items taken: ");
		while (!isOKinput) {
			try {
				numberOfMiniBarItems = input.nextInt();
				isOKinput = true;
				miniBarPrices = numberOfMiniBarItems * 5;
			} catch (InputMismatchException e) {
				System.out.println("Input must be an integer.");
				break;
			}

		}
		System.out.println();
		System.out.println("Movies: \t\t " + nummberOfMovies + " * $10.00 = " + "\t" + moviePrices);
		System.out.println("Mini bar Items:  \t " + numberOfMiniBarItems + " * $5.00 = " + "\t" + miniBarPrices);
		return moviePrices + miniBarPrices;
	}
}
