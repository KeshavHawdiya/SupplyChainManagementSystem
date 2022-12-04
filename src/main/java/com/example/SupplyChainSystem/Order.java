package com.example.SupplyChainSystem;

public class Order {
    public static boolean placeSingleOrder(Product product, String customerEmail){
        String orderQuery = String.format("INSERT INTO orders(quantity, cust_Id, product_Id, status) VALUES(1, (SELECT custid FROM customer WHERE email = '%s'), %s, 'ORDERED')",
                customerEmail, product.getId()
        );

        DataBaseConnection dbConn = new DataBaseConnection();
        if(dbConn.insertData(orderQuery) >= 1){
            return true;
        }

        System.out.println(orderQuery);

        return false;
    }
}
