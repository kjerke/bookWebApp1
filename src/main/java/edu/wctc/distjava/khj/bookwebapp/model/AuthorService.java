/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.khj.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *
 * @author kevinjerke
 */
public class AuthorService {

     private IAuthorDao authorDao;

    public AuthorService(IAuthorDao authorDao) {
        setAuthorDao(authorDao);
    }

    public final List<Author> getAuthorList()
            throws SQLException, ClassNotFoundException {

        return authorDao.getListOfAuthors();
    }
    
    public final Author findAuthorById(Object authorId) 
            throws SQLException, ClassNotFoundException{
     
        if (authorId == null){
            throw new IllegalArgumentException("You must provide a valid author Id");
        }
        
        
        return authorDao.findAuthorById(authorId);
        
    }
    
   
    public final int removeAuthorById(String id) //the value passed in will come from a web form, which always returns strings, so this should be your input 
            throws SQLException, ClassNotFoundException, NumberFormatException{
        
        if(id == null || Integer.parseInt(id) <= 0){
            throw new IllegalArgumentException("Id must be an integer greater than Zero.");
        }
        
        Integer value = Integer.parseInt(id); 
        
        return authorDao.removeAuthorById(value);
    }
    
    public final int addAuthor(List<String> colNames, List<Object>colValues)
            throws ClassNotFoundException, SQLException{
        
       
        if(colNames == null)
            throw new IllegalArgumentException("You must provide valid column names to be updated.");
        if(colValues == null)
            throw new IllegalArgumentException("You must provide appropriate values for each colum to be updated.");
        
       
        return authorDao.addAuthor(colNames, colValues);
        
    }
    
    public final int updateAuthor(List<String> colNames, List<Object> colValues, 
            int pkValue) throws ClassNotFoundException, SQLException{
        
       
        if (colNames == null) {
            throw new IllegalArgumentException("You must provide valid column names to be updated.");
        }
        if (colValues == null) {
            throw new IllegalArgumentException("You must provide appropriate values for each colum to be updated.");
        }
        if (pkValue <= 0 || pkValue > Integer.MAX_VALUE)
            throw new IllegalArgumentException("You must provide a valid Author Id to update any records.");
       
        return authorDao.updateAuthorById(colNames, colValues, pkValue);
    }
    

    public IAuthorDao getAuthorDao() {
        return authorDao;
    }

    //do validation /final
    public void setAuthorDao(IAuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public static void main(String[] args)
            throws SQLException, ClassNotFoundException {

        IAuthorDao dao = new AuthorDao(
                "com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book",
                "root", "admin",
                new MySqlDataAccess()
        );

        AuthorService authorService
                = new AuthorService(dao);

        List<Author> list = authorService.getAuthorList();

        for (Author a : list) {
            System.out.println(a.getAuthorId() + ", "
                    + a.getAuthorName() + ", " + a.getDateAdded() + "\n");
        }
    }
}
