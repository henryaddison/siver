package siver.experiments;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.random.RandomHelper;
import siver.context.SiverContextCreator;

public class InprogressExperiment extends ExperimentalDatum {
	
	private static InprogressExperiment instance;
	private Integer experiment_id;
	private int experiment_run_id;
	private int crash_count;
	private ArrayList<BoatRecord> inprogress_records;
	
	public static void start(Integer experiment_id) {
		if(instance() == null) {
			instance = new InprogressExperiment(experiment_id);
		} else {
			throw new RuntimeException("Trying to setup new Inprogress Experiment while one is already in progress.");
		}
		
		initializeDBConnection();
		BoatRecord.initializeDBConnection();
		try {
			instance().create_experiment_run();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
	
	public static void end() {
		try {
			for(BoatRecord br : instance().inprogress_records) {
				br.flush();
			}
			instance().flush();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			instance = null;
			closeDBConnection();
			BoatRecord.closeDBConnection();
		}
	}
	
	public static InprogressExperiment instance() {
		return instance;
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
		this.inprogress_records = new ArrayList<BoatRecord>();
	}
	
	public static void addBoatRecord(BoatRecord br) {
		if(instance()!= null) {
			instance().inprogress_records.add(br);
		}
	}
	
	public static void removeBoatRecord(BoatRecord br) {
		if(instance()!= null) {
			instance().inprogress_records.remove(br);
		}
	}
	
	public static void incrementCrashCount() {
		if(instance() != null) {
			instance().crash_count++;
		}
	}
	
	public boolean isAutomated() {
		return experiment_id != null;
	}
	
	public int experiment_run_id() {
		return this.experiment_run_id;
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
		finishExperimentRun.setInt(3, SiverContextCreator.getTickCount());
		finishExperimentRun.setInt(4, experiment_run_id);
		finishExperimentRun.executeUpdate();
	    finishExperimentRun.close();
                
	}
}
