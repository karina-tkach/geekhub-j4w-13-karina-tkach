CREATE TABLE bookings
(
    id SERIAL PRIMARY KEY,
    show_id INT NOT NULL,
    seat_id INT NOT NULL,
    user_id INT NOT NULL,
    uuid UUID NOT NULL,
    show_details VARCHAR(255) NOT NULL,
    FOREIGN KEY (show_id) REFERENCES shows(id) ON DELETE CASCADE,
    FOREIGN KEY (seat_id) REFERENCES show_seats(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
