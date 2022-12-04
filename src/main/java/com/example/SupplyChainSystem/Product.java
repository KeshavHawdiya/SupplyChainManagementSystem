package com.example.SupplyChainSystem;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Product {

    public SimpleIntegerProperty id;                      // we need to bind a TableView - so adding these Attributes
    public SimpleStringProperty name;
    public SimpleDoubleProperty price;

    Product(int id, String name, double price){           // constructor
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }

    public static ObservableList<Product> getAllProducts(){
        DataBaseConnection dbConn = new DataBaseConnection();
        ObservableList<Product> data = FXCollections.observableArrayList();
        String selectAllProducts = "SELECT * FROM product";
        try{
            ResultSet rs = dbConn.getQueryTable(selectAllProducts);               // fetching data with help of query
            while (rs.next()){
                data.add(new Product(rs.getInt("prodId"),              // putting all data in list && then return the data
                        rs.getString("name"),
                        rs.getDouble("price")
                )
                );
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }


    public static ObservableList<Product> getProductsOnSearch(String name){
        DataBaseConnection dbConn = new DataBaseConnection();
        ObservableList<Product> data = FXCollections.observableArrayList();
        String selectAllProducts = String.format("SELECT * FROM product WHERE name like '%%%s%%'", name.toLowerCase());
        try{
            ResultSet rs = dbConn.getQueryTable(selectAllProducts);               // fetching data with help of query
            while (rs.next()){
                data.add(new Product(rs.getInt("prodId"),              // putting all data in list && then return the data
                                rs.getString("name"),
                                rs.getDouble("price")
                        )
                );
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }




    // getter-setter methods for id, name & price
    public int getId(){
        return id.get();
    }
    public String getName(){
        return name.get();
    }
    public double getPrice(){
        return price.get();
    }
}
