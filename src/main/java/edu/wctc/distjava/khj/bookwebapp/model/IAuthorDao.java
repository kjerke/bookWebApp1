/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.khj.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author kevinjerke
 */
public interface IAuthorDao {

    public abstract int removeAuthorById(Integer id) throws ClassNotFoundException, SQLException;

    public abstract List<Author> getListOfAuthors() throws SQLException, ClassNotFoundException;

    public int updateAuthor(List<Object> colValue, Object pkValue) throws SQLException, ClassNotFoundException;

    public abstract int addAuthor(List<Object> colValues) throws ClassNotFoundException, SQLException;

}
