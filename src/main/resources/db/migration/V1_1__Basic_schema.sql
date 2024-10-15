CREATE TABLE users(
                      id BIGSERIAL PRIMARY KEY,
                      username VARCHAR(50) NOT NULL,
                      email VARCHAR(100) UNIQUE NOT NULL,
                      password VARCHAR(50) NOT NULL,
                      role VARCHAR(20) NOT NULL
);

CREATE TABLE tasks(
                      id BIGSERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      description TEXT,
                      is_completed BOOLEAN DEFAULT false,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      completed_at TIMESTAMP,
                      user_id BIGINT,
                      CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_user_email ON users(email);
CREATE INDEX idx_task_user_id ON tasks(user_id);