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