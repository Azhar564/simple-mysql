/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycrud;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 * @author azhar
 */
public class MyDB {
    //Connection Variable
    public static final String 
            database = "jdbc:mysql://127.0.0.1:3306/siperpus", 
            username = "root", 
            password = "";
    
    //Metadata Variable
    public static int rowCount, columnCount;
    public static String[] columnName, columnLabel;
    
    //Table
    private static String table;

    /**
     * 
     * @return Table Name
     */
    public static String getTable() { return MyDB.table; }

    /**
     * 
     * @param table set the table name
     */
    public static void setTable(String table) { MyDB.table = table; }
    
    /**
     * 
     * @param rs get ResultSetMetaData from ResultSet 
     */
    public static void fillMetaData(ResultSet rs){
        try {
            columnCount = rs.getMetaData().getColumnCount();
            
            columnName = new String[columnCount];
            columnLabel = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnName[i] = rs.getMetaData().getColumnName(i+1);
                columnLabel[i] = rs.getMetaData().getColumnLabel(i+1);
            }
            
            rs.last();
            rowCount = rs.getRow();
            rs.beforeFirst();
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
    
    /**
     * insert set format
     * @param data data to join
     * @return String join the data
     */
    public static String insertSet(Object[] data){
        String[] dataStr = Arrays.copyOf(data, 0, String[].class);
        return "("+ String.join(", ", dataStr) +")";
    }
    
    /**
     * update set format
     * @param data data to join
     * @return String join the data
     */
    public static String updateSet(Object[] data){
        String[] dataStr = Arrays.copyOf(data, data.length, String[].class);
        String[] dataStrTemp = new String[dataStr.length-1];
        for (int i = 1; i < dataStr.length; i++) {
            dataStrTemp[i-1] = columnName[i] +"='"+dataStr[i]+"'";
        }
        return String.join(", ", dataStrTemp) + " Where " + columnName[0] + "='" + dataStr[0] + "'";
    }
    
    /**
     * 
     * @param table all table to inner join
     * @param id the id to make condition inner join
     * 
     * @return if table = {"one","two","three"} id = {"id","id2"} it will be "(one inner join two on one.id=two.id) inner join three on two.id2 = three.id2"
     */
    public static String joinFormat(String[] table, String[] id){
        String inJoin = "";
        if (table.length > 1 && id.length == table.length-1) {
            inJoin = "(" + table[0];
            for (int i = 0; i < id.length; i++) {
                inJoin += " INNER JOIN " + table[i+1] + " ON " + table[i] + "." + id[i] + "=" + table[i+1] + "." + id[i] + ")";
            }
        }
        else{
            System.out.println("Array out of bound");
        }
        return inJoin;
    }
    
}
