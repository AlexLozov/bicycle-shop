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

--------------------------------------------------------------------

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

--------------------------------------------------------------------


-- Каждому пользователю — одна корзина.
-- При удалении пользователя корзина удаляется автоматически.
CREATE TABLE shopping_cart (
                               id BIGSERIAL PRIMARY KEY,
                               user_id BIGINT UNIQUE NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                               created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);



--------------------------------------------------------------------



-- Связь многие ко многим между корзиной и велосипедами.
-- Можно хранить количество каждого велосипеда (quantity).
CREATE TABLE cart_items (
                            id BIGSERIAL PRIMARY KEY,
                            cart_id BIGINT NOT NULL REFERENCES shopping_cart(id) ON DELETE CASCADE,
                            bicycle_id BIGINT NOT NULL REFERENCES bicycles(id) ON DELETE CASCADE,
                            quantity INT NOT NULL DEFAULT 1,
                            UNIQUE (cart_id, bicycle_id)
);



--------------------------------------------------------------------



INSERT INTO bicycles (name, brand, type, price, stock, description, image_url)
VALUES
    ('first','Trek', 'mountain', 1499.99, 12,'qwerty', 'https://zateya.md/wp-content/uploads/2022/05/scorpion-green-400x267.jpg'),
    ('second','Giant', 'road', 1899.50, 8, 'qwerty','https://zateya.md/wp-content/uploads/2022/05/scorpion-green-400x267.jpg'),
    ('third','Merida', 'bmx', 799.00, 15, 'qwerty','https://zateya.md/wp-content/uploads/2022/05/scorpion-green-400x267.jpg'),
    ('fourth','Specialized', 'hybrid', 1299.99,10, 'qwerty','https://zateya.md/wp-content/uploads/2022/05/scorpion-green-400x267.jpg'),
    ('fifth','Cannondale', 'electric', 2799.00, 5, 'qwerty','https://zateya.md/wp-content/uploads/2022/05/scorpion-green-400x267.jpg');


--------------------------------------------------------------------




INSERT INTO users(username, password, email, created, updated, registration_status, last_login, deleted)
VALUES
    ('fisrt', 'password1', 'first@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP, false),
    ('two', 'password2', 'two@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP, false),
    ('three', 'password3', 'three@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP, false);



--------------------------------------------------------------------




INSERT INTO shopping_cart (user_id, created, updated)
VALUES
    (1, CURRENT_TIMESTAMP,CURRENT_TIMESTAMP), -- корзина для alex
    (2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP), -- корзина для maria
    (3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);



--------------------------------------------------------------------




INSERT INTO cart_items (cart_id, bicycle_id, quantity)
VALUES
    (1, 1, 1),
    (2, 2, 1),
    (3, 3, 1);
