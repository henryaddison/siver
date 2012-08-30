BEGIN;
INSERT INTO migrations VALUES (8);
ROLLBACK;

ALTER TABLE simulation_runs
DROP COLUMN all_boats_finished;

INSERT INTO migrations VALUES (8);