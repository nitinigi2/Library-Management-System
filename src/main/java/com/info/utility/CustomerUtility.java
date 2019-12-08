package com.info.utility;

import com.info.bean.Book;
import com.info.bean.BookType;
import com.info.bean.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerUtility {

    public void booksIssuedByMe(String customerId, SessionFactory sessionFactory) {
        // db side

        Session session = sessionFactory.openSession();

        // creating transaction object
        Transaction t = session.beginTransaction();

        String hql = "from Customer where id = :id";
        Query query1 = session.createQuery(hql);
        query1.setParameter("id", customerId);

        Customer customer1=null;
        try {
            customer1 = (Customer) query1.getSingleResult();
        }catch (Exception e){
            System.out.println("Cannot fetch books issued by this customer. Please try again. ");
        }

        Query query = session.createQuery("from Book where issuedby = :issuedby");
        query.setParameter("issuedby", customer1);
        List<Book> list = query.list();

        if(list.size()==0){
            System.out.println("You have not issued any book. ");
            return;
        }

        System.out.println("Total Number of book issued by me : " + list.size());
        System.out.println("Book Details: ");
        System.out.format("%16s%16s%16s%16s%16s", "Name", "Author", "BookType Id", "Date of Issue", "Bar Code");
        System.out.println();
        for(Book book : list){
            BookType bookType = book.getBookType();
            System.out.format("%16s%16s%16s%16s%16s", bookType.getBookName(), bookType.getAuthor(), bookType.getBookId(),book.getDateOfIssue(), book.getBarcode());
            System.out.println();
        }
        //System.out.println(list);
        t.commit();
        session.close();

    }

    // print profile for Cusomter
    public void showProfile(String id, SessionFactory sessionFactory) {

        // db
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        String hql = "from Customer where id = :id";
        Query query1 = session.createQuery(hql);
        query1.setParameter("id", id);
        Customer c=null;
        try {
            c = (Customer) query1.getSingleResult();
            tx.commit();
        }catch (Exception e){

        }finally {
            session.close();
        }

        System.out.println("Name :" + c.getName()
                + "\n Mobile Number: " + c.getMobNumber()
                + "\n Address: " + c.getAddress()
                + "\n Date of Birth: " + c.getDob()
                + "\n Number of books can issue: " + c.getNoBooksCanBeIssued());
    }

}
