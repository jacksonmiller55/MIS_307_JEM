import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Code for HW5
 * @author Jackson Miller
 */
/**
 * Creates the Car table, insert some data into the table, and drop the table
 * from a database.
 */
// ARGS database.properties
public class CarDB {
	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

		if (args.length == 0) {
			System.out.println("Usage: java CarDB propertiesFile");
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
				stat.execute("DROP TABLE Car");
			} catch (java.sql.SQLException e) {
			}
			
			createTable(stat);

			loadCars(stat, filename);

			boolean runProgram = true;
			String selection = null;
			Scanner input = new Scanner(System.in);
			boolean firstRun = true;
			while (runProgram) {
				if (!firstRun) {
					System.out.println();
					System.out.println();
				}
				selection = printSelectionOptions(input);

				if (selection.equals("Q")) {
					runProgram = quitProgram();
				} else if (selection.equals("A")) {
					addCar(stat, input);
				} else if (selection.equals("C")) {
					System.out.println("Average fuel efficiency: " + calculateAverage(stat));
				} else if (selection.equals("W")) {
					exportTable(stat, input);
				} else if (selection.equals("P")) {
					printTable(stat);
				} else if (selection.equals("M")) {
					upperBoundEfficiency(stat, input);
				}
				firstRun = false;
			}
			input.close();
			stat.execute("DROP TABLE Car");
		}
	}

	public static Statement createTable(Statement stat) {
		try {
			stat.execute("CREATE TABLE Car " + "(Car_Manufacture VARCHAR(12), " + "Car_Model VARCHAR(12), "
					+ "Car_Year INT, " + "Car_MPG INT)");
		} catch (SQLException e) {
			System.out.println("The system was unable to create the table in the database.");
			e.printStackTrace();
		}
		return stat;
	}

	public static void loadCars(Statement stat, String filename) {
		File inputFile = new File(filename);
		Scanner in = null;

		try {
			in = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			System.out.println("The file was not found. Check the path of the file and make sure that it is = args[1]");
			e.printStackTrace();
		}

		String manufacturer = null;
		String model = null;
		int year = 0;
		int mpg = 0;

		while (in.hasNextLine()) {

			year = Integer.parseInt(in.next());
			manufacturer = in.next().trim();
			model = in.next().trim();
			mpg = Integer.parseInt(in.next());

			StringBuilder insertCarInfo = new StringBuilder();
			insertCarInfo.append("INSERT INTO Car VALUES ('");
			insertCarInfo.append(manufacturer);
			insertCarInfo.append("', '");
			insertCarInfo.append(model);
			insertCarInfo.append("', ");
			insertCarInfo.append(year);
			insertCarInfo.append(", ");
			insertCarInfo.append(mpg);
			insertCarInfo.append(")");

			try {
				stat.execute(insertCarInfo.toString());
			} catch (SQLException e) {
				System.out.println(
						"The system was not able to initiate the database table from the input file. Please check your file contents.");
				e.printStackTrace();
			}
		}
		in.close();
	}

	public static boolean quitProgram() {
		System.out.println("You have quit the program.");
		return false;
	}

	public static void printTable(Statement stat) throws SQLException {
		ResultSet result = null;
		try {
			result = stat.executeQuery("SELECT * FROM CAR");
		} catch (SQLException e) {
			System.out.print("The system was not able to execute the query to print the table.");
			e.printStackTrace();
		}
		int columnsNumber = 4;

		while (result.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1) {

					System.out.print("\t");

				}
				String info = result.getString(i);
				if (i < 3) {
					System.out.printf("%-12s", info);
				} else {
					System.out.printf("%-4s", info);
				}
			}
			System.out.println("");
		}
		result.close();
	}

	public static String printSelectionOptions(Scanner input) {
		String selection = null;
		boolean correctInput = false;

		System.out.println("Select from the following options");
		System.out.println("(Q) Quit");
		System.out.println("(A) Add a car");
		System.out.println("(C) Calculate average");
		System.out.println("(W) Write the entire table to a text file");
		System.out.println("(P) Print the entire table");
		System.out.println("(M) Print a subset of the rows based on efficiency");

		selection = input.next();

		while (!correctInput) {

			if (selection.length() > 2) {
				System.out.println("Please enter only one character to select the appropriat option.");
				selection = input.next();
			} else if (!Character.isAlphabetic(selection.charAt(0))) {
				System.out.println(
						"Please enter only the selected character to select the appropriat option. Numbers will not be accepted.");
				selection = input.next();
			} else if ((selection.toUpperCase().equals("Q")) || (selection.toUpperCase().equals("A"))
					|| (selection.toUpperCase().equals("C")) || (selection.toUpperCase().equals("C"))
					|| (selection.toUpperCase().equals("W")) || (selection.toUpperCase().equals("P"))
					|| (selection.toUpperCase().equals("M"))) {
				correctInput = true;
			} else {
				System.out.println("The Character must match one of the appropriat options.");
				selection = input.next();
			}
		}
		return selection.toUpperCase();
	}

	public static boolean addCar(Statement stat, Scanner input) {
		boolean nameIsGood = false;
		boolean modelIsGood = false;
		boolean yearIsGood = false;
		boolean mpgIsGood = false;

		String manufacturer = null;
		String model = null;
		int year = 0;
		int mpg = 0;

		System.out.print("Manufacturer name: ");
		manufacturer = input.next().trim();
		while (!nameIsGood) {
			if (manufacturer.length() > 12) {
				System.out.println(
						"The system does not accept a manufacture that has a length longer than 12 characters. Please enter correct this error.");
				manufacturer = input.next().trim();
			} else {
				nameIsGood = true;
			}
		}
		
		System.out.print("Model name: ");
		model = input.next().trim();
		while (!modelIsGood) {
			if (model.length() > 12) {
				System.out.println(
						"The system does not accept a model that has a length longer than 12 characters. Please enter correct this error.");
				model = input.next().trim();
			} else {
				modelIsGood = true;
			}
		}
		String yearString = null;
		System.out.print("Year: ");
		yearString = input.next();
		while (!yearIsGood) {
			if ((yearString.length() > 4) || (yearString.length() < 4)) {
				System.out.println("Please a 4 digit number to represent the year.");
				yearString = input.next();
			} else if (yearString.contains("-")) {
				System.out.println("The system does not allow negative numbers. Please enter a new number.");
				yearString = input.next();
			} else if ((Character.isAlphabetic(yearString.charAt(0))) || (Character.isAlphabetic(yearString.charAt(1)))
					|| (Character.isAlphabetic(yearString.charAt(2)))
					|| (Character.isAlphabetic(yearString.charAt(3)))) {
				System.out.println("The system only accepts numbers.");
				yearString = input.next();
			} else {
				year = Integer.parseInt(yearString);
				yearIsGood = true;
			}
		}
		String mpgString = null;
		System.out.print("MPG : ");
		mpgString = input.next();

		while (!mpgIsGood) {
			if (mpgString.length() > 2) {
				System.out.println("Please enter a whole number less than 100.");
				mpgString = input.next();
			} else if (mpgString.contains("-")) {
				System.out.println("The system does not allow negative numbers. Please enter a new number.");
				mpgString = input.next();
			} else {
				if (mpgString.length() == 2) {
					if ((Character.isAlphabetic(mpgString.charAt(0)))
							|| (Character.isAlphabetic(mpgString.charAt(1)))) {
						System.out.println("The system only accepts numbers.");
						mpgString = input.next();
					}
				}
				if (mpgString.length() == 1) {
					if (Character.isAlphabetic(mpgString.charAt(0))) {
						System.out.println("The system only accepts numbers.");
						mpgString = input.next();
					}
				}
				mpg = Integer.parseInt(mpgString);
				mpgIsGood = true;
			}
		}

		StringBuilder insertCarInfo = new StringBuilder();
		insertCarInfo.append("INSERT INTO Car VALUES ('");
		insertCarInfo.append(manufacturer);
		insertCarInfo.append("', '");
		insertCarInfo.append(model);
		insertCarInfo.append("', ");
		insertCarInfo.append(year);
		insertCarInfo.append(", ");
		insertCarInfo.append(mpg);
		insertCarInfo.append(")");

		try {
			stat.execute(insertCarInfo.toString());
		} catch (SQLException e) {
			System.out.print("The system was not able to add the values to the database.");
			e.printStackTrace();
		}
		return true;
	}

	public static double calculateAverage(Statement stat) {
		double average = 0;
		try {
			ResultSet result = stat.executeQuery("SELECT AVG(CAST((Car_MPG)AS DECIMAL(10,2))) FROM Car ");
			result.next();
			average = result.getDouble(1);
		} catch (SQLException e) {
			System.out.println("The Car_MPG varables in the SQL Table need to be checked.");
			e.printStackTrace();
		}
		if (average == 0) {
			throw new IllegalArgumentException("The querey did not run properly.");
		}
		return average;
	}

	public static boolean exportTable(Statement stat, Scanner input) throws SQLException {
		System.out.print("Output file name: ");
		String outputFileName = input.next();
		PrintWriter output = null;

		try {
			output = new PrintWriter(outputFileName);
		} catch (FileNotFoundException e) {
			System.out.println("The file name must end with '.txt'");
			e.printStackTrace();
		}

		ResultSet result = null;
		try {
			result = stat.executeQuery("SELECT * FROM CAR");
		} catch (SQLException e) {
			System.out.print("The system was not able to execute the query to export the table.");
			e.printStackTrace();
		}
		int columnsNumber = 4;

		while (result.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1) {
					output.print("\t");
				}
				String info = result.getString(i);
				if (i < 3) {
					output.printf("%-12s", info);
				} else {
					output.printf("%-4s", info);
				}
			}
			output.println("");
		}
		result.close();
		output.close();

		return true;
	}

	public static boolean upperBoundEfficiency(Statement stat, Scanner input) throws SQLException {
		System.out.print("Upper Bound on efficiency (MPG): ");
		String upperBound = null;
		int mpg = 0;
		boolean upperBoundIsGood = false;

		upperBound = input.next();

		while (!upperBoundIsGood) {
			if (upperBound.length() > 2) {
				System.out.println("Please enter a whole number less than 100.");
				upperBound = input.next();
			} else if (upperBound.contains("-")) {
				System.out.println("The system does not allow negative numbers. Please enter a new number.");
				upperBound = input.next();
			} else {
				if (upperBound.length() == 2) {
					if ((Character.isAlphabetic(upperBound.charAt(0)))
							|| (Character.isAlphabetic(upperBound.charAt(1)))) {
						System.out.println("The system only accepts numbers.");
						upperBound = input.next();
					}
				}
				if (upperBound.length() == 1) {
					if (Character.isAlphabetic(upperBound.charAt(0))) {
						System.out.println("The system only accepts numbers.");
						upperBound = input.next();
					}
				}
				mpg = Integer.parseInt(upperBound);
				upperBoundIsGood = true;
			}
		}
		StringBuilder CarsMPGEqualOrLessThan = new StringBuilder();
		CarsMPGEqualOrLessThan.append("SELECT * FROM CAR WHERE Car_MPG <= ");
		CarsMPGEqualOrLessThan.append(mpg);

		String queryCarMPG = CarsMPGEqualOrLessThan.toString();

		ResultSet result = stat.executeQuery(queryCarMPG);
		result.next();

		int columnsNumber = 4;

		while (result.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1) {
					System.out.print("\t");
				}
				String info = result.getString(i);
				if (i < 3) {
					System.out.printf("%-12s", info);
				} else {
					System.out.printf("%-4s", info);
				}
			}
			System.out.println("");
		}
		result.close();

		return true;
	}
}
