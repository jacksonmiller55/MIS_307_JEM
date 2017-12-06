import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

/**
 * Code for HotelManagementSystem This class provides the main interface that
 * the user interacts with in order to select a room number and date that they
 * would like to book, check to see if it is vacant, deselect, check out, print
 * a variety of reports.
 * 
 * @date 12/5/2017
 * 
 * @author Jackson Miller
 * @author Madison Fisher
 * @author Elias VanHorn
 */
public class HotelManagmentSystem {
	static int sMonth;
	static int sDay;
	static int sRoom;

	/**
	 * This is the main method that calls all other methods for the user. It runs
	 * until the user wishes to quit the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String selection = null;
		Scanner input = new Scanner(System.in);
		boolean firstRun = true;
		boolean continueToRun = false;
		Calendar year = loadData();
		AccountsRecievable annualCharges = loadRecordedRoomCharges();

		while (!continueToRun) {
			// Adds 2 lines for spacing after the first time the program is run.
			if (!firstRun) {
				System.out.println();
				System.out.println();
			}
			selection = printSelectionOptions(input);

			if (selection.equals("Q")) {
				continueToRun = quitProgram(year, annualCharges);
			} else if (selection.equals("B")) {
				bookSpecificRoomNumber(year, input);
			} else if (selection.equals("D")) {
				deselctSpecificRoomNumber(year, input);
			} else if (selection.equals("T")) {
				bookRoomType(year, input);
			} else if (selection.equals("C")) {
				checkout(year, input, annualCharges);
			} else if (selection.equals("M")) {
				roomCleaned(year, input);
			} else if (selection.equals("L")) {
				listOfRoomsToBeCleaned(year, input);
			} else if (selection.equals("V")) {
				vacancyReport(year, input);
			} else if (selection.equals("E")) {
				reportedEarnings(input, annualCharges);
			}

			// Tells the program that this is no longer the first run.
			firstRun = false;
		}
		input.close();

	}

	/**
	 * Loads the saved data from "roomsBooked.txt". Inserts the booked rooms into a
	 * new Calendar object. Creates a new Calendar object if no data is loaded in.
	 * 
	 * @return new Calendar year.
	 */
	private static Calendar loadData() {
		Calendar year = new Calendar();
		File saveRooms = new File("src\\roomsBooked.txt");
		try {
			@SuppressWarnings("resource")
			Scanner load = new Scanner(saveRooms);
			while (load.hasNextLine()) {
				sMonth = load.nextInt();
				sDay = load.nextInt();
				sRoom = load.nextInt();
				String isBooked = load.next();

				if (isBooked.trim().equals("B")) {
					year.bookRoom(sMonth, sDay, sRoom);
				}
				if (isBooked.trim().equals("D")) {
					year.dirtyRoom(sMonth, sDay, sRoom);
				}
			}
			return year;

		} catch (FileNotFoundException e) {
			System.out.println("Error: The rooms were not initiated.");
			return year;
		}
	}

	/**
	 * Loads the saved data from "annualCharges.txt". Inserts the booked rooms into
	 * a new AccountsRecievable object. Creates a new AccountsRecievable object if
	 * no data is loaded in.
	 * 
	 * @return new AccountsRecievable annualCharges.
	 */
	private static AccountsRecievable loadRecordedRoomCharges() {
		AccountsRecievable annualCharges = new AccountsRecievable();
		File save = new File("src\\annualCharges.txt");
		try {
			@SuppressWarnings("resource")
			Scanner load = new Scanner(save);
			while (load.hasNextLine()) {
				sMonth = load.nextInt();
				sDay = load.nextInt();
				sRoom = load.nextInt();
				double charge = load.nextDouble();
				annualCharges.setChargesForRoom(sMonth, sDay, sRoom, charge);
			}
			return annualCharges;

		} catch (FileNotFoundException e) {
			System.out.println("Error: The charges were not initiated.");
			return annualCharges;
		}
	}

	/**
	 * Quits the program and saves the all of the room states if they are booked or
	 * not. Rooms and charges are saved.
	 * 
	 * @return True to quit the program.
	 */
	public static boolean quitProgram(Calendar year, AccountsRecievable annualCharges) {
		System.out.println("You have quit the program.");
		return (saveRooms(year) && (saveAnnualCharges(annualCharges)));
	}

	/**
	 * Saves the rooms and the states to "roomsBooked.txt".
	 * 
	 * @param year
	 *            (Calendar) object that contains the booked and unbooked rooms.
	 * @return true if file saved correctly.
	 */
	public static boolean saveRooms(Calendar year) {
		File save = new File("src\\roomsBooked.txt");
		try {
			PrintWriter out = new PrintWriter(save);
			for (int month = 0; month < 12; month++) {
				for (int day = 0; day < year.getNumDays(month); day++) {
					for (int room = 101; room < 153; room++) {
						if (year.checkRoomAvailable(month, day, room) == 0) {
							out.print(month + "\t" + day + "\t" + room + "\t" + "A");
						} else if (year.checkRoomAvailable(month, day, room) == -1) {
							out.print(month + "\t" + day + "\t" + room + "\t" + "D");
						} else {
							out.print(month + "\t" + day + "\t" + room + "\t" + "B");
						}
						if (!(month == 11 && day == 30 && room == 152)) {
							out.println();
						}
					}
				}
			}
			out.close();

			return true;
		} catch (FileNotFoundException e) {
			System.out.println("Print writer did not output to a file for the calendar.");
			return false;
		}
	}

