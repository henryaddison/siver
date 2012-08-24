BEGIN;
INSERT INTO migrations VALUES (7);
ROLLBACK;

UPDATE simulation_parameters
SET control_policy = "GearFocussed"
WHERE control_policy = "BasicBrain";

UPDATE simulation_parameters
SET control_policy = "Overtaking"
WHERE control_policy = "OvertakingBrain";

UPDATE simulation_parameters
SET control_policy = "SafetyFocussed"
WHERE control_policy = "ConservativeBrain";

UPDATE simulation_runs
SET control_policy = "GearFocussed"
WHERE control_policy = "BasicBrain";

UPDATE simulation_runs
SET control_policy = "Overtaking"
WHERE control_policy = "OvertakingBrain";

UPDATE simulation_runs
SET control_policy = "SafetyFocussed"
WHERE control_policy = "ConservativeBrain";

UPDATE boat_records
SET control_policy = "GearFocussed"
WHERE control_policy = "BasicBrain";

UPDATE boat_records
SET control_policy = "Overtaking"
WHERE control_policy = "OvertakingBrain";

UPDATE boat_records
SET control_policy = "SafetyFocussed"
WHERE control_policy = "ConservativeBrain";

INSERT INTO migrations VALUES (7);