ALTER TABLE boat_records
ADD COLUMN aggregate_tenth_tick_gear_difference INT NOT NULL;

INSERT INTO migrations VALUES (1);