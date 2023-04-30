DROP SCHEMA public CASCADE;
CREATE SCHEMA public;

CREATE TYPE academic_status AS ENUM (
    'SENIOR_RESEARCHER',
	'ASSOCIATE_PROFESSOR',
	'PROFESSOR'
);

CREATE TABLE users
(
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(32) UNIQUE,
    password_hash CHAR(60),
    first_name    VARCHAR(32) NOT NULL,
    middle_name   VARCHAR(32) NOT NULL,
    last_name     VARCHAR(32) NOT NULL,
    email         VARCHAR(32) NOT NULL UNIQUE,
    phone         VARCHAR(32),
    info          TEXT
);

CREATE TABLE faculties
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(64)  NOT NULL UNIQUE,
    website VARCHAR(24)  NOT NULL,
    email   VARCHAR(32)  NOT NULL,
    phone   VARCHAR(32)  NOT NULL,
    address VARCHAR(128) NOT NULL,
    info    TEXT
);

CREATE TABLE departments
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(128) NOT NULL UNIQUE,
    faculty_id BIGINT REFERENCES faculties (id),
    email      VARCHAR(32) NOT NULL,
    phone      VARCHAR(32) NOT NULL,
    info       TEXT
);

CREATE TABLE scientific_degrees
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL UNIQUE
);

CREATE TABLE lecturers
(
    department_id        BIGINT REFERENCES departments (id)        NOT NULL,
    scientific_degree_id BIGINT REFERENCES scientific_degrees (id) NOT NULL,
    academic_status      academic_status
) INHERITS(users);

CREATE TABLE specialties
(
    code SMALLINT     NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE academic_groups
(
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(16)                            NOT NULL,
    department_id  BIGINT REFERENCES departments (id)     NOT NULL,
    specialty_code SMALLINT REFERENCES specialties (code) NOT NULL,
    year           SMALLINT                               NOT NULL
);

CREATE TABLE department_specialties
(
    department_id  BIGINT REFERENCES departments (id)     NOT NULL,
    specialty_code SMALLINT REFERENCES specialties (code) NOT NULL,
    PRIMARY KEY (department_id, specialty_code)
);