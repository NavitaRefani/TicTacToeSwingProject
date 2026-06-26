CREATE TABLE players (
                         id SERIAL PRIMARY KEY,
                         username VARCHAR(50) UNIQUE NOT NULL,
                         password VARCHAR(100) NOT NULL,
                         wins INTEGER DEFAULT 0,
                         losses INTEGER DEFAULT 0,
                         draws INTEGER DEFAULT 0,
                         score INTEGER DEFAULT 0
);

INSERT INTO players (username, password, wins, losses, draws, score)
VALUES
    ('student1', '12345', 0, 0, 0, 0),
    ('student2', '12345', 0, 0, 0, 0),
    ('student3', '12345', 0, 0, 0, 0);
