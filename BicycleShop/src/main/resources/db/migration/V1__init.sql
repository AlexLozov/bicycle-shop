CREATE TABLE users(
                        id BIGSERIAL PRIMARY KEY ,
                        username VARCHAR(30) NOT NULL UNIQUE ,
                        password VARCHAR(80) NOT NULL,
                        email VARCHAR(50) NOT NULL UNIQUE ,
                        created TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- дата добавления
                        updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- дата добавления
                        registration_status VARCHAR(30) NOT NULL ,
                        last_login TIMESTAMP,
                        deleted BOOLEAN NOT NULL DEFAULT FALSE
);



CREATE TABLE bicycles (
                          id SERIAL PRIMARY KEY,           -- уникальный идентификатор
                          name VARCHAR(30) NOT NULL UNIQUE,
                          brand VARCHAR(100) NOT NULL,     -- бренд (например Trek, Giant, Merida)
                          type VARCHAR(50) NOT NULL,       -- тип (mountain, road, bmx и т.п.)
                          price DECIMAL(10,2) NOT NULL,    -- цена
                          stock INT NOT NULL DEFAULT 0,    -- количество на складе
                          description VARCHAR(500),
                          image_url TEXT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- дата добавления
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- дата добавления
                          deleted BOOLEAN NOT NULL DEFAULT FALSE
);


INSERT INTO users(username, password, email, created, updated, registration_status, last_login, deleted)
VALUES
    ('fisrt', 'password1', 'first@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP, false),
    ('two', 'password2', 'two@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP, false),
    ('three', 'password3', 'three@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP, false);




INSERT INTO bicycles (name, brand, type, price, stock, description, image_url)
VALUES
    ('first','Trek', 'mountain', 1499.99, 12,'qwerty', 'https://zateya.md/wp-content/uploads/2022/05/scorpion-green-400x267.jpg'),
    ('second','Giant', 'road', 1899.50, 8, 'qwerty','https://zateya.md/wp-content/uploads/2022/05/scorpion-green-400x267.jpg'),
    ('third','Merida', 'bmx', 799.00, 15, 'qwerty','https://zateya.md/wp-content/uploads/2022/05/scorpion-green-400x267.jpg'),
    ('fourth','Specialized', 'hybrid', 1299.99,10, 'qwerty','https://zateya.md/wp-content/uploads/2022/05/scorpion-green-400x267.jpg'),
    ('fifth','Cannondale', 'electric', 2799.00, 5, 'qwerty','https://zateya.md/wp-content/uploads/2022/05/scorpion-green-400x267.jpg');
