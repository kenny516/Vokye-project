CREATE OR REPLACE VIEW vue_somme_ventes_point_vente AS
SELECT 
    pv.id_point_vente,
    pv.localisation,
    v.date_vente,
    SUM(v.quantite * p.prix) AS total_ventes
FROM 
    point_vente pv
JOIN 
    vente v ON pv.id_point_vente = v.id_point_vente
JOIN 
    Produit p ON v.id_produit = p.id_produit
GROUP BY 
    pv.id_point_vente, 
    pv.localisation,
    v.date_vente;
