import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.File;
import java.io.FileNotFoundException;
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

		if (args.length == 0) {
			System.out.println("Usage: java HotelDB propertiesFile");
			System.exit(0);
		}

		SimpleDataSource.init(args[0]);
		if (args.length == 0) {
			System.out.println(
					"Usage: java -classpath driver_class_path" + File.pathSeparator + ". TestDB propertiesFile");
			return;
		}

		SimpleDataSource.init(args[0]);
		String filename = args[1];

		try (Connection conn = SimpleDataSource.getConnection()) {
			Statement stat = conn.createStatement();

			try {
				stat.execute("DROP TABLE Room");
			} catch (java.sql.SQLException e) {
			}
			
			//People that have downloaded the code and are able to use it.
			System.out.println("People that have cloned the repository: Jackson, Elias");
			
			//Creates and loads the table
			createTable(stat);
			loadRooms(stat, filename);
			
			String selection = null;
			Scanner input = new Scanner(System.in);
			boolean firstRun = true;
			boolean continueToRun = true;
			while(continueToRun) {
				//Adds 2 lines for spacing after the first time the program is run.
				if (!firstRun) {
					System.out.println();
					System.out.println();
				}
				selection = printSelectionOptions(input);

				if (selection.equals("Q")) {
					continueToRun = quitProgram();
				
				} else if (selection.equals("P")) {
					printTable(stat);
				
				}
				//Tells the program that this is no longer the first run.
				firstRun = false;
			}
			input.close();
			
			stat.execute("DROP TABLE Room");
		}
	}

	public static Statement createTable(Statement stat) {
		try {
			stat.execute("CREATE TABLE Room (Room_Number INT, Room_Type VARCHAR(40), Room_Price DECIMAL(10,2))");
		} catch (SQLException e) {
			System.out.println("The system was unable to create the table in the database.");
			e.printStackTrace();
		}
		System.out.println("System created table.");
		return stat;
	}

	public static void loadRooms(Statement stat, String filename) {
		File inputFile = new File(filename);
		Scanner in = null;

		try {
			in = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			System.out.println("The file was not found. Check the path of the file and make sure that it is = args[1]");
			e.printStackTrace();
		}

		int roomNumber = 0;
		String roomType = null;
		double roomPrice = 0.00;

		while (in.hasNextLine()) {

			roomNumber = Integer.parseInt(in.next());
			roomType = in.next();
			roomPrice = Double.parseDouble(in.next());
			
			

			StringBuilder insertRoomInfo = new StringBuilder();
			insertRoomInfo.append("INSERT INTO Room VALUES (");
			insertRoomInfo.append(roomNumber);
			insertRoomInfo.append(", '");
			insertRoomInfo.append(roomType);
			insertRoomInfo.append("', ");
			insertRoomInfo.append(roomPrice);
			insertRoomInfo.append(")");

			try {
				stat.execute(insertRoomInfo.toString());
			} catch (SQLException e) {
				System.out.println(
						"The system was not able to initiate the database table from the input file. Please check your file contents.");
				e.printStackTrace();
			}
		}
		in.close();
	}

	public static void printTable(Statement stat) throws SQLException {
		ResultSet result = null;
		try {
			result = stat.executeQuery("SELECT * FROM Room");
		} catch (SQLException e) {
			System.out.print("The system was not able to execute the query to print the table.");
			e.printStackTrace();
		}
		
		System.out.println();
		System.out.println("Room\t Room Type\t Price");
		
		int columnsNumber = 3;
		
		while (result.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1) {

					System.out.print("\t");

				}
				String info = result.getString(i);
				if (i < 2) {
					System.out.printf("%-6s", info);
				} else {
					System.out.printf("%-8s", info);
				}
			}
			System.out.println("");
		}
		result.close();
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
		System.out.println("(P) Print all of the rooms");

		selection = input.next();

		while (!correctInput) {

			if (selection.length() > 2) {
				System.out.println("Please enter only one character to select the appropriat option.");
				selection = input.next();
			} else if (!Character.isAlphabetic(selection.charAt(0))) {
				System.out.println(
						"Please enter only the selected character to select the appropriat option. Numbers will not be accepted.");
				selection = input.next();
			} else if ((selection.toUpperCase().equals("Q"))
					|| (selection.toUpperCase().equals("P"))) 
			{
				correctInput = true;
			} else {
				System.out.println("The Character must match one of the appropriat options.");
				selection = input.next();
			}
		}
		return selection.toUpperCase();
	}
}
