BEGIN;
INSERT INTO migrations VALUES (3);
ROLLBACK;

ALTER TABLE schedules
ADD COLUMN version INT NOT NULL;

UPDATE schedules
SET version = RIGHT(name, 1)
WHERE name != "One boat";

UPDATE schedules
SET version = 1
WHERE name = "One boat";

ALTER TABLE schedules
DROP KEY `name`;

UPDATE schedules
SET name = LTRIM(REVERSE(SUBSTRING(REVERSE(name), 3)))
WHERE name != "One boat";

ALTER TABLE schedules
ADD CONSTRAINT schedule_name_and_version UNIQUE KEY (name, version);

INSERT INTO migrations VALUES (3);