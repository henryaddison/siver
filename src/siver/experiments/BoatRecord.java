package siver.experiments;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import siver.boat.Boat;
import siver.context.SiverContextCreator;
import siver.cox.Cox;

public class BoatRecord extends ExperimentalDatum {
	private Integer scheduled_launch_id, land_tick;
	private int launch_tick, desired_gear, aggregate_gear_difference, simulation_run_id, aggregate_tenth_tick_gear_difference;
	private double speed_multiplier, distance_covered;
	private String brain_type;
	
	public BoatRecord(Integer scheduled_launch_id, int launch_tick, Boat boat, Cox cox) {
		this.scheduled_launch_id = scheduled_launch_id;
		this.launch_tick = launch_tick;
		this.desired_gear = cox.desired_gear();
		this.speed_multiplier = boat.getSpeedMultiplier();
		this.brain_type = cox.brain_type();
		this.distance_covered = 0;
		this.aggregate_gear_difference = 0;
		this.aggregate_tenth_tick_gear_difference = 0;
		if(InprogressSimuation.instance() != null) {
			this.simulation_run_id = InprogressSimuation.instance().simulation_run_id();
		}
		this.land_tick = null;
		InprogressSimuation.addBoatRecord(this);
	}
	
	public void updateStats(double distance_travelled, int gear, int tick) {
		int gear_difference = Math.abs(gear - this.desired_gear);
		aggregate_gear_difference += gear_difference;
		if(tick % 10 == 0) {
			aggregate_tenth_tick_gear_difference += gear_difference;
		}
		distance_covered = distance_travelled;
	}
	
	public void landed(int land_tick) {
		this.land_tick = land_tick;
		flush();
		InprogressSimuation.removeBoatRecord(this);
	}
	
	public void flush() {
		PreparedStatement insertBoatRecord = null;
		
		String sql = "INSERT INTO boat_records(scheduled_launch_id, simulation_run_id, launch_tick, land_tick, desired_gear, speed_multiplier, distance_covered, aggregate_gear_difference, aggregate_tenth_tick_gear_difference, brain_type)"
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
			insertBoatRecord = conn.prepareStatement(sql);
			
			intOrNullParam(insertBoatRecord, 1, scheduled_launch_id);
			insertBoatRecord.setInt(2, simulation_run_id);
			insertBoatRecord.setInt(3, launch_tick);
			intOrNullParam(insertBoatRecord, 4, land_tick);
			insertBoatRecord.setInt(5, desired_gear);
			insertBoatRecord.setDouble(6, speed_multiplier);
			insertBoatRecord.setDouble(7, distance_covered);
			insertBoatRecord.setInt(8, aggregate_gear_difference);
			insertBoatRecord.setInt(9, aggregate_tenth_tick_gear_difference);
			insertBoatRecord.setString(10, brain_type);
		    insertBoatRecord.executeUpdate();
		    insertBoatRecord.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void intOrNullParam(PreparedStatement stmt, int param_index, Integer value) throws SQLException {
		if(value != null) {
			stmt.setInt(param_index, value);
		} else {
			stmt.setNull(param_index, java.sql.Types.INTEGER);
		}
	}
}
