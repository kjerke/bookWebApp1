/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.khj.bookwebapp.model;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author kevinjerke
 */
public class MockAuthorDao implements IAuthorDao {
 
    public MockAuthorDao() {
        
    }

    @Override
    public List<Author> getListOfAuthors() throws SQLException, ClassNotFoundException {
        List<Author> list = Arrays.asList(
        new Author(1,"John Doe", new Date()),
        new Author(2,"Bob Smith", new Date())           
        );
       return list;  
    }
        
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        IAuthorDao dao = new MockAuthorDao();
        
//        List<Author> list = new AuthorDao(
//        "com.mysql.jdbc.Driver",
//        "jdbc:mysql://localhost:3306/book",
//        "root", "admin",
//                new MySqlDataAccess("com.mysql.jdbc.Driver",
//        "jdbc:mysql://localhost:3306/book",
//        "root", "admin")
//                        );
        
        List<Author> list = dao.getListOfAuthors();
        
        for(Author a: list) {
            System.out.println(a.getAuthorId() + ","
            + a.getAuthorName() + ", " + a.getDateAdded() + "\n");
        }
        
    }
    
    @Override
    public final int removeAuthorById(Integer id) throws ClassNotFoundException, SQLException {
        return 1;
    }

    @Override
    public Author findAuthorById(Object authorId) throws SQLException, ClassNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int addAuthor(List<String> colNames, List<Object> colValues) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int updateAuthorById(List<String> colNames, List<Object> colValues, int pkValue) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
