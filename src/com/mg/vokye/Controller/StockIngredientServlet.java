package com.mg.vokye.Controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import com.google.gson.Gson;
import com.mg.vokye.bdd.Connexion;
import com.mg.vokye.function.Function;
import com.mg.vokye.model.Ingredient;
import com.mg.vokye.model.Produit;
import com.mg.vokye.model.StockIngredient;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StockIngredientServlet extends HttpServlet {
    

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        Connexion connect = new Connexion();
        try (Connection c = connect.getConnection()) {
            if (pathInfo == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String[] pathParts = pathInfo.split("/"); // maka an le valeur apres '/'
            
            if (pathParts.length < 2) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            String viewName = pathParts[1];
            
            try {
                String jsonData = null;
                StockIngredient stockIngredient = new StockIngredient();
                switch (viewName) {
                    case "vStockIngredient":
                        List<Ingredient> listIngredients = stockIngredient.check_stock(c);
                        jsonData = new Gson().toJson(listIngredients);
                        break;
                    
                    case "listeProduit":
                        List<Produit> listProduits = Function.getListProduit();
                        jsonData = new Gson().toJson(listProduits);
                        break;
                    
                        // afaka anapina 

                    default:
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
                if (jsonData != null) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(jsonData);
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (ServletException e) {
            throw e;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
