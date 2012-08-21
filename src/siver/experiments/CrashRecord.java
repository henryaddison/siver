package siver.experiments;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import siver.context.SiverContextCreator;

public class CrashRecord extends ExperimentalDatum {
	public static void create(boolean middle_lane, double relative_velocity, int boats_count) {
		PreparedStatement insertCrashRecord = null;
		
		
		
		String sql = "INSERT INTO crash_records(experiment_run_id, tick, middle_lane, relative_velocity, boats_count)"
                + "VALUES(?, ?, ?, ?, ?)";
        try {
			insertCrashRecord = conn.prepareStatement(sql);
			insertCrashRecord.setInt(1, InprogressExperiment.instance().experiment_run_id());
			insertCrashRecord.setInt(2, SiverContextCreator.getTickCount());
			insertCrashRecord.setBoolean(3, middle_lane);
			insertCrashRecord.setDouble(4, relative_velocity);
			insertCrashRecord.setInt(5, boats_count);

		    insertCrashRecord.executeUpdate();
		    insertCrashRecord.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
