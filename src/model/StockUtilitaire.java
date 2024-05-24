package Model;

public class StockUtilitaire {
    int id_stock_utilitaire;
    Ingredient ingredient;
    int quantite;

    
    public StockUtilitaire(int id_stock_utilitaire, Ingredient ingredient, int quantite) {
        this.id_stock_utilitaire = id_stock_utilitaire;
        this.ingredient = ingredient;
        this.quantite = quantite;
    }

    // getters
    public int getId_stock_utilitaire() {
        return id_stock_utilitaire;
    }
    public int getQuantite() {
        return quantite;
    }
    public Ingredient getIngredient() {
        return ingredient;
    }

    // setters
    public void setId_stock_utilitaire(int id_stock_utilitaire) {
        this.id_stock_utilitaire = id_stock_utilitaire;
    }
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
