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
public class HotelManagmentSystem {
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
			printTable(stat);
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
}
