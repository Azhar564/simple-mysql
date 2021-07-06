/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycrud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author azhar
 */
public class MyCRUD {

    private Connection conn;
    public MyCRUD(String table) {
        try {
            conn = DriverManager.getConnection(MyDB.DATABASE_CONNECTION, MyDB.USERNAME, MyDB.PASSWORD);
            MyDB.setTable(table);
        } catch (SQLException ex) {
            Logger.getLogger(MyCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @return the table from database
     */
    public Object[][] GetMyTable(){
        Object[][] table;
        String query = "SELECT * FROM " + MyDB.getTable();
        try {
            ResultSet rs = conn.createStatement().executeQuery(query);
            MyDB.fillMetaData(rs);
            table = new Object[MyDB.rowCount][MyDB.columnCount];
            int i = 0;
            while(rs.next()){
                for (int j = 0; j < MyDB.columnCount; j++) {
                    table[i][j] = rs.getString(j+1);
                }
                i++;
            }
        } catch (SQLException e) {
            table = null;
            Logger.getLogger(MyCRUD.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return table;
    }
    
    /**
     * 
     * @param query Custom your query
     * @return the table from database
     */
    public Object[][] GetMyTable(String query){
        Object[][] table;
        try {
            ResultSet rs = conn.createStatement().executeQuery(query);
            MyDB.fillMetaData(rs);
            table = new Object[MyDB.rowCount][MyDB.columnCount];
            int i = 0;
            while(rs.next()){
                for (int j = 0; j < MyDB.columnCount; j++) {
                    table[i][j] = rs.getString(j+1);
                }
                i++;
            }
        } catch (SQLException e) {
            table = null;
            Logger.getLogger(MyCRUD.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return table;
    }
    
    /**
     * 
     * @param state The state if you update = true or insert = false
     * @param data Data to save in database
     * @return check if your data saved or not 
     */
    public boolean SaveMyData(boolean state, Object[] data){
        boolean saved = false;
        String query = state ?
                "update " + MyDB.getTable() + " set " + MyDB.updateSet(data): 
                "insert into " + MyDB.getTable() + " values " + MyDB.insertSet(data);
        System.out.println(query);
        try {
            conn.createStatement().executeUpdate(query);
            saved = true;
        } catch (SQLException e) {
            Logger.getLogger(MyCRUD.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return saved;
    }
    
    /**
     * 
     * @param id value of column 1 from table row to delete
     * @return check if the data has been deleted or not
     */
    public boolean DeleteMyData(Object id){
        boolean deleted = false;
        try {
            String query = "Delete from " + MyDB.getTable() + " Where " + MyDB.columnName[0] + "='"+id+"'";
            conn.createStatement().executeUpdate(query);
            deleted = true;
        } catch (SQLException e) {
            Logger.getLogger(MyCRUD.class.getName()).log(Level.SEVERE, null, e);
        }
        return deleted;
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //MyCRUD cRUD = new MyCRUD("data_buku");
        //Object[] get = cRUD.GetMyTable()[0];
        
        //System.out.println(Arrays.toString(get));
        //cRUD.SaveMyData(true, get);
        //System.out.println(MyDB.getTable());
        System.out.println(MyDB.joinFormat(new String[]{"satu","dua"}, new String[]{"id"}));
    }
    
}
