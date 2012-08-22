BEGIN;
INSERT INTO migrations VALUES (6);
ROLLBACK;

ALTER TABLE simulation_parameters
CHANGE brain_type control_policy VARCHAR(255) NOT NULL;

ALTER TABLE simulation_runs
CHANGE brain_type control_policy VARCHAR(255) DEFAULT NULL;

ALTER TABLE boat_records
CHANGE brain_type control_policy VARCHAR(255) DEFAULT NULL;

INSERT INTO migrations VALUES (6);