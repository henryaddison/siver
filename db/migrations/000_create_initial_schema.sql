CREATE TABLE schedules (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL UNIQUE KEY,
	description TEXT
) ENGINE=innodb;

CREATE TABLE experiments (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	random_seed INT NOT NULL,
	schedule_id INT NOT NULL,
	brain_type VARCHAR(255) NOT NULL DEFAULT "BasicBrain",
	CONSTRAINT experiment_schedule_fk FOREIGN KEY (schedule_id) REFERENCES schedules(id)
) ENGINE=innodb;

CREATE TABLE scheduled_launches (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	schedule_id INT NOT NULL,
	desired_gear INT NOT NULL,
	speed_multiplier DOUBLE NOT NULL DEFAULT 0.5,
	distance_to_cover DOUBLE NOT NULL,
	launch_tick INT NOT NULL,
	CONSTRAINT launch_schedule_fk FOREIGN KEY (schedule_id) REFERENCES schedules(id),
	CONSTRAINT tick_unique_key UNIQUE KEY (schedule_id, launch_tick)
) ENGINE=innodb;

CREATE TABLE experiment_runs (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	experiment_id INT DEFAULT NULL,
	started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	crash_count INT,
	tick_count INT,
	random_seed INT NOT NULL,
	flushed BOOLEAN DEFAULT FALSE,
	all_boats_finished BOOLEAN DEFAULT FALSE,
	brain_type VARCHAR(255) NOT NULL DEFAULT "BasicBrain",
	CONSTRAINT experiment_run_schedule_fk FOREIGN KEY (experiment_id) REFERENCES experiments(id)
) ENGINE=innodb;

CREATE TABLE boat_records (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	scheduled_launch_id INT DEFAULT NULL,
	experiment_run_id INT NOT NULL,
	launch_tick INT NOT NULL,
	land_tick INT DEFAULT NULL,
	desired_gear INT NOT NULL,
	speed_multiplier DOUBLE NOT NULL,
	distance_covered DOUBLE NOT NULL,
	aggregate_gear_difference INT NOT NULL,
	brain_type varchar(255) NOT NULL DEFAULT 'BasicBrain',
	CONSTRAINT boat_record_launch_fk FOREIGN KEY (scheduled_launch_id) REFERENCES scheduled_launches(id),
	CONSTRAINT boat_record_experiment_run_fk FOREIGN KEY (experiment_run_id) REFERENCES experiment_runs(id)
) ENGINE=innodb;

CREATE TABLE migrations (
	version INT NOT NULL PRIMARY KEY
) ENGINE=innodb;

INSERT INTO migrations VALUES (0);