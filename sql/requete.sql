-- Total price of a recipe
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