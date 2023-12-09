-- Inserting data into the condition table
INSERT INTO `condition` (patient_id, `condition`, description) VALUES (1, 'Fever', 'Elevated body temperature');
INSERT INTO `condition` (patient_id, `condition`, description) VALUES (2, 'Headache', 'Persistent pain in the head');
INSERT INTO `condition` (patient_id, `condition`, description) VALUES (3, 'Allergies', 'Sensitivity to certain substances');
INSERT INTO `condition` (patient_id, `condition`, description) VALUES (4, 'Diabetes', 'High blood sugar levels');
INSERT INTO `condition` (patient_id, `condition`, description) VALUES (5, 'Insomnia', 'Difficulty falling asleep');

-- Inserting data into the encounter table
INSERT INTO encounter (doctor_id, observation_id, patient_id, timestamp, reason) VALUES (6, null, 1, '2023-12-09 15:03:57.247493', 'Routine checkup');
INSERT INTO encounter (doctor_id, observation_id, patient_id, timestamp, reason) VALUES (7, null, 2, '2023-12-09 11:03:57.247493', 'Flu symptoms');
INSERT INTO encounter (doctor_id, observation_id, patient_id, timestamp, reason) VALUES (8, null, 3, '2023-12-09 09:05:57.247493', 'Vaccination');
INSERT INTO encounter (doctor_id, observation_id, patient_id, timestamp, reason) VALUES (9, null, 4, '2023-12-09 01:06:57.247493', 'Emergency visit');
INSERT INTO encounter (doctor_id, observation_id, patient_id, timestamp, reason) VALUES (10, null, 5, '2023-12-09 04:10:57.247493', 'Sports injury evaluation');

-- Inserting data into the observation table
INSERT INTO observation (patient_id, user_id, timestamp, observation) VALUES (1, 12, '2023-12-08 15:03:57.247493', 'Blood pressure measured');
INSERT INTO observation (patient_id, user_id, timestamp, observation) VALUES (2, 13, '2023-12-07 12:03:57.247493', 'Dietary intake recorded');
INSERT INTO observation (patient_id, user_id, timestamp, observation) VALUES (3, 14, '2023-12-01 10:03:57.247493', 'Weight recorded');
INSERT INTO observation (patient_id, user_id, timestamp, observation) VALUES (4, 7, '2023-12-04 08:03:57.247493', 'Mood assessment');
INSERT INTO observation (patient_id, user_id, timestamp, observation) VALUES (5, 10, '2023-12-06 19:03:57.247493', 'Cholesterol level checked');