	/**
	 * Saves the total charges that were assigned to each room to
	 * "annualCharges.txt".
	 * 
	 * @param annualCharges
	 *            AccountsRevievable Object that stores the charges assigned to each
	 *            room.
	 * @return True if the charges were successfully saved.
	 */
	public static boolean saveAnnualCharges(AccountsRecievable annualCharges) {
		File save = new File("src\\annualCharges.txt");
		try {
			PrintWriter out = new PrintWriter(save);
			for (int month = 0; month < 12; month++) {
				for (int day = 0; day < annualCharges.getNumDays(month); day++) {
					for (int room = 101; room < 153; room++) {
						double charge = annualCharges.getChargesForRoom(month, day, room);
						if (charge == 0) {
							out.print(month + "\t" + day + "\t" + room + "\t" + charge);
						} else if (charge != 0) {
							out.print(month + "\t" + day + "\t" + room + "\t" + charge);
						}
						if (!(month == 11 && day == 30 && room == 152)) {
							out.println();
						}
					}
				}
			}
			out.close();

			return true;
		} catch (FileNotFoundException e) {
			System.out.println("Print writer did not output to a file for the annual charges.");
			return false;
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
		System.out.println("(M) Maintenance reporting clean room");
		System.out.println("(L) List of rooms that need cleaned");
		System.out.println("(V) Vaccancy Report");
		System.out.println("(E) Earnings Report");
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
					|| (selection.toUpperCase().equals("C")) || (selection.toUpperCase().equals("M"))
					|| (selection.toUpperCase().equals("L")) || (selection.toUpperCase().equals("V"))
					|| (selection.toUpperCase().equals("E"))) {
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

		double isRoomAvailable;
		isRoomAvailable = year.checkRoomAvailable(sMonth, sDay, sRoom);

		if (isRoomAvailable == 0) {
			year.bookRoom(sMonth, sDay, sRoom);

			System.out.printf("Room %d was booked for %d-%d", sRoom, sMonth + 1, sDay + 1);

		} else if (isRoomAvailable == -1) {
			System.out.printf("Room %d is still dirty for %d-%d", sRoom, sMonth + 1, sDay + 1);
		} else {
			System.out.printf("Room %d is currently booked and unavailable for %d-%d", sRoom, sMonth + 1, sDay + 1);
		}
		saveRooms(year);
	}

	/**
	 * Allows the user to de-select a room so that it is available to book in the
	 * case that they accidentally booked the wrong room.
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

		double isRoomAvailable;
		isRoomAvailable = year.checkRoomAvailable(sMonth, sDay, sRoom);

		if (isRoomAvailable == -1) {
			System.out.printf(
					"Room %d cannot be unselected it is still dirty for %d-%d please select a different room and day",
					sRoom, sMonth + 1, sDay + 1);
			System.out.println();
			return false;
		}
		if (isRoomAvailable == 0) {
			year.unbookRoom(sMonth, sDay, sRoom);
			System.out.printf(
					"Room %d not unselected because it was not previously booked for %d-%d please select a different room and day",
					sRoom, sMonth + 1, sDay + 1);
			System.out.println();
			saveRooms(year);
			return true;
		} else {
			year.unbookRoom(sMonth, sDay, sRoom);
			System.out.printf("Room %d was unselected for %d-%d", sRoom, sMonth + 1, sDay + 1);
			System.out.println();
			saveRooms(year);
			return true;
		}

	}

	/**
	 * Gets the user input for the month that the user would like to book. Making
	 * sure that it is an acceptable entry.
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

		String enteredText = input.next();
		while (!isOKinput) {
			if (isNumber(enteredText)) {
				sMonth = Integer.parseInt(enteredText);

				if ((sMonth >= 1) && (sMonth <= 12)) {
					sMonth -= 1;
					isOKinput = true;
				} else {
					System.out.println("The month must be between the numbers 1 and 12");
					enteredText = input.next();
				}
			} else {
				System.out.println("The month must be between the numbers 1 and 12");
				enteredText = input.next();
			}
		}
		return sMonth;

	}

	/**
	 * Gets the user input for the day that the user would like to book. Making sure
	 * that it is an acceptable entry.
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
		// months with the amount of days to match the arrays
		// 0,2,4,6,7,9,11 = 31
		// 1 = 28
		// 3,5,8,10 =30

		System.out.print("Day: ");
		boolean isOKinput = false;

		if ((sMonth == 0) && (sMonth == 2) && (sMonth == 4) && (sMonth == 6) && (sMonth == 7) && (sMonth == 9)
				&& (sMonth == 11)) {
			String enteredText = input.next();
			while (!isOKinput) {
				if (isNumber(enteredText)) {
					sDay = Integer.parseInt(enteredText);
					if ((sDay >= 1) && (sDay <= 31)) {
						sDay -= 1;
						isOKinput = true;
					} else {
						System.out.println("The day must be between the numbers 1 and 31");
						enteredText = input.next();
					}
				} else {
					System.out.println("The day must be between the numbers 1 and 31");
					enteredText = input.next();
				}
			}
			return sDay;
		} else if ((sMonth == 3) && (sMonth == 5) && (sMonth == 8) && (sMonth == 10)) {
			String enteredText = input.next();
			while (!isOKinput) {
				if (isNumber(enteredText)) {
					sDay = Integer.parseInt(enteredText);
					if ((sDay >= 1) && (sDay <= 30)) {
						sDay -= 1;
						isOKinput = true;
					} else {
						System.out.println("The day must be between the numbers 1 and 30");
						enteredText = input.next();
					}
				} else {
					System.out.println("The day must be between the numbers 1 and 30");
					enteredText = input.next();
				}
			}
			return sDay;
		} else {
			String enteredText = input.next();
			while (!isOKinput) {
				if (isNumber(enteredText)) {
					sDay = Integer.parseInt(enteredText);
					if ((sDay >= 1) && (sDay <= 28)) {
						sDay -= 1;
						isOKinput = true;
					} else {
						System.out.println("The day must be between the numbers 1 and 28");
						enteredText = input.next();
					}
				} else {
					System.out.println("The day must be between the numbers 1 and 28");
					enteredText = input.next();
				}
			}
			return sDay;
		}
	}

	/**
	 * Gets the room input from the user. Making sure that it is an acceptable
	 * entry.
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

		String enteredText = input.next();
		while (!isOKinput) {
			if (isNumber(enteredText)) {
				sRoom = Integer.parseInt(enteredText);
				if ((sRoom >= 101) && (sRoom <= 152)) {
					isOKinput = true;
				} else {
					System.out.println("The room number must be between the numbers 101 and 152");
					enteredText = input.next();
				}
			} else {
				System.out.println("The room number must be between the numbers 101 and 152");
				enteredText = input.next();
			}
		}
		return sRoom;
	}

	/**
	 * Checks to make sure that the entry is a number.
	 * 
	 * @param text
	 *            entered text by the user.
	 * 
	 * @return True if the text entered is a number.
	 */
	public static boolean isNumber(String text) {
		boolean isaNumber = false;
		for (int i = 0; i < text.length(); i++) {
			char index = text.charAt(i);
			if (Character.isDigit(index)) {
				isaNumber = true;
			} else {
				isaNumber = false;
			}
		}
		return isaNumber;
	}

	/**
	 * Gets the user input for the room that the user would like to book.
	 * 
	 * @param year
	 *            Calendar object. Used to reference month, date, and room number.
	 * @param input
	 *            Scanner(System.in)
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

		if (selection.toUpperCase().equals("D")) {
			System.out.println("You have selected Queen Double");

			sRoom = 101;
			double isRoomAvailable = Double.MAX_VALUE;
			while ((isRoomAvailable != 0) && (sRoom <= 120)) {
				isRoomAvailable = year.checkRoomAvailable(sMonth, sDay, sRoom);
				sRoom += 1;
			}
			if ((sRoom == 121) && (isRoomAvailable != 0)) {
				System.out.println("There are no rooms of this style available.");
			} else {
				sRoom -= 1;
				year.bookRoom(sMonth, sDay, sRoom);
				System.out.printf("Room %d was booked for %d-%d", sRoom, sMonth + 1, sDay + 1);
			}

		} else if (selection.toUpperCase().equals("S")) {
			System.out.println("You have selected Single King");

			sRoom = 121;
			double isRoomAvailable = Double.MAX_VALUE;
			while ((isRoomAvailable != 0) && (sRoom <= 140)) {
				isRoomAvailable = year.checkRoomAvailable(sMonth, sDay, sRoom);
				sRoom += 1;
			}
			if ((sRoom == 141) && (isRoomAvailable != 0)) {
				System.out.println("There are no rooms of this style available.");
			} else {
				sRoom -= 1;
				year.bookRoom(sMonth, sDay, sRoom);
				System.out.printf("Room %d was booked for %d-%d", sRoom, sMonth + 1, sDay + 1);
			}

		} else if (selection.toUpperCase().equals("K")) {
			System.out.println("You have selected Kitchen Suite");

			sRoom = 141;
			double isRoomAvailable = Double.MAX_VALUE;
			while ((isRoomAvailable != 0) && (sRoom <= 150)) {
				isRoomAvailable = year.checkRoomAvailable(sMonth, sDay, sRoom);
				sRoom += 1;
			}
			if ((sRoom == 151) && (isRoomAvailable != 0)) {
				System.out.println("There are no rooms of this style available.");
			} else {
				sRoom -= 1;
				year.bookRoom(sMonth, sDay, sRoom);
				System.out.printf("Room %d was booked for %d-%d", sRoom, sMonth + 1, sDay + 1);
			}

		} else if (selection.toUpperCase().equals("L")) {
			System.out.println("You have selected Luxury Suite");

			sRoom = 151;
			double isRoomAvailable = Double.MAX_VALUE;
			while ((isRoomAvailable != 0) && (sRoom <= 152)) {
				isRoomAvailable = year.checkRoomAvailable(sMonth, sDay, sRoom);
				sRoom += 1;
			}
			if ((sRoom == 153) && (isRoomAvailable != 0)) {
				System.out.println("There are no rooms of this style available.");
			} else {
				sRoom -= 1;
				year.bookRoom(sMonth, sDay, sRoom);
				System.out.printf("Room %d was booked for %d-%d", sRoom, sMonth + 1, sDay + 1);
			}

		}
		saveRooms(year);

	}

	/**
	 * Checks the user out of the room and creates a that adds up the extra expenses
	 * and price of the room.
	 * 
	 * @param year
	 *            (Calendar) object that contains the booked and unbooked rooms.
	 * @param input
	 *            (Scanner) that takes in user input for room to checkout.
	 * @return Total price of room to checkout.
	 */
	public static double checkout(Calendar year, Scanner input, AccountsRecievable annualCharges) {
		int numberOfMovies = 0;
		int numberOfMiniBarItems = 0;
		double moviePrices = 0.0;
		double miniBarPrices = 0.0;

		sMonth = monthInput(input);

		sDay = dayInput(input);

		sRoom = roomInput(input);

		double roomPrice = year.getRoomPrice(sMonth, sDay, sRoom);

		if (needsRoomCleaning(year, input)) {
			System.out.print("Number of movies watched: ");
			boolean isOKinput = false;
			String enteredMovies = input.next();
			while (!isOKinput) {
				if (isNumber(enteredMovies)) {
					numberOfMovies = Integer.parseInt(enteredMovies);
					isOKinput = true;
				} else {
					System.out.println("Input must be an integer.");
					enteredMovies = input.next();
				}

			}

			isOKinput = false;
			System.out.print("Number of Mini bar items taken: ");
			String enteredMinibarItems = input.next();
			while (!isOKinput) {
				if (isNumber(enteredMinibarItems)) {
					numberOfMiniBarItems = Integer.parseInt(enteredMinibarItems);
					isOKinput = true;
				} else {
					System.out.println("Input must be an integer.");
					enteredMinibarItems = input.next();
				}
			}

			moviePrices = numberOfMovies * 10;
			miniBarPrices = numberOfMiniBarItems * 5;
			System.out.println();
			System.out.println("_______________________________________________________");
			System.out.println("                      Reciept");
			System.out.println("_______________________________________________________");
			System.out.println("Movies: \t\t " + numberOfMovies + " * $10.00" + "\t= \t" + moviePrices);
			System.out.println("Mini bar Items:  \t " + numberOfMiniBarItems + " * $5.00 " + "\t= \t" + miniBarPrices);
			System.out.println("Room Price: \t\t\t\t= \t" + roomPrice);
			DecimalFormat decformat = new DecimalFormat("#.##");

			double totalPriceBeforeTax = moviePrices + miniBarPrices + roomPrice;
			double totalPriceAfterTax = totalPriceBeforeTax * 1.07;

			totalPriceAfterTax = Double.valueOf(decformat.format(totalPriceAfterTax));

			double tax = totalPriceAfterTax - totalPriceBeforeTax;

			tax = Double.valueOf(decformat.format(tax));

			System.out.println("Sales Tax:  \t\t " + totalPriceBeforeTax + " * 0.07  = " + "\t" + tax);
			System.out.println("_______________________________________________________");
			System.out.println("Total Price: \t\t\t\t\t" + totalPriceAfterTax);
			System.out.println("_______________________________________________________");
			printReciept(numberOfMovies, numberOfMiniBarItems, roomPrice, totalPriceBeforeTax, tax, totalPriceAfterTax);

			saveRooms(year);
			annualCharges.setChargesForRoom(sMonth, sDay, sRoom, totalPriceAfterTax);
			return totalPriceAfterTax;
		} else
			return 0;
	}

	/**
	 * Sets room in a dirty state to show that it needs cleaning.
	 * 
	 * @param year
	 *            Calendar object. Used to reference month, date, and room number.
	 * @param input
	 *            Scanner(System.in)
	 * @return true is the room is successfully set to a dirty state. false if still
	 *         set to clean.
	 */
	public static boolean needsRoomCleaning(Calendar year, Scanner input) {

		double isRoomAvailable;
		isRoomAvailable = year.checkRoomAvailable(sMonth, sDay, sRoom);

		if (isRoomAvailable == -1) {
			System.out.printf("Error: Room %d was already set for cleaning for %d-%d", sRoom, sMonth + 1, sDay + 1);
			System.out.println();
			return false;

		}
		if (isRoomAvailable == 0) {
			year.dirtyRoom(sMonth, sDay, sRoom);
			System.out.printf("Error: Room %d was not set for cleaning for %d-%d because it is not dirty.", sRoom,
					sMonth + 1, sDay + 1);
			System.out.println();
			return false;
		} else {
			year.dirtyRoom(sMonth, sDay, sRoom);

			System.out.printf("Room %d was set for cleaning for %d-%d", sRoom, sMonth + 1, sDay + 1);
			System.out.println();
			saveRooms(year);
			return true;
		}
	}

	/**
	 * Prints a receipt for the customer in an html file. It also opens this html
	 * file "reciept.html".
	 * 
	 * @param numberOfMovies
	 *            The number of movies watched by the customer.
	 * @param numberOfMiniBarItems
	 *            The number of mini bar items.
	 * @param roomPrice
	 *            The initial price of the room.
	 * @param totalPriceBeforeTax
	 *            the total price of the rooms and the extra expenses before tax.
	 * @param tax
	 *            The amount of tax to be multiplied to totalPriceBeforeTax
	 * @param totalPriceAfterTax
	 *            The total charge of the room expenses and the added value of tax.
	 */
	public static void printReciept(int numberOfMovies, int numberOfMiniBarItems, double roomPrice,
			double totalPriceBeforeTax, double tax, double totalPriceAfterTax) {
		int room = sRoom + 1;
		int day = sDay + 1;
		int month = sDay + 1;
		double moviePrices;
		double miniBarPrices;
		moviePrices = numberOfMovies * 10;
		miniBarPrices = numberOfMiniBarItems * 5;

		File reciept = new File("src\\reciept.html");
		FileWriter fstream = null;

		StringBuilder recieptBuilder = new StringBuilder();
		recieptBuilder.append("<html style=\"background-color:beige;\">");
		recieptBuilder.append("<head>");
		recieptBuilder.append("<title>Reciept</title>");
		recieptBuilder.append("</u>");
		recieptBuilder.append("</head>");
		recieptBuilder.append("<body>");
		recieptBuilder.append("<center>");
		recieptBuilder.append(
				"<h1 style=\"color:darkred; font-style: italic; font-size: 40\"; >Thank you for staying with us.</h1>");
		recieptBuilder.append("<u>");
		recieptBuilder.append("<h2 style=\"font-weight: 800; font-family:sans-serif;\">Reciept</h2>");
		recieptBuilder.append("</u>");
		recieptBuilder.append("<h2 style=\"color:darkred;font-family:sans-serif;\">Room: " + room + "  Date: " + month
				+ "\\" + day + "\\2017</h2>");
		recieptBuilder.append("<div>");
		recieptBuilder.append("<h3 style=\"font-weight: 800; font-family:sans-serif;\">Movies</h3>");
		recieptBuilder.append("<div>");
		recieptBuilder.append("<h4 style=\"font-family:sans-serif;\"> Number Watched: " + numberOfMovies
				+ "* $10.00 = $" + moviePrices + "</h4>");
		recieptBuilder.append("</div>");
		recieptBuilder.append("</div>");
		recieptBuilder.append("<div>");
		recieptBuilder.append("<h3 style=\"font-weight: 800; font-family:sans-serif;\">Mini Bar Items</h3>");
		recieptBuilder.append("<div>");
		recieptBuilder.append("<h4 style=\"font-family:sans-serif;\"> Number of Mini Bar Items: " + numberOfMiniBarItems
				+ " * $5.00 = $" + miniBarPrices + "0 </h4>");
		recieptBuilder.append("</div>");
		recieptBuilder.append("</div>");
		recieptBuilder.append("<div>");
		recieptBuilder.append("<h3 style=\"font-weight: 800; font-family:sans-serif;\";>Room charge</h3>");
		recieptBuilder.append("<div>");
		recieptBuilder.append("<h4 style=\"font-family:sans-serif;\">Amount: $" + roomPrice + "</h4>");
		recieptBuilder.append("</div>");
		recieptBuilder.append("</div>");
		recieptBuilder.append("<div>");
		recieptBuilder.append("<h3 style=\"font-weight: 800; font-family:sans-serif;\";>Sales Tax</h3>");
		recieptBuilder.append("<div>");
		recieptBuilder.append(
				"<h4 style=\"font-family:sans-serif;\">$" + totalPriceBeforeTax + " * 0.07 = $" + tax + "</h4>");
		recieptBuilder.append("</div>");
		recieptBuilder.append("</div>");
		recieptBuilder.append("<div>");
		recieptBuilder.append("<u>");
		recieptBuilder.append("<h3 style=\"color:darkred; font-weight:900; font-family:sans-serif;\">Total due:</h3>");
		recieptBuilder.append("<div>");
		recieptBuilder.append("<h4 style=\"color:darkred; font-weight: 900;font-family:sans-serif;\">$"
				+ totalPriceAfterTax + "</h4>");
		recieptBuilder.append("</div>");
		recieptBuilder.append("</u>");
		recieptBuilder.append("</div>");
		recieptBuilder.append("</center>");
		recieptBuilder.append("</body>");
		recieptBuilder.append("</html>");

		try {
			fstream = new FileWriter(reciept);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(recieptBuilder.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Desktop.getDesktop().browse(reciept.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
	}

	/**
	 * Sets a specific room number to clean for the given month, date, and room
	 * number.
	 * 
	 * @param year
	 *            Calendar object. Used to reference month, date, and room number.
	 * @param input
	 *            Scanner(System.in);
	 */
	public static void roomCleaned(Calendar year, Scanner input) {

		sMonth = monthInput(input);

		sDay = dayInput(input);

		sRoom = roomInput(input);

		double isRoomAvailable;
		isRoomAvailable = year.checkRoomAvailable(sMonth, sDay, sRoom);

		if (isRoomAvailable == -1) {
			year.cleanRoom(sMonth, sDay, sRoom);
			System.out.printf("Room %d was cleaned for %d-%d", sRoom, sMonth + 1, sDay + 1);

		} else if (isRoomAvailable == 0) {
			System.out.printf("Room %d is already clean and available to be booked for %d-%d", sRoom, sMonth + 1,
					sDay + 1);
		} else {
			System.out.printf("Room %d currently booked and unavailable for %d-%d", sRoom, sMonth + 1, sDay + 1);
		}
		saveRooms(year);
	}

	/**
	 * Lists the rooms that need to be cleaned. It prints these rooms out on a
	 * maintenance list in an html file. This html file is called
	 * "cleaningList.html".
	 * 
	 * @param year
	 *            Calandar object
	 * @param input
	 *            Scanner (Scanner.in)
	 */
	public static void listOfRoomsToBeCleaned(Calendar year, Scanner input) {

		sMonth = monthInput(input);

		sDay = dayInput(input);

		int month = sMonth + 1;
		int day = sDay + 1;

		ArrayList<Integer> dirtyRooms = new ArrayList<Integer>();

		for (int room = 101; room < 153; room++) {
			if (year.checkRoomAvailable(sMonth, sDay, room) == -1) {
				dirtyRooms.add(room);
			}
		}

		File cleaningList = new File("src\\cleaningList.html");
		FileWriter fstream = null;

		StringBuilder cleaningListBuilder = new StringBuilder();
		cleaningListBuilder.append("<html style=\"background-color:beige;\">");
		cleaningListBuilder.append("<head>");
		cleaningListBuilder.append("<title>Maintenance List</title>");
		cleaningListBuilder.append("</head>");
		cleaningListBuilder.append("<body>");
		cleaningListBuilder.append("<center>");
		cleaningListBuilder.append("<u>");
		cleaningListBuilder
				.append("<h1 style=\"color:darkred; font-style: italic; font-size: 40\"; >Maintenance List</h1>");
		cleaningListBuilder.append("</u>");
		cleaningListBuilder.append("<h2 style=\"font-weight: 800; font-family:sans-serif;\">Dirty Rooms</h2>");
		cleaningListBuilder.append(
				"<h2 style=\"color:darkred;font-family:sans-serif;\">Date: " + month + "\\" + day + "\\2017</h2>");
		cleaningListBuilder.append("<div>");

		for (int j = 0; j < dirtyRooms.size(); j++) {
			cleaningListBuilder.append("<h4 style=\"font-family:sans-serif;\"> Room: " + dirtyRooms.get(j) + " </h4>");
		}

		cleaningListBuilder.append("</div>");
		cleaningListBuilder.append("<div>");
		cleaningListBuilder.append("<u>");
		cleaningListBuilder.append(
				"<h3 style=\"color:darkred; font-weight:900; font-family:sans-serif;\">Please clean as soon as possible.</h3>");
		cleaningListBuilder.append("</u>");
		cleaningListBuilder.append("</div>");
		cleaningListBuilder.append("</center>");
		cleaningListBuilder.append("</body>");
		cleaningListBuilder.append("</html>");

		try {
			fstream = new FileWriter(cleaningList);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(cleaningListBuilder.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Desktop.getDesktop().browse(cleaningList.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();
		saveRooms(year);
	}

	/**
	 * Allows the user to select a vacancy report of their choosing. "M" for a
	 * monthly report. "D" for a daily report.
	 * 
	 * @param year
	 *            Calendar object. Used to reference month, date, and room number.
	 * @param input
	 *            Scanner (Scanner.in)
	 */
	public static void vacancyReport(Calendar year, Scanner input) {
		String selection = null;
		boolean correctInput = false;

		System.out.println("(D) Day Vacancy Report");
		System.out.println("(M) Month Vacancy Report");

		selection = input.next();

		while (!correctInput) {

			if (selection.length() > 2) {
				System.out.println("Please enter only one character to select the appropriat option.");
				selection = input.next();
			} else if (!Character.isAlphabetic(selection.charAt(0))) {
				System.out.println(
						"Please enter only the selected character to select the appropriat option. Numbers will not be accepted.");
				selection = input.next();
			} else if ((selection.toUpperCase().equals("D")) || (selection.toUpperCase().equals("M"))) {
				correctInput = true;
			} else {
				System.out.println("The Character must match one of the appropriat options.");
				selection = input.next();
			}
		}
		if (selection.toUpperCase().equals("M")) {
			monthVacancyReport(year, input);
		} else {
			dayVacancyReport(year, input);
		}
	}

	/**
	 * Generates a vacancy report for the selected day. Shows vacant rooms in html
	 * file "dayVacancyReport.html"
	 * 
	 * @param year
	 *            Calendar object. Used to reference month, date, and room number.
	 * @param input
	 *            Scanner (Scanner.in);
	 */
	public static void dayVacancyReport(Calendar year, Scanner input) {
		File dayVacancyReport = new File("src\\dayVacancyReport.html");
		FileWriter fstream = null;
		DecimalFormat decformat = new DecimalFormat("#.##");
		double occupancyRate = 0;
		sMonth = monthInput(input);
		sDay = dayInput(input);

		StringBuilder dayVacancyReportBuilder = new StringBuilder();
		dayVacancyReportBuilder.append("<html style=\"background-color:beige;\">");
		dayVacancyReportBuilder.append("<head>");
		dayVacancyReportBuilder.append("<title>Vacancy List</title>");
		dayVacancyReportBuilder.append("</head>");
		dayVacancyReportBuilder.append("<body>");
		dayVacancyReportBuilder.append("<center>");
		dayVacancyReportBuilder.append("<u>");
		dayVacancyReportBuilder
				.append("<h1 style=\"color:darkred; font-style: italic; font-size: 40\"; >Vacancy List</h1>");
		dayVacancyReportBuilder.append("</u>");

		ArrayList<Integer> roomStatsBooked = new ArrayList<Integer>();
		ArrayList<Integer> roomStatsVacant = new ArrayList<Integer>();

		for (int room = 101; room < 153; room++) {
			if (year.checkRoomAvailable(sMonth, sDay, room) != 0) {
				roomStatsBooked.add(room);
			} else if (year.checkRoomAvailable(sMonth, sDay, room) == 0) {
				roomStatsVacant.add(room);
			}
		}
		occupancyRate = ((double) roomStatsBooked.size() / (double) 152) * (double) 100;
		occupancyRate = Double.valueOf(decformat.format(occupancyRate));

		dayVacancyReportBuilder.append(
				"<h2 style=\"font-weight: 800; font-family:sans-serif;\">Occupancy Rate " + occupancyRate + "%</h2>");

		boolean queenHeaderCalled = false;
		boolean kingHeaderCalled = false;
		boolean kitchenetteHeaderCalled = false;
		boolean suiteHeaderCalled = false;
		int dayToPrint = sDay + 1;
		int monthToPrint = sMonth + 1;
		dayVacancyReportBuilder.append("<h2 style=\"color:darkred;font-family:sans-serif;\">Vacant rooms for: "
				+ monthToPrint + "\\" + dayToPrint + "\\2017</h2>");

		for (int i = 0; i < roomStatsVacant.size(); i++) {
			if (roomStatsVacant.get(i) < 121) {
				if (!queenHeaderCalled) {
					dayVacancyReportBuilder.append("<div>");
					dayVacancyReportBuilder.append("<u>");
					dayVacancyReportBuilder.append(
							"<h3 style=\"font-weight: 800; font-family:sans-serif;\">Double-Queen $129.99</h3>");
					dayVacancyReportBuilder.append("</u>");
					dayVacancyReportBuilder.append("</div>");
					queenHeaderCalled = true;
				}
			} else if (roomStatsVacant.get(i) < 141) {
				if (!kingHeaderCalled) {
					dayVacancyReportBuilder.append("<div>");
					dayVacancyReportBuilder.append("<u>");
					dayVacancyReportBuilder.append(
							"<h3 style=\"font-weight: 800; font-family:sans-serif;\">Single-King  $139.99</h3>");
					dayVacancyReportBuilder.append("</u>");
					dayVacancyReportBuilder.append("</div>");
					kingHeaderCalled = true;
				}
			} else if (roomStatsVacant.get(i) < 151) {
				if (!kitchenetteHeaderCalled) {
					dayVacancyReportBuilder.append("<div>");
					dayVacancyReportBuilder.append("<u>");
					dayVacancyReportBuilder.append(
							"<h3 style=\"font-weight: 800; font-family:sans-serif;\">Kitchenette-Suite  $159.99</h3>");
					dayVacancyReportBuilder.append("</u>");
					dayVacancyReportBuilder.append("</div>");
					kitchenetteHeaderCalled = true;
				}
			} else {
				if (!suiteHeaderCalled) {
					dayVacancyReportBuilder.append("<div>");
					dayVacancyReportBuilder.append("<u>");
					dayVacancyReportBuilder.append(
							"<h3 style=\"font-weight: 800; font-family:sans-serif;\">Luxury-Suite  $199.99</h3>");
					dayVacancyReportBuilder.append("</u>");
					dayVacancyReportBuilder.append("</div>");
					suiteHeaderCalled = true;
				}
			}
			dayVacancyReportBuilder.append(
					"<p style=\"font-family:sans-serif; line-height: 70%;\"> Room: " + roomStatsVacant.get(i) + "</p>");
		}
		dayVacancyReportBuilder.append("<div>");
		dayVacancyReportBuilder.append("<u>");
		dayVacancyReportBuilder.append(
				"<h3 style=\"color:darkred; font-weight:900; font-family:sans-serif;\">There were 121 rooms vacant.</h3>");
		dayVacancyReportBuilder.append(
				"<h3 style=\"color:darkred; font-weight:900; font-family:sans-serif;\">There were 31 rooms booked.</h3>");
		dayVacancyReportBuilder.append("</u>");
		dayVacancyReportBuilder.append("</div>");
		dayVacancyReportBuilder.append("<hr style=\"font-weight:900;\">");
		dayVacancyReportBuilder.append("</center>");
		dayVacancyReportBuilder.append("</body>");
		dayVacancyReportBuilder.append("</html>");

		try {
			fstream = new FileWriter(dayVacancyReport);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(dayVacancyReportBuilder.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Desktop.getDesktop().browse(dayVacancyReport.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		saveRooms(year);
	}

	/**
	 * Generates a vacancy report for the selected day. Shows vacant rooms in html
	 * file "monthVacancyReport.html".
	 * 
	 * @param year
	 *            Calendar object. Used to reference month, date, and room number.
	 * @param input
	 *            Scanner (Scanner.in)
	 */
	public static void monthVacancyReport(Calendar year, Scanner input) {
		File monthVacancyReport = new File("src\\monthVacancyReport.html");
		FileWriter fstream = null;
		DecimalFormat decformat = new DecimalFormat("#.##");
		double occupancyRate = 0;
		sMonth = monthInput(input);

		StringBuilder monthVacancyReportBuilder = new StringBuilder();
		monthVacancyReportBuilder.append("<html style=\"background-color:beige;\">");
		monthVacancyReportBuilder.append("<head>");
		monthVacancyReportBuilder.append("<title>Vacancy List</title>");
		monthVacancyReportBuilder.append("</head>");
		monthVacancyReportBuilder.append("<body>");
		monthVacancyReportBuilder.append("<center>");
		monthVacancyReportBuilder.append("<u>");
		monthVacancyReportBuilder
				.append("<h1 style=\"color:darkred; font-style: italic; font-size: 40\"; >Vacancy List</h1>");
		monthVacancyReportBuilder.append("</u>");

		for (int day = 0; day < year.getNumDays(sMonth); day++) {
			ArrayList<Integer> roomStatsBooked = new ArrayList<Integer>();
			ArrayList<Integer> roomStatsVacant = new ArrayList<Integer>();

			for (int room = 101; room < 153; room++) {
				if (year.checkRoomAvailable(sMonth, day, room) != 0) {
					roomStatsBooked.add(room);
				} else if (year.checkRoomAvailable(sMonth, day, room) == 0) {
					roomStatsVacant.add(room);
				}
			}
			occupancyRate = ((double) roomStatsBooked.size() / (double) 152) * (double) 100;
			occupancyRate = Double.valueOf(decformat.format(occupancyRate));

			monthVacancyReportBuilder.append("<h2 style=\"font-weight: 800; font-family:sans-serif;\">Occupancy Rate "
					+ occupancyRate + "%</h2>");
			boolean queenHeaderCalled = false;
			boolean kingHeaderCalled = false;
			boolean kitchenetteHeaderCalled = false;
			boolean suiteHeaderCalled = false;
			int dayToPrint = day + 1;
			int monthToPrint = sMonth + 1;
			monthVacancyReportBuilder.append("<h2 style=\"color:darkred;font-family:sans-serif;\">Vacant rooms for: "
					+ monthToPrint + "\\" + dayToPrint + "\\2017</h2>");

			for (int i = 0; i < roomStatsVacant.size(); i++) {
				if (roomStatsVacant.get(i) < 121) {
					if (!queenHeaderCalled) {
						monthVacancyReportBuilder.append("<div>");
						monthVacancyReportBuilder.append("<u>");
						monthVacancyReportBuilder.append(
								"<h3 style=\"font-weight: 800; font-family:sans-serif;\">Double-Queen $129.99</h3>");
						monthVacancyReportBuilder.append("</u>");
						monthVacancyReportBuilder.append("</div>");
						queenHeaderCalled = true;
					}
				} else if (roomStatsVacant.get(i) < 141) {
					if (!kingHeaderCalled) {
						monthVacancyReportBuilder.append("<div>");
						monthVacancyReportBuilder.append("<u>");
						monthVacancyReportBuilder.append(
								"<h3 style=\"font-weight: 800; font-family:sans-serif;\">Single-King  $139.99</h3>");
						monthVacancyReportBuilder.append("</u>");
						monthVacancyReportBuilder.append("</div>");
						kingHeaderCalled = true;
					}
				} else if (roomStatsVacant.get(i) < 151) {
					if (!kitchenetteHeaderCalled) {
						monthVacancyReportBuilder.append("<div>");
						monthVacancyReportBuilder.append("<u>");
						monthVacancyReportBuilder.append(
								"<h3 style=\"font-weight: 800; font-family:sans-serif;\">Kitchenette-Suite  $159.99</h3>");
						monthVacancyReportBuilder.append("</u>");
						monthVacancyReportBuilder.append("</div>");
						kitchenetteHeaderCalled = true;
					}
				} else {
					if (!suiteHeaderCalled) {
						monthVacancyReportBuilder.append("<div>");
						monthVacancyReportBuilder.append("<u>");
						monthVacancyReportBuilder.append(
								"<h3 style=\"font-weight: 800; font-family:sans-serif;\">Luxury-Suite  $199.99</h3>");
						monthVacancyReportBuilder.append("</u>");
						monthVacancyReportBuilder.append("</div>");
						suiteHeaderCalled = true;
					}
				}
				monthVacancyReportBuilder.append("<p style=\"font-family:sans-serif;line-height: 70%;\"> Room: "
						+ roomStatsVacant.get(i) + "</p>");
			}
			monthVacancyReportBuilder.append("<div>");
			monthVacancyReportBuilder.append("<u>");
			monthVacancyReportBuilder.append(
					"<h3 style=\"color:darkred; font-weight:900; font-family:sans-serif;\">There were 121 rooms vacant.</h3>");
			monthVacancyReportBuilder.append(
					"<h3 style=\"color:darkred; font-weight:900; font-family:sans-serif;\">There were 31 rooms booked.</h3>");
			monthVacancyReportBuilder.append("</u>");
			monthVacancyReportBuilder.append("</div>");
			monthVacancyReportBuilder.append("<hr style=\"font-weight:900;\">");
		}
		monthVacancyReportBuilder.append("</center>");
		monthVacancyReportBuilder.append("</body>");
		monthVacancyReportBuilder.append("</html>");
		try {
			fstream = new FileWriter(monthVacancyReport);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(monthVacancyReportBuilder.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Desktop.getDesktop().browse(monthVacancyReport.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		saveRooms(year);
	}

	/**
	 * Allows the user to select a earnings report of their choosing. "M" for a
	 * monthly report. "D" for a daily report.
	 * 
	 * @param input Scanner (Scanner.in)
	 * 
	 * @param annualCharges AccountsRecievable Object
	 */
	public static void reportedEarnings(Scanner input, AccountsRecievable annualCharges) {
		String selection = null;
		boolean correctInput = false;

		System.out.println("(D) Day Reported Earnings Report");
		System.out.println("(M) Month Reported Earnings Report");

		selection = input.next();

		while (!correctInput) {

			if (selection.length() > 2) {
				System.out.println("Please enter only one character to select the appropriat option.");
				selection = input.next();
			} else if (!Character.isAlphabetic(selection.charAt(0))) {
				System.out.println(
						"Please enter only the selected character to select the appropriat option. Numbers will not be accepted.");
				selection = input.next();
			} else if ((selection.toUpperCase().equals("D")) || (selection.toUpperCase().equals("M"))) {
				correctInput = true;
			} else {
				System.out.println("The Character must match one of the appropriat options.");
				selection = input.next();
			}
		}
		if (selection.toUpperCase().equals("M")) {
			monthReportedEarningsReport(annualCharges, input);
		} else {
			dayReportedEarningsReport(annualCharges, input);
		}
	}

	/**
	 * Generates a earnings report for the selected day. Prints the earnings in "dayReportedEarningsReport.html".
	 * 
	 * @param year
	 *            Calendar object. Used to reference month, date, and room number.
	 * @param input
	 *            Scanner (Scanner.in);
	 */
	public static void dayReportedEarningsReport(AccountsRecievable annualCharges, Scanner input) {
		File dayReportedEarningsReport = new File("src\\dayReportedEarningsReport.html");
		FileWriter fstream = null;

		sMonth = monthInput(input);
		sDay = dayInput(input);

		StringBuilder dayReportedEarningsReportBuilder = new StringBuilder();
		dayReportedEarningsReportBuilder.append("<html style=\"background-color:beige;\">");
		dayReportedEarningsReportBuilder.append("<head>");
		dayReportedEarningsReportBuilder.append("<title>Earnings Report</h1</title>");
		dayReportedEarningsReportBuilder.append("</head>");
		dayReportedEarningsReportBuilder.append("<body>");
		dayReportedEarningsReportBuilder.append("<center>");
		dayReportedEarningsReportBuilder.append("<u>");
		dayReportedEarningsReportBuilder
				.append("<h1 style=\"color:darkred; font-style: italic; font-size: 40;\">Earnings Report</h1>");
		dayReportedEarningsReportBuilder.append("</u>");

		ArrayList<Integer> paymentsRecievedRoom = new ArrayList<Integer>();
		ArrayList<Double> paymentsRecievedCharge = new ArrayList<Double>();

		for (int room = 101; room < 153; room++) {
			if (annualCharges.getChargesForRoom(sMonth, sDay, room) != 0) {
				paymentsRecievedRoom.add(room);
				paymentsRecievedCharge.add(annualCharges.getChargesForRoom(sMonth, sDay, room));
			}
		}

		dayReportedEarningsReportBuilder
				.append("<h2 style=\"font-weight: 800; font-family:sans-serif;\">Accounts Recievable</h2>");

		boolean queenHeaderCalled = false;
		boolean kingHeaderCalled = false;
		boolean kitchenetteHeaderCalled = false;
		boolean suiteHeaderCalled = false;
		int dayToPrint = sDay + 1;
		int monthToPrint = sMonth + 1;
		dayReportedEarningsReportBuilder.append("<h2 style=\"color:darkred;font-family:sans-serif;\">Payments for: "
				+ monthToPrint + "\\" + dayToPrint + "\\2017</h2>");

		for (int i = 0; i < paymentsRecievedRoom.size(); i++) {
			if (paymentsRecievedRoom.get(i) < 121) {
				if (!queenHeaderCalled) {
					dayReportedEarningsReportBuilder.append("<div>");
					dayReportedEarningsReportBuilder.append("<u>");
					dayReportedEarningsReportBuilder
							.append("<h3 style=\"font-weight: 800; font-family:sans-serif;\">Double-Queen</h3>");
					dayReportedEarningsReportBuilder.append("</u>");
					dayReportedEarningsReportBuilder.append("</div>");
					queenHeaderCalled = true;
				}
			} else if (paymentsRecievedRoom.get(i) < 141) {
				if (!kingHeaderCalled) {
					dayReportedEarningsReportBuilder.append("<div>");
					dayReportedEarningsReportBuilder.append("<u>");
					dayReportedEarningsReportBuilder
							.append("<h3 style=\"font-weight: 800; font-family:sans-serif;\">Single-King</h3>");
					dayReportedEarningsReportBuilder.append("</u>");
					dayReportedEarningsReportBuilder.append("</div>");
					kingHeaderCalled = true;
				}
			} else if (paymentsRecievedRoom.get(i) < 151) {
				if (!kitchenetteHeaderCalled) {
					dayReportedEarningsReportBuilder.append("<div>");
					dayReportedEarningsReportBuilder.append("<u>");
					dayReportedEarningsReportBuilder
							.append("<h3 style=\"font-weight: 800; font-family:sans-serif;\">Kitchenette-Suite</h3>");
					dayReportedEarningsReportBuilder.append("</u>");
					dayReportedEarningsReportBuilder.append("</div>");
					kitchenetteHeaderCalled = true;
				}
			} else {
				if (!suiteHeaderCalled) {
					dayReportedEarningsReportBuilder.append("<div>");
					dayReportedEarningsReportBuilder.append("<u>");
					dayReportedEarningsReportBuilder
							.append("<h3 style=\"font-weight: 800; font-family:sans-serif;\">Luxury-Suite</h3>");
					dayReportedEarningsReportBuilder.append("</u>");
					dayReportedEarningsReportBuilder.append("</div>");
					suiteHeaderCalled = true;
				}
			}
			dayReportedEarningsReportBuilder.append("<p style=\"font-family:sans-serif; line-height: 70%;\"> Room: "
					+ paymentsRecievedRoom.get(i) + " Payment: $" + paymentsRecievedCharge.get(i) + "</p>");
		}
		dayReportedEarningsReportBuilder.append("<div>");
		dayReportedEarningsReportBuilder.append("<u>");

		double totalChargesRecieved = 0;
		for (int j = 0; j < paymentsRecievedCharge.size(); j++) {
			totalChargesRecieved += paymentsRecievedCharge.get(j);
		}
		dayReportedEarningsReportBuilder.append(
				"<h3 style=\"color:darkred; font-weight:900; font-family:sans-serif;\">Total amount recieved = $"
						+ totalChargesRecieved + "</h3>");
		dayReportedEarningsReportBuilder.append("</u>");
		dayReportedEarningsReportBuilder.append("</div>");
		dayReportedEarningsReportBuilder.append("<hr style=\"font-weight:900;\">");
		dayReportedEarningsReportBuilder.append("</center>");
		dayReportedEarningsReportBuilder.append("</body>");
		dayReportedEarningsReportBuilder.append("</html>");

		try {
			fstream = new FileWriter(dayReportedEarningsReport);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(dayReportedEarningsReportBuilder.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Desktop.getDesktop().browse(dayReportedEarningsReport.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		saveAnnualCharges(annualCharges);
	}

	/**
	 * Generates a earnings report for the selected day. Prints the earnings in "monthReportedEarningsReport.html".
	 * 
	 * @param year
	 *            Calendar object. Used to reference month, date, and room number.
	 * @param input
	 *            Scanner (Scanner.in)
	 */
	public static void monthReportedEarningsReport(AccountsRecievable annualCharges, Scanner input) {
		File dayReportedEarningsReport = new File("src\\monthReportedEarningsReport.html");
		FileWriter fstream = null;

		sMonth = monthInput(input);

		StringBuilder dayReportedEarningsReportBuilder = new StringBuilder();
		dayReportedEarningsReportBuilder.append("<html style=\"background-color:beige;\">");
		dayReportedEarningsReportBuilder.append("<head>");
		dayReportedEarningsReportBuilder.append("<title>Earnings Report</h1</title>");
		dayReportedEarningsReportBuilder.append("</head>");
		dayReportedEarningsReportBuilder.append("<body>");
		dayReportedEarningsReportBuilder.append("<center>");
		dayReportedEarningsReportBuilder.append("<u>");
		dayReportedEarningsReportBuilder
				.append("<h1 style=\"color:darkred; font-style: italic; font-size: 40;\">Earnings Report</h1</h1>");
		dayReportedEarningsReportBuilder.append("</u>");
		double totalChargesRecieved = 0;
		for (int day = 0; day < annualCharges.getNumDays(sMonth); day++) {
			ArrayList<Integer> paymentsRecievedRoom = new ArrayList<Integer>();
			ArrayList<Double> paymentsRecievedCharge = new ArrayList<Double>();

			for (int room = 101; room < 153; room++) {
				if (annualCharges.getChargesForRoom(sMonth, day, room) != 0) {
					paymentsRecievedRoom.add(room);
					paymentsRecievedCharge.add(annualCharges.getChargesForRoom(sMonth, day, room));
				}
			}
			if (paymentsRecievedRoom.size() > 0) {
				dayReportedEarningsReportBuilder
						.append("<h2 style=\"font-weight: 800; font-family:sans-serif;\">Accounts Recievable</h2>");

				boolean queenHeaderCalled = false;
				boolean kingHeaderCalled = false;
				boolean kitchenetteHeaderCalled = false;
				boolean suiteHeaderCalled = false;
				int dayToPrint = day + 1;
				int monthToPrint = sMonth + 1;
				dayReportedEarningsReportBuilder
						.append("<h2 style=\"color:darkred;font-family:sans-serif;\">Payments for: " + monthToPrint
								+ "\\" + dayToPrint + "\\2017</h2>");

				for (int i = 0; i < paymentsRecievedRoom.size(); i++) {
					if (paymentsRecievedRoom.get(i) < 121) {
						if (!queenHeaderCalled) {
							dayReportedEarningsReportBuilder.append("<div>");
							dayReportedEarningsReportBuilder.append("<u>");
							dayReportedEarningsReportBuilder.append(
									"<h3 style=\"font-weight: 800; font-family:sans-serif;\">Double-Queen</h3>");
							dayReportedEarningsReportBuilder.append("</u>");
							dayReportedEarningsReportBuilder.append("</div>");
							queenHeaderCalled = true;
						}
					} else if (paymentsRecievedRoom.get(i) < 141) {
						if (!kingHeaderCalled) {
							dayReportedEarningsReportBuilder.append("<div>");
							dayReportedEarningsReportBuilder.append("<u>");
							dayReportedEarningsReportBuilder
									.append("<h3 style=\"font-weight: 800; font-family:sans-serif;\">Single-King</h3>");
							dayReportedEarningsReportBuilder.append("</u>");
							dayReportedEarningsReportBuilder.append("</div>");
							kingHeaderCalled = true;
						}
					} else if (paymentsRecievedRoom.get(i) < 151) {
						if (!kitchenetteHeaderCalled) {
							dayReportedEarningsReportBuilder.append("<div>");
							dayReportedEarningsReportBuilder.append("<u>");
							dayReportedEarningsReportBuilder.append(
									"<h3 style=\"font-weight: 800; font-family:sans-serif;\">Kitchenette-Suite</h3>");
							dayReportedEarningsReportBuilder.append("</u>");
							dayReportedEarningsReportBuilder.append("</div>");
							kitchenetteHeaderCalled = true;
						}
					} else {
						if (!suiteHeaderCalled) {
							dayReportedEarningsReportBuilder.append("<div>");
							dayReportedEarningsReportBuilder.append("<u>");
							dayReportedEarningsReportBuilder.append(
									"<h3 style=\"font-weight: 800; font-family:sans-serif;\">Luxury-Suite</h3>");
							dayReportedEarningsReportBuilder.append("</u>");
							dayReportedEarningsReportBuilder.append("</div>");
							suiteHeaderCalled = true;
						}
					}
					dayReportedEarningsReportBuilder
							.append("<p style=\"font-family:sans-serif; line-height: 70%;\"> Room: "
									+ paymentsRecievedRoom.get(i) + " Payment: $" + paymentsRecievedCharge.get(i)
									+ "</p>");
				}
			}
			dayReportedEarningsReportBuilder.append("<div>");

			for (int j = 0; j < paymentsRecievedCharge.size(); j++) {
				totalChargesRecieved += paymentsRecievedCharge.get(j);
			}

		}
		dayReportedEarningsReportBuilder.append("<u>");
		dayReportedEarningsReportBuilder.append(
				"<h3 style=\"color:darkred; font-weight:900; font-family:sans-serif;\">Total amount recieved to date = $"
						+ totalChargesRecieved + "</h3>");
		dayReportedEarningsReportBuilder.append("</u>");
		dayReportedEarningsReportBuilder.append("</div>");
		dayReportedEarningsReportBuilder.append("<hr style=\"font-weight:900;\">");
		dayReportedEarningsReportBuilder.append("</center>");
		dayReportedEarningsReportBuilder.append("</body>");
		dayReportedEarningsReportBuilder.append("</html>");

		try {
			fstream = new FileWriter(dayReportedEarningsReport);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(dayReportedEarningsReportBuilder.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Desktop.getDesktop().browse(dayReportedEarningsReport.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		saveAnnualCharges(annualCharges);
	}

}
