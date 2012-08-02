package siver.experiments;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import repast.simphony.random.RandomHelper;

public class InprogressExperiment {
	private final static String URL = "jdbc:mysql://localhost:3306/siver_development";
	private static InprogressExperiment instance;
	private static Connection conn = null;;
	private Integer experiment_id;
	private int experiment_run_id;
	
	public static void start(Integer experiment_id) {
		if(getInstance() == null) {
			instance = new InprogressExperiment(experiment_id);
		} else {
			throw new RuntimeException("Trying to setup new Inprogress Experiment while one is already in progress.");
		}
		
		initializeDBConnection();
		
		try {
			instance.insert_row();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
	
	public static void end() {
		getInstance().flush();
		instance = null;
		closeDBConnection();
	}
	
	public static InprogressExperiment getInstance() {
		return instance;
	}
	
	private static void initializeDBConnection() {
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
	
	private static void closeDBConnection() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void insert_row() throws SQLException {
		PreparedStatement insertExperimentRun = null;
		String sql = "INSERT INTO experiment_runs(experiment_id, random_seed, crash_count)"
                + "VALUES(?, ?, ?)";
        insertExperimentRun = conn.prepareStatement(sql);
		if(experiment_id == null) {
			insertExperimentRun.setNull(1, java.sql.Types.INTEGER);
		} else {
			insertExperimentRun.setInt(1, experiment_id);
		}
	    insertExperimentRun.setInt(2, RandomHelper.getSeed());
	    insertExperimentRun.setInt(3, 0);
	    this.experiment_run_id = insertExperimentRun.executeUpdate();
	    insertExperimentRun.close();
	}
	
	private InprogressExperiment(Integer experiment_id) {
		this.experiment_id = experiment_id;
	}
	
	private void flush() {
        
	}
}
