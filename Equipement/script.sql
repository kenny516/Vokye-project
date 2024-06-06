CREATE VIEW v_equipement AS
SELECT 
    u.id_utilitaire as id,
    u.nom AS nom,
    su.quantite AS quantite,
    un.id_unite AS id_unite,
    un.nom_unite AS nom_unite
FROM 
    Utilitaire u
JOIN 
    Stock_utilitaire su ON u.id_utilitaire = su.id_utilitaire
JOIN 
    Unite un ON u.id_unite = un.id_unite;
