/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.distjava.khj.bookwebapp.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author kevinjerke
 */
@Stateless
public class BookService extends AbstractFacade<Book> {

    @PersistenceContext(unitName = "book_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEm() {
        return em;
    }

    public BookService() {
        super(Book.class);
    }
    
    public void updateBookIsbn(String bookId, String isbn ) {
        Book book = this.findById(new Integer(bookId));
        book.setIsbn(isbn);
        this.edit(book);
    }
            
            
    public void addOrUpdateBook(String bookId, String title, String isbn, String authorId){
        Book book = null;
        
        if(bookId == null || bookId.isEmpty()) {
            book = new Book();
        } else{
            book = new Book(new Integer(bookId));
        }
        book.setTitle(title);
        book.setIsbn(isbn);
        Author author = getEm().find(Author.class, new Integer(authorId));
        book.setAuthorId(author);
    }
    
}
