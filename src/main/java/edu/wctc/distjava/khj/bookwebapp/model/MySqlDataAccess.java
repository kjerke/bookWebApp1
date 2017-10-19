/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.khj.bookwebapp.model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.Vector;

/**
 *
 * @author kevinjerke
 */
public class MySqlDataAccess implements DataAccess {
        private final int ALL_RECORDS = 0;
        private final boolean DEBUG = true;

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
   

        @Override
    public void openConnection(String driverClass, 
            String url, String userName, String password) 
            throws ClassNotFoundException, SQLException {
       if (driverClass == null || driverClass.length() < 5) {
            throw new IllegalArgumentException("Invalid driverClass provided. Cannot connect to the Database");
        }
        if (url == null || url.length() < 5) {
            throw new IllegalArgumentException("Invalid URL provided. Cannot connext to the Database");
        }
        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("Invalid user name provided.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Invalid password provided.");
        }

        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }

    
        @Override
    public void closeConnection() throws SQLException {
    if (conn != null) {
            conn.close();
        }
    }
       
        @Override
    public int createRecord(String tableName, List<String> colNames, 
            List<Object> colValues) throws SQLException {
        
        String sql = "INSERT INTO " + tableName + " ";

        StringJoiner sj = new StringJoiner(", ", "(", ")");
        for (String col : colNames) {
            sj.add(col);
        }

        sql += sj.toString() + " VALUES ";

        //reset the String Joiner by recalling the constructor
        sj = new StringJoiner(", ", "(", ")");
        for (Object value : colValues) {
            sj.add("?");
        }

        sql += sj.toString();

        if (DEBUG) { //turn debug off  (set to false) if going into production
            System.out.println(sql);
        }

        pstmt = conn.prepareStatement(sql);

        //set values for each ? as objects. conversion happens automatically
        for (int i = 1; i <= colValues.size(); i++) {
            pstmt.setObject(i, colValues.get(i - 1));
        }

        return pstmt.executeUpdate();
    }

    
        @Override
    public int deleteRecordById(String tableName, String pkColName, 
            Object pkValue) throws ClassNotFoundException, 
            SQLException {
        
        String sql = "DELETE FROM " + tableName + " WHERE " 
                + pkColName + " = ?";
        
 
        pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1, pkValue);
        return pstmt.executeUpdate();
     
    }
    
    /**
     * Returns records from a table. Requires and open connection.
     * @param tableName
     * @param maxRecords
     * @return
     * @throws SQLException 
     */
    
        @Override
    public final List<Map<String, Object>> findRecordById(String tableName,
            String pkColName, Object pkValue)
            throws SQLException {
        //validation

        //logic
        String sql = "SELECT * FROM " + tableName + " WHERE " + pkColName + " = ?";

        pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1, pkValue);

        if (DEBUG) { //turn debug off  (set to false) if going into production
            System.out.println(sql);
        }

        rs = pstmt.executeQuery();

        Map<String, Object> record = new LinkedHashMap<>();

                ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount(); //be careful, this is 1 based numbering, not 0 based

        List<Map<String, Object>> rawData = new Vector<>();

        while (rs.next()) {
            record = new LinkedHashMap<>();
            for (int colNum = 1; colNum <= colCount; colNum++) {
                //record key put in map,  record value
                record.put(rsmd.getColumnName(colNum), rs.getObject(colNum));
            }
            rawData.add(record);
        }

        return rawData;
    }
        @Override
    public List<Map<String,Object>> getAllRecords(String tableName, int maxRecords) 
            throws SQLException, ClassNotFoundException {
        
    List<Map<String, Object>> rawData = new Vector<>();

        String sql = "";

        //be aware that this code block in vulnerable to sql injection
        //will learn solution in another lesson after 9/26/2017
        if (maxRecords > ALL_RECORDS) {
            sql = "select * from " + tableName + " limit " + maxRecords;
        } else {
            sql = "select * from " + tableName;
        }

        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        ResultSetMetaData rsmd = rs.getMetaData();
        int colCount = rsmd.getColumnCount(); //be careful, this is 1 based numbering, not 0 based

        Map<String, Object> record = null;

        while (rs.next()) {
            record = new LinkedHashMap<>();
            for (int colNum = 1; colNum <= colCount; colNum++) {
                //record key put in map,  record value
                record.put(rsmd.getColumnName(colNum), rs.getObject(colNum));
            }
            rawData.add(record);
        }

        return rawData; 
    }
    
        @Override
 public final int updateRecordById(String tableName, List<String> colNames,
            List<Object> colValues, String pkColName, Object pkValue) throws SQLException {

        //validate here:
        //null/ empty table name, min size of 1 for lists, make sure the size of colNames and colValues should match
        //pkcolName not null/ empty, pkVal not null
        //logic
        String sql = "UPDATE " + tableName + " SET ";

        StringJoiner sj = new StringJoiner(" = ?, ", "", " =? ");

        //adds the ? to the final item in the colNames list
        for (String col : colNames) {
            sj.add(col);

        }

        sql += sj.toString()
                + "WHERE " + pkColName + " = ?";

        if (DEBUG) { //turn debug off  (set to false) if going into production
            System.out.println(sql);
        }

        pstmt = conn.prepareStatement(sql);

        //set values for each ? as objects. conversion happens automatically
        for (int i = 1; i <= colValues.size(); i++) {
            pstmt.setObject(i, colValues.get(i - 1));

            //place this final setObject outside forloop
            if (i == colValues.size()) {
                pstmt.setObject((i + 1), pkValue);
            }
        }

        return pstmt.executeUpdate();

    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        
        DataAccess db = new MySqlDataAccess();
        
        db.openConnection( "com.mysql.jdbc.Driver",
               "jdbc:mysql://localhost:3306/book",
                "root", "admin"
               );
        
        
        int recsAdded = db.createRecord("author", 
                Arrays.asList("author_name", "date_added"), 
                Arrays.asList("Bob Jones","2010-02-11"));
              
                db.closeConnection();
        
                System.out.println("Recs created: " + recsAdded);
//               db.openConnection( "com.mysql.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/book",
//                "root", "admin"
//               );
//        
//        int recsDeleted = db.deleteRecordById("author", "author_id", new Integer(52));
//        System.out.println("No. of Recs Deleted: " + recsDeleted);
//        List<Map<String,Object>> list = db.getAllRecords("author", 0);
//        
//        for(Map<String,Object> rec : list) {
//            System.out.println(rec);
//        }
//        db.closeConnection();
        
    }

    
}

