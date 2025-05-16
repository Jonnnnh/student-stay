-- V1__create.sql

CREATE TABLE buildings (
                           id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           address VARCHAR(255) NOT NULL
);

CREATE TABLE rooms (
                       id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       building_id INT NOT NULL REFERENCES buildings(id),
                       room_number VARCHAR(10) NOT NULL,
                       capacity INT NOT NULL
);

CREATE TABLE students (
                          id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          date_of_birth DATE NOT NULL,
                          email VARCHAR(100),
                          phone VARCHAR(20)
);

CREATE TABLE assignments (
                             id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                             student_id INT NOT NULL REFERENCES students(id),
                             room_id INT NOT NULL REFERENCES rooms(id),
                             assign_date DATE NOT NULL,
                             leave_date DATE
);

CREATE TABLE payments (
                          id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                          student_id INT NOT NULL REFERENCES students(id),
                          amount NUMERIC(10,2) NOT NULL,
                          payment_date DATE NOT NULL,
                          description VARCHAR(255)
);

CREATE TABLE roles (
                       id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE employees (
                           id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           username VARCHAR(50) NOT NULL UNIQUE,
                           password VARCHAR(255) NOT NULL,
                           first_name VARCHAR(50) NOT NULL,
                           last_name VARCHAR(50) NOT NULL,
                           email VARCHAR(100) NOT NULL UNIQUE,
                           created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE employee_roles (
                                employee_id INT NOT NULL REFERENCES employees(id),
                                role_id INT NOT NULL REFERENCES roles(id),
                                PRIMARY KEY (employee_id, role_id)
);