CREATE TABLE bicycles (
                          id SERIAL PRIMARY KEY,           -- уникальный идентификатор
                          brand VARCHAR(100) NOT NULL,     -- бренд (например Trek, Giant, Merida)
                          type VARCHAR(50) NOT NULL,       -- тип (mountain, road, bmx и т.п.)
                          price DECIMAL(10,2) NOT NULL,    -- цена
                          stock INT NOT NULL DEFAULT 0,    -- количество на складе
                          description VARCHAR(500),
                          image_url TEXT,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- дата добавления
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- дата добавления
);


INSERT INTO bicycles (brand, type, price, stock, description, image_url)
VALUES
    ('Trek', 'mountain', 1499.99, 12,'qwerty', 'https://zateya.md/wp-content/uploads/2022/05/scorpion-green-400x267.jpg'),
    ('Giant', 'road', 1899.50, 8, 'qwerty','https://zateya.md/wp-content/uploads/2022/05/scorpion-green-400x267.jpg'),
    ('Merida', 'bmx', 799.00, 15, 'qwerty','https://zateya.md/wp-content/uploads/2022/05/scorpion-green-400x267.jpg'),
    ('Specialized', 'hybrid', 1299.99,10, 'qwerty','https://zateya.md/wp-content/uploads/2022/05/scorpion-green-400x267.jpg'),
    ('Cannondale', 'electric', 2799.00, 5, 'qwerty','https://zateya.md/wp-content/uploads/2022/05/scorpion-green-400x267.jpg');
