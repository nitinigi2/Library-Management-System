package com.info.utility;

import com.info.bean.BookType;
import com.info.bean.Search;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CommonUtility implements Search {
    LoginUtility loginUtility = new LoginUtility();
    SessionFactory sessionFactory = loginUtility.getSessionfactory();

    //public static final Logger logger = LoggerFactory.getLogger(CommonUtility.class);

    BookType bookTypeList = null;

    public void showAllAvailableBooks(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        // creating transaction object
        Transaction t = session.beginTransaction();

        Query query = session.createQuery("from BookType");
        List<BookType> list = query.list();

        System.out.println("All available Books in Library : ");
        System.out.format("%32s%32s%32s%32s", "Name", "Author", "BookType Id", "Number of Books");
        System.out.println();
        for (BookType bookType : list) {
            System.out.format("%32s%32s%32s%32s", bookType.getBookName(), bookType.getAuthor(), bookType.getBookId(), bookType.getBookQuantity());
            System.out.println();
        }

        // System.out.println(list);
        t.commit();
        session.close();

    }

    public void searchBookByName(String bookName, SessionFactory sessionFactory) {
        bookTypeList=null;

        Session session = sessionFactory.openSession();

        // creating transaction object
        Transaction t = session.beginTransaction();

        Query query = session.createQuery("from BookType where bookName = :bookname");
        query.setParameter("bookname", bookName);
        List<BookType> list = query.list();
        if (list.size() == 0) {
            System.out.println("No book available");
        }

        System.out.format("%16s%16s%16s%32s", "Name", "Author", "BookType Id", "Number of Books");
        System.out.println();

        for (BookType bookType : list) {
            System.out.format("%16s%16s%16s%32s", bookType.getBookName(), bookType.getAuthor(), bookType.getBookId(), bookType.getBookQuantity());
            System.out.println();
            bookTypeList = bookType;
        }

        // System.out.println(list);
        t.commit();
        session.close();
    }

    @Override
    public void searchByAuthor(String author, SessionFactory sessionFactory) {
        bookTypeList=null;
        Session session = sessionFactory.openSession();

        // creating transaction object
        Transaction t = session.beginTransaction();

        Query query = session.createQuery("from BookType where author = :author");
        query.setParameter("author", author);
        List<BookType> list = query.list();

        if (list.size() == 0) {
            System.out.println("No book available");
        }

        System.out.format("%32s%32s%32s%32s", "Name", "Author", "BookType Id", "Number of Books");
        System.out.println();

        for (BookType bookType : list) {
            System.out.format("%32s%32s%32s%32s", bookType.getBookName(), bookType.getAuthor(), bookType.getBookId(), bookType.getBookQuantity());
            System.out.println();
            bookTypeList=bookType;
        }

        //System.out.println(list);
        t.commit();
        session.close();
    }

    @Override
    public void searchBySubject(String subject, SessionFactory sessionFactory) {
        bookTypeList=null;
        Session session = sessionFactory.openSession();

        // creating transaction object
        Transaction t = session.beginTransaction();

        Query query = session.createQuery("from BookType where subject = :subject");
        query.setParameter("subject", subject);
        List<BookType> list = query.list();

        if (list.size() == 0) {
            System.out.println("No book available");
        }

        System.out.format("%32s%32s%32s%32s", "Name", "Author", "BookType Id", "Number of Books");
        System.out.println();

        for (BookType bookType : list) {
            System.out.format("%32s%32s%32s%32s", bookType.getBookName(), bookType.getAuthor(), bookType.getBookId(), bookType.getBookQuantity());
            System.out.println();
            bookTypeList=bookType;
        }

        // System.out.println(list);
        t.commit();
        session.close();

    }

    @Override
    public BookType searchBook(String bookProp, String bookType) {
        System.out.println("Search book is called. ");
        System.out.println("BookProp: " + bookProp + "BookType: " + bookType);
        //logger.debug("BookProp: " + bookProp + "BookType: " + bookType);
        if (bookProp != null && bookType != null) {
            if (bookType.equals("Author Name")) searchByAuthor(bookProp, sessionFactory);
            else if (bookType.equals("Book Name")) searchBookByName(bookProp, sessionFactory);
            else if (bookType.equals("Subject Name")) searchBySubject(bookProp, sessionFactory);
        }
        return bookTypeList;
    }

}
