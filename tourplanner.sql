CREATE USER postgres PASSWORD 'postgres';
CREATE DATABASE tourplanner WITH OWNER postgres;

CREATE TABLE tour (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    start_location VARCHAR(255),
    end_location VARCHAR(255),
    transport_type VARCHAR(50),
    distance NUMERIC(8, 2), -- Assuming distance in kilometers with two decimal places
    estimated_time INTERVAL, -- PostgreSQL supports an INTERVAL type for time durations
    route_image_path VARCHAR(255), -- Stores the file path to the image on the file system
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tour_log (
    id SERIAL PRIMARY KEY,
    tour_id INTEGER NOT NULL REFERENCES tour(id),
    log_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    comment TEXT,
    difficulty INTEGER,
    log_distance NUMERIC(8, 2),
    log_time INTERVAL,
    rating INTEGER,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE address (
    id BIGSERIAL PRIMARY KEY,
    postcode INTEGER NOT NULL,
    city VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    fk_person BIGINT,
    FOREIGN KEY (fk_person) REFERENCES person(id) ON DELETE CASCADE
);

CREATE TABLE person (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  e_mail VARCHAR(255) UNIQUE NOT NULL,
  age INT NOT NULL
);
