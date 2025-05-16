INSERT INTO buildings (name, address) VALUES
                                          ('Корпус 1', 'Университетская 15'),
                                          ('Корпус 2', 'Университетская 10'),
                                          ('Корпус 3',  'Университетская 7');

INSERT INTO rooms (building_id, room_number, capacity) VALUES
                                                           (1, '101', 2),
                                                           (1, '102', 2),
                                                           (2, '201', 3),
                                                           (2, '202', 1),
                                                           (3, '301', 4);

INSERT INTO students (first_name, last_name, date_of_birth, email, phone) VALUES
                                                                              ('Иван',    'Петров',    '2001-04-15', 'ivan.petrov@example.com',    '+7905012345'),
                                                                              ('Ольга',    'Иванова',   '2002-07-22', 'olga.ivanova@example.com',   '+7905234568'),
                                                                              ('Дмитрий',  'Соколов',   '2000-11-30', 'dmitry.sokolov@example.com', '+7901234569');

INSERT INTO assignments (student_id, room_id, assign_date, leave_date) VALUES
                                                                           (1, 1, '2025-01-10', NULL),          -- Иван в комнате 101, всё еще проживает
                                                                           (2, 2, '2025-02-01', '2025-03-01'),  -- Ольга в комнате 102, выехала
                                                                           (3, 3, '2025-03-15', NULL);          -- Дмитрий в комнате 201, всё еще проживает

INSERT INTO payments (student_id, amount, payment_date, description) VALUES
                                                                         (1, 500.00, '2025-01-05', 'Первый взнос'),
                                                                         (1, 500.00, '2025-02-05', 'Второй взнос'),
                                                                         (2, 300.00, '2025-02-10', 'Оплата за февраль'),
                                                                         (3, 800.00, '2025-03-10', 'Аванс за март'),
                                                                         (3, 800.00, '2025-04-10', 'Аванс за апрель');

INSERT INTO roles (name) VALUES
                             ('ADMIN'),
                             ('OPERATOR'),
                             ('WATCHER');

INSERT INTO employees (username, password, first_name, last_name, email) VALUES
                                                                             ('admin',    'adminpass',    'Alice', 'Admin',   'alice.admin@example.com'),
                                                                             ('operator', 'operatorpass', 'Bob',   'Operator','bob.operator@example.com'),
                                                                             ('watcher',  'watcherpass',  'Carol', 'Watcher', 'carol.watcher@example.com');

INSERT INTO employee_roles (employee_id, role_id) VALUES
                                                      (1, 1),
                                                      (1, 2),
                                                      (2, 2),
                                                      (3, 3);