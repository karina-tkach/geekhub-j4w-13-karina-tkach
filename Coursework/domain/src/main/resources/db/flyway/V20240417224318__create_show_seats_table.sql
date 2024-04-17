CREATE TABLE show_seats
(
    id SERIAL PRIMARY KEY,
    number INT NOT NULL,
    is_booked BOOLEAN NOT NULL,
    hall_id INT NOT NULL,
    show_id INT NOT NULL,
    FOREIGN KEY (hall_id) REFERENCES halls(id) ON DELETE CASCADE,
    FOREIGN KEY (show_id) REFERENCES shows(id) ON DELETE CASCADE
);
