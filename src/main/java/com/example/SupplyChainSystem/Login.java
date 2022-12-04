package com.example.SupplyChainSystem;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;

public class Login {

    static DataBaseConnection dbConn = new DataBaseConnection();

    // encryption functionality
    public static byte[] getSHA(String input){                                     // read about- Encryption in java
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));              // read about CharacterSet,,, here md takes input as characterSet & returns hashed values in byte
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    static String getEncryptedPassword(String password){
        String encryptedPassword = "";
        BigInteger number = new BigInteger(1, getSHA(password));
        StringBuilder hexString = new StringBuilder(number.toString(16));
        encryptedPassword = hexString.toString();
        return encryptedPassword;
    }

    public static boolean customerLogin(String email, String password){            // Read about Salted-Hashing related about encryption
        try{
            //String query = String.format("SELECT * FROM customer WHERE email = '%s' and password = '%s'", email, password);                       // for normal password
            String query = String.format("SELECT * FROM customer WHERE email = '%s' and password = '%s'", email, getEncryptedPassword(password));   // with encrypted form of password
            ResultSet rs = dbConn.getQueryTable(query);
            if(rs == null){                                                         // one more check- if ResultSet(userID & password) is empty
                return false;
            }
            if(rs.next()){
                return true;
            }
            else return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


//    public static void main(String[] args) {
//        System.out.println(customerLogin("keshav@gmail.com", "abc"));    // to confirm-check out that it's providing TRUE or not
//        System.out.println(getEncryptedPassword("def"));              // to print our password in encrypted form
//    }

}
