import java.util.Scanner;
import java.sql.SQLException;
import java.io.IOException;

/**
 * Code for HW5
 * @author Jackson Miller
 */
/**
 * Creates the Car table, insert some data into the table, and drop the table
 * from a database.
 */
// ARGS database.properties
public class HotelManagmentSystem {
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

		String selection = null;
		Scanner input = new Scanner(System.in);
		boolean firstRun = true;
		boolean continueToRun = true;
		Calendar year = new Calendar();
		
		while (continueToRun) {
			// Adds 2 lines for spacing after the first time the program is run.
			if (!firstRun) {
				System.out.println();
				System.out.println();
			}
			selection = printSelectionOptions(input);

			if (selection.equals("Q")) {
				continueToRun = quitProgram();
			} else if (selection.equals("B")) {
				bookSpecificRoomNumber(year, input);
			}else if (selection.equals("D")) {
				deselctSpecificRoomNumber(year, input);
			}else if (selection.equals("T")) {
				bookRoomType(year, input);
			}
			
			// Tells the program that this is no longer the first run.
			firstRun = false;
		}
		input.close();

	}

	public static boolean quitProgram() {
		System.out.println("You have quit the program.");
		return false;
	}

	public static String printSelectionOptions(Scanner input) {
		String selection = null;
		boolean correctInput = false;

		System.out.println("Select from the following options");
		System.out.println("(Q) Quit");
		System.out.println("(B) Book Specific Room Number");
		System.out.println("(T) Book Room Type");
		System.out.println("(D) Deselect Specific Room Number because of error");

		selection = input.next();

		while (!correctInput) {

			if (selection.length() > 2) {
				System.out.println("Please enter only one character to select the appropriat option.");
				selection = input.next();
			} else if (!Character.isAlphabetic(selection.charAt(0))) {
				System.out.println(
						"Please enter only the selected character to select the appropriat option. Numbers will not be accepted.");
				selection = input.next();
			} else if ((selection.toUpperCase().equals("Q")) || (selection.toUpperCase().equals("B")) || (selection.toUpperCase().equals("D")) || (selection.toUpperCase().equals("T"))) {
				correctInput = true;
			} else {
				System.out.println("The Character must match one of the appropriat options.");
				selection = input.next();
			}
		}
		return selection.toUpperCase();
	}

	public static void bookSpecificRoomNumber(Calendar year, Scanner input) {
		int month = 0;
		int day = 0;
		int room = 100;

		month = monthInput(input, month);

		day = dayInput(input, month, day);

		room = roomInput(input, room);

		boolean isRoomAvailable = false;
		isRoomAvailable = year.checkRoomAvailable(month, day, room);

		if (isRoomAvailable) {
			year.bookRoom(month, day, room);
			System.out.printf("Room %d was booked for %d-%d", room, month+1, day+1);

		} else {
			System.out.printf("Room %d is unavailable for %d-%d", room, month+1, day+1);
		}
	}

	public static void deselctSpecificRoomNumber(Calendar year, Scanner input) {
		int month = 0;
		int day = 0;
		int room = 100;

		month = monthInput(input, month);

		day = dayInput(input, month, day);

		room = roomInput(input, room);

		boolean isRoomAvailable = false;
		isRoomAvailable = year.checkRoomAvailable(month, day, room);

		if (!isRoomAvailable) {
			year.unbookRoom(month, day, room);
			System.out.printf("Room %d was unselected for %d-%d", room, month+1, day+1);

		} else {
			System.out.printf("Room %d was not unselected for %d-%d", room, month+1, day+1);
		}
	}
	
	public static int monthInput(Scanner input, int month) {
		System.out.print("Month: ");
		boolean isOKinput = false;

		month = input.nextInt();
		while (!isOKinput) {
			if ((month >= 1) && (month <= 12)) {
				month -= 1;
				isOKinput = true;
			} else {
				System.out.println("The month must be between the numbers 1 and 12");
				month = input.nextInt();
			}
		}
		return month;
	}

	public static int dayInput(Scanner input, int month, int day) {
		System.out.print("Day: ");
		boolean isOKinput = false;
		
		day = input.nextInt();

		// months with the amount of days to match the arrays
		// 0,2,4,6,7,9,11 = 31
		// 1 = 28
		// 3,5,8,10 =30

		if ((month == 0) && (month == 2) && (month == 4) && (month == 6) && (month == 7) && (month == 9) && (month == 11)) {
			while (!isOKinput) {
				if ((day >= 1) && (day <= 31)) {
					day -= 1;
					isOKinput = true;
				} else {
					System.out.println("The day must be between the numbers 1 and 31");
					day = input.nextInt();
				}
			}
			return day;
		}
		
		else if ((month == 3) && (month == 5) && (month == 8) && (month == 10)) {
			while (!isOKinput) {
				if ((day >= 1) && (day <= 30)) {
					day -= 1;
					isOKinput = true;
				} else {
					System.out.println("The day must be between the numbers 1 and 30");
					day = input.nextInt();
				}
			}
			return day;
		}
		else {
			while (!isOKinput) {
				if ((day >= 1) && (day <= 28)) {
					day -= 1;
					isOKinput = true;
				} else {
					System.out.println("The day must be between the numbers 1 and 28");
					day = input.nextInt();
				}
			}
			return day;
		}	
	}
	
	public static int roomInput(Scanner input, int room) {
		System.out.print("Room Number: ");
		boolean isOKinput = false;

		room = input.nextInt();
		while (!isOKinput) {
			if ((room >= 101) && (room <= 152)) {
				isOKinput = true;
			} else {
				System.out.println("The room number must be between the numbers 101 and 152");
				room = input.nextInt();
			}
		}
		return room;
	}
	
	public static void bookRoomType (Calendar year, Scanner input) {
		String selection = null;
		boolean correctInput = false;
		
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
			} else if ((selection.toUpperCase().equals("D")) || (selection.toUpperCase().equals("S")) || (selection.toUpperCase().equals("K")) || (selection.toUpperCase().equals("L"))) {
				correctInput = true;
			} else {
				System.out.println("The Character must match one of the appropriat options.");
				selection = input.next();
			}
		}
		int month = 0;
		int day = 0;

				
		if (selection.equals("D")) {
			System.out.println("You have selected Queen Double");
			month = monthInput(input, month);
			day = dayInput(input, month, day);
			
			int room = 101;
			boolean isRoomAvailable = false;
			while ((!isRoomAvailable) && (room <= 120)) {
				isRoomAvailable = year.checkRoomAvailable(month, day, room);
				room += 1;
			}
			if((room == 121) && (!isRoomAvailable)) {
				System.out.println("There are no rooms of this style available.");
			}
			else {
				room -= 1;
				year.bookRoom(month, day, room);
				System.out.printf("Room %d was booked for %d-%d", room, month+1, day+1);
			}
			
			
		}else if (selection.equals("S")) {
			System.out.println("You have selected Single King");
			month = monthInput(input, month);
			day = dayInput(input, month, day);
						
			int room = 121;
			boolean isRoomAvailable = false;
			while ((!isRoomAvailable) && (room <= 140)) {
				isRoomAvailable = year.checkRoomAvailable(month, day, room);
				room += 1;
			}
			if((room == 141) && (!isRoomAvailable)) {
				System.out.println("There are no rooms of this style available.");
			}
			else {
				room -= 1;
				year.bookRoom(month, day, room);
				System.out.printf("Room %d was booked for %d-%d", room, month+1, day+1);
			}
			
		}else if (selection.equals("K")) {
			System.out.println("You have selected Kitchen Suite");
			month = monthInput(input, month);
			day = dayInput(input, month, day);
			
			int room = 141;
			boolean isRoomAvailable = false;
			while ((!isRoomAvailable) && (room <= 150)) {
				isRoomAvailable = year.checkRoomAvailable(month, day, room);
				room += 1;
			}
			if((room == 151) && (!isRoomAvailable)) {
				System.out.println("There are no rooms of this style available.");
			}
			else {
				room -= 1;
				year.bookRoom(month, day, room);
				System.out.printf("Room %d was booked for %d-%d", room, month+1, day+1);
			}
			
		}else if (selection.equals("L")) {
			System.out.println("You have selected Luxury Suite");
			month = monthInput(input, month);
			day = dayInput(input, month, day);
			
			int room = 151;
			boolean isRoomAvailable = false;
			while ((!isRoomAvailable) && (room <= 152)) {
				isRoomAvailable = year.checkRoomAvailable(month, day, room);
				room += 1;
			}
			if((room == 153) && (!isRoomAvailable)) {
				System.out.println("There are no rooms of this style available.");
			}
			else {
				room -= 1;
				year.bookRoom(month, day, room);
				System.out.printf("Room %d was booked for %d-%d", room, month+1, day+1);
			}
			
		}

	}
}
