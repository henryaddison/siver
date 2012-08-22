BEGIN;
INSERT INTO migrations VALUES (5);
ROLLBACK;

#rename the tables
RENAME TABLE experiments TO simulation_parameters;
RENAME TABLE experiment_runs TO simulation_runs;

#Change name of simulation_runs foreign key link to simulation_parameters
ALTER TABLE simulation_runs
DROP FOREIGN KEY `experiment_run_schedule_fk`;

ALTER TABLE simulation_runs
CHANGE experiment_id simulation_parameters_id INT DEFAULT NULL;

ALTER TABLE simulation_runs
ADD CONSTRAINT `simulation_run_schedule_fk` FOREIGN KEY (`simulation_parameters_id`) REFERENCES `simulation_parameters` (`id`);


#Change name of crash records foreign key link to simulation_runs
ALTER TABLE crash_records
DROP FOREIGN KEY `crash_record_experiment_run_fk`;

ALTER TABLE crash_records
CHANGE experiment_run_id simulation_run_id INT NOT NULL;

ALTER TABLE crash_records
ADD CONSTRAINT `crash_record_simulation_run_fk` FOREIGN KEY (`simulation_run_id`) REFERENCES `simulation_runs` (`id`);

#Change name of boat records foreign key link to simulation_runs
ALTER TABLE boat_records
DROP FOREIGN KEY `boat_record_experiment_run_fk`;

ALTER TABLE boat_records
CHANGE experiment_run_id simulation_run_id INT NOT NULL;

ALTER TABLE boat_records
ADD CONSTRAINT `boat_record_simulation_run_fk` FOREIGN KEY (`simulation_run_id`) REFERENCES `simulation_runs` (`id`);

INSERT INTO migrations VALUES (5);