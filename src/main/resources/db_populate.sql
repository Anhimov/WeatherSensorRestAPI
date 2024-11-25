INSERT INTO sensor (name)
VALUES ('Moscow'),
       ('Saint Petersburg'),
       ('Novosibirsk'),
       ('Yekaterinburg'),
       ('Kazan'),
       ('Nizhny Novgorod'),
       ('Samara');

-- Moscow
INSERT INTO measurement (value, raining, created_at, sensor_id)
VALUES (2.5, TRUE, '2024-11-01 08:00:00', 1),
       (3.0, FALSE, '2024-11-01 14:00:00', 1),
       (1.5, TRUE, '2024-11-01 20:00:00', 1),
       (0.0, FALSE, '2024-11-02 08:00:00', 1),
       (-1.5, TRUE, '2024-11-02 14:00:00', 1),
       (-2.0, FALSE, '2024-11-02 20:00:00', 1),
       (1.0, TRUE, '2024-11-03 08:00:00', 1);

-- Saint Petersburg
INSERT INTO measurement (value, raining, created_at, sensor_id)
VALUES (3.5, TRUE, '2024-11-01 08:00:00', 2),
       (4.0, FALSE, '2024-11-01 14:00:00', 2),
       (2.0, TRUE, '2024-11-01 20:00:00', 2),
       (1.0, FALSE, '2024-11-02 08:00:00', 2),
       (-0.5, TRUE, '2024-11-02 14:00:00', 2),
       (-1.0, FALSE, '2024-11-02 20:00:00', 2),
       (2.0, TRUE, '2024-11-03 08:00:00', 2);

-- Novosibirsk
INSERT INTO measurement (value, raining, created_at, sensor_id)
VALUES (-5.0, FALSE, '2024-11-01 08:00:00', 3),
       (-3.0, FALSE, '2024-11-01 14:00:00', 3),
       (-8.0, TRUE, '2024-11-01 20:00:00', 3),
       (-10.0, FALSE, '2024-11-02 08:00:00', 3),
       (-12.0, FALSE, '2024-11-02 14:00:00', 3),
       (-9.0, TRUE, '2024-11-02 20:00:00', 3),
       (-6.0, FALSE, '2024-11-03 08:00:00', 3);

-- Yekaterinburg
INSERT INTO measurement (value, raining, created_at, sensor_id)
VALUES (0.0, TRUE, '2024-11-01 08:00:00', 4),
       (1.0, FALSE, '2024-11-01 14:00:00', 4),
       (-1.0, TRUE, '2024-11-01 20:00:00', 4),
       (-2.0, FALSE, '2024-11-02 08:00:00', 4),
       (-3.5, TRUE, '2024-11-02 14:00:00', 4),
       (-4.0, FALSE, '2024-11-02 20:00:00', 4),
       (0.5, TRUE, '2024-11-03 08:00:00', 4);

-- Kazan
INSERT INTO measurement (value, raining, created_at, sensor_id)
VALUES (2.0, FALSE, '2024-11-01 08:00:00', 5),
       (3.5, TRUE, '2024-11-01 14:00:00', 5),
       (1.0, FALSE, '2024-11-01 20:00:00', 5),
       (0.0, TRUE, '2024-11-02 08:00:00', 5),
       (-1.5, FALSE, '2024-11-02 14:00:00', 5),
       (-2.0, TRUE, '2024-11-02 20:00:00', 5),
       (0.0, FALSE, '2024-11-03 08:00:00', 5);

-- Nizhny Novgorod
INSERT INTO measurement (value, raining, created_at, sensor_id)
VALUES (1.5, TRUE, '2024-11-01 08:00:00', 6),
       (2.5, FALSE, '2024-11-01 14:00:00', 6),
       (0.5, TRUE, '2024-11-01 20:00:00', 6),
       (-1.0, FALSE, '2024-11-02 08:00:00', 6),
       (-3.0, TRUE, '2024-11-02 14:00:00', 6),
       (-4.0, FALSE, '2024-11-02 20:00:00', 6),
       (0.0, TRUE, '2024-11-03 08:00:00', 6);

-- Samara
INSERT INTO measurement (value, raining, created_at, sensor_id)
VALUES (3.0, FALSE, '2024-11-01 08:00:00', 7),
       (4.5, TRUE, '2024-11-01 14:00:00', 7),
       (2.0, FALSE, '2024-11-01 20:00:00', 7),
       (1.0, TRUE, '2024-11-02 08:00:00', 7),
       (0.0, FALSE, '2024-11-02 14:00:00', 7),
       (-1.0, TRUE, '2024-11-02 20:00:00', 7),
       (2.0, FALSE, '2024-11-03 08:00:00', 7);

