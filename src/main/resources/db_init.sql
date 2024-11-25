CREATE TABLE IF NOT EXISTS sensor
(
    id   int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    name varchar(100) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS measurement
(
    id        int PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    value     double precision NOT NULL,
    raining   boolean NOT NULL,
    created_at timestamp NOT NULL,
    sensor_id int REFERENCES sensor (id)
);
