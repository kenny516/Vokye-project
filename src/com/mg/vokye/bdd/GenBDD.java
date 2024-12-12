package com.mg.vokye.bdd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mg.vokye.bdd.annotation.*;
import java.lang.reflect.Field;

public class GenBDD {

    // com.mg.vokye.function for the insertion
    public void save(Connection connex) throws Exception {
        String nameTable = this.getClass().getAnnotation(Table.class).nom();
        Object[] insertData = this.prepaInsert();

        String columns = (String) insertData[0];
        String placeholders = (String) insertData[1];
        List<Object> values = (List<Object>) insertData[2];

        String query = "INSERT INTO " + nameTable + " (" + columns + ") VALUES (" + placeholders + ")";

        try (PreparedStatement preparedStatement = connex.prepareStatement(query)) {
            // Réglage des valeurs
            int index = 1;
            for (Object value : values) {
                preparedStatement.setObject(index++, value);
            }
            // Exécution de l'insertion
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error executing insert", e);
        }
    }

    public Object[] prepaInsert() throws IllegalAccessException {
        StringBuilder columns = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        List<Object> values = new ArrayList<>();

        Class<?> clazz = this.getClass();

        // Parcourir tous les champs de la classe
        for (Field field : clazz.getDeclaredFields()) {
            // Vérifier si le champ est annoté avec @Colonne
            if (field.isAnnotationPresent(Colonne.class)) {
                Colonne colonne = field.getAnnotation(Colonne.class);

                // Rendre le champ accessible s'il est privé
                field.setAccessible(true);
                // Obtenir la valeur du champ
                Object value = field.get(this);

                // Construire la liste des colonnes et des placeholders
                if (columns.length() > 0) {
                    columns.append(", ");
                    placeholders.append(", ");
                }
                columns.append(colonne.nom());
                placeholders.append("?");
                values.add(value);
            }
        }
        return new Object[] { columns.toString(), placeholders.toString(), values };
    }

    public void getById(Connection connex, int id) throws Exception {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // Obtenir le nom de la table
            String nameTable = this.getClass().getAnnotation(Table.class).nom();

            // Construire la requête SQL
            String query = "SELECT * FROM " + nameTable + " WHERE id_" + nameTable.toLowerCase() + " = ?";
            preparedStatement = connex.prepareStatement(query);
            preparedStatement.setInt(1, id);

            // Exécuter la requête et traiter le résultat
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Parcourir les champs de la classe
                for (Field field : this.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(Colonne.class)) {
                        Colonne colonne = field.getAnnotation(Colonne.class);
                        String columnName = colonne.nom();

                        // Rendre le champ accessible s'il est privé
                        field.setAccessible(true);

                        // Déterminer le type du champ et le remplir avec la valeur correspondante
                        if (field.getType() == int.class) {
                            field.setInt(this, resultSet.getInt(columnName));
                        } else if (field.getType() == double.class) {
                            field.setDouble(this, resultSet.getDouble(columnName));
                        } else if (field.getType() == String.class) {
                            field.set(this, resultSet.getString(columnName));
                        } else if (field.getType() == java.util.Date.class) {
                            field.set(this, resultSet.getDate(columnName));
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw (e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception ex) {
                throw (ex);
            }
        }
    }

    public <T> List<T> getAll(Connection connex) throws Exception {
        List<T> liste_object = new ArrayList<>();
        Statement state = null;

        try {
            String nameTable = this.getClass().getAnnotation(Table.class).nom();
            String sql = "SELECT * FROM " + nameTable;
            state = connex.createStatement();

            ResultSet res = state.executeQuery(sql);
            while (res.next()) {
                T instance = (T) this.getClass().getDeclaredConstructor().newInstance();
                for (Field field : this.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(Colonne.class)) {
                        Colonne colonne = field.getAnnotation(Colonne.class);
                        String columnName = colonne.nom();

                        field.setAccessible(true);

                        if (field.getType() == int.class) {
                            field.setInt(instance, res.getInt(columnName));
                        } else if (field.getType() == double.class) {
                            field.setDouble(instance, res.getDouble(columnName));
                        } else if (field.getType() == String.class) {
                            field.set(instance, res.getString(columnName));
                        } else if (field.getType() == Date.class) {
                            field.set(instance, res.getDate(columnName));
                        } else if (field.getType() == java.util.Date.class) {
                            field.set(instance, (java.util.Date) res.getDate(columnName));
                        }
                    }
                }
                liste_object.add(instance);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (state != null) {
                    state.close();
                }
            } catch (Exception ex) {
                throw ex;
            }
        }
        return liste_object;
    }

    public void supp(Connection connex, int id) throws Exception {
        Connection conn = null;
        PreparedStatement preparedStatement = null;

        try {
            String nameTable = this.getClass().getAnnotation(Table.class).nom();
            String query = "DELETE FROM " + nameTable + " WHERE id_" + nameTable.toLowerCase() + " = ? ";
            conn = connex;
            conn.setAutoCommit(false);
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                throw (ex);
            }
            throw (e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.commit();
                    conn.close();
                }
            } catch (Exception e) {
                throw (e);
            }
        }
    }

    public void update(Connection connex, int id) throws Exception {
        PreparedStatement preparedStatement = null;
        try {
            String nameTable = this.getClass().getAnnotation(Table.class).nom();
            StringBuilder queryBuilder = new StringBuilder("UPDATE " + nameTable + " SET ");
    
            List<Object> values = new ArrayList<>();
            int index = 0;
    
            for (Field field : this.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Colonne.class)) {
                    Colonne colonne = field.getAnnotation(Colonne.class);
                    String columnName = colonne.nom();
    
                    field.setAccessible(true);
                    Object value = field.get(this);
    
                    if (index > 0) {
                        queryBuilder.append(", ");
                    }
                    queryBuilder.append(columnName).append(" = ?");
                    values.add(value);
                    index++;
                }
            }
    
            queryBuilder.append(" WHERE id_" + nameTable + " = ?");
            String query = queryBuilder.toString();
    
            preparedStatement = connex.prepareStatement(query);
    
            for (int i = 0; i < values.size(); i++) {
                Object value = values.get(i);
                if (value instanceof Integer) {
                    preparedStatement.setInt(i + 1, (Integer) value);
                } else if (value instanceof Double) {
                    preparedStatement.setDouble(i + 1, (Double) value);
                } else if (value instanceof String) {
                    preparedStatement.setString(i + 1, (String) value);
                } else if (value instanceof Date) {
                    preparedStatement.setDate(i + 1, (Date) value);
                }
            }
    
            preparedStatement.setInt(values.size() + 1, id);
    
            preparedStatement.executeUpdate();
            connex.commit();
        } catch (Exception e) {
            try {
                if (connex != null) {
                    connex.rollback();
                }
            } catch (Exception ex) {
                throw ex;
            }
            throw e;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }
    

}