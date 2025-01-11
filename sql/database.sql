CREATE DATABASE boulangerie;

\c boulangerie;

CREATE TABLE boulangerie_user (
    id_user SERIAL PRIMARY KEY,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    user_password VARCHAR(100) NOT NULL
);

CREATE TABLE category (
    id_category SERIAL PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL
);

CREATE TABLE recipe (
    id_recipe SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    recipe_description TEXT,
    id_category INT NOT NULL,
    cook_time TIME NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_date DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_category) REFERENCES category(id_category)
);

CREATE TABLE ingredient (
    id_ingredient SERIAL PRIMARY KEY,
    ingredient_name VARCHAR(255) NOT NULL,
    unit VARCHAR(50) NOT NULL, -- For example, grams, milliliters, teaspoons, etc.
    price INT NOT NULL DEFAULT 0 
);

ALTER TABLE ingredient ADD COLUMN est_base BOOLEAN NOT NULL DEFAULT FALSE;

CREATE TABLE recipe_ingredient (
    id_recipe INT,
    id_ingredient INT,
    quantity DECIMAL(10,2), -- To store the amount needed for each recipe
    PRIMARY KEY (id_recipe, id_ingredient),
    FOREIGN KEY (id_recipe) REFERENCES recipe(id_recipe),
    FOREIGN KEY (id_ingredient) REFERENCES ingredient(id_ingredient)
);

ALTER TABLE recipe_ingredient ADD COLUMN est_base BOOLEAN NOT NULL DEFAULT FALSE;

CREATE TABLE step (
    id_step SERIAL PRIMARY KEY,
    id_recipe INT NOT NULL,
    step_number INT NOT NULL,
    instruction TEXT NOT NULL,
    FOREIGN KEY (id_recipe) REFERENCES recipe(id_recipe)
);

CREATE TABLE review (
    id_review SERIAL PRIMARY KEY,
    id_user INT NOT NULL,
    id_recipe INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    review_date DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_user) REFERENCES boulangerie_user(id_user),
    FOREIGN KEY (id_recipe) REFERENCES recipe(id_recipe)
);

-- Stock Features

CREATE TABLE type_mvm (
    id_type SERIAL PRIMARY KEY,
    type_name VARCHAR(10) NOT NULL UNIQUE CHECK (type_name IN ('entree', 'sortie'))
);

INSERT INTO type_mvm (type_name) VALUES ('entree'), ('sortie');

CREATE TABLE stock_ingredient (
    id_stock SERIAL PRIMARY KEY,
    id_ingredient INT NOT NULL,
    id_type INT NOT NULL,
    qtt INT NOT NULL,
    FOREIGN KEY (id_ingredient) REFERENCES ingredient(id_ingredient),
    FOREIGN KEY (id_type) REFERENCES type_mvm(id_type)
);

CREATE TABLE stock_recipe (
    id_stock SERIAL PRIMARY KEY,
    id_recipe INT NOT NULL,
    id_type INT NOT NULL,
    qtt INT NOT NULL,
    FOREIGN KEY (id_recipe) REFERENCES recipe(id_recipe),
    FOREIGN KEY (id_type) REFERENCES type_mvm(id_type)
);

---------------
-- Sales Features

CREATE TABLE sale (
    id_sale SERIAL PRIMARY KEY,
    id_user INT NOT NULL,
    sale_date DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_user) REFERENCES boulangerie_user(id_user)
);

CREATE TABLE sale_recipe (
    id_sale INT,
    id_recipe INT,
    qtt INT NOT NULL,
    PRIMARY KEY (id_sale, id_recipe),
    FOREIGN KEY (id_sale) REFERENCES sale(id_sale),
    FOREIGN KEY (id_recipe) REFERENCES recipe(id_recipe)
);

