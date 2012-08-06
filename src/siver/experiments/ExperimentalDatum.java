package siver.experiments;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public abstract class ExperimentalDatum {
	protected static Connection conn = null;
	private final static String URL = "jdbc:mysql://localhost:3306/siver_development";
	
	protected static void initializeDBConnection() {
		if(conn == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				conn = DriverManager.getConnection(URL, "siver", "");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected static void closeDBConnection() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn = null;
		}
	}
}
