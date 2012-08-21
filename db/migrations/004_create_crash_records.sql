BEGIN;
INSERT INTO migrations VALUES (4);
ROLLBACK;

CREATE TABLE crash_records (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	experiment_run_id INT NOT NULL,
	tick INT NOT NULL,
 	middle_lane BOOLEAN DEFAULT NULL,
	relative_velocity DOUBLE DEFAULT NULL,
	boats_count INT DEFAULT NULL,
	CONSTRAINT crash_record_experiment_run_fk FOREIGN KEY (experiment_run_id) REFERENCES experiment_runs(id)
) ENGINE=innodb;

ALTER TABLE experiment_runs
DROP COLUMN crash_count;

INSERT INTO migrations VALUES (4);