package edu.uestc.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DataBase {
    private static DataBase dataBase = new DataBase();
    private Connection con;
    private DataBase(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "192.168.1.9:3306/test?useUnicode=true&characterEncoding=utf-8" ;
            String username = "root" ;
            String password = "woaizxl" ;
            con = DriverManager.getConnection(url , username , password );
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static  DataBase getInstance(){
        return dataBase;
    }

    public ResultSet query(String sql) throws Exception{
        return con.createStatement().executeQuery(sql);
    }

    public void execute(String sql) throws Exception{
        con.createStatement().execute(sql);
    }

}
