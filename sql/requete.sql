-- Total price of a recipe
-- Total price of a recipe
CREATE VIEW recipe_total_price AS
SELECT 
    ri.id_recipe,
    SUM(ri.quantity * i.price) AS total_price
FROM 
    recipe_ingredient ri
JOIN 
    ingredient i ON ri.id_ingredient = i.id_ingredient
WHERE 
    ri.id_recipe = 1
GROUP BY 
    ri.id_recipe;

-- Current stock of ingredients
CREATE VIEW current_ingredient_stock AS
SELECT 
    si.id_ingredient,
    i.ingredient_name,
    SUM(CASE WHEN si.id_type = (SELECT id_type FROM type_mvm WHERE type_name = 'entree') THEN si.qtt ELSE 0 END) -
    SUM(CASE WHEN si.id_type = (SELECT id_type FROM type_mvm WHERE type_name = 'sortie') THEN si.qtt ELSE 0 END) AS current_quantity
FROM 
    stock_ingredient si
JOIN 
    ingredient i ON si.id_ingredient = i.id_ingredient
GROUP BY 
    si.id_ingredient, i.ingredient_name;

CREATE VIEW current_recipe_stock AS
SELECT 
    sr.id_recipe,
    r.title AS recipe_name,
    SUM(CASE WHEN sr.id_type = (SELECT id_type FROM type_mvm WHERE type_name = 'entree') THEN sr.qtt ELSE 0 END) -
    SUM(CASE WHEN sr.id_type = (SELECT id_type FROM type_mvm WHERE type_name = 'sortie') THEN sr.qtt ELSE 0 END) AS current_quantity
FROM 
    stock_recipe sr
JOIN 
    recipe r ON sr.id_recipe = r.id_recipe
GROUP BY 
    sr.id_recipe, r.title;

--Requette ingredients specifique
CREATE OR REPLACE VIEW specific_ingredients AS
SELECT
    r.id_recipe,
    r.title,
    r.recipe_description,
    r.cook_time,
    r.created_by,
    r.created_date,
    r.id_category,
    i.id_ingredient AS ingredient_id
FROM 
    recipe r
JOIN 
    recipe_ingredient ri ON r.id_recipe = ri.id_recipe
JOIN 
    ingredient i ON ri.id_ingredient = i.id_ingredient;


SELECT * FROM specific_ingredients 
WHERE 
    ingredient_id = 1;