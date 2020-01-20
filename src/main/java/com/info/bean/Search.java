package com.info.bean;

import org.hibernate.SessionFactory;

import javax.ejb.Local;
import java.util.ArrayList;

@Local
public interface Search {
    public void searchBookByName(String name, SessionFactory sessionFactory);
    public void  searchByAuthor(String author,SessionFactory sessionFactory);
    public void  searchBySubject(String subject, SessionFactory sessionFactory);
    public BookType searchBook(String bookProp, String bookType);
}
