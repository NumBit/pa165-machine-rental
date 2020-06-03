INSERT INTO machine (id, description, manufacturer, name, price) VALUES
(1, 'machine description', 'manufacturer', 'Best machine', 100),
(2, 'machine description2', 'manufacturer', 'Cool machine', 220),
(3, 'machine description3', 'manufacturer', 'New machine', 370),
(4, 'machine description4', 'manufacturer', 'Worst machine', 123);

INSERT INTO revision (id, note, revision_date, revision_time, machine_id) VALUES
(1, 'revision note1', '2020-01-06', '18:47:52', 1 ),
(2, 'revision note2', '2020-02-16', '12:55:22', 2 ),
(3, 'revision note3', '2020-03-01', '10:30:00', 3 ),
(4, 'revision note4', '2020-03-22', '12:34:22', 3 ),
(5, 'revision note5', '2020-02-08', '09:09:09', 3 );

INSERT INTO abstract_user (id, login, password_hash) VALUES
(2, 'user2', '$2a$04$IdVOM5VFXhArzinEQy5ZVekSknmh/j28iJyCR.P0AyU51xmbg87jS'),
(3, 'user3', '$2a$04$8KdWyJEaB63mB9Jl8vkdfeo1iUFO9dsbpABfUs9Bs83vehFUOBr4G'),
(4, 'user4', '$2a$04$OgFiL77zzWub0H.BW8WAvOYOtevSb17paAD6mOiB7rlX/4p0Qgs0m'),
(5, 'admin1', '$2a$04$OIWC5fEu4OdeXPJ/adyF6uOiuTSTnrQMdbdh.rzj2Uiag6Tph7A7W'),
(6, 'admin2', '$2a$04$hISVF2zNVAyCoi6dr0n6euachVgJY1emMC9vEzZHRjoIRLkDMXDke'),
(7, 'user7', '$2a$04$QqtebQmNC1LxXAsJji368uDrp2kEQFZqPlHT5EdcVgDK0EG06Ac.G');

INSERT INTO admin (name, sure_name, id) VALUES
('John', 'Smith', 5),
('Morty', 'Smith', 6);

INSERT INTO customer (email, legal_form, id) VALUES
('user7@gmail.com', 0, 7),
('user2@gmail.com', 0, 2),
('user3@gmail.com', 1, 3),
('user4@gmail.com', 0, 4);


INSERT INTO rental (id, description, rental_date, return_date, customer_id, machine_id) VALUES
(1, 'long term', '2020-02-02', '2020-03-02', 7, 1),
(2, 'long term', '2020-02-28', '2020-03-01', 2, 3),
(3, 'long term', '2020-03-02', '2020-03-04', 2, 4),
(4, 'short term', '2020-04-05', '2020-04-07', 3, 2),
(5, 'short term', '2020-05-05', '2020-05-10', 4, 2),
(6, 'short term', '2020-01-02', '2020-01-03', 4, 1);