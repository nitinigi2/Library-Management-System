package com.info.bean;

import org.hibernate.SessionFactory;

public interface Search {
    public void searchBookByName(String name, SessionFactory sessionFactory);
    public void searchByAuthor(String author,SessionFactory sessionFactory);
    public void searchBySubject(String subject, SessionFactory sessionFactory);
}
