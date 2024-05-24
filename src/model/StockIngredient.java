package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StockIngredient {
    int id_stock_ingredient;
    Ingredient ingredient;
    int quantite;
    
    public StockIngredient(int id_stock_ingredient, Ingredient ingredient, int quantite) {
        setId_stock_ingredient(id_stock_ingredient);
        setIngredient(ingredient);
        setQuantite(quantite);
    }
    
    // getters
    public int getId_stock_ingredient() {
        return id_stock_ingredient;
    }
    
    public Ingredient getIngredient() {
        return ingredient;
    }
    public int getQuantite() {
        return quantite;
    }

    // setters
    public void setId_stock_ingredient(int id_stock_ingredient) {
        this.id_stock_ingredient = id_stock_ingredient;
    }
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    // methods
    
    // methode qui verifie le stock
    public List<Ingredient> check_stock(Connection connection) throws Exception {
        List<Ingredient> ingredientsEnRupture = new ArrayList<>();
        
        String sql = "SELECT i.* FROM Ingredient i JOIN Stock_ingredient si ON i.id_ingredient = si.id_ingredient WHERE si.quantite = 0";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        Unite unite = new Unite();

        while(resultSet.next()) {
            Ingredient ingredient = new Ingredient();
            ingredient.setId_ingredient(resultSet.getInt("id_ingredient"));
            ingredient.setNom(resultSet.getString("nom"));
            unite.setId_unite(resultSet.getInt("id_unite"));
            ingredient.setUnite(unite);
            ingredientsEnRupture.add(ingredient);
        }

        // Fermer les ressources
        resultSet.close();
        preparedStatement.close();

        return ingredientsEnRupture;
    }
}
