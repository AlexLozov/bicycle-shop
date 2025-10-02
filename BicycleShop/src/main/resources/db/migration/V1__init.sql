CREATE TABLE bicycles (
                          id SERIAL PRIMARY KEY,           -- уникальный идентификатор
                          brand VARCHAR(100) NOT NULL,     -- бренд (например Trek, Giant, Merida)
                          model VARCHAR(100) NOT NULL,     -- модель велосипеда
                          type VARCHAR(50) NOT NULL,       -- тип (mountain, road, bmx и т.п.)
                          price DECIMAL(10,2) NOT NULL,    -- цена
                          stock INT NOT NULL DEFAULT 0,    -- количество на складе
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- дата добавления
);
