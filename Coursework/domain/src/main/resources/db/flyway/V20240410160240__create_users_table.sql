CREATE TABLE users
(
    id        SERIAL PRIMARY KEY,
    firstName VARCHAR NOT NULL,
    lastName  VARCHAR NOT NULL,
    password  VARCHAR NOT NULL,
    email     VARCHAR UNIQUE NOT NULL,
    role      VARCHAR NOT NULL
);
INSERT INTO Users (firstName, lastName, password, email, role) VALUES
                                                                   ('John', 'Doe', '$2a$12$R4FDyPJfpAjtnDhK4PInm.R5LTRtEZzJPNwrc5PkK.6kqpM1XI5ii', 'super_admin@gmail.com', 'SUPER_ADMIN'),
                                                                   ('Julia', 'Fox', '$2a$12$ynJnidcZ4T1.GGeNQTXdm.GNjKtb2CSgm6bWQh4wlFSx.xGbAKYMi', 'admin@gmail.com', 'ADMIN'),
                                                                   ('John', 'Smith', '$2a$12$lZsO/8rBbuNwgpTyAQScLeOsANBfs6qfcO1c493r2F/rREtztCasW', 'user@gmail.com', 'USER');
