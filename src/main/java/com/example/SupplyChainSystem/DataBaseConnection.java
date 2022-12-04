package com.example.SupplyChainSystem;
import java.sql.*;

public class DataBaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/supply_chain_system_majorproject";     //DB_URL - database URL/// for local machine
    private static final String USER = "root";
    private static final String PASS = "ptdr@2607";

    public Statement getStatement(){                                          // create method -getStatement()
        Statement stmnt = null;                                               // Statement is class, used to execute SQL queries
        Connection conn;
        try{
            conn = DriverManager.getConnection(DB_URL, USER,PASS);            // that JDBC connector helps in connecting database
            stmnt = conn.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
        return stmnt;
    }


    public ResultSet getQueryTable(String query){
        Statement stmnt = getStatement();                                     // call above stmnt part and execute queries and produce ResultSet
        try{                                                                  // Statement -CLASS,,, stmnt -object,,, and call to getStatement()method
            return stmnt.executeQuery(query);                                 // now call function -executeQuery()
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public int insertData(String query){
        Statement stmnt = getStatement();
        try{
            return stmnt.executeUpdate(query);                                 // update data / insertion
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }


//    public static void main(String[] args) {
//
//        DataBaseConnection dbConn = new DataBaseConnection();
//
//        String query = "SELECT * FROM customer";
//        ResultSet rs = dbConn.getQueryTable(query);
//        try{
//            while (rs != null && rs.next()){
//                System.out.println("Fetched Result");                              // now print our output
//                System.out.println("cid: "+rs.getInt("custid") +        // it will provide our data from mySQL query/data
//                        " name: "+rs.getString("first_name") +
//                        " email: "+rs.getString("email")
//                );
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
