/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.khj.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author kevinjerke
 */
@Stateless
public class AuthorService implements Serializable {

    //private iAuthorDAO authorDao;

    @PersistenceContext(unitName = "book_PU")
    private EntityManager em;
    
    public AuthorService() {
    }

    /*
    Better to catch exceptions in a more robust manner, but this is faster for now. 
    See class 20 lecture recording for description of what a better strategy may be. 
    */
    public List<Author> getAuthorList() throws Exception{

        List<Author> authorList = new ArrayList<>();
        String jpql = "select a from Author a";
        TypedQuery<Author> q = getEm().createQuery(jpql, Author.class);
        //q.setMaxResults(500);  //Optional
        authorList = q.getResultList();

        return authorList;
    }
    
    public Author findOneAuthorById(Object authorId) 
            throws SQLException, ClassNotFoundException{
        //validation
        if (authorId == null){
            throw new IllegalArgumentException("You must provide a valid author Id");
        }
        
        //logic
//        return authorDao.findOneAuthorById(authorId);
        int id = Integer.parseInt(authorId.toString());
        Author author = getEm().find(Author.class, id);
        return author;
        
    }
    
    
    public int removeAuthorById(String id) //the value passed in will come from a web form, which always returns strings, so this should be your input 
            throws SQLException, ClassNotFoundException, NumberFormatException{
        
        if(id == null || Integer.parseInt(id) <= 0){
            throw new IllegalArgumentException("Id must be an integer greater than Zero.");
        }
        
        Integer value = Integer.parseInt(id); //throws number format exception
        int recordsDeleted = 0;
        String jpql = "DELETE FROM Author a WHERE a.authorId = :id";
        Query q = getEm().createQuery(jpql);
        q.setParameter("id", value);
        recordsDeleted = q.executeUpdate();
        
//        return authorDao.removeAuthorById(value);
        return recordsDeleted;
    }      
        
    public void addAuthor(String authorName)
            throws ClassNotFoundException, SQLException{
        
        //validation

        
        //logic
        //return authorDao.addAuthor(colNames, colValues);
        
        Author author = new Author();
        author.setAuthorName(authorName);
        author.setDateAdded(new Date());
        
        getEm().merge(author);
        
    }
    
    public void updateAuthor(String authorName, String pkValue)
            throws ClassNotFoundException, SQLException{
        
        //validation
        
        //Add for authorName and dateAdded

        if (Integer.parseInt(pkValue) <= 0 || Integer.parseInt(pkValue) > Integer.MAX_VALUE)
            throw new IllegalArgumentException("You must provide a valid Author Id to update any records.");
        
        //logic
        //return authorDao.updateAuthorById(colNames, colValues, pkValue);
        
        Date dateAdded = new Date();
//        String jpql = "UPDATE Author a SET a.authorName = '" + authorName +
//                "', a.dateAdded = '" + dateAdded + "'WHERE a.authorId = '" + id + "'";
//        Query q = getEm().createQuery(jpql);
//        recordsUpdated = q.executeUpdate();   
        
        Author tempAuth = findOneAuthorById(pkValue);
        tempAuth.setAuthorName(authorName);
        tempAuth.setDateAdded(dateAdded);
        
        getEm().merge(tempAuth);
                 
    }
    
    /**
     * 
     * @return em
     */
    public EntityManager getEm() {
        return em;
    }

    /**
     * 
     * @param em 
     */
    public void setEm(EntityManager em) {
        //validation
        
        //logic
        this.em = em;
    }


        
    }


