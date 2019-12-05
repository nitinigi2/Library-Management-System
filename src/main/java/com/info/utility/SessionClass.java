package com.info.utility;

import com.info.bean.*;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class SessionClass {
    private static SessionFactory sessionFactory;

    public static void createSessionFactoryObject() {

        try {
            Configuration configuration = new Configuration().configure().addAnnotatedClass(Customer.class).addAnnotatedClass(User.class)
                    .addAnnotatedClass(Book.class).addAnnotatedClass(BookType.class)
                    .addAnnotatedClass(Vendor.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
                    applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (HibernateException he) {
            System.err.println("Error creating Session: " + he);
            throw new ExceptionInInitializerError(he);
        }
    }

    public static SessionFactory getSessionFactoryObject() {
        return sessionFactory;
    }
}
