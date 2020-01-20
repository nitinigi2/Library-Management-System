package com.info.utility;

import com.info.bean.Book;
import com.info.bean.BookType;
import com.info.bean.Customer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CustomerUtility {

    //private static final Logger logger = LoggerFactory.getLogger(CommonUtility.class);

   // private static Logger log = LogManager.getLogger(CustomerUtility.class);

    public List<Book> booksIssuedByMe(String customerId, SessionFactory sessionFactory) {

        // db side
        Session session = sessionFactory.openSession();

        // creating transaction object
        Transaction t = session.beginTransaction();

        String hql = "from Customer where id = :id";
        Query query1 = session.createQuery(hql);
        query1.setParameter("id", customerId);

        Customer customer1 = null;
        try {
            customer1 = (Customer) query1.getSingleResult();
        } catch (Exception e) {

        }

        Query query = session.createQuery("from Book where issuedby = :issuedby");
        query.setParameter("issuedby", customer1);
        List<Book> list = query.list();

        if (list.size() == 0) {
            System.out.println("You have not issued any book. ");
            return list;
        }

        System.out.println("Total Number of book issued by me : " + list.size());
        System.out.println("Book Details: ");
        System.out.format("%32s%32s%32s%32s%32s", "Name", "Author", "BookType Id", "Date of Issue", "Bar Code");
        System.out.println();
        for (Book book : list) {
            BookType bookType = book.getBookType();
            System.out.format("%32s%32s%32s%32s%32s", bookType.getBookName(), bookType.getAuthor(), bookType.getBookId(), book.getDateOfIssue(), book.getBarcode());
            System.out.println();
        }
        //System.out.println(list);
        t.commit();
        session.close();
        return list;
    }

    // print profile for Cusomter
    List<String> userData = new ArrayList<>();

    public Customer showProfile(String id, SessionFactory sessionFactory) {

        System.out.println("Id" + id);

        // db
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        String hql = "from Customer where id = :id";
        Query query1 = session.createQuery(hql);
        query1.setParameter("id", id);
        Customer c = null;
        try {
            c = (Customer) query1.getSingleResult();
            tx.commit();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        } finally {
            session.close();
        }


        System.out.println(c);

        userData.add(c.getName());
        userData.add(String.valueOf(c.getMobNumber()));
        userData.add(c.getAddress());
        userData.add(c.getDob());
        userData.add(String.valueOf(c.getNoBooksCanBeIssued()));


        System.out.println("Name :" + c.getName()
                + "\n Mobile Number: " + c.getMobNumber()
                + "\n Address: " + c.getAddress()
                + "\n Date of Birth: " + c.getDob()
                + "\n Number of books can issue: " + c.getNoBooksCanBeIssued());

        System.out.println(userData);
        //log.debug(""+c);
        return c;

    }

    public List<String> getUserData() {
        return userData;
    }

}
