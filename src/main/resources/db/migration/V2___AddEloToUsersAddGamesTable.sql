ALTER TABLE users ADD COLUMN elo_rating INT DEFAULT 1000;

CREATE TABLE games
(
    id              BIGSERIAL PRIMARY KEY,
    white_player_id BIGINT REFERENCES users (id),
    black_player_id BIGINT REFERENCES users (id),

    status          VARCHAR(20)  NOT NULL,
    winner          VARCHAR(20),

    current_fen     VARCHAR(255) NOT NULL,
    pgn             TEXT,

    time_control    VARCHAR(20),

    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);