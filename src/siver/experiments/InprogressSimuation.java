package siver.experiments;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ISchedule;
import repast.simphony.engine.schedule.ScheduleParameters;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.ui.RSApplication;
import siver.context.SiverContextCreator;
import siver.cox.control_policies.GearFocussed;
import siver.cox.control_policies.ControlPolicy;
import siver.ui.UserPanel;

public class InprogressSimuation extends ExperimentalDatum {
	private static final double TICK_TIMEOUT = 4*60*60; // a 4 hour session is an automated simulation run maximum
	
	private static InprogressSimuation instance;
	private Integer simulation_parameters_id, schedule_id;
	private int simulation_run_id;
	private ArrayList<BoatRecord> inprogress_records;
	private int random_seed;
	private Class<? extends ControlPolicy> control_policy;
	
	public static void start() {
		if(instance() == null) {
			instance = new InprogressSimuation();
		} else {
			throw new RuntimeException("Trying to start new Inprogress Simulation while one is already in progress.");
		}
		
		initializeDBConnection();
		BoatRecord.initializeDBConnection();
		try {
			instance().findRandomSeedScheduleIdAndControlPolicy();
			instance().create_simulation_run();
			instance().scheduleLaunches();
			if(instance().isAutomated()) {
				//set up a scheduled method to run when after a certain number of ticks has passed so simulation runs don't take too long
				ISchedule schedule = RunEnvironment.getInstance().getCurrentSchedule();
				ScheduleParameters simTimeoutParams = ScheduleParameters.createOneTime(TICK_TIMEOUT);
				schedule.schedule(simTimeoutParams, instance(), "stopSim");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
	
	public static void end() {
		//flush any remaining boat records to the database
		try {
			for(BoatRecord br : instance().inprogress_records) {
				br.flush();
			}
			instance().flush();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//make sure all DB connections are closed and set the instance to null
			instance = null;
			closeDBConnection();
			BoatRecord.closeDBConnection();
		}
	}
	
	public static InprogressSimuation instance() {
		return instance;
	}
	
	private void create_simulation_run() throws SQLException {
		PreparedStatement insertSimulationRun = null;
		String sql = "INSERT INTO simulation_runs(simulation_parameters_id, random_seed, control_policy)"
                + "VALUES(?, ?, ?)";
        insertSimulationRun = conn.prepareStatement(sql);
		if(isAutomated()) {
			insertSimulationRun.setInt(1, simulation_parameters_id);
		} else {
			insertSimulationRun.setNull(1, java.sql.Types.INTEGER);
		}
	    insertSimulationRun.setInt(2, random_seed);
	    if(control_policy != null) {
	    	insertSimulationRun.setString(3, control_policy.getSimpleName());
	    } else {
	    	insertSimulationRun.setNull(3, java.sql.Types.VARCHAR);
	    }
	    insertSimulationRun.executeUpdate();
	    ResultSet keys = insertSimulationRun.getGeneratedKeys();
	    keys.first();
	    this.simulation_run_id = keys.getInt(1);
	    
	    insertSimulationRun.close();
	}
	
	private InprogressSimuation() {
		Parameters params = RunEnvironment.getInstance().getParameters(); 
		this.simulation_parameters_id = (Integer)params.getValue("SimulationParametersId");
		if(this.simulation_parameters_id <= 0) this.simulation_parameters_id = null;
		this.inprogress_records = new ArrayList<BoatRecord>();
	}
	
	private void findRandomSeedScheduleIdAndControlPolicy() {
		if(isAutomated()) {
			PreparedStatement getRandomSeed = null;
			String sql = "SELECT random_seed, schedule_id, control_policy FROM simulation_parameters WHERE ID = ?";
			try {
				getRandomSeed = conn.prepareStatement(sql);
				getRandomSeed.setInt(1, simulation_parameters_id);
				ResultSet rseed = getRandomSeed.executeQuery();
				rseed.first();
				random_seed = rseed.getInt(1);
				schedule_id = rseed.getInt(2);
				String coxControlPolicyClassName =  rseed.getString(3);
				getRandomSeed.close();
				
				if(!coxControlPolicyClassName.startsWith("siver.")) {
					coxControlPolicyClassName = GearFocussed.class.getPackage().getName()+"."+coxControlPolicyClassName;
				}
				control_policy = (Class<? extends ControlPolicy>) Class.forName(coxControlPolicyClassName);
				RandomHelper.setSeed(random_seed);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			random_seed = RandomHelper.getSeed();
			schedule_id = null;
		}
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
	
	public static void incrementCrashCount(boolean middle_lane, double relative_velocity, int boats_count) {
		if(instance() != null) {
			CrashRecord.create(middle_lane, relative_velocity, boats_count);
		}
	}
	
	private boolean isAutomated() {
		return simulation_parameters_id != null;
	}
	
	public int simulation_run_id() {
		return this.simulation_run_id;
	}
	
	private void flush() throws SQLException {
		PreparedStatement finishSimulationRun = null;
		String sql = "UPDATE simulation_runs " +
				     "SET flushed = ?, tick_count = ? " + 
					 "WHERE id = ?";
		finishSimulationRun = conn.prepareStatement(sql);
		if(isAutomated()) {
			finishSimulationRun.setInt(1, simulation_parameters_id);
		} else {
			finishSimulationRun.setNull(1, java.sql.Types.INTEGER);
		}
		finishSimulationRun.setBoolean(1, true);
		finishSimulationRun.setInt(2, SiverContextCreator.getTickCount());
		finishSimulationRun.setInt(3, simulation_run_id);
		finishSimulationRun.executeUpdate();
	    finishSimulationRun.close();
                
	}
	
	private void scheduleLaunches() {
		//remove any old user control panel that may be there to prevent manual launches mid automated simulation run
		if(RSApplication.getRSApplicationInstance() != null) {
			//make sure we're in a mode where there is an RS Application instance (so not running in batch mode)
			RSApplication.getRSApplicationInstance().removeCustomUserPanel();
		}
		if(isAutomated()) {
			PreparedStatement stmt = null;
			String sql = "SELECT launch_tick, desired_gear, speed_multiplier, distance_to_cover, id " +
						"FROM scheduled_launches WHERE schedule_id = ?";
			try {
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, schedule_id);
				ResultSet launchData = stmt.executeQuery();
				ISchedule schedule = RunEnvironment.getInstance().getCurrentSchedule();
				while(launchData.next()) {
					int launch_tick = launchData.getInt(1);
					int desired_gear = launchData.getInt(2);
					double speed_multiplier = launchData.getDouble(3);
					double distance_to_cover = launchData.getDouble(4);
					int schedule_launch_id = launchData.getInt(5);
					
					ScheduleParameters params = ScheduleParameters.createOneTime(launch_tick);
					schedule.schedule(params, SiverContextCreator.getBoatHouse(), "launch", 
							schedule_launch_id,desired_gear, speed_multiplier, distance_to_cover, control_policy);
				}
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			//if the simulation run does not have an automated schedule
			//then add a control panel so boat's may be launched manually
			RSApplication.getRSApplicationInstance().addCustomUserPanel(new UserPanel()); //and add a new one
		}
	}
	
	public void stopSim() {
		SiverContextCreator.stopSim();
	}
}
