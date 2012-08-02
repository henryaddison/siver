package siver.experiments;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.random.RandomHelper;

public class InprogressExperiment {
	private final static String URL = "jdbc:mysql://localhost:3306/siver_development";
	private static InprogressExperiment instance;
	private static Connection conn = null;;
	private Integer experiment_id;
	private int experiment_run_id;
	private int crash_count;
	
	public static void start(Integer experiment_id) {
		if(instance() == null) {
			instance = new InprogressExperiment(experiment_id);
		} else {
			throw new RuntimeException("Trying to setup new Inprogress Experiment while one is already in progress.");
		}
		
		initializeDBConnection();
		
		try {
			instance().create_experiment_run();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
	
	public static void end() {
		try {
			instance().flush();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			instance = null;
			closeDBConnection();
		}
	}
	
	public static InprogressExperiment instance() {
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
	
	private void create_experiment_run() throws SQLException {
		PreparedStatement insertExperimentRun = null;
		String sql = "INSERT INTO experiment_runs(experiment_id, random_seed, crash_count)"
                + "VALUES(?, ?, ?)";
        insertExperimentRun = conn.prepareStatement(sql);
		if(isAutomated()) {
			insertExperimentRun.setInt(1, experiment_id);
		} else {
			insertExperimentRun.setNull(1, java.sql.Types.INTEGER);
		}
	    insertExperimentRun.setInt(2, RandomHelper.getSeed());
	    insertExperimentRun.setInt(3, 0);
	    insertExperimentRun.executeUpdate();
	    ResultSet keys = insertExperimentRun.getGeneratedKeys();
	    keys.first();
	    this.experiment_run_id = keys.getInt(1);
	    
	    insertExperimentRun.close();
	}
	
	private InprogressExperiment(Integer experiment_id) {
		this.experiment_id = experiment_id;
		crash_count = 0;
	}
	
	public static void incrementCrashCount() {
		if(instance() != null) {
			instance().crash_count++;
		}
	}
	
	public boolean isAutomated() {
		return experiment_id != null;
	}
	
	private void flush() throws SQLException {
		PreparedStatement finishExperimentRun = null;
		String sql = "UPDATE experiment_runs " +
				     "SET flushed = ?, crash_count = ?, tick_count = ? " + 
					 "WHERE id = ?";
		finishExperimentRun = conn.prepareStatement(sql);
		if(isAutomated()) {
			finishExperimentRun.setInt(1, experiment_id);
		} else {
			finishExperimentRun.setNull(1, java.sql.Types.INTEGER);
		}
		finishExperimentRun.setBoolean(1, true);
		finishExperimentRun.setInt(2, crash_count);
		finishExperimentRun.setInt(3, (int)Math.floor(RunEnvironment.getInstance().getCurrentSchedule().getTickCount()));
		finishExperimentRun.setInt(4, experiment_run_id);
		finishExperimentRun.executeUpdate();
	    finishExperimentRun.close();
                
	}
}
